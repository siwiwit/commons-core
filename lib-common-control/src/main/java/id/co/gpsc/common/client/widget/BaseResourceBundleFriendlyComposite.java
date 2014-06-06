package id.co.gpsc.common.client.widget;



import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;







import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.form.MandatoryValidationEnabledControl;
import id.co.gpsc.common.client.form.MandatoryValidationFailureException;
import id.co.gpsc.common.client.form.ResourceBundleEnableContainer;
import id.co.gpsc.common.client.util.JSONUtilities;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.common.control.ResourceBundleConfigurableControl;



/**
 * composite yang i18 friendly. jadinya kontrol di dalam nya bisa di configure
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * 
 * @category composite
 **/
public abstract class BaseResourceBundleFriendlyComposite extends BaseSimpleComposite implements ResourceBundleEnableContainer, IMandatoryEnabledContainer{

	/**
	 * tag yang di periksa. di curigai elemen ini yang mandatory enabled
	 **/
	public static String[] SCANNED_TAG_FOR_MANDATORY_CHECKER ={"input" , "textarea" ,"select"}; 

	
	/**
	 * versi raw dari {@link #SCANNED_TAG_FOR_MANDATORY_CHECKER}
	 **/
	private JsArrayString tagToScan = null ; 
	private ArrayList<ResourceBundleConfigurableControl> i18Controls = new ArrayList<ResourceBundleConfigurableControl>();
	
	
	private ArrayList<BaseResourceBundleFriendlyComposite> childContainers = new ArrayList<BaseResourceBundleFriendlyComposite>() ; 
	
	
	/**
	 * daftar kontrol yang mandatory enabled
	 **/
	private ArrayList<MandatoryValidationEnabledControl> mandatoryEnabledControls = new ArrayList<MandatoryValidationEnabledControl>();
	
	
	
	/**
	 * register resource bundle frienly container
	 **/
	public void registerChildContainer (BaseResourceBundleFriendlyComposite resourceBundleFriendlyContaiener){
		if (! this.childContainers.contains(resourceBundleFriendlyContaiener))
			this.childContainers.add(resourceBundleFriendlyContaiener); 
	}
	
	
	
	
	@Override
	public void registerResourceBundleEnabledNode(
			ResourceBundleConfigurableControl widget) {
		if ( widget==null||i18Controls.contains(widget))
			return ;
		i18Controls.add(widget);
		if ( isAppAdminModeOn()){
			
		}
	}
	
	@Override
	public void registerResourceBundleEnabledNodes(
			Collection<ResourceBundleConfigurableControl> widgets) {
		if ( widgets==null||widgets.isEmpty())
			return ;
		for (ResourceBundleConfigurableControl scn : widgets ){
			registerResourceBundleEnabledNode(scn);
		}
	}
	
	@Override
	public void registerResourceBundleEnabledNodes(
			ResourceBundleConfigurableControl[] widgets) {
		if ( widgets==null||widgets.length==0)
			return ;
		for (ResourceBundleConfigurableControl scn : widgets ){
			registerResourceBundleEnabledNode(scn);
		}
		
	}
	
	@Override
	public void unregisterResourceBundleEnabledNode(
			ResourceBundleConfigurableControl widget) {
		if ( this.i18Controls.contains(widget))
			i18Controls.remove(widget);
	}
	
	
	@Override
	public Set<String> getI18Keys() {
		HashSet<String> keys = new HashSet<String>();
		for ( ResourceBundleConfigurableControl scn: this.i18Controls){
			keys.add(scn.getI18Key());
		}
		return keys;
	}
	
	
	
	
	
