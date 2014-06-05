package id.co.gpsc.common.client.form.advance;

import id.co.gpsc.common.util.I18Utilities;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.VerticalAlign;
import com.google.gwt.user.client.DOM;

/**
 * Class util untuk custom control
 * @author I Gede Mahendra 
 * @since 16 Okt 2012
 */
public class AdvanceControlUtil {
		
	private final String ID_LABEL = "_label";
	private final String ID_POINT = "_point";
	private final String ID_UNIT = "_unit";
	private static AdvanceControlUtil instance;
	
	/**
	 * css marker mandatory
	 **/
	public static String MANDATORY_MARKER_CSS_NAME="mandatory-marker";
	
	
	/**
	 * ini title untuk mandatory marker, kalau di hover keluar tulisan apa
	 **/
	public static String MANDATORY_MARKER_TITLE_I18_KEY ="global.mandatory-marker-title";
	
	/**
	 * Generate label dan titik tua
	 * @param i18Key - i18 Key yg didaftarkan di database
	 * @param isPoint - flag utk titik dua. True:Titik dua isi, False:Titik dua tidak isi
	 * @param defautlLabel - Label default jika tidak ada i18 Key
	 * @param element - element parent	 
	 */
	public List<Element> createdLabelAndPoint(String i18Key, Boolean isPoint, String defaultLabel, Element element){
		List<Element> elementList = new ArrayList<Element>();
		
		String txtId = element.getId();
		Element parent = element.getParentElement();						
		if ("TD".equalsIgnoreCase(parent.getTagName())){
			Element elementTR = parent.getParentElement();
			NodeList<Element> allNodes =  elementTR.getElementsByTagName("TD");
			
			int tdIndex = -1 ;
			for ( int i=0; i<allNodes.getLength();i++){
				Element scn = allNodes.getItem(i);					
				if (parent.equals(scn)){
					tdIndex=i;
					break ;
				}
			}
			if (tdIndex>=0){				
				String labelOnInternalizationCache = "";
				if(i18Key != null && i18Key.trim().length()>0){
					labelOnInternalizationCache = I18Utilities.getInstance().getInternalitionalizeText(i18Key);
				}
				
				String actualLabelToWrite = "";
				if(labelOnInternalizationCache == null ||labelOnInternalizationCache.length()==0){
					actualLabelToWrite = defaultLabel;
				}else{
					actualLabelToWrite = labelOnInternalizationCache;
				}
				
				int minus = 0;
				if(isPoint){	
					Element point = DOM.createSpan();
					point.setInnerHTML(":");
					point.setId(element.getId() + ID_LABEL);					
					allNodes.getItem(tdIndex-1).appendChild(point);
					allNodes.getItem(tdIndex-1).getStyle().setVerticalAlign(VerticalAlign.TOP);
					elementList.add(point);
					minus = 2;
				}else{
					minus = 1;
				}
				
				Element label = DOM.createLabel();				
				label.setInnerHTML(actualLabelToWrite);				
				label.setAttribute("for", txtId);
				label.setId(element.getId() + ID_POINT);
				elementList.add(label);
				allNodes.getItem(tdIndex-minus).appendChild(label);
				allNodes.getItem(tdIndex-minus).getStyle().setVerticalAlign(VerticalAlign.TOP);// set vertical align top untuk : dan label
			}
		}
		return elementList;
	}

	/**
	 * Created Element Unit
	 * @param elementParent
	 * @param i18KeyUnit
	 * @param defaultUnit
	 * @param isLeft
	 * @param labelUnit
	 * @return
	 */
	public Element createdUnit(Element elementParent, String i18KeyUnit, String defaultUnit, boolean isLeft, Element labelUnit){		
		Element parent = elementParent.getParentElement();		
		labelUnit = DOM.createSpan();		
		labelUnit.setId(DOM.createUniqueId() + ID_UNIT);
		String fixLabelUnit = "";
		if(i18KeyUnit == null){
			fixLabelUnit = defaultUnit;			
		}else{
			fixLabelUnit = I18Utilities.getInstance().getInternalitionalizeText(i18KeyUnit);
		}
		
		labelUnit.setInnerHTML(fixLabelUnit);
		if(isLeft){ //posisi unit di kiri
			parent.insertBefore(labelUnit, elementParent);
		}else{ // posisi unit di kanan
			parent.insertAfter(labelUnit, elementParent);
		}
		return labelUnit;
	}
	
	
	/**
	 * Get Singleton Instance object CustomControlUtil
	 * @return CustomControlUtil
	 */
	public static AdvanceControlUtil getInstance(){
		if(instance == null){
			return new AdvanceControlUtil();
		}else{
			return instance;
		}
	}
	
	
	/**
	 * 
	 * ini menaruh marker mandatory pada <i>label</i>
	 * @return element marker. simpan ini untuk proses remove nya/hide
	 **/
	public Element  renderMandatoryMarker (Element labelElement) {
		Element span = DOM.createSpan();
		span.setClassName(MANDATORY_MARKER_CSS_NAME);
		span.setTitle(I18Utilities.getInstance().getInternalitionalizeText(MANDATORY_MARKER_TITLE_I18_KEY, "this fields is mandatory"));
		span.setInnerHTML("*");
		labelElement.appendChild(span);
		return span;
	}
	
	
	/**
	 * naruh mandatory marker setelah node
	 * @param textElement ini element input(bisa text, combo , checkbox) jadinya mandatory marker akan di taruh setelah elemen ini
	 * */
	public Element  renderMandatoryMarkerAfterNode (Element textElement) {
		Element span = DOM.createSpan();
		span.setClassName(MANDATORY_MARKER_CSS_NAME);
		span.setTitle(I18Utilities.getInstance().getInternalitionalizeText(MANDATORY_MARKER_TITLE_I18_KEY, "this fields is mandatory"));
		span.setInnerHTML("*");
		textElement.getParentElement().appendChild(span);
		return span;
	}
}