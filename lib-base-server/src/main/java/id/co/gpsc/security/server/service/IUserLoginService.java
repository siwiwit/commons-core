/**
 * File Name : IUserLoginService.java
 * Package   : id.co.sigma.arium.security.server.service
 * Project   : security-server
 */
package id.co.gpsc.security.server.service;

import id.co.gpsc.common.security.LoginParameter;
import id.co.gpsc.common.security.LoginResultData;
import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.security.server.CurrentUserCurrentluUsedException;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

/**
 * Interface login user services
 * @author I Gede Mahendra
 * @since Nov 19, 2012, 11:57:10 AM
 * @version $Id
 */
public interface IUserLoginService {
	
	/**
	 * Proses login ke dalam aplikasi dg cara biasa. Masukin username dan password
	 * @param parameter
	 * @return LoginResultData
	 * @throws Exception
	 */
	public LoginResultData loginApplication(LoginParameter parameter) throws Exception;
	
	/**
	 * Proses login dg mode NTLM yaitu domain authentification dari Windows
	 * @param parameter - applicationId dan username domain-nya
	 * @return LoginResultData
	 * @throws Exception
	 */
	public LoginResultData loginApplicationViaNTLM(LoginParameter parameter) throws Exception;
	
	/**
	 * Get application berdasarkan applicationId yg didapat dari URL
	 * @param applicationId
	 * @return Application
	 * @throws Exception
	 */
	public Application getApplication(BigInteger applicationId) throws Exception;
	
	
	
	/**
	 * membaca data user dengan username
	 * @author <a href="gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 **/
	public User getUserByUserName (String userName ) ; 
	
	
	
	/**
	 * mencari di mana saja user
	 **/
	public List<Branch> getUserBranches (BigInteger userId) ; 
	
	
	
	/**
	 * get data sign on dnegan id sigon
	 **/
	public Signon getSigonData (BigInteger sigonId) ; 
	
	
	public Signon createSignOnDataAndKickPrevUser(BigInteger appId , BigInteger userId
			) throws CurrentUserCurrentluUsedException,  Exception;
	
	/**
	 * worker untuk notifikasi server(misal cams), kalau user : YYY sudah berhasil login. url server notifikasi di dapat dari table sec_application
	 **/
	public void notifyRequesterHostOnSuccessLogin ( Signon signonData );
	
	/**
	 * Generate URL Redirect ke aplikasi yg memanggil security ini
	 * @param applicationId
	 * @param errorCode
	 * @param uuid
	 * @return URL
	 * @throws Exception
	 */
	public String getUrlRedirectToApplication(Application application ,BigInteger applicationId, Integer errorCode, String uuid, String userName , HttpServletResponse response ) throws Exception ;
	
}