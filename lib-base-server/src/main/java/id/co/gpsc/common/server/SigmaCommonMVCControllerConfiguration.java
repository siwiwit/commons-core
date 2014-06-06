package id.co.gpsc.common.server;

import id.co.gpsc.common.server.service.PageConfigurationJSONProviderContoller;
import id.co.gpsc.common.server.service.WebMVCAnnotationActivator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;




/**
 * configurator bean untuk load Page controller(berbasis spring MVC)
 **/
@Configuration
public class SigmaCommonMVCControllerConfiguration {
	/**
	 * ini maksa spring MVC Up
	 **/
	@Lazy(value=false)
	@Bean
	public WebMVCAnnotationActivator getWebMVCAnnotationActivator(){
		return new WebMVCAnnotationActivator();
	}

	
	@Bean
	@Lazy(value=false)
	public PageConfigurationJSONProviderContoller getPageConfigurationJSONProviderContoller(){
		return new PageConfigurationJSONProviderContoller();
	}
	
	

}
