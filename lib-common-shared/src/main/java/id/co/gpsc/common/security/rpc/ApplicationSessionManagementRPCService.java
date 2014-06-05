package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.ApplicationSessionRegistry;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ApplicationSessionManagementRPCService extends JSONSerializedRemoteService{
	
	/**
	 * membaca data user yang sedang log in
	 * @param usernameWildCard username wild card
	 * @param pageSize ukuran page per pembacaan
	 * @param page page berapa yang hendak di baca
	 *  
	 */
	public PagedResultHolder<ApplicationSessionRegistry> getCurrentlyLogedInUser (String usernameWildCard ,String realNameWildCard , String email ,  int pageSize , int page );
	
	
	/**
	 * paksa session di loggof
	 * @param sessionId id dari session yang di paksa logoff
	 */
	public void forceLogoff( String sessionId ) ; 

}
