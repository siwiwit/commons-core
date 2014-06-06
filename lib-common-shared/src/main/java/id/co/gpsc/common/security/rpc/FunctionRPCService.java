/**
 * 
 */
package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.exception.DataNotFoundException;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.PageDefinition;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;
import id.co.gpsc.common.security.dto.PageDefinitionDTO;

import java.math.BigInteger;
import java.util.List;

/**
 * rpc untuk function
 * @author Dode
 * @version $Id
 * @since Jan 7, 2013, 10:19:50 AM
 */
public interface FunctionRPCService extends JSONSerializedRemoteService {
	
	/**
	 * get function by group id order by tree level and sibling order
	 * @param groupId group id
	 * @return list of function
	 * @throws Exception
	 */
	public List<Function> getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(List<BigInteger> groupIds) throws Exception;
	
	/**
	 * get function by application id order by tree level and sibling order
	 * @param applicationId application id
	 * @return list of function
	 * @throws Exception
	 */
	public List<Function> getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(BigInteger applicationId) throws Exception;
	
	
	/**
	 * versi ini mengeluarkan DTO dari application menu. 
	 **/
	public List<ApplicationMenuDTO> getCurrentAppMenuDToByAppIdOrderByTreeLevelAndSiblingOrder() throws Exception;
	
	
	/**
	 * membaca menu. di cari dengan current application
	 * @return list of menus dari current application
	 **/
	public List<Function> getCurrentAppMenusOrderByTreeLevelAndSiblingOrder( ) throws Exception;
	
	
	 
	
	
	/**
	 * halaman yang tersedia dalam aplikasi saat ini
	 **/
	public PagedResultHolder<PageDefinitionDTO> getCurrentAppAvailablePages (SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page)throws Exception ; 
	
	
	
	
	/**
	 * hapus application menu. di hapus bersama dengan semua referensi 
	 * @param applicationMenuId id dari menu yang hendak di hapus
	 **/
	public void eraseApplicationMenu (BigInteger applicationMenuId) throws Exception ; 
	
	
	
	/**
	 * update application menu. yang tidak ikut di update : 
	 * <ol>
	 * <li>menu code</li>
	 * 
	 * 
	 * </ol>
	 * @param menuData berisi perubahan menu
	 **/
	public void updateApplicationMenu (ApplicationMenuDTO menuData ) throws DataNotFoundException , Exception  ; 
	
	
	
	
	
	/**
	 * add menu node
	 **/
	public ApplicationMenuDTO appendNewMenuNode (ApplicationMenuDTO menuData ) throws Exception; 
	
	
	/**
	 * mencari data page definition dengan id dari page
	 */
	public PageDefinition getPageDefinition (BigInteger  page ) ; 
}
