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
import id.co.gpsc.common.client.util.FormatingUtils;
import id.co.gpsc.common.client.util.OnScreenConfigurationUtils;
import id.co.gpsc.jquery.client.util.JQueryUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.text.shared.Parser;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.DOM;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;

/**
 * textbox untuk menampung currency. Apa saja yang di akan di lakukan textbox -> 
 * <ol>
 * 	<li>Formatting -> kalau ribuan akan di pecah tambahi delimiter indonesia -> .</li>
 *  <li>Defaultnya -> angka setelah pecahan akan di bulatkan dan akan di ganti dengan tanda <i>,-</i>(sebagaimana dalam lineup D1)</li>
 * </ol>
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @category form
 **/
public class CurrencyTextBox extends ValueBoxBase<BigDecimal> 
	implements MandatoryValidationEnabledControl, OnScreenConfigurableControl , ICommonTextboxBasedElement, ITransformableToReadonlyLabel{
	
	
	
	/**
	 * pemisah decinal default. se isi app akan memakai decimal separator untuk entry apa
	 */
	public static char DEFAULT_DECIMAL_SEPARATOR_CHAR ='.' ; 
	
	
	/**
	 * nama css textbox, currency. kalau di set, default akan mempergunakan ini
	 **/
	public static String CSS_NAME_CURRENCY_TEXTBOX;
	
	
	
	
	
	/**
	 * nilai decimal yang di pagang textbox ini
	 **/
	protected BigDecimal holdedDecimalValue; 
	
	private boolean attached  = false ;
	
	/**
	 * element currency marker
	 **/
	private Element currencyMarkerElement ; 
	
	
	
	
	/**
	 * tanda currency. default : Rp
	 **/
	private String currencySign ="Rp"; 
	
	/**
	 * tampilkan sign currency --> Rp, $  etc (default : false ) 
	 **/
	private boolean includeCurrencySign = false  ; 
	
	
	/**
	 * automatic register on attach. di set true. efeknya ini akan secara otomatis di register ke parent on attach. apa saja : 
	 * <ol>
	 * 	<li>i18</li>
	 * <li>mandatory</li>
	 *  </ol>
	 **/
	private boolean autoRegisterOnAttach = false ;
	
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	private String placeHolder ; 
	
	
	
	/**
	 * id on screen configuration
	 **/
	private String onScreenConfigurationId  ; 
	
	
	/**
	 *  id dari form di mana dia di taruh. ini di pergunakan untuk load/save. ini di inject oleh container
	 **/
	private String parentFormConfigurationId; 

	
	private boolean roundAfterDot=true;
	
	private int numberOfRoundingAfterDot=4; 
	
	private CurrencyRenderer renderer=new CurrencyRenderer() ; 
	
	
	
	private boolean focused = false ; 
	private boolean justFocused = false ; 
	
	
	
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
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public String getPlaceHolder() {
		return placeHolder;
	}
	/**
	 * placeholder. ini di munculkan dalam text yang belum di isi
	 **/
	public void setPlaceholder(String placeHolder) {
		try {
			this.placeHolder = placeHolder ; 
			if ( placeHolder!=null&&placeHolder.length()>0){
				getElement().setAttribute("placeHolder", placeHolder); 
			}
			else{
				getElement().removeAttribute("placeHolder");
			}
		} catch (Exception e) {
			
		}
		
	}
	@SuppressWarnings("unchecked")
	private ChildSelfRegisterAggregator<CurrencyTextBox> childSelfRegisterAggregator= 
			new ChildSelfRegisterAggregator<CurrencyTextBox>(new ChildSelfRegisterWorker[]{
				new MandatoryControlSelfRegisterWorker(), 
				new ResourceAwareControlSelfRegisterWorker()
			}, this);  
	/**
	 * currency text box. ini akan menelan angka setelah koma
	 **/
	public CurrencyTextBox(){
		this(true , 2); 
		
	}
	
	
	public CurrencyTextBox(boolean includeCurrencySign){
		this( includeCurrencySign, true , 2); 
		
	}
	
	/**
	 * angka setelah koma di telan atau tidak
	 **/
	public CurrencyTextBox(final boolean swalowFraction ,final int numberOfDigitOnFraction){
		this ( false , swalowFraction , numberOfDigitOnFraction); 
		
	}

	public InputElement getInputElemet(){
		return getElement().cast();
	}
	
	
	
	  /**
	   * set panjang maksimum dalam textbox
	   */
	public void setMaxLength(int length){
		getInputElemet().setMaxLength(length);
	}
	
	
	public int getMaxLength(){
		return getInputElemet().getMaxLength();
	}
	
	/**
	 * tampilkan sign currency --> Rp, $  etc (default : false ) 
	 **/
	public void setIncludeCurrencySign(boolean includeCurrencySign) {
		if ( this.includeCurrencySign== includeCurrencySign )
			return ; 
		this.includeCurrencySign = includeCurrencySign;
		plugCurrencySignWorker(); 
	}
	
	
	/**
	 * actual worker constructor
	 * konstruktor dengan marker currency (default RP bisa di rubah dengan )
	 * @param includeCurrencySign  kontro perlu di render dengan currency sign atau tidak
	 * @param swalowFraction pecahan di telan atau tidak
	 **/
	public CurrencyTextBox(final boolean includeCurrencySign , final boolean swalowFraction ,final int numberOfDigitOnFraction){
		super(DOM.createInputText() ,  new Renderer<BigDecimal>() {
			@Override
			public String render(BigDecimal object) {
				if ( object==null)
					return ""; 
				 
				return FormatingUtils.getInstance().formatCurrency(object);
			}
			@Override
			public void render(BigDecimal object, Appendable appendable)
					throws IOException {
				
				if ( object==null)
					return ; 
				appendable.append(
						swalowFraction ? 
						FormatingUtils.getInstance().formatCurrency(object)
						: 
						FormatingUtils.getInstance().formatSimpleFloat(object, false, numberOfDigitOnFraction)	
				); 
			}
			
		},  
		new Parser<BigDecimal>() {
			@Override
			public BigDecimal parse(CharSequence text) throws ParseException {
				try {
					if ( text==null|| text.length()==0)
						return null; 
					return new BigDecimal(text.toString()); 
				} catch (Exception e) {
					
					
					return null;
				}
			}
		} ); 
		
		addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				focused = false ;
				System.out.println("focus lost , value : " + holdedDecimalValue);
				validateUserEntryRestoreToOldValueIfInvalid();
				renderNumberToControl(holdedDecimalValue);
								
			}
		}); 
		
		addFocusHandler(new FocusHandler() {
			
			@Override
			public void onFocus(FocusEvent event) {
				focused = true  ; 
				justFocused = true ; 
				renderNumberToControl(holdedDecimalValue);
				new Timer() {
					@Override
					public void run() {
						selectAll();
					}
				}.schedule(5);
				
			}
		});
		
		/*
		addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if ( justFocused){
					justFocused = false ; 
					return  ; 
				}
				validateUserEntryRestoreToOldValueIfInvalid();
			}
		});*/
		
		addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				System.err.println("keypress " + event.getCharCode());
				if ( justFocused){
					justFocused = false ; 
					return  ; 
				}
				validateUserEntryRestoreToOldValueIfInvalid();
				
			}
		}); 
		
		

		//setAlignment(TextAlignment.RIGHT); 
	
		this.includeCurrencySign = includeCurrencySign; 
		setMaxLength(13);
		
		if ( CSS_NAME_CURRENCY_TEXTBOX!=null&& CSS_NAME_CURRENCY_TEXTBOX.length()>0){
			setStyleName(CSS_NAME_CURRENCY_TEXTBOX);
		}
	}
	
	
	/**
	 * render data ke dalam control
	 */
	private void renderNumberToControl (BigDecimal value ) {
		if ( value == null){
			getElement().setPropertyString("value", "");
			return ; 
		}
		if ( focused ){
			getElement().setPropertyString("value",generateUnFormattedString(value)); 
		}else {
			getElement().setPropertyString("value", generateFormattedString(value));
		}
		
	}
	
	
	
	/**
	 * memvalidasi entry dari user. kalau misalnya salah maka data di kembalikan ke posisi awal
	 */
	private void validateUserEntryRestoreToOldValueIfInvalid () {
		String oldVal = generateUnFormattedString(holdedDecimalValue); 
		String newVal = getElement().getPropertyString("value"); 
		if ( newVal!= null )
			newVal=newVal.trim(); 
		else 
			newVal = ""; 
		if ( newVal.isEmpty()){
			System.err.println("new val is blank, holded value nulled now");
			holdedDecimalValue = null ; 
			getElement().setPropertyString("value", "");
			return  ; 
		}
		try {
			if ( FormatingUtils.getInstance().getDecimalSeparatorChar()==','){
				if ( newVal.contains(".")){
					getElement().setPropertyString("value", oldVal);
					JQueryUtils.getInstance().showToolTip(this,  FormatingUtils.getInstance().getDefaultMessageForInvalidDecimalSeparator(), 2000);
					return ; 
				}
			}
			BigDecimal swap = new BigDecimal(newVal); 
			holdedDecimalValue = swap ; 
			GWT.log("nilai valid : " + newVal);
		} catch (Exception e) {
			JQueryUtils.getInstance().showToolTip(this,  "Entry :[" + newVal  + "] tidak valid, entry akan di kembalikan ke nilai sebelumnya " , 2000);
			e.printStackTrace();
			getElement().setPropertyString("value", oldVal);
		}
	}
	/**
	 * format currency
	 */
	private String generateUnFormattedString ( BigDecimal money) {
		if ( money== null)
			return "" ; 
		return money.toPlainString(); 
	}
	
	/**
	 * format currency
	 */
	private String generateFormattedString ( BigDecimal money) {
		if ( money== null)
			return "" ; 
		
		String val =  FormatingUtils.getInstance().formatSimpleFloat(money, roundAfterDot, numberOfRoundingAfterDot) ; 
		System.out.println("raw formated:" + val);
		if (val== null || val.isEmpty())
			return "" ; 
		if ( FormatingUtils.getInstance().getDecimalSeparatorChar()==','){
			if ( val.contains(".")  ){
				String[] swaps = val.split("\\.");
				System.out.println("pecahan 1:" + swaps[0]);
				swaps[0] =  swaps[0].replaceAll("\\,", "."); 
				if ( swaps.length> 1)
					return swaps[0] + "," + swaps[1]; 
				return swaps[0]; 
			}
			return val.replaceAll("\\,", "."); 
		}else {
			return val ;
		} 
	}
	
	
	
	@Override
	public void setValue(BigDecimal value, boolean fireEvents) {
		this.holdedDecimalValue=value; 
		renderNumberToControl(holdedDecimalValue);
		if ( readonlyMode)
			switchToReadonlyText();
	}
	
	@Override
	public BigDecimal getValueOrThrow() throws ParseException {
		return holdedDecimalValue;
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
	protected void readCurrencyDecimalChar(){
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
	
	
	@Override
	protected void onAttach() {
		super.onAttach();
		attached= true ; 
		if ( getElement().getId()==null||getElement().getId().length()==0)
			getElement().setId(DOM.createUniqueId());
		getElement().setPropertyObject(CommonClientControlConstant.TAG_KEY_SEL_REF, this);
		plugCurrencySignWorker(); 
		
		if ( autoRegisterOnAttach)
			childSelfRegisterAggregator.registerToParent();
		showHideEditConfigButton(showEditConfigButton);
		
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
	
	
	/**
	 * taruh tanda currency 
	 **/
	private void plugCurrencySignWorker (){
		if ( includeCurrencySign && attached){
			this.currencyMarkerElement =  DOM.createElement("span");
			currencyMarkerElement.setInnerHTML(currencySign + "&nbsp;");
			DOM.insertBefore((Element)getElement().getParentElement(), currencyMarkerElement, getElement());
			 
		}
	}
	
	@Override
	protected void onDetach() {
		super.onDetach();
		attached= false ; 
		unPlugMandatoryMarker(); 
		if ( includeCurrencySign){
			this.currencyMarkerElement.removeFromParent(); 
			this.currencyMarkerElement= null ; 
		}
		if ( autoRegisterOnAttach)
			childSelfRegisterAggregator.unregisterFromParent();
		
		try {
			OnScreenConfigurationUtils.getInstance().hideOnScreenEditorControl(this);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	

	
	
	
	/**
	 * tanda currency. default : Rp
	 **/
	public void setCurrencySign(String currencySign) {
		this.currencySign = currencySign;
	}
	/**
	 * tanda currency. default : Rp
	 **/
	public String getCurrencySign() {
		return currencySign;
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

	
	
	private   void unPlugMandatoryMarker (){
		if ( mandatoryMarker!=null&& mandatoryMarker.getParentElement() !=null){
			mandatoryMarker.removeFromParent();
		}	
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
	
	/**
	 * konversi string ke big decimal
	 **/
	private native BigDecimal makeCurrency(String someString)/*-{
		try{
			return new BigDecimal(someString/1);
		}
		catch(ex){
			return new BigDecimal(0) ;
		}
	
	}-*/;

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
	
	
}
