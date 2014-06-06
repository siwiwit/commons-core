package id.co.gpsc.security.server.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.ApplicationSessionRegistry;
import id.co.gpsc.common.security.rpc.ApplicationSessionManagementRPCService;
import id.co.gpsc.security.server.service.IApplicationSessionManagement;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class ApplicationSessionManagementRPCServiceImpl extends BaseSecurityRPCService<ApplicationSessionManagementRPCService> implements ApplicationSessionManagementRPCService{

	
	@Autowired
	private IApplicationSessionManagement applicationSessionManagement ;
	
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationSessionManagementRPCServiceImpl.class);
	
	
	@Override
	public Class<ApplicationSessionManagementRPCService> implementedInterface() {
		return ApplicationSessionManagementRPCService.class;
	}

	@Override
	public PagedResultHolder<ApplicationSessionRegistry> getCurrentlyLogedInUser(
			String usernameWildCard, String realNameWildCard, String email,
			int pageSize, int page) {
		return applicationSessionManagement.getCurrentlyLogedInUser(usernameWildCard, realNameWildCard, email, pageSize, page);
	}

	@Override
	public void forceLogoff(String sessionId) {
		try {
			SessionInformation inf =sessionRegistry.getSessionInformation(sessionId) ;
			Object w =  inf.getPrincipal(); 
			inf.expireNow(); 
			sessionRegistry.removeSessionInformation(sessionId);
			
		} catch (Exception e) {
			logger.error("gagal loggoff user untuk session id : " +sessionId ); 
		}
		  
		
	}

}
