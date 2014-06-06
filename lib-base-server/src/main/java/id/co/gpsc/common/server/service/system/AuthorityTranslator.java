package id.co.gpsc.common.server.service.system;

import java.util.Collection;

import id.co.gpsc.common.server.data.security.SigmaAuthority;
import id.co.gpsc.common.server.data.security.UserLoginNotificationData;



/**
 * translator dari array of user group ke SigmaAuthority. ini agar applikasi comply dengan spring security. karena spring security memblok user dengan authority dari user,
 * jadinya untuk user musti di provide authority
 **/
public interface AuthorityTranslator {
	
	public Collection<SigmaAuthority> generateAuthorities (UserLoginNotificationData notificationResult) ; 

}
