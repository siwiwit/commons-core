package id.co.gpsc.jquery.client.grid.cols;

import id.co.gpsc.common.control.ResourceBundleConfigurableControl;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.grid.CustomFormatter;
import id.co.gpsc.jquery.client.grid.GridHorizontalAlign;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;





public abstract class BaseColumnDefinition<DATA , COLTYPE> implements ResourceBundleConfigurableControl{
	
	/**
	 * key untuk menaruh original data(versi POJO ke dalam js object).pls check {@link #appendRow(Object)}
	 **/
	//public static final String ORIGINAL_OBJECT_KEY_ON_RAW_JS ="originalData";
	
	/**
	 * javascript variable name untuk column yang di definisikan
	 **/
	protected String rawJsDataName ;
	
	
	/**
	 * key internalization untuk header label
	 **/
	protected String headerLabeli18Key; 
	
	/**
	 * id dari current row. beberapa mungkin memerlukan ini. variable ini di inject oleh grid pada. jadinya dalam proses {@link #getData(Object)}, kalau misalnya ID di perlukan bisa di akses di sini
	 **/
	private String currentRowId ; 
	/**
	 * task yang akan di render pada saat proses render selesai
	 **/
	private ArrayList<Command> afterRenderPendedTasks = new ArrayList<Command>();
	
	
	/**
	 * reference ke elemen parent. jqgrid thing. ini untuk akses method jqgrid. manipulasi terkait column
	 **/
	private Element parentGridReference ; 
	
	/**
	 * worker untuk replace grid label header. ini berguna setelah grid sudah selesai di buat. 
	 * jadinya dengan column definition ini kita bisa memerintahkan replace label dari grid header
	 **/
	protected ReplaceLabelWorker replaceLabelWorker;
	
	
	
	private static int INCREMENTED_COLUMN_NAME=1;
	
	
	
	
	/**
	 * map grid data dengan row id . di assign ke sini untuk bisa di pergunakan dalam proses formatting
	 **/
	private HashMap<String, DATA> indexedGridDataByRowId ;  
	
	
	
	
	/**
	 * column sortable atau tidak. default <i>false</i>
	 **/
	private boolean sortable = false ;
	
	
	
	/**
	 * column boleh di resize atau tidak
	 **/
	private boolean  resizeable = true ; 
	
	/**
	 * nama field untuk sorting. kalau ini tidak null dan tidak blank, field akan menjadi sortable
	 **/
	private String sortFieldName ;
	
	private int width ; 
	
	
	/**
	 * header label . ini apa yang muncul di header
	 */
	private String headerLabel ;
	/**
	 * constructor grid column definition
	 * @param headerLabel label header. bisa di rubah dengan code
	 * @param columnWidth lebar column
	 **/
	public BaseColumnDefinition(String headerLabel , int columnWidth){
		rawJsDataName =generateJsFieldName(); 
		constructionTaskMethod();
		this.headerLabel = headerLabel ; 
		this.width=columnWidth;
	}
	
	
	public BaseColumnDefinition(String headerLabel , int columnWidth, String i18HeaderKey){
		this(headerLabel , columnWidth);
		this.headerLabeli18Key = i18HeaderKey ; 
	}
	
	

	
	/**
	 * generate nama js field untuk column. override ini kalau di perlukan nama js yang dedicated(misal untuk debug purpose)
	 **/
	protected String generateJsFieldName () {
		return "AUTO_FIELD_"+ ((new Date()).getTime()+"_"+ (INCREMENTED_COLUMN_NAME++));
	}
	
	/**
	 * membaca data dari object. data yang di render ini pada cell
	 * 
	 **/
	public abstract COLTYPE getData (DATA data) ;
	
	
	/**
	 * custom formatter untuk grid. kalau misal nya rasa perlu handle manual, berarti anda perlu menyediakan formatter untuk cell column. 
	 * anda perlu punya pengetahuan soal HTML kalau anda bermaksud mempergunakan pendekatan ini. Default method ini return null(no custom formatter, pakai default)<br/>
	 * <strong><i style="color:red">perhatian:</i></strong> nilai ini akan membuat nilai getFormatterType di abaikan
	 * @see #getFormatterType()
	 **/
	public CustomFormatter<DATA> getCustomFormatter(){
		return null ;
	}
	/**
	 * label column(preconfigured label)
	 **/
	public  String getHeaderLabel (){
		return this.headerLabel ; 
	}
	
