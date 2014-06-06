package id.co.gpsc.common.client.security;

import id.co.gpsc.common.client.widget.BaseSimpleComposite;
import id.co.gpsc.common.security.dto.ApplicationDTO;
import id.co.gpsc.common.security.dto.UserDetailDTO;

import java.math.BigInteger;
import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

/**
 * composite base arium security
 * @author Gede Sutarsa
 * @since Dec 13, 2012, 12:05:27 PM
 * @version $Id
 */
public class BaseAriumSecurityComposite extends BaseSimpleComposite{
	
	
	
	/**
	 * Variable static yg menampung object applicationDTO yg didpt dari event click pd grid application
	 */
	private static ApplicationDTO applicationDTO;
	
	
	/**
	 * Get current application id
	 * @return BigInteger
	 */
	protected BigInteger getCurrentApplicationId () {
		/*return getUserDetail().getApplicationId();*/
		/*return BigInteger.ONE;*/
		return userDetail.getApplicationId();
	}
	
	/**
	 * Get current application name
	 * @return String
	 */
	protected String getApplicationName(){
		/*return getUserDetail().getApplicationName();*/
		return userDetail.getApplicationName();
	}
	
	
	
	/**
	 * Get fullname user login
	 * @return String
	 */
	protected String getCurrentFullnameUserLogin(){
		return userDetail.getFullNameUser();
	}
	
	/**
	 * Get application date
	 * @return
	 */
	protected String getCurrentApplicationDate(){
		
		//FIXME: anda perlu memasukan app date di sini
		return DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).format(new Date());
	}

	

	/**
	 * Set variable static untuk application DTO
	 * @return applicationDTO
	 */
	protected static ApplicationDTO getApplicationDTO() {
		return applicationDTO;
	}

	/**
	 * Get variable static untuk application DTO
	 * @param applicationDTO
	 */
	protected static void setApplicationDTO(ApplicationDTO applicationDTO) {
		BaseAriumSecurityComposite.applicationDTO = applicationDTO;
	}
	
	/**
	 * Get nama aplikasi spesial untuk title panel
	 * @return appName
	 */
	protected String getApplicationNameForTitlePanel(){
		String appName = "";
		if(getApplicationDTO() != null){
			appName = getApplicationDTO().getApplicationName().toUpperCase() + " - ";
		}
		return appName;
	}
}