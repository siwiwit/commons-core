/**
 * 
 */
package id.co.gpsc.security.server.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.PasswordPolicy;
import id.co.gpsc.common.security.rpc.PasswordPolicyRPCService;
import id.co.gpsc.security.server.service.IPasswordPolicyService;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 3:42:08 PM
 */
/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.PasswordPolicyRPCServiceImpl" , 
		description="Servlet RPC untuk handle password policy Domain" , 
		urlPatterns={"/sigma-rpc/password-policy.app-rpc"})*/
public class PasswordPolicyRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseSecurityRPCService<PasswordPolicyRPCService>
		implements PasswordPolicyRPCService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5776508300609473971L;

	@Autowired
	private IPasswordPolicyService passwordPolicyService;
	
	@Override
	public PagedResultHolder<PasswordPolicy> getPasswordPolicyData(
			int pagePosition, int pageSize) throws Exception {
		return passwordPolicyService.getPasswordPolicy(pagePosition, pageSize);
	}

	@Override
	public void insert(PasswordPolicy data) throws Exception {
		passwordPolicyService.insert(data);
	}

	@Override
	public void update(PasswordPolicy data) throws Exception {
		passwordPolicyService.update(data);
	}

	@Override
	public void remove(BigInteger id) throws Exception {
		passwordPolicyService.remove(id);
	}

	@Override
	public Class<PasswordPolicyRPCService> implementedInterface() {
		return PasswordPolicyRPCService.class;
	}

}
