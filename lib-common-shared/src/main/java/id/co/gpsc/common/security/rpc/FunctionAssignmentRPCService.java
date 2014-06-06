/**
 * 
 */
package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.app.security.MenuEditingData;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.domain.FunctionAssignment;

import java.math.BigInteger;
import java.util.List;

/**
 * rpc untuk function assignment
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 2:14:30 PM
 */
public interface FunctionAssignmentRPCService extends JSONSerializedRemoteService {
	
	
	/**
	 * membaca data aplication yang tersedia
	 * @param applicationId id aplikasi
	 * @param groupId id group 
	 */
	public MenuEditingData getAllAvailableMenu ( BigInteger applicationId , BigInteger groupId ) ; 
	
	/**
	 * get function id by group id
	 * @param groupId group id
	 * @return list of function id
	 * @throws Exception
	 */
	public List<BigInteger> getFunctionIdByGroupId(BigInteger groupId) throws Exception;
	
	/**
	 * added by dode
	 * add and remove menu item from group menu item
	 * @param addedMenuItem list of added menu item
	 * @param removedMenuItem list of removed menu item
	 * @throws Exception
	 */
	public void addRemoveFunctionAssignment(List<FunctionAssignment> addedMenuItem, List<FunctionAssignment> removedMenuItem) throws Exception;
}
