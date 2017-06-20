package com.innova4d.interf;

import java.rmi.RemoteException;

/**
 * Objectro abstracto para recuperar e iniziar la informacion comun de todos
 * los vehiculos, siendo la clase padre comun.
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

public abstract class Vehiculo {

    private String id;	
	private int x;
	private int y;
    private int v;
   

    /**
     *
     * @param id El id ligado a este vehiculo
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @param x La coordenada x donde se posiciona ahora el vehiculo
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /**
     *
     * @param y La coordenada y donde se posiciona ahora el vehiculo
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     *
     * @param v La velocidad con la cual se desplaza ahora el vehiculo en el plano x
     */
    public void setVelocidad(int v) {
        this.v = v;
    }

    /**
     * 
     * @return El id del vehiculo en String
     */
    public String getId() {
        return id;
    }
 
    /**
     *
     * @return La coordenada entera x del vehiculo
     */
	public int getX() {
		return x;
	}
    
    /**
     *
     * @return La coordenada entera y del vehiculo 
     */ 
	public int getY() {
		return y;
	}

    /**
     *
     * @return La velocidad con la cual se mueve el vehiculo en el plano x
     */
    public int getVelocidad() {
        return v;
    }

}
