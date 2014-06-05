package id.co.gpsc.common.client.dualcontrol;


import id.co.gpsc.common.client.common.LayeredLogicUploadSubmitHandler;
import id.co.gpsc.common.client.control.CommonChainedUploadHandler;
import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.control.SimpleFileUpload;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.advance.TextBoxAreaWithLabel;
import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.HeaderDataOnlyCommonDualControlContainerTable;
import id.co.gpsc.common.exception.BadBulkUploadDataException;
import id.co.gpsc.jquery.client.grid.IReloadGridCommand;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * 
 * panel untuk upload dual controlled master data
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class UploadDualControlledMasterDataPanel extends BaseSigmaComposite {

	private static UploadDualControlledMasterDataPanelUiBinder uiBinder = GWT
			.create(UploadDualControlledMasterDataPanelUiBinder.class);

	
	@UiField SimpleFileUpload upldExcelFile ; 
	@UiField HTMLPanel pnlHeaderContainerPanel ; 
	@UiField ExtendedButton btnUpload;
	@UiField ExtendedButton btnBatal; 
	@UiField FormPanel formUpload ; 
	@UiField Hidden hidnHandledFQCN ; 
	@UiField TextBoxAreaWithLabel txtRemark ; 
	
	
	
	/**
	 * command untuk reload current grid
	 */
	private IReloadGridCommand reloadGridCommand ; 
	
	
	interface UploadDualControlledMasterDataPanelUiBinder extends
			UiBinder<Widget, UploadDualControlledMasterDataPanel> {
	}

	
	
	private Class<? extends DualControlEnabledData<?, ?>> handledClass ; 
	public UploadDualControlledMasterDataPanel(final Class<? extends DualControlEnabledData<?, ?>> handledClass  , IReloadGridCommand reloadGridCommand ) {
		this.handledClass = handledClass ; 
		try {
			initWidget(uiBinder.createAndBindUi(this));
			this.reloadGridCommand  = reloadGridCommand ;
			formUpload.setMethod("POST");
			formUpload.setEncoding(FormPanel.ENCODING_MULTIPART);
			formUpload.setAction(CommonClientControlUtil.getInstance().getApplicationBaseUrl() + "common/upload-common-file.jsp");
			hidnHandledFQCN.setValue(handledClass.getName());
			
			formUpload.addSubmitCompleteHandler(new LayeredLogicUploadSubmitHandler() {
				
				@Override
				protected void handlerOnUploadSuccessHandler(String uploadedFileKey) {
					runProccessUploadedFile(uploadedFileKey);
				}
				@Override
				protected void handlerOnUploadFailedHandler(String errorMessage) {
					Window.alert("gagal upload file. error  : " + errorMessage );
				}
			}); 
		} catch (Exception e) {
			Window.alert(e.getMessage());
		}
		
	} 
	@UiHandler(value="btnBatal")
	  void onButtonBatalClick( ClickEvent evt ){
		MainPanelStackControl.getInstance().closeCurrentPanel();
		reloadGridCommand.reload();
		
	}
	
	@UiHandler(value="btnUpload")
	  void onButtonUploadClick( ClickEvent evt ){
		if (upldExcelFile.getFilename() == null || upldExcelFile.getFilename().isEmpty() ){
			Window.alert("mohon pilih file yang akan di upload");
			return ; 
		}
		upldExcelFile.uploadFile(new CommonChainedUploadHandler() {
			
			@Override
			public void onUploadFileSuccess(String fileKey) {
				runProccessUploadedFile(fileKey);
				
			}
			
			@Override
			public void onUploadFileFailed(String messsage, String filePath) {
				Window.alert("gagal upload file "+filePath+". error  : " + messsage );
				
			}
		});
		
	}
	
	
	private void runProccessUploadedFile (String uploadedFileKey ) {
		DualControlDataRPCServiceAsync
		.Util.getInstance()
		.handleUploadedMasterFile(uploadedFileKey, handledClass.getName(), txtRemark.getValue(), new AsyncCallback<HeaderDataOnlyCommonDualControlContainerTable>() {
			
			@Override
			public void onSuccess(HeaderDataOnlyCommonDualControlContainerTable result) {
				Window.alert("upload berhasil , data dalam proses approval dengan nomor referensi : " +  result.getReffNo() );
				MainPanelStackControl.getInstance().closeCurrentPanel();
				UploadDualControlledMasterDataPanel.this.reloadGridCommand.reload();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				
				if ( caught instanceof BadBulkUploadDataException){
					BadBulkUploadDataException badBlkExc = (BadBulkUploadDataException) caught ; 
					String msg = "File di upload ada kesalahan, silakan di cek berikut ini:<br/>" ;
					msg +="<div style=\"overflow: scroll; width: auto; height: 400px;\"><div>"
							+ " <span style=\"text-align: left;\"><ol>" ;
					
					for ( String scn : badBlkExc.getInvalidDataMessages()) {
						msg +="<li>" + scn.replaceAll("\\n", "<br />") + "</li>" ;
					}
					msg +="</ol>";
					msg +="<ol></ol></span>"
							+ "</div></div>"
							+ "<button onclick='$.unblockUI()'>Tutup</button>" ; 
					blockEntirePage(msg);
					//JQueryUtils.getInstance().unblockEntirePage(10000);
					return ; 
					
				}else {
					Window.alert("gagal memproses file upload.error : " + caught.getMessage());
				}
				
			}
		});
	}
	
	private native void blockEntirePage (   String messageForBlocked  ) /*-{
	$wnd.$.blockUI({
		message:messageForBlocked , 
		css : { top: "100px" }
		});
	}-*/;
	
	/**
	 * Menaruh widget untuk title. ini anda bisa pergunakan untuk title + brief desc
	 */
	public void setTitleWidget ( Widget headerTitlePanel ) {
		if ( pnlHeaderContainerPanel.getWidgetCount()>0)
			pnlHeaderContainerPanel.clear();
		if ( headerTitlePanel != null)
			pnlHeaderContainerPanel.add(headerTitlePanel);
	}
}
