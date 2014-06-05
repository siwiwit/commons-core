package id.co.gpsc.common.client.form;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;







/**
 * combo box. dedicated di buat dengan simple set value
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa(gede.sutarsa@gmail.com)</a>
 * @version $Id
 **/
public class ExtendedListBox extends BaseExtendedListbox  implements HasValue<String[]>  {
	
	
	
	private ArrayList<ValueChangeHandler<String[]>> changeHandlers = new ArrayList<ValueChangeHandler<String[]>>(); 
	
	public ExtendedListBox(){
		super(true); 
		addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				if (changeHandlers.isEmpty())
					return ;
				String [] vals = getValue(); 
				
				for ( ValueChangeHandler<String[]> handler : changeHandlers){
					
				}
				
			}
		}); 
	}
	
	
	
	

	@Override
	public boolean validateMandatory() {
		
		return false;
	}





	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<String[]> handler) {
		
		//FIXME: change handler blm di siapkan 
		return null;
	}





	@Override
	public String[] getValue() {
		ArrayList<String > holder = new ArrayList<String>() ; 
		for ( int i = 0 ;i< getItemCount(); i++){
			if (this.isItemSelected(i))
				holder.add(getValue(i)); 
		}
		if ( holder.size()==0 )
			return null ; 
		String [] retval = new String[holder.size()]; 
		holder.toArray(retval); 
		return retval;
	}


	
	
	/**
	 * propagasi event ke listeners
	 **/
	protected void fireEvent ( String[] values ){
		//TBD
	}



	@Override
	public void setValue(String[] value) {
		setValue(value , false ); 
	}





	@Override
	public void setValue(String[] value, boolean fireEvents) {
		if (value== null|| value.length==0){
			for ( int i = 0 ;i< getItemCount(); i++){
				setItemSelected(i, false);  
			}
		}else{
			for ( int i = 0 ;i< getItemCount(); i++){
				boolean matchFound =false ;
				String val = getValue(i); 
				if ( val== null){
					for ( String scn : value){
						if (scn== null){
							matchFound = true ; 
							break ;
						}
					}
				}else{
					for ( String scn : value){
						if ( val.equals(scn)){
							matchFound = true ; 
							break ; 
						}
					}
				}
				
				setItemSelected(i, matchFound);  
			}
		}
		
	}

	 
	
}
