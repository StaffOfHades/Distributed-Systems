package com.innova4d.interf;

/**
 * Un buen diseño crea las constantes en un clase separada
 * donde se mantienen los mismos valores siempre;
 * En este caso la hacemos una interface, para facilitar el accesso;
 * Esto permite que alguein los herede y los use.
 */

public interface Constant {
	
    // El id asociado al RemoteInterface
    String RMI_ID = "TestRMI";
    
    // El puerto de acceso al servidor
	int RMI_PORT = 8080;
    
    // La direccion de IP del servidor
    String RMI_ADDRESS = "localhost"; 
    
    // El ancho (cantidad de vehiculos) que soporta la pista, definido en coordenas X
    int ANCHO = 6;
    
    // La altura (largo) que soprta la pista, definida en coordenadas Y
    int ALTURA = 30;

}
