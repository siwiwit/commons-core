/**
 * File Name : Branch.java
 * Package   : id.co.gpsc.arium.security.shared.domain
 * Project   : security-data
 */
package id.co.gpsc.common.security.domain;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import id.co.gpsc.common.security.domain.audit.BaseAuditedObject;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

/**
 * Entiti untuk tabel : sec_branch
 * @author I Gede Mahendra
 * @since Nov 9, 2012, 2:37:05 PM
 * @version $Id
 */
@Entity
@Table(name="sec_branch")
public class Branch extends BaseAuditedObject implements IJSONFriendlyObject<Branch>{

	private static final long serialVersionUID = 1430766844084002850L;
	
	/**
	* branch id<br/>
	* column :BRANCH_ID
	**/
	@Id	
	@TableGenerator(name = "generator_branch",
					table = "hibernate_sequences",
					pkColumnName = "sequence_name",
					valueColumnName = "sequence_next_hi_value",
					pkColumnValue = "sec_branch", 		      	  		      	    
					allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_branch")
	@Column(name="BRANCH_ID")
	private BigInteger id;
	/**
	* reference ke parent<br/>
	* column :BRANCH_ID_PARENT
	**/
	@Column(name="BRANCH_ID_PARENT")
	private BigInteger branchParendId;
	/**
	* kode cabang<br/>
	* column :BRANCH_CODE
	**/
	@Column(name="BRANCH_CODE")
	private String branchCode;
	/**
	* nama cabang<br/>
	* column :BRANCH_NAME
	**/
	@Column(name="BRANCH_NAME")
	private String branchName;
	/**
	* alamat cabang<br/>
	* column :BRANCH_ADDRESS
	**/
	@Column(name="BRANCH_ADDRESS")
	private String branchAddress;
	/**
	* status<br/>
	* column :STATUS
	**/
	@Column(name="STATUS", length=1)
	private String status;
	/**
	* deskripsi<br/>
	* column :DESCRIPTION
	**/
	@Column(name="DESCRIPTION")
	private String description;

	/**
	 * Default Constructor
	 */
	public Branch() {
		super();
	}
	
	/**
	 * Additional Branch
	 * @param idBranch
	 */
	public Branch(BigInteger idBranch){
		this.id = idBranch;
	}
	
	/**
	* branch id<br/>
	* column :BRANCH_ID
	**/
	public void setId(BigInteger id){
	  this.id=id;
	}
	/**
	* branch id<br/>
	* column :BRANCH_ID
	**/
	public BigInteger getId(){
	    return this.id;
	}
	/**
	* reference ke parent<br/>
	* column :BRANCH_ID_PARENT
	**/
	public void setBranchParendId(BigInteger branchParendId){
	  this.branchParendId=branchParendId;
	}
	/**
	* reference ke parent<br/>
	* column :BRANCH_ID_PARENT
	**/
	public BigInteger getBranchParendId(){
	    return this.branchParendId;
	}
	/**
	* kode cabang<br/>
	* column :BRANCH_CODE
	**/
	public void setBranchCode(String branchCode){
	  this.branchCode=branchCode;
	}
	/**
	* kode cabang<br/>
	* column :BRANCH_CODE
	**/
	public String getBranchCode(){
	    return this.branchCode;
	}
	/**
	* nama cabang<br/>
	* column :BRANCH_NAME
	**/
	public void setBranchName(String branchName){
	  this.branchName=branchName;
	}
	/**
	* nama cabang<br/>
	* column :BRANCH_NAME
	**/
	public String getBranchName(){
	    return this.branchName;
	}
	/**
	* alamat cabang<br/>
	* column :BRANCH_ADDRESS
	**/
	public void setBranchAddress(String branchAddress){
	  this.branchAddress=branchAddress;
	}
	/**
	* alamat cabang<br/>
	* column :BRANCH_ADDRESS
	**/
	public String getBranchAddress(){
	    return this.branchAddress;
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
	* deskripsi<br/>
	* column :DESCRIPTION
	**/
	public void setDescription(String description){
	  this.description=description;
	}
	/**
	* deskripsi<br/>
	* column :DESCRIPTION
	**/
	public String getDescription(){
	    return this.description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((branchAddress == null) ? 0 : branchAddress.hashCode());
		result = prime * result
				+ ((branchCode == null) ? 0 : branchCode.hashCode());
		result = prime * result
				+ ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result
				+ ((branchParendId == null) ? 0 : branchParendId.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Branch other = (Branch) obj;
		if (branchAddress == null) {
			if (other.branchAddress != null)
				return false;
		} else if (!branchAddress.equals(other.branchAddress))
			return false;
		if (branchCode == null) {
			if (other.branchCode != null)
				return false;
		} else if (!branchCode.equals(other.branchCode))
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (branchParendId == null) {
			if (other.branchParendId != null)
				return false;
		} else if (!branchParendId.equals(other.branchParendId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "Branch [id=" + id + ", branchParendId=" + branchParendId
				+ ", branchCode=" + branchCode + ", branchName=" + branchName
				+ ", branchAddress=" + branchAddress + ", status=" + status
				+ ", description=" + description + "]";
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("branchAddress",getBranchAddress());
		jsonContainer.put("branchCode",getBranchCode());
		jsonContainer.put("branchName",getBranchName());
		jsonContainer.put("branchParendId",getBranchParendId());
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		jsonContainer.put("description",getDescription());
		jsonContainer.put("id",getId());
		jsonContainer.put("modifiedBy",getModifiedBy());
		jsonContainer.put("modifiedByIPAddress",getModifiedByIPAddress());
		jsonContainer.put("modifiedOn",getModifiedOn());
		jsonContainer.put("status",getStatus());
	}
	@Override
	public Branch instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		Branch retval = new Branch();
		retval.setBranchAddress( (String)jsonContainer.get("branchAddress" ,  String.class.getName()));
		retval.setBranchCode( (String)jsonContainer.get("branchCode" ,  String.class.getName()));
		retval.setBranchName( (String)jsonContainer.get("branchName" ,  String.class.getName()));
		retval.setBranchParendId( (BigInteger)jsonContainer.get("branchParendId" ,  BigInteger.class.getName()));
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		retval.setDescription( (String)jsonContainer.get("description" ,  String.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setModifiedBy( (String)jsonContainer.get("modifiedBy" ,  String.class.getName()));
		retval.setModifiedByIPAddress( (String)jsonContainer.get("modifiedByIPAddress" ,  String.class.getName()));
		retval.setModifiedOn( (Date)jsonContainer.get("modifiedOn" ,  Date.class.getName()));
		retval.setStatus( (String)jsonContainer.get("status" ,  String.class.getName()));
		return retval; 
	}
}