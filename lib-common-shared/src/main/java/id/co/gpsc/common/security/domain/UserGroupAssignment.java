/**
 * File Name : UserGroupAssignment.java
 * Package   : id.co.gpsc.arium.security.shared.domain
 * Project   : security-data
 */
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

/**
 * Entiti untuk tabel : sec_group_assignment
 * @author I Gede Mahendra
 * @since Nov 9, 2012, 3:10:29 PM
 * @version $Id
 */
@Entity
@Table(name="sec_group_assignment")
public class UserGroupAssignment extends BaseCreatedObject implements IJSONFriendlyObject<UserGroupAssignment> {

	private static final long serialVersionUID = -7107320955056832737L;
	
	@Id
	@TableGenerator(name = "generator_group_assignment",
					table = "hibernate_sequences",
					pkColumnName = "sequence_name",
					valueColumnName = "sequence_next_hi_value",
					pkColumnValue = "sec_group_assignment", 		      	  		      	    
					allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_group_assignment")
	@Column(name="GROUP_ASSIGNMENT_ID")
	private BigInteger id;
	
	@Column(name="GROUP_ID")
	private BigInteger groupId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GROUP_ID", insertable=false, updatable=false, nullable=true)
	private UserGroup userGroup;
	
	@Column(name="USER_ID")
	private BigInteger userId;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=false, updatable=false, nullable=true)
	private User user;

	/**
	 * group assignment id<br>
	 * COLUMN : GROUP_ASSIGNMENT_ID
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * group assignment id<br>
	 * COLUMN : GROUP_ASSIGNMENT_ID
	 * @param id
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * group id<br>
	 * COLUMN : GROUP_ID
	 * @return groupId
	 */
	public BigInteger getGroupId() {
		return groupId;
	}

	/**
	 * group id<br>
	 * COLUMN : GROUP_ID
	 * @param groupId
	 */
	public void setGroupId(BigInteger groupId) {
		this.groupId = groupId;
	}

	/**
	 * reference ke object UserGroup {@link id.co.gpsc.common.security.domain.UserGroup}
	 * @return userGroup
	 */
	public UserGroup getUserGroup() {
		return userGroup;
	}
	
	/**
	 * reference ke object UserGroup {@link id.co.gpsc.common.security.domain.UserGroup}
	 * @param userGroup
	 */
	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

	/**
	 * user id<br>
	 * COLUMN : USER_ID
	 * @return id
	 */
	public BigInteger getUserId() {
		return userId;
	}

	/**
	 * user id<br>
	 * COLUMN : USER_ID
	 * @param userId
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	/**
	 * reference ke object UserGroup {@link id.co.gpsc.common.security.domain.User}
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * reference ke object UserGroup {@link id.co.gpsc.common.security.domain.User}
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		UserGroupAssignment other = (UserGroupAssignment) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserGroupAssignment [id=" + id + ", groupId=" + groupId
				+ ", userId=" + userId + "]";
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		jsonContainer.put("groupId",getGroupId());
		jsonContainer.put("id",getId());
		  
		 User param7 = getUser();   
		 if ( param7 != null){ 
		
 //1. Ok tampung dulu variable
			Application param7_defaultApplication_tmp = param7.getDefaultApplication();
//2. null kan variable 
			param7.setDefaultApplication(null);
// 3 taruh ke json
			jsonContainer.put("user", param7);
//4. restore lagi 
			param7.setDefaultApplication(param7_defaultApplication_tmp);
		}
		jsonContainer.put("user",getUser());
		  
		 UserGroup param8 = getUserGroup();   
		 if ( param8 != null){ 
		
 //1. Ok tampung dulu variable
			Application param8_application_tmp = param8.getApplication();
//2. null kan variable 
			param8.setApplication(null);
// 3 taruh ke json
			jsonContainer.put("userGroup", param8);
//4. restore lagi 
			param8.setApplication(param8_application_tmp);
		}
		jsonContainer.put("userGroup",getUserGroup());
		jsonContainer.put("userId",getUserId());
	}
	
	@Override
	public UserGroupAssignment instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		UserGroupAssignment retval = new UserGroupAssignment();
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		retval.setGroupId( (BigInteger)jsonContainer.get("groupId" ,  BigInteger.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		  
		retval.setUser( (User)jsonContainer.get("user" ,  User.class.getName()));
		  
		retval.setUserGroup( (UserGroup)jsonContainer.get("userGroup" ,  UserGroup.class.getName()));
		retval.setUserId( (BigInteger)jsonContainer.get("userId" ,  BigInteger.class.getName()));
		return retval; 
	}
}