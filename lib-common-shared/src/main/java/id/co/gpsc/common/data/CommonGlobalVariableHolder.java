package id.co.gpsc.common.data;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;



/**
 * holder variable global
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 **/
public final class CommonGlobalVariableHolder {
	
	
	private static CommonGlobalVariableHolder instance ; 
	
	
	
	
	
	private CommonGlobalVariableHolder(){
		
	}
	
	
	/**
	 * singleton instance
	 **/
	public static CommonGlobalVariableHolder getInstance() {
		if(instance==null)
			instance = new CommonGlobalVariableHolder();
		return instance;
	}
	


	
	/**
	 * ganti url dari ServiceDefTarget. kalau di create dengan GWT.create url target akan relative terhadap posisi modul. ini perlu di koreksi agar request menghasilkan request yang valid
	 **/
	public void fixTargetUrl  (ServiceDefTarget serviceTarget){
		String modulBasePath =  GWT.getModuleBaseURL() ;
		String tail =  serviceTarget.getServiceEntryPoint().substring(modulBasePath.length());
		if ( tail.startsWith("/"))
			tail = tail.substring(1);
		serviceTarget.setServiceEntryPoint(GWT.getHostPageBaseURL() + tail);
	}
	
	
	
	/**
	 * URL base app
	 **/
	public String getBaseURL (){
		
		 return GWT.getHostPageBaseURL() ; 
	}
	

}
