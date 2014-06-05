package id.co.sigma.common.server.service;

/**
 * base interface untuk service
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface IBaseService {
	/**
	 * membaca IP dari current login
	 **/
	public String getCurrentUserIpAddress (); 
	/**
	 * current user yang login, ini ikut dengan spring security
	 **/
	public String getCurrentUserName () ;
	
	/**
	 * kode cabang dari user
	 */
	public String getCurrentBranchCode (); 
}
