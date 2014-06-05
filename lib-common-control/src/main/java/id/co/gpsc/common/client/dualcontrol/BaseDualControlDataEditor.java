package id.co.gpsc.common.client.dualcontrol;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.ClosePanelCommand;
import id.co.gpsc.common.client.control.EditorOperation;
import id.co.gpsc.common.client.control.ExpensivePanelGenerator;
import id.co.gpsc.common.client.control.MainPanelStackControl;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.form.advance.TextBoxAreaWithLabel;
import id.co.gpsc.common.client.form.exception.CommonFormValidationException;
import id.co.gpsc.common.client.rpc.DualControlDataRPCServiceAsync;
import id.co.gpsc.common.client.rpc.SigmaAsyncCallback;
import id.co.gpsc.common.client.widget.BaseEditorPanel;
import id.co.gpsc.common.client.widget.EditorState;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;
import id.co.gpsc.common.data.app.SimpleMasterDataDualControlApprovalResult;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.common.util.ObjectGeneratorManager;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;
import id.co.gpsc.jquery.client.util.JQueryUtils;



/**
 * base class untuk edit data dual control
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public abstract class BaseDualControlDataEditor<KEY extends Serializable ,  DATA extends DualControlEnabledData<DATA, KEY>> extends BaseEditorPanel<DATA> implements IDualControlEditor<DATA>{

	
	
	
	
	/**
	 * reference balik dari editor ke main panel. sekadar untuk helper
	 */
	private BaseDualControlMainPanel<KEY, DATA> mainPanelReference ;
	
	/**
	 * operasi yang di lakukan apa
	 **/
	private DualControlEnabledOperation dualControlEnabledOperation ; 
	
	
	/**
	 * ke isi kalau dalam proses approve. data di buka dari common dual controll data
	 **/
	private CommonDualControlContainerTable currentCommonDualControlContainerData ; 
	
	
	/**
	 * state dari dual control editor state
	 **/
	private DualControlEditorState  dualControlEditorState   ; 
	
	
	
	/**
	 * panel tempat menaruh title panel
	 */
	private SimplePanel titlePanel ; 
	
	
	private static final Command DO_NOTHING_COMMAND = new Command() {
		
		@Override
		public void execute() {
			
			
		}
	};
	
	
	
	
	/**
	 * data change handlerls
	 **/
	private List<DataChangedEventHandler<DATA>> dataModifiedHandlers = new ArrayList<DataChangedEventHandler<DATA>>(); 
	
	
	
	
	
	
	
	
	
	
	/**
	 * textarea untuk remark. ini di tampilkan dalam kasus reject
	 **/
	protected TextBoxAreaWithLabel remark ; 
	
	
	
	/**
	 * remark dari approval. ini wajib di isikan dalam kasus reject
	 */
	protected TextBoxAreaWithLabel approvalRemark ;
	
	/**
	 * container luar. ini untuk menaruh shared item. misal : remark text area , tombol 
	 **/
	protected FlexTable outmostContainerTable  ;
	
	protected ExtendedButton btnCancel ; 
	protected ExtendedButton btnApprove ;
	protected ExtendedButton btnSaveForApproval ;
	protected ExtendedButton btnReject ;
	protected ExtendedButton btnRevice ;
	protected ExtendedButton btnClose ; 
	
	
	
	/**
	 * tombol lanjut. ini untuk memproses berikut nya
	 */
	protected ExtendedButton btnNext ; 
	/**
	 * tombol untuk kembali ke entry
	 */
	protected ExtendedButton btnReentryBack ; 
	
	
	
	/**
	 * ini index dari remark. kalau reject, remark itu mandatory
	 **/
	private static final int REMARK_ROW_INDEX = 2 ; 
	
	public BaseDualControlDataEditor(){
		super () ; 
		
		
	}
	 
	
	/**
	 * class yang di handle class ini
	 **/
	public abstract Class<DATA> getProccessedClass () ;
	
	
	
	
	
	/**
	 * register change handler
	 **/
	public HandlerRegistration addDataChangeHandlers (final DataChangedEventHandler<DATA> handler) {
		if ( dataModifiedHandlers.contains(handler))
			return null ;
		dataModifiedHandlers.add(handler);
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				dataModifiedHandlers.remove(handler);
				
			}
		};
	}
	
	
	
	/**
	 * propagasi data change
	 **/
	protected void fireChangeHandler ( DATA editedData , EditorOperation operation ){
		for ( DataChangedEventHandler<DATA> scn : this.dataModifiedHandlers){
			if ( scn== null)
				continue ; 
			scn.handleDataChange(editedData, operation);
		}
	}
	
	
	/**
	 * command untuk close panel
	 **/
	protected ClosePanelCommand closePanelCommand = new ClosePanelCommand() {
		
		@Override
		public void closePanel() {
			MainPanelStackControl.getInstance().closePanel(BaseDualControlDataEditor.this);
		}
	}; 
	
	
	
	
	/**
	 * message standard untuk proses konfirmasi pembatalan operasi
	 **/
	protected abstract String getDefaultCancelButtonUserConfirmationMessage () ;
	
	
	/**
	 * message kalau konfirmasi. message yang di tampilkan ke user apa
	 **/
	protected abstract String getDefaultApproveConfirmationMessage()  ; 
	
	
	/**
	 * i18 message untuk konfirmasi approval data
	 **/
	protected abstract String getApproveConfirmationMessageI18nKey()  ;
	
	
	
	/**
	 * key i18n key untuk konfirmasi  proses pembatalan(penekanan tombol cancel/batal) 
	 **/
	protected abstract String getCancelButtonUserConfirmationMessageI18nKey () ;
	
	
	/**
	 * message kalau mau reject 
	 **/
	protected abstract String getDefaultRejectConfirmationMessage () ;
	
	
	
	
	/**
	 * message confirmasi reject
	 **/
	protected abstract String getRejectConfirmationMessageI18NKey () ;
	
	
	
	/**
	 * label default untuk tomblo close
	 **/
	protected abstract String getDefaultCloseButtonLabel  () ; 
	
	/**
	 * key internalization untuk close button. 
	 **/
	protected abstract String getCloseButtonLabelI18nKey () ; 
	/**
	 * key i18 code untuk tombol save
	 **/
	protected abstract String getSaveButtonLabelI18nKey (); 
	
	/**
	 * default Save Message
	 **/
	protected String getDefaultSaveButtonLabel () {
		return "Save"; 
	}
	
	
	/**
	 * i18n Code utnuk tombol batal
	 **/
	protected abstract String getCancelButtonLabelI18nCode ()  ; 
	/**
	 * label default batal
	 **/
	protected String getDefaultCancelButtonLabel () {
		return "Cancel"; 
	}
	/**
	 * i18key back button. ini pada panel konfirmasi. 
	 */
	protected abstract String getBackButtonLabelI18nCode () ;
	
	/**
	 * default back button. ini pada panel konfirmasi
	 */
	protected abstract String getBackButtonDefaultLabel () ;
	/**
	 * default label untuk tombol next
	 */
	protected abstract String getNextButtonDefaultLabel () ;
	
	
	/**
	 * key i18 untuk next button
	 */
	protected abstract String getNextButtonLabelI18nKey () ;
	
	/**
	 * i18n code utnuk label approve button
	 **/
	protected abstract String getApproveButtonLabelI18nCode () ; 
	
	
	/**
	 * label default  tombol approve
	 **/
	protected String getDefaultApproveButtonLabel () {
		return "Approve"; 
	}

	
	
	/**
	 * i18n code utnuk label reject button
	 **/
	protected abstract String getRejectButtonLabelI18nCode () ; 
	
	
	/**
	 * label default  tombol approve
	 **/
	protected String getDefaultRejectButtonLabel () {
		return "Reject"; 
	}
	
	
	/**
	 * i18n code utnuk label revice button
	 **/
	protected abstract String getReviceButtonLabelI18nCode () ; 
	
	
	/**
	 * label default  tombol revice
	 **/
	protected String getDefaultReviceButtonLabel () {
		return "Revice"; 
	}
	
	
	
	
	
	
	
	
	/**
	 * message standard kalau gagal membaca data. anda perlu menyediakan implementasi anda masing-masing untuk message standard. message ini di override kalau misalnya i18n code untuk message ini di sediakan.pls refer pada method : {@link #getMessageFailOpenDataI18nCode()} 
	 **/
	protected abstract String getDefaultMessageFailOpenData(BigInteger dataId) ; 
	
	/**
	 * i18N code untuk kasus pembacaan data gagal
	 **/
	protected abstract String getMessageFailOpenDataI18nCode() ;
	
	
	/**
	 * default message kalau proses save for approval gagal
	 **/
	protected abstract String getDefaultMessageValidateForApprovalFail() ;
	
	
	
	/**
	 * kode i18 n code untuk kasus gagal validasi untuk proses approval
	 **/
	protected abstract String getValidateForApprovalFailMessageI18nCode() ;
	
	
	
	/**
	 * handler kalau proses approval gagal
	 **/
	protected   Command getApprovalFailureHandler () {
		return DO_NOTHING_COMMAND;
	}
	
	
	
	
	
	
	/**
	 * message kalau approve sukses
	 **/
	protected abstract String getDefaultApproveSuccessMessage () ; 
	
	/**
	 * key i18 kalau proses approve selesai sukses
	 **/
	protected abstract String getApproveSuccessMessageI18nKey () ;
	
	/**
	 * use case nya : <br/>
	 * <ol>
	 * <li>Data gagal di buka</li>
	 *  <li>panel harus switch ke mana ini adalah tanggung jawab dari command ini</li>
	 *  </ol>
	 *  default : no action
	 **/
	protected   Command getCommandOnOpenDataFailedHandler () {
		return DO_NOTHING_COMMAND ; 
	}
	
	
	
	
	
	
	
	
	/**
	 * ini kalau proses save for approval gagal. normal nya : do nothing
	 **/
	protected   Command getCommandOnSaveApprovalFailHandler() {
		return DO_NOTHING_COMMAND;
	}
	
	
	
	
	
	
	
	/**
	 * ini untuk render message kalau approval sudah selesai
	 */
	protected abstract String getDefaultMessageOnSaveApprovalDoneHandler(SimpleMasterDataDualControlApprovalResult approvalResult ) ;
	
	
	/**
	 * message kalau approve data gagal
	 * @param dataId id dari data yang di edit
	 * @param exception exception yang di kirim dari server
	 **/
	protected abstract String getMessageOnApproveDataFailure (BigInteger dataId , Throwable exception) ; 
	
	
	
	
	
	
	/**
	 * ini membaca message dalam kasus proses save for approval gagal
	 **/
	protected abstract String getMessageOnSaveForApprovalFailMessage (Throwable failure  ); 
	
	
	
	
	
	
	/**
	 * 18n code untuk notifikasi user kalau data data untuk di approve selesai disimpan
	 **/
	protected  abstract String getsaveForApprovalDoneHandlerI18nMessage()  ; 
	
	
	
	/**
	 * i18n key untuklabel remark. remark di mandatory dalam kasus reject
	 **/
	protected abstract String getRemarkTextAreaLabelI18nKey () ; 
	
	/**
	 * default remark label
	 **/
	protected abstract String getDefaultRemarkTextAreaLabel() ;
	
	
	
	
	/**
	 * catatan persetujuan/ tolak
	 */
	protected   String getDefaultApprovalRemarkTextAreaLabel() {
		return "Catatan persetujuan/tolak"; 
	}
	
	
	
	/**
	 * ini alert message yang akan di keluarkan kalau user memilih reject data dan tidak mengisikan remark. 
	 * Isian remark wajib
	 */
	protected String getDefaultRejectWithNoRemarkWarningMessage () {
		return "Anda memutuskan untuk menolak data. Anda wajib menyediakan alasan kenapa data di tolak. Silakan isikan isian :  " + getDefaultApprovalRemarkTextAreaLabel() ; 
	}
	
	
	
	
	/**
	 * ini untuk switch ke dalam mode edit data. bisa add new atau edit. Hati-hati dengan edit, beberapa field mungkin not-editable. pergunakan variable <i>addNew</i> untuk memeriksa add/ edit 
	 **/
	protected abstract void switchEditablePanel (boolean addNew) ;
	
	
	/**
	 * membuka data dengan berdasar dual control data. jadinya yang di kirimkan adalah ID dari dual control
	 * @param dualControlDataId id dari table {@link CommonDualControlContainerTable}. 
	 * @param makeReadonly editor di jadikan readonly atau editable
	 **/
	public void openDataByDualControlId (final BigInteger dualControlDataId,final Command commandAfterLoadComplete ) {
		DualControlDataRPCServiceAsync.Util.getInstance().getDataById(dualControlDataId, new SigmaAsyncCallback<CommonDualControlContainerTable>() {
			@Override
			public void onSuccess(CommonDualControlContainerTable response) {
				currentCommonDualControlContainerData = response ;
				String rawJson =  response.getJsonData(); 
				//DATA data= null;
				try {
					DATA sample =  ObjectGeneratorManager.getInstance().instantiateSampleObject(getProccessedClass().getName());
					currentData = sample.instantiateFromJSON(SharedServerClientLogicManager.getInstance().getJSONParser().parseJSON(rawJson));
					
				} catch (Exception e) {
					if ( currentData== null){
						Window.alert("maaf, data tidak di temukan untuk id: " + dualControlDataId + ".pesan error : " + e.getMessage());
						e.printStackTrace();
					}
				}
				remark.setValue(response.getLatestRemark());
				renderDataToControl(currentData, editorState);
				currentData.setApprovalStatus( DualControlApprovalStatusCode.generateFromRawString(  response.getApprovalStatus() ) );
				if (DualControlApprovalStatusCode.WAITING_APPROVE_BULK.equals(currentData.getApprovalStatus())){
					Widget header =   new HTML("");
					titlePanel.setWidget(header);
				}else {
					Widget header =  generateApproveItemTitlePanel(currentData);
					if ( header== null)
						header = new HTML("");
					titlePanel.setWidget(header);
				}
				if ( commandAfterLoadComplete!= null)
					commandAfterLoadComplete.execute();
				
			}

			@Override
			protected void customFailurehandler(Throwable caught) {
				String i18nCode = getMessageFailOpenDataI18nCode() ;
				String messageDefault = getDefaultMessageFailOpenData(dualControlDataId);
				String messageToRender = messageDefault ; 
				if ( i18nCode!= null&& i18nCode.length()>0){
					String messagei18 =  I18Utilities.getInstance().getInternalitionalizeText(i18nCode, messageDefault , new Object[]{dualControlDataId});
					if ( messagei18!=null ){
						messageToRender = messagei18 ;
					}
				}
				Window.alert(messageToRender); 
				Command cmd =  getCommandOnOpenDataFailedHandler();
				if ( cmd!= null)
					cmd.execute(); 
			}
		});
	}
	
	
	/**
	 * simpan data untuk proses approval.yang di lakukan di sini adalah : 
	 * <ol>
	 * <li>run proses validasi custom(ini tanggung jawab dari masing-masing). validasi custom di lakukan di method</li>
	 * <li>membaca data dari control</li>
	 * 
	 * <li></li>
	 * </ol>
	 **/
	protected void saveForApproval () {
		try {
			validateSaveForApproval(); 
		} catch (CommonFormValidationException e) {
			String message = I18Utilities.getInstance().getInternalitionalizeText(getValidateForApprovalFailMessageI18nCode(), getDefaultMessageValidateForApprovalFail()); 
			Window.alert(message + ",\nInfo tambahan : " + e.getMessage()); 
			return ; 
		}
		final DATA editedData =  getCurrentData() ;
		fetchDataFromControlToObject(editedData, editorState); 
		// kirim ke RPC
		sendRPCRequestFOrSaveForApproval(editedData);
	}
	
	
	
	
	
	
	/**
	 * worker untuk kirim RPC request ke server. override di sini kalau berencana mengganti route dari data
	 */
	protected void sendRPCRequestFOrSaveForApproval (final DATA editedData) {
		JQueryUtils.getInstance().blockEntirePage("mengirim data. mohon menunggu");
		DualControlUtil.getInstance().submitDataForApproval(dualControlEnabledOperation,remark.getValue() ,  editedData, new SigmaAsyncCallback<SimpleMasterDataDualControlApprovalResult>() {
			@Override
			public void onSuccess(SimpleMasterDataDualControlApprovalResult result) {
				JQueryUtils.getInstance().unblockEntirePage();
				String msg = I18Utilities.getInstance().getInternalitionalizeText(getsaveForApprovalDoneHandlerI18nMessage(), getDefaultMessageOnSaveApprovalDoneHandler(result), new Object[]{result});
				Window.alert(msg); 
				closePanelCommand.closePanel(); 
				fireChangeHandler(editedData, DualControlEnabledOperation.INSERT.equals(dualControlEnabledOperation)? EditorOperation.ADD : EditorOperation.EDIT);
				 
			}
			@Override
			protected void customFailurehandler(Throwable caught) {
				JQueryUtils.getInstance().unblockEntirePage();
				Window.alert(getMessageOnSaveForApprovalFailMessage(caught));
				getCommandOnSaveApprovalFailHandler().execute();
				
			}
		}); 
	}
	
	/**
	 * ini validasi dalam proses approval. anda wajib memeriksa setiap elemen dalam form. kalau ada yang salah, anda perlu throw CommonFormValidationException
	 **/
	protected abstract void validateSaveForApproval()throws CommonFormValidationException;
	
	
	 
	
	
	/**
	 * menolak proses approval
	 **/
	protected void rejectItem (String rejectReason) {
		
	}
	
	
	
	/**
	 *
	 **/
	@Override
	public void addAndEditNewData(DATA data) {
		remark.setVisible(true); 
		
		setDualControlEnabledOperation(  DualControlEnabledOperation.INSERT);
		setDualControlEditorState(DualControlEditorState.CREATE_FOR_APPROVAL);
		super.addAndEditNewData(data);
		if ( DualControlEditorState.CREATE_FOR_APPROVAL.equals(   getDualControlEditorState() )){
			new Timer() {
				@Override
				public void run() {
					switchEditablePanel(true);
				}
			}.schedule(100);
			
			remark.setVisible(true);
			remark.restoreControl();
			approvalRemark.setVisible(false);
			
		}else if (  DualControlEditorState.APPROVAL.equals(   getDualControlEditorState()) ){
			remark.setVisible(true);
			remark.restoreControl();
			approvalRemark.setVisible(true);
		}
		
		Widget w =  generateNewDataTitlePanel(); 
		if ( w == null)
			w = new HTML(""); 
		titlePanel.setWidget(w);
	}
	
	
	
	
	/**
	 * extrat method {@link #renderDataToControl(Object, EditorState)} agar bisa di expose
	 */
	public void renderDataToEditorControl(DATA data, EditorState editorState) {
		this.renderDataToControl(data, editorState);
		
	}
	
	@Override
	public void editExistingData(DATA data) {
		//FIXME: 1. ini musti di pastikan sudah ada dalam database
		//FIXME: 2. ini musti di pastikan tidak dalam state edit. kalau ada , maka operator yang di pergunakan adalah id.co.sigma.common.client.dualcontrol.BaseDualControlDataEditor.openDataByDualControlId(BigInteger)
		super.editExistingData(data);
		remark.setVisible(true); 
		remark.restoreControl();
		approvalRemark.setVisible(false);
		setDualControlEnabledOperation(  DualControlEnabledOperation.UPDATE);
		data.setApprovalStatus(DualControlApprovalStatusCode.WAITING_APPROVE_UPDATE);
		//if ( DualControlEditorState.CREATE_FOR_APPROVAL.equals(   getDualControlEditorState() )){
			new Timer() {
				@Override
				public void run() {
					switchEditablePanel(false);
					
				}
			}.schedule(100);
		//}
		
		Widget w =  generateEditExistingItemTitlePanel(data) ;
		if ( w == null)
			w = new HTML(""); 
		titlePanel.setWidget(w);
		btnSaveForApproval.setVisible(false);
		btnApprove.setVisible(false);
		btnRevice.setVisible(false);
		btnReentryBack.setVisible(false);
		btnReject.setVisible(false);
		btnCancel.setVisible(true);
		btnClose.setVisible(false);
	}
	
	
	
	@Override
	protected final Widget generateUnderlyingWidget() {
		
		btnApprove = new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getApproveButtonLabelI18nCode(), getDefaultApproveButtonLabel()), new ClickHandler() {
			@Override
			public void onClick(ClickEvent evt) {
				onApproveClick();
			}
		});
		btnCancel= new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getCancelButtonLabelI18nCode(), getDefaultCancelButtonLabel()), new ClickHandler() {
			@Override
			public void onClick(ClickEvent evt) {
				onCancelButtonClick();
			}
		});  
		
		btnSaveForApproval= new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getSaveButtonLabelI18nKey(), getDefaultSaveButtonLabel()), new ClickHandler() {
			@Override
			public void onClick(ClickEvent evt) {
				saveForApproval();
			}
		}); 
		
		btnRevice= new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getReviceButtonLabelI18nCode(), getDefaultReviceButtonLabel()), new ClickHandler() {
			@Override
			public void onClick(ClickEvent evt) {
				
			}
		}); 
		
		btnReject =new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getRejectButtonLabelI18nCode(), getDefaultRejectButtonLabel() ), new ClickHandler() {
			@Override
			public void onClick(ClickEvent evt) {
				onRejectButtonClick();
			}
		}); 
		btnClose =new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getCloseButtonLabelI18nKey(), getDefaultCloseButtonLabel() ), new ClickHandler() {
			@Override
			public void onClick(ClickEvent evt) {
				closePanelCommand.closePanel();
			}
		});  
		btnNext = new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getNextButtonLabelI18nKey() , getNextButtonDefaultLabel()) , new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onNextClick();
			}
		}); 
		btnReentryBack = new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getBackButtonLabelI18nCode() , getBackButtonDefaultLabel()) , new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onReEntryButtonClick();
			}
		}); 
		
		outmostContainerTable = new FlexTable();
		
		titlePanel = new SimplePanel(); 
		outmostContainerTable.setWidget( 0, 0, titlePanel);
		outmostContainerTable.getFlexCellFormatter().setColSpan(0, 0, 3);
		
		outmostContainerTable.setWidget(1, 0, instantiateEditorPanel());
		outmostContainerTable.getFlexCellFormatter().setColSpan(1, 0, 3);
		
		remark = new TextBoxAreaWithLabel(); 
		remark.setI18Key(getRemarkTextAreaLabelI18nKey());
		remark.setDefaultLabel(getDefaultRemarkTextAreaLabel()); 
		outmostContainerTable.setWidget(REMARK_ROW_INDEX, 2, remark);
		
		Integer lebarRemark = getRemarkColumnWidth() ; 
		if ( lebarRemark!= null){
			GWT.log("lebar remark di set menjadi : " + lebarRemark);
			outmostContainerTable.getFlexCellFormatter().setWidth(REMARK_ROW_INDEX, 0, lebarRemark +"px");
		}else{
			GWT.log("lebar remark tidak di set");
		}
		// approval remark. di bawah remark
		approvalRemark = new TextBoxAreaWithLabel(); 
		approvalRemark.setDefaultLabel(getDefaultApprovalRemarkTextAreaLabel());
		outmostContainerTable.setWidget(REMARK_ROW_INDEX  + 1, 2, approvalRemark);
		
		
		final int buttonIndex = REMARK_ROW_INDEX + 2 ;  
		
		outmostContainerTable.getFlexCellFormatter().setColSpan(buttonIndex, 0, 3);
		final FlowPanel buttonPanel = new FlowPanel();  
		outmostContainerTable.setWidget(  buttonIndex , 0  , buttonPanel) ; 
		buttonPanel.add(btnNext);
		buttonPanel.add(btnSaveForApproval) ;
		
		buttonPanel.add(btnApprove) ;
		buttonPanel.add(btnReject) ;
		SimplePanel wrapper = new SimplePanel();
		//buttonPanel.add(btnRevice) ;
		buttonPanel.add(wrapper);
		buttonPanel.add(btnReentryBack);
		buttonPanel.add(btnCancel) ;
		buttonPanel.add(btnClose);
		 
		wrapper.add(btnRevice);
		wrapper.setVisible(false);
		return outmostContainerTable;
	}
	
	
	/**
	 * handle click tombol reject
	 **/
	protected void onRejectButtonClick() {
		if (approvalRemark.getValue()== null ||approvalRemark.getValue().isEmpty()){
			Window.alert(getDefaultRejectWithNoRemarkWarningMessage());
			return ; 
		}
		if (! Window.confirm(I18Utilities.getInstance().getInternalitionalizeText(getRejectConfirmationMessageI18NKey(), getDefaultRejectConfirmationMessage())))
			return ;
		String messsage = approvalRemark.getValue();
		final DATA editedData =  getCurrentData() ;
		DualControlDataRPCServiceAsync.Util.getInstance().rejectData(currentCommonDualControlContainerData.getId(), messsage, new SigmaAsyncCallback<Void>() {
			@Override
			protected void customFailurehandler(Throwable caught) {
				Window.alert("gagal reject data, error message :" + caught.getMessage());
			}
			@Override
			public void onSuccess(Void result) {
				MainPanelStackControl.getInstance().closePanel(BaseDualControlDataEditor.this);
				fireChangeHandler(editedData, EditorOperation.EDIT);
			}
		});
		
	}
	
	
	
	/**
	 * handle tombol cancel
	 **/
	protected void onCancelButtonClick() {
		if ( Window.confirm(I18Utilities.getInstance().getInternalitionalizeText(getCancelButtonUserConfirmationMessageI18nKey(), getDefaultCancelButtonUserConfirmationMessage()))){
			MainPanelStackControl.getInstance().closePanel(this);
		}
	}
	
	
	protected void onApproveClick() {
		if (! Window.confirm(I18Utilities.getInstance().getInternalitionalizeText( getApproveButtonLabelI18nCode(), getDefaultApproveConfirmationMessage()))){
			return ;
		}
		final DATA editedData =  getCurrentData() ;
		fetchDataFromControlToObject(editedData, editorState);
		currentCommonDualControlContainerData.setJsonData(editedData.generateJSONString());
		currentCommonDualControlContainerData.setApprovalRemark(approvalRemark.getValue());
		
		DualControlUtil.getInstance().approveData(dualControlEnabledOperation, currentCommonDualControlContainerData, new SigmaAsyncCallback<Void>() {
			@Override
			protected void customFailurehandler(Throwable caught) {
				Window.alert(getMessageOnApproveDataFailure(currentCommonDualControlContainerData.getId(), caught)) ; 
				getApprovalFailureHandler().execute();
			}
			@Override
			public void onSuccess(Void result) {
				Window.alert(I18Utilities.getInstance().getInternalitionalizeText(getApproveSuccessMessageI18nKey(), getDefaultApproveSuccessMessage()));
				closePanelCommand.closePanel();
				fireChangeHandler(editedData, EditorOperation.EDIT);
				
				
			}
		}); 
	}
	
	
	
	/**
	 * handler tombol next. ini untuk konfirmasi
	 */
	protected void onNextClick () {
		try {
			validateSaveForApproval(); 
		} catch (CommonFormValidationException e) {
			String message = I18Utilities.getInstance().getInternalitionalizeText(getValidateForApprovalFailMessageI18nCode(), getDefaultMessageValidateForApprovalFail()); 
			Window.alert(message + ",\nInfo tambahan : " + e.getMessage()); 
			return ; 
		}
		JQueryUtils.getInstance().blockEntirePage("Konfirmasi data anda");
		switchToViewReadOnlyPanel();
		btnSaveForApproval.setVisible(true);
		btnReentryBack.setVisible(true);
		btnCancel.setVisible(true);
		btnNext.setVisible(false);
		JQueryUtils.getInstance().unblockEntirePage(500);
	}
	
	
	/**
	 * tombol ini untuk kembali ke form entry
	 */
	protected void onReEntryButtonClick () {
		this.switchEditablePanel(EditorState.addNew.equals(editorState));
		btnSaveForApproval.setVisible(false);
		btnReentryBack.setVisible(false);
		btnCancel.setVisible(true);
		btnNext.setVisible(true);
	}
	
	
	
	/**
	 * di sini anda perlu instantiate widget. cukup panggil semacam ini<br/>
	 * <code>
	 * uiBinder.createAndBindUi(this)
	 * </code>
	 **/
	protected abstract Widget instantiateEditorPanel () ;
	
	
	
	/**
	 * set editor state. ini otomatis juga akan mengatur button yang visible
	 **/
	public void setDualControlEditorState(
			DualControlEditorState dualControlEditorState) {
		
		this.dualControlEditorState = dualControlEditorState;
		if ( DualControlEditorState.CREATE_FOR_APPROVAL.equals(this.dualControlEditorState) ){
			btnApprove.setVisible(false); 
			btnReject.setVisible(false);
			btnRevice.setVisible(false);
			btnSaveForApproval.setVisible(true);
			btnCancel.setVisible(true);
			btnClose.setVisible(false);
			btnSaveForApproval.setVisible(false);
			btnReentryBack.setVisible(false);
			btnNext.setVisible(true);
			
		}
		else if ( DualControlEditorState.APPROVAL.equals(this.dualControlEditorState)){
			btnApprove.setVisible(true); 
			btnReject.setVisible(true);
			btnRevice.setVisible(true);
			btnSaveForApproval.setVisible(false);
			btnCancel.setVisible(true);
			btnClose.setVisible(false);
			btnNext.setVisible(false);
			btnReentryBack.setVisible(false);
		}
		else{
			btnApprove.setVisible(false); 
			btnReject.setVisible(false);
			btnRevice.setVisible(false);
			btnSaveForApproval.setVisible(false);
			btnCancel.setVisible(false);
			btnClose.setVisible(true);
			btnNext.setVisible(false);
			btnReentryBack.setVisible(false);
		}
	}
	
	 
	
	
	
	/**
	 * dual control editor state saat ini
	 **/
	public DualControlEditorState getDualControlEditorState() {
		return dualControlEditorState;
	}
	
	/**
	 * add by dode
	 * menambahkan dual control enable operation
	 * @param dualControlEnabledOperation
	 */
	public void setDualControlEnabledOperation(
			DualControlEnabledOperation dualControlEnabledOperation) {
		this.dualControlEnabledOperation = dualControlEnabledOperation;
		
	}
	@Override
	public void viewDataAsReadOnly(DATA data) {
		super.viewDataAsReadOnly(data);
		switchToViewReadOnlyPanel();
		remark.switchToReadonlyText();
		btnApprove.setVisible(false);
		btnSaveForApproval.setVisible(false);
		btnNext.setVisible(false);
		btnReentryBack.setVisible(false);
		btnReject.setVisible(false);
		btnRevice.setVisible(false);
		btnClose.setVisible(true);
		btnCancel.setVisible(false);
		remark.setVisible(false); 
		Widget w =  generateViewDetailReadonlyDataTitlePanel(data);
		if ( w == null)
			w = new HTML(""); 
		titlePanel.setWidget(w);
	}
	
	
	@Override
	public void approveSingleData(BigInteger approvalId) {
		 
		setDualControlEditorState(DualControlEditorState.APPROVAL);
		remark.setVisible(true);
		remark.switchToReadonlyText();
		approvalRemark.setVisible(true);
		
		
		
		openDataByDualControlId(approvalId ,  new Command() {
			
			@Override
			public void execute() {
				switchToViewReadOnlyPanel();
				setDualControlEditorState(DualControlEditorState.APPROVAL);
				Widget w =  generateApproveItemTitlePanel(getCurrentData());
				if ( w == null)
					w = new HTML(""); 
				titlePanel.setWidget(w);
				approvalRemark.setValue("");
			}
		} );
		
		
	}

	
	
	@Override
	public void viewSingleDataRejectedOrApprovedDataDetail(BigInteger approvalId) {
		openDataByDualControlId(approvalId , new Command() {
			@Override
			public void execute() {
				GWT.log("Render single approval detail");
				setDualControlEditorState(DualControlEditorState.VIEW_DETAIL);
				switchToViewReadOnlyPanel();
				Widget w = generateViewDetailReadonlyDataTitlePanel(currentData); 
				if ( w == null)
					w = new HTML(""); 
				titlePanel.setWidget(w);
				remark.setVisible(true);
				remark.switchToReadonlyText();
				approvalRemark.setVisible(true);
				approvalRemark.switchToReadonlyText();
				// set remark
				if ( currentCommonDualControlContainerData!= null){
					remark.setValue(currentCommonDualControlContainerData.getLatestRemark());
					approvalRemark.setValue(currentCommonDualControlContainerData.getApprovalRemark());
				}
				
				
			}
		} );
		
	}
	@Override
	public void viewSingleDataApprovalDetail(BigInteger approvalId) {
		
		openDataByDualControlId(approvalId , new Command() {
			@Override
			public void execute() {
				GWT.log("Render single approval detail");
				setDualControlEditorState(DualControlEditorState.VIEW_DETAIL);
				switchToViewReadOnlyPanel();
				Widget w = generateViewDetailReadonlyDataTitlePanel(currentData); 
				if ( w == null)
					w = new HTML(""); 
				titlePanel.setWidget(w);
				
				remark.setVisible(true);
				remark.switchToReadonlyText();
				approvalRemark.setVisible(false);
				
			}
		} );
		
	}
	
	
	
	/**
	 * hide remark field
	 */
	protected void hideRemark ( boolean hideFlag  ) {
		remark.setVisible(!hideFlag);
	}
	
	/**
	 * reference balik dari editor ke main panel. sekadar untuk helper
	 */
	public void setMainPanelReference(
			BaseDualControlMainPanel<KEY, DATA> mainPanelReference) {
		this.mainPanelReference = mainPanelReference;
	}
	/**
	 * reference balik dari editor ke main panel. sekadar untuk helper
	 */
	public BaseDualControlMainPanel<KEY, DATA> getMainPanelReference() {
		return mainPanelReference;
	}
	
	
	
	/**
	 * method ini akan membuat HTML panel(any). title dalam proses approve
	 */
	protected Widget generateApproveItemTitlePanel (DATA data) {
		return null ; 
	}
	
	/**
	 * generate title panel untuk proses edit existing data
	 */
	protected Widget generateEditExistingItemTitlePanel (DATA data ) {
		return null ; 
	}
	
	
	
	/**
	 * panel untuk add new data
	 */
	protected Widget generateNewDataTitlePanel () {
		return null ; 
	}
	
	
	/**
	 * generate readonly data
	 */
	protected Widget generateViewDetailReadonlyDataTitlePanel (DATA data){
		return null ; 
	}
	
	
	
	
	/**
	 * durasi default untuk timer . berapa milisecond akan di delay dari proses instantiate clone panel sampai pada proses di kembalikan ke pemangil. 
	 * ini untuk kemudahan pengetesan
	 */
	public static int DEFAULT_DELAY_ON_MAKE_CLONE_IN_MILIS =100 ; 
	
	/**
	 * worker untuk membuat clone dari object
	 */
	public  abstract  void makeClone (final ExpensivePanelGenerator<BaseDualControlDataEditor<KEY, DATA>> callback) ; 
	
	 
	

	@Override
	public void setRemarkLabel(String label) {
		
		remark.setControlLabel(label); 
		
		
		
	}
	
	@Override
	public void switchRemarkReadonly(boolean readonly) {
		if (readonly ) {
			remark.switchToReadonlyText()  ; 
		}
		else{
			remark.restoreControl(); 
		}
	}
	
	
	
	/**
	 * lebar column remark. kalau null berarti tidak akan di set.<br/>
	 * ini di ambil dalam px.
	 */
	public Integer getRemarkColumnWidth () {
		return null ; 
	}
}

