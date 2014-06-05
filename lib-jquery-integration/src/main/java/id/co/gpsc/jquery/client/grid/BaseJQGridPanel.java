package id.co.gpsc.jquery.client.grid;

import id.co.gpsc.common.control.DataProcessWorker;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.BaseJqueryWidget;
import id.co.gpsc.jquery.client.SimpleDataFilter;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;
import id.co.gpsc.jquery.client.grid.cols.GridColumnGroup;
import id.co.gpsc.jquery.client.grid.cols.HaveSubWidgetColumnDefintion;
import id.co.gpsc.jquery.client.grid.cols.ReplaceLabelWorker;
import id.co.gpsc.jquery.client.grid.cols.StringColumnDefinition;
import id.co.gpsc.jquery.client.grid.event.GridRowDoubleClickHandler;
import id.co.gpsc.jquery.client.grid.event.GridSelectAllHandler;
import id.co.gpsc.jquery.client.grid.event.GridSelectRowHandler;
import id.co.gpsc.jquery.client.util.JQueryUtils;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;



/**
 * Wrapper jquery grid(base class) tidak di rencanakan untuk di pergunakan langsung
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 9 Aug 2012
 **/
public abstract class BaseJQGridPanel<DATA> extends SimplePanel{
	
	
	
	
	/**
	 * nama variable untuk menaruh language dari JQ Grid
	 **/
	public static final String JQGRID_LANGUAGE_VARIABLE_NAME="JQGRID_LANGUAGE";  
	
	/**
	 * nama variable untuk map reference grid
	 **/
	public static final String GRID_REFERENCE_MAP_NAME="SIGMA_JQGRID_CELL_BUTTON_GRID_REFERENCE";
	/**
	 * prefix nama method untuk handler button click
	 **/
	public static final String IN_CELL_BUTTON_METHOD_NAME_PREFIX="SIGMA_CELL_BUTTON_HANDLER";
	
	/**
	 * method counter untuk akses button handler. karena ini di taruh dalam native js jadinya ini perlu dijamin method nya beda
	 **/
	public static int IN_CELL_BUTTON_METHOD_COUNTER = 1 ;
	
	/**
	 * flag in cell button sudah siap atau tidak
	 **/
	public static boolean IN_CELL_BUTTON_INITIALIZED = false ; 
	
	
	
	/**
	 * positioning row baru
	 **/
	public enum AppendRowPositioning {
		last("last"), 
		first("first") , 
		before("before"), 
		after("after") ;
		private String internalRep ;
		private AppendRowPositioning(String pos ){
			this.internalRep = pos ;
			
		}
		
		@Override
		public String toString() {
			return internalRep;
		}
	}
	
	protected JavaScriptObject widgetConstructArgument = JavaScriptObject.createObject() ; 
	/**
	 * flag jquery sudah di render atau belum
	 **/
	protected boolean jqueryRendered =false ;
	
	
	/**
	 * render jquery on attach atau tidak
	 **/
	protected boolean renderJqueryOnAttach  = true ; 
	
	private Element spanOuter;

	/**
	 * hash table. key=POJO, data = string row id
	 **/
	private Map<DATA, String> indexedRowIdByObject = new HashMap<DATA, String>();
	

	/**
	 * kebalikan dari #indexedRowIdByObject
	 **/
	private HashMap<String, DATA> indexedDataById = new HashMap<String, DATA>();
	
	
	/**
	 * map of radio click handler
	 **/
	private HashMap<String, Command> indexedRadioColumnClickHandler = new HashMap<String, Command>();
	
	/**
	 * click handler utnuk checkbox
	 **/
	private HashMap<String, Command> indexedCheckboxClickHandler = new HashMap<String, Command>();
	
	
	private static int noBrainObjectId =1;
	private String caption ; 
	
	
	
	/**
	 * argument : rownumbers dalam jqgrid. menambahkan 
	 */
	private boolean appendRowNumberColumn =false; 
	
	
	
	/**
	 * element table dari grid
	 **/
	private Element gridTableElement ; 
	private Element gridButtonElement ; 
	private BaseColumnDefinition<DATA, ?>[] actualColumnDefinitions ;
	private HaveSubWidgetColumnDefintion<DATA>[] haveSubWidgetColumns;
	
	
	/**
	 * event handler double click
	 **/
	private ArrayList<GridRowDoubleClickHandler<DATA>> doubleClickHandlers= new ArrayList<GridRowDoubleClickHandler<DATA>>();
	
	/**
	 * handler select row
	 **/
	private ArrayList<GridSelectRowHandler<DATA>> selectRowHandlers= new ArrayList<GridSelectRowHandler<DATA>>();
	
	
	
	
	
	
	
	/**
	 * grid button. untuk add/remove button
	 **/
	private GridButtonWidget gridButtonWidget ;  
	
	
	
	/**
	 * data processor di index
	 **/
	private HashMap<String, DataProcessWorker<DATA>> indexedProcessor= new HashMap<String, DataProcessWorker<DATA>>();
	/**
	 * handler select all row
	 **/
	protected ArrayList<GridSelectAllHandler<DATA>> selectAllRowHandlers = new ArrayList<GridSelectAllHandler<DATA>>();
	
	
	/**
	 * handler sort change
	 **/
	private ISortOrderChange<DATA> sortOrderChangeHandler ;
	
	
	
	
	/**
	 * command post run
	 **/
	private ArrayList<Command> postRenderCommand = new ArrayList<Command>(); 
	/**
	 * command untuk reload grid
	 **/
	protected IReloadGridCommand reloadCommand ; 
	
	
	protected boolean multipleSelection  = false ; 
	
	
	/**
	 * awalan untuk ID dari setiap checkbox yang di generate. ini di pergunakan untuk scan object mana saja yang ada dalam posisi checked
	 **/
	private String checkboxIdPrefix  ; 
	
	/**
	 * prefix dari radio button
	 **/
	private String radioIdPrefix  ;
	
	
	
	/**
	 * nama method untuk handler radio click
	 **/
	private String radioClickHandlerMethodName ;
	
	/**
	 * nama method untuk handler checkbox click
	 **/
	private String checkboxClickHandlerMethodName ;
	
	
	/**
	 * nama dari radio button
	 **/
	private String radioName  ;
	
	
	protected BaseJQGridPanel(){
		
		super(DOM.createSpan());
		
		this.spanOuter=getElement();
		
		
		
		putConstructorArgument("datatype", "local");
		generateUnderlyingElement();
		
		String genId  = this.getClass().getName() ; 
		if ( genId.contains("$"))
			genId = genId.replaceAll("\\$", "_"); 
		if ( genId.contains("."))
			genId = genId.replaceAll("\\.", "_");
		checkboxIdPrefix = genId + "_checkbox_";
		radioIdPrefix = genId +"_radio_";
		radioName = genId ; 
		radioClickHandlerMethodName = radioIdPrefix + "_click"; 
		checkboxClickHandlerMethodName = checkboxIdPrefix + "_click";
		putConstructorArgument("altRows", true); 
		putConstructorArgument("shrinkToFit", false); 
	 
	}
	
	
	
	
	
	protected BaseJQGridPanel(boolean multipleSelection ){
		this();
		
		this.multipleSelection= multipleSelection ; 
		
		
		if ( multipleSelection)
			putConstructorArgument("multiselect", multipleSelection);
		
	}
	
	
	
	
	
