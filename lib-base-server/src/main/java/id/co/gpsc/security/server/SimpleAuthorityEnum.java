package id.co.gpsc.security.server;

/**
 * Enum untuk authoritu yg berlaku di arium security
 * @author I Gede Mahendra
 * @since Jan 29, 2013, 11:30:56 AM
 * @version $Id
 */
public enum SimpleAuthorityEnum{
	
	SUPER_ADMIN("SUPER_ADMIN"),
	ADMIN_APP("ADMIN_APP");
	
	private String authorize ; 
	
	private SimpleAuthorityEnum(String authorityCode){
		this.authorize=authorityCode;
	}
	
	@Override
	public String toString() {
		return authorize;
	}
}