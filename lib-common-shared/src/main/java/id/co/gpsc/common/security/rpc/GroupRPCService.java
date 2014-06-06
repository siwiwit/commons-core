package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.dto.UserGroupDTO;

import java.math.BigInteger;

/**
 * Group RPC
 * @author I Gede Mahendra
 * @since Nov 26, 2012, 3:57:26 PM
 * @version $Id
 */
public interface GroupRPCService extends JSONSerializedRemoteService{
	
	/**
	 * Sample RPC Service
	 * @param comment
	 * @return String
	 */
	public String sampleRpc(String comment);
	
	/**
	 * Get all group
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return PagedSimpleDataGridData
	 * @throws Exception
	 */
	public PagedResultHolder<UserGroupDTO> getAllGroup(UserGroup parameter,int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Insert user group	
	 * @param parameter
	 * @throws Exception
	 */
	public void insert(UserGroup parameter) throws Exception;
	
	/**
	 * Delete user group
	 * @param parameter
	 * @throws Exception
	 */
	public void delete(BigInteger parameter) throws Exception;
	
	/**
	 * Update user group
	 * @param parameter
	 * @throws Exception
	 */
	public void update(UserGroup parameter) throws Exception;
	
	/**
	 * Get user group by parameter
	 * @param parameter
	 * @return UserGroup
	 * @throws Exception
	 */
	public UserGroupDTO getUserGroupByParameter(UserGroup parameter) throws Exception;
}