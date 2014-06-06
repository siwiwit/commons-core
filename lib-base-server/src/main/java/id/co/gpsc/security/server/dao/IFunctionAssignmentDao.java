package id.co.gpsc.security.server.dao;

import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.server.dao.IBaseDao;

import java.math.BigInteger;
import java.util.List;

/**
 * Interface untuk function assignment
 * @author I Gede Mahendra
 * @since Dec 13, 2012, 3:42:34 PM
 * @version $Id
 */
public interface IFunctionAssignmentDao extends IBaseDao {
	
	/**
	 * Delete function assignment berdasarkan group id
	 * @param groupId
	 * @throws Exception
	 */
	public void deleteFunctionByGroupId(BigInteger groupId) throws Exception;
	
	
	
	/**
	 * menghapus data dari {@link UserGroupAssignment}. ini jadinya semua user yang mendapat group sesuai ID akan di hapus
	 */
	public void deleteUserGroupAssigmentByGroupId(BigInteger groupId) throws Exception;
	
	
	/**
	 * add by dode
	 * get function id on function assignment by group id
	 * @param groupIds list of group id
	 * @return list of function id
	 * @throws Exception
	 */
	public List<BigInteger> getFunctionIdByGroupId(List<BigInteger> groupIds) throws Exception;
	
	
	/**
	 * membaca group assignment berdasarkan ID dari group
	 * @param groupId Id dari group yang hendak di baca
	 */
	public List<FunctionAssignment> getFunctionAssigmentByGroupId ( BigInteger groupId  ) ; 
	
	/**
	 * add by dode
	 * get function assignment data by id and group id
	 * @param data list of function assignment
	 * @return list of function assignment
	 * @throws Exception
	 */
	public List<FunctionAssignment> getFunctionAssignmentByIdAndGroupId(List<FunctionAssignment> data) throws Exception;
	
	/**
	 * add by dode
	 * delete function assigment by function id
	 * @param functionId function id
	 * @throws Exception
	 */
	public void deleteFunctionAssigmentByFunctionId(BigInteger functionId) throws Exception;
}