	/**
	 * versi ini memungkinkan render Jqgrid on attach
	 * @param multipleSelection flag multiple selection
	 * @param renderOnAttach true = widget di render on attach
	 **/
	protected BaseJQGridPanel(boolean multipleSelection , boolean renderOnAttach ){
		this(multipleSelection); 
		this.renderJqueryOnAttach = renderOnAttach  ; 
	}
	
	
	/**
	 * menaruh string dalam constructor argument grid
	 */
	protected void putConstructorArgument(String key, String value){
		NativeJsUtilities.getInstance().putObject(widgetConstructArgument, key, value);
	}
	/**
	 * menaruh javascript object dalam constructor arguments
	 */
	protected void putConstructorArgument(String key, JavaScriptObject value){
		NativeJsUtilities.getInstance().putObject(widgetConstructArgument, key, value);
	}
	
	/**
	 * menaruh boolean dalam constructor arguments
	 */
	protected void putConstructorArgument(String key, boolean value){
		NativeJsUtilities.getInstance().putObject(widgetConstructArgument, key, value);
	}
	
	protected void generateUnderlyingElement() {
		//<table id="grid_panel_all_func"></table>
		//<div id="grid_button_panel_all_func"></div>
		
		
		
		spanOuter.setId("SIGMA_JQGRID_" + DOM.createUniqueId());
		gridTableElement = DOM.createTable();
		gridTableElement.setId("GRID_TABLE_"+DOM.createUniqueId());
		gridButtonElement = DOM.createDiv();
		gridButtonElement.setId("GRID_BUTTON_" + DOM.createUniqueId());
		
		
		spanOuter.appendChild(gridTableElement);
		spanOuter.appendChild(gridButtonElement);
		gridButtonWidget = generateGridButtonWidget(gridTableElement, gridButtonElement);
		
		
	}
	
	
	
	/**
	 * worker yang menggenerate grid button widget.<br/>
	 * Ini ada di sisi bawah grid, dan bisa di isi dengan paging control, tombol-tombol tertentu etc.<br/>
	 * override <span style="color:red"><strong>hanya</strong></span> kalau anda paham dan memang memerlukan fungsional tambahan untuk grid button
	 * @param gridTableElement reference ke grid table. kemungkinan anda akan memerlukan id nya untuk manipulasi
	 * @param gridButtonElement reference ke grid button div. sama juga, mungkin anda akan memerlukan id nya untuk manipulasi
	 **/
	protected GridButtonWidget generateGridButtonWidget (Element gridTableElement , Element gridButtonElement){
		return new GridButtonWidget(gridTableElement, gridButtonElement);
	}
	
	
	
	//@Override
	@SuppressWarnings("unchecked")
	protected void renderJQWidgetOnAttachWorker() {
		if ( jqueryRendered)
			return ; 
		if ( multipleSelection)
			putConstructorArgument("onSelectAll", buildSelectAllHandlerGlueMethod());
		this.actualColumnDefinitions = getActualGridColumnDefinitions();
		if ( this.actualColumnDefinitions.length>0){
			ArrayList<HaveSubWidgetColumnDefintion<DATA>> parentTypes = new ArrayList<HaveSubWidgetColumnDefintion<DATA>>();
			for ( BaseColumnDefinition<DATA, ?> scn : actualColumnDefinitions){
				scn.setParentGridReference(gridTableElement);
				if (scn instanceof HaveSubWidgetColumnDefintion){
					parentTypes.add((HaveSubWidgetColumnDefintion<DATA>)scn);
				}
			}
			if ( !parentTypes.isEmpty()){
				this.haveSubWidgetColumns=new HaveSubWidgetColumnDefintion[parentTypes.size()];
				int i=0;
				for ( HaveSubWidgetColumnDefintion<DATA> scn : parentTypes){
					haveSubWidgetColumns[i++]=scn;
				}
			}
		}
		JavaScriptObject arrHeaderLabel = JavaScriptObject.createArray();
		for ( String h: getHeaderLabels()){
			NativeJsUtilities.getInstance().pushToArray(arrHeaderLabel, h);
			
		}
		JavaScriptObject arrColumnDef = buildColumnDefinitions() ; 
		putConstructorArgument("colNames", arrHeaderLabel); //colNames
		putConstructorArgument("colModel", arrColumnDef); //colModel
		putConstructorArgument("caption", caption); //caption
		putConstructorArgument("ondblClickRow", buildSelectDoubleClickHandlerGlueMethod());
		putConstructorArgument("onSelectRow", buildSelectRowHandlerGlueMethod());
		putConstructorArgument("pager", this.gridButtonElement.getId());
		putConstructorArgument("onSortCol", this.buildSortChangeEventHandlerGlueMethod());
		//JQueryUtils.getInstance().renderJQueryWidget(getJQWidgetId(), getJQueryClassName(), widgetConstructArgument);
		renderJQueryWidget(getJQWidgetId(), getJQueryClassName());
		if ( Window.Location.getHost().indexOf("localhost")>=0 || Window.Location.getHost().indexOf("127.0.0.1")>=0 ){
			String key = "COLUMN-DEF-" + getElement().getId() ; 
			NativeJsUtilities.getInstance().putVariableToWindowVariable(key, arrColumnDef);
			NativeJsUtilities.getInstance().writeToBrowserConsole("menaruh column def ke browser variable : " + key ); 
		}
		// render pager
		this.gridButtonWidget.renderEmptyButtonWidget();
		
		getElement().setPropertyObject(BaseJqueryWidget.DEBUG_KEY_CONSTRUCTOR_ARGUMENT, widgetConstructArgument);
		
		
	}
	/**
	 * common method. render widget
	 * @param widgetId id dari widget
	 * @param jqueryClassname nama class jquery yang perlu di render
	 **/
	protected   void renderJQueryWidget(String widgetId, String jqueryClassname){
		NativeJsUtilities.getInstance().writeToBrowserConsole("rendering jquery widget>>"+ jqueryClassname);
		JQueryUtils.getInstance().renderJQueryWidget(widgetId, jqueryClassname, this.widgetConstructArgument);
		this.jqueryRendered=true;
		// kalau diantara grid ada yang perlu run tambahan task eksekusi saja. give delay bentar agar dom benar2 siap
		new Timer() {
			@Override
			public void run() {
				for ( BaseColumnDefinition<DATA, ?> scn : actualColumnDefinitions){
					scn.runAfterRenderTask();
				}
				if (! postRenderCommand.isEmpty()){
					for ( Command cmd : postRenderCommand){
						cmd.execute(); 
					}
				}
				renderMergedColumnHeader(true);
				
				
			}
		}.schedule(10);
		
	}
	
	
	
	
	/**
	 * render column header. ini hanya akan bekerja kalau anda override methode {@link #getGroupedColumnHeader()}
	 * @param useColSpanStyle Determine if the non grouping header cell should be have cell above it - value of false, or the column should be treated as one combining boot - true <br/>  
	 * contoh dalam  dengan argument <a href="http://www.trirand.com/jqgridwiki/lib/exe/detail.php?id=wiki%3Agroupingheadar&media=wiki:grouping1.png">false</a> <br/>
	 * contoh dalam  dengan argument <a href="http://www.trirand.com/jqgridwiki/lib/exe/detail.php?id=wiki%3Agroupingheadar&media=wiki:grouping3.png">true</a> <br/>
	 **/
	public void renderMergedColumnHeader ( boolean useColSpanStyle ){
		GridColumnGroup[] grps =  getGroupedColumnHeader();
		if ( grps== null)
			return ;
		JavaScriptObject obj = JavaScriptObject.createArray(); 
		for (GridColumnGroup scn : grps){
			createAndPushToArray(scn.getStartOfGroupColumn().getRawJsDataName(), scn.getNumberOfGroupColumn()	, scn.getGroupTitle(), obj); 
		}
		renderGroupedheaderWorker(getJQWidgetId(), useColSpanStyle, obj);
		
	}
	
	
	
