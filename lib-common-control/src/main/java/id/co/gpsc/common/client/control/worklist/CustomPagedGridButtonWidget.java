package id.co.gpsc.common.client.control.worklist;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;

import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.jquery.client.grid.GridButtonWidget;




/**
 * handler tombol sisi bawah grid. ini contain tombol dan pager. 
 * panel ini mmepergunakan {@link GridPagingControl}
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * 
 **/
public class CustomPagedGridButtonWidget extends GridButtonWidget{

	
	
	
	private GridPagingControl gridPagingControl ; 
	/**
	 * page change handler
	 **/
	protected PageChangeHandler pageChangeHandler; 
	
	private Element gridTableElement ;
	
	
	
	 
	public CustomPagedGridButtonWidget(final Element gridTableElement,
			final Element gridButtonElement) {
		super(gridTableElement, gridButtonElement);
		this.gridTableElement   = gridButtonElement ; 
		
		
	}
	
	
	
	
	/**
	 * show Paging grid container 
	 */
	private void showPagingControlWrapperPanel () {
		Element elem =  DOM.getElementById(  gridTableElement.getId() + "_center");
		if ( elem== null){
			new Timer() {
				@Override
				public void run() {
					showPagingControlWrapperPanel();
				}
			}.schedule(500);
			return ; 
		}
		
		elem.getStyle().setProperty("display", "");
	}
	
	
	
	
	/**
	 * assign data, jadinya data paging akan di proses
	 * @param pagedData data paging
	 **/
	public <DATA>void assignData(PagedResultHolder<DATA> pagedData){
		 
		if(pagedData==null||pagedData.getTotalPage()==null||pagedData.getTotalPage().intValue()<=1){
			if ( gridPagingControl!= null){
				GWT.log("paging control di invis karena total page = null atau count < 0 ");
				gridPagingControl.setVisible(false);
			}
			return ;
		}
		
		if ( gridPagingControl==null){
			DOM.getElementById(this.gridButtonElement.getId() + "_center").setInnerHTML("");
			gridPagingControl = new GridPagingControl();
			RootPanel.get(/*this.gridButtonElement.getId() + "_center"*/).add(gridPagingControl);
			//DOM.appendChild(DOM.getElementById(this.gridButtonElement.getId() + "_center"), gridPagingControl.getElement());
			 DOM.getElementById(this.gridButtonElement.getId() + "_center").appendChild( gridPagingControl.getElement());
			
			GWT.log("Ok selesai membuat paging control");
			new Timer() {
				@Override
				public void run() {
					showPagingControlWrapperPanel();
				}
			}.schedule(500);
			
			if ( pageChangeHandler!=null)
				gridPagingControl.setPageChangeHandler(pageChangeHandler);
			
		}
		else{
			GWT.log("paging control ! null visible = true");
			showPagingControlWrapperPanel();
			gridPagingControl.setVisible(true);
			
		}
		gridPagingControl.adjustPaging(pagedData.getTotalPage(), pagedData.getPageSize(), pagedData.getPage());
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
		if ( gridPagingControl!=null)
			gridPagingControl.setPageChangeHandler(pageChangeHandler);
	}

	
	
	
}
