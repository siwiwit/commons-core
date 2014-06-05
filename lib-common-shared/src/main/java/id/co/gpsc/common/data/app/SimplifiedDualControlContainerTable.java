package id.co.gpsc.common.data.app;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * mapper ke table : m_dual_control_table, tanpa lob data. ini di pergunakan untuk grid. jadinya data di tarik lebih ringan. <br/>
 * ini cuma di desain untuk proses read
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 *@deprecated ini tidak jelas di pergunakan di mana. class ini akan di drop
 */
@Entity
@Table(name="m_dual_control_table")
@Deprecated
public class SimplifiedDualControlContainerTable implements Serializable, IJSONFriendlyObject<SimplifiedDualControlContainerTable>{


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8951296476941511050L;

	/**
	* primary key data<br/>
	* column :pk
	**/
	@Id
	@Column(name="pk", nullable=true)
	@TableGenerator(name = "generator_m_dual_control_table",
		table = "hibernate_sequences",
		pkColumnName = "sequence_name",
		valueColumnName = "sequence_next_hi_value",
		pkColumnValue = "sec_dual_control", 		      	  		      	    
		allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.TABLE , generator="generator_m_dual_control_table")
	private BigInteger id;
	 
	/**
	* CREATE,UPDATE,DELETE<br/>
	* column :operation_code
	**/
	@Column(name="operation_code", nullable=true)
	private String operationCode;
	 
	/**
	* full qualified class name dari data. ini untuk POJO dari object<br/>
	* column :target_fqcn
	**/
	@Column(name="target_fqcn", nullable=true, updatable=false)
	private String targetObjectFQCN;
	
	
	
	
	/**
	 * reference as object ke dual control definition
	 **/
	@ManyToOne(fetch=FetchType.EAGER )
	@JoinColumns(value={
			@JoinColumn(name="target_fqcn" , insertable=false, updatable=false )
	})
	private DualControlDefinition dualControlDefinition ; 
	 
	/**
	* approval status<br/>
	* column :approval_status
	**/
	@Column(name="approval_status", nullable=true)
	private String approvalStatus;
	 
	/**
	* user creator<br/>
	* column :creator_user_id
	**/
	@Column(name="creator_user_id", nullable=true)
	private String creatorUserId;
	 
	/**
	* timestamp create<br/>
	* column :created_time
	**/
	@Column(name="created_time", nullable=true)
        @Temporal(TemporalType.TIMESTAMP)
	private Date createdTime;
	 
	/**
	* user approver<br/>
	* column :approver_user_id
	**/
	@Column(name="approver_user_id", nullable=true)
	private String approverUserId;
	 
