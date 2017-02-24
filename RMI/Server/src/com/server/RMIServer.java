package com.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano<mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public class RMIServer {
	
	// Server id with which to associate the server.
	public static final String RMI_ID = "TestRMI";
	
	// Server port to use.
	public static final int RMI_PORT = 8080;
	
	/**
	 * 
	 * @param args
	 * @throws AccessException
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {
		ServerDefinition impl = new ServerDefinition();
		Registry registry = LocateRegistry.createRegistry(RMI_PORT);
		registry.bind(RMI_ID, impl);
		
		System.out.println("Server is running...");
	}
}
