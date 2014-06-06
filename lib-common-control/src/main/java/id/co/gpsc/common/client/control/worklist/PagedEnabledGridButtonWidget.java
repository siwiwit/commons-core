package id.co.gpsc.common.client.control.worklist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;

import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.jquery.client.grid.GridButtonWidget;
import id.co.gpsc.jquery.client.util.JQueryUtils;


/**
 * ekspansi dari {@link GridButtonWidget}, dengan tambahan task untuk proses paging. paging by default akan di hide, karena asumsinya data di load dengan mekanisme GWT<br/>
 * Jadinya paging tidak bekerja dengan normal. method ini meng-override mekanisme paging
 *  
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 *@deprecated pergunakan  {@link CustomPagedGridButtonWidget}
 **/
@Deprecated
public class PagedEnabledGridButtonWidget extends GridButtonWidget{

	
	
	/**
	 * css untuk enable / disable icon
	 **/
	protected static final String DISABLE_BUTTON_CSS_NAME="ui-state-disabled";
	
	
	
	
	/**
	 * configure button, kalau di perlukan
	 **/
	private boolean pagingButtonConfigured =false ; 
	
	
	/**
	 * tombol next enabled atau tidak
	 **/
	protected boolean nextButtonEnabled =false ;
	
	/**
	 * prev button enabled atau tidak
	 **/
	protected boolean prevButtonEnabled =false ;
	
	/**
	 * posisi page saat ini
	 **/
	protected int currentPage =0; 
	
	
	/**
	 * ada berapa page yang ada
	 **/
	protected int totalPageCount =1; 
	/**
	 * enable last page
	 **/
	protected boolean lastPageButtonPageEnabled =false;
	
	
	/**
	 * page change handler
	 **/
	protected PageChangeHandler pageChangeHandler; 
	
	
	/**
	 * textbox current page
	 **/
	protected Element txtCurrentPage ;
	
	/**
	 * enable / tidak jump first page
	 **/
	protected boolean firstPageButtonEnabled=false ; 
	public PagedEnabledGridButtonWidget(Element gridTableElement,
			Element gridButtonElement) {
		super(gridTableElement, gridButtonElement);
	}

	
	
	/**
	 * assign data, jadinya data paging akan di proses
	 * @param pagedData data paging
	 **/
	public <DATA>void assignData(PagedResultHolder<DATA> pagedData){
		if(pagedData==null||pagedData.getTotalPage()==null||pagedData.getTotalPage().intValue()<=1){
			showHidePagingSide(false);
			
			return ;
		}
		
		
		showHidePagingSide(true);
		totalPageCount = pagedData.getTotalPage();
		currentPage = pagedData.getPage();
		configurePagingButtonIfNeeded();
		String val = (currentPage+1)+ "";
		DOM.setElementProperty((com.google.gwt.user.client.Element) txtCurrentPage, "value", val);
		
		enableDisablePrevButton(currentPage!=0);
		enableDisableNextRelatedButton(currentPage<(totalPageCount-1));
		String idPagger = "sp_1_" + gridButtonElement.getId() ;
		DOM.getElementById(idPagger).setInnerHTML(totalPageCount+"");
		
		
	}
	
	
	
	/**
	 * disable prev + first button
	 **/
	private void enableDisablePrevButton(boolean enabled){
		prevButtonEnabled=enabled; 
		firstPageButtonEnabled=enabled;
		if ( enabled){
			DOM.getElementById("first_" + gridButtonElement.getId()).removeClassName(DISABLE_BUTTON_CSS_NAME);
			DOM.getElementById("prev_" + gridButtonElement.getId()).removeClassName(DISABLE_BUTTON_CSS_NAME);
		}else{
			DOM.getElementById("first_" + gridButtonElement.getId()).addClassName(DISABLE_BUTTON_CSS_NAME);
			DOM.getElementById("prev_" + gridButtonElement.getId()).addClassName(DISABLE_BUTTON_CSS_NAME);
		}
	}
	
	
	/**
	 * disable next button+ first button
	 **/
	private void enableDisableNextRelatedButton(boolean enabled){
		nextButtonEnabled=enabled; 
		lastPageButtonPageEnabled =enabled;
		if ( enabled){
			DOM.getElementById("next_" + gridButtonElement.getId()).removeClassName(DISABLE_BUTTON_CSS_NAME);
			DOM.getElementById("last_" + gridButtonElement.getId()).removeClassName(DISABLE_BUTTON_CSS_NAME);
		}else{
			DOM.getElementById("next_" + gridButtonElement.getId()).addClassName(DISABLE_BUTTON_CSS_NAME);
			DOM.getElementById("last_" + gridButtonElement.getId()).addClassName(DISABLE_BUTTON_CSS_NAME);
		}
	}
	
	
	/**
	 * handler penekanan tombol next
	 **/
	protected void nextClickHandler(){
		if ( !nextButtonEnabled)
			return ; 
		if(pageChangeHandler==null){
			Window.alert("page change handler belum di definisikan");
			return ;
		}
		pageChangeHandler.onPageChange(currentPage+1);
	}
	