	/**
	 * native JS untuk render group header
	 */
	private native void renderGroupedheaderWorker (String jqgridId , boolean useColSpanStyleArg, JavaScriptObject mergedGroupArgument)/*-{
		$wnd.jQuery("#" + jqgridId).jqGrid('setGroupHeaders', {
			useColSpanStyle: useColSpanStyleArg,
			groupHeaders:mergedGroupArgument 
		});
		
	}-*/; 
	
	
	
	
	/**
	 * membuat object group column dan memasukan ke dalam array. ini di pergunakan untuk membuat group column
	 * @param startColumnName ini di ambil dari {@link BaseColumnDefinition#getRawJsDataName()}
	 * @param numberOfColumns berapa column yang di kelompokan
	 * @param titleText title untuk header
	 *  
	 */
	private native void createAndPushToArray (String startColumnName, int numberOfColumns , String titleText, JavaScriptObject targetArray) /*-{
		//{startColumnName: 'amount', numberOfColumns: 3, titleText: '<em>Price</em>'}
		var obj = {}; 
		obj.startColumnName = startColumnName ; 
		obj.numberOfColumns = numberOfColumns; 
		obj.titleText = titleText; 
		
		targetArray.push ( obj); 
	
	}-*/;
	
	
	/**
	 * pls pergunakan {@link #setWidth(int)}
	 * @deprecated pls pergunakan {@link #setWidth(int)}. hasil dengan ini mungkin tidak akan konsisten
	 **/
	@Deprecated
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
	}
	
	/**
	 * set lebar(dalam pixel)
	 **/
	public void setWidth(final int width) {
		if ( !this.jqueryRendered){
			
			postRenderCommand.add(new Command() {
				
				@Override
				public void execute() {
					setWidth(width);
					
				}
			});
		}
		else{
			JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "setGridWidth" , width , true);
		}
	}
	
	
	public void setHeight(final int height) {
		if ( !this.jqueryRendered){
			//super.setHeight(height + "px");
			postRenderCommand.add(new Command() {
				
				@Override
				public void execute() {
					setHeight(height);
					GWT.log("height set pended command ");
				}
			});
		}	
		else{
			NativeJsUtilities.getInstance().writeToBrowserConsole("gridTableElement.getId()>>" +gridTableElement.getId() + ", di set dengan height:" + height);
			JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "setGridHeight" , height , true);
		}
	}
	
	
	//@Override
	protected String getJQueryClassName() {
		return "jqGrid";
	}

	
	/**
	 * ganti caption dari jquery. 
	 * caption berada di sisi atas dari grid
	 **/
	public void setCaption (String caption) {
		this.caption = caption ; 
		if ( this.jqueryRendered){
			JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "setCaption" , this.caption);
		}
		putConstructorArgument("caption", caption);
			
	}
	
	
	/**
	 * caption dari grid
	 **/
	public String getCaption() {
		return caption;
	}
	
	
	/**
	 * column-column yang available untuk 
	 **/
	protected abstract BaseColumnDefinition<DATA, ?>[] getColumnDefinitions();
	
	
	
	/**
	 * beberapa column bisa di group dengan title yang sama. versi table nya gambaran nya sbb : <br/>
	 * <table border="1">
	 * 	<tr align="center">	
	 * 	<td colspan="2">kelompok 1</td>
	 * 	<td  colspan="3">kelompok 2</td>
	 * 	<td  colspan="4"> kelompok 3</td>
	 * </tr>
	 * <tr align="center">	
	 * 	<td  >sub 1.1</td>
	 * 	<td  >sub 1.2</td>
	 * 
	 * 	<td  >sub 2.1</td>
	 *  <td  >sub 2.2</td>
	 *  <td  >sub 2.3</td>
	 *  
	 *  <td  >sub 3.1</td>
	 *  <td  >sub 3.2</td>
	 *  <td  >sub 3.3</td>
	 *  <td  >sub 3.4</td>
	 * </tr> 
	 * 
	 * </table>
	 * 
	 * yang perlu di lakukan : <br/>
	 * 
	 * 
	 * <ol>
	 * <li>Define group yang anda perlukan cek {@link GridColumnGroup}</li>
	 * </ol>
	 **/
	protected GridColumnGroup[]  getGroupedColumnHeader ( ) {
		return null ; 
	}
	
	
	
	/**
	 * mandatory di sediakan. setiap object memerlukan <i>key</i> data grid. method ini akan menyediakan row id ini. kalau di perlukan id tersendiri(misal anda prefer mempergunakan primary key dari data) override method ini
	 **/
	protected  String generateTemporalDataKey (DATA data) {
		return ""+(noBrainObjectId++);
	}
	
	/**
	 * worker untuk append row ke dalam grid.<br/>
	 * 
	 * Sebagai catatan, proses pengisian data, sebaiknya <strong>hanya</strong> di lakukan kalau grid sudah di masukan ke dalam dom.Kalau tidak maka data tidak akan terender dalam grid 
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 **/
	public String appendRow(DATA data){
		if (! GWT.isProdMode()){
			
		}
		final String trId=
		 actualAppendDataBridge(data, AppendRowPositioning.last, null);
		return trId;
	}
	
	
	/**
	 * worker untuk append row ke dalam grid, sebelum data yang di minta
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 **/
	public String appendRowBefore(DATA data , DATA dataForOffset){
		if (!indexedRowIdByObject.containsKey(dataForOffset))
			return actualAppendDataBridge(data , AppendRowPositioning.last, null );
		String rowId = indexedRowIdByObject.get(dataForOffset);
		return actualAppendDataBridge(data, AppendRowPositioning.before, rowId);
	}
	
	/**
	 * append data. data di sisipkan setelah data tertentu. paramter di kirimkan berupa ID row di mana data akan di sisipkan sebelumnya
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * @param data data baru untuk di masukan ke dalam grid
	 * @param dataForOffsetRowId id dari row, di mana data ini akan di sisipkan sebelumnya
	 * @return id row baru yang di sisipkan. ini berguna untuk manipulasi data
	 *  
	 **/
	public String appendRowBefore(DATA data , String dataForOffsetRowId){
		return actualAppendDataBridge(data, AppendRowPositioning.before, dataForOffsetRowId);
	}
	
	
	
	
	/**
	 * versi sebaliknya dari {@link #appendRowBefore(Object, Object)}. ini menambahkan data setelah row data tertentu
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * @param data data baru untuk di render dalam grid
	 * @param dataForOffset data untuk offset. data akan di masukan setelah data ini
	 * @return id row data baru
	 **/
	public String appendRowAfter(DATA data , DATA dataForOffset){
		if (!indexedRowIdByObject.containsKey(dataForOffset))
			return actualAppendDataBridge(data , AppendRowPositioning.last, null );
		String rowId = indexedRowIdByObject.get(dataForOffset);
		return actualAppendDataBridge(data, AppendRowPositioning.after, rowId);
	}
	
	
	/**
	 * 
	 * append row after row id tertentu
	 **/
	public String appendRowAfter(DATA data , String dataForOffsetRowId){
		return actualAppendDataBridge(data, AppendRowPositioning.after, dataForOffsetRowId);
	}
	
	
	
	/**
	 * remove 1 row dengan data
	 * @param data data yang di remove dari grid. pls pastikan ini reference object yang sama yang pernah di masukan ke dalam grid
	 * @since 18 aug 2012
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 **/
	public boolean remove(DATA data){
		if (! indexedRowIdByObject.containsKey(data))
			return false ;
		String rowId = indexedRowIdByObject.get(data);
		indexedRowIdByObject.remove(data);
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "delRowData" ,rowId) ;
		return true ;
	}
	/**
	 * remove 1 row dengan data. versi ini dengan ID dari row
	 * @param rowId id row
	 * @since 18 aug 2012
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 **/
	public boolean remove(String rowId){
		if ( !this.indexedDataById.containsKey(rowId))
			return false ;
		DATA swap = indexedDataById.get(rowId);
		if ( swap==null)
			return false ;
		indexedRowIdByObject.remove(swap);
		indexedDataById.remove(rowId);
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "delRowData" ,rowId) ;
		return true ;
	}
	
	
	
	/**
	 * kosongkan grid(remove all)
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * @since 18 aug 2012
	 **/
	public void clearData(){
		
		showHideLoadingBlockerScreen(false);
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "clearGridData");
		//FIXME: clear juga data cache
	}
	
	
	
	/**
	 * reset selection. jadinya semua selection akan di hilangkan. tidak ada item yang dalam posisi selected setelah ini
	 **/
	public void resetSelection(){
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "resetSelection");
	}

	
	
	/**
	 * kasus ini kalau POJO data berubah. jadinya grid perlu di update. use this instead of clearr and re fill
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * @since 18-aug-2012
	 **/
	public void updateRowData (DATA data){
		String key = indexedRowIdByObject.get(data);
		if ( key==null)
			return ;
		JavaScriptObject raw= generateRawJsData(key ,  data);
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "setRowData" , key , raw);
	}
	
	
	
	/**
	 * mark data sebeagai selected item
	 * @param rowId id dari row
	 * @since 18-aug-2012
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * 
	 **/
	public void markAsSelected (String rowId , boolean propageSelectedEvent ) {
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "setSelection", rowId , propageSelectedEvent);
	}
	
	
	/**
	 * mark data sebeagai selected item
	 * @param data yang akan di select
	 * @param propageSelectedEvent true = akan me-rise on selected event
	 * @since 18-aug-2012
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * 
	 **/
	public void markAsSelected (DATA data , boolean propageSelectedEvent ) {
		String rowId = indexedRowIdByObject.get(data);
		if ( rowId==null)
			return ;
		JQueryUtils.getInstance().triggerVoidMethod(gridTableElement.getId(), getJQueryClassName(), "setSelection", rowId , propageSelectedEvent);
	}
	
	
	
	
	/**
	 * register row selection handler ke dalam grid
	 **/
	public HandlerRegistration addRowSelectedHandler(final GridSelectRowHandler<DATA> handler){
		if ( this.selectRowHandlers.contains(handler))
			return null ;
		selectRowHandlers.add(handler);
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				selectRowHandlers.remove(handler);
			}
		};
	}
	
	
	/**
	 * register row double click handler
	 **/
	public HandlerRegistration addDoubleclickHandler (final GridRowDoubleClickHandler<DATA> handler){
		if ( this.doubleClickHandlers.contains(handler))
			return null ;
		doubleClickHandlers.add(handler);
		return new HandlerRegistration() {
			@Override
			public void removeHandler() {
				doubleClickHandlers.remove(handler);
			}
		};
	}
	
	
	
	/**
	 * grid button. untuk add/remove button
	 **/
	public GridButtonWidget getGridButtonWidget() {
		return gridButtonWidget;
	}
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		if ( renderJqueryOnAttach)
			renderJQWidgetOnAttachWorker();
		

	}
	
	/**
	 * worker untuk mengganti label pada header grid
	 * @param gridId id grid. pls refer ke #gridTableElement  method {@link Element#getId()}
	 * @param columnJsRawName pls cek {@link BaseColumnDefinition#getRawJsDataName()}
	 * @param newLabel label baru utnuk grid
	 **/
	protected native void setHeaderLabel(String gridId , String columnJsRawName , String newLabel)/*-{
		$wnd.$("#" +gridId ).jqGrid("setLabel",columnJsRawName ,newLabel );
	}-*/;
	
	
	
	
	
	

	
	/**
	 * actual worker untuk append row ke dalam grid
	 **/
	protected String actualAppendDataBridge(DATA data , AppendRowPositioning position , String relativeToRowId) {
		String id = generateTemporalDataKey(data);
		indexedRowIdByObject.put(data, id);
		indexedDataById.put(id, data);
		addRowData(gridTableElement.getId(), id, generateRawJsData(id, data) , position.toString() , relativeToRowId);
		
		if ( this.haveSubWidgetColumns!=null){
			for(HaveSubWidgetColumnDefintion<DATA> scn : haveSubWidgetColumns){
				Widget[] childWidgets = scn.generateWidgets(data);
				if ( childWidgets==null||childWidgets.length==0)
					continue ;
				String idPanel =  scn.getLatestWidgetContainerId();
				for ( Widget w : childWidgets){
					DOM.getElementById(idPanel).appendChild(w.getElement());
					adopt(w);
				}
				
				
				
			}
		}
		return id ; 
	}
	
	
	
	/**
	 * worker method untuk generate raw javascript object untuk data tertentu
	 * @param rowId id row. ini untuk locate row data
	 **/
	protected JavaScriptObject generateRawJsData (String rowId , DATA data ){
		JavaScriptObject jsObject = JavaScriptObject.createObject();
		if ( this.actualColumnDefinitions==null){
			this.actualColumnDefinitions = getActualGridColumnDefinitions(); 
		}
		for ( BaseColumnDefinition<DATA, ?> scn : actualColumnDefinitions){
			scn.setCurrentRowId(rowId);// plug row id ke column definition, agar mereka tahu id nya
			scn.extractAndPutDataToObject(data, jsObject);
		}
		NativeJsUtilities.getInstance().putVariableToWindowVariable("RAW_GRID_DATA", jsObject);
		
		return jsObject ; 
	}
	
	native void testCheckNativePutFault(JavaScriptObject targetObject)/*-{
		targetObject["wise"]="sabar";
	
	}-*/;
	native void echoContent(JavaScriptObject targetObject , String key)/*-{
		$wnd.console.log(key+ "::after all set>>"+ targetObject[key]);

	}-*/;
	
	native void putDebugerVariable(JavaScriptObject targetObject , String key)/*-{
		$wnd[key]=targetObject;
	
	}-*/;
	
	
	
	
	
	
	/**
	 * native js method<br/>
	 * worker untuk append data ke dalam grid. ini worker pemanggilan jquery grid
	 * @param gridId id grid. ini untuk locator jqgrid
	 * @param rowId id data yang akan di masukan ke dalam grid
	 * @param jsData data js yang di entry ke dalam grid
	 *  @category native jsni
	 **/
	protected native void addRowData(String gridId , String rowId  , JavaScriptObject jsData, String newRowPosition , String rowIdRelativeTo)/*-{
		//rowid, data, position, srcrowid
		//alert(gridId);
		$wnd["ADD_DEBUGER"]=jsData;
		$wnd.$("#"+ gridId).jqGrid("addRowData",rowId , jsData , newRowPosition , rowIdRelativeTo);
	}-*/;
	
	
	/**
	 * worker untuk membaca actual data dari grid, di cari berdasarkan row id 
	 * @param gridId pls pergunakan gridTableElement+ {@link Element#getId()} untuk id dari grid
	 * @param rowId id row. refer pada id row untuk data 
	 * @param originalDataKey pls pergunakan {@link #ORIGINAL_OBJECT_KEY_ON_RAW_JS}( value : {@value #ORIGINAL_OBJECT_KEY_ON_RAW_JS})
	 **/
	protected native DATA getRowDataByRowId (String gridId , String rowId , String originalDataKey)/*-{
		var swap = $wnd.$("#"+ gridId).jqGrid("getRowData",rowId );
		if ( swap==null)
			return null ;
		return 	swap[originalDataKey];
	
	}-*/;
	
	
	
	
	/**
	 * generate grid column definition yang akhirnya benar-benar di pergunakan. Use case yang mungkin : 
	 * <ol>
	 * 	<li>grid memiliki column nomor</li>
	 * </ol>
	 * override method ini kalau anda ada rencana semacam itu
	 **/
	protected BaseColumnDefinition<DATA, ?>[] getActualGridColumnDefinitions(){
		return this.getColumnDefinitions();
	}
	
	
	
	/**
	 * labels dari header. override kalau anda memerlukan customized header. Default nya header di ambil dari column definition
	 **/
	protected String[] getHeaderLabels(){
		String[] retval = new String[this.actualColumnDefinitions.length];
		int i =0;
		for (BaseColumnDefinition<DATA, ?> scn : actualColumnDefinitions){
			retval[i++]= scn.getActualToDisplayLabel();
		}
		return retval ;
	}
	
	
	protected void propagateSortChange(String rawJsFieldName , int columnIndex , String sortOrder  ){
		if ( sortOrderChangeHandler==null)
			return ;
		BaseColumnDefinition<DATA, ?> col = actualColumnDefinitions[columnIndex]; 
		sortOrderChangeHandler.onSortChange(col.getSortFieldName(), "asc".equalsIgnoreCase(sortOrder), col);
	}
	
	
	/**
	 * build column definitions array
	 **/
	@SuppressWarnings("unchecked")
	protected JsArray<JavaScriptObject> buildColumnDefinitions(){
		JsArray<JavaScriptObject> colDefs = (JsArray<JavaScriptObject>) JavaScriptObject.createArray();
		for (final BaseColumnDefinition<DATA, ?> scn: actualColumnDefinitions){
			colDefs.push(scn.generateRawGridColumnDefinition());
			scn.assignReplaceLabelWorker(new ReplaceLabelWorker() {
				@Override
				public void setLabel(String label) {
					setHeaderLabel(gridTableElement.getId(), scn.getRawJsDataName(), label);
				}
			});
		}
		
		return colDefs ;
	}
	
	
	
	
	/**
	 * build js function yang akan bind ke row select method
	 **/
	protected native JavaScriptObject buildSelectRowHandlerGlueMethod ()/*-{
		var swapThis= this;
		return function (rowid){
			
			swapThis.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::propagateRowSelectEvent(Ljava/lang/String;)(rowid);
		}
	
	}-*/;
	
	
	
	/**
	 * method glue untuk handler sort change. jadinya kalau header di click, sort berubah
	 **/
	protected native JavaScriptObject buildSortChangeEventHandlerGlueMethod()/*-{
		var swapThis=this ; 
		return function (index, colindex, sortorder){
			swapThis.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::propagateSortChange(Ljava/lang/String;ILjava/lang/String;)(index, colindex, sortorder);
		
		}
	
	}-*/;
	
	/**
	 * build double click row glue
	 **/
	protected native JavaScriptObject buildSelectDoubleClickHandlerGlueMethod ()/*-{
		var swapThis= this;
		return function (rowid){
			
			swapThis.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::propagateRowDoubleClickEvent(Ljava/lang/String;)(rowid);
		}
	}-*/;
	
	
	/**
	 * build seelct all event glu method. <br/>
	 * <i>Perhatian</i> method ini tidak di bind by default. jadinya cek pada sub class
	 * <br/>
	 * 
	 * 
	 * <table border="1" >
	 * 	<tr valign="top">
	 * 		<td >20 Aug 2012</td>
	 * 
	 * 		<td>Tipikal row select all pada jqgrid ternyata return string dengan delimiter <strong><i style="color:red;font-weight:strong">,</i></strong><br/>
	 * 
	 * Dengan begini ID virtual dari data row tidak boleh mengandung ,
	 * </td>
	 * </tr> 
	 * </table>
	 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
	 * @since 20-aug-2012
	 * 
	 * 
	 * 
	 **/
	protected native JavaScriptObject buildSelectAllHandlerGlueMethod()/*-{
		var swapThis= this;
		return function (rowidArray){
			if ( rowidArray==null||rowidArray.length==0)
				return ;
			
			swapThis.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::propagateSelectAllEventHandler(Ljava/lang/String;)(rowidArray+"");
		}
	
	}-*/;
	
	
	/**
	 * membaca original data dengan data row id
	 **/
	public DATA getDataByRowID(String rowId){
		return indexedDataById.get(rowId);
	}
	
	/**
	 * propagasi row doubleclick evenst
	 **/
	protected void propagateRowDoubleClickEvent(String rowId ){
		DATA orgData = getDataByRowID(rowId); 
		if ( orgData==null)
			return ; 
		for ( GridRowDoubleClickHandler<DATA> handler: this.doubleClickHandlers){
			handler.onCellSelect(rowId, orgData);
		}
	}
	
	
	/**
	 * propagasi row selection handler. jadinya di trigger pada saat 1 row di pilih
	 **/
	protected void propagateRowSelectEvent(String rowId ){
		
		DATA orgData = getDataByRowID(rowId); 
		if ( orgData==null){
			GWT.log("data null untuk row id : " + rowId +", propagasi select handler tidak di lakukan.JQGrid element id :" + gridTableElement.getId());
			return ; 
		}
		else{
			
		}
		
		for ( GridSelectRowHandler<DATA> handler: this.selectRowHandlers){
			
			handler.onSelectRow(rowId, orgData);
		}
	}
	
	
	
	/**
	 * propagasikan select all handler. pls be aware. select all tidak di implement pada {@link BaseJQGridPanel}. Pls pergunakan subclass untuk hal ini
	 **/
	protected void propagateSelectAllEventHandler(String param){
		if ( param==null|| param.length()==0)
			return ;
		
		String array[] = param.split(",");
		//String transform[] = new String[array.length()];
		ArrayList<DATA> arr= new ArrayList<DATA>();
		for ( int i = 0 ; i < array.length; i++){
			arr.add(getDataByRowID(array[i]));
		}
		for( GridSelectAllHandler<DATA> handler: this.selectAllRowHandlers){
			handler.onSelectAll(arr);
		}
		
	}
	
	//@Override
	protected String getJQWidgetId() {
		
		return gridTableElement.getId();
	}
	
	protected void setJQWidgetId(String id) {
		
		gridTableElement.setId(id);
		
	};
	
	
	
	
	
	
	
	
	
	/**
	 * spesial, dedicated untuk membuat hyperlink. agar tidak bingung dengan data yang di perlukan
	 * @param handler hadler click
	 *  
	 */
	protected StringColumnDefinition<DATA> generateHyperlinkCell( final  CellHyperlinkHandler<DATA> handler , String defaultLabel , String i18Code , int width){
		return generateCustomCell(handler , defaultLabel , i18Code , width );
	}
	
	
	
	/**
	 * membuat grid column custom. bisa di click
	 **/
	protected StringColumnDefinition<DATA> generateCustomCell( final  CellGenericHandler<DATA> handler , String defaultLabel , String i18Code , int width){
		@SuppressWarnings({  "unchecked" })
		CellGenericHandler<DATA>[] handlers = (CellGenericHandler<DATA>[]) new CellGenericHandler<?>[]{
			handler 	
		}; 
		return generateCustomCell(handlers , defaultLabel , i18Code , width ); 
	}
	
	/**
	 * membuat grid column custom. bisa di click
	 **/
	protected StringColumnDefinition<DATA> generateCustomCell( final  CellGenericHandler<DATA>[] handlers , String defaultLabel , String i18Code , int width){
		
		if ( !(handlers == null || handlers.length==0)){
			buildHyperlinkFacade(handlers);
			StringColumnDefinition<DATA> retval = new StringColumnDefinition<DATA>(  I18Utilities.getInstance().getInternalitionalizeText( i18Code ,  defaultLabel) , width) {
				
				@Override
				public String getData(DATA data) {
					 
					String currentRowId =  getCurrentRowId();
					 
					String retval = "" ;
					for (CellGenericHandler<DATA> scn : handlers  ){
						if ( retval.length()>0)
							retval +="&nbsp;";
						String methodToInvoke =  scn.generateMethodInvokeStatement(currentRowId);
						retval +=  scn.generateHTMLNode(data, methodToInvoke);
						
					}
					return retval;
				}
			}; 
			return retval ;
		}
		else{
			StringColumnDefinition<DATA> retval = new StringColumnDefinition<DATA>(  I18Utilities.getInstance().getInternalitionalizeText( i18Code ,  defaultLabel) , width) {
				@Override
				public String getData(DATA data) {
					return "&nbsp;";
				}
			}; 
			return retval ; 
		}
		 
	}
	
	
	private native void registerNativemethod ( String methodName)/*-{
		var methodGridCell = function (rowId ){
			
		
		}
	
	}-*/;
	
	 
	
	
	/**
	 * ini untuk menggenarate cell dengan tombol-tombol. tombol ini di buat dengan css. ikut model jquery ui icons
	 * @param buttonHandlers array of button handler, kalau di click mau ngapain di definsikan sendiri
	 * @param defaultLabel label default untuk grid header
	 * @param i18Code i18n code untuk header label
	 * @param width lebar default dari grid
	 * 
	 **/
	protected StringColumnDefinition<DATA> generateButtonsCell(final CellButtonHandler<DATA>[] buttonHandlers , String defaultLabel , String i18Code , int width){
		
		
		buildIconFacade(buttonHandlers);
		StringColumnDefinition<DATA> retval = new StringColumnDefinition<DATA>(  I18Utilities.getInstance().getInternalitionalizeText( i18Code ,  defaultLabel) , width) {
			@Override
			public String getData(DATA data) {
				if (buttonHandlers==null||buttonHandlers.length==0)
					return "";
				String retval="<ul id=\"icons\" class=\"ui-widget ui-helper-clearfix\">";
				for ( CellButtonHandler<DATA> prc : buttonHandlers){
					if ( !prc.isVisible()  || !prc.isDataAllowMeToVisible(data))
						continue ;// skip karena di hide
					retval+= prc.generateButtonNode(getCurrentRowId()) ; 
							
							
							//generateButtonNode(getCurrentRowId(),prc.getButtonCssName(),   prc.getDefaultIconTitle() , prc.getMethodNameIndexerKey());
				}
				retval +="</ul>";
				return retval;
			}
			
			@Override
			public GridHorizontalAlign getHorizontalAlign() {
				return GridHorizontalAlign.center;
			}
		};
		
		return retval ; 
	}
	
	
	
	
	
	/**
	 * mencari data yang di checked oleh user. method ini di akses dengan mengirimkan id dari data prefix. pls check method {@link #generateCheckbox(String, String, String, int)}
	 * @param checboxIdPrefix prefix id dari checkbox. ini di pergunakan kalau ada lebih dari 1 checkbox column
	 **/
	public List<DATA> getCheckedData (String additionalChecboxIdPrefix ){
		ArrayList<DATA> retval = new ArrayList<DATA>();
		for ( String key : indexedDataById.keySet()){
			String domID = checkboxIdPrefix + additionalChecboxIdPrefix +  key ;
			if (JQueryUtils.getInstance().isChecked(domID)){
				retval.add(  indexedDataById.get(key)); 
			}
		}
		return retval ; 
	}
	
	
	
	/**
	 * mengecek data dari radio button. mana yang di select. karena radio button cuma mengakomodir 1 data data di pilih. maka method ini return 1 data
	 * @param additionalRadioIdPrefix data prefix tambahan untuk radio
	 **/
	public DATA getCheckedRadioData (String additionalRadioIdPrefix ){
		for ( String key : indexedDataById.keySet()){
			String domID = radioIdPrefix + additionalRadioIdPrefix +  key ;
			if (JQueryUtils.getInstance().isChecked(domID)){
				return   indexedDataById.get(key); 
			}
		}
		return null ; 
	}
	
	
	 
	
	
	/**
	 * build checkbox.ada check
	 * @param checkboxIdAdditionalPrefix tambahan id. ini perlu anda masukan agar anda bisa mentrack balik item yang di check dengan method. check pada method {@link #getCheckedData(String)}
	 * @param handle yang akan meresponse mana yang di click
	 **/
	protected StringColumnDefinition<DATA> generateCheckbox(final String checkboxIdAdditionalPrefix ,   String defaultLabel , String i18Code , int width){
		return generateCheckbox(checkboxIdAdditionalPrefix, defaultLabel, i18Code, width , null);
	}
	
	
	
	/**
	 * versi ini , checkbox bisa mentrigger event kalau di click. anda bisa kombinasikan dengan {@link #getCheckedData(String)} untuk item mana saja yang di pilih
	 **/
	protected StringColumnDefinition<DATA> generateCheckbox(final String checkboxIdAdditionalPrefix ,   String defaultLabel , String i18Code , int width, Command clickHandler){
		return generateCheckbox(checkboxIdAdditionalPrefix , defaultLabel, i18Code, width , clickHandler , null ); 
	}
	
	
	/**
	 * versi ini , checkbox bisa mentrigger event kalau di click. anda bisa kombinasikan dengan {@link #getCheckedData(String)} untuk item mana saja yang di pilih
	 **/
	protected StringColumnDefinition<DATA> generateCheckbox(final String checkboxIdAdditionalPrefix ,   String defaultLabel , String i18Code , int width, Command clickHandler , final SimpleDataFilter<DATA > dataFilter){
		final boolean  requireClickHandler = clickHandler!= null ;
		final String actualMethodToHit =  this.checkboxClickHandlerMethodName + checkboxIdAdditionalPrefix  ; 
		final String clickStatement = requireClickHandler?  "onclick=\"" + actualMethodToHit + " ('" + checkboxIdAdditionalPrefix + "')\"" :""; 
		if ( requireClickHandler){
			JQueryUtils.getInstance().registerBridgeMethod(actualMethodToHit, clickHandler); 
			this.registerCheckboxClickHandler(actualMethodToHit); 
			indexedCheckboxClickHandler.put(checkboxIdAdditionalPrefix, clickHandler); 
		}
		
		
		StringColumnDefinition<DATA> retval = new StringColumnDefinition<DATA>(defaultLabel , width) {
			@Override
			public String getData(DATA data) {
				String rowId = indexedRowIdByObject.get(data);
				String hiddenMerker ="" ; 
				if ( dataFilter!= null && !dataFilter.allowToVisible(data)){
					hiddenMerker = " style='display:none' "; 
				}
				String retval="<input type=\"checkbox\" "+hiddenMerker+" id=\""+ checkboxIdPrefix + checkboxIdAdditionalPrefix +  rowId +"\" "+clickStatement+">";
				return retval;
			}
			@Override
			public GridHorizontalAlign getHorizontalAlign() {
				return GridHorizontalAlign.center;
			}
		};
		return retval;
	}
	
	
	
	
	/**
	 * check all/un check all  checkbox 
	 **/
	public void selectAllCheckbox (String checkboxIdAdditionalPrefix , boolean checked){
		if ( this.indexedDataById.isEmpty())
			return ; 
		for ( String rowId : indexedDataById.keySet()){
			String chkId = checkboxIdPrefix + checkboxIdAdditionalPrefix +  rowId ; 
			Element e =  DOM.getElementById(chkId);
			if  (e == null )
				continue ;
			e.setPropertyBoolean("checked", checked);
		}
		//checkboxIdPrefix + checkboxIdAdditionalPrefix +  rowId
	}
	
	
	/**
	 * radio button check
	 **/
	protected StringColumnDefinition<DATA> generateRadio( final String additionalRadioIdPrefix , String defaultLabel  ,   String i18Code , int width){
		return generateRadio(additionalRadioIdPrefix , defaultLabel ,i18Code , width , null );
	}
	
	/**
	 * versi ini denganhandler kalau data di click. jadinya ada informasi yang akan di event di trigger kalau tombol disabled
	 **/
	protected StringColumnDefinition<DATA> generateRadio( final String additionalRadioIdPrefix , String defaultLabel  ,   String i18Code , int width, Command radioClickHandler){
		
		final boolean  requireClickHandler = radioClickHandler!= null ;
		
		String actualMethodToHit = this.radioClickHandlerMethodName  + additionalRadioIdPrefix  ; 
		final String clickStatement = requireClickHandler?  "onclick=\"" +  actualMethodToHit+ " ('" + additionalRadioIdPrefix + "')\"" :""; 
		if ( requireClickHandler){
			this.registerRadioButtonClickHandler(actualMethodToHit);
			JQueryUtils.getInstance().registerBridgeMethod(actualMethodToHit, radioClickHandler); 
			indexedRadioColumnClickHandler.put(additionalRadioIdPrefix, radioClickHandler); 
		}
		StringColumnDefinition<DATA> retval = new StringColumnDefinition<DATA>(defaultLabel , width) {
			
			@Override
			public String getData(DATA data) {
				String rowId = indexedRowIdByObject.get(data);
				String retval="<input type=\"radio\" id=\""+ radioIdPrefix + additionalRadioIdPrefix +  rowId +"\" name=\""+radioName + "_" + additionalRadioIdPrefix
						+"\" "+clickStatement+">";
				return retval;
			}
			@Override
			public GridHorizontalAlign getHorizontalAlign() {
				return GridHorizontalAlign.center;
			}
		};
		return retval;
		
	}
	
	
	
	
	/**
	 * register click handler untuk radio button
	 **/
	private native void registerRadioButtonClickHandler( String functionName ) /*-{
		var swapThis = this ;
		var stringPrefix = this.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::radioClickHandlerMethodName ; 
		$wnd[functionName] = function (radioPrefix ) {
			var util = @id.co.gpsc.jquery.client.util.JQueryUtils::getInstance()();
			if ( util==null) {
				alert("util nya null"); 
			}
			util.@id.co.gpsc.jquery.client.util.JQueryUtils::triggerBridgeMethod(Ljava/lang/String;)( stringPrefix + radioPrefix); 
			 
		}
	
	}-*/;
	
	
	
	/**
	 * register click method untuk checkbox
	 **/
	private native void registerCheckboxClickHandler( String functionName ) /*-{
		var swapThis = this ;
		var stringPrefix = this.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::checkboxClickHandlerMethodName ;
		
		$wnd[functionName] = function (checkPrefix ) {
			var util = @id.co.gpsc.jquery.client.util.JQueryUtils::getInstance()();
			
			util.@id.co.gpsc.jquery.client.util.JQueryUtils::triggerBridgeMethod(Ljava/lang/String;)(stringPrefix + checkPrefix); 
		}
	
	}-*/;
	
	
	
	
	
	/**
	 * meneruskan click handler.ini di teruskan dari native js pada radio button
	 * @param radioButtonidPrefix radio button id prefix
	 **/
	protected  void propagateRadioClickHandler (String radioButtonidPrefix) {
		if ( indexedRadioColumnClickHandler.containsKey(radioButtonidPrefix))
			indexedRadioColumnClickHandler.get(radioButtonidPrefix).execute(); 
	}
	
	
	/**
	 * propagate click handler utnuk checkbox
	 **/
	public  void propagateCheckboxClickHandler (String radioButtonidPrefix) {
		if ( indexedCheckboxClickHandler.containsKey(radioButtonidPrefix))
			indexedCheckboxClickHandler.get(radioButtonidPrefix).execute(); 	
	}
	
	
	/**
	 * facade untuk prepare button handler
	 **/
	public void buildIconFacade(final CellButtonHandler<DATA>[] buttonHandlers){
		if (!IN_CELL_BUTTON_INITIALIZED){
			initiateCellClickGlobalVariableHolder(GRID_REFERENCE_MAP_NAME);
			IN_CELL_BUTTON_INITIALIZED=true;
		}
		
		for ( CellButtonHandler<DATA> scn : buttonHandlers ){
			String methodName = IN_CELL_BUTTON_METHOD_NAME_PREFIX + IN_CELL_BUTTON_METHOD_COUNTER;
			IN_CELL_BUTTON_METHOD_COUNTER++;// increment biar ndak ada yg bentrok
			putCellButtonClickHandler(GRID_REFERENCE_MAP_NAME, methodName);
			scn.setMethodNameIndexerKey(methodName);
			indexedProcessor.put(methodName, scn.getDataProcessor());
			scn.setActualIconTitle(generateIconTitle(scn));// generate icon, b
		}
		
	}
	
	/**
	 * facade untuk prepare button handler
	 **/
	public void buildHyperlinkFacade(final CellGenericHandler<DATA>[] hyperlinkHandlers){
		if (!IN_CELL_BUTTON_INITIALIZED){
			initiateCellClickGlobalVariableHolder(GRID_REFERENCE_MAP_NAME);
			IN_CELL_BUTTON_INITIALIZED=true;
		}
		for ( CellGenericHandler<DATA> scn : hyperlinkHandlers ){
			String methodName = IN_CELL_BUTTON_METHOD_NAME_PREFIX + IN_CELL_BUTTON_METHOD_COUNTER;
			IN_CELL_BUTTON_METHOD_COUNTER++;// increment biar ndak ada yg bentrok
			putCellButtonClickHandler(GRID_REFERENCE_MAP_NAME, methodName);
			indexedProcessor.put(methodName, scn );
			scn.setMethodOnClickName(methodName);
		}
		
	}
	
	
	
	
	/**
	 * worker untuk generate string <li> icon button </button>
	 **/
	protected String generateButtonNode (String rowDataId , String cssName ,   String iconTitle , String methodName  ){
	
		return "<li class=\"ui-state-default ui-corner-all\" title=\""+iconTitle+"\" onclick=\""+ methodName+"('"+ rowDataId+"','"+methodName+"')"  +"\"><span class=\"ui-icon "+cssName+"\"></span></li>";
		
	}
	
	
	 
	
	
	/**
	 * generate title dari icon
	 **/
	protected String generateIconTitle ( CellButtonHandler<DATA> proccessor ){
		//FIXME: title harus ambil dari i18 thing
		return proccessor.getDefaultIconTitle();
	}
	
	 
	
	 
	
	/**
	 * initiate task. jadinya di awal kita perlu membuat map untuk 2 item : 
	 * <ol>
	 * 	<li>javascript method, yang akan handler click tombol dalam cell</li>
	 * <li>reference ke grid. ini untuk kemudahan dalam proses (java code bikin processor dengan argument object)</li>
	 * </ol>
	 * @param clickHandlerMapName nama variable map untuk click handler
	 * @param gridReferenceMapName nama map untuk grid. yang di pergunakan adalah {@link BaseJQGridPanel#GRID_REFERENCE_MAP_NAME}
	 *  
	 **/
	protected native void initiateCellClickGlobalVariableHolder( String gridReferenceMapName)/*-{
		
		$wnd[gridReferenceMapName]={};
	
	}-*/;
	
	
	
	/**
	 * workr untuk menaruh click method dalam native js
	 * @param gridReferenceMapName nama map untuk grid reference,yang di pergunakan adalah {@link BaseJQGridPanel#GRID_REFERENCE_MAP_NAME}
	 * @param methodName ini gabungan antara : {@link BaseJQGridPanel#IN_CELL_BUTTON_METHOD_NAME_PREFIX} +  {@link BaseJQGridPanel#IN_CELL_BUTTON_METHOD_COUNTER} 
	 **/
	protected native void putCellButtonClickHandler(String gridReferenceMapName, String methodName )/*-{
		
		// dataRowId : id row yang di click
		// parameterKeyFromParamOnTag -> ada dalam argument click juga. ini method name na apa. ini akan sama dengan parameter methodName
		var mthd = function(dataRowId ){
			var grid =$wnd[gridReferenceMapName][methodName];
			// panggil saja id.co.sigma.jquery.client.grid.BaseJQGridPanel.triggerCellButtonClickHandler(String, String) biar dia yang solve the problem
			grid.@id.co.gpsc.jquery.client.grid.BaseJQGridPanel::triggerCellButtonClickHandler(Ljava/lang/String;Ljava/lang/String;)(dataRowId,methodName);
		};
		
		$wnd[gridReferenceMapName][methodName]=this;
		$wnd[methodName]=mthd;
		
	
	}-*/;
	
	
	
	
	/**
	 * membaca data selected data(dalam kasus multiple selection)
	 **/
	public   List<DATA>  getCurrentSelectedData ( ){
		JavaScriptObject jsArray = getCurrentSelectedDataWorker(getJQWidgetId());
		int panjang =  NativeJsUtilities.getInstance().countArrayLength(jsArray); 
		if ( panjang==0)
			return null ; 
		ArrayList<DATA> holder = new ArrayList<DATA>();
		for ( int i = 0 ; i<panjang ; i++){
			String id = NativeJsUtilities.getInstance().fetchStringData(jsArray, i); 
			DATA swap = this.getDataByRowID(id); 
			if ( swap!=null)
				holder.add(swap); 
		}
		return holder; 
		
	}
	
	/**
	 * worker untuk membaca data yang currently selected . ini bekerja dengan mempergunakan method <i>selarrrow</i>
	 * 
	 **/
	protected native JavaScriptObject getCurrentSelectedDataWorker (String gridId )/*-{
		return $wnd.$("#" +gridId ).jqGrid("getGridParam" , 'selarrrow');
	
	}-*/; 
	
	public void triggerCellButtonClickHandler(String dataRowId , String handlerIndexerId){
		DATA data= getDataByRowID(dataRowId); 
		if ( data==null){
			GWT.log("tidak ada object dengan id : "  + dataRowId + ",propagasi ke processor tidak di lakukan");
			return;
		}
		this.indexedProcessor.get(handlerIndexerId).runProccess(data);
		
	}
	
	
	/**
	 * command untuk reload grid
	 **/
	public void assignReloadCommand(IReloadGridCommand reloadCommand) {
		this.reloadCommand = reloadCommand;
		
	}
	
	/**
	 * handler sort change
	 **/
	public void assignSortOrderChangeHandler(
			ISortOrderChange<DATA> sortOrderChangeHandler) {
		this.sortOrderChangeHandler = sortOrderChangeHandler;
	}
	
	
	/**
	 * show/hide blocking panel
	 * @param display true => display blocker
	 **/
	public void showHideLoadingBlockerScreen(boolean display){
		try {
			showHideLoadingBlockerScreenWorker(gridTableElement.getId(), display);
		} catch (Exception e) {
			GWT.log("gagal show/hide grid blocker. error message : " + e.getMessage());
		}
		
	}
	
	/**
	 * worker untuk show/hide blocking screen di grid
	 * @param gridElementId id jquery
	 * @param display display/hide
	 *  
	 **/
	protected native void showHideLoadingBlockerScreenWorker(String gridElementId , boolean display)/*-{
		var mthd="show";
		if (!display){
			mthd="hide";
		}
		$wnd.$("#load_" + gridElementId)[mthd]();
		$wnd.$("#lui_" + gridElementId)[mthd]();
	}-*/;



	public HandlerRegistration addSelectAllHandler(final GridSelectAllHandler<DATA> handler) {
		if ( this.selectAllRowHandlers.contains(handler))
			return null; 
		selectAllRowHandlers.add(handler);
		return new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				selectAllRowHandlers.remove(handler);
			}
		};
	}
	
	
	
	
	/**
	 * 
	 * 
	 * method ini hanya bekerja kalau grid sudah di attach ke dalam container
	 * @param spaceLeavedOnRightSide ini kita mau menyisakan berapa pixel di sisi kanan grid
	 **/
	public void adjustGridToFitCurrentScreenSize ( int spaceLeavedOnRightSide , int minimumWidth ){
		if ( !isAttached())
			return ; 
		int lebarWIndow = Window.getClientWidth();
		int kiri =  this.getAbsoluteLeft();
		int widthToSet = lebarWIndow - spaceLeavedOnRightSide - kiri ; 
		if ( widthToSet< minimumWidth)
			widthToSet = minimumWidth ;
		setWidth(widthToSet);
	}





	public boolean isAppendRowNumberColumn() {
		return appendRowNumberColumn;
	}





	/**
	 * argument : rownumbers dalam jqgrid. menambahkan. ini bekerja hanya sebelum widget di attach ke container/ di render
	 * @param appendRowNumberColumn true -> ketambahan grid column number nantinya
	 */
	public void setAppendRowNumberColumn(boolean appendRowNumberColumn) {
		this.appendRowNumberColumn = appendRowNumberColumn;
		if ( this.jqueryRendered){
		}
		this.putConstructorArgument("rownumbers", appendRowNumberColumn);
	}
}
