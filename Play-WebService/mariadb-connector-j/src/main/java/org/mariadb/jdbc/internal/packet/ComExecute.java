/*
MariaDB Client for Java

Copyright (c) 2012-2014 Monty Program Ab.

This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.

This library is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
for more details.

You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to Monty Program Ab info@montyprogram.com.

This particular MariaDB Client for Java file is work
derived from a Drizzle-JDBC. Drizzle-JDBC file which is covered by subject to
the following copyright and notice provisions:

Copyright (c) 2009-2011, Marcus Eriksson , Stephane Giron

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of the driver nor the names of its contributors may not be
used to endorse or promote products derived from this software without specific
prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS  AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
OF SUCH DAMAGE.
*/

package org.mariadb.jdbc.internal.packet;

import org.mariadb.jdbc.internal.packet.dao.parameters.ParameterHolder;
import org.mariadb.jdbc.internal.stream.PacketOutputStream;
import org.mariadb.jdbc.internal.util.dao.ClientPrepareResult;
import org.mariadb.jdbc.internal.util.dao.QueryException;

import java.io.IOException;
import java.util.List;

public class ComExecute {

    public ComExecute() { }

    /**
     * Client-side PrepareStatement.execute() packet send.
     *
     * @param writer outputStream
     * @param clientPrepareResult clientPrepareResult
     * @param parameters parameter
     * @throws IOException if connection fail
     */
    public static void sendSubCmd(final PacketOutputStream writer, final ClientPrepareResult clientPrepareResult, ParameterHolder[] parameters)
            throws IOException {
        writer.write(Packet.COM_QUERY);
        if (clientPrepareResult.isRewriteType()) {

            writer.write(clientPrepareResult.getQueryParts().get(0));
            writer.write(clientPrepareResult.getQueryParts().get(1));
            for (int i = 0; i < clientPrepareResult.getParamCount(); i++) {
                parameters[i].writeTo(writer);
                writer.write(clientPrepareResult.getQueryParts().get(i + 2));
            }
            writer.write(clientPrepareResult.getQueryParts().get(clientPrepareResult.getParamCount() + 2));

        } else {

            writer.write(clientPrepareResult.getQueryParts().get(0));
            for (int i = 0; i < clientPrepareResult.getParamCount(); i++) {
                parameters[i].writeTo(writer);
                writer.write(clientPrepareResult.getQueryParts().get(i + 1));
            }

        }

    }

