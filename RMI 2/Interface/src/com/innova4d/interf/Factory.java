package com.innova4d.interf;

import java.rmi.RemoteException;

/**
 * Implementacion concreta de Vehiculo, en forma de Auto,
 * que permite ser serializado.
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

public class Factory {

    
    private final static int AVION = 0;
    private final static int AUTO = 1;
    private final static int BOEING = 2;
    private final static int UFO = 3;
    private final static int TRANVIA = 4; 
   
    /**
     * Metodo estatico que permite recuperar 
     * el nombre actual de vehiculo en una pista.
     *
     * @param i Indice del vehiculo actual
     * @return Nombre del vehiculo actual
     */
    public static String getName(int i) {
        String name = "<- ";
        switch (i) {
            case AVION:
                name += "Avion";
                break;
            case AUTO:
                name += "Auto";
                break;
            case BOEING:
                name += "Boeing";
                break;
            case UFO:
                name += "UFO";
                break;
            case TRANVIA:
                name += "Tranvia";
                break;
            default:
                name = "-> Libre";
                break;
        } 
        
        return name;
    }

    /**
     * Metodo estatico que permite crear el vehiculo 
     * apropiado para cada pista.
     *
     * @param i Indice del vehiculo actual
     * @return El vehiculo actuactual
     * @throws RemoteExceptio
     */
    public static Vehiculo getVehiculo(int i) throws RemoteException {
       final Vehiculo v;

        switch (i) {
            case AUTO:
                v = new Auto("Ferrari", AUTO, 0, 5);
                break;
            case AVION:
                v = new Avion("Lufthansa", AVION, 0, 2);
                break;
            case BOEING:
                v = new Boeing("Atlantico", BOEING, 0, 3);
                break;
            case UFO:
                v = new UFO("SCOUT", UFO, 0, 1);
                break;
            case TRANVIA:
                v = new Tranvia("Transporte", TRANVIA, 0, 4);
                break;
            default:
                v = null;
                break;
        }

        return v;  
    }

}

