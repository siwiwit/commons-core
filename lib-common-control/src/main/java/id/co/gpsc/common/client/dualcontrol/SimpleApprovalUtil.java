package id.co.gpsc.common.client.dualcontrol;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Window;

public final  class SimpleApprovalUtil implements ISimpleApprovalPanelManager{
	
	
	
	private static SimpleApprovalUtil instance ; 
	
	
	
	private Map<String, ISimpleApprovalPanelGenerator> approvalPanelMap ; 
	
	private SimpleApprovalUtil(){
		this.approvalPanelMap = new HashMap<String, ISimpleApprovalPanelGenerator>(); 
		
	}
	
	
	public static SimpleApprovalUtil getInstance() {
		if ( instance == null)
			instance = new SimpleApprovalUtil(); 
		return instance;
	}


	@Override
	public void register(ISimpleApprovalPanelGenerator approvalHandler) {
		approvalPanelMap.put(approvalHandler.getHandledClass().getName(), approvalHandler);
		
	}


	@Override
	public ISimpleApprovalPanel getHandler(String fqcn) {
		try{
			return approvalPanelMap.get(fqcn).instantiatePanel();
		}catch ( Exception exc ){
			Window.alert("gagal instantiate panel  approval("+fqcn+").error di laporkan : " + exc.getMessage());
			return null ; 
		}
		
	}

}
