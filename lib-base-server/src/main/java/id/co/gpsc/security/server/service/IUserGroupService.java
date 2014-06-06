package id.co.gpsc.security.server.service;

import java.math.BigInteger;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.dto.UserGroupDTO;

/**
 * User Group Service
 * @author I Gede Mahendra
 * @since Nov 26, 2012, 6:03:19 PM
 * @version $Id
 */
public interface IUserGroupService {

	/**
	 * Get all user group
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return PageResultHolder
	 * @throws Exception
	 */
	public PagedResultHolder<UserGroupDTO> getAllUserGroup(UserGroup parameter, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Insert user group
	 * @param parameter
	 * @throws Exception
	 */
	public void insert(UserGroup parameter) throws Exception;
	
	/**
	 * Delete user group
	 * @param paramater
	 * @throws Exception
	 */
	public void delete(BigInteger paramater) throws Exception;
	
	/**
	 * Update user group
	 * @param parameter
	 * @throws Exception
	 */
	public void update(UserGroup parameter) throws Exception;
	
	/**
	 * Get user group by parameter
	 * @param parameter
	 * @return User group
	 * @throws Exception
	 */
	public UserGroupDTO getUserGroupByParameter(UserGroup parameter) throws Exception;
}