package id.co.gpsc.common.security.domain;

import id.co.gpsc.common.security.domain.audit.BaseCreatedObject;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entiti untuk tabel : sec_password_hs
 * @author I Gede Mahendra
 * @since Nov 19, 2012, 2:38:18 PM
 * @version $Id
 */
@Entity
@Table(name="sec_password_hs")
public class UserPassword extends BaseCreatedObject implements IJSONFriendlyObject<UserPassword>{

	private static final long serialVersionUID = 1140422460020887761L;
	
	@Id
	@TableGenerator(name = "generator_password_hs",
					table = "hibernate_sequences",
					pkColumnName = "sequence_name",
					valueColumnName = "sequence_next_hi_value",
					pkColumnValue = "sec_password_hs", 		      	  		      	    
					allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_password_hs")
	@Column(name="PASSWORD_ID")
	private BigInteger id;
	
	@Column(name="USER_ID")
	private BigInteger userId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=false, updatable=false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="EFFECTIVE_DATE")
	private Date effectiveDate;
	
	@Column(name="CIPHER_TEXT")
	private String cipherText;

	/**
	 * password id<br>Column : PASSWORD_ID
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * password id<br>Column : PASSWORD_ID
	 * @param id
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * user id<br>Column : USER_ID
	 * @return userId
	 */
	public BigInteger getUserId() {
		return userId;
	}
	
	/**
	 * user id<br>Column : USER_ID
	 * @param userId
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	/**
	 * reference object User
	 * @return user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * reference object User
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Tanggal Efektif<br>Column : EFFECTIVE_DATE
	 * @return effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * Tanggal Efektif<br>Column : EFFECTIVE_DATE
	 * @param effectiveDate
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * Cipher text<br>Column : CIPHER_TEXT
	 * @return cipherText
	 */
	public String getCipherText() {
		return cipherText;
	}
	
	/**
	 * Cipher text<br>Column : CIPHER_TEXT
	 * @param cipherText
	 */
	public void setCipherText(String cipherText) {
		this.cipherText = cipherText;
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("cipherText",getCipherText());
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		jsonContainer.put("effectiveDate",getEffectiveDate());
		jsonContainer.put("id",getId());
		  
		 User param8 = getUser();   
		 if ( param8 != null){ 
		
 //1. Ok tampung dulu variable
			Application param8_defaultApplication_tmp = param8.getDefaultApplication();
//2. null kan variable 
			param8.setDefaultApplication(null);
// 3 taruh ke json
			jsonContainer.put("user", param8);
//4. restore lagi 
			param8.setDefaultApplication(param8_defaultApplication_tmp);
		}
		jsonContainer.put("user",getUser());
		jsonContainer.put("userId",getUserId());
	}
	
	@Override
	public UserPassword instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		UserPassword retval = new UserPassword();
		retval.setCipherText( (String)jsonContainer.get("cipherText" ,  String.class.getName()));
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		retval.setEffectiveDate( (Date)jsonContainer.get("effectiveDate" ,  Date.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		  
		retval.setUser( (User)jsonContainer.get("user" ,  User.class.getName()));
		retval.setUserId( (BigInteger)jsonContainer.get("userId" ,  BigInteger.class.getName()));
		return retval; 
	}
}