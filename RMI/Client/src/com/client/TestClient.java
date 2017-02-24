package com.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import com.interf.test.TestRemote;

/**
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano <mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public class TestClient {

	// Server id with which to locate.
	public static final String RMI_ID = "TestRMI";
	
	// Server port to be connect to to
	public static final int RMI_PORT = 8080;

	public static void main(String[] args) throws RemoteException, NotBoundException {
		// Find the server,
		Registry registry = LocateRegistry.getRegistry("localhost", RMI_PORT);
		
		
		TestRemote remote = (TestRemote) registry.lookup(RMI_ID);
		System.out.println("123 == test: " + remote.test("123"));
		System.out.println("test == test: " + remote.test("test"));

		System.out.println("5 * 5 = " + remote.multiply(5, 5));

        System.out.println("Is \'aba\' a Palindrome? " + remote.isPalindrome("aba"));

        System.out.println("Is \'abba\' a Palindrome? " + remote.isPalindrome("abba"));

        System.out.println("Is \'abb\' a Palindrome? " + remote.isPalindrome("abb"));
	}

}
