/**
 * 
 */
package id.co.sigma.security.server.service;

import id.co.sigma.common.security.domain.Function;
import id.co.sigma.common.security.dto.ApplicationMenuDTO;

import java.math.BigInteger;
import java.util.List;

/**
 * service untuk function
 * @author Dode
 * @version $Id
 * @since Jan 7, 2013, 10:13:19 AM
 */
public interface IFunctionService {
	
	/**
	 * get function by group id order by tree level position dan sibling order
	 * @param groupId group id
	 * @return list of function
	 * @throws Exception
	 */
	public List<Function> getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(List<BigInteger> groupIds) throws Exception;
	
	/**
	 * get function data by application id order by tree level position dan sibling order
	 * @param applicationId application id
	 * @return list of function
	 * @throws Exception
	 */
	public List<Function> getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(BigInteger applicationId) throws Exception;
	
	
	/**
	 * membaca data function dengan ID dari function/menu
	 **/
	public Function getFunctionById(BigInteger functionId ) ; 
	
	
	/**
	 * update function data
	 **/
	public void updateFunction (Function function ) throws Exception;
	
	/**
	 * hapus application menu. di hapus bersama dengan semua referensi 
	 * @param applicationMenuId id dari menu yang hendak di hapus
	 **/
	public void eraseApplicationMenu (BigInteger applicationMenuId) throws Exception ;
	
	/**
	 * insert function
	 * @param newData
	 * @throws Exception
	 */
	public ApplicationMenuDTO insertNewApplicationMenu(ApplicationMenuDTO newData) throws Exception;
}
