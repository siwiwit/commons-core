package id.co.sigma.common.server.rpc.impl;

import id.co.sigma.common.server.rpc.IRPCHandler;
import id.co.sigma.common.server.rpc.IRPCHandlerManager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class BaseRPCHandler<T> implements IRPCHandler<T>,  InitializingBean , ApplicationContextAware{

	@Override
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.applicationContext = ctx ; 
		
	}
	
	
	
	private  ApplicationContext applicationContext ;  
	//@Autowired
	private IRPCHandlerManager handlerManager ;
	
	
	public BaseRPCHandler(){
		
		
		System.out.println("-----------------------------------------------------");
		System.out.println(this.getClass().getName() +" di init"); 
		System.out.println("-----------------------------------------------------");
		
		
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		handlerManager = (IRPCHandlerManager) applicationContext.getBean("rpc-handler-manager");
		
		
		handlerManager.registerHandler(this);
		
		System.out.println("-----------------------------------------------------");
		System.out.println(this.getClass().getName() +" di register ke :" + handlerManager); 
		System.out.println("-----------------------------------------------------");
		
		
	}
	
	
	
	public void setHandlerManager(IRPCHandlerManager handlerManager) {
		this.handlerManager = handlerManager;
	}
	
	public IRPCHandlerManager getHandlerManager() {
		return handlerManager;
	}
	
	
}
