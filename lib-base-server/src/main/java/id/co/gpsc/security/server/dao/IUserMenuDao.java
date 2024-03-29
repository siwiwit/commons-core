package id.co.gpsc.security.server.dao;

import java.math.BigInteger;
import java.util.List;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.domain.UserGroupAssignment;


/**
 * Interface Dao untuk mendapatkan menu berdasarkan usernya
 * @author I Gede Mahendra
 * @since Nov 12, 2012, 12:26:37 PM
 * @version $Id
 */
public interface IUserMenuDao {
	
	/**
	 * Get satu row data berdasarkan parameter yg dimasukkan. Minimal Paramater : UUID dan ApplicationId
	 * @param parameter
	 * @return Object Signon yg berisi UserId
	 * @throws Exception
	 */
	public Signon getDataSignonByParam(Signon parameter) throws Exception;
	
	/**
	 * Get satu atau lbh data pada group_assigment berdasarkan parameter yg dimasukkan. Minimal parameter : user_id
	 * @param parameter - userId
	 * @return List of UserGroupAssigment yg isi group_id
	 * @throws Exception
	 */
	public List<UserGroupAssignment> getGroupAssigmentByParam(BigInteger userId) throws Exception;
	
	/**
	 * Get satu atau lebih data pada function_assignment berdasarkan parameter. Minumal parameter : group_id
	 * @param parameter berupa list(BigInteger) dr group_id. Dpt dimasukkan group_id lbh dari 1
	 * @return List of FunctionAssignment yg isi function_id
	 * @throws Exception
	 */
	public List<FunctionAssignment> getFunctionAssignmentByGroupId(List<BigInteger> parameter) throws Exception;
	
	/**
	 * Get function menu berdasarkan function_id nya
	 * @param parameter - List(BigInteger) dari id_function
	 * @return List of function
	 * @throws Exception
	 */
	public List<Function> getFunctionMenuByFunctionId(List<BigInteger> parameter) throws Exception;
}