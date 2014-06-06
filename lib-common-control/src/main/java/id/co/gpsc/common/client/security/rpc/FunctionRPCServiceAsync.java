/**
 * 
 */
package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.FunctionRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.PageDefinition;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;
import id.co.gpsc.common.security.dto.PageDefinitionDTO;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * rpc untuk function
 * @author Dode
 * @version $Id
 * @since Jan 7, 2013, 10:25:24 AM
 */
public interface FunctionRPCServiceAsync {

	public static class Util {
		private static FunctionRPCServiceAsync instance ; 
		
		public static FunctionRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(FunctionRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * get function by group id order by tree level and sibling order
	 * @param groupId group id
	 * @return list of function
	 * @throws Exception
	 */
	public void getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(List<BigInteger> groupIds, AsyncCallback<List<Function>> callback) throws Exception;
	
	/**
	 * get function by application id order by tree level and sibling order
	 * @param applicationId application id
	 * @return list of function
	 * @throws Exception
	 */
	public void getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(BigInteger applicationId, AsyncCallback<List<Function>> callback) throws Exception;
	
	/**
	 * halaman yang tersedia dalam aplikasi saat ini
	 * 
	 **/
	public void getCurrentAppAvailablePages ( SimpleQueryFilter[] filters , SimpleSortArgument[] sortArgs , int pageSize , int page, AsyncCallback<PagedResultHolder<PageDefinitionDTO>> callback) ;
	
	
	/**
	 * membaca menu. di cari dengan current application
	 * @return list of menus dari current application
	 **/
	public void getCurrentAppMenusOrderByTreeLevelAndSiblingOrder( AsyncCallback<List<Function> > callback)  ;
	
	/**
	 * versi ini mengeluarkan DTO dari application menu. 
	 **/
	public void getCurrentAppMenuDToByAppIdOrderByTreeLevelAndSiblingOrder(AsyncCallback<List<ApplicationMenuDTO>> callback)  ;

	
	/**
	 * hapus application menu. di hapus bersama dengan semua referensi 
	 * @param applicationMenuId id dari menu yang hendak di hapus
	 **/
	public void eraseApplicationMenu (BigInteger applicationMenuId , AsyncCallback<Void> callback) throws Exception; 
	
	
	
	/**
	 * update application menu. yang tidak ikut di update : 
	 * <ol>
	 * <li>menu code</li>
	 * 
	 * 
	 * </ol>
	 * @param menuData berisi perubahan menu
	 **/
	public void updateApplicationMenu (ApplicationMenuDTO menuData , AsyncCallback<Void> callback) throws Exception; 
	
	
	
	/**
	 * add menu node
	 **/
	public void appendNewMenuNode (ApplicationMenuDTO menuData , AsyncCallback<ApplicationMenuDTO> callback) throws Exception; 
	
	
	/**
	 * mencari data page definition dengan id dari page
	 */
	public void getPageDefinition (BigInteger  page , AsyncCallback<PageDefinition> callback) ; 
	
}
