package id.co.gpsc.common.client.form;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.client.control.ChildSelfRegisterAggregator;
import id.co.gpsc.common.client.control.ChildSelfRegisterWorker;
import id.co.gpsc.common.client.control.ControlCommonValueType;
import id.co.gpsc.common.client.control.IFormElementConfiguration;
import id.co.gpsc.common.client.control.ITransformableToReadonlyLabel;
import id.co.gpsc.common.client.control.MandatoryControlSelfRegisterWorker;
import id.co.gpsc.common.client.control.OnScreenConfigurableControl;
import id.co.gpsc.common.client.control.ResourceAwareControlSelfRegisterWorker;
import id.co.gpsc.common.client.form.advance.AdvanceControlUtil;
import id.co.gpsc.common.client.util.CommonClientControlUtil;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;






import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ValueBoxBase;



/**
 * @category form
 **/
public class FloatTextBox  extends ValueBoxBase<Float> 
	implements MandatoryValidationEnabledControl, OnScreenConfigurableControl, ICommonTextboxBasedElement, ITransformableToReadonlyLabel{

	
	
	private boolean attached  = false ;
	
	/**
	 * id on screen configuration
	 **/
	private String onScreenConfigurationId  ; 
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	private String placeholder ; 
	
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
	
	/**
	 * current number formatter
	 **/
	private NumberFormat currentFormatter =  NumberFormat.getFormat("#,##0.###");
	
	
	
	
	@SuppressWarnings("unchecked")
	private ChildSelfRegisterAggregator<FloatTextBox> childSelfRegisterAggregator= 
			new ChildSelfRegisterAggregator<FloatTextBox>(new ChildSelfRegisterWorker[]{
				new MandatoryControlSelfRegisterWorker(), 
				new ResourceAwareControlSelfRegisterWorker()
			}, this);  
	
	@Override
	protected void onDetach() {
		super.onDetach();
		attached= false ; 
		unPlugMandatoryMarker(); 
		
		if ( autoRegisterOnAttach){
			try{
				childSelfRegisterAggregator.unregisterFromParent();
			}
			catch ( Exception exc){
				exc.printStackTrace();
			}
		}
		
		try {
			OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * automatic register on attach. di set true. efeknya ini akan secara otomatis di register ke parent on attach. apa saja : 
	 * <ol>
	 * 	<li>i18</li>
	 * <li>mandatory</li>
	 *  </ol>
	 **/
	private boolean autoRegisterOnAttach = true ;
	
	private DummyFloatParser parser ; 
	
	
	private FloatRenderer renderer ; 
	
	
	
	
	/**
	 * nilai yang lg di pegang
	 **/
	private Float currentHoldedValue ; 
	
	
	
	public FloatTextBox(){
		this(DOM.createInputText(), new FloatRenderer() , new DummyFloatParser());
	}
	
	
	
	
	/**
	 * ada pembulatan atau tidak 
	 **/
	private boolean roundAfterDot;
	/**
	 * di bulatakan berapa angka setelah koma
	 **/
	private int numberOfRoundingAfterDot; 
	
	public FloatTextBox(boolean roundAfterDot, int numberOfRoundingAfterDot) {
		this() ; 
		
	}
	
	protected FloatTextBox( Element targetElement , FloatRenderer renderer  , DummyFloatParser parser) {
		super(targetElement,renderer, parser);
		this.renderer = renderer ; 
		this.renderer = renderer;
		setAlignment(TextAlignment.RIGHT);
		
		parser.setValueProvider(new ValueProvider<Float>() {
			
			@Override
			public Float getValue() {
				return currentHoldedValue;
			}
		});
		addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				/*if ( currentHoldedValue==null){
					getElement().setPropertyString("value", "");
					
				}else{
					String current = getElement().getPropertyString("value");
					if ( current!=null&& current.length()>0){
						current = current.replaceAll("\\" + CommonClientControlUtil.getInstance().getThousandSeparator(), "");
						getElement().setPropertyString("value", current);
					}
					
				}*/
				readTextEntryDecimalForFormatEntry();
			}
		});
		addBlurHandler(blurHandler);
		addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				readFloatChar();
			}
		});
	}

	@Override
	public void setI18Key(String key) {
		getElement().setPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM, key);
	}

	@Override
	public String getI18Key() {
		return getElement().getPropertyString(CommonClientControlConstant.I18_KEY_ON_DOM);
	}

	@Override
	public void setConfiguredText(String text) {
		getElement().setPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY, text) ; 
		
	}
	
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public String getPlaceHolder() {
		return placeholder;
	}
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public void setPlaceholder(String placeHolder) {
		try {
			this.placeholder = placeHolder ; 
			if ( placeHolder!=null&&placeHolder.length()>0){
				getElement().setAttribute("placeHolder", placeHolder); 
			}
			else{
				getElement().removeAttribute("placeHolder");
			}
		} catch (Exception e) {
			
		}
		
	}

	/**
	 * flag show hide config button
	 **/
	private boolean showEditConfigButton = false ; 
	
	
	@Override
	public void showHideEditConfigButton(boolean show) {
		this.showEditConfigButton = show ; 
		if ( isAttached()){
			if ( show)
				OnScreenConfigurationUtils.getInstance().renderEditoControl(this, ControlCommonValueType.numberValue);
			else
				OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		}
	}

	@Override
	public void setMandatory(boolean mandatory) {
		CommonClientControlUtil.getInstance().setMandatory(this, mandatory);
		if ( this.isAttached()){
			baseMandatoryMarkerRenderer();
		}
	}

	@Override
	public boolean isMandatory() {
		return getElement().getPropertyBoolean(CommonClientControlConstant.CONTROL_MANDATORY_DOM_KEY);
	}

	@Override
	public String getControlBusinessName() {
		return getElement().getPropertyString(CommonClientControlConstant.CONTROL_BUSINESS_NAME_DOM_KEY) ;
	}

	@Override
	public boolean validateMandatory() {
		if ( !isMandatory())
			return true ; 
		return getValue()!=null;
	}
	/**
	 * automatic register on attach. di set true. efeknya ini akan secara otomatis di register ke parent on attach. apa saja : 
	 * <ol>
	 * 	<li>i18</li>
	 * <li>mandatory</li>
	 *  </ol>
	 **/
	public void setAutoRegisterOnAttach(boolean autoRegisterOnAttach) {
		this.autoRegisterOnAttach = autoRegisterOnAttach;
	}
	/**
	 * automatic register on attach. di set true. efeknya ini akan secara otomatis di register ke parent on attach. apa saja : 
	 * <ol>
	 * 	<li>i18</li>
	 * <li>mandatory</li>
	 *  </ol>
	 **/
	public boolean isAutoRegisterOnAttach() {
		return autoRegisterOnAttach;
	}
	
	
	
	
	private   void unPlugMandatoryMarker (){
		if ( mandatoryMarker!=null&& mandatoryMarker.getParentElement() !=null){
			mandatoryMarker.removeFromParent();
		}	
	}
	
	
	@Override
	public void setValue(Float value) {
		this.currentHoldedValue= value ; 
		getElement().setPropertyString("value", renderer.render(value));
	}
	
	
	
	
	private BlurHandler blurHandler = generateBlurHandler(); 
	
	
	
	/**
	 * worker generate on blur. formatting dan baca data da di sini
	 **/
	protected BlurHandler generateBlurHandler () {
		return new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent handler) {
				String currentEntry = getElement().getPropertyString("value");
				if ( currentEntry==null||currentEntry.length()==0){
					currentHoldedValue=null ;
					return ;
				}	
				
				
				String afterRemoveThousand =currentEntry.replaceAll("\\" +  CommonClientControlUtil.getInstance().getThousandSeparator(), "");
				String afterNormalizeDecomal = afterRemoveThousand.replaceAll("\\" + CommonClientControlUtil.getInstance().getDecimalSeparator(), ".");
				 
				String[] arr = afterNormalizeDecomal.split("\\."); 
				String depan = arr[0];
				String belakang =  ( (arr.length==2) ? arr[1]:"0");
				
				
				if ( roundAfterDot){
					if ( belakang.length()> numberOfRoundingAfterDot)
						belakang = belakang.substring(0, numberOfRoundingAfterDot-1);
				}
					
				belakang = "0." + belakang ;
				if ( belakang.length()<3)
					belakang = belakang +"0";
				
				currentHoldedValue = makeFloat(depan) + makeFloat(belakang);
				
				System.out.println("float conversion : original entry :" +currentEntry + ",no thousand separator :" +  afterRemoveThousand + ", normalized decimal point:" + afterNormalizeDecomal + ",float result : " + currentHoldedValue) ;
				getElement().setPropertyString("value", renderer.render(currentHoldedValue));
			}
		}; 
	}
	
	/**
	 * membaca big decimal dan menyalinnya sesuai dengan internationalization
	 * @author ashadi.pratama
	 */
	protected void readTextEntryDecimalForFormatEntry(){
		String currentEntry = getElement().getPropertyString("value");
		if ( currentEntry==null||currentEntry.length()==0){
			return ;
		}	

		String[] divString=currentEntry.split("\\.");
		String thousand =divString[0].replaceAll(",",CommonClientControlUtil.getInstance().getThousandSeparator());
		String decimal="";
		if(divString.length>1)
			decimal=CommonClientControlUtil.getInstance().getDecimalSeparator()+divString[1];
		
		String backFormater=thousand+decimal;
		
		System.out.println("back conversion : original entry :" +currentEntry + ",thousand :" +  thousand + ", decimal :" + decimal) ;
		getElement().setPropertyString("value",backFormater);
	}
	
	/**
	 * membatasi karakter yang masuk saat di ketik dengan asumsi karakter yang boleh dimasukkan
	 * adalah 0123456789.,
	 * @author ashadi.pratama
	 */
	protected void readFloatChar(){
		String currentEntry = getElement().getPropertyString("value");
		if ( currentEntry==null||currentEntry.length()==0){
			return ;
		}	
		
		String validString="0123456789.,";
		String validChar="";
		for(int i=0;i<currentEntry.length();i++){
			if(validString.indexOf(currentEntry.charAt(i))>=0)
				validChar+=currentEntry.charAt(i);
		}
		
		getElement().setPropertyString("value",validChar);
	}
	
	/**
	 * konversi string ke float
	 **/
	private native float makeFloat(String someString)/*-{
		try{
			return someString/1;
		}
		catch(ex){
			return 0 ;
		}
		
	
	}-*/;
	
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	private String placeHolder ; 
	
	
	
	
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
	protected void onAttach() {
		super.onAttach();
		showHideEditConfigButton(screenEditable);
		baseMandatoryMarkerRenderer();
	}
	
	/**
	 * marker mandatory
	 **/
	protected Element mandatoryMarker = null ;
	
	protected void baseMandatoryMarkerRenderer() {
		if ( isMandatory()){
			if(mandatoryMarker==null)
				mandatoryMarker=(Element) AdvanceControlUtil.getInstance().renderMandatoryMarkerAfterNode(getElement());
			else
				mandatoryMarker.getStyle().setProperty("display", "");
		}
		else{
			if ( mandatoryMarker!=null)
				mandatoryMarker.getStyle().setDisplay(Display.NONE);
		}
		if (!isVisible())
			mandatoryMarker.getStyle().setDisplay(Display.NONE);
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if ( !visible){
			if ( mandatoryMarker!=null){
				mandatoryMarker.getStyle().setProperty("display", "none");
			}
		}
		else{
			baseMandatoryMarkerRenderer();
		}
	}
	
	@Override
	public void assignConfigurationData(
			IFormElementConfiguration formConfiguration) {
		OnScreenConfigurationUtils.getInstance().applyConfiguration(this, formConfiguration);
	}
	@Override
	public void restoreControl() {
		readonlyMode = false ; 
		CommonClientControlUtil.getInstance().switchToNormalMode(this);
		
	}
	
	
	
	private boolean readonlyMode = false ; 

	@Override
	public void switchToReadonlyText() {
		readonlyMode  = true ; 
		CommonClientControlUtil.getInstance().switchToReadOnlyLabel( this);
		
	}
	@Override
	public void setValue(Float value, boolean fireEvents) {
		
		super.setValue(value, fireEvents);
		if ( readonlyMode)
			switchToReadonlyText();
	}
}
