package id.co.gpsc.common.server.rpc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

/**
 * rpc url mapper Sigma
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 5 Aug 2012
 **/
//@Service(value="urlMapping")
//@Lazy(value=false)
public class SigmaRPCServiceUrlMapper extends SimpleUrlHandlerMapping implements SelfRegisterRPCUrlMapper , ServletConfigAware{
	Map<String, RPCServletWrapperController> urlHandlerMap ; 
	
	
	
	protected ServletConfig servletConfig;
	
	protected int automaticServletNameCounter =1 ; 
	
	public SigmaRPCServiceUrlMapper(){
		setOrder(1); 
		urlHandlerMap= new HashMap<String, RPCServletWrapperController>();
		
	}
	
	
	@Override
	public void registerRPCService(RPCServletWrapperController wrappedRPCservlet) { 
		//registerHandler(wrappedRPCservlet.getRemoteServicePath(), wrappedRPCservlet);  
		//getServletContext().addServlet("sigma-rpc-servlet" + automaticServletNameCounter,(Servlet) wrappedRPCservlet).addMapping(wrappedRPCservlet.getRemoteServicePath());
		automaticServletNameCounter++;
	}
		
		


	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig; 
		if ( servletConfig==null){
			System.out.println("servlet config null @" + this.getClass().getName());
		}
		else{
			System.out.println("not null @" + this.getClass().getName());
		}
		
	}
	
}
