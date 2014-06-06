/**
 * 
 */
package id.co.gpsc.security.server.dao;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.server.dao.IBaseDao;

import java.math.BigInteger;
import java.util.List;

/**
 * dao untuk function
 * @author Dode
 * @version $Id
 * @since Jan 4, 2013, 7:15:57 PM
 */
public interface IFunctionDao extends IBaseDao {
	
	/**
	 * get function data by tree level position dan sibling order by application id
	 * @param applicationId application id
	 * @return list of function
	 * @throws Exception
	 */
	public List<Function> getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(BigInteger applicationId) throws Exception;
	
	
	/**
	 * select ke dalam table application menu, 
	 * @param applicationId id dari app yang perlu di load
	 **/
	public List<Function> getAppMenuByAppIdJoindedWithPageOrderByTreeLevelAndSiblingOrder(BigInteger applicationId) throws Exception;
	
	/**
	 * get function data by function id order by tree level position dan sibling order by application id
	 * @param functionIds list of function id
	 * @return list of function
	 * @throws Exception
	 */
	public List<Function> getFunctionByFunctionIdOrderByTreeLevelAndSiblingOrder(List<BigInteger> functionIds) throws Exception;
	
	/**
	 * add by dode
	 * get function by parent id
	 * @param parentId parent
	 * @return number of child function
	 * @throws Exception
	 */
	public Long getFunctionByFunctionIdParent(BigInteger parentId) throws Exception;
	
	
	
	
	/**
	 * membaca data dengan id dari aplikasi
	 * @param applicationId id dari aplikasi
	 */
	public List<Function> getAllFunctionByApplicationId (BigInteger applicationId ) ; 
	
	/**
	 * add by dode
	 * delete function by id
	 * @param id function id
	 * @throws Exception
	 */
	public void deleteFunctionById(BigInteger id) throws Exception;
	
	/**
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public Integer getNextSiblingOrder(BigInteger parentId, BigInteger applicationId) throws Exception;
}
