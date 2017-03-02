package com.innova4d.interf;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Implementacion concreta de Vehiculo, en forma de UFO, que permite ser serializado
 */
public class UFO extends Vehiculo implements Serializable, ObjectInputValidation {
	
	// Serial Unique ID 
	private static final long serialVersionUID = 572L;

    /**
     * Constructor publico por defacto para la creacion de un UFO
     * @param id Con la cual asociar el ufo
     * @param x Carril que ocupa en la pista
     * @param y Posicion que ocupa en el carril
     * @param v Velocidad del ufo
     */
	public UFO(String id, int x, int y, int v) throws RemoteException {
		this.setId(id);
        this.setX(x);
		this.setY(y);
        this.setVelocidad(v);
	}

    /**
     * Metodo de apoyo para deserializar el ufo
     * @param in ObjectStream encargado de recrear el ufo
     */
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.setId( (String) in.readObject() );
        this.setX(in.readInt());
        this.setY(in.readInt());
        this.setVelocidad(in.readInt());
    }
    
    /**
     * Metodo de apoyo para serializar el ufo
     * @param out ObjectStream encargado de convertir el ufo
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(this.getId());
        out.writeInt(this.getX());
        out.writeInt(this.getY());
        out.writeInt(this.getVelocidad());
    }

    @Override
    public void validateObject() throws InvalidObjectException {
        try {
            // El id debe existir y ser valido
            if (this.getId() == null || this.getId().isEmpty() )
                throw new InvalidObjectException("Id cannot be null or empty");
            
            // La posicion X debe ser una coordenda no negativa
            if (this.getX() < 0)
                throw new InvalidObjectException("X cannot be negative");
           
            // La posicion Y debe ser una coordenda no negativa
            if (this.getY() < 0)
                throw new InvalidObjectException("Y cannot be negative"); 
            
            // La velocidad debe ser un numero positivo
            if (this.getVelocidad() <= 0)
                throw new InvalidObjectException("Speed cannot be negative");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
