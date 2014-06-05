package id.co.gpsc.common.security;

import java.math.BigInteger;

/**
 * Class untuk menampung variable global
 * @author Gede Sutarsa
 * @since Dec 13, 2012, 12:05:45 PM
 * @version $Id
 */
public class AriumSecurityGlobalParameterHolder {
	
	/**
	 * aplikasi current id
	 **/
	public static BigInteger CURRENT_APPLICATION_ID ; 
	
	/**
	 * nama current aplikasi 
	 */
	public static String CURRENT_APPLICATION_NAME;
	
	/**
	 * Nama user yg login
	 */
	public static String CURRENT_USER_LOGIN;	
}