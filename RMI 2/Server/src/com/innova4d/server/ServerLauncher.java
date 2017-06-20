package com.innova4d.server;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.innova4d.interf.Constant;

/**
 * Implementacion del servidor, encargado de leventarlo de forma adecuada,
 * y ligar el API necesario para recibir llamadas.
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

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
