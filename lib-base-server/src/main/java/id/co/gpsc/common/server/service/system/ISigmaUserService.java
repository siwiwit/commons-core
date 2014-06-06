package id.co.gpsc.common.server.service.system;

import id.co.gpsc.common.server.data.security.UserLoginNotificationData;



/**
 * interface user akses
 **/
public interface ISigmaUserService {
	
	
	
	/**
	 * method ini untuk menerima dari security apps, notifikasi user berhasil authenticate
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 * @param userName username authenticated
	 * @param uuid uui dari user
	 * @param clientHost hostname dari user
	 * @param fullnName fullname dari user
	 * @param email email dari user
	 *  
	 **/
	public void pushRegisteredUser (UserLoginNotificationData notificationData ) ; 

}
