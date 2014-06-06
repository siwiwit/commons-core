package id.co.gpsc.security.server.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.dto.BranchDTO;
import id.co.gpsc.common.security.rpc.BranchRPCService;
import id.co.gpsc.common.server.rpc.impl.BaseRPCHandler;
import id.co.gpsc.security.server.service.IBranchService;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;

/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.BranchRPCServiceImpl" , 
		description="Servlet RPC untuk handle application" , 
		urlPatterns={"/sigma-rpc/branch-list.app-rpc"})*/
public class BranchRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/ BaseSecurityRPCService<BranchRPCService> implements BranchRPCService{
	
	private static final long serialVersionUID = 5220648987667093142L;

	@Autowired
	private IBranchService branchService;
	
	@Override
	public PagedResultHolder<BranchDTO> getDataByParameter(SigmaSimpleQueryFilter[] filter, int page, int pageSize) throws Exception {
		return branchService.getUserByParameter(filter, page, pageSize);
	}

	@Override
	public void saveOrUpdateBranch(Branch data) throws Exception {
		branchService.saveOrUpdate(data);
	}

	@Override
	public void remove(BigInteger id) throws Exception {
		branchService.remove(id);
	}

	@Override
	public Class<BranchRPCService> implementedInterface() {
		return BranchRPCService.class;
	}
}