	/**
	* user yang approve<br/>
	* column :approved_time
	**/
	@Column(name="approved_time", nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedTime;
	 
	
	/**
	* general purpose key1. user defined<br/>
	* column :key_1
	**/
	@Column(name="key_1", nullable=true)
	private String key1;
	 
	/**
	* general purpose ke2. untuk indexing<br/>
	* column :key_2
	**/
	@Column(name="key_2", nullable=true)
	private String key2;
	 
	
	/**
	 * column : latest_remark
	 * catatan terakhir dari data, ini dalam kasus reject dsb, revise
	 **/
	@Column(name="latest_remark" , length=256)
	private String latestRemark ; 
	
	
	/**
	 * flag data multiple line atau tidak. Multiple line berasal dari data upload<br/>
	 * column : single_data_flag
	 */
	@Column(name="single_data_flag", length=1 )
	private String singleDataFlag = "Y"; 
	
	
	/**
	 * reff no dari data. column : reff_no
	 */
	@Column(name="reff_no" , length=32 )
	private String reffNo ; 
	
	
	
	/**
	 * nama file(kalau proses melalui upload)<br/>
	 * column : file_name
	 */
	@Column(name="file_name" , length=128)
	private String fileName ; 
	
	
	
	/**
	 * berapa line dalam data
	 */
	@Column(name="line_count" , precision=5 , scale=0)
	private Integer lineCount  =1 ;
	
	
	

	/**
	* primary key data<br/>
	* column :pk
	**/
	public void setId(BigInteger id){
	  this.id=id;
	}
	 
	/**
	* primary key data<br/>
	* column :pk
	**/
	public BigInteger getId(){
	    return this.id;
	}
	 
	/**
	* CREATE,UPDATE,DELETE<br/>
	* column :operation_code
	**/
	public void setOperationCode(String operationCode){
	  this.operationCode=operationCode;
	}
	 
	/**
	* CREATE,UPDATE,DELETE<br/>
	* column :operation_code
	**/
	public String getOperationCode(){
	    return this.operationCode;
	}
	 
	/**
	* full qualified class name dari data. ini untuk POJO dari object<br/>
	* column :target_fqcn
	**/
	public void setTargetObjectFQCN(String targetObjectFQCN){
	  this.targetObjectFQCN=targetObjectFQCN;
	}
	 
	/**
	* full qualified class name dari data. ini untuk POJO dari object<br/>
	* column :target_fqcn
	**/
	public String getTargetObjectFQCN(){
	    return this.targetObjectFQCN;
	}
	 
	/**
	* approval status<br/>
	* column :approval_status
	**/
	public void setApprovalStatus(String approvalStatus){
	  this.approvalStatus=approvalStatus;
	}
	 
	/**
	* approval status<br/>
	* column :approval_status
	**/
	public String getApprovalStatus(){
	    return this.approvalStatus;
	}
	 
	/**
	* user creator<br/>
	* column :creator_user_id
	**/
	public void setCreatorUserId(String creatorUserId){
	  this.creatorUserId=creatorUserId;
	}
	 
	/**
	* user creator<br/>
	* column :creator_user_id
	**/
	public String getCreatorUserId(){
	    return this.creatorUserId;
	}
	 
	/**
	* timestamp create<br/>
	* column :created_time
	**/
	public void setCreatedTime(Date createdTime){
	  this.createdTime=createdTime;
	}
	 
	/**
	* timestamp create<br/>
	* column :created_time
	**/
	public Date getCreatedTime(){
	    return this.createdTime;
	}
	 
	/**
	* user approver<br/>
	* column :approver_user_id
	**/
	public void setApproverUserId(String approverUserId){
	  this.approverUserId=approverUserId;
	}
	 
	/**
	* user approver<br/>
	* column :approver_user_id
	**/
	public String getApproverUserId(){
	    return this.approverUserId;
	}
	 
	/**
	* user yang approve<br/>
	* column :approved_time
	**/
	public void setApprovedTime(Date approvedTime){
	  this.approvedTime=approvedTime;
	}
	 
	/**
	* user yang approve<br/>
	* column :approved_time
	**/
	public Date getApprovedTime(){
	    return this.approvedTime;
	}
	 
	
	 
	/**
	* general purpose key1. user defined<br/>
	* column :key_1
	**/
	public void setKey1(String key1){
	  this.key1=key1;
	}
	 
	/**
	* general purpose key1. user defined<br/>
	* column :key_1
	**/
	public String getKey1(){
	    return this.key1;
	}
	 
	/**
	* general purpose ke2. untuk indexing<br/>
	* column :key_2
	**/
	public void setKey2(String key2){
	  this.key2=key2;
	}
	 
	/**
	* general purpose ke2. untuk indexing<br/>
	* column :key_2
	**/
	public String getKey2(){
	    return this.key2;
	}
	 
	/**
	 * column : latest_remark
	 * catatan terakhir dari data, ini dalam kasus reject dsb, revise
	 **/
	public void setLatestRemark(String latestRemark) {
		this.latestRemark = latestRemark;
	}
	/**
	 * column : latest_remark
	 * catatan terakhir dari data, ini dalam kasus reject dsb, revise
	 **/
	public String getLatestRemark() {
		return latestRemark;
	}

	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("approvalStatus",getApprovalStatus());
		jsonContainer.put("approvedTime",getApprovedTime());
		jsonContainer.put("approverUserId",getApproverUserId());
		jsonContainer.put("createdTime",getCreatedTime());
		jsonContainer.put("creatorUserId",getCreatorUserId());
		jsonContainer.put("id",getId());
		jsonContainer.put("key1",getKey1());
		jsonContainer.put("key2",getKey2());
		jsonContainer.put("latestRemark",getLatestRemark());
		jsonContainer.put("operationCode",getOperationCode());
		jsonContainer.put("targetObjectFQCN",getTargetObjectFQCN());
		jsonContainer.put("dualControlDefinition", getDualControlDefinition());
		jsonContainer.put("singleDataFlag", singleDataFlag);
		jsonContainer.put("reffNo", reffNo);
		jsonContainer.put("fileName", fileName); 
		jsonContainer.put("lineCount", lineCount); 
	}
	
