package com.Innova4d.DP;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Solucion al problema de los filósofos que no resuelve el Deadlock).
 * @author <a title="mauricio.gracianoaz@udlap.mx" href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @version 1.7
 */
public class CenaFilosofos {
	
	/**
	 * Una prueba de los filósofos.
	 * @param args Parametros de entrada
	 */
	public static void main (String[] args) {
	    // El número de filósofos...
    	int numFilosofos = 2;
        if (args.length > 0)
		    numFilosofos = Integer.parseInt(args[0]);

        /*  Cada tenedor es un recurso compartido.
		 *  Los recursos compartidos en Java se definen como tipo Lock  */
		Lock[] tenedores = new ReentrantLock[numFilosofos];
		for (int i = 0; i < numFilosofos; i++) {
			tenedores[i] = new ReentrantLock();
		}
		
        /*  Crear un arreglo de filósofos [numFilosofos] 
		 *  En cada posición del arreglo, crear un filósofo
		 *  Inicializar el Thread para cada filósofo.
		 *   
		 *  En el siguiente ejemplo se implementa un código para inicializar un Thread con un filósofo, 
		 *  realiza el código para inicializar 5 ó más filósofos.
		 *  Checar la variable numFilosofos.    */
		Filosofo[] filosofos = new Filosofo[numFilosofos];
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, tenedores[i], tenedores[(i+1)%numFilosofos]);
		    new Thread(filosofos[i]).start();
        }
	}

}
