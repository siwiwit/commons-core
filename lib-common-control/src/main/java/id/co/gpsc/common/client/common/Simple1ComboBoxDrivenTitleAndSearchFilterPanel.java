package id.co.gpsc.common.client.common;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;

import id.co.gpsc.common.client.control.Base1ComboSearchFilterPanel;
import id.co.gpsc.common.client.control.SimpleSearchFilterHandler;
import id.co.gpsc.common.client.form.ExtendedButton;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.util.I18Utilities;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class Simple1ComboBoxDrivenTitleAndSearchFilterPanel extends Base1ComboSearchFilterPanel implements ITitleAndSearchPanelFilter{

	
	private SimpleSearchFilterHandler searchFilterHandler ; 
	/**
	 * tombol search
	 **/
	protected ExtendedButton searchButton  ;
	
	
	
	
	/**
	 * tombol reset
	 **/
	protected ExtendedButton resetButton ; 
	
	/**
	 * label default untuk search 
	 **/
	protected abstract String getSearchButtonDefaultLabel () ; 
	
	
	
	/**
	 * key internalziation untuk tombol search 
	 **/
	protected abstract String getSearchButtonI18nKey () ;
	
	
	
	
	
	/**
	 * label default untuk reset
	 **/
	protected abstract String getResetDefaultLabel () ; 
	
	
	/**
	 * key i18n untuk tombol reset
	 **/
	protected abstract String getResetI18nKey () ;
	
	
	public Simple1ComboBoxDrivenTitleAndSearchFilterPanel(){
		super(1);
	}
	
	@Override
	protected void configureAdditionalPanel(FlexTable targetTable) {
		
		super.configureAdditionalPanel(targetTable);
		targetTable.getFlexCellFormatter().setColSpan(0, 0, 2);// untuk title panel
		targetTable.setHTML(0, 0, getHtmlForTitle());
		targetTable.getFlexCellFormatter().setColSpan(2, 0, 2);// untuk button panel
		FlowPanel buttonPanel = new FlowPanel();
		targetTable.setWidget(2, 0, buttonPanel);
		searchButton = new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getSearchButtonI18nKey(), getSearchButtonDefaultLabel()),new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				doSearchData();
			}
		}); 
		resetButton = new ExtendedButton(I18Utilities.getInstance().getInternalitionalizeText(getResetI18nKey(), getResetDefaultLabel()) , new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				resetSearchForm();
			}
		}); 
		buttonPanel.add(searchButton); 
		buttonPanel.add(resetButton);
		Element s = DOM.createSpan() ; 
		s.setInnerHTML("&nbsp;");
		buttonPanel.getElement().appendChild(s);
		
		
	}
	
	
	/**
	 * ini untuk meminta. ini bisa di trigger kalau misalnya search mau di run programmatically
	 */
	protected void doSearchData () {
		SimpleSortArgument [] sort = getDefaultSortArguments(); 
		SimpleQueryFilter[] a = generateDataFilterArguments(); 
		getSearchFilterHandler().applyFilter( a , sort);
	}
	
	
	/**
	 * html string untuk title. masukan tag HTML anda di sini. ini akan di render di atas search filter
	 **/
	protected abstract String getHtmlForTitle() ; 
	
	
	
	
	
	
	
	@Override
	public SimpleSearchFilterHandler getSearchFilterHandler() {
		return this.searchFilterHandler;
	}

	@Override
	public void setSearchFilterHandler(
			SimpleSearchFilterHandler searchFilterHandler) {
		this.searchFilterHandler = searchFilterHandler ; 
		
	}
	
	
	
	/**
	 * sort argument standard untuk data
	 */
	protected SimpleSortArgument [] getDefaultSortArguments () {
		return null ; 
	}
}
