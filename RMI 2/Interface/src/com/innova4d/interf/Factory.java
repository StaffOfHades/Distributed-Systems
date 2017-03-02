package com.innova4d.interf;

import java.rmi.RemoteException;

public class Factory {

    
    private final static int AVION = 0;
    private final static int AUTO = 1;
    private final static int BOEING = 2;
    private final static int UFO = 3;
    
    public static String getName(int i) throws RemoteException {
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
            default:
                name = "-> Libre";
                break;
        } 
        
        return name;
    }

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
                v = new Boeing("TrasAtlantico", BOEING, 0, 3);
                break;
            case UFO:
                v = new UFO("SCOUT", UFO, 0, 1);
                break;
            default:
                v = null;
                break;
        }

        return v;  
    }

}

