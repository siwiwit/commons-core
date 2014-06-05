/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.sigma.common.server.rpc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * interface untuk invoke method handler actual dari RPC
 * @author gps
 */
public interface IRPCHandlerMethodInvoker {
    
    
    public Object invokeMethod(   HttpServletRequest request ,    Object[] serialzedArguments ) throws Exception; 
    
}
