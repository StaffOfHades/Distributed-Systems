package com.innova4d.interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
	
	public Vehiculo[][] getMapaPistas()  throws RemoteException;
    public Boolean checkInVehiculo(Vehiculo v) throws RemoteException;    
    public Vehiculo getVehiculo(String id, int c) throws RemoteException;
	public Boolean moverVehiculo(Vehiculo v, int c) throws RemoteException; 

}
