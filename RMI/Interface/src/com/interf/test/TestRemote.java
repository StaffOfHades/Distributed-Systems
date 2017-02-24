package com.interf.test;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author   Francisco Gutiérrez <fsalvador23@gmail.com>, Mauricio Graciano <mau.graci@gmail.com>
 * @version  0.2
 * @since    2017-02-22
 */
public interface TestRemote extends Remote {

	/**
	 * En ésta interfaz se definen los métodos que deberá
	 * implementar el servidor.
	 *
	 * En caso de agregar métodos que reciban o envíen objetos
	 * Se debe tener encuenta el serializado de dicho objeto (Marshalling).
	 * Para éste ejemplo definimos un simple método test.
	 *
	 * @param test Un String a comparar, si es idéntico a la palabra "test".
	 *
	 * @return true en caso de que sea idéntico a "test".
	 */
	Boolean test(String test) throws RemoteException;

	/**
	 * Given two number, multiply them.
	 * @param a the first number
	 * @param b the second number
	 * @return the result of the multiplication
	 */
    Integer multiply(Integer a, Integer b) throws RemoteException;
    
    
	/**
	 * Given a string, check if its palindrome, in other words, 
	 * if its the same word when spelled backwards.
	 * @param string the potental palindrome
	 * @return if the string is a palindrome or not
	 */
    Boolean isPalindrome(String string) throws RemoteException;

}
