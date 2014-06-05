package id.co.gpsc.jquery.client.form;

import id.co.gpsc.common.form.BaseFormElement;
import id.co.gpsc.jquery.client.BaseJqueryWidget;
import id.co.gpsc.jquery.client.CommonJQueryUtilities;
import id.co.gpsc.jquery.client.enums.AnimationSpeed;
import id.co.gpsc.jquery.client.enums.WeekName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsDate;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.impl.FocusImpl;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;




/**
 * JQuery date picker
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public class JQDatePicker extends BaseJqueryWidget implements HasText,HasValue<Date>, HasChangeHandlers,HasAllKeyHandlers , BaseFormElement{

	
	
	public static final String DEFAULT_DATE_FORMAT ="dd/mm/yy"; 
	
	
	
	/**
	 * durasi timer untuk set option
	 */
	public static final int DALAYED_TIMER_SET_OPTION = 2000 ; 

	/**
	 * date yang tidak bisa di pilih
	 **/
	protected List<String> unSelectableDates=new ArrayList<String>();
	private String messageOnMandatoryValidationError;

	private boolean attached  = false ;
	private final boolean mandatory = false ;
	
	
	/**
	 * control dalam posisi enabled atau tidak
	 **/
	private boolean enabled = true ; 
	private boolean isKeyPressed = false;
	private static TextBoxImpl impl = GWT.create(TextBoxImpl.class);
	private static final FocusImpl implFocus = FocusImpl.getFocusImplForWidget();
	/**
	 * marker mandatory
	 **/
	private Element mandatoryMarker ;

	private boolean valueChangeHandlerInitialized;

	public JQDatePicker (){
		super();
		
		addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getNativeEvent().getKeyCode()!=KeyCodes.KEY_BACKSPACE && event.getNativeEvent().getKeyCode()!=KeyCodes.KEY_DELETE){
					isKeyPressed=true;
				}
			}
		});
		setFormat(DEFAULT_DATE_FORMAT);
	}
	
	@Override
	protected Element generateUnderlyingElement() {
		Element retval = DOM.createInputText();
		retval.setId(DOM.createUniqueId());
		retval.setPropertyInt("maxlength", 10);
		retval.setPropertyInt("size", 10);
		return retval;
	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return addDomHandler(handler, ChangeEvent.getType());
	}

	@Override
	public HandlerRegistration addKeyDownHandler(KeyDownHandler handler) {
		return addDomHandler(handler, KeyDownEvent.getType());
	}



	@Override
	public HandlerRegistration addKeyPressHandler(KeyPressHandler handler) {
		return addDomHandler(handler, KeyPressEvent.getType());
	}
	@Override
	public HandlerRegistration addKeyUpHandler(KeyUpHandler handler) {
		return addDomHandler(handler, KeyUpEvent.getType());
	}

	@Override
	public HandlerRegistration addValueChangeHandler(
			ValueChangeHandler<Date> handler) {
		if ( !this.valueChangeHandlerInitialized){
			valueChangeHandlerInitialized=true ;
			addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					ValueChangeEvent.fire(JQDatePicker.this, getValue());
				}
			});
		}
		return addHandler(handler, ValueChangeEvent.getType());
	}




	/**
	 * hanya ijinkan button image untuk memilih tanggal
	 * @param flag <i>true</i> -> berarti hanya button image yang bisa di pergunakan untuk memilih tanggal , <i>false</i> -> pemilihan tanggal bisa dengan tombol + dengan click pada areal date picker
	 **/
	public void allowButtonImageOnly(boolean flag){
		triggerOption( "buttonImageOnly", flag);
	}

	/**
	 * Allows you to change the month by selecting from a drop-down list. You can enable this feature by setting the attribute to true.<br/>
	 * jadinya month picker muncul sebagai drop down atau tidak
	 * @param flag <i>true</i> enabled
	 **/
	public void allowChangeMonth(boolean flag){
		triggerOption( "changeMonth", flag);
	}

	/**
	 * allow to change the year & month by selecting from a drop-down list
	 * @param showMonthYear
	 */
	public void allowChangeMonthYear(boolean showMonthYear){
		triggerOption("changeMonth", showMonthYear);
		triggerOption("changeYear", showMonthYear);		
	}
	
	private boolean allowedChangeYear = false;
	public void setAllowedChangeYear(boolean allowedChangeYear) {
		this.allowedChangeYear = allowedChangeYear;
		triggerOption("changeYear", JQDatePicker.this.allowedChangeYear);
	}

	public boolean isAllowedChangeYear(){
		return allowedChangeYear;
	}

	/**
	 * 
	 * Allows you to change the year by selecting from a drop-down list. You can enable this feature by setting the attribute to true.<br/>
	 * year muncul sebagai drop down atau tidak
	 * @param flag <i>true</i> enabled
	 **/
	public void allowChangeYear(boolean flag){
		triggerOption( "changeYear", flag);
	}



	/**
	 * default : <br/>
	 * false <br/>
	 * di bagian bawah di tampilkan tombol atau tidak
	 **/
	public void allowShowButtonPanel(boolean showButtonPanel){
		triggerOption("showButtonPanel", showButtonPanel);
	}



	/**
	 * default state :<br/>
	 * false <br/>
	 * When true a column is added to show the week of the year. The calculateWeek option determines how the week of the year is calculated. You may also want to change the firstDay option.
	 **/
	public void allowShowWeek(boolean showWeek){
		triggerOption("showWeek", showWeek);
	}





	/**
	 * disable date. jadinya tanggal tidak bisa di pilih dari date picker
	 **/
	public void disabledDate(Date date){
		String strDate=CommonJQueryUtilities.getInstance().getIsoDateFormatter().format(date);

		unSelectableDates.add(strDate);
	}


	@Override
	protected String getJQueryClassName() {
		return "datepicker";
	}

	@Override
	public String getText() {
		return getElement().getPropertyString("value");
	}



	
	@Override
	public Date getValue() {
		JsDate rawValue=triggerDateReturnMethod(getJQWidgetId(), getJQueryClassName(), "getDate");
		if ( rawValue==null)
			return null;
		DateTimeFormat fmt =DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		String dateString= rawValue.getFullYear() +"-" + (rawValue.getMonth()+1) + "-" + rawValue.getDate() +" " + rawValue.getHours() + ":" + rawValue.getMonth()  +":" + rawValue.getMinutes();
		Date date = fmt.parse(dateString);
		return date;
	}


	/**
	 * check date bisa di pilih atau tidak
	 **/
	protected boolean isDateEnabled(int year4Digit , int monthZeroBasedIndex , int date){

		String fmt=year4Digit + "-" + make2DigitString(monthZeroBasedIndex+1) +"-" + make2DigitString(date);
		boolean enabled=  !unSelectableDates.contains(fmt) ;

		return  enabled ;
	}


	/**
	 * @return the isKeyPressed
	 */
	public boolean isKeyPressed() {
		return isKeyPressed;
	}
	protected String make2DigitString(int someNumber){
		if (!( someNumber<10) )
			return "" + someNumber ;
		else
			return "0" + someNumber;
	}


	
	/**
	 * set id dari node. karena ini adalah elemen form, ideal nya ini perlu di set dengan id yang self describe<br/>
	 * 
	 * <i>Mohon <strong>Tidak</strong></i> mempergunakan method {@link #getElement()} + {@link Element#setId(String)} untuk mengeset id
	 * 
	 **/
	@Override
	public void setDomId(String elementId) {
		if ( attached){
			destroy(this.getElement().getId());
			ensureDebugId(elementId);
			renderJQueryWidget();
		}
		else
			ensureDebugId(elementId) ;
	}
	@Override
	public String getDomId() {
		return getElement().getId();
	}
	
	private native void destroy(String id)/*-{
		$wnd.$("#" + id).datepicker("destroy");
	
	}-*/;



	@Override
	protected void onAttach() {

		super.onAttach();

		Element e=  DOM.getElementById("ui-datepicker-div");
		if ( e!= null ){
			if (  CommonJQueryUtilities.getInstance().isResizeJqueryWidgetProgrammatically()){
				e.getStyle().setFontSize(CommonJQueryUtilities.getInstance().getWidgetResizePercentage(), Unit.PCT);
			}

		}
		attached= true ;
		//FIXME: masukan mandatory related task
	}

	@Override
	protected void onDetach() {
		super.onDetach();
		attached= false ;
		unPlugMandatoryMarker();
	}




	@Override
	protected  void renderJQueryWidget(String widgetId, String jqueryClassname){
		renderDatePickerWorker(widgetId, jqueryClassname, widgetConstructArgument); 
	}
	
	
	
	/**
	 * actual worker untuk render date picker
	 * 
	 **/
	private native void renderDatePickerWorker(String widgetId, String jqueryClassname , JavaScriptObject datepickerArg) /*-{
		var selector= "#" +widgetId;
		var thisReference =this  ;
		
		 
		
		datepickerArg["beforeShowDay"]= function(theDate){
				var theYear= theDate.getFullYear();
				var theMonth = theDate.getMonth();
				var day=theDate.getDate();
	
				var qa=thisReference.@id.co.gpsc.jquery.client.form.JQDatePicker::isDateEnabled(III)(theYear,theMonth,day);
				return [qa,''];
			};
		
		$wnd.$(selector)[jqueryClassname](datepickerArg);
		this.@id.co.gpsc.jquery.client.BaseJqueryWidget::jqueryRendered=true ;
	
	
	}-*/;


	/**
	 * set url dari button. jadinya akan ada icon picker setelah kontrol
	 * @param  imageUrl url dari image date picker
	 **/
	public void setButtonImageUrl(String imageUrl ){
		triggerOption( "buttonImage", imageUrl);
	}

	/**
	 * set label yang akan di assign pada tombol trigger. default -> ..
	 * @param buttonText label pada tombol picker
	 **/
	public void setbuttonText(String buttonText){
		triggerOption( "buttonText", buttonText);
	}

	/**
	 * default :<br/>
	 * 'Done'
	 * The text to display for the close link. This attribute is one of the regionalisation attributes. Use the showButtonPanel to display this button.<br/>
	 * <i>hanya</i> aktiv kalau option allowShowButtonPanel = <i>true</i>
	 * @param closeText text pada close link di date picker
	 **/
	public void setCloseText(String closeText){
		triggerOption( "closeText", closeText);
	}

	/**
	 * True if the input field is constrained to the current date format.
	 * @param constraint constraint atau tidak
	 **/
	public void setConstrainInput(String constraint){
		triggerOption( "constrainInput", constraint);
	}

	/**
	 * The text to display for the current day link. This attribute is one of the regionalisation attributes. Use the showButtonPanel to display this button
	 * default : <br/>
	 * today
	 * @param currentText current date text. defaultnya : today
	 **/
	public void setCurrentText(String currentText){
		triggerOption( "currentText", currentText);
	}

	/**
	 * set default date untuk date picker<br/>
	 **/
	public void setDefaultDate(Date defaultDate){
		triggerOption( "defaultDate", defaultDate);
	}



	/**
	 * set durasi animasi. defaultnya : normal.
	 * @param duration durasi animasi. defaultnya normal
	 * 
	 **/
	public void setDuration (AnimationSpeed duration){
		triggerOption( "duration", duration.toString());
	}

	public void setEnabled(boolean enable) {
		this.enabled= enable ; 
		triggerVoidMethod(getJQWidgetId(), getJQueryClassName(), enable? "enable":"disable");
	}

	/**
	 * hari pertama dalam date picker. defaultnya : sunday (minggu). kalau mau memakai senin sebagai hari pertama berarti set monday dalam argumen
	 * @param firstDayOnPicker hari pertama dalam date picker
	 **/
	public void setFirstDay(WeekName firstDayOnPicker){
		triggerOption( "firstDay", firstDayOnPicker.getInternalRepresentation());
	}


	public void setFocus(boolean focused) {
		if (focused) {
			implFocus.focus(getElement());
		} else {
			implFocus.blur(getElement());
		}
	}

	/**
	 * set format sesuai keinginan
	 * <br>ex : yy/mm/dd , dd/mm/yy, dll
	 */
	public void setFormat(String format){
		triggerOption("dateFormat", format);
	}


	/**
	 * @param isKeyPressed the isKeyPressed to set
	 */
	public void setKeyPressed(boolean isKeyPressed) {
		this.isKeyPressed = isKeyPressed;
	}

	static DateTimeFormat FMT_TAHUN = DateTimeFormat.getFormat("yyyy");
	static DateTimeFormat FMT_BULAN = DateTimeFormat.getFormat("MM");
	static DateTimeFormat FMT_TGL = DateTimeFormat.getFormat("dd");

	/**
	 * set maksimal date yang bisa di pilih oleh user
	 **/
	public void setMaxDate (final Date maxDate){
		if ( maxDate== null)
			return  ; 
		new Timer() {
			
			@Override
			public void run() {
				String elementId =  getElement().getId();
				int thn = Integer.parseInt(FMT_TAHUN.format(maxDate)); 
				int bln = Integer.parseInt(FMT_BULAN.format(maxDate));
				int tgl = Integer.parseInt(FMT_TGL.format(maxDate));
				setMinimalDateWorker(elementId, thn, bln, tgl);
				
			}
		}.schedule(DALAYED_TIMER_SET_OPTION);
		
		
	}
	
	
	/**
	 * set minimum ate yang bisa di pilih
	 **/
	public void setMinDate (final Date minDate){
		if ( minDate== null)
			return  ;
		
		new Timer() {
			
			@Override
			public void run() {
				String elementId =  getElement().getId(); 
				//triggerOption( "minDate", minDate);
				
				int thn = Integer.parseInt(FMT_TAHUN.format(minDate)); 
				int bln = Integer.parseInt(FMT_BULAN.format(minDate));
				int tgl = Integer.parseInt(FMT_TGL.format(minDate));
				setMinimalDateWorker(elementId, thn, bln, tgl);
				
			}
		}.schedule(DALAYED_TIMER_SET_OPTION);;
		
		
	}
	
	
	
	
	/**
	 * native script jqyery untuk set minimal date
	 * @param id id date picker
	 * @param tahun tahun 
	 * @param bulan bulan minimal picker
	 * @param tgl tgl minimal date picker
	 *  
	 */
	private native void setMinimalDateWorker (String id , int tahun, int bulan , int tgl ) /*-{
		$wnd.console.log("id :" + id +", tahun = " + tahun +", bulan : " + bulan + ", tgl : " + tgl) ; 
		$wnd.$("#" + id).datepicker("option", "minDate", new Date(tahun, bulan - 1, tgl) );
	}-*/;
	
	private native void setMaximalDateWorker (String id , int tahun, int bulan , int tgl ) /*-{
		$wnd.$("#" + id).datepicker("option", "maxDate", new Date(tahun, bulan - 1 , tgl) );
	}-*/;




	public void setReadOnly(boolean readOnly) {
		getElement().setPropertyBoolean("readOnly", readOnly);
		String readOnlyStyle = "readonly";
		if (readOnly) {
			addStyleDependentName(readOnlyStyle);
		} else {
			removeStyleDependentName(readOnlyStyle);
		}
	}




	public void setSelectionRange(int pos, int length) {
		// Setting the selection range will not work for unattached elements.
		if (!isAttached()) {
			return;
		}

		if (length < 0) {
			throw new IndexOutOfBoundsException(
					"Length must be a positive integer. Length: " + length);
		}
		if (pos < 0 || length + pos > getText().length()) {
			throw new IndexOutOfBoundsException("From Index: " + pos
					+ "  To Index: " + (pos + length) + "  Text Length: "
					+ getText().length());
		}
		impl.setSelectionRange(getElement(), pos, length);
	}

	@Override
	public void setText(String text) {
		getElement().setPropertyString("value", text != null ? text : "");
	}

	/**
	 * string yang akan muncul setelah control. <br/>Misalnya perlu format dari date setelah date picker {mm/dd/yy} atau {bulan-tgl-tahun}
	 * @param label label yang akan muncul setelah kontrol
	 **/
	public void setTextAfterControl (String label){
		triggerOption( "appendText", label);
	}

	@Override
	public void setValue(final Date value) {
		if ( this.jqueryRendered)
			triggerSingleArgmentJQueryMethod(getJQWidgetId(), getJQueryClassName(), "setDate", generateDate(value));
		else{
			new Timer() {

				@Override
				public void run() {
					triggerSingleArgmentJQueryMethod(getJQWidgetId(), getJQueryClassName(), "setDate", generateDate(value));
				}
			}.schedule(600);
		}

	}



	@Override
	public void setValue(Date value, boolean fireEvents) {
		Date oldValue=getValue();
		if ( fireEvents){
			if ( (oldValue==null && value==null) ||
					(oldValue!=null && oldValue.equals(value)) ||
					(value!=null && value.equals(oldValue)) ){

			}
			else
				ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
		}
		setValue(value);

	}



	/**
	 * Default:<br/>
	 * 'Wk' <br/>
	 * The text to display for the week of the year column heading. This attribute is one of the regionalisation attributes. Use showWeek to display this column.
	 **/
	public void setWeekHeader(String weekHeader){
		triggerOption( "weekHeader", weekHeader);
	}


	/**
	 * 
	 * maksimal + minimal year yang tersedia dalam selector
	 **/
	public void setYearRange(int minYear ,int maxYear , boolean useRelativeToCurrentYear){
		String optionString= useRelativeToCurrentYear? "c-" + minYear +":c+" +maxYear  : minYear +":" + maxYear ;
		triggerOption("yearRange", optionString);

	}

	private   void unPlugMandatoryMarker (){
		if ( mandatoryMarker!=null&& mandatoryMarker.getParentElement() !=null){
			mandatoryMarker.removeFromParent();
		}
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	
}
