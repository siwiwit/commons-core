package id.co.gpsc.security.server.service;

import java.math.BigInteger;
import java.util.List;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.security.dto.UserGroupAssignmentDTO;
import id.co.gpsc.common.security.dto.UserGroupDTO;

/**
 * User group Assignemnt Service
 * @author I Gede Mahendra
 * @since Dec 7, 2012, 11:56:52 AM
 * @version $Id
 */
public interface IUserGroupAssignmentService {
	
	/**
	 * Get user group assignment by parameter
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return PageResultHolder
	 * @throws Exception
	 */
	public PagedResultHolder<UserGroupAssignmentDTO> getGroupAssignmentByParameter(UserGroupAssignment parameter, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Insert data
	 * @param data
	 * @throws Exception
	 */
	public void insert(UserGroupAssignment data) throws Exception;
	
	/**
	 * Delete data
	 * @param data
	 * @throws Exception
	 */
	public void delete(UserGroupAssignment data) throws Exception;
	
	/**
	 * Get user group berdasarkan user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupAssignmentDTO> getGroupByUserId(BigInteger userId) throws Exception;
	
	/**
	 * get user group assignment berdasarkan user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupDTO> getUserGroupByUserId(BigInteger userId) throws Exception;
}