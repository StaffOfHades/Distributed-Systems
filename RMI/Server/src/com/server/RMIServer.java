package com.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Server implementation, responsible for setting up the server with the correct
 * values. Most of the work is delagated to the Registry class. 
 *
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano<mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public class RMIServer {
	
	// Server id with which to associate the server.
	public static final String RMI_ID = "TestRMI";
	
	// Server port to use.
	public static final int RMI_PORT = 8080;
	
	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {
		// Create the object responsible for handling the specfic behavior of the API interface.
        ServerDefinition implementation = new ServerDefinition();
        
        // Create a new server, with the specified port, 
		Registry registry = LocateRegistry.createRegistry(RMI_PORT);

        // And add the ID and implementation for the API interface.
		registry.bind(RMI_ID, implementation);

        // If everything is set up correctly, show a succes message	
		System.out.println("Server is running...");
	}
}
