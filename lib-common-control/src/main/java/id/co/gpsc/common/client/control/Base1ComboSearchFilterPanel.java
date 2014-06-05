package id.co.gpsc.common.client.control;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

import id.co.gpsc.common.client.form.ExtendedComboBox;
import id.co.gpsc.common.client.form.ExtendedTextBox;
import id.co.gpsc.common.client.form.FloatTextBox;
import id.co.gpsc.common.client.form.IntegerTextBox;
import id.co.gpsc.common.client.widget.BaseSigmaComposite;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.form.JQDatePicker;



/**
 * panel search dengan 1 combo box untuk semua field search
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class Base1ComboSearchFilterPanel extends BaseSigmaComposite{

	/**
	 * table untuk form search, tambahkan di sini kalau perlu tambahan fields
	 **/
	protected FlexTable searchFormContainer ;
	
	protected ExtendedComboBox searchFilterComboBox= new ExtendedComboBox() ;
	
	protected ExtendedTextBox searchFilterTextBox = new ExtendedTextBox() ;
	protected IntegerTextBox searchFilterIntBox = new IntegerTextBox() ;
	protected FloatTextBox searchFilterFloatBox = new FloatTextBox();
	protected JQDatePicker searchFilterDateBox = new JQDatePicker() ;
	
	
	protected HasValue<?> currentEntry ; 
	
	protected HashMap<String, BaseSimpleSearchComboContentLocator> indexedLocator = new HashMap<String, BaseSimpleSearchComboContentLocator>();
	

	
	/**
	 * di index berapa search combo akan di render
	 **/
	protected int searchComboRowIndex = 0 ; 
	
	public Base1ComboSearchFilterPanel(){
		this(0);
		
	}
	
	
	public Base1ComboSearchFilterPanel(int comboRowIndex){
		this.searchComboRowIndex = comboRowIndex ;
		searchFormContainer = new FlexTable();
		
		initWidget(searchFormContainer);
		configureSearchPanel();
		configureAdditionalPanel(searchFormContainer);
	}
	
	
	
	/**
	 * produce filter dari selection saat ini
	 **/
	public SigmaSimpleQueryFilter[] generateDataFilterArguments() {
		if ( currentEntry==null)
			return null ; 
		Object val = currentEntry.getValue() ; 
		if ( val!= null){
			//FIXME: masukan enhacement ---> in , etc
			if ( indexedLocator.containsKey(this.searchFilterComboBox.getValue())){
				
				//SimpleSearchComboContentLocator searchContent =  indexedLocator.get(this.searchFilterComboBox.getValue()); 
				
				SigmaSimpleQueryFilter  v = new SigmaSimpleQueryFilter(); 
				v.setField( indexedLocator.get(this.searchFilterComboBox.getValue()).getQueryFilter());
				v.assignFilterWorker(val);
				if ( currentEntry.equals(this.searchFilterTextBox)){
					String filterAsString = (String) val ; 
					if ( filterAsString.length()==0)
						return null ;// filter kosong berati
					if ( filterAsString.contains("%"))
						v.setOperator(SimpleQueryFilterOperator.likePercentProvided);
					else {
						if(arr != null) {
							
							for(BaseSimpleSearchComboContentLocator locator : arr) {
								if(locator.getQueryFilter().equals(v.getField())) {
									v.setOperator(locator.getOperator() == null ? SimpleQueryFilterOperator.equal : locator.getOperator());
									break;
								}
							}
							
						} else {
							v.setOperator(SimpleQueryFilterOperator.equal);
						}
						
					}
						
				}else{
					
					if(arr != null) {
						
						for(BaseSimpleSearchComboContentLocator locator : arr) {
							if(locator.getQueryFilter().equals(v.getField())) {
								v.setOperator(locator.getOperator() == null ? SimpleQueryFilterOperator.equal : locator.getOperator());
								break;
							}
						}
						
					} else {
						v.setOperator(SimpleQueryFilterOperator.equal);
					}
				}
				
				return new SigmaSimpleQueryFilter[]{
					v 	
				};
			}
			
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 * configure additional control pada search panel
	 **/
	protected void configureAdditionalPanel(FlexTable targetTable) {
		
	}
	
	
	private boolean configured = false ; 
	
	BaseSimpleSearchComboContentLocator[] arr = null;
	
	/**
	 * trigger ini untuk reconfigure. ini 
	 */
	protected void configureSearchPanel() {
		
		try{
			
			if (! configured){
				searchFormContainer.setWidget(searchComboRowIndex, 0, searchFilterComboBox);
				searchFilterComboBox.addChangeHandler(new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent event) {
						
						renderSearchFilterEntryInputBox();
					}
				});
			}
			searchFilterComboBox.clear(); 
			arr = getSearchFilterContents(); 
			if ( arr!=null&& arr.length>0){
				if ( isPutNoneSelectionSearchContent()){
					String lbl = I18Utilities.getInstance().getInternalitionalizeText("global.mda.search.combo.none.selected"); 
					if ( lbl==null||lbl.length()==0)
						lbl="Please choose";
					searchFilterComboBox.addItem(lbl, "");
				}
				for (BaseSimpleSearchComboContentLocator scn : arr ){
					if ( indexedLocator.containsKey(scn.getQueryFilter()))
						continue ;
					indexedLocator.put(scn.getQueryFilter(), scn);
					String lbl = I18Utilities.getInstance().getInternalitionalizeText(scn.getI18Key());
					if ( lbl==null||lbl.length()>0)
						lbl = scn.getDefaultLabel();
					searchFilterComboBox.addItem(lbl, scn.getQueryFilter());
				}
				renderSearchFilterEntryInputBox();
				
			}
			else{
				GWT.log("locator lib common control 0 length");
			}
			
			
		}finally{
			configured = true ; 
		}
		
	}
	/**
	 * render search widget. jadinya textbox nya di ganti sesuai dengan tipe
	 **/
	protected void renderSearchFilterEntryInputBox(){
		String val =  searchFilterComboBox.getValue() ;
		HasValue<?> w = getSearchInputBox(val); 
		replaceSearchBoxWorker(w);
		
	}
	
	
	
	
	/**
	 * mencari control yang akan di taruh di sebelah combo box. override ini kalau anda memerlukan control custom untuk entry 
	 * @param selectorValue key dari selector combo box( di sisi kiri kontrol)
	 */
	protected HasValue<?> getSearchInputBox ( String selectorValue ){
		HasValue<?> w = this.searchFilterTextBox;
		if ( selectorValue==null||selectorValue.length()==0){
			GWT.log("combo selector tidak return value. exit now");
			return null; 
		}	
		if ( !indexedLocator.containsKey(selectorValue)){
			GWT.log("locator kosong untuk id : " + selectorValue + ", avaliable keyu : ");
						
			return null; 
		}
		Class<?> cls =  indexedLocator.get(selectorValue).getFilterClassType();
		
		if ( Date.class.equals(   cls) ) 
			w = this.searchFilterDateBox;
		else if ( Float.class.equals(cls))
			w = this.searchFilterFloatBox;
		else if ( Integer.class.equals(cls))
			w = this.searchFilterIntBox ;
		return  w ; 
	}
	
	
	
	/**
	 * menaruh control di sebelah combo box. ini otomatis akan mengganti current entry dengan control yang di di taruh di sebelah combo box
	 */
	private void replaceSearchBoxWorker (HasValue<?> control ){
		Widget current = null ; 
		try {
			if ( searchFormContainer.getCellCount(0)>1 )
				current=  this.searchFormContainer.getWidget(searchComboRowIndex, 1);
		} catch (Exception e) {
			GWT.log("gagal membaca data >>" + e.getMessage() , e);
		} 
		
		if ( current!=null)
			current.removeFromParent(); 
		this.currentEntry = control ; 
		this.searchFormContainer.setWidget(searchComboRowIndex, 1 , (Widget)control);
	}

	
	public void resetSearchForm() {
		this.searchFilterDateBox.getElement().setPropertyString("value", "");
		this.searchFilterFloatBox.getElement().setPropertyString("value", "");
		this.searchFilterIntBox.getElement().setPropertyString("value", "");
		this.searchFilterTextBox.getElement().setPropertyString("value", "");
		
	}

	
	
	
	/**
	 * locator searcg content
	 **/
	protected abstract BaseSimpleSearchComboContentLocator[] getSearchFilterContents () ; 
	
	
	/**
	 * boolean flag. kalau ini true, akan di tambahkan empty selection dalam combo box
	 * @return true -> berarti di tambahkan pilihan -> silakan pilih
	 **/
	protected abstract boolean isPutNoneSelectionSearchContent (); 
}
