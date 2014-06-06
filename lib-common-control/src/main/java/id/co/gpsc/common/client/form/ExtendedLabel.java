package id.co.gpsc.common.client.form;


import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.ChildSelfRegisterAggregator;
import id.co.gpsc.common.client.control.ChildSelfRegisterWorker;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.control.ResourceAwareControlSelfRegisterWorker;
import id.co.gpsc.common.client.control.i18.I18TextEditorPanel;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.util.I18Utilities;
import id.co.gpsc.jquery.client.container.JQDialog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;



/**
 * wrapper label(form label)
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @author <a href="dein.mahendra@gmail.com">Gede Mahendra</a>
 * @version $Id
 * @since 6 aug 2012
 * 
 **/
public class ExtendedLabel extends Widget implements HasText , SelfRegisterEnableResourceBundleConfigurableLabel,OnScreenConfigurableControl{
	
	/**
	 * key untuk internalization
	 **/
	public static final String I18_KEY="i18";
	
	/**
	 * id on screen configuration
	 **/
	private String onScreenConfigurationId  ; 
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	private String parentFormConfigurationId; 
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	@Override
	public String getParentFormConfigurationId() {
		return parentFormConfigurationId;
	}
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	@Override
	public void setParentFormConfigurationId(String parentFormId) {
		this.parentFormConfigurationId = parentFormId ; 
		
	}
	
	/**
	 * resource bundle container
	 **/
	private ResourceBundleEnableContainer resourceBundleEnableContainer ; 
	
	/**
	 * internalization thing. di register on attach ke parent atau tidak. pls berhati-hati untuk set ini =true.bisa bermasalah terhadap performence
	 **/
	private boolean registerI18OnAttach = true ;
	
	
	
	/**
	 * key internalization
	 **/
	private String i18Key;
	
	
	
	/**
	 * icon untuk proses edit
	 **/
	private Element editorIcon =null; 
	
	
	/**
	 * tempat menaruh text
	 **/
	private Element textSpan ;
	/**
	 * container icon
	 **/
	private Element iconSpan ; 
	
	
	private Widget targetWidget ;
	
	
	
	
	public ExtendedLabel(){
		setElement(DOM.createLabel());
		textSpan = DOM.createSpan();
		iconSpan = DOM.createSpan();
		getElement().setId("EXTENDED_LABEL-" + DOM.createUniqueId());
		getElement().appendChild(textSpan);
		getElement().appendChild(iconSpan);
		
		textSpan.getStyle().setFloat(Float.LEFT);
		textSpan.getStyle().setDisplay(Display.INLINE);
		iconSpan.getStyle().setFloat(Float.RIGHT);
		iconSpan.getStyle().setDisplay(Display.INLINE);
		
	}
	
	
	
	/**
	 * konstruksi widget dengan label
	 * @param label string label untuk widget 
	 **/
	public ExtendedLabel(String label){
		this() ; 
		this.setText(label);
	}
	
	
	@Override
	public void setText(String text) {
		if ( this.i18Key!=null&&i18Key.length()>0){
			if ( setLabelFromi18Worker(i18Key))
				return ; 
		}
		this.textSpan.setInnerHTML(text);
		
	}
	
	
	
	
	/**
	 * ini versi safe terhadap HTML/xml
	 */
	public void setInnerText(String text) {
		if ( this.i18Key!=null&&i18Key.length()>0){
			if ( setLabelFromi18Worker(i18Key))
				return ; 
		}
		this.textSpan.setInnerHTML(text);
		
	}
	
	
	
	@Override
	public String getText() {
		return getElement().getInnerText();
	}

	
	
	
	/**
	 * set id dari node. bagus untuk automated test
	 **/
	public void setDomId(String id){
		 
		this.ensureDebugId(id);
	}
	
	
	
	
	
	
	/**
	 * get id dari node
	 **/
	public String getDomId(){
		return getElement().getId();
		 
	}

	public Widget getTargetWidget() {
		return targetWidget;
	}


	public void setTargetWidget(Widget targetWidget) {
		this.targetWidget = targetWidget;
	}
	
	
	
	
	
	
	/**
	 * set id element target. element ini di set untuk element mana. efeknya kalau di klik elemen akan fokus ke elemen tsb
	 **/
	public void setForTargetElementId (String targetElementId){
		
		getElement().setAttribute("for", targetElementId);
	}
	
	
	/**
	 * getter. elemen focus ke elemen mana
	 **/
	public String setForTargetElementId (){
		return getElement().getAttribute("for");
	}

	
	@Override
	public void registerToContainer() {
		
		@SuppressWarnings("unchecked")
		ChildSelfRegisterAggregator<ExtendedLabel> agg = new ChildSelfRegisterAggregator<ExtendedLabel>(new ChildSelfRegisterWorker[]{
			new ResourceAwareControlSelfRegisterWorker()
		}, this);
		agg.registerToParent();
	}

