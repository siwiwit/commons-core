/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.data.serializer;

import id.co.gpsc.common.data.ObjectSerializer;

import java.math.BigInteger;

/**
 *
 * @author gps
 */
public class VoidSerializer implements ObjectSerializer<Void>{

    @Override
    public String serialize(Void object) {
        return "-[VOID]-"; 
    }

    @Override
    public Void deserialize(String stringRepresentation) {
        return null ; 
    }

    private static final  String[] FQCN =  {
        Void.class.getName() 
    }; 
    @Override
    public String[] acceptedClassFQCNS() {
        return FQCN; 
    }
    
}
