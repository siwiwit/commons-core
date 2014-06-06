/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package id.co.gpsc.common.server.rpc.impl;

import id.co.gpsc.common.data.ObjectSerializer;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;
import id.co.gpsc.common.data.serializer.JSONSerializerManager;
import id.co.gpsc.common.data.serializer.json.ListDataWrapperJSONFriendlyType;
import id.co.gpsc.common.data.serializer.json.ListDataWrapperPrimitiveType;
import id.co.gpsc.common.data.serializer.json.NonPrimitiveArrayDataWrapper;
import id.co.gpsc.common.data.serializer.json.ObjectSerializerManager;
import id.co.gpsc.common.data.serializer.json.PrimitiveArrayDataWrapper;
import id.co.gpsc.common.data.serializer.json.RPCResponseWrapper;
import id.co.gpsc.common.data.serializer.json.SimpleObjectWrapper;
import id.co.gpsc.common.exception.LoginExpiredException;
import id.co.gpsc.common.exception.SimpleJSONSerializableException;
import id.co.gpsc.common.server.dao.util.ServerSideParsedJSONContainer;
import id.co.gpsc.common.server.rpc.IRPCHandler;
import id.co.gpsc.common.server.rpc.IRPCHandlerManager;
import id.co.gpsc.common.server.rpc.IRPCHandlerMethodInvoker;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.JSONCharacterEscapeUtil;
import id.co.gpsc.common.util.json.ParsedJSONContainer;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.context.request.RequestContextHolder;

/**
 *
 * @author gps
 */
public class RPCHandlerManagerImpl implements  IRPCHandlerManager{

    
     
   
    
    static final ArrayList<String> PRIMITIVE_FQCN = new ArrayList<String>(); 
   
    @Autowired
    private SessionRegistry sessionRegistry ; 
    
    Map<String, IRPCHandlerMethodInvoker> invokerMap = new HashMap<String, IRPCHandlerMethodInvoker>(); 
    
    
    private static final Logger logger = LoggerFactory.getLogger(RPCHandlerManagerImpl.class);
    
    
    @Override
    public void registerHandler(final IRPCHandler handler) {
        Class<?> interfaceImplemented =  handler.implementedInterface(); 
        final String namaIface =  handler.implementedInterface().getName(); 
        Method[] methods =  interfaceImplemented.getMethods() ; 
        Logger logger =  LoggerFactory.getLogger(RPCHandlerManagerImpl.class);
        String message = ">>> " + handler.getClass().getName() + " reporting for duty";  
        logger.debug(message) ;
        System.out.println(message);
        if ( methods != null && methods.length>0){
            for (final  Method scn : methods ){
            	
            	
            	String keyBase = handler.implementedInterface().getName() +"." + scn.getName();
                
               StringBuilder key = new StringBuilder( keyBase) ; 
               final Class<?>[] paramTypes =  scn.getParameterTypes(); 
               if ( paramTypes!= null && paramTypes.length>0){
                   for (Class<?> prm : paramTypes ){
                       key.append(prm.getName()); 
                               
                   }
               }
               
               
               
             invokerMap.put(key.toString(),  
                        
                new IRPCHandlerMethodInvoker() {

                   @Override
                   public Object invokeMethod(HttpServletRequest request,   Object[] serialzedArguments) throws Exception {
                       try {
                    	   
                    	   
                    	  Method m =   BeanUtils.findDeclaredMethod(handler.getClass(), scn.getName(), paramTypes) ;
                    	  if ( m== null){
                    		  Logger log =  LoggerFactory.getLogger(RPCHandlerManagerImpl.class);
                    		  StringBuffer buff = new StringBuffer(); 
                    		  buff.append("method : " + handler.getClass() + "#"  +  scn.getName()); 
                    		  buff.append("param signature : "   );
                    		  for ( Class<?> cls : paramTypes ){
                    			  buff.append(cls.getName());
                    		  }
                    		  log.error( buff.toString());
                    		  
                    		  
                    	  }
                           return m.invoke(handler, serialzedArguments);
                           
                           
                       } catch (Exception ex) {
                    	    
                    	   LoggerFactory.getLogger(RPCHandlerManagerImpl.class).error("gagal invoke method : " +  scn.getName() + ", error message : " + ex.getMessage());
                    	   throw ex ;
                       }  
                        
                   }

                   
                });
            }
        }
    }

