package id.co.gpsc.security.server.dao;

import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.domain.ApplicationUser;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.security.domain.UserPassword;
import id.co.gpsc.common.server.dao.IBaseDao;

import java.math.BigInteger;
import java.util.List;

/**
 * User Dao Interface
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 1:45:38 PM
 * @version $Id
 */
public interface IUserDao extends IBaseDao{
	
	/**
	 * Get user by parameter
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserByParameter(User parameter, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Count user by parameter
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Integer countUserByParameter(User parameter) throws Exception;
	
	/**
	 * Get application user berdasarkan application Id
	 * @param applicationId
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return List of application user
	 * @throws Exception
	 */
	public List<ApplicationUser> getApplicationUser(BigInteger applicationId, User parameter, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Get user by list of user id
	 * @param userIds
	 * @return list of user
	 * @throws Exception
	 */
	public List<User> getUserByUserId(List<BigInteger> userIds) throws Exception;
	
	/**
	 * Get user group assignment berdasarkan user id
	 * @param userIds
	 * @return list of user group assignment
	 * @throws Exception
	 */
	public List<UserGroupAssignment> getGroupAssignmentByUserId(List<BigInteger> userIds) throws Exception;
	
	/**
	 * get semua data user
	 * @param filters filters untuk querynya
	 * @return list data user
	 * @throws Exception
	 */
	public List<User> getUserByFilters(SimpleQueryFilter[] filters, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * count semua data user di db
	 * @param filters filter untuk query
	 * @return jumlah data user
	 * @throws Exception
	 */
	public Integer countUserByFilters(SimpleQueryFilter[] filters) throws Exception;
	
	/**
	 * get user by id
	 * @param id id user yang dicari
	 * @return data user yang bersesuaian
	 * @throws Exception
	 */
	public User getUserById(BigInteger id) throws Exception;
	
	/**
	 * get user password by id
	 * @param id id biginteger
	 * @return list user password
	 * @throws Exception
	 */
	public List<UserPassword> getUserPasswordByUserId(BigInteger id) throws Exception;
	
	/**
	 * cek apakah user name exist di database
	 * @param userCode user name yang dicek
	 * @return user data
	 * @throws Exception
	 */
	public User isUserNameExist(String userCode, Integer applicationId) throws Exception;

	public User getUserByUserName(String username);

	/**
	 * Mencari jumlah user dalam yg termasuk di dalam satu kode cabang.
	 * 
	 * @param branchCode default kode cabang
	 * 
	 * @return Jumlah user yg terdaftar dalam suatu kode cabang.
	 */
	public Long selectUserCountByBranchCode(String branchCode);
}