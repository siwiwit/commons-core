package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.security.exception.PasswordPolicyException;

import java.math.BigInteger;

/**
 * User RPC Service
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 11:01:15 AM
 * @version $Id
 */
//@RemoteServiceRelativePath(value="/sigma-rpc/user.app-rpc")
public interface UserRPCService extends JSONSerializedRemoteService{
	
	/**
	 * Get username by parameter
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PagedResultHolder<UserDTO> getUserByParameter(SigmaSimpleQueryFilter[] filter, int page, int pageSize) throws Exception;
	
	/**
	 * Get user by parameter
	 * @param applicationId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @return PageResultHolder
	 * @throws Exception
	 */
	public PagedResultHolder<UserDTO> getUserByParameter(BigInteger applicationId,SigmaSimpleQueryFilter[] filter, int page, int pageSize) throws Exception;
	
	/**
	 * get user by filter
	 * @param filters filter searching
	 * @param pagePosition page data yang ingin di tampilkan
	 * @param pageSize size data per page
	 * @return data user
	 * @throws Exception
	 */
	public PagedResultHolder<User> getUserByFilter(SigmaSimpleQueryFilter[] filters, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * insert data user
	 * @param data data user yang di save
	 * @throws Exception
	 */
	public void insert(User data) throws Exception, PasswordPolicyException;
	
	/**
	 * update data user
	 * @param data data user yang di update
	 * @throws Exception
	 */
	public void update(User data) throws Exception;
	
	/**
	 * remove user
	 * @param id id user yang di remove
	 * @throws Exception
	 */
	public void remove(BigInteger id) throws Exception;
	
	/**
	 * reset password
	 * @param user data user dengan password baru
	 * @throws Exception
	 */
	public void resetPassword(User data) throws Exception, PasswordPolicyException;
	
	/**
	 * Get current sigma user login
	 * @return Sigma User Detail
	 */
	public UserDetailDTO getCurrentUserLogin();
}