	/**
	 * label column(preconfigured label)
	 **/
	public void setHeaderLabel(String headerLabel) {
		this.headerLabel = headerLabel;
	}
	
	
	
	/**
	 * header label pengganti
	 **/
	private String overrideLabelHeader ; 
	
	
	 
	
	
	
	/**
	 * timpa label dengan label baru
	 **/
	public void setOverrideLabelHeader(String overrideLabelHeader) {
		this.overrideLabelHeader = overrideLabelHeader;
		if ( parentGridReference!=null){
			setLabelWorker(parentGridReference.getId(), getRawJsDataName(), overrideLabelHeader, null);
		}
	}
	
	
	/**
	 * worker untuk replace header label . versi ini dengan tambahan set css untuk header
	 * @param overrideLabelHeader label untuk header grid
	 * @param additionalCssForHeader css tambahan untuk di apply ke header grid
	 */
	public void setOverrideLabelHeader(final String overrideLabelHeader,final String additionalCssForHeader) {
		this.overrideLabelHeader = overrideLabelHeader;
		if ( parentGridReference!=null){
			setLabelWorker(parentGridReference.getId(), getRawJsDataName(), overrideLabelHeader, additionalCssForHeader);
		}else{
			afterRenderPendedTasks.add(new Command() {
				
				@Override
				public void execute() {
					setLabelWorker(parentGridReference.getId(), getRawJsDataName(), overrideLabelHeader, additionalCssForHeader);
				}
			});
		}
	}
	
	
	
	
	
	
	
	
	/**
	 * label yang akhirnya di render di grid column. dalam kasus i18 related , field ini tidak akan sa,a dengan {@link #getHeaderLabel()}
	 **/
	public String getActualToDisplayLabel() {
		if ( overrideLabelHeader!=null&&overrideLabelHeader.length()>0)
			return overrideLabelHeader;
		String swapheaderLabel =  getHeaderLabel();
		if (this.headerLabeli18Key!=null&& this.headerLabeli18Key.length()>0){
			String i18Label =  I18Utilities.getInstance().getInternalitionalizeText(headerLabeli18Key);
			if ( i18Label!=null && i18Label.length()>0){
				swapheaderLabel = i18Label;
			}
		}
		return swapheaderLabel ; 
	}
	
	public abstract GridHorizontalAlign getHorizontalAlign();
	
	
	/**
	 * untuk client side sorting. data dalam grid bisa di sort atau tidak<br/>
	 * default : false
	 **/
	public boolean isSortable(){
		return sortable ;
	}
	
	
	
