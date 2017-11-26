/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion.interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Adrián Bustamante Zarate
 */
public interface IVerificacionConexion extends Remote{
    public boolean verficarConexion(boolean banderaSeñal) throws RemoteException;
}
