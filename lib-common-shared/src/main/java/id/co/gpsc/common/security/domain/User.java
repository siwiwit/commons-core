/**
 * File Name : User.java
 * Package   : id.co.sigma.arium.security.shared.domain
 * Project   : security-data
 */
package id.co.gpsc.common.security.domain;

import id.co.gpsc.common.security.domain.audit.BaseAuditedObject;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * Entiti untuk tabel : sec_user
 * @author I Gede Mahendra
 * @since Nov 9, 2012, 2:12:17 PM
 * @version $Id
 */
@Entity
@Table(name="sec_user")
public class User extends BaseAuditedObject implements IJSONFriendlyObject<User> {

	private static final long serialVersionUID = -6026281510605996562L;
	
	/**
	* id<br/>
	* column :USER_ID
	**/
	@Id
	@TableGenerator(name = "generator_user",
					table = "hibernate_sequences",
					pkColumnName = "sequence_name",
					valueColumnName = "sequence_next_hi_value",
					pkColumnValue = "sec_user", 		      	  		      	    
					allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_user")
	@Column(name="USER_ID")
	private BigInteger id;
	/**
	* kode user<br/>
	* column :USER_CODE
	**/
	@Column(name="USER_CODE")
	private String userCode;
	/**
	* tanggal expire<br/>
	* column :EXPIRED_DATE
	**/
	@Column(name="EXPIRED_DATE")
	private Date  expiredDate;
	/**
	* tanggal lahir<br/>
	* column :BIRTHDATE
	**/
	@Column(name="BIRTHDATE")
	private Date  birthDate;
	/**
	* datetime pattern<br/>
	* column :DATETIME_PATTERN
	**/
	@Column(name="DATETIME_PATTERN")
	private String datetimePattern;
	/**
	* timezone<br/>
	* column :TIMEZONE
	**/
	@Column(name="TIMEZONE")
	private String timezone;
	/**
	* desimal separator<br/>
	* column :DECIMAL_SEPARATOR
	**/
	@Column(name="DECIMAL_SEPARATOR")
	private String decimalSeparator;
	/**
	* numeric skale<br/>
	* column :NUMERIC_SCALE
	**/
	@Column(name="NUMERIC_SCALE")
	private Integer numericScale;
	/**
	* email<br/>
	* column :EMAIL
	**/
	@Column(name="EMAIL")
	private String email;
	/**
	* currency<br/>
	* column :CURRENCY
	**/
	@Column(name="CURRENCY")
	private String currency;
	/**
	* maksimal data per page<br/>
	* column :MAX_ROWS_PER_PAGE
	**/
	@Column(name="MAX_ROWS_PER_PAGE")
	private Integer maxRowPerPage;
	/**
	* failed login attemps<br/>
	* column :FAILED_LOGIN_ATTEMPTS
	**/
	@Column(name="FAILED_LOGIN_ATTEMPTS")
	private Integer failedLoginAttempts;
	/**
	* real name<br/>
	* column :REAL_NAME
	**/
	@Column(name="REAL_NAME")
	private String realName;
	/**
	* status<br/>
	* column :STATUS
	**/
	@Column(name="STATUS", length=1)
	private String status;
	/**
	* locale<br/>
	* column :LOCALE
	**/
	@Column(name="LOCALE")
	private String locale;
	/**
	* default application id<br/>
	* column :DEFAULT_APPLICATION_ID
	**/
	@Column(name="DEFAULT_APPLICATION_ID")
	private Integer defaultApplicationId;
	/**
	* active status<br/>
	* column :ACTIVE_STATUS
	**/
	@Column(name="ACTIVE_STATUS")
	private String activeStatus;
	/**
	* login status<br/>
	* column :LOGIN_STATUS
	**/
	@Column(name="LOGIN_STATUS")
	private String loginStatus;
	
	/*@OneToOne(fetch=FetchType.LAZY,mappedBy="user")
	private UserPassword password;*/
	
	/**
	 * add by dode
	 * default application
	 * column :DEFAULT_APPLICATION_ID
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="DEFAULT_APPLICATION_ID", insertable=false, updatable=false)
	private Application defaultApplication;
	
	/**
	* password user<br/>
	* column :CHIPPER_TEXT
	**/
	@Column(name="CHIPPER_TEXT")
	private String chipperText;
	
	/**
	* is ntlm user<br/>
	* column :IS_NTLM_USER
	**/
	@Column(name="IS_NTLM_USER")
	private String ntlmUser;
	
	/**
	 * flag super admin atau bukan
	 **/
	@Column(name="IS_SUPER_ADMIN")		
	private String superAdmin; 
	
	
	/**
	 * kode cabang default user, dia berada di mana <br/>
	 * column : default_branch_code
	 */
	@Column(name="default_branch_code", length=16)
	private String defaultBranchCode ; 
	
	
	
	
	
	/**
	* id<br/>
	* column :USER_ID
	**/
	public void setId(BigInteger id){
	  this.id=id;
	}
	/**
	* id<br/>
	* column :USER_ID
	**/
	public BigInteger getId(){
	    return this.id;
	}
	/**
	* kode user<br/>
	* column :USER_CODE
	**/
	public void setUserCode(String userCode){
	  this.userCode=userCode;
	}
	/**
	* kode user<br/>
	* column :USER_CODE
	**/
	public String getUserCode(){
	    return this.userCode;
	}
	/**
	* tanggal expire<br/>
	* column :EXPIRED_DATE
	**/
	public void setExpiredDate(Date  expiredDate){
	  this.expiredDate=expiredDate;
	}
	/**
	* tanggal expire<br/>
	* column :EXPIRED_DATE
	**/
	public Date getExpiredDate(){
	    return this.expiredDate;
	}
	/**
	* tanggal lahir<br/>
	* column :BIRTHDATE
	**/
	public void setBirthDate(Date  birthDate){
	  this.birthDate=birthDate;
	}
	/**
	* tanggal lahir<br/>
	* column :BIRTHDATE
	**/
	public Date getBirthDate(){
	    return this.birthDate;
	}
	/**
	* datetime pattern<br/>
	* column :DATETIME_PATTERN
	**/
	public void setDatetimePattern(String datetimePattern){
	  this.datetimePattern=datetimePattern;
	}
	/**
	* datetime pattern<br/>
	* column :DATETIME_PATTERN
	**/
	public String getDatetimePattern(){
	    return this.datetimePattern;
	}
	/**
	* timezone<br/>
	* column :TIMEZONE
	**/
	public void setTimezone(String timezone){
	  this.timezone=timezone;
	}
	/**
	* timezone<br/>
	* column :TIMEZONE
	**/
	public String getTimezone(){
	    return this.timezone;
	}
	/**
	* desimal separator<br/>
	* column :DECIMAL_SEPARATOR
	**/
	public void setDecimalSeparator(String decimalSeparator){
	  this.decimalSeparator=decimalSeparator;
	}
	/**
	* desimal separator<br/>
	* column :DECIMAL_SEPARATOR
	**/
	public String getDecimalSeparator(){
	    return this.decimalSeparator;
	}
	/**
	* numeric skale<br/>
	* column :NUMERIC_SCALE
	**/
	public void setNumericScale(Integer numericScale){
	  this.numericScale=numericScale;
	}
	/**
	* numeric skale<br/>
	* column :NUMERIC_SCALE
	**/
	public Integer getNumericScale(){
	    return this.numericScale;
	}
	/**
	* email<br/>
	* column :EMAIL
	**/
	public void setEmail(String email){
	  this.email=email;
	}
	/**
	* email<br/>
	* column :EMAIL
	**/
	public String getEmail(){
	    return this.email;
	}
	/**
	* currency<br/>
	* column :CURRENCY
	**/
	public void setCurrency(String currency){
	  this.currency=currency;
	}
	/**
	* currency<br/>
	* column :CURRENCY
	**/
	public String getCurrency(){
	    return this.currency;
	}
	/**
	* maksimal data per page<br/>
	* column :MAX_ROWS_PER_PAGE
	**/
	public void setMaxRowPerPage(Integer maxRowPerPage){
	  this.maxRowPerPage=maxRowPerPage;
	}
	/**
	* maksimal data per page<br/>
	* column :MAX_ROWS_PER_PAGE
	**/
	public Integer getMaxRowPerPage(){
	    return this.maxRowPerPage;
	}
	/**
	* failed login attemps<br/>
	* column :FAILED_LOGIN_ATTEMPTS
	**/
	public void setFailedLoginAttempts(Integer failedLoginAttempts){
	  this.failedLoginAttempts=failedLoginAttempts;
	}
	/**
	* failed login attemps<br/>
	* column :FAILED_LOGIN_ATTEMPTS
	**/
	public Integer getFailedLoginAttempts(){
	    return this.failedLoginAttempts;
	}
	/**
	* real name<br/>
	* column :REAL_NAME
	**/
	public void setRealName(String realName){
	  this.realName=realName;
	}
	/**
	* real name<br/>
	* column :REAL_NAME
	**/
	public String getRealName(){
	    return this.realName;
	}
	/**
	* status<br/>
	* column :STATUS
	**/
	public void setStatus(String status){
	  this.status=status;
	}
	/**
	* status<br/>
	* column :STATUS
	**/
	public String getStatus(){
	    return this.status;
	}
	/**
	* locale<br/>
	* column :LOCALE
	**/
	public void setLocale(String locale){
	  this.locale=locale;
	}
	/**
	* locale<br/>
	* column :LOCALE
	**/
	public String getLocale(){
	    return this.locale;
	}
	/**
	* default application id<br/>
	* column :DEFAULT_APPLICATION_ID
	**/
	public void setDefaultApplicationId(Integer defaultApplicationId){
	  this.defaultApplicationId=defaultApplicationId;
	}
	/**
	* default application id<br/>
	* column :DEFAULT_APPLICATION_ID
	**/
	public Integer getDefaultApplicationId(){
	    return this.defaultApplicationId;
	}
	/**
	* active status<br/>
	* column :ACTIVE_STATUS
	**/
	public void setActiveStatus(String activeStatus){
	  this.activeStatus=activeStatus;
	}
	/**
	* active status<br/>
	* column :ACTIVE_STATUS
	**/
	public String getActiveStatus(){
	    return this.activeStatus;
	}
	/**
	* login status<br/>
	* column :LOGIN_STATUS
	**/
	public void setLoginStatus(String loginStatus){
	  this.loginStatus=loginStatus;
	}
	/**
	* login status<br/>
	* column :LOGIN_STATUS
	**/
	public String getLoginStatus(){
	    return this.loginStatus;
	}
	/**
	 * Reference object UserPassword
	 * @return password
	 */
	/*public UserPassword getPassword() {
		return password;
	}*/
	/**
	 * Reference object UserPassword
	 * @param password
	 */
	/*public void setPassword(UserPassword password) {
		this.password = password;
	}*/
	
	/**
	 * add by dode
	 * default application
	 * column :DEFAULT_APPLICATION_ID
	 */
	public void setDefaultApplication(Application defaultApplication) {
		this.defaultApplication = defaultApplication;
	}
	
	/**
	 * add by dode
	 * default application
	 * column :DEFAULT_APPLICATION_ID
	 */
	public Application getDefaultApplication() {
		return defaultApplication;
	}
	
	/**
	 * password<br/>
	 * column :CHIPPER_TEXT
	 */
	public String getChipperText() {
		return chipperText;
	}
	/**
	 * password<br/>
	 * column :CHIPPER_TEXT
	 */
	public void setChipperText(String chipperText) {
		this.chipperText = chipperText;
	}
	
	/**
	* is ntlm user<br/>
	* column :IS_NTLM_USER
	**/
	public String getNtlmUser() {
		return ntlmUser;
	}
	
	/**
	* is ntlm user<br/>
	* column :IS_NTLM_USER
	**/
	public void setNtlmUser(String ntlmUser) {
		this.ntlmUser = ntlmUser;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((activeStatus == null) ? 0 : activeStatus.hashCode());
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		result = prime * result
				+ ((datetimePattern == null) ? 0 : datetimePattern.hashCode());
		result = prime
				* result
				+ ((decimalSeparator == null) ? 0 : decimalSeparator.hashCode());
		result = prime
				* result
				+ ((defaultApplicationId == null) ? 0 : defaultApplicationId
						.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((expiredDate == null) ? 0 : expiredDate.hashCode());
		result = prime
				* result
				+ ((failedLoginAttempts == null) ? 0 : failedLoginAttempts
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result
				+ ((loginStatus == null) ? 0 : loginStatus.hashCode());
		result = prime * result
				+ ((maxRowPerPage == null) ? 0 : maxRowPerPage.hashCode());
		result = prime * result
				+ ((numericScale == null) ? 0 : numericScale.hashCode());
		result = prime * result
				+ ((realName == null) ? 0 : realName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((timezone == null) ? 0 : timezone.hashCode());
		result = prime * result
				+ ((userCode == null) ? 0 : userCode.hashCode());
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
		User other = (User) obj;
		if (activeStatus == null) {
			if (other.activeStatus != null)
				return false;
		} else if (!activeStatus.equals(other.activeStatus))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (datetimePattern == null) {
			if (other.datetimePattern != null)
				return false;
		} else if (!datetimePattern.equals(other.datetimePattern))
			return false;
		if (decimalSeparator == null) {
			if (other.decimalSeparator != null)
				return false;
		} else if (!decimalSeparator.equals(other.decimalSeparator))
			return false;
		if (defaultApplicationId == null) {
			if (other.defaultApplicationId != null)
				return false;
		} else if (!defaultApplicationId.equals(other.defaultApplicationId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (expiredDate == null) {
			if (other.expiredDate != null)
				return false;
		} else if (!expiredDate.equals(other.expiredDate))
			return false;
		if (failedLoginAttempts == null) {
			if (other.failedLoginAttempts != null)
				return false;
		} else if (!failedLoginAttempts.equals(other.failedLoginAttempts))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locale == null) {
			if (other.locale != null)
				return false;
		} else if (!locale.equals(other.locale))
			return false;
		if (loginStatus == null) {
			if (other.loginStatus != null)
				return false;
		} else if (!loginStatus.equals(other.loginStatus))
			return false;
		if (maxRowPerPage == null) {
			if (other.maxRowPerPage != null)
				return false;
		} else if (!maxRowPerPage.equals(other.maxRowPerPage))
			return false;
		if (numericScale == null) {
			if (other.numericScale != null)
				return false;
		} else if (!numericScale.equals(other.numericScale))
			return false;
		if (realName == null) {
			if (other.realName != null)
				return false;
		} else if (!realName.equals(other.realName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (timezone == null) {
			if (other.timezone != null)
				return false;
		} else if (!timezone.equals(other.timezone))
			return false;
		if (userCode == null) {
			if (other.userCode != null)
				return false;
		} else if (!userCode.equals(other.userCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", userCode=" + userCode + ", expiredDate="
				+ expiredDate + ", birthDate=" + birthDate
				+ ", datetimePattern=" + datetimePattern + ", timezone="
				+ timezone + ", decimalSeparator=" + decimalSeparator
				+ ", numericScale=" + numericScale + ", email=" + email
				+ ", currency=" + currency + ", maxRowPerPage=" + maxRowPerPage
				+ ", failedLoginAttempts=" + failedLoginAttempts
				+ ", realName=" + realName + ", status=" + status + ", locale="
				+ locale + ", defaultApplicationId=" + defaultApplicationId
				+ ", activeStatus=" + activeStatus + ", loginStatus="
				+ loginStatus + "]";
	}
	
	/**
	 * flag super admin atau bukan
	 **/
	public String getSuperAdmin() {
		return superAdmin;		
	}
	
	/**
	 * flag super admin atau bukan
	 **/
	public void setSuperAdmin(String superAdmin) {
		this.superAdmin = superAdmin;
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("activeStatus",getActiveStatus());
		jsonContainer.put("birthDate",getBirthDate());
		jsonContainer.put("chipperText",getChipperText());
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		jsonContainer.put("currency",getCurrency());
		jsonContainer.put("datetimePattern",getDatetimePattern());
		jsonContainer.put("decimalSeparator",getDecimalSeparator());
		  
		 Application param11 = getDefaultApplication();   
		 if ( param11 != null){ 
		
 //1. Ok tampung dulu variable
//2. null kan variable 
// 3 taruh ke json
			jsonContainer.put("defaultApplication", param11);
//4. restore lagi 
		}
		jsonContainer.put("defaultApplication",getDefaultApplication());
		jsonContainer.put("defaultApplicationId",getDefaultApplicationId());
		jsonContainer.put("email",getEmail());
		jsonContainer.put("expiredDate",getExpiredDate());
		jsonContainer.put("failedLoginAttempts",getFailedLoginAttempts());
		jsonContainer.put("id",getId());
		jsonContainer.put("locale",getLocale());
		jsonContainer.put("loginStatus",getLoginStatus());
		jsonContainer.put("maxRowPerPage",getMaxRowPerPage());
		jsonContainer.put("modifiedBy",getModifiedBy());
		jsonContainer.put("modifiedByIPAddress",getModifiedByIPAddress());
		jsonContainer.put("modifiedOn",getModifiedOn());
		jsonContainer.put("ntlmUser",getNtlmUser());
		jsonContainer.put("numericScale",getNumericScale());
		jsonContainer.put("realName",getRealName());
		jsonContainer.put("status",getStatus());
		jsonContainer.put("superAdmin",getSuperAdmin());
		jsonContainer.put("timezone",getTimezone());
		jsonContainer.put("userCode",getUserCode());
		jsonContainer.put("defaultBranchCode", getDefaultBranchCode());
	}
	@Override
	public User instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		User retval = new User();
		retval.setActiveStatus( (String)jsonContainer.get("activeStatus" ,  String.class.getName()));
		retval.setBirthDate( (Date)jsonContainer.get("birthDate" ,  Date.class.getName()));
		retval.setChipperText( (String)jsonContainer.get("chipperText" ,  String.class.getName()));
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		retval.setCurrency( (String)jsonContainer.get("currency" ,  String.class.getName()));
		retval.setDatetimePattern( (String)jsonContainer.get("datetimePattern" ,  String.class.getName()));
		retval.setDecimalSeparator( (String)jsonContainer.get("decimalSeparator" ,  String.class.getName()));
		  
		retval.setDefaultApplication( (Application)jsonContainer.get("defaultApplication" ,  Application.class.getName()));
		retval.setDefaultApplicationId( (Integer)jsonContainer.get("defaultApplicationId" ,  Integer.class.getName()));
		retval.setEmail( (String)jsonContainer.get("email" ,  String.class.getName()));
		retval.setExpiredDate( (Date)jsonContainer.get("expiredDate" ,  Date.class.getName()));
		retval.setFailedLoginAttempts( (Integer)jsonContainer.get("failedLoginAttempts" ,  Integer.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setLocale( (String)jsonContainer.get("locale" ,  String.class.getName()));
		retval.setLoginStatus( (String)jsonContainer.get("loginStatus" ,  String.class.getName()));
		retval.setMaxRowPerPage( (Integer)jsonContainer.get("maxRowPerPage" ,  Integer.class.getName()));
		retval.setModifiedBy( (String)jsonContainer.get("modifiedBy" ,  String.class.getName()));
		retval.setModifiedByIPAddress( (String)jsonContainer.get("modifiedByIPAddress" ,  String.class.getName()));
		retval.setModifiedOn( (Date)jsonContainer.get("modifiedOn" ,  Date.class.getName()));
		retval.setNtlmUser( (String)jsonContainer.get("ntlmUser" ,  String.class.getName()));
		retval.setNumericScale( (Integer)jsonContainer.get("numericScale" ,  Integer.class.getName()));
		retval.setRealName( (String)jsonContainer.get("realName" ,  String.class.getName()));
		retval.setStatus( (String)jsonContainer.get("status" ,  String.class.getName()));
		retval.setSuperAdmin( (String)jsonContainer.get("superAdmin" ,  String.class.getName()));
		retval.setTimezone( (String)jsonContainer.get("timezone" ,  String.class.getName()));
		retval.setUserCode( (String)jsonContainer.get("userCode" ,  String.class.getName()));
		retval.setDefaultBranchCode(jsonContainer.getAsString("defaultBranchCode")); 
		return retval; 
	}
	
	/**
	 * kode cabang default user, dia berada di mana <br/>
	 * column : default_branch_code
	 */
	public void setDefaultBranchCode(String defaultBranchCode) {
		this.defaultBranchCode = defaultBranchCode;
	}
	/**
	 * kode cabang default user, dia berada di mana <br/>
	 * column : default_branch_code
	 */
	public String getDefaultBranchCode() {
		return defaultBranchCode;
	}
	
	
}