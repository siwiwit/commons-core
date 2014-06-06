package id.co.gpsc.common.client.control.lookup;

import id.co.gpsc.common.client.control.Base1ComboSearchFilterPanel;
import id.co.gpsc.common.client.control.BaseSimpleSearchComboContentLocator;
import id.co.gpsc.common.client.control.worklist.PagedSimpleGridPanel;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;
import id.co.gpsc.jquery.client.grid.cols.BaseColumnDefinition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



/**
 * abstract class lookup dialog. mohon jangan pergunakan ini
 **/
public abstract class BaseSimpleLookupDialog<KEY,ENTITY> {
	
	/**
	 * penampungan sementara widget, agar semua event bekerja normal pada popup
	 **/
	protected static FlowPanel TEMPORAL_WIDGET_CONTAINER ; 
	
	protected VerticalPanel outmostContainer = new VerticalPanel();
	
	
	
	/**
	 * grid selector lookup
	 **/
	protected PagedSimpleGridPanel<ENTITY> selectorGrid ; 
	
	/**
	 * key internalization untuk label column <i>No</i>
	 **/
	protected abstract String getNoColumnI18Key() ; 
	
	
	/**
	 * search combo box
	 **/
	protected Base1ComboSearchFilterPanel searchComboBox ; 
	

	/**
	 * dialog untuk selector
	 **/
	protected JQDialog dialog = null ; 
	
	/**
	 * current page yang akan di tampilkan
	 **/
	protected int currentPage = 0 ; 
	
	/**
	 * button reset dan cari
	 */
	protected ExtendedButton btnCari,btnReset;
	
	
	
	public BaseSimpleLookupDialog(){
		if(TEMPORAL_WIDGET_CONTAINER==null){
			TEMPORAL_WIDGET_CONTAINER = new FlowPanel(); 
			RootPanel.get().add(TEMPORAL_WIDGET_CONTAINER);
			TEMPORAL_WIDGET_CONTAINER.setVisible(false);
		}
		
		selectorGrid = instantiateAndConfigureGrid();
		
		Integer lebarGrid = getDefaultGridWidth() ; 
		if ( lebarGrid!= null ){
			selectorGrid.setWidth(lebarGrid);
		}
		
		Integer tinggiGrid = getDefaultGridHeight(); 
		if ( tinggiGrid!= null){
			selectorGrid.setHeight(tinggiGrid);
		}
			
		
		searchComboBox = generateSearchComboBox();
		
		// grid
		outmostContainer.add(searchComboBox);
		
		outmostContainer.add(selectorGrid);
		
	}
	
	

	/**
	 * key internalization untuk title dari dialog(paling luar). 
	 * 
	 **/
	protected abstract String getLookupTitleI18Key() ; 
	
	/**
	 * key internalization untuk kasus blm ada yang di pilih
	 **/
	protected abstract String getNoneSelectedI18Key () ; 
	
	/**
	 * get i18 OK label
	 **/
	protected abstract  String getOkLabelI18Key();
	
	
	
	/**
	 * i18 key untuk cancel
	 **/
	protected abstract String getCancelLabelI18Key();
	

	
	
	
	/**
	 * class entity
	 **/
	protected abstract Class<? extends ENTITY> getLookupDataClass() ; 
	
	
	
	
	/**
	 * pls send RPC request di sini
	 * @param filters filters data
	 * @param page page berapa yang di ambil
	 * @param pageSize ukuran page per pembacaan
	 * @param callback callback untuk worker render data ke dalam grid selector
	 **/
	protected abstract void retrieveData(SimpleQueryFilter[] filters , int page , int pageSize , AsyncCallback<PagedResultHolder<ENTITY>> callback ) ; 
	
	
	
	/**
	 * default width dari popup. null berarti auto
	 **/
	protected abstract Integer getDefaultWidth () ; 
	
	
	/**
	 * ini untuk menaruh lebar grid. berapa lebar grid. kalau null, maka lebar grid tidak akan di set
	 */
	protected  Integer getDefaultGridWidth () {
		return null ;  
	}
	
	
	/**
	 * tinggi dari grid. ini di override kalau mau mengeset tinggi dari grid. kalau null maka akan di abaikan
	 */
	protected Integer getDefaultGridHeight () {
		return null ; 
	}
	/**
	 * label standard untuk popup
	 **/
	protected abstract String getDefaultPopupCaption() ; 
	