	/**
	 * handler button prev clicked
	 **/
	protected void prevButtonClickHandler(){
		if(!prevButtonEnabled)
			return ;
		if(pageChangeHandler==null){
			Window.alert("page change handler belum di definisikan");
			return ;
		}
		pageChangeHandler.onPageChange(currentPage-1);
	}
	
	/**
	 * handler penekanan tombol prev
	 **/
	protected void firstClickHandler(){
		if (!firstPageButtonEnabled)
			return ;
		if(pageChangeHandler==null){
			Window.alert("page change handler belum di definisikan");
			return ;
		}
		pageChangeHandler.onPageChange(0);
	}
	
	protected void lastClickHandler(){
		if(!lastPageButtonPageEnabled)
			return ;
		pageChangeHandler.onPageChange(totalPageCount-1);
	}
	
	
	
	
	/**
	 * kalau blm di konfigurasi method ini akan konfigurasi paging kontrol
	 *  
	 **/
	protected void configurePagingButtonIfNeeded(){
		if(pagingButtonConfigured)
			return ; 
		
		pagingButtonConfigured=true;
		//first
		JQueryUtils.getInstance().appendClickHandler("first_" + gridButtonElement.getId(), new Command() {
			@Override
			public void execute() {
				
				firstClickHandler();
			}
		});
		//prev
		JQueryUtils.getInstance().appendClickHandler("prev_" + gridButtonElement.getId(), new Command() {
			@Override
			public void execute() {
				
				prevButtonClickHandler();
			}
		});
		//next
		JQueryUtils.getInstance().appendClickHandler("next_" + gridButtonElement.getId(), new Command() {
			@Override
			public void execute() {
				
				nextClickHandler();
			}
		});
		
		JQueryUtils.getInstance().appendClickHandler("last_" + gridButtonElement.getId(), new Command() {
			@Override
			public void execute() {
				
				lastClickHandler();
			}
		});
		//TextBox.wrap(element)
		NodeList<Element> els = DOM.getElementById("pg_" + gridButtonElement.getId()).getElementsByTagName("input");
		if ( els!=null&&els.getLength()>0){
			txtCurrentPage = els.getItem(0);
			txtCurrentPage.setId("GPS_JQ_PAGING" + DOM.createUniqueId());
		}
		txtCurrentPage.getStyle().setHeight(20, Unit.PX);
		sinkKeyPressHandler(txtCurrentPage);
		 
	}


	
	/**
	 * handler kalau dalam tombol paging di tekan enter
	 **/
	protected void handleEnterPress(){
		String userEntry = DOM.getElementProperty((com.google.gwt.user.client.Element) txtCurrentPage, "value");
		if ( userEntry==null||userEntry.length()==0){
			resetPagertextboxToDefault();
			return ;
		}
		try {
			int pageAsInt = Integer.parseInt(userEntry)-1;
			if ( pageAsInt<0){
				resetPagertextboxToDefault();
				return ;
			}
			else if (pageAsInt>totalPageCount-1){
				resetPagertextboxToDefault();
				return ;
			}
			pageChangeHandler.onPageChange(pageAsInt);
			
		} catch (Exception e) {
			GWT.log("gagal handle key press.error : " + e.getMessage());
		}
		
		
	}
	
	/**
	 * ganti nilai textbox dengan nilai default ( {@link #currentPage} + 1) 
	 **/
	protected void resetPagertextboxToDefault(){
		DOM.setElementPropertyInt((com.google.gwt.user.client.Element) txtCurrentPage, "value", currentPage + 1 );
	}
	
	
	
	/**
	 * worker untuk menaruh data keypress event handler dalam textbox page
	 **/
	protected native void sinkKeyPressHandler(Element element)/*-{
		var thisRef = this ; 
		var mthd = function(e){
			if ( e.which==13||e.which==10)
				thisRef.@id.co.gpsc.common.client.control.worklist.PagedEnabledGridButtonWidget::handleEnterPress()();
		}
		
		$wnd.$(element).unbind("keypress");
		
		$wnd.$(element).keypress(mthd);
	
	}-*/;
	
	
	
	
	
	/**
	 * page change handler
	 **/
	public PageChangeHandler getPageChangeHandler() {
		return pageChangeHandler;
	}


	/**
	 * page change handler
	 **/
	public void setPageChangeHandler(PageChangeHandler pageChangeHandler) {
		this.pageChangeHandler = pageChangeHandler;
	}
}
