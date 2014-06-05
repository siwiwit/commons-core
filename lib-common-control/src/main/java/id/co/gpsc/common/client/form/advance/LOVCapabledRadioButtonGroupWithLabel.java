package id.co.gpsc.common.client.form.advance;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.form.LOVCapabledRadioButtonGroup;

/**
 * Komponent yg menampilkan Label, titik dua dan LOV Radio Button Group. Komponen ini harus di-wrapper dalam tag table<br>
 * <code> 		
 * 		< table ><br>
 * 			< tr ><br>
 * 				< td >< /td ><br>
 * 				< td >< /td><br>
 * 				< td >< p1:LOVCapabledRadioButtonGroupWithLabel ui:field="txtBoxTest2" /> < /td><br>
 * 			< tr ><br> 			
 * 		</ table>
 * </code>
 * @author I Gede Mahendra
 * @version $Id
 * @since 17 aug 2012
 */
public class LOVCapabledRadioButtonGroupWithLabel extends LOVCapabledRadioButtonGroup implements AutomaticLabeledControl{

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
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if ( label!=null)
			label.getStyle().setProperty("display", visible?"":"none");
		if ( point!=null)
			point.getStyle().setProperty("display", visible?"":"none");
	}
}
