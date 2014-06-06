package id.co.gpsc.common.client.control.worklist;

import id.co.gpsc.common.client.widget.BasePagingControl;
import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.jquery.client.util.NativeJsUtilities;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.TableLayout;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;



/**
 * control paging
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class GridPagingControl extends Widget implements BasePagingControl{
	
	protected static String TAG_PAGING_COMMAND ="pgcommand";
	protected static String TAG_BTN_ENABLED_FLAG="btnenabledstate";
	
	private Element tbody ;
	private Element tr ; 
	private Element tdFirst ;
	private Element tdPrev ;
	private Element tdNext ;
	private Element tdLast ;
	
	private Element pagingText ; 
	private Element pageTotalSpan ;
	
	
	private int totalPage;
	private int pageSize;
	private int currentPagePosition;
	
	
	/**
	 * page change handler
	 **/
	private PageChangeHandler pageChangeHandler ; 
	public GridPagingControl(){
		Element tbl = DOM.createTable(); 
		setElement(tbl);
		tbody = DOM.createElement("tbody");
		getElement().appendChild(tbody);
		tr = DOM.createElement("tr");
		tbody.appendChild(tr);
		// table node 
		configureTableNode();
		tdFirst = generateCommonArrowed("ui-icon-seek-first");// fist
		tdFirst.setAttribute(TAG_PAGING_COMMAND, "first");
		
		tdPrev = generateCommonArrowed("ui-icon-seek-prev");// prev
		tdPrev.setAttribute(TAG_PAGING_COMMAND, "prev");
		appendSeparator();
		generateCurrentPageControl();
		
		appendSeparator();
		tdNext = generateCommonArrowed("ui-icon-seek-next");// next
		tdNext.setAttribute(TAG_PAGING_COMMAND, "next");
		tdLast = generateCommonArrowed("ui-icon ui-icon-seek-end");// last
		tdLast.setAttribute(TAG_PAGING_COMMAND, "last");
		
		enableDisableButton(tdNext, true);
		enableDisableButton(tdPrev, true);
		
		sinkKeyPressHandler(pagingText);
		
		bindClickButtonHandler(tdFirst);
		bindClickButtonHandler(tdPrev);
		bindClickButtonHandler(tdNext);
		bindClickButtonHandler(tdLast);
		
		setVisible(false);
	}
	
	
	private void configureTableNode(){
		getElement().setAttribute("cellspacing", "0");
		getElement().setAttribute("cellpadding", "0");
		getElement().setAttribute("border", "0");
		getElement().getStyle().setTableLayout(TableLayout.AUTO);
		getElement().setClassName("ui-pg-table");
		
		
		
	}
	
	
	private void appendSeparator(){
		Element retval = DOM.createElement("td");
		retval.addClassName("ui-pg-button");
		retval.addClassName("ui-state-disabled");
		retval.getStyle().setWidth(4	, Unit.PX);
		retval.getStyle().setCursor(Cursor.AUTO);
		tr.appendChild(retval);
		Element spn = DOM.createSpan();
		retval.appendChild(spn);
		spn.addClassName("ui-separator");
		
	}
	@Override
	public void setVisible(boolean visible) {
		GWT.log("ada perintah untuk visible :" + visible);
		super.setVisible(visible);
	}
	
	private Element generateCommonArrowed(String iconCss){
		Element retval = DOM.createElement("td");
		tr.appendChild(retval);
		retval.addClassName("ui-pg-button");
		retval.addClassName("ui-corner-all");
		
		retval.getStyle().setCursor(Cursor.DEFAULT);
		
		Element spn = DOM.createSpan();
		spn.addClassName("ui-icon");
		spn.addClassName(iconCss);
		retval.appendChild(spn);
		return retval ; 
	}
	
	
	
	
	
	/**
	 * naruh click listener pada tombol
	 **/
	protected native void bindClickButtonHandler(Element element)/*-{
		var swapThis=this ;
		$wnd.$(element).click(function (){
				
				
				var tag =  element.getAttribute(@id.co.gpsc.common.client.control.worklist.GridPagingControl::TAG_PAGING_COMMAND);
				
				if (tag=="first")
					swapThis.@id.co.gpsc.common.client.control.worklist.GridPagingControl::navigateFirst()();
				if (tag=="prev")
					swapThis.@id.co.gpsc.common.client.control.worklist.GridPagingControl::navigatePrev()();
				if (tag=="next")
					swapThis.@id.co.gpsc.common.client.control.worklist.GridPagingControl::navigatenext()();
				if (tag=="last")
					swapThis.@id.co.gpsc.common.client.control.worklist.GridPagingControl::navigateLast()();			
				
			});
	
	}-*/;
	
	/**
	 * tipu-tipu
	 **/
	protected  void enableDisableButton(Element element , boolean enabled){
		element.setAttribute(TAG_BTN_ENABLED_FLAG, enabled + "");
		enableDisableButtonNativeWorker(element, enabled);
		
	}
	protected native void enableDisableButtonNativeWorker(Element element , boolean enabled)/*-{
		var swapThis=this;
		if ( enabled){
			
			
			$wnd.$(element).hover(function(){
				$wnd.$(element).addClass("ui-state-hover")
			}, 
			function(){
				$wnd.$(element).removeClass("ui-state-hover")
			});
			$wnd.$(element).removeClass("ui-state-disabled")
		}
		else{
			$wnd.$(element).unbind("hover");
			$wnd.$(element).addClass("ui-state-disabled")
		}	
		
	}-*/;
	
	void generateCurrentPageControl (){
		Element td = DOM.createElement("td");
		tr.appendChild(td);
		//FIXME : ini musti i18 friendly
		td.appendChild(NativeJsUtilities.getInstance().createTextNode("Halaman"));
		td.setDir("ltr");
		pagingText = DOM.createInputText();
		td.appendChild(pagingText);
		
		
		
		pagingText.setAttribute("size", "2");
		pagingText.setAttribute("maxlength", "7");	
		pagingText.setAttribute("value", "0");
		pagingText.getStyle().setHeight(20, Unit.PX);
		pagingText.getStyle().setWidth(80, Unit.PX);
		
		pagingText.setAttribute("role", "textbox");
		
		
		td.appendChild(NativeJsUtilities.getInstance().createTextNode(" dari "));
		pageTotalSpan = DOM.createSpan();
		td.appendChild(pageTotalSpan);
		pageTotalSpan.setInnerHTML("0");
	}


	@Override
	public void adjustPaging(int totalPage, int pageSize,
			int currentPagePosition) {
		
		this.totalPage=totalPage; 
		this.pageSize = pageSize ; 
		this.currentPagePosition=currentPagePosition;
		pageTotalSpan.setInnerHTML(totalPage + "");
		String val = (currentPagePosition + 1) + ""; 
		pagingText.setPropertyString("value", val);
		
		
		boolean visible = totalPage> 1  ;
		
		
		GWT.log("Render paging dengan totalPage: " + totalPage  + ",pageSize:" + pageSize + ", currentPagePosition:" + currentPagePosition +", visible : " + visible);
		
		
		NativeJsUtilities.getInstance().writeToBrowserConsole("write paging text box menjadi :" + val + " total page >" + totalPage);
		//pagingText.setAttribute("value", val);
		  
		
		setVisible( visible); 
			
		if ( currentPagePosition==0){
			enableDisableButton(tdFirst, false);
			enableDisableButton(tdPrev, false);
		}
		else{
			enableDisableButton(tdFirst, true);
			enableDisableButton(tdPrev, true);
		}
		if ( currentPagePosition>=totalPage-1){
			enableDisableButton(tdLast, false);
			enableDisableButton(tdNext, false);
		}else{
			enableDisableButton(tdLast, true);
			enableDisableButton(tdNext, true);
		}
	}
	
	
	protected void navigateFirst(){
		if ( ("" +true).equals(tdFirst.getAttribute(TAG_BTN_ENABLED_FLAG)))
			pageChangeHandler.onPageChange(0);
	}
	protected void navigatePrev(){
		if ( ("" +true).equals(tdPrev.getAttribute(TAG_BTN_ENABLED_FLAG)))
			pageChangeHandler.onPageChange(currentPagePosition-1);
	}
	protected void navigatenext(){
		if ( ("" +true).equals(tdNext.getAttribute(TAG_BTN_ENABLED_FLAG)))
			pageChangeHandler.onPageChange(currentPagePosition+1);
	}
	
	protected void navigateLast(){
		if ( ("" +true).equals(tdLast.getAttribute(TAG_BTN_ENABLED_FLAG)))
			pageChangeHandler.onPageChange(totalPage-1);
	}

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
	
	/**
	 * worker untuk menaruh data keypress event handler dalam textbox page
	 **/
	protected native void sinkKeyPressHandler(Element element)/*-{
		var thisRef = this ; 
		var mthd = function(e){
			if ( e.which==13||e.which==10)
				thisRef.@id.co.gpsc.common.client.control.worklist.GridPagingControl::handleEnterPress()();
		}
		
		$wnd.$(element).unbind("keypress");
		
		$wnd.$(element).keypress(mthd);
	
	}-*/;
	
	
	/**
	 * handler kalau dalam tombol paging di tekan enter
	 **/
	protected void handleEnterPress(){
		String userEntry =   pagingText.getPropertyString(  "value");
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
			else if (pageAsInt>totalPage-1){
				resetPagertextboxToDefault();
				return ;
			}
			pageChangeHandler.onPageChange(pageAsInt);
			
		} catch (Exception e) {
			GWT.log("gagal handle press.error : " + e.getMessage());
		}
		
		
	}
	
	void resetPagertextboxToDefault(){
		  pagingText.setPropertyString(  "value" , (currentPagePosition+1) + "");
	}

}
