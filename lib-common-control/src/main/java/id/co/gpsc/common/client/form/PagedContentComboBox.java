package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.client.widget.PageChangeHandler;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

public class PagedContentComboBox extends  ListBox{


	
	private final String NAVIGATE_NEXT_KEY = "NEXT";
	
	private final String NAVIGATE_PREV_KEY = "PREV";
	
	private int internalComboPagePosition = 1 ; 
	
	private int internalTotalPage = 1 ; 
	
	
	
	private Image loadingImage ; 
	/**
	 * total data yang di tampilkan dalam 1 paging. default 100
	 **/
	private int numberDisplayedPage = 100 ; 
	
	/**
	 * total data yang ada untuk di tampilkan as paging control
	 **/
	private int totalDataPage; 
	
	
	private Integer currentPagePosition;  
	
	
	
	/**
	 * basis paging 0 atau 1 . 
	 **/
	private boolean use0PagingForIndex = true ; 
	
	
	/**
	 * handler kalau page change
	 **/
	private PageChangeHandler pageChangeHandler; 
	
	
	private int rpcPagingSize =10; 
	
	public PagedContentComboBox(){
		addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				if (  getSelectedIndex()==-1)
					return ; 
				String val = getValue(getSelectedIndex()); 
				 
				setEnabled(false); 
				loadingImage.setVisible(true);
				
				
				if (NAVIGATE_PREV_KEY.equalsIgnoreCase(val) ){
					 
					int pageDataBaru = (internalComboPagePosition-1)*numberDisplayedPage-1; 
					setTotalDataPage(totalDataPage,rpcPagingSize ,  pageDataBaru); 
				}else if ( NAVIGATE_NEXT_KEY.equalsIgnoreCase(val)){
					String valPrev = getValue(getSelectedIndex()-1); 
					
					int pageDataBaru = Integer.parseInt(valPrev) +1 ;  //internalComboPagePosition*numberDisplayedPage+1;
					setTotalDataPage(totalDataPage, rpcPagingSize , pageDataBaru);
				}
				else{
					//TODO: masukan paging di sini
					int pagePosition = Integer.parseInt(val); 
					if (pageChangeHandler!=null )
						pageChangeHandler.onPageChange(pagePosition); 
				}
				
			}
		}); 
		loadingImage= AbstractImagePrototype.create(  CommonResourceBundle.getResources().iconLoadingWheel()).createImage() ;
		loadingImage.setVisible(false);
		RootPanel.get().add(loadingImage); 
	}
	
	
	/**
	 * assign page change handler
	 * @param pageChangeHandler handler untuk page change
	 **/
	public void assignPageChangeHandler(PageChangeHandler pageChangeHandler) {
		this.pageChangeHandler = pageChangeHandler;
	}
	/**
	 * set berapa page total data. 
	 * @param totalPage total data dari pemanggilan rpc 
	 * @param pageSize page size request data ke rpc. bisa jadi beda dengan internal page
	 * @param currentPagePosition posisi page yang sedang render
	 **/
	public void setTotalDataPage (int totalPage , int pageSize  , int currentPagePosition ){
		this.totalDataPage = totalPage ; 
		this.rpcPagingSize = pageSize ;
		this.currentPagePosition = currentPagePosition; 
		  
		
		 internalTotalPage = (int)Math.ceil(	((float)totalPage * (float)pageSize)/(float)numberDisplayedPage);
		 internalComboPagePosition = (int)Math.ceil(	(float)this.currentPagePosition/(float)numberDisplayedPage)-1;
		 if ( internalComboPagePosition<0)
			 internalComboPagePosition = 0 ; 
		 // render dulu combo content
		 this.clear();// kosongkan 
		 int selectedIndex = 0  ;
		 
		 
		  
		 
		 
		 GWT.log("called dengan totalPage:"+ totalPage +",currentPagePosition:" + this.currentPagePosition +",internalComboPagePosition:"+internalComboPagePosition +",internalTotalPage:" + internalTotalPage +",numberDisplayedPage:" +numberDisplayedPage) ;
		 if ( internalComboPagePosition>1){
			 this.addItem("prev"  , NAVIGATE_PREV_KEY );
			 selectedIndex= 1; 
		 }
		 int maxData = totalPage< numberDisplayedPage? totalPage: numberDisplayedPage; 
		 int adder = ((internalComboPagePosition )  * numberDisplayedPage) + 1 ;
		 setEnabled(false); 
		 this.loadingImage.setVisible(true);
		 for( int i = 0 ; i< maxData ; i++){
			 int printedOne = i+ adder ;
			 String asString = printedOne +""; 
			 String pagingValue = (printedOne-1) +""; 
			 addItem(asString , pagingValue); 
			  
			 if ( (printedOne-1)==currentPagePosition){
				 selectedIndex= i;
			 } 
		 }
		// if ( internalComboPagePosition<internalTotalPage)
			// this.addItem("next"  , NAVIGATE_NEXT_KEY );
		 setSelectedIndex(selectedIndex);
		 
		 new Timer() {
			@Override
			public void run() {
				loadingImage.setVisible(false);
				setEnabled(true);
			}
		}.schedule(200);
		  
		 
	}
	
	
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		getParent().getElement().insertAfter(this.loadingImage.getElement()  , getElement()); 
	}
	
	
	@Override
	protected void onDetach() {
		getParent().getElement().removeChild(this.loadingImage.getElement());
		super.onDetach();
	}
	
	
	
	
	
	
	
	
	/**
	 * total data yang di tampilkan dalam 1 paging. default 100
	 **/
	public void setNumberDisplayedPage(int numberDisplayedPage) {
		this.numberDisplayedPage = numberDisplayedPage;
	}
	
	
	/**
	 * basis paging 0 atau 1 . 
	 * @param use0PaginginIndex true -> basis paging = 0  
	 * 
	 **/
	public void setUse0PagingForIndex(boolean use0PaginginIndex) {
		this.use0PagingForIndex = use0PaginginIndex;
	}
	/**
	 * basis paging 0 atau 1 .true = basis 0 , false = basis 1 
	 **/
	public boolean isUse0PagingForIndex() {
		return use0PagingForIndex;
	}


}
