package id.co.sigma.security.server.rpc;

import id.co.sigma.common.security.domain.ApplicationUser;
import id.co.sigma.common.security.domain.Signon;
import id.co.sigma.common.security.domain.User;
import id.co.sigma.common.security.dto.UserGroupAssignmentDTO;
import id.co.sigma.common.security.menu.ApplicationMenuSecurity;
import id.co.sigma.common.security.rpc.ApplicationUserRPCService;
import id.co.sigma.common.server.rpc.impl.BaseRPCHandler;
import id.co.sigma.security.server.dao.IUserDao;
import id.co.sigma.security.server.service.IApplicationUserService;
import id.co.sigma.security.server.service.IUserMenuService;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Application User RPC Service
 * @author I Gede Mahendra
 * @since Dec 20, 2012, 10:40:32 AM
 * @version $Id
 */

public class ApplicationUserRPCServiceImpl extends  BaseSecurityRPCService<ApplicationUserRPCService> implements ApplicationUserRPCService{

	

	@Autowired
	private IApplicationUserService applicationUserService;
	
	@Autowired
	private IUserMenuService userMenuService;
	
	
	
	@Autowired
	private IUserDao userDao ; 
	
	
	@Override
	public Integer countApplicationUserByParameter(ApplicationUser parameter) throws Exception {		
		return applicationUserService.countApplicationUserByParameter(parameter);
	}

	@Override
	public void insertOrUpdate(List<UserGroupAssignmentDTO> data, BigInteger applicationId, BigInteger userId, String currentUser) throws Exception {
		applicationUserService.insertApplicationUser(data, applicationId, userId, currentUser);		
	}

	@Override
	public void deleteApplicationUser(BigInteger applicationId, BigInteger userId) throws Exception {
		applicationUserService.deleteApplicationUser(applicationId, userId);		
	}

	@Override
	public List<ApplicationMenuSecurity> getApplicationMenu(Signon data) throws Exception {		
		return userMenuService.getMenuApplication(data);
	}
	
	
	/**
	 * method ini di desain untuk deploy dengan 1 app untuk sec_* , jadinya bukan di mix. ini mencari menu yang di miliki oleh user tertentu
	 * @param username username yang hendak di cari
	 **/
	public List<ApplicationMenuSecurity> getApplicationMenu(String username) throws Exception{
		if ( username== null|| username.length()==0)
			return null ; 
		User usr =  userDao.getUserByUserName(username);
		if (usr == null)
			return null ; 
		return userMenuService.getMenuApplication(usr.getId());
	}
	
	

	@Override
	public Class<ApplicationUserRPCService> implementedInterface() {
		return ApplicationUserRPCService.class;
	}	
	
	
	
	
}