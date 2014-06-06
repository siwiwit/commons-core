/**
 * File Name : PageDefinition.java
 * Package   : id.co.gpsc.arium.security.shared.domain
 * Project   : security-data
 */
package id.co.gpsc.common.security.domain;

import id.co.gpsc.common.data.SingleKeyEntityData;
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
 * Entiti untuk tabel : sec_page_definition
 * @author I Gede Mahendra
 * @since Nov 9, 2012, 12:34:50 PM
 * @version $Id
 */
@Entity
@Table(name="sec_page_definition")
public class PageDefinition extends BaseCreatedObject implements SingleKeyEntityData<BigInteger>, IJSONFriendlyObject<PageDefinition>{

	private static final long serialVersionUID = -7013942142386812409L;
	
	@Id
	@TableGenerator(name = "generator_page_definition",
					table = "hibernate_sequences",
					pkColumnName = "sequence_name",
					valueColumnName = "sequence_next_hi_value",
					pkColumnValue = "sec_page_definition", 		      	  		      	    
					allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="generator_page_definition")
	@Column(name="PAGE_ID")
	private BigInteger id;
	
	@Column(name="PAGE_CODE")
	private String pageCode;
	
	@Column(name="APPLICATION_ID")
	private BigInteger applicationId;	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APPLICATION_ID", insertable=false, updatable=false)
	private Application application;
	
	@Column(name="PAGE_URL")
	private String pageUrl;
	
	@Column(name="ADDITIONAL_DATA")
	private String additionalData;

	
	
	/**
	 * catatan page
	 */
	@Column(name="page_remark" , length=256)
	private String remark ; 
	
	/**
	 * column : page_remark
	 * catatan dari halaman, ini halaman apa
	 **/
	/*@Column(name="page_remark" , length=256)
	private String remark ; */
	/**
	 * page id<br>
	 * COLUMN : PAGE_ID
	 * @return id
	 */
	public BigInteger getId() {
		return id;
	}
	
	/**
	 * page id<br>
	 * COLUMN : PAGE_ID
	 * @param id
	 */
	public void setId(BigInteger id) {
		this.id = id;
	}

	/**
	 * kode halaman, ini di maintain dengan konvensi. kode app + kode<br>
	 * COLUMN : PAGE_CODE
	 * @return pageCode
	 */
	public String getPageCode() {
		return pageCode;
	}

	/**
	 * kode halaman, ini di maintain dengan konvensi. kode app + kode<br>
	 * COLUMN : PAGE_CODE
	 * @param pageCode
	 */
	public void setPageCode(String pageCode) {
		this.pageCode = pageCode;
	}

	/**
	 * reference ke applikasi<br>
	 * COLUMN : APPLICATION_ID
	 * @return applicationId
	 */
	public BigInteger getApplicationId() {
		return applicationId;
	}

	/**
	 * reference ke applikasi<br>
	 * COLUMN : APPLICATION_ID
	 * @param applicationId
	 */
	public void setApplicationId(BigInteger applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * reference ke object Application {@link id.co.gpsc.common.security.domain.Application}
	 * @return application
	 */
	public Application getApplicationList() {
		return application;
	}

	/**
	 * reference ke object Application {@link id.co.gpsc.common.security.domain.Application}
	 * @param application
	 */
	public void setApplicationList(Application application) {
		this.application = application;
	}

	/**
	 * Url/ command /panel ID dari URL<br>
	 * COLUMN : PAGE_URL
	 * @return pageUrl
	 */
	public String getPageUrl() {
		return pageUrl;
	}

	/**
	 * Url/ command /panel ID dari URL<br>
	 * COLUMN : PAGE_URL
	 * @param pageUrl
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	/**
	 * additional data(yg di mengerti oleh applikasi, misal meta data dalam JSON etc)<br>
	 * COLUMN : ADDITIONAL_DATA
	 * @return additionalData
	 */
	public String getAdditionalData() {
		return additionalData;
	}

	/**
	 * additional data(yg di mengerti oleh applikasi, misal meta data dalam JSON etc)<br>
	 * COLUMN : ADDITIONAL_DATA
	 * @param additionalData
	 */
	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((additionalData == null) ? 0 : additionalData.hashCode());
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime * result
				+ ((applicationId == null) ? 0 : applicationId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((pageCode == null) ? 0 : pageCode.hashCode());
		result = prime * result + ((pageUrl == null) ? 0 : pageUrl.hashCode());
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
		PageDefinition other = (PageDefinition) obj;
		if (additionalData == null) {
			if (other.additionalData != null)
				return false;
		} else if (!additionalData.equals(other.additionalData))
			return false;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pageCode == null) {
			if (other.pageCode != null)
				return false;
		} else if (!pageCode.equals(other.pageCode))
			return false;
		if (pageUrl == null) {
			if (other.pageUrl != null)
				return false;
		} else if (!pageUrl.equals(other.pageUrl))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PageDefinition [id=" + id + ", pageCode=" + pageCode
				+ ", applicationId=" + applicationId + ", application="
				+ application + ", pageUrl=" + pageUrl + ", additionalData="
				+ additionalData + "]";
	}
	
	/**
	 * column : page_remark
	 * catatan dari halaman, ini halaman apa
	 **/
	/*public void setRemark(String remark) {
		this.remark = remark;
	}
	*//**
	 * column : page_remark
	 * catatan dari halaman, ini halaman apa
	 **//*
	public String getRemark() {
		return remark;
	}*/
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("additionalData",getAdditionalData());
		jsonContainer.put("applicationId",getApplicationId());
		  
		 Application param3 = getApplicationList();   
		 if ( param3 != null){ 
		
 //1. Ok tampung dulu variable
//2. null kan variable 
// 3 taruh ke json
			jsonContainer.put("applicationList", param3);
//4. restore lagi 
		}
		jsonContainer.put("applicationList",getApplicationList());
		jsonContainer.put("createdBy",getCreatedBy());
		jsonContainer.put("createdOn",getCreatedOn());
		jsonContainer.put("creatorIPAddress",getCreatorIPAddress());
		jsonContainer.put("id",getId());
		jsonContainer.put("pageCode",getPageCode());
		jsonContainer.put("pageUrl",getPageUrl());
		jsonContainer.put("remark",getRemark());
	}
	
	@Override
	public PageDefinition instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		PageDefinition retval = new PageDefinition();
		retval.setAdditionalData( (String)jsonContainer.get("additionalData" ,  String.class.getName()));
		retval.setApplicationId( (BigInteger)jsonContainer.get("applicationId" ,  BigInteger.class.getName()));
		  
		retval.setApplicationList( (Application)jsonContainer.get("applicationList" ,  Application.class.getName()));
		retval.setCreatedBy( (String)jsonContainer.get("createdBy" ,  String.class.getName()));
		retval.setCreatedOn( (Date)jsonContainer.get("createdOn" ,  Date.class.getName()));
		retval.setCreatorIPAddress( (String)jsonContainer.get("creatorIPAddress" ,  String.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setPageCode( (String)jsonContainer.get("pageCode" ,  String.class.getName()));
		retval.setPageUrl( (String)jsonContainer.get("pageUrl" ,  String.class.getName()));
		retval.setRemark( (String)jsonContainer.get("remark" ,  String.class.getName()));
		return retval; 
	}
	
	
	/**
	 * catatan page
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * catatan page
	 */
	public String getRemark() {
		return remark;
	}
}