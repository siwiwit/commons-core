/**
 * 
 */
package id.co.gpsc.common.client.rpc.impl;

/**
 * @author Agus Gede Adipartha Wibawa
 * @since Sep 6, 2013, 4:57:22 PM
 *
 */
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.rpc.ManualJSONSerializeRPCService;
import id.co.gpsc.common.data.AppConfigurationDrivenDetaiResultHolder;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.SystemParamDrivenClass;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlDataRPCService;
import id.co.gpsc.common.data.app.DualControlDefinition;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;
import id.co.gpsc.common.data.app.HeaderDataOnlyCommonDualControlContainerTable;
import id.co.gpsc.common.data.app.SimpleMasterDataDualControlApprovalResult;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;

public class DualControlDataRPCServiceAsyncImpl extends ManualJSONSerializeRPCService<DualControlDataRPCService> implements DualControlDataRPCServiceAsync{

	@Override
	protected Class<DualControlDataRPCService> getServiceInterface() {
		return DualControlDataRPCService.class;
	}
	
		public void getDataById(java.math.BigInteger param0,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.app.CommonDualControlContainerTable> callback) {
		this.submitRPCRequestRaw( "getDataById", new Class<?>[]{
			java.math.BigInteger.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	public void rejectData(java.math.BigInteger param0,java.lang.String param1,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "rejectData", new Class<?>[]{
			java.math.BigInteger.class,java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void approveAndApplyData(id.co.gpsc.common.data.app.CommonDualControlContainerTable param0,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Void> callback) {
		this.submitRPCRequestRaw( "approveAndApplyData", new Class<?>[]{
			id.co.gpsc.common.data.app.CommonDualControlContainerTable.class, 
			
		}, 
		new Object[]{
			 param0, 
		}, 
		callback); 	
	}


	

	public void getTargetDataAsJSonString(java.lang.String param0,java.lang.String param1,com.google.gwt.user.client.rpc.AsyncCallback<java.lang.String> callback) {
		this.submitRPCRequestRaw( "getTargetDataAsJSonString", new Class<?>[]{
			java.lang.String.class,java.lang.String.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void submitDataForApproval(id.co.gpsc.common.data.app.CommonDualControlContainerTable param0,id.co.gpsc.common.data.app.DualControlEnabledOperation param1,com.google.gwt.user.client.rpc.AsyncCallback<java.math.BigInteger> callback) {
		this.submitRPCRequestRaw( "submitDataForApproval", new Class<?>[]{
			id.co.gpsc.common.data.app.CommonDualControlContainerTable.class,id.co.gpsc.common.data.app.DualControlEnabledOperation.class, 
			
		}, 
		new Object[]{
			 param0, param1, 
		}, 
		callback); 	
	}


	public void getDataRequiredApproval(java.lang.String param0,id.co.gpsc.common.data.query.SimpleQueryFilter[] param1,id.co.gpsc.common.data.query.SimpleSortArgument[] param2,int param3,int param4,com.google.gwt.user.client.rpc.AsyncCallback<id.co.gpsc.common.data.PagedResultHolder<id.co.gpsc.common.data.app.CommonDualControlContainerTable>> callback) {
		this.submitRPCRequestRaw( "getDataRequiredApproval", new Class<?>[]{
			java.lang.String.class,id.co.gpsc.common.data.query.SimpleQueryFilter[].class,id.co.gpsc.common.data.query.SimpleSortArgument[].class,int.class,int.class, 
			
		}, 
		new Object[]{
			 param0, param1, param2, param3, param4, 
		}, 
		callback); 	
	}

	@Override
	public void getDataForEditList(
			String objectFQCN,
			SimpleQueryFilter[] filters,
			SimpleSortArgument[] sortArguments,
			int pageSize,
			int page,
			AsyncCallback<PagedResultHolder<? extends DualControlEnabledData<?, ?>>> callback) {
		this.submitRPCRequestRaw( "getDataForEditList", new Class<?>[]{
				String.class , 
				SimpleQueryFilter[].class , 
				SimpleSortArgument[].class , 
				int.class , 
				int.class
				
				
			}, 
			new Object[]{
				 objectFQCN , filters , sortArguments,pageSize , page 
			}, 
			callback); 	
		
	}

	
	
	/**
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 */
	@Override
	public <DATA extends DualControlEnabledData<?, ?>> void getBulkApprovalDataDetails(
			BigInteger approvalDataId, int pageSize, int page,
			AsyncCallback<PagedResultHolder<DATA>> callback) {
		this.submitRPCRequestRaw( "getBulkApprovalDataDetails", new Class<?>[]{
				BigInteger.class , 
				int.class , 
				int.class
			}, 
			new Object[]{
				approvalDataId , pageSize , page 
			}, 
			callback); 	
		
	}

	@Override
	public void getMasterDataDualControlDefinitions(
			AsyncCallback<List<DualControlDefinition>> callback) {
		this.submitRPCRequestRaw( "getMasterDataDualControlDefinitions", new Class<?>[]{
				
			}, 
			new Object[]{
				
			}, 
			callback); 	
	}

	@Override
	public void approveAndApplyBulkData(BigInteger bulkDataId,
			AsyncCallback<Void> callback)  {
		this.submitRPCRequestRaw( "approveAndApplyBulkData", new Class<?>[]{
				BigInteger.class 
		}, 
		new Object[]{
			bulkDataId
		}, 
		callback); 	
	}

	@Override
	public void rejectBulkData(BigInteger bulkDataId, String rejectReason,
			AsyncCallback<Void> callback)  {
		this.submitRPCRequestRaw( "rejectBulkData", new Class<?>[]{
			BigInteger.class , 
			String.class
		}, 
		new Object[]{
			bulkDataId , 
			rejectReason
		}, 
		callback); 	
		
	}

	@Override
	public void submitMasterDataDataForApproval(
			CommonDualControlContainerTable dualControlledData,
			DualControlEnabledOperation operation,
			AsyncCallback<SimpleMasterDataDualControlApprovalResult> callback) {
		this.submitRPCRequestRaw( "submitMasterDataDataForApproval", new Class<?>[]{
				id.co.gpsc.common.data.app.CommonDualControlContainerTable.class,id.co.gpsc.common.data.app.DualControlEnabledOperation.class, 
				
			}, 
			new Object[]{
				dualControlledData, operation, 
			}, 
			callback); 	
		
	}

	@Override
	public void handleUploadedMasterFile(
			String uploadedDataKey,
			String targetClassFQCN,
			String remark,
			AsyncCallback<HeaderDataOnlyCommonDualControlContainerTable> callback) {
		this.submitRPCRequestRaw( "handleUploadedMasterFile", new Class<?>[]{
				String.class , 
				String.class , 
				String.class
			}, 
			new Object[]{
				uploadedDataKey, targetClassFQCN, remark
			}, 
			callback); 	
		
		
	}

	@Override
	public void rejectAllWaitingApprovalData(Date latestDateToFetch,
			String remarkForAllRejected, AsyncCallback<Void> callback) {
		this.submitRPCRequestRaw( "rejectAllWaitingApprovalData", new Class<?>[]{
				Date.class , 
				String.class 
			}, 
			new Object[]{
				latestDateToFetch, remarkForAllRejected
			}, 
			callback); 	
		
	}
	
	
	@Override
	public <D extends SystemParamDrivenClass<?, ?>> void getSytemConfigurationDrivenData(String fqcn,
			AsyncCallback<AppConfigurationDrivenDetaiResultHolder<D>> callback) {
		this.submitRPCRequestRaw( "getSytemConfigurationDrivenData", new Class<?>[]{
				String.class 
			}, 
			new Object[]{
				fqcn
			}, 
			callback); 	
	}






	

}