/**
 * 
 */
package id.co.gpsc.security.server.service;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.PasswordPolicy;
import id.co.gpsc.common.security.domain.User;

import java.math.BigInteger;

/**
 * @author Dode
 * @version $Id
 * @since Jan 30, 2013, 3:47:01 PM
 */
public interface IPasswordPolicyService {
	
	/**
	 * mendapatkan password policy
	 * @return list of password policy
	 * @throws Exception
	 */
	public PagedResultHolder<PasswordPolicy> getPasswordPolicy(int pagePosition, int pageSize) throws Exception;
	
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