    /**
     * Client side PreparedStatement.executeBatch values rewritten (concatenate value params according to max_allowed_packet)
     *
     * @param writer outputStream
     * @param queryParts query parts
     * @param parameters parameters
     * @param currentIndex currentIndex
     * @param paramCount parameter count
     * @param parameterList parameter list
     * @param rewriteValues is query rewritable by adding values
     * @return current index
     * @throws IOException if connection fail
     */
    public static int sendRewriteCmd(final PacketOutputStream writer, final List<byte[]> queryParts, ParameterHolder[] parameters,
                                     int currentIndex, int paramCount, List<ParameterHolder[]> parameterList, boolean rewriteValues)
            throws IOException {
        writer.startPacket(0);
        writer.buffer.put(Packet.COM_QUERY);

        byte[] firstPart = queryParts.get(0);
        byte[] secondPart = queryParts.get(1);

        if (!rewriteValues) {
            //write first
            writer.write(firstPart, 0, firstPart.length);
            writer.write(secondPart, 0, secondPart.length);

            int staticLength = 1;
            for (int i = 0; i < queryParts.size(); i++) staticLength += queryParts.get(i).length;

            for (int i = 0; i < paramCount; i++) {
                parameters[i].writeTo(writer);
                writer.write(queryParts.get(i + 2));
            }
            writer.write(queryParts.get(paramCount + 2));

            // write other, separate by ";"
            while (currentIndex < parameterList.size()) {
                parameters = parameterList.get(currentIndex);

                //check packet length so to separate in multiple packet
                int parameterLength = 0;
                boolean knownParameterSize = true;
                for (ParameterHolder parameter : parameters) {
                    long paramSize = parameter.getApproximateTextProtocolLength();
                    if (paramSize == -1) {
                        knownParameterSize = false;
                        break;
                    }
                    parameterLength += paramSize;
                }

                if (knownParameterSize) {
                    //We know the additional query part size. This permit :
                    // - to resize buffer size if needed (to avoid resize test every write)
                    // - if this query will be separated in a new packet.
                    if (writer.checkRewritableLength(staticLength + parameterLength)) {
                        writer.assureBufferCapacity(staticLength + parameterLength);
                        writer.buffer.put((byte)';');
                        writer.buffer.put(firstPart, 0, firstPart.length);
                        writer.buffer.put(secondPart, 0, secondPart.length);
                        for (int i = 0; i < paramCount; i++) {
                            parameters[i].writeUnsafeTo(writer);
                            writer.writeUnsafe(queryParts.get(i + 2));
                        }
                        writer.writeUnsafe(queryParts.get(paramCount + 2));
                        currentIndex++;
                    } else {
                        break;
                    }
                } else {
                    //we cannot know the additional query part size.
                    writer.write(';');
                    writer.write(firstPart, 0, firstPart.length);
                    writer.write(secondPart, 0, secondPart.length);
                    for (int i = 0; i < paramCount; i++) {
                        parameters[i].writeTo(writer);
                        writer.write(queryParts.get(i + 2));
                    }
                    writer.write(queryParts.get(paramCount + 2));
                    currentIndex++;
                }
            }

        } else {
            writer.write(firstPart, 0, firstPart.length);
            writer.write(secondPart, 0, secondPart.length);
            int lastPartLength = queryParts.get(paramCount + 2).length;
            int intermediatePartLength = queryParts.get(1).length;

            for (int i = 0; i < paramCount; i++) {
                parameters[i].writeTo(writer);
                writer.write(queryParts.get(i + 2));
                intermediatePartLength += queryParts.get(i + 2).length;
            }

            while (currentIndex < parameterList.size()) {
                parameters = parameterList.get(currentIndex);

                //check packet length so to separate in multiple packet
                int parameterLength = 0;
                boolean knownParameterSize = true;
                for (ParameterHolder parameter : parameters) {
                    long paramSize = parameter.getApproximateTextProtocolLength();
                    if (paramSize == -1) {
                        knownParameterSize = false;
                        break;
                    }
                    parameterLength += paramSize;
                }

                if (knownParameterSize) {
                    //We know the additional query part size. This permit :
                    // - to resize buffer size if needed (to avoid resize test every write)
                    // - if this query will be separated in a new packet.
                    if (writer.checkRewritableLength(1 + parameterLength + intermediatePartLength + lastPartLength)) {
                        writer.assureBufferCapacity(1 + parameterLength + intermediatePartLength + lastPartLength);
                        writer.buffer.put((byte) ',');
                        writer.buffer.put(secondPart, 0, secondPart.length);

                        for (int i = 0; i < paramCount; i++) {
                            parameters[i].writeUnsafeTo(writer);
                            byte[] addPart = queryParts.get(i + 2);
                            writer.buffer.put(addPart, 0, addPart.length);
                        }
                        currentIndex++;
                    } else {
                        break;
                    }
                } else {
                    writer.write((byte) ',');
                    writer.write(secondPart, 0, secondPart.length);

                    for (int i = 0; i < paramCount; i++) {
                        parameters[i].writeTo(writer);
                        writer.write(queryParts.get(i + 2));
                    }
                    currentIndex++;
                }
            }
            writer.write(queryParts.get(paramCount + 2));
        }

        writer.finishPacketWithoutRelease(true);
        return currentIndex;
    }

    /**
     * Statement.executeBatch() rewritten multiple (concatenate with ";") according to max_allowed_packet)
     *
     * @param writer outputstream
     * @param firstQuery first query
     * @param queries queries
     * @param currentIndex currentIndex
     * @return current index
     * @throws IOException if connection error occur
     */
    public static int sendMultiple(final PacketOutputStream writer, String firstQuery, List<String> queries, int currentIndex) throws IOException {
        writer.startPacket(0);
        writer.write(Packet.COM_QUERY);
        //index is already set to 1 for first one
        writer.write(firstQuery.getBytes("UTF-8"));

        //add query with ";"
        while (currentIndex < queries.size()) {
            byte[] sqlByte = queries.get(currentIndex).getBytes("UTF-8");
            if (!writer.checkRewritableLength(sqlByte.length + 1)) break;
            writer.write(';');
            writer.write(sqlByte);
            currentIndex++;
        }

        writer.finishPacketWithoutRelease(true);
        return currentIndex;
    }

    /**
     * Send directly to socket the sql data.
     *
     * @param writer output stream
     * @param sqlBytes the query in UTF-8 bytes
     * @throws IOException    if connection error occur
     * @throws QueryException if packet max size is to big.
     */
    public static void sendDirect(final PacketOutputStream writer, byte[] sqlBytes) throws IOException, QueryException {
        writer.sendDirect(sqlBytes, 0, sqlBytes.length, Packet.COM_QUERY);
    }

}
