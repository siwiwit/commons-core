package id.co.gpsc.common.data.app;

import id.co.gpsc.common.util.ObjectGeneratorManager;
import id.co.gpsc.common.util.json.ParsedJSONContainer;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;


import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Transient;




/**
 * base class untuk simple data yang dual control
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
@MappedSuperclass
public abstract class SimpleDualControlData<POJO extends SimpleDualControlData<?>>   implements DualControlEnabledData<POJO, BigInteger>{
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4656396900584023149L;


	/**
	 * kode approval status. ini untuk proses persistence
	 **/
	@Column(name="approval_status" , length=32)
	protected String approvalStatusCode ; 
	
	
	
	
	/**
	 * ID dari flow dual control
	 * column : curr_dual_ctr_id
	 **/
	@Column(name="curr_dual_ctr_id")
	protected BigInteger currentCommonDualControlId ; 
	
	
	/**
	 * kode status data. A vs D. A= active, D = deactive
	 * column : data_status
	 **/
	@Column(name="data_status")
	protected String dataStatusCode ; 
	

	@Transient
	protected DualControlApprovalStatusCode approvalStatusEnum  ; 
	
	
	
	@PostLoad
	protected void postLoadTask (){
		if ( approvalStatusCode!=null){
			for (DualControlApprovalStatusCode scn : DualControlApprovalStatusCode.values() ){
				if ( scn.toString().equals(approvalStatusCode) ){
					approvalStatusEnum = scn; 
					break ; 
				}
			}
		}
	}
	
	@Override
	public void setApprovalStatus(DualControlApprovalStatusCode approvalStatusRaw) {
		this.approvalStatusEnum = approvalStatusRaw ; 
		if ( approvalStatusRaw!= null)
			this.approvalStatusCode = approvalStatusRaw.toString();
		else
			this.approvalStatusCode  = null ; 
	}
	
	
	@Override
	public DualControlApprovalStatusCode getApprovalStatus() {
		return approvalStatusEnum;
	}
	
	/**
	 * ID dari flow dual control
	 * column : curr_dual_ctr_id
	 **/
	public BigInteger getCurrentCommonDualControlId() {
		return currentCommonDualControlId;
	}
	/**
	 * ID dari flow dual control
	 * column : curr_dual_ctr_id
	 **/
	public void setCurrentCommonDualControlId(BigInteger currentCommonDualControlId) {
		this.currentCommonDualControlId = currentCommonDualControlId;
	}


	/**
	 * kode status data. A vs D. A= active, D = deactive
	 * column : data_status
	 **/
	public void setDataStatusCode(String dataStatusCode) {
		this.dataStatusCode = dataStatusCode;
	}
	/**
	 * kode status data. A vs D. A= active, D = deactive
	 * column : data_status
	 **/
	public String getDataStatusCode() {
		return dataStatusCode;
	}
	
	
	
	public void setApprovalStatusCodeRaw(String approvalStatusCode) {
		this.approvalStatusCode = approvalStatusCode;
		this.approvalStatusEnum = DualControlApprovalStatusCode.generateFromRawString(approvalStatusCode); 
	}
	
	public String getApprovalStatusCodeRaw() {
		return approvalStatusCode;
	}
	
	/**
	 * generate big int. null safe
	 **/
	protected BigInteger makeBigInteger (Number number) {
		if ( number ==null)
			return null ; 
		return new BigInteger(number.intValue() +""); 
	}
	
	
	/**
	 * konstruksi big decimal. null safe method
	 **/
	protected BigDecimal makeBigDecimal (Number number) {
		if ( number ==null)
			return null ; 
		return new BigDecimal(number.toString()); 
	}
	
	
	
	
	@Transient
	private Double nullDouble = null ; 
	
	
	
	
	
	
	
	/**
	 * helper untuk menaruh object dalam json. null save
	 **/
	protected void putNumberOnJSONContainer (ParsedJSONContainer container , String key , Number value) {
		if ( value==null){
			container.put(key, nullDouble);
		}else{
			container.put(key, value.doubleValue());
		}
		
	}
	 
	@Override
	public String getApprovalStatusJPAAnnotatedField() {
		return "approvalStatusCode";
	}
	
	@Override
	public String getActiveFlagJPAAnnotatedField() {
		return "dataStatusCode";
	}
	
	
	@Override
	public final  String generateJSONString() {
		ParsedJSONContainer c = SharedServerClientLogicManager.getInstance().getJSONParser().createBlankObject(); 
		translateToJSON(c);
		return c.getJSONString();
	}
	
	 
	
	
	@Override
	public final POJO instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		POJO targetObject = ObjectGeneratorManager.getInstance().instantiateSampleObject(this.getClass().getName());
		targetObject.approvalStatusCode = jsonContainer.getAsString("approvalStatusCode");
		targetObject.approvalStatusEnum = DualControlApprovalStatusCode.generateFromRawString(targetObject.approvalStatusCode);
		targetObject.dataStatusCode = jsonContainer.getAsString("dataStatusCode");
		targetObject.currentCommonDualControlId = (BigInteger)jsonContainer.get("currentCommonDualControlId" , BigInteger.class.getName());
		extractDataFromJSON(targetObject, jsonContainer);
		
		return targetObject;
	}
	
	
	
	
	/**
	 * salin data dari json container
	 **/
	protected abstract void extractDataFromJSON (POJO  targetObject , ParsedJSONContainer jsonContainer); 
	 
	
	
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainerData) {
		jsonContainerData.put("approvalStatusCode", approvalStatusCode);
		jsonContainerData.put("dataStatusCode", dataStatusCode); 
		jsonContainerData.put("currentCommonDualControlId", currentCommonDualControlId);
	}
	
	
}