	@Override
	public SimplifiedDualControlContainerTable instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		SimplifiedDualControlContainerTable retval = new SimplifiedDualControlContainerTable();
		retval.setApprovalStatus( (String)jsonContainer.get("approvalStatus" ,  String.class.getName()));
		retval.setApprovedTime( (Date)jsonContainer.get("approvedTime" ,  Date.class.getName()));
		retval.setApproverUserId( (String)jsonContainer.get("approverUserId" ,  String.class.getName()));
		retval.setCreatedTime( (Date)jsonContainer.get("createdTime" ,  Date.class.getName()));
		retval.setCreatorUserId( (String)jsonContainer.get("creatorUserId" ,  String.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setKey1( (String)jsonContainer.get("key1" ,  String.class.getName()));
		retval.setKey2( (String)jsonContainer.get("key2" ,  String.class.getName()));
		retval.setLatestRemark( (String)jsonContainer.get("latestRemark" ,  String.class.getName()));
		retval.setOperationCode( (String)jsonContainer.get("operationCode" ,  String.class.getName()));
		retval.setTargetObjectFQCN( (String)jsonContainer.get("targetObjectFQCN" ,  String.class.getName()));
		DualControlDefinition swap = (DualControlDefinition)jsonContainer.get("dualControlDefinition", DualControlDefinition.class.getName());
		retval.setDualControlDefinition(swap);
		retval.singleDataFlag = jsonContainer.getAsString("singleDataFlag"); 
		retval.reffNo  = jsonContainer.getAsString("reffNo") ;
		retval.fileName = jsonContainer.getAsString("fileName"); 
		retval.lineCount = jsonContainer.getAsInteger( "lineCount") ;
		
		
		return retval; 
	}
	/**
	 * reference as object ke dual control definition
	 **/
	public void setDualControlDefinition(
			DualControlDefinition dualControlDefintion) {
		this.dualControlDefinition = dualControlDefintion;
	}
	/**
	 * reference as object ke dual control definition
	 **/
	public DualControlDefinition getDualControlDefinition() {
		return dualControlDefinition;
	}
	
	
	/**
	 * flag data multiple line atau tidak. Multiple line berasal dari data upload<br/>
	 * column : single_data_flag
	 */
	public void setSingleDataFlag(String singleDataFlag) {
		this.singleDataFlag = singleDataFlag;
	}
	/**
	 * flag data multiple line atau tidak. Multiple line berasal dari data upload<br/>
	 * column : single_data_flag
	 */
	public String getSingleDataFlag() {
		return singleDataFlag;
	}
	

	/**
	 * reff no dari data. column : reff_no
	 */
	public void setReffNo(String reffNo) {
		this.reffNo = reffNo;
	}
	/**
	 * reff no dari data. column : reff_no
	 */
	public String getReffNo() {
		return reffNo;
	}
	/**
	 * nama file(kalau proses melalui upload)<br/>
	 * column : file_name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * nama file(kalau proses melalui upload)<br/>
	 * column : file_name
	 */
	public String getFileName() {
		return fileName;
	}
	
	
	/**
	 * berapa line dalam data
	 */
	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}
	/**
	 * berapa line dalam data
	 */
	public Integer getLineCount() {
		return lineCount;
	}
}
