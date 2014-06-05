package id.co.gpsc.common.client.control;

import java.util.ArrayList;

import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.data.SimpleKeyValue;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;

/**
 * kontrol dengan layout sbb : 
 * 
 * 
 * <table>
 * 	<tr>
 * 	<td>
 * <select multiple="multiple" style="height:80px">
 * 	<option>Sample 1</option>
 * <option>Sample 2</option>
 * <option>Sample 2</option>
 * <option>Sample 3</option>
 * <option>Sample 4</option>
 * <option>Sample 5</option>
 * <option>Sample 6</option>
 * <option>Sample 7</option>
 * <option>Sample 8</option>
 * 
 * </select>
 * </td><td>
 * <button>&lt;&lt;</button><br/>
 * <button>&lt;</button><br/>
 * <button>&gt;</button><br/>
 * <button>&gt;&gt;</button>
 * </td><td>
 * <select multiple="multiple"  style="height:80px">
 * 	<option>Sample 1</option>
 * <option>Sample 22</option>
 * <option>Sample 23</option>
 * <option>Sample 34</option>
 * <option>Sample 45</option>
 * 
 * </select>
 * 
 * </td>
 * 
 * </tr>
 * 
 * </table>
 * list dengan selector kanan dan kiri. kiri untuk list yang tersedia, kanan untuk item item yang sudah di pilih
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class DoubleListSelectorControl extends Composite  implements HasValue<String[]>  {
	
	
	
	protected HorizontalPanel outmostPanel;
	
	
	protected ListBox allNotSelected = new ListBox(true) ; 
	protected ListBox allSelected  = new ListBox(true); ; 
	
	
	protected ExtendedButton removeAllButton = new ExtendedButton();
	protected ExtendedButton removeButton = new ExtendedButton();
	protected ExtendedButton addAllButton = new ExtendedButton();
	protected ExtendedButton addButton = new ExtendedButton(">&nbsp;");
	
	
	
	private ArrayList<SimpleKeyValue> allPosibleValue = new ArrayList<SimpleKeyValue>();  
	
	
	
	private ArrayList<ValueChangeHandler<String[]>> changeHandlers = new ArrayList<ValueChangeHandler<String[]>>() ;
	
	//add by dode, dikeluarkan supaya bisa di hide
	protected VerticalPanel vpane;
	
	
	
	
	public DoubleListSelectorControl(){
		outmostPanel = new HorizontalPanel();
		outmostPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE); 
		initWidget(outmostPanel);
		
		outmostPanel.add(allNotSelected); 
		
		vpane = new VerticalPanel();
		removeAllButton.setHTML("&nbsp;&lt;&lt;"); 
		removeButton.setHTML("&nbsp;&lt;&nbsp;");
		addAllButton.setHTML("&nbsp;&gt;&gt;");
		addButton.setHTML("&nbsp;&gt;&nbsp;");
		
		addButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> selectedVals = new ArrayList<String>() ;
				try{
					for ( int i = 0 ; i< allNotSelected.getItemCount(); i++){
						if ( allNotSelected.isItemSelected(i))
							selectedVals.add(allNotSelected.getValue(i)); 
					}
					if (selectedVals.isEmpty())
						return ; 
					String[] currentVal  = getValue(); 
					if ( currentVal!= null && currentVal.length>0){
						for ( String scn : currentVal){
							selectedVals.add(scn); 
						}
					}
					String [] valToSet = new String[selectedVals.size()]; 
					for ( int i =0 ; i < selectedVals.size(); i++){
						valToSet[i] = selectedVals.get(i) ;
					}
					setValue(valToSet); 
 					
				}finally {
					selectedVals.clear(); 
				}
				
			}
		}); 
		
		removeButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ArrayList<String> remainSelectedItem  = new ArrayList<String>() ; 
				for ( int i = 0 ; i< allSelected.getItemCount(); i++){
					if ( !allSelected.isItemSelected(i))
						remainSelectedItem.add(allSelected.getValue(i)); 
				}
				String [] valToSet = new String[remainSelectedItem.size()]; 
				for ( int i =0 ; i < remainSelectedItem.size(); i++){
					valToSet[i] = remainSelectedItem.get(i) ;
				}
				setValue(valToSet); 
				
			}
		}); 
		
		removeAllButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				setValue(null); 
			}
		}); 
		addAllButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				String [] swapVal = new String[allPosibleValue.size()]; 
				for ( int i = 0 ; i < allPosibleValue.size(); i++){
					swapVal[i] = allPosibleValue.get(i).getKey(); 
				}
				setValue(swapVal); 
				
			}
		}); 
		
		vpane.add(removeAllButton); 
		vpane.add(removeButton);
		vpane.add(addButton);
		vpane.add(addAllButton);
		vpane.setSpacing(2); 
		outmostPanel.add(vpane); 
		outmostPanel.add(allSelected);
		setListNotSelectedWidth("100px");
		setListSelectedWidth("100px"); 
		setHeight("120px"); 
		
		
		
	}
	
	
	
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		allNotSelected.setHeight(height); 
		allSelected.setHeight(height); 
	}
	
	
	
	/**
	 * kosongkan semua
	 **/
	public void clear () {
		this.allNotSelected.clear(); 
		allPosibleValue.clear(); 
		allSelected.clear(); 
	}
	
	
	public void addItem ( String value , String label ){
		SimpleKeyValue s = new SimpleKeyValue(); 
		s.setKey(value); 
		s.setValue(label) ; 
		this.allPosibleValue.add(s);
		if ( isAttached())
			allNotSelected.addItem(label, value); 
		
	}

	/*
	@Override
	protected void onAttach() {
		super.onAttach();
		if ( !allPosibleValue.isEmpty()){
//			allPosibleValue.clear(); 
			allSelected.clear(); 
			setValue(null); 
		}
		
	}*/

	@Override
	public HandlerRegistration addValueChangeHandler(
			final ValueChangeHandler<String[]> handler) {
		changeHandlers.add(handler); 
		
		return new HandlerRegistration() {
			
			@Override
			public void removeHandler() {
				changeHandlers.remove(handler); 
			}
		};
	}
	
	
	
	private void fireChangeEvent () {
		if ( this.changeHandlers.isEmpty())
			return ;
		//FIXME : blm siap
		
	}


	@Override
	public String[] getValue() {
		int cnt =allSelected.getItemCount();  
		if ( cnt==0)
			return null;
		
		String [] retval = new String[cnt]; 
		for ( int i = 0 ; i < cnt; i++){
			retval[i] = allSelected.getValue(i); 
		}
		return retval ; 
	}


	@Override
	public void setValue(String[] value) {
		setValue(value , false); 
	}


	@Override
	public void setValue(String[] values, boolean fireEvents) {
		 if ( values== null|| values.length==0){
			allNotSelected.clear(); 
			allSelected.clear(); 
			for ( SimpleKeyValue scn : allPosibleValue){
				allNotSelected.addItem(scn.getValue(), scn.getKey());
			}
			 return  ; 
		 }
		try{
			allNotSelected.clear(); 
			allSelected.clear(); 
			if ( allPosibleValue.isEmpty())
				return ; 
			for ( SimpleKeyValue scn : allPosibleValue){
				String key = scn.getKey();
				boolean matchFound = false;
				if ( values!= null){
					if ( key== null){
						for ( String value : values){
							if ( value== null  ){
								matchFound = true ; 
								break ; 
							}
						}
					}else{
						for ( String value : values){
							if (key.equals(value) ){
								matchFound = true ; 
								break ; 
							}
						}
					}
				}
				
				if ( matchFound){
					allSelected.addItem(scn.getValue(), scn.getKey());
				}else{
					allNotSelected.addItem(scn.getValue(), scn.getKey());
				}
				
					 
			}
			
		}finally{
			if ( fireEvents)
				fireChangeEvent();
		}
		
		
	}
	
	
	
	 
	
	/**
	 * lebar dari selected item. sisi kanan
	 */
	public void setListSelectedWidth(String width) {
		this.allSelected.setWidth(width); 
	}
	
	
	
	/**
	 * lebar dari selected item. sisi kanan
	 */
	public String getListSelectedWidth() {
		return allSelected.getElement().getAttribute("width"); 
	}
	
	/**
	 * set lebar dari not selected item. ini item yang ada di sisi kiri
	 */
	public void setListNotSelectedWidth(String width) {
		this.allNotSelected.setWidth(width); 
	}
	/**
	 * set lebar dari not selected item. ini item yang ada di sisi kiri
	 */
	public String getListNotSelectedWidth() {
		return allNotSelected.getElement().getAttribute("width"); 
	}
	
	
	

}
