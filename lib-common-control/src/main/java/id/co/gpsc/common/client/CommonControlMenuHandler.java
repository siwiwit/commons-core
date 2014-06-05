package id.co.gpsc.common.client;

import id.co.gpsc.common.client.dualcontrol.MasterDataUnifiedApprovalMainPanel;
import id.co.gpsc.common.client.dualcontrol.MasterDataUnifiedRejectMainPanel;
import id.co.gpsc.common.client.security.IMenuHandlerPanelGenerator;
import id.co.gpsc.common.client.security.MenuHandlerPanelGeneratorGroup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;

/**
 *
 * panel generator untuk menu/ panel di level common controls
 *
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class CommonControlMenuHandler extends MenuHandlerPanelGeneratorGroup{

	
	
	
	private final IMenuHandlerPanelGenerator<?>[] handlers =new IMenuHandlerPanelGenerator<?>[] {
			/*new IMenuHandlerPanelGenerator<UnifiedApprovalMainPanel>(){
				@Override
				public String getMenuCode() {
					return "MASTER::APPROVAL-MAIN-PANEL";
				}
				@Override
				public UnifiedApprovalMainPanel instantiateWidget() {
					return GWT.create(UnifiedApprovalMainPanel.class);
				}
				@Override
				public void restoreWidgetToDefaultState(
						UnifiedApprovalMainPanel widgetToRestore) {
					// FIXME : masukan proses restore di sni
				}

				@Override
				public String getDescriptionLabel() {
					return "Daftar approval";
				}
				
			}*/
			
			
			new IMenuHandlerPanelGenerator<MasterDataUnifiedRejectMainPanel>() {

				@Override
				public String getMenuCode() {
					return "MASTER::REJECT-MAIN-PANEL";
				}

				@Override
				public MasterDataUnifiedRejectMainPanel instantiateWidget() {
					MasterDataUnifiedRejectMainPanel retval = new MasterDataUnifiedRejectMainPanel(); 
					return retval;
				}

				@Override
				public void restoreWidgetToDefaultState(
						MasterDataUnifiedRejectMainPanel widgetToRestore) {
					widgetToRestore.adjustScreenWidth();
					
				}

				@Override
				public String getDescriptionLabel() {
					return "Daftar Reject";
				}
				
				
			} , 
			
			
			
			new IMenuHandlerPanelGenerator<id.co.gpsc.common.client.dualcontrol.MasterDataUnifiedApprovalMainPanel>() {

				@Override
				public String getMenuCode() {
					return "MASTER::APPROVAL-MAIN-PANEL";
				}

				@Override
				public MasterDataUnifiedApprovalMainPanel instantiateWidget() {
					final MasterDataUnifiedApprovalMainPanel retval = GWT.create(MasterDataUnifiedApprovalMainPanel.class)  ; 
					new Timer() {
						
						@Override
						public void run() {
							retval.adjustScreenWidth();
							
						}
					}.schedule(100);
					return retval;
				}

				@Override
				public void restoreWidgetToDefaultState(
						MasterDataUnifiedApprovalMainPanel widgetToRestore) {
					//widgetToRestore.adjustScreenWidth();
					
				}

				@Override
				public String getDescriptionLabel() {
					return "Daftar approval";
				}
				
			}
			
			/*new IMenuHandlerPanelGenerator<CommonSimpleApprovalListPanel>() {

				@Override
				public String getMenuCode() {
					return "MASTER::APPROVAL-MAIN-PANEL";
				}

				@Override
				public CommonSimpleApprovalListPanel instantiateWidget() {
					return new CommonSimpleApprovalListPanel();
				}

				@Override
				public void restoreWidgetToDefaultState(
						CommonSimpleApprovalListPanel widgetToRestore) {
					
					
				}

				@Override
				public String getDescriptionLabel() {
					return "List Data Untuk Diapprove";
				}
			}*/
	};
	
	
	
	@Override
	protected IMenuHandlerPanelGenerator<?>[] getGenerators() {
		return handlers;
	}
	
	//

}
