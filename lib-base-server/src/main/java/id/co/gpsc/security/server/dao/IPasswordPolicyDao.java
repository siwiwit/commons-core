/**
 * 
 */
package id.co.gpsc.security.server.dao;

import id.co.gpsc.common.security.domain.PasswordPolicy;

import java.math.BigInteger;
import java.util.List;

/**
 * dao untuk password policy
 * @author Dode
 * @version $Id
 * @since Jan 2, 2013, 4:23:03 PM
 */
public interface IPasswordPolicyDao {
	
	/**
	 * mendapatkan password policy
	 * @return list of password policy
	 * @throws Exception
	 */
	public List<PasswordPolicy> getPasswordPolicy() throws Exception;
	
	/**
	 * count password policy
	 * @return number of password policy
	 * @throws Exception
	 */
	public Integer countPasswordPolicy() throws Exception;
	
	/**
	 * get password policy data by id
	 * @param id id password policy
	 * @return password policy data
	 * @throws Exception
	 */
	public PasswordPolicy getPasswordPolicyById(BigInteger id) throws Exception;
}
