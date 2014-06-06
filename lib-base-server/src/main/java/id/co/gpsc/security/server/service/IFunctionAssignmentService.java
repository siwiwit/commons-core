/**
 * 
 */
package id.co.gpsc.security.server.service;

import id.co.gpsc.common.security.domain.FunctionAssignment;

import java.math.BigInteger;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * service untuk function assignment
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 2:33:43 PM
 */
public interface IFunctionAssignmentService {
	
	/**
	 * get function id by group id
	 * @param groupId group id
	 * @return list of function id
	 * @throws Exception
	 */
	public List<BigInteger> getFunctionIdByGroupId(BigInteger groupId) throws Exception;
	
	/**
	 * add and remove function assignment data
	 * @param addedData list of added data
	 * @param removedData list of removed data
	 * @throws Exception
	 */
	public void addRemoveFunctionAssignment(List<FunctionAssignment> addedData, List<FunctionAssignment> removedData) throws Exception;
}
