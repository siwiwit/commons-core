package id.co.gpsc.common.security.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.rpc.JSONSerializedRemoteService;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.dto.BranchDTO;

import java.math.BigInteger;

/**
 * Branch list RPC Service
 * @author I Gede Mahendra
 * @since Jan 30, 2013, 3:34:57 PM
 * @version $Id
 */
//@RemoteServiceRelativePath(value="/sigma-rpc/branch-list.app-rpc")
public interface BranchRPCService extends JSONSerializedRemoteService{
	
	/**
	 * Get branch by parameter
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @return pageResultHolder
	 */
	public PagedResultHolder<BranchDTO> getDataByParameter(SigmaSimpleQueryFilter[] filter, int page, int pageSize) throws Exception;
	
	/**
	 * Save or update data
	 * @param data
	 * @throws Exception
	 */
	public void saveOrUpdateBranch(Branch data) throws Exception; 
	
	/**
	 * Remove branch
	 * @param id
	 * @throws Exception
	 */
	public void remove(BigInteger id) throws Exception;
}