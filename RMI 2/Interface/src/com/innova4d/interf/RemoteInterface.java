package com.innova4d.interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
	
	public Vehiculo[][] getMapaPistas()  throws RemoteException;
    @Deprecated public Boolean checkInVehiculo(Vehiculo v) throws RemoteException;
    @Deprecated public Vehiculo getVehiculo(String id, int c) throws RemoteException;
	public Boolean moverVehiculo(Vehiculo v, int c) throws RemoteException; 
    public Vehiculo createVehiculo(int c) throws RemoteException;

}
