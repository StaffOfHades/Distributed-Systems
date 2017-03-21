package com.Innova4d.DP;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * El problema de los filósofos (Esta versión causa un Deadlock).
 * @author Francisco Gutiérrez <fsalvador23@gmail.com>
 * @version 1.0
 */
public class CenaFilosofos {
	// El número de filósofos...
	private static int NUM_FILOSOFOS = 2;
	
	/**
	 * Una prueba de los filósofos.
	 * @param args Not used
	 */
	public static void main (String[] args) {
		NUM_FILOSOFOS = Integer.parseInt(args[0]);

        /*
		 *  Cada tenedor es un recurso compartido.
		 *  Los recursos compartidos en Java se definen como tipo Lock.
		 *  
		 */	
		Lock[] tenedores = new ReentrantLock[NUM_FILOSOFOS];
		for (int i = 0; i < NUM_FILOSOFOS; i++) {
			tenedores[i] = new ReentrantLock();
		}
		
        /*
		 *  Crear un arreglo de filósofos [5] 
		 *  En cada posición del arreglo, crear un filósofo
		 *  Inicializar el Thread para cada filósofo.
		 *   
		 *  En el siguiente ejemplo se implementa un código para inicializar un Thread con un filósofo, 
		 *  realiza el código para inicializar 5 ó más filósofos.
		 *  Checar la variable NUM_FILOSOFOS.
		 */
		Filosofo[] filosofos = new Filosofo[NUM_FILOSOFOS];
        for (int i = 0; i < NUM_FILOSOFOS; i++) {
            filosofos[i] = new Filosofo(i, tenedores[i], tenedores[(i+1)%NUM_FILOSOFOS]);
		    new Thread(filosofos[i]).start();
        }
	}

}
