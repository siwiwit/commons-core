package id.co.gpsc.common.client.widget.notification;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.HTML;

import id.co.gpsc.common.client.form.MandatoryValidationEnabledControl;
import id.co.gpsc.common.client.form.MandatoryValidationFailureException;
import id.co.gpsc.common.util.I18Utilities;


/**
 * 
 * dialog untuk mandatory violation failure
 * 
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 
 **/
public class MandatoryViolationNotificationDialog extends BaseNotificationDialog{
	
	private static final String NOTIFICATION_TEMPLATE="<p><span class='ui-icon ui-icon-alert' style='float:left; margin:0 7px 20px 0;'></span>" + 
			"<div style='height:200px;width:400px;overflow:auto'>" + 
				"{TITLE}<br/>{DETIL}"+ 

		"</div></p>";
	
	
	
	/**
	 * template untuk UL
	 **/
	private static final String UL_TEMPLATE ="<ul>{LI}</ul>";
	
	
	/**
	 * template untuk data dengan 2 column
	 **/
	private static final String TWO_COL_UL_TEMPLATE ="<table><tr valign='top'><td>{D1}</td><td>{D2}</td></tr></table>";
	
	
	
	/**
	 * batas minimal. kalau lebih dari ini maka data akan di pecah menjadi 2 column, kiri dan kanan
	 **/
	private static int LIMIT_FOR_SPLIT_TO_TWO_COLUMN=6;
	
	/**
	 * ada kemungkinan kalau suatu komponen tidak mengirimkan informasi detail, kontrol mana saja yang bermasalah. dlaam kasus seperti ini, maka message ini akan di tampilkan.
	 **/
	protected String defaultMessageOnNoControlDetails = "Mandatory field was reported not filled, but no detail was specified,Please check your entry";
	
	
	/**
	 * ini merupakan pasangan dari {@link #defaultMessageOnNoControlDetail}, jadinya internalization, kalau tidak ada detail maka ini di tampilkan
	 **/
	protected String defaultMessageOnNoControlI8NKey ; 
	
	public MandatoryViolationNotificationDialog(){
		super(); 
		this.dialogDefaultTitle =  "Mandatory Field(s) not filled";
	}
	
	

	/**
	 * menampilan dialog mandatory violation exception
	 * @param mandatoryViolationException notification untuk user
	 **/
	public void showNotification (MandatoryValidationFailureException mandatoryViolationException) {
		ArrayList<MandatoryValidationEnabledControl> controls =  mandatoryViolationException.getInvalidControls();
		if ( controls==null||controls.isEmpty()){
			String actual = NOTIFICATION_TEMPLATE.replaceAll("\\{TITLE}", I18Utilities.getInstance().getInternalitionalizeText(defaultMessageOnNoControlI8NKey, defaultMessageOnNoControlDetails))
						.replaceAll("\\{DETIL}", ""); 
			
			 
			showNotificationMessage(new HTML(actual));
			return ; 
		}
		else{
			if ( controls.size()> LIMIT_FOR_SPLIT_TO_TWO_COLUMN){
				showNotificationMessage(new HTML(generate2ColumnLI(controls)));
			}else{
				showNotificationMessage(new HTML(generateULStatement(controls, 0, controls.size())));
			}
		}
		
	}
	
	
	
	private String generate2ColumnLI(ArrayList<MandatoryValidationEnabledControl> controls) {
		int splitPoint = (int)Math.ceil(controls.size()/2);
		return TWO_COL_UL_TEMPLATE.replaceAll("\\{D1}", generateULStatement(controls, 0, splitPoint))
					.replaceAll("\\{D2}", generateULStatement(controls,  splitPoint , controls.size()));
		
		
	}
	
	/**
	 * generate UL + li untuk node yang bermasalah
	 **/
	private String generateULStatement(ArrayList<MandatoryValidationEnabledControl> controls , int startIndex , int endIndex) {
		 
		String str = "" ; 
		for ( int i=startIndex ; i<endIndex;i++){
			String lbl = controls.get(i).getControlBusinessName(); 
			if(lbl==null)
				lbl= "unknown";
			str +="<li>" + lbl  + "</li>"; 
		}
		return UL_TEMPLATE.replaceAll("\\{LI}", str); 
	}
	
	/**
	 * ada kemungkinan kalau suatu komponen tidak mengirimkan informasi detail, kontrol mana saja yang bermasalah. dlaam kasus seperti ini, maka message ini akan di tampilkan.
	 **/
	public void setDefaultMessageOnNoControlDetails(
			String defaultMessageOnNoControlDetails) {
		this.defaultMessageOnNoControlDetails = defaultMessageOnNoControlDetails;
	}
	/**
	 * ada kemungkinan kalau suatu komponen tidak mengirimkan informasi detail, kontrol mana saja yang bermasalah. dlaam kasus seperti ini, maka message ini akan di tampilkan.
	 **/
	public String getDefaultMessageOnNoControlDetails() {
		return defaultMessageOnNoControlDetails;
	}
	
	
	/**
	 * ini merupakan pasangan dari {@link #defaultMessageOnNoControlDetail}, jadinya internalization, kalau tidak ada detail maka ini di tampilkan
	 **/
	public String getDefaultMessageOnNoControlI8NKey() {
		return defaultMessageOnNoControlI8NKey;
	}
	/**
	 * ini merupakan pasangan dari {@link #defaultMessageOnNoControlDetail}, jadinya internalization, kalau tidak ada detail maka ini di tampilkan
	 **/
	public void setDefaultMessageOnNoControlI8NKey(
			String defaultMessageOnNoControlI8NKey) {
		this.defaultMessageOnNoControlI8NKey = defaultMessageOnNoControlI8NKey;
	}
	

}
