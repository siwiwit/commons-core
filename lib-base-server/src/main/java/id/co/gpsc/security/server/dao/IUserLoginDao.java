/**
 * File Name : IUserLoginDao.java
 * Package   : id.co.gpsc.arium.security.server.dao
 * Project   : security-server
 */
package id.co.gpsc.security.server.dao;

import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.domain.UserPassword;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface Dao untuk proses login
 * @author I Gede Mahendra
 * @since Nov 19, 2012, 11:54:40 AM
 * @version $Id
 */
public interface IUserLoginDao {

	/**
	 * Get user berdasarkan username
	 * @param username - username yg didpt dr form login
	 * @return object User
	 * @throws Exception
	 */
	public User getUserByUsername(String username) throws Exception;
	
	/**
	 * Get list data signOn berdasarkan user id
	 * @param userId - userId yg didapat dr return method getUserByUsername(String username)
	 * @return list of signon
	 * @throws Exception
	 */
	public List<Signon> getSignOnByUserId(BigInteger userId) throws Exception;
	
	/**
	 * Get application data berdasarkan applicationId
	 * @param applicationId - applicationId berdasarkan URL
	 * @return application
	 * @throws Exception
	 */
	public Application getApplicationData(BigInteger applicationId) throws Exception;
	
	/**
	 * check user password di tabel password history
	 * @param userId
	 * @return object userPassword
	 * @throws Exception
	 */
	public UserPassword getPasswordAtHistory(BigInteger userId) throws Exception;
	
	
	
	/**
	 * membaca data user signon by id
	 **/
	public Signon getSigonData(BigInteger sigonId);
	
	/**
	 * Get data signon berdasarkan uuid yg didapat dari jsessionid spring security
	 * @param uuid
	 * @return Signon
	 */
	public Signon getSignonData(String uuid) throws Exception;
	
	/**
	 * Get data signon terakhir berdasarkan userid
	 * @param userId
	 * @return Signon
	 * @throws Exception
	 */
	public Signon getLastLoginTime(BigInteger userId) throws Exception;
}
