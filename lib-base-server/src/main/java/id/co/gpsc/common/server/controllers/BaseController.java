package id.co.gpsc.common.server.controllers;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

/**
 * base class controller spring MVC, ini untuk share common thing
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class BaseController {
	
	
	
	/**
	 * base app url. 
	 **/
	public static final String BASE_APP_URL_KEY ="base";
	
	
	
	
	
	/**
	 * base url dari app. ini untuk memasang absolut url resource
	 **/
	protected String getBaseAppUrl ( HttpServletRequest request) {
		
		
		String baseUrl = request.getContextPath(); 
		if ( baseUrl.endsWith("/"))
			baseUrl =  baseUrl.substring(0, baseUrl.length()-1);
		return baseUrl ; 
	}
	
	
	
	public static void main( String[] arg){
		String tst = "aaa/bb/ccc/"; 
		System.out.println(tst.substring(0, tst.length()-1));
	}
	
	
	/**
	 * menaruh model and view dalam model , key = {@link #BASE_APP_URL_KEY}
	 **/
	protected void putBaseUrl ( ModelAndView modelAndView ,  HttpServletRequest request){
		modelAndView.addObject(BASE_APP_URL_KEY, getBaseAppUrl(request));
	}
	
	

}
