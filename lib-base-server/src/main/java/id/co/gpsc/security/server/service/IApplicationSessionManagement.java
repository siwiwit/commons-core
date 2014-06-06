package id.co.gpsc.security.server.service;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.ApplicationSessionRegistry;

/**
 * 
 * service untuk handler session management
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IApplicationSessionManagement {
	
	
	
	/**
	 * membaca data user yang sedang log in
	 * @param usernameWildCard username wild card
	 * @param pageSize ukuran page per pembacaan
	 * @param page page berapa yang hendak di baca
	 *  
	 */
	public PagedResultHolder<ApplicationSessionRegistry> getCurrentlyLogedInUser (String usernameWildCard ,String realNameWildCard , String email ,  int pageSize , int page ); 
	
	 

}