    @Override
    public RPCResponseWrapper invokeRPCRequest(HttpServletRequest request ) throws LoginExpiredException ,  Exception {
    	Object swap =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if ( swap == null|| swap instanceof String || swap instanceof org.springframework.security.authentication.AnonymousAuthenticationToken){// kalau kosong mencurigakan dia expired
    		throw new LoginExpiredException(); 
    	}
    	
    	
    	SessionInformation inf =  sessionRegistry.getSessionInformation(RequestContextHolder.currentRequestAttributes().getSessionId()); 
		if ( inf== null){
			throw new LoginExpiredException(); 
		}
    	
        String methodIndexer = request.getParameter("METHOD_TO_INVOKE"); 
        String paramTypeFQCNArray = request.getParameter("PARAM_FQCN");
        Object [] params = null ; 
        if ( paramTypeFQCNArray!= null &&  paramTypeFQCNArray.length()>0){
            String[] fqcnSplited = paramTypeFQCNArray.split("\\-");
            params = new Object[fqcnSplited.length];
            for ( int i = 0 ; i < fqcnSplited.length;i++){
                String paramFQCN = fqcnSplited[i]  ; 
                String rawArg = request.getParameter("RPC_PARAM_" + i);
                rawArg = JSONCharacterEscapeUtil.getInstance().convertToUrlDecodedString(rawArg);
                params[i ] = deserializer(rawArg, paramFQCN); 
                methodIndexer+=paramFQCN;
            }
        }
        
        
        
        
        RPCResponseWrapper w = null ; 
        try{
        	
        	IRPCHandlerMethodInvoker invoker  =  invokerMap.get(methodIndexer); 
        	if ( invoker== null){
        		logger.error("tidak ada handler untuk interface : " +methodIndexer + "param : " + paramTypeFQCNArray +",anda perlu mengecek definisi async anda");
        	}
            Object result = invoker.invokeMethod(request,  params);
       
            w = new RPCResponseWrapper(JSONSerializerManager.getInstance().transalateToFriendlyObject(result));
            
        }catch ( Exception exc){
        	Exception actualExc = exc ; 
            if (exc instanceof InvocationTargetException){
            	// kalau java.lang.reflect.InvocationTargetException exception di bungkus, di unpact saja. cari pattern lain
            	Throwable  th  = ((InvocationTargetException)exc).getTargetException() ;
            	if ( th!= null && th instanceof Exception )
            		actualExc = (Exception)th ;
            }else{
            	
            }
        	
        	
            w = new RPCResponseWrapper( tanslateException(actualExc)); 
            LoggerFactory.getLogger(RPCHandlerManagerImpl.class).error("gagal invoke RPC : " + methodIndexer + ", error di laporkan : " + exc.getMessage() , exc );
            //FIXME: masukan error handling di sini
        }
        
        
        return w ; 
        
        
        
        
        
        
    }
    
    
    
    protected SimpleJSONSerializableException tanslateException (Exception exception ){
        //FIXME: perlu  error custom
        return new SimpleJSONSerializableException(exception);
    }
    
    
    
    
    
    /**
     * deserialize dari raw string data menjadi object
     **/
    protected Object deserializer (String rawData , String classFQCN    ) {
        if ( rawData == null || rawData.isEmpty())
            return null ; 
        if ( ObjectSerializerManager.isSimpleObject(classFQCN)){
    		
        	SimpleObjectWrapper w = new SimpleObjectWrapper(); 
        	 try {
        		 ParsedJSONContainer parser = SharedServerClientLogicManager.getInstance().getJSONParser().parseJSON(rawData);
				SimpleObjectWrapper smpWrapper =  w.instantiateFromJSON(parser) ; 
				return smpWrapper.getActualData();
			} catch (Exception e) {
				
				e.printStackTrace();
				return null; 
			}
        	
        	
        	
    	}
        if ( ObjectSerializerManager.getInstance().isHaveSerializer(classFQCN))
            return  ObjectSerializerManager.getInstance().getSerializer(classFQCN).deserialize(rawData); 
        
        try{
        	
        	
        	
        	
        	if ( java.util.List.class.isAssignableFrom(Class.forName(classFQCN))){
        		return javaUtilListSubClassSerializer(rawData);
        	}
        	else if ( classFQCN.startsWith("[L")){
        		ObjectSerializer<?> s = arrayOfObjectSerializer(classFQCN); 
        		if ( s!= null){
        			ObjectSerializerManager.getInstance().registerSerializer(s);
        			return s.deserialize(rawData);
        		} 
        	}else{
        		return simpleObjectSerializer(rawData , classFQCN);
        	}
          
        }catch ( Exception exc){
        	LoggerFactory.getLogger(RPCHandlerManagerImpl.class).error("gagal mengkonversi class : " + classFQCN  + ", error message : " + exc.getMessage() ,exc) ;
            return null; 
        }
        return null ;
         
    }
    
    
    
    
    
    
    
    
    /**
     * construct list type
     **/
    protected Object javaUtilListSubClassSerializer (String rawData) {
    	ServerSideParsedJSONContainer p = new ServerSideParsedJSONContainer(rawData);
		String actualFQCN = p.getAsString(IJSONFriendlyObject.FQCN_MARKER_KEY);
		if (ObjectSerializerManager.isSimpleObject(actualFQCN)){
			ListDataWrapperPrimitiveType l = new ListDataWrapperPrimitiveType();
			
			ListDataWrapperPrimitiveType act =  l.instantiateFromJSON(p);
			return act.getActualData(); 
		}else{
			ListDataWrapperJSONFriendlyType l = new ListDataWrapperJSONFriendlyType(); 
			ListDataWrapperJSONFriendlyType act =  l.instantiateFromJSON(p);
			return act.getActualData();
		}
    	
    }
    
    
   
    
    
    
    
    
    /**
     * serializer object array
     **/
    private ObjectSerializer arrayOfObjectSerializer ( final  String classFQCN) {
    	String fqcnNoArrayMarker = classFQCN.substring(2 , classFQCN.length()-1) ;
    	
    	if (ObjectSerializerManager.isSimpleObject(fqcnNoArrayMarker)){
    		
    		final ObjectSerializer<Object[]> s = new ObjectSerializer<Object[]>() {
    			
    			private final String[] CLS ={classFQCN};
    			@Override
    			public String[] acceptedClassFQCNS() {
    				return CLS;
    			}
    			@Override
    			public Object[] deserialize(String stringRepresentation) {
    				final ServerSideParsedJSONContainer c = new ServerSideParsedJSONContainer(stringRepresentation);
    	    		final PrimitiveArrayDataWrapper p = new PrimitiveArrayDataWrapper(c ); 
    	    		return p.getActualData();
    			}
				@Override
				public String serialize(Object[] object) {
					final ServerSideParsedJSONContainer c = new ServerSideParsedJSONContainer( );
    	    		final PrimitiveArrayDataWrapper p = new PrimitiveArrayDataWrapper( object );
    	    		p.translateToJSON(c);
					return c.getJSONString();
				}
			};
			
    		return s  ; 
    		
    	}else{
    		final ObjectSerializer<IJSONFriendlyObject<?>[]> s = new ObjectSerializer<IJSONFriendlyObject<?>[]>() {

    			
    			private final String[] CLS ={classFQCN} ; 
				@Override
				public String serialize(IJSONFriendlyObject<?>[] object) {
		    		NonPrimitiveArrayDataWrapper<?> sample = new NonPrimitiveArrayDataWrapper(object); 
		    		ServerSideParsedJSONContainer c = new ServerSideParsedJSONContainer(); 
		    		sample.translateToJSON(c); 
		    		return c.getJSONString(); 
				}

				@Override
				public IJSONFriendlyObject<?>[] deserialize(String stringRepresentation) {
					ServerSideParsedJSONContainer c = new ServerSideParsedJSONContainer(stringRepresentation);
		    		NonPrimitiveArrayDataWrapper<?> sample = new NonPrimitiveArrayDataWrapper(); 
		    		return sample.instantiateFromJSON(c).getActualData();
				}

				@Override
				public String[] acceptedClassFQCNS() {
					return CLS;
				}
    			
    			
    			
    		};
    		
    		return s ;
    		
    	}
    	
    }
    
    
    
