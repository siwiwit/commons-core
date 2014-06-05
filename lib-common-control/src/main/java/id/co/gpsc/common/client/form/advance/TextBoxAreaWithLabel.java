package id.co.gpsc.common.client.form.advance;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.form.ExtendedTextArea;
import id.co.gpsc.common.form.BaseFormElement;
import id.co.gpsc.common.util.I18Utilities;

/**
 * Komponent yg menampilkan Label, titik dua dan textbox area. Komponen ini harus di-wrapper dalam tag table<br>
 * <code> 		
 * 		< table ><br>
 * 			< tr ><br>
 * 				< td >< /td ><br>
 * 				< td >< /td><br>
 * 				< td >< p1:TextBoxAreaWithLabel ui:field="txtBoxTest2" /> < /td><br>
 * 			< tr ><br> 			
 * 		</ table>
 * </code>
 * @author I Gede Mahendra
 * @version $Id
 * @since 17 aug 2012
 */
public class TextBoxAreaWithLabel extends ExtendedTextArea implements AutomaticLabeledControl, BaseFormElement{
	
	private String defaultLabel = "Default Label";	
	private Boolean isPoint = true;		
	private Element label;
	private Element point;
	
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
			renderMandatoryMarker();
		}
	}
	
	/**
	 * override supaya render mandatory di parent ga dipake 
	 */
	@Override
	protected void baseMandatoryMarkerRenderer() { }
	
	/**
	 * worker untuk render mandatory marker
	 **/
	protected void renderMandatoryMarker () {
		if ( isMandatory()){
			if(mandatoryMarker==null)
				mandatoryMarker=AdvanceControlUtil.getInstance().renderMandatoryMarker(label);
			else
				mandatoryMarker.getStyle().setProperty("display", "");
		}
		else{
			if ( mandatoryMarker!=null)
				mandatoryMarker.getStyle().setDisplay(Display.NONE);
		}
	}
	
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
	public void setDomId(String id) {
		getElement().setAttribute("id", id);		
	}

	@Override
	public String getDomId() {
		return getElement().getAttribute("id");
	}
	@Override
	public String getControlBusinessName() {
		if (getI18Key()!=null&&getI18Key().length()>0)
			return I18Utilities.getInstance().getInternalitionalizeText(getI18Key());
		return null;
	}
	
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if ( label!=null) {
			label.getStyle().setProperty("display", visible?"":"none");
			if (visible)
				renderMandatoryMarker();
		}
		if ( point!=null)
			point.getStyle().setProperty("display", visible?"":"none");
	}
	
	@Override
	public void setMandatory(boolean mandatory) {
		super.setMandatory(mandatory);
		if ( this.isAttached()){
			renderMandatoryMarker();
		}
	}
	
	
	/**
	 * set label dari textbox
	 */
	public void setControlLabel (String label) {
		this.defaultLabel = label ; 
		if ( this.label!= null){
			this.label.setInnerHTML( label== null ? "" :  label);
		}
		
		
	}
}