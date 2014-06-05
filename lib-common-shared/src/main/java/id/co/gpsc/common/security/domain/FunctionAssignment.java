/**
 * File Name : FunctionAssignment.java
 * Package   : id.co.sigma.arium.security.shared.domain
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
 * Entiti untuk tabel : sec_function_assignment
 * @author I Gede Mahendra
 * @since Nov 9, 2012, 3:28:50 PM
 * @version $Id
 */
@Entity
@Table(name="sec_function_assignment")
public class FunctionAssignment extends BaseCreatedObject implements IJSONFriendlyObject<FunctionAssignment> {

	private static final long serialVersionUID = 4776419933883162445L;
	
	@Id
	@TableGenerator(name = "generator_function_assignment",
					table = "hibernate_sequences",
					pkColumnName = "sequence_name",
					valueColumnName = "sequence_next_hi_value",
					pkColumnValue = "sec_function_assignment", 		      	  		      	    
					allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_function_assignment")
	@Column(name="FUNCTION_ASSIGNMENT_ID")
	private BigInteger id;
	
	@Column(name="FUNCTION_ID")
	private BigInteger functionId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FUNCTION_ID", insertable=false, updatable=false)
	private Function function;
	
	@Column(name="GROUP_ID")
	private BigInteger groupId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GROUP_ID", insertable=false, updatable=false)
	private UserGroup group;

	/**
	 * Function Assignment Id<br>
	 * COLUMN : FUNCTION_ASSIGNMENT_ID
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}

	/**
	 * Function Assignment Id<br>
	 * COLUMN : FUNCTION_ASSIGNMENT_ID
	 * @param id
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * Function  Id<br>
	 * COLUMN : FUNCTION_ID
	 * @return
	 */
	public BigInteger getFunctionId() {
		return functionId;
	}

	/**
	 * Function  Id<br>
	 * COLUMN : FUNCTION_ID
	 * @param functionId
	 */
	public void setFunctionId(BigInteger functionId) {
		this.functionId = functionId;
	}

	/**
	 * reference ke object Function {@link id.co.gpsc.common.security.domain.Function}
	 * @return function
	 */
	public Function getFunction() {
		return function;
	}

	/**
	 * reference ke object Function {@link id.co.gpsc.common.security.domain.Function}
	 * @param function
	 */
	public void setFunction(Function function) {
		this.function = function;
	}

	/**
	 * Group Id<br>
	 * COLUMN : GROUP_ID
	 * @return groupId
	 */
	public BigInteger getGroupId() {
		return groupId;
	}

	/**
	 * Group Id<br>
	 * COLUMN : GROUP_ID
	 * @param groupId
	 */
	public void setGroupId(BigInteger groupId) {
		this.groupId = groupId;
	}
	
	/**
	 * reference ke object UserGroup {@link id.co.gpsc.common.security.domain.UserGroup}
	 * @return group
	 */
	public UserGroup getGroup() {
		return group;
	}

	/**
	 * reference ke object UserGroup {@link id.co.gpsc.common.security.domain.UserGroup}
	 * @param group
	 */
	public void setGroup(UserGroup group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((functionId == null) ? 0 : functionId.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		FunctionAssignment other = (FunctionAssignment) obj;
		if (functionId == null) {
			if (other.functionId != null)
				return false;
		} else if (!functionId.equals(other.functionId))
			return false;
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
		return true;
	}

	@Override
	public String toString() {
		return "FunctionAssignment [id=" + id + ", functionId=" + functionId
				+ ", groupId=" + groupId + "]";
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		  
		 Function param5 = getFunction();   
		 if ( param5 != null){ 
		
 //1. Ok tampung dulu variable
			Application param5_application_tmp = param5.getApplication();
			PageDefinition param5_pageDefinition_tmp = param5.getPageDefinition();
//2. null kan variable 
			param5.setApplication(null);
			param5.setPageDefinition(null);
// 3 taruh ke json
			jsonContainer.put("function", param5);
//4. restore lagi 
			param5.setApplication(param5_application_tmp);
			param5.setPageDefinition(param5_pageDefinition_tmp);
		}
		jsonContainer.put("function",getFunction());
		jsonContainer.put("functionId",getFunctionId());
		/*hati hati dengan variable ini. ini bukan tipe simple dan bukan tipe id.co.sigma.common.util.json.IJSONFriendlyObject*/		jsonContainer.put("group",getGroup());
		jsonContainer.put("groupId",getGroupId());
		jsonContainer.put("id",getId());
	}
	
	@Override
	public FunctionAssignment instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		FunctionAssignment retval = new FunctionAssignment();
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		  
		retval.setFunction( (Function)jsonContainer.get("function" ,  Function.class.getName()));
		retval.setFunctionId( (BigInteger)jsonContainer.get("functionId" ,  BigInteger.class.getName()));
		/*hati hati dengan variable ini. ini bukan tipe simple dan bukan tipe id.co.sigma.common.util.json.IJSONFriendlyObject*/		retval.setGroup( (UserGroup)jsonContainer.get("group" ,  UserGroup.class.getName()));
		retval.setGroupId( (BigInteger)jsonContainer.get("groupId" ,  BigInteger.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		return retval; 
	}
}