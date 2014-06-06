package id.co.gpsc.security.server.rpc;

import java.math.BigInteger;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.security.domain.UserGroup;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.security.rpc.GroupRPCService;
import id.co.gpsc.common.server.rpc.BaseSelfRegisteredRPCService;
import id.co.gpsc.security.server.service.IUserGroupService;

import javax.servlet.annotation.WebServlet;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Group RPC Service Impl
 * @author I Gede Mahendra
 * @since Nov 26, 2012, 4:10:31 PM
 * @version $Id
 */
public class GroupRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseSecurityRPCService<GroupRPCService> implements GroupRPCService{

	private static final long serialVersionUID = -3764022559012913633L;

	@Autowired
	private IUserGroupService userGroupService;	
	
	@Override
	public String sampleRpc(String comment) {
		return comment;
	}

	@Override
	public PagedResultHolder<UserGroupDTO> getAllGroup(UserGroup parameter,int pagePosition, int pageSize) throws Exception{			
		return userGroupService.getAllUserGroup(parameter, pagePosition, pageSize);		
	}

	@Autowired
	public void setUserGroupService(IUserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	@Override
	public void insert(UserGroup parameter) throws Exception {
		userGroupService.insert(parameter);
	}

	@Override
	public void delete(BigInteger parameter) throws Exception {
		userGroupService.delete(parameter);		
	}

	@Override
	public UserGroupDTO getUserGroupByParameter(UserGroup parameter) throws Exception {		
		return userGroupService.getUserGroupByParameter(parameter);
	}

	@Override
	public void update(UserGroup parameter) throws Exception {
		userGroupService.update(parameter);		
	}

	@Override
	public Class<GroupRPCService> implementedInterface() {
		return GroupRPCService.class;
	}	
}