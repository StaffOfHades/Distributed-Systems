package com.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import com.interf.test.TestRemote;

/**
 * Client side implementation of the Client-Server implementation,
 * responsible for interacting with the user. 
 * Through acces to the API interface, calls are directed to the server, 
 * and the result is returned and recieved by the client 
 *
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano <mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public class TestClient {

	// Server id with which to locate.
	public static final String RMI_ID = "TestRMI";
	
	// Server port to be connect to to
	public static final int RMI_PORT = 8080;

    // Server address where its located
    public static final String RMI_ADDRESS = "localhost";

	public static void main(String[] args) throws RemoteException, NotBoundException {	
        // Connect to the server
		Registry registry = LocateRegistry.getRegistry(RMI_ADDRESS, RMI_PORT);

        // And find the remote API interface
		TestRemote remote = (TestRemote) registry.lookup(RMI_ID);
		
        // Run the rest method
        System.out.println("123 == test: " + remote.test("123"));
		System.out.println("test == test: " + remote.test("test"));
        
        // Run the multiplication method
		System.out.println("5 * 5 = " + remote.multiply(5, 5));

        // And finally try the palindrome checker method
        System.out.println("Is \'aba\' a Palindrome? " + remote.isPalindrome("aba"));
        System.out.println("Is \'abba\' a Palindrome? " + remote.isPalindrome("abba"));
        System.out.println("Is \'abb\' a Palindrome? " + remote.isPalindrome("abb"));

        //System.out.println("Is \'" + args[0] + "\' a Palindrome? " + remote.isPalindrome(args[0]));
    }

}
