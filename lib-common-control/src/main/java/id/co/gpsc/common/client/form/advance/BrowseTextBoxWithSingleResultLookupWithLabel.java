package id.co.gpsc.common.client.form.advance;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.form.BrowseTextBoxWithSingleResultLookup;
import id.co.gpsc.common.control.ResourceBundleConfigurableControl;
import id.co.gpsc.common.data.SingleKeyEntityData;


/**
 * base lookup dengan single dialog box, + automated label
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public abstract class BrowseTextBoxWithSingleResultLookupWithLabel<KEY , ENTITY extends SingleKeyEntityData<KEY>> extends BrowseTextBoxWithSingleResultLookup<KEY, ENTITY> implements  ResourceBundleConfigurableControl , AutomaticLabeledControl{

	private String defaultLabel = "Lookup input";	
	private Boolean isPoint = true;
		
	private Element label;
	private Element point;
	protected String i18Key;
	
	
	/**
	 * Get default Label
	 * @return
	 */
	public String getDefaultLabel() {
		return defaultLabel;
	}

	/**
	 * Set default label
	 * @param defaultLabel
	 */
	public void setDefaultLabel(String defaultLabel) {
		this.defaultLabel = defaultLabel;
	}
	
	/**
	 * Getter untuk menampilkan titik dua
	 * @return True : Titik dua ditampilkan, False : titik dua tidak ditampilkan
	 */
	public Boolean getIsPoint() {
		return isPoint;
	}

	/**
	 * untuk menampilkan titik dua
	 * @param isPoint True : Titik dua ditampilkan, False : titik dua tidak ditampilkan
	 */
	public void setIsPoint(Boolean isPoint) {
		this.isPoint = isPoint;
	}
	
	
	@Override
	public void addCssToLabel(String cssName) {
		this.label.addClassName(cssName);
		
	}
	
	@Override
	public void addCssToPoint(String cssName) {
		this.point.addClassName(cssName);
	}
	
	
	@Override
	public void removeCssFromLabel(String cssName) {
		this.label.removeClassName(cssName);
		
	}
	
	@Override
	public void removeCssFromPoint(String cssName) {
		this.point.removeClassName(cssName);
		
	}

	@Override
	public String getCssToLabel() {		
		return this.label.getClassName();
	}

	@Override
	public void setCssToLabel(String cssName) {
		addCssToLabel(cssName);
	}

	@Override
	public String getCssToPoint() {		
		return this.point.getClassName();
	}

	@Override
	public void setCssToPoint(String cssName) {
		addCssToPoint(cssName);		
	}
	
	
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if ( label!=null)
			label.getStyle().setProperty("display", visible?"":"none");
		if ( point!=null)
			point.getStyle().setProperty("display", visible?"":"none");
	}
	
	
	@Override
	protected void onDetach() {		
		super.onDetach();
		if(isPoint){			
			point.removeFromParent();
		}
		label.removeFromParent();
	}
	
	@Override
	protected void onAttach() {		
		super.onAttach();		
		if(getElement().getId() == null || getElement().getId().length() == 0){
			getElement().setId(DOM.createUniqueId());
		}						
		List<Element> result = AdvanceControlUtil.getInstance().createdLabelAndPoint(getElement().getPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM), isPoint, defaultLabel, getElement());
		if(!result.isEmpty()){
			if(isPoint){
				this.point = result.get(0);
				this.label = result.get(1);
			}else{
				this.label = result.get(0);
			}
		}		
	}

	@Override
	public void setI18Key(String key) {
		this.i18Key=  key ; 
		getElement().setPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM, key);
	}


	@Override
	public String getI18Key() {
		return i18Key ; 
	}

	@Override
	public void setConfiguredText(String text) {
		if(label!=null)
			label.setInnerHTML(text);
		
	}
	
	
	public void setMyLabel(String defaultLabel){
		
		this.defaultLabel = defaultLabel ; 
	}
	
	
	public String getMyLabel(){
		return defaultLabel;
	}
}

