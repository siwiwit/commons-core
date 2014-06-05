package id.co.gpsc.common.client.widget.dialog;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Composite untuk relative date
 * @author I Gede Mahendra
 * @since Jan 23, 2013, 6:50:40 PM
 * @version
 */
public class RelativeDateValueComposite extends Composite{
	
	private HorizontalPanel hpanel;
	private ListBox cmbOperator;
	private ListBox cmbParameter;
	private TextBox txtValue;
	
	/**
	 * Constructor
	 */
	public RelativeDateValueComposite() {
		hpanel = new HorizontalPanel();
		cmbOperator = new ListBox();
		cmbParameter = new ListBox();
		txtValue = new TextBox();
		
		cmbOperator.setWidth("50px");
		cmbParameter.setWidth("100px");
		txtValue.setWidth("50px");
		
		cmbOperator.addItem("+", RelativeOperatorEnum.Plus.toString());
		cmbOperator.addItem("-", RelativeOperatorEnum.Minus.toString());
		
		cmbParameter.addItem("Day", RelativeOperatorEnum.Day.toString());
		cmbParameter.addItem("Month", RelativeOperatorEnum.Month.toString());
		cmbParameter.addItem("Year", RelativeOperatorEnum.Year.toString());
		
		hpanel.setSpacing(2);
		hpanel.add(cmbOperator);		
		hpanel.add(cmbParameter);		
		hpanel.add(txtValue);
		
		initWidget(hpanel);
	}

	/**
	 * Show hide horizontal panel
	 * @param state - True:Show, False:Hide
	 */
	public void showPanelRelativeDate(boolean state) {
		hpanel.setVisible(state);
	}
	
	/**
	 * Clear control
	 */
	public void clearDataInControl(){
		cmbOperator.setSelectedIndex(0);
		cmbParameter.setSelectedIndex(0);
		txtValue.setValue("");
	}
	
	/*GETTER untuk control*/
		
	public ListBox getCmbOperator() {
		return cmbOperator;
	}

	public ListBox getCmbParameter() {
		return cmbParameter;
	}

	public TextBox getTxtValue() {
		return txtValue;
	}
}