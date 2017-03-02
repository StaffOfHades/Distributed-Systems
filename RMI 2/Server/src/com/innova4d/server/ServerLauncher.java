package com.innova4d.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.innova4d.interf.Constant;

public class ServerLauncher implements Constant {

	/**
	 * 
	 * @param args
	 * @throws AccessException
	 * @throws RemoteException
	 * @throws AlreadyBoundException
	 */
	public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {
        // Create the class which implements RemoteInterface,
		ControlTower control = new ControlTower();
        
        // Create the server,
		Registry registry = LocateRegistry.createRegistry(RMI_PORT);
		
        // Add the RemoteInterface with an id,
        registry.bind(RMI_ID, control);
        
        // And if everything was succesful, show a message.
		System.out.println("Control Tower (Server) has started");
	}
}
