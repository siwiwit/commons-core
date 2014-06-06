
package id.co.gpsc.common.server.controllers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import id.co.gpsc.common.data.serializer.json.RPCResponseWrapper;
import id.co.gpsc.common.exception.BaseIsSerializableException;
import id.co.gpsc.common.exception.IStackTraceToStringWorker;
import id.co.gpsc.common.exception.SimpleJSONSerializableException;
import id.co.gpsc.common.server.dao.util.ServerSideDateTimeParser;
import id.co.gpsc.common.server.dao.util.ServerSideParsedJSONContainer;
import id.co.gpsc.common.server.dao.util.ServerSideWrappedJSONParser;
import id.co.gpsc.common.server.rpc.IRPCHandlerManager;
import id.co.gpsc.common.server.util.ExceptionRelatedUtils;
import id.co.gpsc.common.server.util.ServerSideSimpleDebugWriterManager;
import id.co.gpsc.common.util.IObjectGenerator;
import id.co.gpsc.common.util.ObjectGeneratorManager;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ini web servlet untuk handler request dengan param json. data di pass dengan plain string, response juga di kirim dengan plain string
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
@Controller
public class ManualJSONSerializedRPCServlet implements InitializingBean{

	
	
	
	@Autowired
	private IRPCHandlerManager handlerManager ; 
     
	public static ArrayList< Class<? extends Throwable>>   UN_LOGGED_CLASSES = new ArrayList<Class<? extends Throwable>>() ;  
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManualJSONSerializedRPCServlet.class); 
	/**
	 * map yang shelf populate
	 **/
	Map<String, IObjectGenerator> indexer = new HashMap<String, IObjectGenerator>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 6430860024042089180L;
		
		
		
		
		public boolean containsKey(Object key ){
			
			if ( super.containsKey(key))
				return true ; 
			IObjectGenerator s = get(key); 
			return s!= null ; 
		}
		
		public IObjectGenerator get(  Object key) {
			
			
			
			
			
			
			if ( !super.containsKey(key)){
				final IObjectGenerator gen = new IObjectGenerator() {
					
					 
					@SuppressWarnings("unchecked")
					@Override
					public <T> T instantiateSampleObject(String objectFQCN) {
						try {
							
							
							
							Class<?> cls = Class.forName(objectFQCN);  
							if (  cls.isEnum()) {
								 return (T) cls.getEnumConstants()[0]; 
							}
							else
								return (T) BeanUtils.instantiate(cls);
						} catch (Exception e) {
							LoggerFactory.getLogger(ManualJSONSerializedRPCServlet.class).error("gagal create class>>" + objectFQCN +", error : " + e.getMessage() , e);
							e.printStackTrace();
							return null;
						}
					}
					@SuppressWarnings("unchecked")
					@Override
					public <T> T[] instantiateArray(String objectFQCN, int size) {
						 
						try {
							return (T[]) Array.newInstance(Class.forName(objectFQCN), size);
						}  catch (Exception e) {
							LoggerFactory.getLogger(ManualJSONSerializedRPCServlet.class).error("gagal create array of  class>>" + objectFQCN +", error : " + e.getMessage() , e);
							e.printStackTrace();
							return null;
						}  
					}
				};
				
				put((String)key, gen); 
				
			}
			return super.get(key); 
			
		};
	};
	
	
	@RequestMapping(
			method={RequestMethod.POST , 
			RequestMethod.GET} , 
			value={"/{version}/{lang}/communication-link/json-servlet.json-rpc"} )
	public @ResponseBody String handleRPCRequest ( @PathVariable(value="version") String version , 
			 @PathVariable(value="lang")String lang ,  HttpServletRequest request ) {
		return handleRPCRequest(request);
		
	}
	
	/**
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 *  
	 **/
	@RequestMapping(
				method={RequestMethod.POST , 
				RequestMethod.GET} , 
				value={"/communication-link/json-servlet.json-rpc"} )
	public @ResponseBody String handleRPCRequest (HttpServletRequest request ) {
		if (! ObjectGeneratorManager.getInstance().isIndexerMapReplaced()){
			indexer.putAll(ObjectGeneratorManager.getInstance().getIndexedgenerators());
			ObjectGeneratorManager.getInstance().setIndexedgenerators(indexer); 
		}
		SharedServerClientLogicManager.getInstance().setJSONParser(  ServerSideWrappedJSONParser.getInstance());
		SharedServerClientLogicManager.getInstance().setDateTimeParser(ServerSideDateTimeParser.getInstance());
		SharedServerClientLogicManager.getInstance().setDebugerWriterManager(new ServerSideSimpleDebugWriterManager());
		RPCResponseWrapper w;
		try {
			w = handlerManager.invokeRPCRequest(request );
		} catch (Exception e) {
			w= new RPCResponseWrapper( new SimpleJSONSerializableException(e));
			boolean doLog = true ; 
			for (  Class<? extends Throwable > cls : UN_LOGGED_CLASSES ){
				if ( e.getClass().isAssignableFrom(cls)){
					doLog = false ; 
					break ; 
				}
			}
			if ( doLog){
				StringBuffer arg = new StringBuffer(); 
				Map<String, String[]> mp = request.getParameterMap() ; 
				for ( String key : mp.keySet()){
					arg.append(key); 
					arg.append(":");
					String[] mps = mp.get(key); 
					if ( mps!= null){
						for ( String valParam : mps){
							arg.append(valParam ); 
							arg.append(","); 
						}
					}
					arg.append("\n");		
							
				}
				LOGGER.error("gagal eksekusi rpc dengan argument : {} , error :  {}" , new Object[]{
						arg , e.getMessage() 
						
				}  ) ;
			}
			
		} 
		ServerSideParsedJSONContainer c = new ServerSideParsedJSONContainer(); 
        if ( w!= null){
            
           w.translateToJSON(c);
           String json =  c.getJSONString() ;
           return json ;      
            
        }
        
		return "{\"data\":null}" ; 
	}






	@Override
	public void afterPropertiesSet() throws Exception {
		BaseIsSerializableException.STACKTRACE_TO_STRING_WORKER = new IStackTraceToStringWorker() {
			
			@Override
			public String extractStackTraceToString(Throwable exception) {
				return ExceptionRelatedUtils.getInstance().getStackTraceAsString(exception);
			}
		}; 
		
	}
	
	
	
	public static void main ( String [] args){
		String pattern = "alpha{0} omega {1}"; 
		System.out.println(  pattern.replaceAll("\\{0\\}", "variable1").replaceAll("\\{1\\}", "variable2"));
		
		
	}

    
    
    
    
    
    
    
    
}
