package id.co.gpsc.jquery.client.grid;

import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.util.I18Utilities;

public class CellButtonHandler<DATA> {

	/**
	 * css label
	 **/
	private String buttonCssName ; 
	
	/**
	 * label default untuk icon
	 **/
	private String defaultIconTitle ; 
	
	/**
	 * key internalization untuk icon
	 **/
	private String iconi18Key ; 
	/**
	 * worker untuk memproses data
	 **/
	private DataProcessWorker<DATA> dataProcessor ;
	
	
	
	/**
	 * kalau i18 not found -> {@link #defaultIconTitle} , kalau di temukan di ambil dari i18 resource
	 **/
	private String actualIconTitle ; 
	
	
	
	/**
	 * key indexer method . ini untuk akses ke dalam click ikon. method apa yg harus di trgger
	 **/
	private String methodNameIndexerKey ; 
	
	
	/**
	 * visible atau tidak. warning, ini hanya berguna untuk kalau sudah di clear grid nya
	 **/
	private boolean visible = true ; 
	
	
	
	/**
	 * konstruktor default
	 **/
	public CellButtonHandler(String buttonCssName , String defaultIconTitle ){
		this.buttonCssName=buttonCssName ; 
		this.defaultIconTitle=defaultIconTitle; 
	}
	
	
	
	/**
	 * konstruktor default
	 **/
	public CellButtonHandler(String buttonCssName , String defaultIconTitle , DataProcessWorker<DATA> dataProcessor ){
		this.buttonCssName=buttonCssName ; 
		this.defaultIconTitle=defaultIconTitle; 
		this.dataProcessor=dataProcessor;
	}
	/**
	 * css label
	 **/
	public String getButtonCssName() {
		return buttonCssName;
	}
	/**
	 * css label
	 **/
	public void setButtonCssName(String buttonCssName) {
		this.buttonCssName = buttonCssName;
	}
	/**
	 * worker untuk memproses data
	 **/
	public DataProcessWorker<DATA> getDataProcessor() {
		return dataProcessor;
	}
	/**
	 * worker untuk memproses data
	 **/
	public void setDataProcessor(DataProcessWorker<DATA> dataProcessor) {
		this.dataProcessor = dataProcessor;
	}
	/**
	 * label default untuk icon
	 **/
	public String getDefaultIconTitle() {
		return defaultIconTitle;
	}
	/**
	 * label default untuk icon
	 **/
	public void setDefaultIconTitle(String defaultIconTitle) {
		this.defaultIconTitle = defaultIconTitle;
	}
	/**
	 * key internalization untuk icon
	 **/
	public String getIconi18Key() {
		return iconi18Key;
	}
	/**
	 * key internalization untuk icon
	 **/
	public void setIconi18Key(String iconi18Key) {
		this.iconi18Key = iconi18Key;
	}
	/**
	 * visible atau tidak. warning, ini hanya berguna untuk kalau sudah di clear grid nya
	 **/
	public boolean isVisible() {
		return visible;
	}
	
	
	
	/**
	 * data driven show hide button, jadinya datanya yang menentukan apakah data nya harus visible atau tidak. misal kalau state = deleted tombol edit = invis
	 **/
	public boolean isDataAllowMeToVisible (DATA data) {
		return true ; 
	}
	
	/**
	 * visible atau tidak. warning, ini hanya berguna untuk kalau sudah di clear grid nya
	 **/
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	/**
	 * key indexer method . ini untuk akses ke dalam click ikon. method apa yg harus di trgger
	 **/
	public String getMethodNameIndexerKey() {
		return methodNameIndexerKey;
	}
	/**
	 * key indexer method . ini untuk akses ke dalam click ikon. method apa yg harus di trgger
	 **/
	public void setMethodNameIndexerKey(String methodNameIndexerKey) {
		this.methodNameIndexerKey = methodNameIndexerKey;
	}
	/**
	 * kalau i18 not found -> {@link #defaultIconTitle} , kalau di temukan di ambil dari i18 resource
	 **/
	public String getActualIconTitle() {
		return actualIconTitle;
	}
	/**
	 * kalau i18 not found -> {@link #defaultIconTitle} , kalau di temukan di ambil dari i18 resource
	 **/
	public void setActualIconTitle(String actualIconTitle) {
		this.actualIconTitle = actualIconTitle;
	} 
	
	
	/**
	 * worker untuk generate string <li> icon button </button>
	 * @param rowDataId rowid dari data. ini unutk membuat method handler
	 **/
	public String generateButtonNode (String rowDataId /*, String cssName ,   String iconTitle , String methodName */ ){
	
		String titl = I18Utilities.getInstance().getInternalitionalizeText(getIconi18Key() ,   getDefaultIconTitle() ); 
		
		return "<li class=\"ui-state-default ui-corner-all\" title=\""+titl+"\" onclick=\""+ getMethodNameIndexerKey()+"('"+ rowDataId+"','"+getMethodNameIndexerKey()+"')"  +"\"><span class=\"ui-icon "+getButtonCssName()+"\"></span></li>";
		
	}
	

}
