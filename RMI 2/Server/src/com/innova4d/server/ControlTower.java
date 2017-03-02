package com.innova4d.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.innova4d.interf.*;

public class ControlTower extends UnicastRemoteObject implements RemoteInterface, Constant {
	
	/**
	 * El constructor, protegido para asegurar la integridad de ControlTower.
	 */
	protected ControlTower() throws RemoteException {
		super();
	}

	private Vehiculo[][] mapaPista = new Vehiculo[ANCHO][ALTURA];

	private static final long serialVersionUID = 1L;

	/**
 	 * Regresa el mapa de las pistas.
 	 */
	@Override
	public Vehiculo[][] getMapaPistas() throws RemoteException {
		return this.mapaPista;
	}
	
    /**
	 * Mueve un Vehiculo en la matriz, un bloque a la vez. Izq. Der.
	 */
	@Override
	public Boolean moverVehiculo(Vehiculo v, int c) throws RemoteException {
		Boolean flag = false;
		int newY = v.getY() + 1;
		if (newY < ALTURA) {
	    	try {
				this.mapaPista[c][v.getY()] = null;
				this.mapaPista[c][newY] = v;
				v.setY(newY);
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
                flag = false;
			}
		}
		return flag;
	}
	
  /**
 	* Busca por un Vehiculo en el mapa aéreo usando su ID.
 	* @param id El identificador del vehiculo
 	* @param c  El carril donde se encuentra el vehiculo.
 	*/
	@Override
	public Vehiculo getVehiculo(String id, int c) throws RemoteException {
		Vehiculo v = null;
        int i = 0;
        
        while (v == null && i < ALTURA) {
            v = this.mapaPista[c][i];
            if (v != null && !v.getId().equals(id) )
		    	v = null;
            i++;
        }

		return v;
	}

	@Override
	public Boolean checkInVehiculo(Vehiculo v) throws RemoteException {
		if ( this.mapaPista[ v.getX() ][ v.getY() ] == null)
			this.mapaPista[v.getX()][v.getY()] = v;
		return true;
	}

}
