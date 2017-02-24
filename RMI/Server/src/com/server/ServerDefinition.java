package com.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.interf.test.TestRemote;

/**
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano <mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public class ServerDefinition extends UnicastRemoteObject implements TestRemote {

	// Default (serialized & unique) identifier
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. Must implement constructor matching super.
	 */
	ServerDefinition() throws RemoteException {
		super();
	}

	@Override
	public Boolean test(String test) throws RemoteException {
		return test.equalsIgnoreCase("test");
	}

	@Override
	public Integer multiply(Integer a, Integer b) throws RemoteException {
		// Check whether we have a correct value for both numbers
		if (a == null || b == null) {
			return null;
		}

		return a * b;
	}

	@Override
	public Boolean isPalindrome(String string) throws RemoteException {
        String copy = "";
        
        // Create an inverted copy of the recieved string,
        for (int i = string.length(); i > 0; i--) {
            copy += string.charAt(i - 1);
        }

		// and check the inverted string is the same as the recieved one.
        return copy.equals(string);
	}

}
