/**
 * 
 */
package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.domain.PasswordPolicy;

import java.math.BigInteger;

/**
 * password policy RPC Service
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 3:34:34 PM
 */
//@RemoteServiceRelativePath(value="/sigma-rpc/password-policy.app-rpc")
public interface PasswordPolicyRPCService extends JSONSerializedRemoteService {
	
	/**
	 * get password policy data
	 * @param pagePosition page want to display
	 * @param pageSize size data per page
	 * @return list of password policy data
	 * @throws Exception
	 */
	public PagedResultHolder<PasswordPolicy> getPasswordPolicyData(int pagePosition, int pageSize) throws Exception;
	
	/**
	 * insert data password policy
	 * @param data data password policy yang di save
	 * @throws Exception
	 */
	public void insert(PasswordPolicy data) throws Exception;
	
	/**
	 * update data password policy
	 * @param data data password policy yang di update
	 * @throws Exception
	 */
	public void update(PasswordPolicy data) throws Exception;
	
	/**
	 * remove password policy
	 * @param id id password policy yang di remove
	 * @throws Exception
	 */
	public void remove(BigInteger id) throws Exception;
}
