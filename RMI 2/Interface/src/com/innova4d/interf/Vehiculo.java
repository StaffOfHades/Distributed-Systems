package com.innova4d.interf;

import java.rmi.RemoteException;

public abstract class Vehiculo {

    private String id;	
	private int x;
	private int y;
    private int v;
   
    public String getId() throws RemoteException  {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

    public int getVelocidad() {
        return v;
    }

    public void setVelocidad(int v) {
        this.v = v;
    }
}
