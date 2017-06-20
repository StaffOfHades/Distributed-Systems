package com.innova4d.interf;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Implementacion concreta de Vehiculo, en forma de Tranvia,
 * que permite ser serializado.
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

public class Tranvia extends Vehiculo implements Serializable, ObjectInputValidation {
	
	// Serial Unique ID 
	private static final long serialVersionUID = 36L;

    /**
     * Constructor publico por defacto para la creacion de un Tranvia
     * @param id Con la cual asociar el tranvia
     * @param x Carril que ocupa en la pista
     * @param y Posicion que ocupa en el carril
     * @param v Velocidad del tranvia
     */
	public Tranvia(String id, int x, int y, int v) {
		this.setId(id);
        this.setX(x);
		this.setY(y);
        this.setVelocidad(v);
	}

    /**
     * Metodo de apoyo para deserializar el tranvia
     * @param in ObjectStream encargado de recrear el tranvia
     */
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
        in.defaultReadObject();
        this.setId( (String) in.readObject() );
        this.setX(in.readInt());
        this.setY(in.readInt());
        this.setVelocidad(in.readInt());
    }
    
    /**
     * Metodo de apoyo para serializar el tranvia
     * @param out ObjectStream encargado de convertir el tranvia
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
    }

}
