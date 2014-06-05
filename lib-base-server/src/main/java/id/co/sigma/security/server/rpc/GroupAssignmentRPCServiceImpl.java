package id.co.sigma.security.server.rpc;

import id.co.sigma.common.data.PagedResultHolder;
import id.co.sigma.common.security.domain.UserGroupAssignment;
import id.co.sigma.common.security.dto.UserGroupAssignmentDTO;
import id.co.sigma.common.security.rpc.GroupAssignmentRPCService;
import id.co.sigma.security.server.service.IUserGroupAssignmentService;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.GroupAssignmentRPCServiceImpl" , 
		description="Servlet RPC untuk handle Group User" , 
		urlPatterns={"/sigma-rpc/group-assignment.app-rpc"})*/
public class GroupAssignmentRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseSecurityRPCService<GroupAssignmentRPCService> implements GroupAssignmentRPCService{

	private static final long serialVersionUID = 8846531085713519227L;

	@Autowired
	private IUserGroupAssignmentService groupAssignmentService;
	
	@Override
	public PagedResultHolder<UserGroupAssignmentDTO> getAllGroup(UserGroupAssignment parameter, int pagePosition, int pageSize) throws Exception {	
		return groupAssignmentService.getGroupAssignmentByParameter(parameter, pagePosition, pageSize);
	}

	@Override
	public void insert(UserGroupAssignment data) throws Exception {
		groupAssignmentService.insert(data);
	}

	@Override
	public void delete(BigInteger data) throws Exception {
		UserGroupAssignment parameter = new UserGroupAssignment();
		parameter.setId(data);
		groupAssignmentService.delete(parameter);	
	}

	@Override
	public List<UserGroupAssignmentDTO> getUserGroupByUserId(BigInteger userId) throws Exception {		
		return groupAssignmentService.getGroupByUserId(userId);
	}

	@Override
	public Class<GroupAssignmentRPCService> implementedInterface() {
		return GroupAssignmentRPCService.class;
	}
}