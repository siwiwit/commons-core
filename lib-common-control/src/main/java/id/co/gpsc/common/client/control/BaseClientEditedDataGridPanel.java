package id.co.gpsc.common.client.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.client.lov.ClientSideLOVManager;
import id.co.gpsc.common.client.lov.LOVCapabledControl;
import id.co.gpsc.common.client.lov.NoWidgetLOVCapabledObject;
import id.co.gpsc.common.client.widget.BaseSimplePopupEditorPanel;
import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.data.ClientSideListDataEditorContainer;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.LOVRequestArgument;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.common.data.lov.StrongTypedCustomLOVID;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CellButtonHandler;
import id.co.gpsc.jquery.client.grid.SimpleGridPanel;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;



/**
 * base class untuk grid dengan client side data container. jadinya proses editing di lakukan di client
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class BaseClientEditedDataGridPanel<DATA> extends SimpleGridPanel<DATA> {
	/**
	 * grid data container
	 **/
	protected ClientSideListDataEditorContainer<DATA> dataContainer ; 

	private Map<String, LOVCapabledControl> simpleLOVHolder =new HashMap<String, LOVCapabledControl>();
	
	private Map<StrongTypedCustomLOVID, LOVCapabledControl> customLOVHolder = new HashMap<StrongTypedCustomLOVID, LOVCapabledControl>();
	 
	
	
	
	/**
	 * tombol edit
	 **/
	protected CellButtonHandler<DATA> editButton ; 
	
	protected CellButtonHandler<DATA> eraseButton ;
	
	protected CellButtonHandler<DATA> viewDetailButton ;
	
	
	public BaseClientEditedDataGridPanel(){
		super();
		
		setCaption(I18Utilities.getInstance().getInternalitionalizeText(getGridCaptionI18NKey(), getGridCaptionDefaultLabel()));
		this.dataContainer = new ClientSideListDataEditorContainer<DATA>();
		this.dataContainer.appendDataChangeHandler(new Command() {
			@Override
			public void execute() {
				clearData(); 
				if ( dataContainer.getAllStillExistData()==null||dataContainer.getAllStillExistData().isEmpty())
					return ; 
				for ( DATA scn : dataContainer.getAllStillExistData()){
					appendRow(scn);
				}
			}
		});
		
		if ( this.getRequiredLOVParameterIds()!=null&& this.getRequiredLOVParameterIds().length>0){
			populateSimpleLOVList(this.getRequiredLOVParameterIds(), simpleLOVHolder);
		}
		if( this.getRequiredCustomLOVParameter()!=null&& this.getRequiredCustomLOVParameter().length>0){
			populateCustomLOVList(getRequiredCustomLOVParameter(), customLOVHolder);
		}
		
		fillLookupValue(new Command() {
			
			@Override
			public void execute() {
				GWT.log("selesai me-request LOV data untuk class:" + BaseClientEditedDataGridPanel.this.getClass().getName());
			}
		});
		

	
	}
	
	
	
	
	
	/**
	 * tombol add
	 **/
	private String addButtonid ; 
	
	
	
	
	
	/**
	 * show / hide add  button
	 **/
	public void showAddButton (boolean show) {
		System.out.println("show add button flag untuk class : " + this.getClass() + ":" + show + ",addButtonid:" + addButtonid);
		if ( !show){
			if ( addButtonid==null||addButtonid.length()==0)
				return ; 
			DOM.getElementById(addButtonid).getStyle().setProperty("display", "none");
		}else{
			if ( addButtonid==null||addButtonid.length()==0){
				addButtonid = DOM.createUniqueId(); // tipu dulu, dengan isi variable temp
				new Timer() {
					
					@Override
					public void run() {
						addButtonid = getGridButtonWidget().appendButton("Add", "ui-icon-document", new Command() {
							@Override
							public void execute() {
								addNewData(); 
							}
						});
						
					}
				}.schedule(10);
			}
			else	 
				DOM.getElementById(addButtonid).getStyle().setProperty("display", "");
		}
	}
	
	
	
	/**
	 * data container untuk proses editing
	 **/
	public ClientSideListDataEditorContainer<DATA> getDataContainer() {
		return dataContainer;
	}
	
	

	
	
	/**
	 * request fill Lov capabled control
	 **/
	public void fillLookupValue(Command afterLoadCompleteCommand) {
		if ( simpleLOVHolder.isEmpty()&&customLOVHolder.isEmpty())
			return ; 
		ArrayList<LOVRequestArgument> args= new ArrayList<LOVRequestArgument>();
		ArrayList<String> avoidDuplicateHandler = new ArrayList<String>();
		
		for ( LOVCapabledControl scn : simpleLOVHolder.values()){
			String key = scn.getLOVSource().toString()+"::"+ scn.getParameterId() + "-" + I18Utilities.getInstance().getCurrentLocalizationCode() ;
			if ( avoidDuplicateHandler.contains(key))
				continue ;
			avoidDuplicateHandler.add(key);
			LOVRequestArgument baru = new LOVRequestArgument(scn.getParameterId(), scn.getLOVSource());
			args.add(baru);
		}
		
		
		for ( LOVCapabledControl scn : customLOVHolder.values()){
			String key = scn.getLOVSource().toString()+"::"+ scn.getParameterId() + "-" + I18Utilities.getInstance().getCurrentLocalizationCode() ;
			if ( avoidDuplicateHandler.contains(key))
				continue ;
			avoidDuplicateHandler.add(key);
			LOVRequestArgument baru = new LOVRequestArgument(scn.getParameterId(), scn.getLOVSource());
			args.add(baru);
		}
		
		
		GWT.log("request LOV data untuk class :" + this.getClass().getName() +",request size :" + args.size());
		 ClientSideLOVManager.getInstance().requestLOVUpdate( I18Utilities.getInstance().getCurrentLocalizationCode(), this, args , afterLoadCompleteCommand);
	}
	
	/**
	 * pekerjaan nya : 
	 * <ol>
	 * <li>dari lovIds di buat jadi {@link NoWidgetLOVCapabledObject}</li>
	 * <li>object {@link NoWidgetLOVCapabledObject} di masukan ke dalam map simpleLOVHolderIndexer</li>
	 * </ol>
	 * 
	 * @param lovIds id dari LOV id yang perlu di load
	 * @param simpleLOVHolderIndexer map tmp naruh LOVCapabledObject
	 **/
	protected void populateSimpleLOVList(String[] lovIds , Map<String, LOVCapabledControl> simpleLOVHolderIndexer ) {
		if (lovIds==null||lovIds.length==0)
			return ; 
		for ( String scn : lovIds){
			NoWidgetLOVCapabledObject c = new NoWidgetLOVCapabledObject();
			c.setParameterId(scn);
			ClientSideLOVManager.getInstance().registerLOVChangeListener(this, c);
			simpleLOVHolderIndexer.put(scn, c);
		}
		
	}
	
	
	
	
	/**
	 * populate custom LOV provider
	 **/
	protected void populateCustomLOVList(StrongTypedCustomLOVID[] lovIds , Map<StrongTypedCustomLOVID, LOVCapabledControl> customLOVHolderIndexer ) {
		if (lovIds==null||lovIds.length==0)
			return ; 
		for ( StrongTypedCustomLOVID scn : lovIds){
			NoWidgetLOVCapabledObject c = new NoWidgetLOVCapabledObject();
			c.setLovSource(LOVSource.useCustomProvider);
			c.setParameterId(ClientSideLOVManager.getInstance().generateActualLOVID(scn));
			ClientSideLOVManager.getInstance().registerLOVChangeListener(this, c);
			customLOVHolderIndexer.put(scn, c);
		}
		
	}
	
	
	
	/**
	 * membaca id dari LOV parameter yang di perlukan dalam grid. ini misalnya kalau di grid di perlukan data dari LOV tertentu<br/>
	 * Override ini kalau anda memerlukan LOV khusus untuk grid anda
	 **/
	protected  String[] getRequiredLOVParameterIds() {
		return null ;
	}
	
	
	/**
	 * ini data LOV id yang perlu di load(yang custom)
	 **/
	protected StrongTypedCustomLOVID [] getRequiredCustomLOVParameter(){
		return null ;
	}
	
	/**
	 * membaca simple LOV data, di cari dengan id dari lov.
	 * @param lovId id dari LOV
	 * @param dataValueAsString key lov, di jadikan string*dalam kasus big integer , atau integer * 
	 * 
	 **/
	protected CommonLOV getLOVData (String lovId , String dataValueAsString) {
		if (! simpleLOVHolder.containsKey(lovId)){
			GWT.log("tidak ada lov dengan id :" + lovId + ",di temukan dalam grid :" + this.getClass().getName());
			return null ;
		}
		CommonLOVHeader h = simpleLOVHolder.get(lovId).getLOVData();
		if(h==null){
			GWT.log("lov dengan id :" + lovId + ",kosong dalam grid :" + this.getClass().getName());
			return null ;
		}
		return h.findById(dataValueAsString);
	}
	
	
	
	
	
	/**
	 * membaca data LOV enum
	 **/
	protected CommonLOV getLOVData (StrongTypedCustomLOVID lovId , String dataValueAsString) {
		if (! simpleLOVHolder.containsKey(lovId)){
			GWT.log("tidak ada lov dengan id :" + lovId + ",di temukan dalam grid :" + this.getClass().getName());
			return null ;
		}
		CommonLOVHeader h = simpleLOVHolder.get(lovId).getLOVData();
		if(h==null){
			GWT.log("lov dengan id :" + lovId + ",kosong dalam grid :" + this.getClass().getName());
			return null ;
		}
		return h.findById(dataValueAsString);
	}
	
	@Override
	protected BaseColumnDefinition<DATA, ?>[] getActualGridColumnDefinitions() {
		
		BaseColumnDefinition<DATA, ?>[] progDefineds =  getColumnDefinitions();
		
		
		@SuppressWarnings("unchecked")
		BaseColumnDefinition<DATA, ?>[] retval = (BaseColumnDefinition<DATA, ?>[])new BaseColumnDefinition<?, ?>[(progDefineds==null?0:progDefineds.length) +2];
		
		
		BaseColumnDefinition<DATA, ?> rownumCol =  generateRowNumberColumnDefinition(getDefaultRowNumberLabel(), getDefaultRowNumberColumnWidth(), getRowNumberI18NKey());
		retval[0] = rownumCol;
		BaseColumnDefinition<DATA, ?> actionColl = generateButtonsCell(generateButtonHandler(), getActionColumnHeaderDefaultLabel(), getActionColumnHeaderLabelI18NKey(), getActionColumnHeaderDefaultWidth());
		retval[1] = actionColl ; 
		if (progDefineds!=null ){
			for (int i=0;i<progDefineds.length;i++){
				retval[i+2] = progDefineds[i];
			}
		}
		
		// column tombil
		
		
		
		return retval;
	}
	
	
	
	
	/**
	 * key internalization untuk row number
	 **/
	protected abstract String getRowNumberI18NKey() ;
	
	
	
	/**
	 * label default untuk row number di grid
	 **/
	protected String getDefaultRowNumberLabel () {
		return "No";
	}
	
	
	
	/**
	 * lebar default row number column
	 **/
	protected int getDefaultRowNumberColumnWidth () {
		return 50;
	}
	
	
	
	
	/**
	 * ini untuk nampilkan edit data
	 **/
	protected void editData (DATA data){
		getEditorPanel().editExistingData(data); 
	}
	
	
	protected void addNewData (){
		DATA objBaru = generateNewInstanceData(); 
		getEditorPanel().addAndEditNewData((DATA)objBaru); 
		
	}

	
	protected void viewData(DATA data) {
		getEditorPanel().viewDataAsReadOnly(data); 
	}
	
	
	
	/**
	 * ini instantiate object untuk dalam kasis add new data
	 **/
	protected abstract DATA generateNewInstanceData () ; 
	
	
	
	
	private BaseSimplePopupEditorPanel< DATA> editor ; 
	
	
	
	
	protected BaseSimplePopupEditorPanel<DATA> getEditorPanel () {
		if(editor==null){
			editor = generateEditorWidget() ; 
			editor.setDataContainer( getDataContainer());
		}
		
		return editor ; 
	}
	
	
	
	/**
	 * generate editor panel
	 **/
	protected abstract BaseSimplePopupEditorPanel<DATA > generateEditorWidget () ; 
	
	
	
	
	
	
	
	/**
	 * label default untuk grid caption
	 **/
	protected abstract String getGridCaptionDefaultLabel() ;
	
	/**
	 * key internalization untuk grid header label
	 **/
	protected abstract String getGridCaptionI18NKey() ;
	
 
	
	
	
	/**
	 * untuk icon hapus
	 **/
	protected String getEraseButtonCssName () {
		return "ui-icon-trash" ; 
	}
	
	
	
	
	/**
	 * default title untuk erase data dari grid. Ini di pakai kalau {@link #getEraseButtonTitleI18NKey()} -> tidak ditemukan dalam i18N Cache
	 **/
	protected String getEraseButtonDefaultTitle () {
		return "erase"; 
	}
	
	
	/**
	 * internalization key untuk title icon erase pada grid
	 * 
	 * 
	 **/
	protected abstract String getEraseButtonTitleI18NKey () ;
	
	/**
	 * css name untuk tombol edit
	 **/
	protected  String getEditButtonCssName () {
		return "ui-icon-pencil"; 
	} 
	
	
	
	/**
	 * default title untuk edit
	 **/
	protected String getEditButtonDefaultTitle () {
		return "Edit data";
	}
	
	
	
	/**
	 * key intnernalization untuk title edit button. internationalize me please
	 **/
	protected abstract String getEditButtonTitleI18NKey ();
	/**
	 * icon nampilkan detail
	 **/
	protected String getViewDetailButtonCssName () {
		return "ui-icon-folder-open";
	}
	
	
	
	/**
	 * title untuk view detail
	 **/
	protected String getViewDetailButtonDefaultTitle (){
		return "view";
	}
	
	
	
	/**
	 * get i18 N key untuk view detail
	 **/
	protected abstract String getViewDetailButtonTitleI18NKey ();
	
	/**
	 * pekerjaan di sini adalah comfirm user untuk menghapus data. Kalau OK berarti data di flag hapus dari memory
	 **/
	protected abstract void validateEraseAndConfirmEraseData(DATA data , AsyncCallback<Boolean> allowEraseCallback);
	
	
	
	/**
	 * key internalization untuk action column
	 **/
	protected abstract String getActionColumnHeaderLabelI18NKey() ;
	
	
	
	/**
	 * lebar standard dari action column
	 **/
	public int getActionColumnHeaderDefaultWidth(){
		return 100 ; 
	}
	
	/**
	 * default header label untuk action
	 **/
	protected String getActionColumnHeaderDefaultLabel() {
		return "action";
	}
	
	
	/**
	 * worker untuk erase data
	 **/
	protected void eraseData (final DATA dataToErase){
		validateEraseAndConfirmEraseData(dataToErase	, new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if ( result==null||!result.booleanValue())
					return ;
				getDataContainer().eraseData(dataToErase);
			}
			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
				return ;
			}
		});
	}
	
	
	
	/**
	 * constructor action buttons. ini untuk edit, add, view detail
	 **/
	@SuppressWarnings("unchecked")
	protected CellButtonHandler<DATA>[] generateButtonHandler () {
		this.editButton = generateEditButton();
		this.eraseButton = generateEraseButton();
		this.viewDetailButton = generateViewDetailButton();
		
		
		return (CellButtonHandler<DATA>[])new CellButtonHandler<?>[]{
				editButton 
				, 
				eraseButton 
				, viewDetailButton
		} ;
	}
	
	
	
	/**
	 * worker untuk generate tombol edit. override ini kalau anda memerlukan button edit yang custom untuk ini. Mohon di cek juga : 
	 * {@link #getEditButtonCssName()}
	 * {@link #getEditButtonDefaultTitle()}
	 * {@link #getEditButtonTitleI18NKey()}
	 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
	 * 
	 **/
	protected CellButtonHandler<DATA> generateEditButton() {
		return new CellButtonHandler<DATA>(getEditButtonCssName() , I18Utilities.getInstance().getInternalitionalizeText(getEditButtonTitleI18NKey(), getEditButtonDefaultTitle() ) , new DataProcessWorker<DATA>() {
			@Override
			public void runProccess(DATA data) {
				editData(data);
			}
		});
	}
	
	
	
	
	/**
	 * generate button click erase data dalam grid
	 *  {@link #getEraseButtonCssName()}
	 *   {@link #getEraseButtonTitleI18NKey()}
	 *    {@link #getEraseButtonDefaultTitle()}
	 *    
	 **/
	protected CellButtonHandler<DATA> generateEraseButton() {
		return new CellButtonHandler<DATA>(getEraseButtonCssName(), I18Utilities.getInstance().getInternalitionalizeText(getEraseButtonTitleI18NKey(), getEraseButtonDefaultTitle()), new DataProcessWorker<DATA>() {
			@Override
			public void runProccess(DATA data) {
				eraseData(data);
			}
		});
	}
	
	
	
	
	/**
	 * generate button untuk view detail
	 **/
	protected CellButtonHandler<DATA> generateViewDetailButton(){
		return new CellButtonHandler<DATA>(getViewDetailButtonCssName() , I18Utilities.getInstance().getInternalitionalizeText(getViewDetailButtonTitleI18NKey(), getViewDetailButtonDefaultTitle()) , new DataProcessWorker<DATA>() {
		
			@Override
			public void runProccess(DATA data) {
				viewData(data);
			}
		}); 
	}
	
	
	
	
	
	/**
	 * show/hide show detail button dalam grid
	 **/
	public void hideDetailButton(boolean hide) {
		this.viewDetailButton.setVisible(!hide);
	}
	
	
}