    public static void main (String[] arg){
    	String fqcn ="L[java.lang.String;";
    	System.out.println(fqcn.substring(2 , fqcn.length()-1));
    	//DualControlEnabledOperation.class.getEnumConstants()
    	String json ="{\"internalCode\":\"insert\"}";
    	ServerSideParsedJSONContainer p = new ServerSideParsedJSONContainer(json);
    	DualControlEnabledOperation rslt =  DualControlEnabledOperation.DELETE.instantiateFromJSON(p);
    	
    	
    	Object swap;
		try {
			swap = Class.forName( DualControlEnabledOperation.class.getName()).getEnumConstants()[0];
			if ( swap instanceof IJSONFriendlyObject){
	    		System.out.println("hore, yes , yes");
	    	}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} 
    	
    	System.err.println(rslt.toString());
    	
    	
    }
    
    
    
    
    
    
    /**
     * serializer simple object
     **/
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Object simpleObjectSerializer (String rawData , String classFQCN) throws Exception{
    	
    	
    	
		Class cls = Class.forName(classFQCN) ;
    	Object tmp = null  ; 
    	if ( cls.isEnum()){
    		Object allEnumVals[] =  cls.getEnumConstants();
    		if ( allEnumVals== null)
    			return null ;
    		tmp = allEnumVals[0];// ambil saja index 1 karena kita cuma perlu sample, bukan real value
    		
    	}
    	else{
    		tmp=  BeanUtils.instantiate(  cls);
    	}
    	
    	final Object sample  = tmp; 
    	
          
         if ( sample instanceof IJSONFriendlyObject){
              final String[] FQCNS ={
                  sample.getClass().getName()
              };  
              
              final IJSONFriendlyObject convertedObj = (IJSONFriendlyObject)sample ; 
              ObjectSerializerManager.getInstance().registerSerializer(new ObjectSerializer<IJSONFriendlyObject>(){

                  @Override
                  public String[] acceptedClassFQCNS() {
                      return FQCNS ; 
                  }

                  @Override
                  public IJSONFriendlyObject deserialize(String stringRepresentation) {
                      return (IJSONFriendlyObject) convertedObj.instantiateFromJSON(new ServerSideParsedJSONContainer(stringRepresentation)); 
                  }

                  @Override
                  public String serialize(IJSONFriendlyObject object) {
                      ServerSideParsedJSONContainer c = new ServerSideParsedJSONContainer(); 
                      object.translateToJSON(c);
                      return c.getJSONString(); 
                  }
                  
                  
                  
              });
              return  ObjectSerializerManager.getInstance().getSerializer(classFQCN).deserialize(rawData); 
          }
          return null ; 
    }
   
    
}
