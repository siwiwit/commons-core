package id.co.gpsc.common.client.security.rpc;

import id.co.gpsc.common.client.security.rpc.impl.BranchRPCServiceAsyncImpl;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.dto.BranchDTO;

import java.math.BigInteger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BranchRPCServiceAsync {
	
	public static class Util {
		private static BranchRPCServiceAsync instance ; 
		
		public static BranchRPCServiceAsync getInstance() {
			if (instance==null){
				instance = GWT.create(BranchRPCServiceAsyncImpl.class);
//				CommonGlobalVariableHolder.getInstance().fixTargetUrl((ServiceDefTarget)instance);
			}			 
			return instance;
		}
	}
	
	/**
	 * Get branch by parameter
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param callback
	 */
	void getDataByParameter(SimpleQueryFilter[] filter, int page, int pageSize, AsyncCallback<PagedResultHolder<BranchDTO>> callback);
	
	/**
	 * Save or update data branch
	 * @param data
	 */
	void saveOrUpdateBranch(Branch data, AsyncCallback<Void> callback);
	
	/**
	 * remove data branch
	 * @param id
	 * @param callback
	 */
	void remove(BigInteger id, AsyncCallback<Void> callback);
}