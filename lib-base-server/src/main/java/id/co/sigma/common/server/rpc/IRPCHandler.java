/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.sigma.common.server.rpc;

/**
 *
 * @author gps
 */
public interface IRPCHandler<T> {
    
    
    /**
     * interface yang di handle oleh servlet ini
     */
    public Class<T> implementedInterface () ; 
}
