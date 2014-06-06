package id.co.gpsc.security.server;

import org.springframework.security.core.GrantedAuthority;

/**
 * Authority
 * @author I Gede Mahendra
 * @since Jan 29, 2013, 11:09:22 AM
 * @version $Id
 */
public class SimpleUserAuthority implements GrantedAuthority{

	private static final long serialVersionUID = -4592877226590205303L;

	/**
	 *  kode auth
	 **/
	private String authority ;
	
	/**
	 * Constructor
	 */
	public SimpleUserAuthority() {
		super();
	}
	
	/**
	 * Additional Constructor
	 * @param authority
	 */
	public SimpleUserAuthority(SimpleAuthorityEnum authority){
		this.authority = authority.toString();
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	} 
	
}
