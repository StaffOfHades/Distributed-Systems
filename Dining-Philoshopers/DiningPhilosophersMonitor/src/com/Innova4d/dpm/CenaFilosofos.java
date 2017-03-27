package com.Innova4d.dpm;

/**
 * Solucion al problema de los filosofos que no causa Dealock, pero no resuelve Starvation
 * 
 * @author <a title="mauricio.gracianoaz@udlap.mx" href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @version 1.7
 */
public class CenaFilosofos {
	
	public static void main (String[] args) {
        // El numero de filosofos
        int num_filosofos = 2;
        if (args.length > 0)
            num_filosofos = Integer.parseInt(args[0]);
		
        Filosofo[] filosofos = new Filosofo[num_filosofos];
		
        // El monitor controla la asignación de recursos compartidos.
		Monitor monitor = new Monitor(num_filosofos);
		for (int i = 0; i < num_filosofos; i++) {
			filosofos[i] = new Filosofo(i, monitor);
			new Thread(filosofos[i]).start();
		}
	}
}