	/**
	 * set sortable atau tidak. hanya bekerja kalau belum di render. default <i>false</i>
	 **/
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}
	/**
	 * 
	 * column bisa di resize atau tidak
	 * default : true
	 **/
	public boolean isResizeable(){
		return resizeable ;
	}
	
	/**
	 * column boleh di resize atau tidak
	 **/
	public void setResizeable(boolean resizeable) {
		this.resizeable = resizeable;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * lebar column(dalam pixel)
	 **/
	public  int getWidth () {
		return this.width;
	}
	
	
	/**
	 * @see #isSortable()
	 * kalau isSortable = true , ini menentukan bagaimana data di sort(client side sort). nilai yang mungkin : 
	 * <ol>
	 * 	<li>int-integer sorting</li>
	 * 	<li>float-sorted as float</li>
	 * 	<li>date-sorted as date</li>
	 * 	<li>text-sorted as string</li>
	 * </ol>
	 * 
	 **/
	public abstract String getSorttype();
	
	
	
	/**
	 * tipe formatter. kombinasi yang mungkin : <br/>
	 * <ol>
	 * <li>integer</li>
	* <li>number</li>
	* <li>currency</li>
	* <li>date (uses formats compatable with php function date. For more info visit www.php.net)</li>
	* <li>checkbox</li>
	* <li>mail</li>
	* <li>link</li>
	* <li>showlink</li>
	* <li>select (this is not a real select but a special case for editing modules. See note below)</li>
 
	 * </ol>
	 **/
	public abstract String getFormatterType();
	
	
	
	/**
	 * method ini membangun definisi column(pls check manual jqgrid untuk lebih memahami ini)
	 **/
	public JavaScriptObject generateRawGridColumnDefinition (){
		JavaScriptObject obj = JavaScriptObject.createObject();
		generateRawGridColumnDefinitionWorker(obj);
		return obj;
	}
	
	
	/**
	 * column definition builder worker. ini agar pekerjaan bisa di override parsial. jadinya dari {@link #generateRawGridColumnDefinition()}, di sub kan ke method ini
	 **/
	protected void generateRawGridColumnDefinitionWorker (JavaScriptObject rawColumnDefinition){
		String actualHeaderLabel  = getHeaderLabel();
		if ( this.headerLabeli18Key!=null && this.headerLabeli18Key.length()>0){
			String swap = I18Utilities.getInstance().getInternalitionalizeText(headerLabeli18Key);
			if ( swap !=null&& swap.length()>0)
				actualHeaderLabel = swap ; 
			GWT.log("column i18:" + headerLabeli18Key+", acutal label:" + actualHeaderLabel);
		}
		else{
			GWT.log("--column def tidak ada i18 --");
			
		}
		
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "label", actualHeaderLabel);
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "index", getRawJsDataName());
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "sortable",isSortable());
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "resizable",isResizeable());
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "width",getWidth());
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "sorttype", getSorttype());
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "name", getRawJsDataName());
		NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "align", getHorizontalAlign().toString());
		if ( sortable)
			NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "index", sortFieldName);
		CustomFormatter<DATA> myFotmatter = getCustomFormatter();
		if (myFotmatter!=null){
			NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "formatter", buildNativeFormatterWrapper(myFotmatter));
		} else{
			String formatter = getFormatterType();
			if ( formatter!=null&&formatter.length()>0)
				NativeJsUtilities.getInstance().putObject(rawColumnDefinition, "formatter", formatter);
		}
	}
	
	/**
	 * menaruh data dalam javascript object. Method ini di pergunakan oleh data provider grid. jadinya per data di extract masing-masing field di masukan ke dalam single json object
	 **/
	public abstract void extractAndPutDataToObject(DATA data , JavaScriptObject targetObject);
	native void putDebugerVariable(Object targetObject , String key)/*-{
		//alert("menaruh key :" + key + " value : "  +targetObject + " ke window object" );
		$wnd[key]=targetObject;
		alert(key+":"+$wnd[key]);
	}-*/;

	native void echoContent(JavaScriptObject targetObject , String key)/*-{
		$wnd.console.log(key+ ">>"+ targetObject[key]);
	
	}-*/;
	
	
	/**
	 * nama js untuk menaruh data column dalam javascript object. ini berguna juga dalam proses rename column header label
	 **/
	public String getRawJsDataName() {
		return rawJsDataName;
	}
	
	
	
	
	/**
	 * nama js untuk menaruh column dalam js. 
	 * ini juga di pergunakan dalam proses i18n
	 **/
	public void setRawJsDataName(String rawJsDataName) {
		this.rawJsDataName = rawJsDataName;
	}
	
	
	/**
	 * worker untuk replace grid label header. ini berguna setelah grid sudah selesai di buat. 
	 * jadinya dengan column definition ini kita bisa memerintahkan replace label dari grid header
	 **/
	public void assignReplaceLabelWorker(ReplaceLabelWorker replaceLabelWorker) {
		this.replaceLabelWorker = replaceLabelWorker;
	}
	
	
	
	
	
	
	/**
	 * kebalikan dari {@link #putObject(JavaScriptObject, String, Object)}. membaca dari json object
	 **/
	public native JavaScriptObject getObject(JavaScriptObject target , String key)/*-{
		return target[key];
	
	}-*/;
	
	
	/**
	 * builder adapter dari {@link CustomFormatter} ke raw js method yang bertugas memformat data dalam cell
	 * @param actualFormatter formatter yang akan di wrap dalam adapter method
	 * @param pojoJsKey key untuk akses ke raw data dalam javascript object
	 *  
	 **/
	protected native JavaScriptObject buildNativeFormatterWrapper (CustomFormatter<DATA> actualFormatter) /*-{
		
		var swapthis = this ; 
		var retval = function (el, cellval, opts) {
			var hashMap =  swapthis.@id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition::indexedGridDataByRowId;
			var objPOJO =  hashMap.@java.util.HashMap::get(Ljava/lang/Object;)(cellval.rowId);
			return actualFormatter.@id.co.gpsc.jquery.client.grid.CustomFormatter::format(Ljava/lang/Object;)(objPOJO);
		}
	
		return retval ;
	}-*/;
	
	
	/**
	 * method ini di panggil dalam constructor method. kalau memerlukan pekerjaan tertentu pada saat construct override method ini. <br/>
	 * misal nya : 
	 * <ol>
	 * <li>set param tertentu</li>
	 * <li>baca dari konfigurasi(local-> session storage)</li>
	 * </ol>
	 **/
	protected void constructionTaskMethod(){
		
	}
	
	
	
	
	/**
	 * akses ke row data. ini untuk konsumsi formatter. 
	 **/
	protected DATA getGridDataByRowId (String rowId){
		return indexedGridDataByRowId.get(rowId);
				
	}
	
	
	
	/**
	 * worker untuk mengganti column header label. ini bekerja kalau grid sudah di render. method ini memanggil underlying JQGRID method
	 * @param jqgridObjectId id dari jqgrid object
	 * @param columnName nama column. refer ke {@link #getRawJsDataName()}
	 * @param newLabel label pengganti
	 * @param additionalCss css yang hendak di apply ke header grid column 
	 *  
	 **/
	protected native void setLabelWorker (String jqgridObjectId, String columnName , String newLabel , String additionalCss)/*-{
		$wnd.$("#" +jqgridObjectId ).jqGrid("setLabel" ,columnName , newLabel,additionalCss );
	}-*/;
	/**
	 * map grid data dengan row id . di assign ke sini untuk bisa di pergunakan dalam proses formatting
	 **/
	public void assignIndexedGridDataByRowId(
			HashMap<String, DATA> indexedGridDataByRowId) {
		this.indexedGridDataByRowId = indexedGridDataByRowId;
	}
	
	/**
	 * reference ke elemen parent. jqgrid thing. ini untuk akses method jqgrid. manipulasi terkait column
	 **/
	public void setParentGridReference(Element parentGridReference) {
		this.parentGridReference = parentGridReference;
	}
	
	
	
	/**
	 * render task after render grid
	 **/
	public void runAfterRenderTask(){
		for ( Command scn : this.afterRenderPendedTasks){
			scn.execute();
		}
	}

	/**
	 * id dari current row. beberapa mungkin memerlukan ini. variable ini di inject oleh grid pada. jadinya dalam proses {@link #getData(Object)}, kalau misalnya ID di perlukan bisa di akses di sini
	 **/
	public String getCurrentRowId() {
		return currentRowId;
	}

	/**
	 * id dari current row. beberapa mungkin memerlukan ini. variable ini di inject oleh grid pada. jadinya dalam proses {@link #getData(Object)}, kalau misalnya ID di perlukan bisa di akses di sini
	 **/
	public void setCurrentRowId(String currentRowId) {
		this.currentRowId = currentRowId;
	}
	/**
	 * nama field untuk sorting. kalau ini tidak null dan tidak blank, field akan menjadi sortable
	 **/
	public String getSortFieldName() {
		return sortFieldName;
	}
	/**
	 * nama field untuk sorting. kalau ini tidak null dan tidak blank, field akan menjadi sortable
	 **/
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
		sortable=this.sortFieldName!=null&& this.sortFieldName.length()>0 ;
		
	}
	
	@Override
	public void setI18Key(String key) {
		this.headerLabeli18Key = key ; 
	}
	
	@Override
	public String getI18Key() {
		return headerLabeli18Key;
	}
	
	@Override
	public void setConfiguredText(String text) {
		//FIXME: replace header column text 
		
	}
}
