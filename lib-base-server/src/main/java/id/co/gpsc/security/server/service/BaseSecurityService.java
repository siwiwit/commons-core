package id.co.gpsc.security.server.service;

import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.server.service.AbstractSigmaService;
import id.co.gpsc.security.server.SigmaUserDetail;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Base Security Service
 * @author I Gede Mahendra
 * @since Jan 16, 2013, 1:04:05 PM
 * @version $Id
 */
public class BaseSecurityService extends AbstractSigmaService {
	
	/**
	 * Get user detail DTO dari spring security context
	 * @return UserDetailDTO
	 */
	public UserDetailDTO getUserDetailDTOFromSecurityContext(){									
		SigmaUserDetail user = (SigmaUserDetail) getSigmaUserDetailFromSecurityContext();
		UserDetailDTO userDetail = null;
		if(user != null){
			userDetail = new UserDetailDTO();
			userDetail.setApplicationId(user.getApplicationId());
			userDetail.setApplicationLoginUrl(user.getApplicationLoginUrl());
			userDetail.setApplicationName(user.getApplicationName());
			userDetail.setApplicationUrl(user.getApplicationUrl());
			userDetail.setFullNameUser(user.getFullNameUser());
			userDetail.setUsername(user.getUsername());
			userDetail.setPassword(user.getPassword());		
			userDetail.setPasswordNoHashing(user.getPasswordNoHashing());
			
			userDetail.setLastLogin(user.getLastLogin());
			userDetail.setUuid(user.getUuid());
			userDetail.setUserId(user.getUserInternalId());		
			userDetail.setCurrentBranch(user.getCurrentBranchCode());
		}		
		return userDetail; 		
	}
	
	/**
	 * Get Sigma user detail dari spring security context
	 * @return SigmaUserDetail
	 */
	public SigmaUserDetail getSigmaUserDetailFromSecurityContext(){	
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		if ( auth==null)
			return null ; 
		Object swap =  auth.getPrincipal();  
		if (swap==null){
			return null;
		}			
		if (!( swap instanceof SigmaUserDetail)){
			return null ;
		}
		return (SigmaUserDetail) swap;		
	}
	
	/**
	 * Get object spring authentification
	 * @return Authentication
	 */
	public Authentication getAuthentificationContext(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
}