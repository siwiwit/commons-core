package id.co.gpsc.common.client.security.function;

import id.co.gpsc.common.client.control.ITransformableToReadonlyLabel;
import id.co.gpsc.common.client.style.CommonResourceBundle;
import id.co.gpsc.common.security.domain.Function;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * tree item untuk menu selector
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class MenuSelectorTreeItem extends TreeItem implements ITransformableToReadonlyLabel{
	static String URL_IMAGE ; 
	static   String TREE_ITEM_TEMPLATE ; 
	
	static {
		URL_IMAGE =  AbstractImagePrototype.create(CommonResourceBundle.getResources().iconCheckedGreen() ).createImage().getUrl();
		TREE_ITEM_TEMPLATE ="<input type='checkbox' id=':ID' "
				+ " onclick=':CLICK_HANDLER'><img src='"+URL_IMAGE+"' id=':ID_IMG'  style=\"display:none\"/><span :TITLE> :LABEL</span>";
	}
	//AbstractImagePrototype.create(.
	//static final Imagere
	
	
	
	
	
	private String checkboxId = DOM.createUniqueId(); 
	
	
	private Function menu ; 
	static int MENU_COUNTER =1 ;
	static final String MENU_PREFIX="MENU_EDITOR_CLCK_";
	
	public MenuSelectorTreeItem(Function menuItem) {
		super(); 
		menu = menuItem ; 
		String mthd = MENU_PREFIX + MENU_COUNTER ; 
		MENU_COUNTER ++ ; 
		String actHtml = TREE_ITEM_TEMPLATE
				.replaceAll(":ID", checkboxId)
				.replaceAll(":CLICK_HANDLER", mthd +"()")
				.replaceAll(":LABEL", menuItem.getFunctionLabel())
				; 
		bindClickHandler(mthd);
		if ( menuItem.getFunctionCode()!= null){
			actHtml = actHtml.replaceAll(":TITLE","title='Menu code :" +  menuItem.getFunctionCode() +"'");
		}
		else{
			actHtml  = actHtml.replaceAll(":TITLE", "");
		}
		
		setHTML(actHtml);
	}
	
	private native void bindClickHandler (String  methodName) /*-{
		var ths = this ; 
		$wnd[methodName] = function () {
			ths.@id.co.gpsc.common.client.security.function.MenuSelectorTreeItem::clickHandler()(); 
		}
	
	}-*/; 
	/**
	 * disbale checkbox
	 */
	public void setEnabled ( boolean enabled) {
		DOM.getElementById(checkboxId).setPropertyBoolean("disabled", !enabled);
	}
	
	
	/**
	 * set item checked atau tidak
	 */
	public boolean isChecked () {
		return DOM.getElementById(checkboxId).getPropertyBoolean("checked");
	}
	
	
	
	boolean currentValue =false ; 
	
	
	/**
	 * membuat item di checked / tidak
	 */
	public void setChecked (boolean checked) {
		currentValue = checked ; 
		
		
		DOM.getElementById(checkboxId).setPropertyBoolean("checked" , checked);
		if ( checked)
			checkAllMatchingParentPath();
		else {
			uncheckAdditionalTask();
		}
	}
	
	
	
	protected void clickHandler () {
		Element e =  DOM.getElementById(checkboxId) ;
		if ( e.getPropertyBoolean("disabled"))
			return ; 
		if ( e.getPropertyBoolean("checked")){
			checkAllMatchingParentPath();
		}
		else{
			uncheckAdditionalTask();
		}
			
	}
	
	
	
	
	/**
	 * handle kalau ada perubahan pada nilai checked vs unchecked
	 */
	private void uncheckAdditionalTask () {
		MenuSelectorTreeItem bapak = (MenuSelectorTreeItem)getParentItem(); 
		if ( bapak!= null)
			bapak.uncheckIfNoneChildSelected();
	}
	
	/**
	 * uncheck semua parent. ini mengikuti tree nya
	 */
	protected void checkAllMatchingParentPath (  ){
		MenuSelectorTreeItem bpk = this ; 
		do {
			bpk = ( MenuSelectorTreeItem) bpk.getParentItem(); 
			if ( bpk== null)
				break ; 
			bpk.setChecked(true);
		}while ( bpk!= null); 
	}
	
	
	/**
	 * lawan dari {@link #checkAllMatchingParentPath()}, ini akan uncheck item yang tidak ada anak nya
	 */
	protected void uncheckIfNoneChildSelected (  ){
		if ( getChildCount()==0 )
			return ;
		boolean haveChecked = false ; 
		for ( int i = 0 ; i< getChildCount() ; i++){
			MenuSelectorTreeItem ank = (MenuSelectorTreeItem) getChild(i);
			if ( ank.isChecked()){
				haveChecked= true; 
				break ; 
			}
		}
		if ( !haveChecked){
			setChecked(false);
			MenuSelectorTreeItem bapak = (MenuSelectorTreeItem)getParentItem(); 
			if ( bapak!= null)
				bapak.uncheckIfNoneChildSelected();
		}
	}
	
	
	

	
	public Function getMenu() {
		return menu;
	}

	@Override
	public void restoreControl() {
		//DOM.getElementById(checkboxId).getStyle().setProperty("display", "");
		//DOM.getElementById(checkboxId + "_IMG").getStyle().setProperty("display", "none");
		if ( getChildCount()==0){
			setEnabled(true);
		}
	}

	@Override
	public void switchToReadonlyText() {
		setEnabled(false);
		/*DOM.getElementById(checkboxId).getStyle().setProperty("display", "none");
		if ( isChecked())
			DOM.getElementById(checkboxId + "_IMG").getStyle().setProperty("display", "");
		*/	
		
	}
	
	
}
