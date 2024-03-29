package id.co.gpsc.security.server.service;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface menu service
 * @author I Gede Mahendra
 * @since Nov 12, 2012, 4:25:18 PM
 * @version $Id
 */
public interface IUserMenuService {
	
	/**
	 * Get menu application yg akan di kirim ke client dlm bentuk JSON
	 * @param parameter - ApplicationID dan UUID
	 * @return list of application menu security
	 * @throws Exception
	 */
	public List<ApplicationMenuSecurity> getMenuApplication(Signon parameter) throws Exception;
	
	/**
	 * Membuat JSON application menu yg akan dikirim ke client
	 * @param parameter - ApplicationID dan UUID
	 * @return String JSON
	 * @throws Exception
	 */
	public String createJsonApplicationMenu(Signon parameter) throws Exception;
	/**
	 * mencari menu apa saja yang di miliki oleh user. ini di cari dengan ID dari user. <br/>
	 * apa yang di lakukan : 
	 * <ol>
	 * <li>cari group user dari table sec_user_assignment</li>
	 * <li>dari group user, locate function apa saja si user dapat nya</li>
	 * </ol>
	 * @param userId id dari user
	 **/
	public List<Function> getAllowedMenusByUserId (BigInteger userId)throws Exception ; 
	
	public List<ApplicationMenuSecurity> getMenuApplication(BigInteger userId) throws Exception  ;
}