	//@Override
	public void registerToContainerOld() {
		Element current =  getElement().getParentElement();
		int maxLoop = 1000;
		int i=0;
		while (current!=null ){
			try {
				Object swap =  current.getPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF);
				if ( swap!=null&&swap instanceof ResourceBundleEnableContainer){
					registerToContainer((ResourceBundleEnableContainer)swap);
					return ; 
				}
			} catch (Exception e) {
				
			}
			if ( i>= maxLoop)
				break ;
			i++;
			current = current.getParentElement() ; 
		}
	}


	@Override
	public void registerToContainer(
			ResourceBundleEnableContainer targetContainer) {
		targetContainer.registerResourceBundleEnabledNode(this);
		this.resourceBundleEnableContainer = targetContainer ; 
	}


	@Override
	public void setConfiguredText(String text) {
		this.textSpan.setInnerHTML(text);
		
	}

	/**
	 * internalization thing. di register on attach ke parent atau tidak. pls berhati-hati untuk set ini =true.bisa bermasalah terhadap performence
	 **/
	@Override
	public void setRegisterOnAttach(boolean registerOnAttach) {
		this.registerI18OnAttach = registerOnAttach ; 
	}

	/**
	 * internalization thing. di register on attach ke parent atau tidak. pls berhati-hati untuk set ini =true.bisa bermasalah terhadap performence
	 **/
	@Override
	public boolean isRegisterOnAttach() {
		return registerI18OnAttach;
	}
	
	@Override
	protected void onAttach() {
		
		super.onAttach();
		if ( getElement().getId()==null)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		if ( this.registerI18OnAttach){
			registerToContainer();
			
		}
	}
	@Override
	protected void onDetach() {
		super.onDetach();
		if ( this.resourceBundleEnableContainer!=null&&registerI18OnAttach){
			this.resourceBundleEnableContainer.unregisterResourceBundleEnabledNode(this);
			this.resourceBundleEnableContainer=null ;
		}
	}


	@Override
	public void setI18Key(String key) {
		this.i18Key = key ;
		getElement().setPropertyString(I18_KEY, key);
		//ini langsung ambil key dari internalization
		setLabelFromi18Worker(key);
		String lbl =  I18Utilities.getInstance().getInternalitionalizeText(key);
		if ( lbl!=null&&lbl.length()>0){
			setText(lbl);
		}
	}
	
	
	
	
	private boolean setLabelFromi18Worker(String key){
		if ( key!=null && key.length()>0){
			try {
				String labelText =  I18Utilities.getInstance().getInternalitionalizeText(key);
				//FIXME: masukan dev mode checker
				if ( labelText!=null && labelText.length()>0){
					setConfiguredText(labelText);
					return true ; 
				}
			} catch (Exception e) {
				e.printStackTrace();
					
			}
			
		}
		return false ;
	}


	@Override
	public String getI18Key() {
		return i18Key;
	}


	@Override
	public void showHideEditConfigButton(boolean show) {
		if ( show){
			if ( editorIcon==null){
				GWT.log("create editor icon. untuk label ..");
				editorIcon=DOM.createSpan();
				editorIcon.setClassName("ui-state-default ui-corner-all ui-state-hover ui-icon ui-icon-comment");
				iconSpan.appendChild(editorIcon); 
				iconSpan.getStyle().setCursor(Cursor.POINTER);
				Event.sinkEvents(iconSpan, Event.ONCLICK);
				Event.setEventListener(iconSpan, new EventListener() {
					@Override
					public void onBrowserEvent(Event event) {						
						if(Event.ONCLICK==event.getTypeInt()){
							showFormEdit();
						}						
					}
				});				
			}
			editorIcon.getStyle().setProperty("display", "");
		}
		else{
			if ( editorIcon!=null)
				editorIcon.getStyle().setDisplay(Display.NONE);
		}				
	}
	
	/**
	 * Menampilkan form editor localizatio utk label
	 */
	public void showFormEdit(){			
		JQDialog dialog = new JQDialog("I18 Text Editor", new I18TextEditorPanel());		
		dialog.setHeight(420);
		dialog.setWidth(400);		
		dialog.show(true);	
		dialog.setResizable(true);
	}


/**
	 * id on screen configuration
	 **/
	@Override
	public String getOnScreenConfigurationId() {
		return onScreenConfigurationId;
	}
	
	/**
	 * id on screen configuration
	 **/
	@Override
	public void setOnScreenConfigurationId(String configurationId) {
		this.onScreenConfigurationId = configurationId ; 
		
	}
	
	private String groupId;
	
	@Override
	public String getGroupFormConfiguration() {		
		return this.groupId;
	}

	@Override
	public void setOnScreenGroupFormConfiguration(String groupId) {
		this.groupId = groupId;		
	}
	
	/**
	 * id group, di inject oleh parent container/form
	 **/
	@Override
	public String getGroupId() {
		return groupId;
	}
	/**
	 * id group, di inject oleh parent container/form
	 **/
	@Override
	public void setGroupId(String groupId) {
		this.groupId = groupId;
		
	}
	
	/**
	 * penanada apakah kontrol boleh di konfigurasi atau tidak
	 */
	private boolean screenEditable = true;

	@Override
	public void setOnScreenEditable(boolean state) {
		this.screenEditable = state;
	}

	@Override
	public boolean isOnScreenEditable() {
		return screenEditable;
	}

	@Override
	public void assignConfigurationData(
			IFormElementConfiguration formConfiguration) {
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
	}
	
	
}
