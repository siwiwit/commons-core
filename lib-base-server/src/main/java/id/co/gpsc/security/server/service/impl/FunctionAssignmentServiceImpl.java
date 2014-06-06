/**
 * 
 */
package id.co.gpsc.security.server.service.impl;

import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.security.server.dao.impl.FunctionAssignmentDaoImpl;
import id.co.gpsc.security.server.service.IFunctionAssignmentService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * service untuk function assignment 
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 2:37:12 PM
 */
@Service
public class FunctionAssignmentServiceImpl implements
		IFunctionAssignmentService {

	@Autowired
	private FunctionAssignmentDaoImpl functionAssignmentDao;
	
	@Override
	public List<BigInteger> getFunctionIdByGroupId(BigInteger groupId)
			throws Exception {
		List<BigInteger> groupIds = new ArrayList<BigInteger>();
		groupIds.add(groupId);
		return functionAssignmentDao.getFunctionIdByGroupId(groupIds);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void addRemoveFunctionAssignment(List<FunctionAssignment> addedData,
			List<FunctionAssignment> removedData) throws Exception {
		//add data
		if (addedData != null && !addedData.isEmpty())
			functionAssignmentDao.inserts(addedData);
		
		//remove data
		if (removedData != null && !removedData.isEmpty()) {
			List<FunctionAssignment> dbRemovedData = functionAssignmentDao.getFunctionAssignmentByIdAndGroupId(removedData);
			functionAssignmentDao.delete(dbRemovedData);
		}
	}
}