	/**
	 * key internalization untuk grid caption
	 **/
	protected abstract String getGridCaptionI18Key();
	
	
	
	/**
	 * i18 key untuk tombol cari
	 **/
	protected abstract String getSearchButtonLabelI18Key () ;
	
	/**
	 * default label untuk tombol cari
	 **/
	protected String getDefaultSearchButtonLabel () {
		return "Search";
	}
	
	
	
	
	/**
	 * get default reset button label
	 **/
	protected abstract String getResetButtonLabelI18Key () ;
	/**
	 * get default reset button label
	 **/
	protected String getDefaultResetButtonLabel () {
		return "Reset";
	}
	
	
	/**
	 * label standard untuk grid
	 **/
	protected abstract String getDefaultGridCaption() ; 
	
	
	/**
	 * menampilkan grid yang visible untuk lookup. apa saja yang bisa di pilih oleh user
	 **/
	protected abstract BaseColumnDefinition<ENTITY, ?> [] getLookupGridColumns (); 
	
	
	/**
	 * default label untuk tombol OK
	 **/
	protected  String getDefaultOKLabel (){
		return "Select";
	}
	
	
	/**
	 * default label untuk tombol Cancel
	 **/
	protected  String getDefaultCancelLabel (){
		return "Cancel";
	}
	