	/* (non-Javadoc)
	 * @see id.co.gpsc.common.client.widget.IMandatoryEnabledContainer#validateMandatory()
	 */
	//@Override
	private void validateMandatoryOld () throws MandatoryValidationFailureException {
		JsArrayString ids= getEntryControlIds(getElement().getId(), JSONUtilities.getInstance().generateArray(  SCANNED_TAG_FOR_MANDATORY_CHECKER));
		if ( ids==null||ids.length()==0){
			GWT.log("no match found");
			return ; 
		}
		
		MandatoryValidationFailureException exc = new MandatoryValidationFailureException("mandatory validation failed");
		for(int i=0;i<ids.length();i++){
			Element swap =  DOM.getElementById(ids.get(i));
			Object obj = swap.getPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF);
			if(obj==null||!(obj instanceof MandatoryValidationEnabledControl)){
				GWT.log("skip element id : " + ids.get(i)+", key :" +CommonClientControlConstant.TAG_KEY_SEL_REF +",null atau bukan "+  MandatoryValidationEnabledControl.class.getName());
				continue ; 
			}	
			MandatoryValidationEnabledControl ctrl = (MandatoryValidationEnabledControl)obj;
			exc.pushIfNotValid(ctrl);
			
		}
		if ( !exc.getInvalidControls().isEmpty())
			throw exc ;
	}
	
	
	@Override
	public void validateMandatory() throws MandatoryValidationFailureException {
		if ( mandatoryEnabledControls.isEmpty())
			return ;
		MandatoryValidationFailureException exc = new MandatoryValidationFailureException("mandatory validation failed");
		for( MandatoryValidationEnabledControl cnt : this.mandatoryEnabledControls){
			exc.pushIfNotValid(cnt);
		}
		if ( exc.getInvalidControls().isEmpty())
			return ;
		throw exc ; 
		
	}
	
	
	@Override
	public void registerMandatoryComponent(
			MandatoryValidationEnabledControl control) {
		if ( mandatoryEnabledControls.contains(control))
			return ; 
		mandatoryEnabledControls.add(control); 
		
	}
	
	@Override
	public void unregisterMandatoryComponent(
			MandatoryValidationEnabledControl control) {
		mandatoryEnabledControls.remove(control);
		
	}
	
	
	
	
	
	
	
	/**
	 * worker membaca array of id dari elemen yang di curigai mandatory enabled node
	 * @param widgetId id widget root. ini ikut dari root element
	 * @param scannedTag tag-tag yang di scan
	 **/
	protected native JsArrayString getEntryControlIds(String widgetId , JsArrayString scannedTag ) /*-{
		if ( scannedTag.length==0)
			return null ; 
		var tagString=""; 
		var retval = []; 
		for ( i=0;i< scannedTag.length;i++){
			var jqStr = "#" + widgetId + " " + scannedTag[i];
			var arr=$wnd.$(jqStr); 
			if ( arr.length==0)	{
				continue;
			}
			for ( i=0;i< arr.length;i++){
				if ( arr[i].id==null||arr[i].id.length==0)
					continue ;
				retval.push(arr[i].id);
			}	
		}
		return retval;
	}-*/;
	
	/**
	 * kontrol yang bisa di edit di screen mandatory atau ndak
	 **/
	protected OnScreenConfigurableControl[] getOnScreenConfigurableControls () {
		return null ; 
	}
	
	
	
	
	/**
	 * method ini untuk render isi ke dalam form elemen configuration. jadinya akan di baca dari cache, konfigurasi untuk elemen apa
	 **/
	protected void fillFormElementConfigurations () {
		OnScreenConfigurableControl[] constrols =  getOnScreenConfigurableControls ();
		if(constrols==null||constrols.length==0)
			return ; 
		for (OnScreenConfigurableControl ctrl  : constrols){
			IFormElementConfiguration cnf = OnScreenConfigurationUtils.getInstance().getFormConfiguration(ctrl.getParentFormConfigurationId(), ctrl.getOnScreenConfigurationId());
			if ( cnf!=null){
				ctrl.assignConfigurationData(cnf);
			}
		}
			
		
		
	}
	
	
	
	/**
	 * method ini akan mengeluarkan tombol edit on screen editor button, kalau di mungkinkan. ini tergantung pada privilage dari user. 
	 * kalau allowed, maka tombol akan di render 
	 **/
	protected void renderOnScreenEditorButtonIfPossible() {
		
		//FIXME: masukan pengecekan boleh tidak admin mode di sini
		OnScreenConfigurableControl[] constrols =  getOnScreenConfigurableControls ();
		System.out.println("rendering control dari icon editor untuk " + this.getClass());
		if(constrols==null||constrols.length==0)
			return ; 
		for (OnScreenConfigurableControl ctrl  : constrols){
			ctrl.showHideEditConfigButton(true);
		}
		
	}
	
	
	
	
	private String groupId ; 
	
	@Override
	public void setPanelGroupId(String groupId) {
		this.groupId = groupId ; 
		
	}
	
	
	
	@Override
	public String getPanelGroupId() {
		return groupId;
	}
	
	
	
}
