package com.innova4d.interf;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface que indica los metodos que por contrato debe existir
 * entre el client y el servidor.
 * 
 * El servidor implementa los metodos, y se encarga del funcionamiento.
 * En cambio, el cliente solo tiene acceso a esta interface,
 * y es todo lo que sabe.
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

public interface RemoteInterface extends Remote {
    
    /**
      * Regresa el mapa de las pistas.
      *
      * @return Mapa de la pista, con los vehiculos que alli se ecuentren.
      * @throws RemoteException
      */
	public Vehiculo[][] getMapaPistas() throws RemoteException;    

    /**
     * Mueve un Vehiculo en la matriz, un bloque a la vez, de izquierda a derecha.
     * 
     * @param v El vehiculo a mover
     * @param c El numero de carril donde se encuentra
     * @return Si el vehiculo pudo moverse
     * @throws RemoteException
     */
    public Boolean moverVehiculo(Vehiculo v, int c) throws RemoteException; 
   
    /**
     * Crea el vehuclo corresondiente al carril indicado,
     * y lo regresa.
     * 
     * @param c Carril donde debe crearse el vehiculo
     * @return Referencia al vehiculo creado
     * @throws RemoteException
     */ 
    public Vehiculo createVehiculo(int c) throws RemoteException;

    /**
     * Agrega el vehiculo al carril seleccionado de la pista.
     *
     * @param v Vehiculo que se debe agregar a la pista
     * @return Verdadero si se pudo agregar, o en su defecto, falso
     * @throws RemoteException
     */
    @Deprecated
    public Boolean checkInVehiculo(Vehiculo v) throws RemoteException;
    
    /**
     * Se recupera el vehiculo que se encuentre en el carril dado,
     * y que tenga el nombre adecuado. 
     *
     * @param id Nombre vinculado al vehiculo
     * @param c Carril donde se encuentra el vehiculo
     * @return El vehiculo encontrado; nota, que si no se encontro, sera un valor nulo
     * @throws RemoteException
     */
    @Deprecated
    public Vehiculo getVehiculo(String id, int c) throws RemoteException;
	
}
