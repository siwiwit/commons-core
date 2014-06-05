package id.co.gpsc.common.client.util;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.client.ui.Widget;










import com.google.gwt.user.datepicker.client.CalendarUtil;

import id.co.gpsc.common.client.CommonClientControlConstant;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;



/**
 * common client
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 **/
public final class CommonClientControlUtil {
	
	private static CommonClientControlUtil instance ; 
	
	
	/**
	 * nilai default. pergunakan . untuk thousand separator atau tidak
	 **/
	public static final boolean DEFAULT_USE_DOT_FOR_THOUSAND_SEAPRATOR =false ; 
	
	
	
	
	
	/**
	 * URL url base dari applikasi . ini untuk memperbaiki servlet. ini bisa di timpa dengan url valid, kalau path entry point page dalam
	 **/
	private String applicationBaseUrl = GWT.getHostPageBaseURL();  
	
	
	
	/**
	 * ini adalah panel temporal tempat menaruh control sebelum di pindahkan ke tempat lain dengan operasi DOM. ini untuk menjamin semua event handler berjalan lancar
	 **/
	private FlowPanel temporalSwapPanel ; 
	
	
	/**
	 * currennt configuration. thousand separator . atau , kalau false berarti ,(default en)true  berarti thousand separator = .(ala indonesia)
	 **/
	private boolean useDotForThousandSeparator =false ; 
	
	
	
	/**
	 * flag thousand separator smt di configure atau tidak
	 **/
	private boolean thousandSeparatorEverConfigured =false ;  
	
	
	/**
	 * localization yang sedang di load
	 **/
	private String currentLoadedLocalization ; 
	
	
	
	
	
	
	/**
	 * singleton instance
	 **/
	public static CommonClientControlUtil getInstance() {
		if ( instance==null){
			instance=new CommonClientControlUtil();
			instance.configureConsoleObject(); 
		}
		return instance;
	}
	
	
	/**
	 * fallback kalau object console tidak di kenali
	 */
	private native void configureConsoleObject () /*-{
		if ( typeof $wnd.console === "undefined" ) {
			$wnd.console ={} ; 
		} 
		if ( typeof $wnd.console.log === "undefined"){
			$wnd.console.log=function(msg){
			}; 
		}
		if ( typeof $wnd.console.error === "undefined"){
			$wnd.console.error=function(msg){}; 
		}
	}-*/;
	
	
	
	private CommonClientControlUtil() {
		temporalSwapPanel = new FlowPanel(); 
		temporalSwapPanel.setVisible(false);
		RootPanel.get().add(temporalSwapPanel);
		
		
	}




	/**
	 * membaca resource sting dengan key
	 * @param key key dari resource yang perlu di akses
	 * 
	 **/
	public native String getStringResource (String key) /*-{
		try{
			return $wnd.id.co.sigma.languages[key];
		}
		catch(exc){
			return null ; 
		}	
		
	}-*/;
	/**
	 * worker untuk naruh mandatory marker 
	 * @param widget wiget yang di kasi marker mandatory
	 * @param mandatory flag mandatory/atau tidak
	 **/
	public void setMandatory(Widget widget, boolean mandatory){
		widget.getElement().setPropertyBoolean(CommonClientControlConstant.CONTROL_MANDATORY_DOM_KEY, mandatory);
		if ( mandatory)
			widget.getElement().setAttribute(CommonClientControlConstant.CONTROL_MANDATORY_DOM_KEY, "true");
		else
			widget.getElement().removeAttribute(CommonClientControlConstant.CONTROL_MANDATORY_DOM_KEY);
	}
	
	/**
	 * reload i18 Text. Dev mode dari database, else dari file dengan tag &lt;script&gt;&lt;/script&gt;
	 **/
	public void reloadI18Texts(String localeCode){
		String url = applicationBaseUrl+ "sigma-app-configuration/" +localeCode+ "/i18-groups.json";
		fetchScriptSynchronous(url);
	}
	
	
	
	
	/**
	 * loader script. ini bekerja synchronous
	 * @param scriptUrl url javascript
	 **/
	public native void fetchScriptSynchronous(String scriptUrl)/*-{
		$wnd.$.ajax({ 	
				type: "GET", 	
				global: true, 	
				url: scriptUrl, 	
				async: false, 	
				dataType: "script" });
		
	}-*/;
	
	
	
	
	/**
	 * currennt configuration. thousand separator . atau , kalau false berarti ,(default en)true  berarti thousand separator = .(ala indonesia)
	 **/
	public boolean isUseDotForThousandSeparator() {
		return useDotForThousandSeparator;
	}
	
	
	
	
	/**
	 * @param useDotForThousandSeparator = true -> . untuk thousand separator
	 **/
	public void setUseDotForThousandSeparator(boolean useDotForThousandSeparator) {
		this.useDotForThousandSeparator = useDotForThousandSeparator;
	}
	
	
	/**
	 * thousand separator apa yang di pakai (, atau .)
	 **/
	public String getThousandSeparator (){
		return useDotForThousandSeparator?"." :",";
	}
	
	
	/**
	 * decimal separator. normal nya pakai .
	 **/
	public String getDecimalSeparator () {
		return useDotForThousandSeparator?",":".";
	}


	
	
