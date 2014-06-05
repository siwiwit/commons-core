package id.co.gpsc.jquery.client.grid;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;



/**
 * pemisahan pekerjaan. bagian ini akan handle manipulasi pada button widget. widget di sisi bawah grid
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 20-aug-2012
 **/
public class GridButtonWidget {
	
	
	private Element gridTableElement ;
	protected Element gridButtonElement;
	
	
	public GridButtonWidget(Element gridTableElement , Element gridButtonElement){
		this.gridButtonElement = gridButtonElement ;
		this.gridTableElement=gridTableElement;
	}

	
	
	/**
	 * append custom button actual worker
	 * @param buttonCaption label untuk tombol. kosongkan kalau hanya mengiginkan button saja
	 * @param buttonIconClass icon class untuk tombol. di sarankan memakai salah satu dari Framework Icons (content color preview) 
	 * @param clickHandler handler pada saat tombol di click
	 */
	public   String appendButton(String buttonCaption , String buttonIconClass ,final  Command clickHandler){
		String idButtonBaru = "GRID_BUTTON_" +  DOM.createUniqueId() ; 
		//appendButtonWorker(buttonCaption,  gridTableElement.getId(),gridButtonElement.getId(), buttonIconClass, clickHandler);
		appendButtonWorker(buttonCaption,  gridTableElement.getId(),gridButtonElement.getId(), idButtonBaru,buttonIconClass, clickHandler);
		return idButtonBaru ; 
	}

	
	
	
	/**
	 * hide paging control. kalau misalnya kosong, bisa jadi data musti di kosongkan saja<br/>
	 * <strong style="color:red">perhatian !</strong> fungsi ini cenderung bisa berbahaya kalau jqgrid berubah spesifikasinya
	 * @param show show/hide
	 **/
	public void showHidePagingSide(boolean show){
		DOM.getElementById(this.gridButtonElement.getId() + "_center").getStyle().setProperty("display", show?"":"none");
	}
	
	
	
	
	
	/**
	 * worker untuk render grid. ini untuk mengasi space holder custom button
	 **/
	public void renderEmptyButtonWidget() {
		
		hideAllBuiltinButton(this.gridButtonElement.getId(), this.gridTableElement.getId());
		new Timer() {
			@Override
			public void run() {
				showHidePagingSide(false);
			}
		}.schedule(250);
		
		
		
		//DOM.getElementById(this.gridButtonElement.getId() + "_right").appendChild(smpl);
		
		
	}
	
	/**
	 * worker untuk append button
	 * @param buttonCaption caption untuk button
	 * @param buttonId id button
	 * @param gridId id dari grid
	 * @param buttonIconClass icon untuk button
	 * @param clickHandler handler pada saat click di tekan
	 **/
	protected native  void appendButtonWorker(String buttonCaption  , String gridId , String buttonId , String buttonIconClass ,final  Command clickHandler)/*-{
		
		 
		buttonId ="#" +buttonId; 
		gridId ="#" + gridId;
		
		
		
		
		$wnd.$(gridId).navGrid(buttonId , { 
        	add:false,
        	edit:false,
        	del:false,
        	search:false,
        	refresh:false  
        }).navButtonAdd(
			buttonId, 
			{
				caption:buttonCaption , 
				buttonicon: buttonIconClass, 
				onClickButton:function(){
					if ( clickHandler!=null)
						clickHandler.@com.google.gwt.user.client.Command::execute()();
				}
		});
	}-*/;
	
	
	/**
	 * worker untuk append button
	 * @param buttonCaption caption untuk button
	 * @param buttonGroupId id button
	 * @param gridId id dari grid
	 * @param buttonIconClass icon untuk button
	 * @param clickHandler handler pada saat click di tekan
	 * @param buttonId id untuk tombol baru. ini dengan tujuan agar tombol bisa di manipulasi
	 **/
	protected native  void appendButtonWorker(String buttonCaption, String gridId , String buttonGroupId   ,String buttonId, String buttonIconClass ,final  Command clickHandler)/*-{
		
		 
		buttonGroupId ="#" +buttonGroupId; 
		gridId ="#" + gridId;
		
		
		
		
		$wnd.$(gridId).navGrid(buttonGroupId , { 
        	add:false,
        	edit:false,
        	del:false,
        	search:false,
        	refresh:false  
        }).navButtonAdd(
			buttonGroupId, 
			{
				caption:buttonCaption , 
				id: buttonId, 
				buttonicon: buttonIconClass, 
				onClickButton:function(){
					if ( clickHandler!=null)
						clickHandler.@com.google.gwt.user.client.Command::execute()();
				}
		});
	}-*/;
	
	
	
	/**
	 * hide semua tombol bawaan JQgrid. tombol akan di buat dengan custom
	 * @param gridId id grid
	 * @param buttonId id button panel
	 **/
	protected native  void hideAllBuiltinButton( String gridId  ,String buttonId )/*-{
		$wnd.$(gridId).navGrid(buttonId , { 
        	add:false,
        	edit:false,
        	del:false,
        	search:false,
        	refresh:false  
        });
	}-*/;
	
	
	
}
