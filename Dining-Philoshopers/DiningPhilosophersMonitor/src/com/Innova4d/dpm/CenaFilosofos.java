package com.Innova4d.dpm;

/**
 * Esta versión no causa Dealock.
 * Un filósofo podría morir de hambre.
 * 
 * @author Francisco Gutiérrez
 * @version v1.0
 *
 */
public class CenaFilosofos {
	
	public static void main (String[] args) {
        int num_filosofos = Integer.parseInt(args[0]);
		Filosofo[] filosofos = new Filosofo[num_filosofos];
		
        // El monitor controla la asignación de recursos compartidos.
		Monitor monitor = new Monitor(num_filosofos);
		for (int i = 0; i < num_filosofos; i++) {
			filosofos[i] = new Filosofo(i, monitor);
			new Thread(filosofos[i]).start();
		}
	}
}
