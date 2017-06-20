package com.innova4d.interf;

/**
 * Interface con las constantes comunes entre servidor y cliente necesarias
 * para su funcionamiento.
 *
 * Un buen diseño crea las constantes en un clase separada
 * donde se mantienen los mismos valores siempre;
 * En este caso la hacemos una interface, para facilitar el accesso;
 * Esto permite que alguien los herede y los use.
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

public interface Constant {
	
    // El id asociado al RemoteInterface
    String RMI_ID = "TestRMI";
    
    // El puerto de acceso al servidor
	int RMI_PORT = 8080;
    
    // La direccion de IP del servidor
    String RMI_ADDRESS = "localhost"; 
    
    // El ancho (cantidad de vehiculos) que soporta la pista, definido en coordenas X
    int ANCHO = 4;
    
    // La altura (largo) que soprta la pista, definida en coordenadas Y
    int ALTURA = 12;

}
