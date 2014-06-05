package id.co.gpsc.common.client.control.worklist;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;

import id.co.gpsc.common.client.widget.PageChangeHandler;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.jquery.client.grid.GridButtonWidget;
import id.co.gpsc.jquery.client.grid.SimpleGridPanel;



/**
 * grid dengan support paging. data di kirimkan ke dalam grid dengan wrapper : {@link PagedResultHolder}, grid di configure dengan informasi dalam data wrapper ini. Mohon jangan mempergunakan method 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class PagedSimpleGridPanel<DATA> extends SimpleGridPanel<DATA>{
	
	/**
	 * page change handler
	 **/
	private PageChangeHandler pageChangeHandler;
	/**
	 * ukuran page per pembacaan data
	 **/
	protected int pageSize = 10;
	/**
	 * page yang perlu di request ke server
	 **/
	protected int currentPageToRequest = 0;  
	
	 
	public PagedSimpleGridPanel(){
		super();
	}
	
	
	public PagedSimpleGridPanel( boolean multipleSelection){
		super(multipleSelection);
	}
	
	
	public PagedSimpleGridPanel( boolean multipleSelection, boolean renderOnAttach){
		super(multipleSelection, renderOnAttach);
	}
	
	/**
	 * inject data dalam bentuk wrapped data ke dalam grid
	 **/
	public void setData(PagedResultHolder<DATA> data){
		this.clearData();
		getGridButtonWidget().assignData(data);
		if ( data==null)
			return ; 
		this.firstRowNumberToRender = (data.getPage()* data.getPageSize()) + 1;
		//add data
		if ( data==null||data.getHoldedData()==null||data.getHoldedData().isEmpty()){
			return ; 
		}	
		for ( DATA scn : data.getHoldedData()){
			this.appendRow(scn);
		}
		
		
	}

	public PageChangeHandler getPageChangeHandler() {
		return pageChangeHandler;
	}

	
	/**
	 * set page change handler
	 **/
	public void setPageChangeHandler(PageChangeHandler pageChangeHandler) {
		this.pageChangeHandler = pageChangeHandler;
		getGridButtonWidget().setPageChangeHandler( new PageChangeHandler() {
			
			@Override
			public void onPageChange(int newPage) {
				currentPageToRequest=newPage;
				PagedSimpleGridPanel.this.pageChangeHandler.onPageChange(newPage);
			}
		} );
	}

	/**
	 * page yang perlu di request
	 **/
	public int getCurrentPageToRequest() {
		return currentPageToRequest;
	}

	/**
	 * ukuran page per pembacaan data
	 **/
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ukuran page per pembacaan data
	 **/
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@Override
	protected GridButtonWidget generateGridButtonWidget(
			Element gridTableElement, Element gridButtonElement) {
		
		CustomPagedGridButtonWidget swap = new CustomPagedGridButtonWidget(gridTableElement, gridButtonElement);
		if ( pageChangeHandler!=null)
			swap.setPageChangeHandler(pageChangeHandler);
		swap.setPageChangeHandler(new PageChangeHandler() {
			@Override
			public void onPageChange(int newPage) {
				currentPageToRequest =newPage;
			}
		});
		return swap;
	}

	@Override
	public CustomPagedGridButtonWidget getGridButtonWidget() {
		
		return (CustomPagedGridButtonWidget)super.getGridButtonWidget();
	}
	
	
	
	/**
	 * reset data grid. ini untuk mengosongkan data, ini juga menjamin proses penomoran data dalam row sinkron lagi. 
	 **/
	public void resetGrid() {
		clearData(); 
		this.currentPageToRequest=0 ; 
		this.getGridButtonWidget().showHidePagingSide(false);
	}
	
	
	/**
	 * brid method. sebenar nya ini mempergunakan variable 
	 * @param buttonCaption label untuk tombol. kosongkan kalau hanya mengiginkan button saja
	 * @param buttonIconClass icon class untuk tombol. di sarankan memakai salah satu dari Framework Icons (content color preview) 
	 * @param clickHandler handler pada saat tombol di click
	 */
	public   String appendButton(String buttonCaption , String buttonIconClass ,final  Command clickHandler){
		
		return this.getGridButtonWidget().appendButton(buttonCaption, buttonIconClass, clickHandler); 
		
	}
	
	
	/**
	 * menggerate text cell yang float di sisi kanan
	 */
	public String generateRightFloatingText ( String gridCellText ) {
		if ( gridCellText == null)
			gridCellText = "" ; 
		return "<div style='float:right'>" + gridCellText   + "</div>";
	}
	
	
}
