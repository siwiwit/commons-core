package id.co.gpsc.common.client.dualcontrol;


import java.util.HashMap;
import java.util.Map;

import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.rpc.SimpleAsyncCallback;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;
import id.co.gpsc.common.data.app.SimpleMasterDataDualControlApprovalResult;



/**
 * utils utnuk dual control
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public final class DualControlUtil implements IDualControlEditorManager{
	
	
	
	
	private static DualControlUtil  instance ; 
	
	
	
	
	Map<String, IDualControlEditorGenerator<?>> singleApprovalHandler = new HashMap<String, IDualControlEditorGenerator<?>>() ; 
	
	
	Map<String, IDualControlMultipleDataEditorGenerator <?>> multipleApprovalHandler = new HashMap<String, IDualControlMultipleDataEditorGenerator<?>>() ; 
	
	
	
	/**
	 * ini di trigger pada saat data berubah. jadinya daftar approval perlu di reload
	 */
	@SuppressWarnings("rawtypes")
	private  DataChangedEventHandler approveDoneHandler ; 
	
	
	
	private DualControlUtil() {}
	
	
	
	public static DualControlUtil getInstance() {
		if ( instance ==null)
			instance = new DualControlUtil(); 
		return instance;
	}
	/**
	 * Submit data untuk di approve
	 **/
	public void submitDataForApproval (DualControlEnabledOperation operation , String remarkOnData , DualControlEnabledData< ?, ?> data , SimpleAsyncCallback<SimpleMasterDataDualControlApprovalResult> callback) {
		String jsonString = data.generateJSONString(); 
		CommonDualControlContainerTable data4RPC = new CommonDualControlContainerTable();
		data4RPC.setTargetObjectFQCN(data.getClass().getName());
		data4RPC.setJsonData(jsonString); 
		data4RPC.setKey1(data.getKey1AsString());
		data4RPC.setKey2(data.getKey2AsString());
		
		//add by dode
		//menambahkan set current dual control id
		String approvalStatusCode ;
		if ( DualControlEnabledOperation.INSERT.equals(operation)){
			approvalStatusCode = DualControlApprovalStatusCode.WAITING_APPROVE_CREATE.toString();
			data.setApprovalStatus(DualControlApprovalStatusCode.WAITING_APPROVE_CREATE);
		}
		else if ( DualControlEnabledOperation.UPDATE.equals(operation)){
			approvalStatusCode = DualControlApprovalStatusCode.WAITING_APPROVE_UPDATE.toString();
			data.setApprovalStatus(DualControlApprovalStatusCode.WAITING_APPROVE_UPDATE);
		}
		else{
			approvalStatusCode = DualControlApprovalStatusCode.WAITING_APPROVE_DELETE.toString();
			data.setApprovalStatus(DualControlApprovalStatusCode.WAITING_APPROVE_DELETE);
		}
		
		data4RPC.setLatestRemark(remarkOnData);
		data4RPC.setApprovalStatus(approvalStatusCode);
		data4RPC.setId(data.getCurrentCommonDualControlId());
		data4RPC.setOperationCode(operation.toString());
		DualControlDataRPCServiceAsync.Util.getInstance().submitMasterDataDataForApproval(data4RPC, operation, callback);
	} 
	
	
	
	
	
	/**
	 * approve data
	 * @param operation operasi : add, edit, delete
	 * @param data data yang perlu di approve
	 * @param callback callbak kalau proses ini selesai
	 **/
	public void approveData (DualControlEnabledOperation operation ,  CommonDualControlContainerTable data , SimpleAsyncCallback<Void> callback) {
		DualControlDataRPCServiceAsync.Util.getInstance().approveAndApplyData(data, callback);
	}
	
	
	
	
	
	
	
	
	/**
	 * membaca dual control handler. ini di pergunakan untuk handle proses approve
	 **/
	@SuppressWarnings("unchecked")
	public IDualControlEditor<?> getDualControlHandler ( String fqcn ){
		if (! singleApprovalHandler.containsKey(fqcn))
			return null ;
		IDualControlEditor<?> pnl =  this.singleApprovalHandler.get(fqcn).instantiateEditorPanel();
		if ( approveDoneHandler!= null)
			pnl.addDataChangeHandlers(approveDoneHandler); 
		return pnl; 
	}
	
	
	 



	



	@Override
	@SuppressWarnings("unchecked")
	public IDualControlMultipleDataEditor<?> getMultipleItemHandler(String fqcn) {
		if ( !multipleApprovalHandler.containsKey(fqcn))
			return null ; 
		IDualControlMultipleDataEditor<?> pnl = multipleApprovalHandler.get(fqcn).instantiatePanel();
		if ( approveDoneHandler!= null)
			pnl.addDataChangeHandlers(approveDoneHandler); 
		return pnl ; 
	}



	
	/**
	 * ini di trigger pada saat data berubah. jadinya daftar approval perlu di reload
	 */
	public void setApproveDoneHandler(@SuppressWarnings("rawtypes") DataChangedEventHandler approveDoneHandler) {
		this.approveDoneHandler = approveDoneHandler;
	}



	@Override
	public void registerDualControlHandler(
			IDualControlEditorGenerator<?> panelGenerator) {
		this.singleApprovalHandler.put(panelGenerator.getHandledClass().getName()	, panelGenerator); 
		
	}



	@Override
	public void registerDualControlMultipleHandler(
			IDualControlMultipleDataEditorGenerator<?> generator) {
		multipleApprovalHandler.put(generator.getHandledClass().getName(), generator) ; 
		
	}



	
	
	

}
