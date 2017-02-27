package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface API responsible for making sure all calls from the client to the server and back
 * follow some guidelines, in order to garantuee appropiate communication.
 *
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano <mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public interface TestRemote extends Remote {

	/**
	 * Test method to check whether the client-server arquitecture
	 * is properly set up and working.
     *
	 * @param test String to compare to.
	 * @return whether the string is the same as "test".
	 */
	Boolean test(String test) throws RemoteException;

	/**
	 * Given two number, multiply them.
	 *
     * @param a the first number
	 * @param b the second number
	 * @return the result of the multiplication
	 */
    Integer multiply(Integer a, Integer b) throws RemoteException;
    
    
	/**
	 * Given a string, check if its palindrome, in other words, 
	 * if its the same word when spelled backwards.
	 *
     * @param string the potental palindrome
	 * @return if the string is a palindrome or not
	 */
    Boolean isPalindrome(String string) throws RemoteException;

}