	/**
	 * add widget ke dalam temp container
	 **/
	public void addWidgetToSwapContainer(Widget w) {
		temporalSwapPanel.add(w);
	}
	
	
	
	protected static final String READ_ONLY_LABEL_KEY_ON_PARENT ="readonlyLabelDomRef";
	
	
	/**
	 * transform combo box menjadi readonly label
	 **/
	public void switchToReadOnlyLabel (ListBox targetListBox) {
		targetListBox.getElement().getStyle().setDisplay(Display.NONE);
		if ( targetListBox.getElement().getParentElement()== null)
			return ;
		Object swap  =  targetListBox.getElement().getPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT);
		if ( swap!= null){// sudah ada , jadinya asumsi nya masih visible. ndak di lanjutkan yah
			
			Element elem = (Element)swap ; 
			replaceReadonlyLabel(elem, getComboBoxLabel(targetListBox));
			elem.getStyle().setProperty("display", "");
			targetListBox.getElement().getStyle().setDisplay(Display.NONE);
			return ; 
		}
		
		String lbl = getComboBoxLabel(targetListBox); 
		Element e= instantiateReadonlyLabel(lbl);
		targetListBox.getElement().setPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT, e);
		Element parent =  targetListBox.getElement().getParentElement();
		parent.insertAfter(e, targetListBox.getElement());
		
		
	}
	
	
	protected String getComboBoxLabel (ListBox targetListBox ){
		String lbl ="" ; 
		int idx = targetListBox.getSelectedIndex();
		if ( idx>=0 && idx <targetListBox.getItemCount()){
			lbl
			 = targetListBox.getItemText(idx);
		} 
		return lbl ;
	}
	
	
	/**
	 * transform combo box menjadi readonly label
	 **/
	public void switchToReadOnlyLabel (ValueBoxBase<?> txt) {
		if ( txt.getElement().getParentElement()== null)
			return ;
		Object swap  =  txt.getElement().getPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT);
		if ( swap!= null){// sudah ada , jadinya asumsi nya masih visible. ndak di lanjutkan yah
			replaceReadonlyLabel((Element)swap, txt.getText());  
			return ;  
		}
		
		Element e= instantiateReadonlyLabel(txt.getText());
		txt.getElement().setPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT, e);
		Element parent =  txt.getElement().getParentElement();
		parent.insertAfter(e, txt.getElement());
		
		txt.getElement().getStyle().setDisplay(Display.NONE);
	}
	
	
	/**
	 * transform combo box menjadi readonly label
	 **/
	public void switchToReadOnlyLabel (Element txt, String label) {
		if ( txt.getParentElement()== null)
			return ;
		Object swap  =  txt.getPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT);
		if ( swap!= null){// sudah ada , jadinya asumsi nya masih visible. ndak di lanjutkan yah
			replaceReadonlyLabel((Element)swap, label);
			return ; 
		}
		
		Element e= instantiateReadonlyLabel(label);
		txt.setPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT, e);
		Element parent =  txt.getParentElement();
		parent.insertAfter(e, txt);
		txt.getStyle().setDisplay(Display.NONE);;
		
	}
	
	
	/**
	 * restore to normal mode
	 **/
	public void switchToNormalMode (ValueBoxBase<?> textbox) {
		if ( textbox.getElement().getParentElement()== null)
			return ;
		Object swap  =  textbox.getElement().getPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT);
		if ( swap== null){// ndak cocok nih, abaikan saja
			return ; 
		}
		Element e = (Element)swap ; 
		e.removeFromParent();
		textbox.getElement().setPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT, null);
		textbox.setVisible(true);
		textbox.getElement().getStyle().setProperty("display"  , "");
	}
	
	
	/**
	 * restore to normal mode
	 **/
	public void switchToNormalMode (ListBox targetListBox) {
		if ( targetListBox.getElement().getParentElement()== null)
			return ;
		Object swap  =  targetListBox.getElement().getPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT);
		if ( swap== null){// ndak cocok nih, abaikan saja
			return ; 
		}
		Element e = (Element)swap ; 
		e.removeFromParent();
		targetListBox.getElement().setPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT, null);
		targetListBox.getElement().getStyle().setProperty("display"  , "");
	}
	
	
	/**
	 * restore to normal mode
	 **/
	public void switchToNormalMode (Element targetListBox) {
		if ( targetListBox.getParentElement()== null)
			return ;
		Object swap  =  targetListBox.getPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT);
		if ( swap== null){// ndak cocok nih, abaikan saja
			return ; 
		}
		Element e = (Element)swap ; 
		e.removeFromParent();
		targetListBox.setPropertyObject(READ_ONLY_LABEL_KEY_ON_PARENT, null);
		targetListBox.getStyle().setProperty("display", "") ;
	}
	
	
	
	
	/**
	 * ini mmebuat span berisi text. kalau di perlukan css tertentu masukan di sini
	 **/
	protected Element instantiateReadonlyLabel ( String label) {
		Element e = DOM.createElement("span");
		replaceReadonlyLabel(e, label);
		
		return e; 
	}
	
	
	
	
	/**
	 * method ini untuk mengganti label dari readonly label. di pisah agar proses nya bisa di sahre dengan proses switch. karena dalam proses switch ini ada perlu update label readonly
	 **/
	protected void replaceReadonlyLabel(Element targetElement , String label ){
		targetElement.setInnerHTML(label);
		//FIXME: kalau perlu css, masukan di sini
	}
	
	/**
	 * URL url base dari applikasi . ini untuk memperbaiki servlet. ini bisa di timpa dengan url valid, kalau path entry point page dalam
	 **/
	public void setApplicationBaseUrl(String applicationBaseUrl) {
		this.applicationBaseUrl = applicationBaseUrl;
	}
	/**
	 * URL url base dari applikasi . ini untuk memperbaiki servlet. ini bisa di timpa dengan url valid, kalau path entry point page dalam<br/>
	 * ini di akhiri tanda <strong>/</strong>
	 **/
	public String getApplicationBaseUrl() {
		return applicationBaseUrl;
	}

	
	/**
	 * menebak base URL dari applikasi. ini di cek dengan object Windows
	 */
	public String guestBaseAppUrl ( ) {
		String hostName = Window.Location.getHost(); 
		String allUrl = GWT.getHostPageBaseURL();
		
		String allSplited [] = allUrl.split("/"); 
		String appName = null ;
		
		for ( int i = 0 ; i< allSplited.length ; i++){
			String scn = allSplited[i]; 
			if ( scn == null )
				continue ; 
			if ( scn.endsWith(hostName)){
				appName = allSplited [i+1];
				break ; 
			}
		}
		int posLast =  allUrl.indexOf(hostName) + hostName.length() + 1;
		return allUrl.substring(0 , posLast ) + appName + "/"; 
		 
	}

	
	
	
	/**
	 * deteksi user agent
	 */
	public   native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;
	
	
	
	/**
	 * deteksi IE atau bukan
	 */
	public boolean isInternetExplorer () {
		
		return getUserAgent().contains("msie") || getUserAgent().toLowerCase().contains("trident"); 
	}
	
	
	/**
	 * logger. 
	 */
	public void log (String message ) {
		logWorker(message);
	}
	
	
	
	/**
	 * logger error. ini dengan exception
	 */
	public void error ( Throwable errror , String additionalMessage ) {
		String msg = additionalMessage +".\nError exc : " + errror.getMessage() ; 
		errorWorker(msg);
	}
	
	
	public void error (   String errorMessage ) {
		errorWorker(errorMessage);
	}
	
	
	
	
	/**
	 * native worker untuk message dengan tipe log
	 */
	private native void logWorker (String message )/*-{
		$wnd.console.log( message); 
	}-*/;
	
	
	/**
	 * native worker untuk message dengan tipe error
	 */
	private native void errorWorker (String message )/*-{
		$wnd.console.error( message); 
	}-*/;
	
	
	
	
	
	
	/**
	 * generate filter dengan date betwen. jadinya bikin tgl skr + tgl h+1
	 * @param fieldName 
	 */
	public SigmaSimpleQueryFilter generateDateWithBetweenFilter ( String fieldName  , Date dateFilter )  {
		Date tglDari = generate00HourPassedDate(dateFilter); 
		Date tglSampai = generate00HourPassedDateNextDate(dateFilter);
		SigmaSimpleQueryFilter retval = new SigmaSimpleQueryFilter(fieldName ,tglDari , tglSampai ); 
		return retval ; 
		
	}
	
	
	
	/**
	 * membuat tgl besok
	 */
	public Date generate00HourPassedDateNextDate (Date date) {
		Date  tglBsk= new Date(date.getTime()); 
		CalendarUtil.addDaysToDate(tglBsk, 1);
		DateTimeFormat  fmt = DateTimeFormat.getFormat("yyyy-MM-dd"); 
		DateTimeFormat  fmtFull = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		String jam = fmt.format(tglBsk) + " 00:00:00";
		return fmtFull.parse(jam);
	}
	
	/**
	 * membuat tanggal yang sama jam 00. ini untuk filter from
	 */
	public Date generate00HourPassedDate (Date date) {
		if ( date == null)
			return null ;
		DateTimeFormat  fmt = DateTimeFormat.getFormat("yyyy-MM-dd"); 
		DateTimeFormat  fmtFull = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
		String jam = fmt.format(date) + " 00:00:00";
		return fmtFull.parse(jam); 
		
	}
	

}
