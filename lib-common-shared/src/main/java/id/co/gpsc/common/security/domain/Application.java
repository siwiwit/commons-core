/**
 * File Name : Application.java
 * Package   : id.co.gpsc.arium.security.shared.domain
 * Project   : security-data
 */
package id.co.gpsc.common.security.domain;

import java.math.BigInteger;
import java.util.Date;

import id.co.gpsc.common.security.domain.audit.BaseAuditedObject;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
/**
 * Entiti untuk tabel : sec_application
 * @author I Gede Mahendra
 * @since Nov 9, 2012, 11:37:26 AM
 * @version $Id
 */
@Entity
@Table(name="sec_application")
public class Application extends BaseAuditedObject implements IJSONFriendlyObject<Application>{	

	private static final long serialVersionUID = 8389686936501266421L;

	@Id	
	@TableGenerator(name = "generator_application",
	    			table = "hibernate_sequences",
	    			pkColumnName = "sequence_name",
	    			valueColumnName = "sequence_next_hi_value",
	    			pkColumnValue = "sec_application", 		      	  		      	    
	    			allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_application")
	@Column(name="APPLICATION_ID")
	private BigInteger id;
	
	@Column(name="APPLICATION_CODE")
	private String applicationCode;
	
	@Column(name="APPLICATION_NAME")
	private String applicationName;
	
	@Column(name="APPLICATION_URL")
	private String applicationUrl;
	
	@Column(name="AUT_LOGIN_URL")
	private String autentificationLoginUrl;
	
	
	/**
	 * url untuk notifikasi Auth result. jadi nya dari app security ke app client - via http request
	 **/
	@Column(name="NOTIFY_SUCCED_URL")
	private String notifyAuthenticateResultUrl ;
	
	@Column(name="STATUS", length=1)
	private String status;
	
	@Column(name="LANG")
	private String language;
	
	@Column(name="SESSION_TIMEOUT")
	private Integer sessionTimeOut;
	
	
	
	/**
	 * kick prev login dalam kasus concurrent login
	 **/
	@Column(name="KICK_PREV_CONCURENT_LOGIN")
	private String kickPreviousLogin ; 
	
	
	
	
	/**
	 * ID applikasi<br>
	 * COLUMN : APPLICATION_ID
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * ID applikasi<br>
	 * COLUMN : APPLICATION_ID
	 * @param id
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * Kode applikasi<br>
	 * COLUMN : APPLICATION_CODE
	 * @return applicationCode
	 */
	public String getApplicationCode() {
		return applicationCode;
	}

	/**
	 * Kode applikasi<br>
	 * COLUMN : APPLICATION_CODE
	 * @param applicationCode
	 */
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}

	/**
	 * Nama applikasi<br>
	 * COLUMN : APPLICATION_NAME
	 * @return applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * Nama applikasi<br>
	 * COLUMN : APPLICATION_NAME
	 * @param applicationName
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * Status, active inactive<br>
	 * COLUMN : STATUS
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Status, active inactive<br>
	 * COLUMN : STATUS
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Internalization i18N<br>
	 * COLUMN : LANG
	 * @return language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Internalization i18N<br>
	 * COLUMN : LANG
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * Session timeout<br>
	 * COLUMN : SESSION_TIMEOUT
	 * @return sessionTimeOut
	 */
	public Integer getSessionTimeOut() {
		return sessionTimeOut;
	}

	/**
	 * Session timeout<br>
	 * COLUMN : SESSION_TIMEOUT
	 * @param sessionTimeOut
	 */
	public void setSessionTimeOut(Integer sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	/**
	 * URL dari aplikasi<br>
	 * COLUMN : APPLICATION_URL
	 * @return applicationUrl
	 */
	public String getApplicationUrl() {
		return applicationUrl;
	}
	
	/**
	 * URL dari aplikasi<br>
	 * COLUMN : APPLICATION_URL
	 * @param applicationUrl
	 */

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}
	
	/**
	 * Login URL<br>
	 * COLUMN : AUT_LOGIN_URL
	 * @return autentificationLoginUrl
	 */

	public String getAutentificationLoginUrl() {
		return autentificationLoginUrl;
	}
	
	/**
	 * Login URL<br>
	 * COLUMN : AUT_LOGIN_URL
	 * @param autentificationLoginUrl
	 */