	public void showLookup(){
		if ( dialog==null){
			String title = getDefaultPopupCaption();
			String i18TitleLabel =  I18Utilities.getInstance().getInternalitionalizeText(getLookupTitleI18Key());
			if ( i18TitleLabel!=null&&i18TitleLabel.length()>0)
				title = i18TitleLabel ;
			dialog=new JQDialog(title, outmostContainer);
			
			String lblPilih = I18Utilities.getInstance().getInternalitionalizeText(getOkLabelI18Key());
			String lblBatal = I18Utilities.getInstance().getInternalitionalizeText(getCancelLabelI18Key());
			if ( lblPilih==null||lblPilih.length()==0)
				lblPilih = getDefaultOKLabel();
			if ( lblBatal==null||lblBatal.length()==0)
				lblBatal = getDefaultCancelLabel();
			dialog.appendButton(lblPilih, new Command() {
				
				@Override
				public void execute() {
						selectionDoneClickHandler(dialog); 
					}
				});
			dialog.appendButton(lblBatal, new Command() {
				
				@Override
				public void execute() {
					dialog.close();
				}
			});
			
			Integer lebar =  getDefaultWidth();
			if ( lebar!=null){
				System.out.println("set width menjadi :" + lebar);
				dialog.setWidth(lebar.intValue());
			}
			
			//dialog.appendButton(buttonLabel, buttonHandler)
		}
		
		dialog.show(true);
		
		
		
	}
	
	
	
	
	
	
	/**
	 * method handler yang akan di trigger kalau selection done handler
	 **/
	protected abstract void selectionDoneClickHandler (JQDialog dialogReference ) ; 

	
	protected Base1ComboSearchFilterPanel generateSearchComboBox(){
		return new Base1ComboSearchFilterPanel() {
			
			@Override
			protected boolean isPutNoneSelectionSearchContent() {
				return false;
			}
			
			@Override
			protected BaseSimpleSearchComboContentLocator[] getSearchFilterContents() {
				return generateSearchCriteriaLocators();
			}
			@Override
			protected void configureAdditionalPanel(FlexTable targetTable) {
				
				String lblCari = I18Utilities.getInstance().getInternalitionalizeText(getSearchButtonLabelI18Key());
				String lblReset = I18Utilities.getInstance().getInternalitionalizeText(getResetButtonLabelI18Key());
				
				if (lblCari==null||lblCari.length()==0)
					lblCari = getDefaultSearchButtonLabel();
				if (lblReset==null||lblReset.length()==0)
					lblReset = getDefaultResetButtonLabel();
				
				btnCari = new ExtendedButton(lblCari , new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent clickEvt) {
						currentPage = 0 ;
						reloadSearchResult();
					}
				});
				
				btnReset = new ExtendedButton(lblReset, new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent clickEvt) {
						resetSearchForm();
						//FIXME: panggil reload
						reloadSearchResult();
					}
				});
				targetTable.setWidget(0, 2, btnCari);
				targetTable.setWidget(0, 3, btnReset);
			}
		};
	}
	
	
	/**
	 * locators untuk search criteria
	 **/
	protected abstract BaseSimpleSearchComboContentLocator[] generateSearchCriteriaLocators () ;  
	
	
	
	
	
	
	/**
	 * flag multiple selection atau bukan
	 **/
	protected abstract boolean isMultipleSelction();  
	/**
	 *
	 * worker untuk instantiate grid selector. <ol>
	 * <li>untuk di peratikan, grid column definition diambil dari {@link #getLookupGridColumns()}</li>
	 * <li>kalau memerlukan custom, override method ini</li>
	 * </ol>
	 **/ 
	protected PagedSimpleGridPanel<ENTITY> instantiateAndConfigureGrid(){
		PagedSimpleGridPanel<ENTITY> actualSelectorGrid = new PagedSimpleGridPanel<ENTITY>(isMultipleSelction()) {
			@Override
			protected BaseColumnDefinition<ENTITY, ?>[] getColumnDefinitions() {
				BaseColumnDefinition<ENTITY, ?>[] tmp = BaseSimpleLookupDialog.this.getLookupGridColumns();
				
				@SuppressWarnings("unchecked")
				BaseColumnDefinition<ENTITY, ?>[] retval = (BaseColumnDefinition<ENTITY, ?>[])new BaseColumnDefinition<?, ?>[tmp.length+1];
				retval[0] = generateRowNumberColumnDefinition("No", 30, getNoColumnI18Key());
				int i =1 ;
				for (BaseColumnDefinition<ENTITY, ?> scn : tmp ){
					retval[i++]= scn; 
				}
				return retval;
				//return tmp;
			}
		};
		String gridCaption =I18Utilities.getInstance().getInternalitionalizeText(getGridCaptionI18Key());
		if ( gridCaption==null)
			gridCaption = getDefaultGridCaption();
		actualSelectorGrid.setCaption(gridCaption);
		actualSelectorGrid.setPageChangeHandler(new PageChangeHandler() {
			
			@Override
			public void onPageChange(int newPage) {
				currentPage = newPage ; 
				reloadSearchResult();
			}
		});
		
		
		return actualSelectorGrid ; 
	}
	
	
	

	/**
	 * worker untuk reload grid
	 **/
	protected void reloadSearchResult () {
		 
		System.out.println("lookup navigate ke : " + currentPage);
		this.retrieveData (generateDataFilters(), 
				currentPage,selectorGrid.getPageSize() , new AsyncCallback<PagedResultHolder<ENTITY>>() {
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("gagal membaca data lookup.error message : " + caught.getMessage() , caught);
			}
			@Override
			public void onSuccess(PagedResultHolder<ENTITY> dataResult) {
				
				renderDataToGrid(dataResult);
			}
		});
	}
	
	
	/**
	 * ini method yang akan menggenerate argument search, pergunakan ini kalau anda berniat melakukan override
	 **/
	protected SimpleQueryFilter[] generateDataFilters() {
		return  this.searchComboBox.generateDataFilterArguments() ; 
	}
	
	
	/**
	 **/
	protected int getPageSize () {
		return 10 ; 
	}
	
	
	
	/**
	 * method worker untuk render data ke grid
	 **/
	protected abstract void renderDataToGrid (PagedResultHolder<ENTITY> dataResult); 
	
	
	
	
	
	/**
	 * lebar dari grid. kalau return null berarti grid tidak akan di set sama sekali
	 */
	public Integer getGridWidth () {
		return null ; 
	}
	
	
	
	
	
	
	protected native void setMouseOver (String buttonId) /*-{
		$wnd.$("#"+buttonId).mouseover(function(){
			$wnd.$("#"+buttonId).removeClass("ui-state-hover");
		}) ;
	}-*/; 
	
}
