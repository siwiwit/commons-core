package id.co.gpsc.common.security.domain;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Primary Key dari tabel : sec_application_user
 * @author I Gede Mahendra
 * @since Dec 14, 2012, 12:18:49 PM
 * @version $Id
 */
@Embeddable
public class ApplicationUserKey implements Serializable,IsSerializable, IJSONFriendlyObject<ApplicationUserKey>{

	private static final long serialVersionUID = 6796304485937487194L;
	
	@Column(name="user_id")
	private BigInteger userId;
	
	@Column(name="application_id")
	private BigInteger applicationId;

	/**
	* id user<br/>
	* column :user_id
	**/
	public BigInteger getUserId() {
		return userId;
	}

	/**
	* id user<br/>
	* column :user_id
	**/
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	/**
	* id applikasi<br/>
	* column :application_id
	**/
	public BigInteger getApplicationId() {
		return applicationId;
	}

	/**
	* id applikasi<br/>
	* column :application_id
	**/
	public void setApplicationId(BigInteger applicationId) {
		this.applicationId = applicationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((applicationId == null) ? 0 : applicationId.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicationUserKey other = (ApplicationUserKey) obj;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("applicationId",getApplicationId());
		jsonContainer.put("userId",getUserId());
	}
	
	@Override
	public ApplicationUserKey instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		ApplicationUserKey retval = new ApplicationUserKey();
		retval.setApplicationId( (BigInteger)jsonContainer.get("applicationId" ,  BigInteger.class.getName()));
		retval.setUserId( (BigInteger)jsonContainer.get("userId" ,  BigInteger.class.getName()));
		return retval; 
	}
	
}