	public void setAutentificationLoginUrl(String autentificationLoginUrl) {
		this.autentificationLoginUrl = autentificationLoginUrl;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((applicationCode == null) ? 0 : applicationCode.hashCode());
		result = prime * result
				+ ((applicationName == null) ? 0 : applicationName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((language == null) ? 0 : language.hashCode());
		result = prime * result
				+ ((sessionTimeOut == null) ? 0 : sessionTimeOut.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Application other = (Application) obj;
		if (applicationCode == null) {
			if (other.applicationCode != null)
				return false;
		} else if (!applicationCode.equals(other.applicationCode))
			return false;
		if (applicationName == null) {
			if (other.applicationName != null)
				return false;
		} else if (!applicationName.equals(other.applicationName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (sessionTimeOut == null) {
			if (other.sessionTimeOut != null)
				return false;
		} else if (!sessionTimeOut.equals(other.sessionTimeOut))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", applicationCode=" + applicationCode
				+ ", applicationName=" + applicationName + ", status=" + status
				+ ", language=" + language + ", sessionTimeOut="
				+ sessionTimeOut + "]";
	}
	/**
	 * url untuk notifikasi Auth result. jadi nya dari app security ke app client - via http request
	 **/
	public void setNotifyAuthenticateResultUrl(
			String notifyAuthenticateResultUrl) {
		this.notifyAuthenticateResultUrl = notifyAuthenticateResultUrl;
	}
	/**
	 * url untuk notifikasi Auth result. jadi nya dari app security ke app client - via http request
	 **/
	public String getNotifyAuthenticateResultUrl() {
		return notifyAuthenticateResultUrl;
	}
	
	
	public String generateNotificationAuthenticationUrl () {
		String app = this.getApplicationUrl() ; 
		if ( app.endsWith("/") ) 
			app = app.substring(0, app.length()-1);
		String path = notifyAuthenticateResultUrl;
		if ( !path.startsWith("/"))
			path ="/" + path ; 
		return app + path ; 
	}
	
	
	
	/**
	 * seharusnya ini login.j, bagaimana mekanisme auto login
	 **/
	public String generateAutomaticLoginUrl () {
		String app = this.getApplicationUrl() ; 
		if ( app.endsWith("/") ) 
			app = app.substring(0, app.length()-1);
		String path = autentificationLoginUrl;
		if ( !path.startsWith("/"))
			path ="/" + path ; 
		return app + path ; 
	}
	
	/**
	 * kick prev login dalam kasus concurrent login
	 **/
	public void setKickPreviousLogin(String kickPreviousLogin) {
		this.kickPreviousLogin = kickPreviousLogin;
	}
	/**
	 * kick prev login dalam kasus concurrent login
	 **/
	public String getKickPreviousLogin() {
		return kickPreviousLogin;
	}

	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("applicationCode",getApplicationCode());
		jsonContainer.put("applicationName",getApplicationName());
		jsonContainer.put("applicationUrl",getApplicationUrl());
		jsonContainer.put("autentificationLoginUrl",getAutentificationLoginUrl());
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		jsonContainer.put("id",getId());
		jsonContainer.put("kickPreviousLogin",getKickPreviousLogin());
		jsonContainer.put("language",getLanguage());
		jsonContainer.put("modifiedBy",getModifiedBy());
		jsonContainer.put("modifiedByIPAddress",getModifiedByIPAddress());
		jsonContainer.put("modifiedOn",getModifiedOn());
		jsonContainer.put("notifyAuthenticateResultUrl",getNotifyAuthenticateResultUrl());
		jsonContainer.put("sessionTimeOut",getSessionTimeOut());
		jsonContainer.put("status",getStatus());
	}
	@Override
	public Application instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		Application retval = new Application();
		retval.setApplicationCode( (String)jsonContainer.get("applicationCode" ,  String.class.getName()));
		retval.setApplicationName( (String)jsonContainer.get("applicationName" ,  String.class.getName()));
		retval.setApplicationUrl( (String)jsonContainer.get("applicationUrl" ,  String.class.getName()));
		retval.setAutentificationLoginUrl( (String)jsonContainer.get("autentificationLoginUrl" ,  String.class.getName()));
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setKickPreviousLogin( (String)jsonContainer.get("kickPreviousLogin" ,  String.class.getName()));
		retval.setLanguage( (String)jsonContainer.get("language" ,  String.class.getName()));
		retval.setModifiedBy( (String)jsonContainer.get("modifiedBy" ,  String.class.getName()));
		retval.setModifiedByIPAddress( (String)jsonContainer.get("modifiedByIPAddress" ,  String.class.getName()));
		retval.setModifiedOn( (Date)jsonContainer.get("modifiedOn" ,  Date.class.getName()));
		retval.setNotifyAuthenticateResultUrl( (String)jsonContainer.get("notifyAuthenticateResultUrl" ,  String.class.getName()));
		retval.setSessionTimeOut( (Integer)jsonContainer.get("sessionTimeOut" ,  Integer.class.getName()));
		retval.setStatus( (String)jsonContainer.get("status" ,  String.class.getName()));
		return retval; 
	}
}