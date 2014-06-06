/**
 * 
 */
package id.co.gpsc.security.server.rpc;

import id.co.gpsc.common.data.app.security.MenuEditingData;
import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.common.security.rpc.FunctionAssignmentRPCService;
import id.co.gpsc.common.server.rpc.BaseSelfRegisteredRPCService;
import id.co.gpsc.common.server.rpc.impl.BaseRPCHandler;
import id.co.gpsc.security.server.service.IFunctionAssignmentService;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * rpc untuk function assignment
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 2:24:17 PM
 */
/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.FunctionAssignmentRPCServiceImpl" , 
		description="Servlet RPC untuk handle Function Assignment Domain" , 
		urlPatterns={"/sigma-rpc/function-assignment.app-rpc"})*/
public class FunctionAssignmentRPCServiceImpl extends
		/*BaseSelfRegisteredRPCService*/ BaseSecurityRPCService<FunctionAssignmentRPCService> implements FunctionAssignmentRPCService {

	private static final long serialVersionUID = -3333502819213472079L;

	@Autowired
	private IFunctionAssignmentService functionAssignmentService;
	
	@Override
	public List<BigInteger> getFunctionIdByGroupId(BigInteger groupId)
			throws Exception {
		return functionAssignmentService.getFunctionIdByGroupId(groupId);
	}

	@Override
	public void addRemoveFunctionAssignment(List<FunctionAssignment> addedMenuItem,
			List<FunctionAssignment> removedMenuItem) throws Exception {
		functionAssignmentService.addRemoveFunctionAssignment(addedMenuItem, removedMenuItem);
	}

	@Override
	public Class<FunctionAssignmentRPCService> implementedInterface() {
		return FunctionAssignmentRPCService.class;
	}

	@Override
	public MenuEditingData getAllAvailableMenu(BigInteger applicationId,
			BigInteger groupId) {
		
		return null;
	}

}
