package com.innova4d.client;

import java.net.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.innova4d.interf.Factory;
import com.innova4d.interf.Vehiculo;
import com.innova4d.interf.Constant;
import com.innova4d.interf.RemoteInterface;

/**
 * ClientLauncher es el encargado de correr el cliente, conectarse con el servidor, e
 * imprimir el resultado.
 *
 * Los datos de conexion son heredados de la inteface Constant.
 * Ademas, la clase solo sabe manejar la clase Vehiculo, y solo
 * tiene acceso a la interface RemoteInterface.*
 *
 * @author <a href="mailto:mauricio.gracianoaz@udlap.mx">Mauricio Graciano - 149605</a>
 * @author <a href="mailto:alan.perezco@udlap.mx">Alan Perez - 150294</a>
 * @author <a href="mailto:daniel.torrez @udlap.mx">Daniel Alberto - 146995</a>
 * @version 1.7
 * @since February 2017
 */

public class ClientLauncher implements Constant {

    /**
     *
     * @param args parametros de inicializacion del programa
     * @throws RemoteException Si ocurre algun problema con la conexion
     * @throws NotBoundException Si no esta ligada la interface al id 
     */  	
	public static void main(String[] args) throws RemoteException, NotBoundException {
        // Busca el servidor en en la dirrecion definida por Constant.RMI_ADDRESS,
        // en puerto el puerto defindo por Constant.RMI_PORT.
		Registry registry = LocateRegistry.getRegistry(RMI_ADDRESS, RMI_PORT);
		
        // Y recupera la RemoteInterface definida por el ID en Constant.RMI_ID.
        final RemoteInterface remote = (RemoteInterface) registry.lookup(RMI_ID);
	        
        // Iniciar el cliente de la interfaz gráfica.
		ClientLauncher.guiClient(remote);
       
        // Inicia todos los vehiculos
        ClientLauncher.vehiculosClient(remote);
 
    }

    /**
     * Metodo encargado de recibir los vehiculos y llevarlos a traves de la pista.
     * En esta implementación, el servidor es el encargado de crear y almacenar
     * los vehiculos, mientras que el cliente solo manda la solicutd para moverlos.
     *
     * @param remote Interfaze ligada al servidor
     * @throws RemoteException Si occure algun problema con la conexion
     */
    private static void vehiculosClient(final RemoteInterface remote) throws RemoteException 
    {	
        
        // Recorre los vehiculos:
        for (int i = 0; i < ANCHO; i++) { 

            // Creo los vehiculos concretos.
            //final Vehiculo v = Factory.getVehiculo(i);
            
            final Vehiculo v = remote.createVehiculo(i);
            
            // Si el vehiculo existe, enlazate con la torre de control:
            if (v != null) {
                // Primero, se agrega el vehiculo a la torre de control,
                //remote.checkInVehiculo(v);
                
                // Y despues, muevelo a traves de la pista en su carril (x)
                // a una velocidad constante (velocidad).
                ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		        exec.scheduleAtFixedRate(new Runnable() {
		            @Override
		            public void run() {
			            try {
				            //remote.moverVehiculo(remote.getVehiculo(v.getId(), v.getX()), v.getX());
				            remote.moverVehiculo(v, v.getX());
			            } catch (Exception e) {
				            e.printStackTrace();
                            System.out.println("\n\nTerminating Program\n");
                            System.exit(1);
			            }
		            }
		        }, v.getVelocidad(), v.getVelocidad(), TimeUnit.SECONDS);
            }         
        }

	}

    /**
	 * Metodo encargado de imprimir la GUI de las pistas.
     *
     * @param remote Interfaza ligada al servidor
	 * @throws RemoteException Si occure algun problema con la conexion
	 */
	private static void guiClient (final RemoteInterface remote ) throws RemoteException
    {	
        //Obtiene la pista y se imprime cada segundo.
		ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleAtFixedRate(new Runnable() {
		  @Override
		  public void run() {
			try {
				System.out.print(ClientLauncher.printPista(remote.getMapaPistas()));
			} catch (RemoteException e) {
				e.printStackTrace();
                System.out.println("\n\nTerminating Program\n");
                System.exit(1);
			}
		  }
		}, 0, 1, TimeUnit.SECONDS);
	}
	
    /**
	 * Imprime en consola el espacio aéreo desde la
	 * torre de control (Servidor) en el Cliente.
	 * Método que implementa guiClient()
	 * @param vs Matriz que contiene el espacio aéreo a imprimir.
	 * @throws RemoteException Si occure algun problema con la conexion
	 */
	private static String printPista(Vehiculo[][] vs) throws RemoteException {
		String output = "\r=== Pista ===\n";
        String name;
        
        // Recorre las filas, de arriba para abajo.
		for (int i = 0; i < ANCHO; i++) {
            
            // Recorre una fila invidual de izquierda a derecha.
		    for (int j = 0; j < ALTURA; j++) {
		        // Inicializa el nombre.
                name = " ";
            
                // Si el vehiculo existe, recupera el id como nombre.
                if (vs[i][j] != null)
                    name = "" + vs[i][j].getId().charAt(0);        
                
                // Imprime el contenido de cada celda.
                output += "[" + name + "]";
            }
            
            // Escoje el tipo de vehiculo que recorre un carril e imprimelo.
		    output = "\r" + output + Factory.getName(i) + "\n";            		    
		}

        //Regresa el resultado 
		return "\r" + output + "\n";
	}

}
