package id.co.gpsc.security.server.dao;

import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.domain.UserGroupAssignment;

import java.math.BigInteger;
import java.util.List;

/**
 * User group assigment Interface
 * @author I Gede Mahendra
 * @since Dec 7, 2012, 11:39:31 AM
 * @version $Id
 */
public interface IUserGroupAssignmentDao {
	
	/**
	 * Get data user group assignment berdasarkan parameter
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return list of UserGroupAssignment
	 * @throws Exception
	 */
	public List<UserGroupAssignment> getDataByParameter(UserGroupAssignment parameter,int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Count all user group assignment
	 * @param parameter
	 * @return Integer
	 * @throws Exception
	 */
	public Integer countAllUserGroupAssignment(UserGroupAssignment parameter) throws Exception;
	
	/**
	 * Delete group assignment berdasarkan group id
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroupAssignmentByGroupId(BigInteger groupId) throws Exception;
	
	/**
	 * Delete group assignmeny berdasarkan user id
	 * @param userId
	 * @throws Exception
	 */
	public void deleteGroupAssignmentByUserId(BigInteger userId) throws Exception;
	
	/**
	 * Delete group assignment berdasarkan group id dan user id
	 * @param userId
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteGroupAssignmenyByUserGroupId(BigInteger userId, BigInteger groupId) throws Exception;
	
	/**
	 * Get all group assignment by user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<UserGroupAssignment> getDataByUserId(BigInteger userId) throws Exception;
	
	
	
	/**
	 * mencari apakah ada user yang masuk dalam group yang di minta
	 */
	public boolean isAnyUserWithGroup ( UserGroup group ) ; 
}