/**
 * 
 */
package id.co.gpsc.common.client.security.control;

import id.co.gpsc.common.security.domain.Function;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * component item tree menu untuk menangani item yang bisa berupa check box dan text
 * dan untuk mengetahui apakah item ini memiliki item child yang menunya selected
 * @author Dode
 * @version $Id
 * @since Jan 8, 2013, 4:55:09 PM
 */
public class ExtendedMenuTreeItem extends TreeItem {
	
	/**
	 * component check box tree item
	 */
	private CheckBox checkBox;
	
	/**
	 * child dari item yang terselected
	 */
	private int numOfSelectedChild;
	
	/**
	 * text dari item
	 */
	private String text;
	
	/**
	 * visibility dari checkboxnya
	 */
	private boolean checkBoxVisible = true;
	
	/**
	 * function yang diwakilkan oleh item (optional)
	 */
	private Function function;
	
	public ExtendedMenuTreeItem() {
		initializeField();
	}
	
	public ExtendedMenuTreeItem(String text) {
		initializeField();
		setText(text);
	}
	
	public ExtendedMenuTreeItem(String text, boolean visibleCheckBox) {
		initializeField();
		setCheckBoxVisbility(visibleCheckBox);
		setText(text);
	}
	
	/**
	 * inisialisasi variabel dengan nilai defaultnya
	 */
	private void initializeField() {
		checkBox = new CheckBox();
		numOfSelectedChild = 0;
		setWidget(checkBox);
	}
	
	/**
	 * increase number selected child
	 */
	public void incNumOfSelectedChild() {
		numOfSelectedChild++;
	}
	
	/**
	 * decrease number selected child
	 */
	public void decNumOfSelectedChild() {
		numOfSelectedChild--;
	}
	
	/**
	 * get number selected child
	 * @return
	 */
	public int getNumOfSelectedChild() {
		return numOfSelectedChild;
	}
	
	/**
	 * set checkbox visibility
	 * @param visible checkbox visibility
	 */
	public void setCheckBoxVisbility(boolean visible) {
		checkBoxVisible = visible;
		if (visible)
			setWidget(checkBox);
		else
			super.setText(text);
	}
	
	/**
	 * set field text
	 */
	@Override
	public void setText(String text) {
		//kalau checkbox not visible set text dengan text
		if (!checkBoxVisible)
			super.setText(text);
		checkBox.setText(text);
		this.text = text;
	}
	
	public Function getFunction() {
		return function;
	}
	
	public void setFunction(Function function) {
		this.function = function;
	}
	
	/**
	 * get visibility dari checkbox
	 * @return
	 */
	public boolean isCheckBoxVisible() {
		return checkBoxVisible;
	}
	
	/**
	 * get checkbox
	 * @return component checkbox
	 */
	public CheckBox getchCheckBox() {
		return checkBox;
	}
	
	/**
	 * get check box value
	 * @return check box value
	 */
	public boolean getCheckBoxValue() {
		return checkBox.getValue();
	}
	
	/**
	 * set check box value
	 * @param value check box value
	 */
	public void setCheckBoxValue(boolean value) {
		checkBox.setValue(value);
	}
	
}
