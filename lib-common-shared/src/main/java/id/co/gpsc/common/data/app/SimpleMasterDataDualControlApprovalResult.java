package id.co.gpsc.common.data.app;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.math.BigInteger;



/**
 * data result untuk simple dual control approval
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class SimpleMasterDataDualControlApprovalResult implements IJSONFriendlyObject<SimpleMasterDataDualControlApprovalResult>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4193654605707280203L;

	private BigInteger id ;
	
	private String referenceNumber ; 
	protected String approvalStatus;
	
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainerData) {
		jsonContainerData.put("id", id);
		jsonContainerData.put("referenceNumber", referenceNumber);
		jsonContainerData.put("approvalStatus", approvalStatus);
		
		
	}
	@Override
	public SimpleMasterDataDualControlApprovalResult instantiateFromJSON(
			ParsedJSONContainer jsonContainer) {
		SimpleMasterDataDualControlApprovalResult retval = new SimpleMasterDataDualControlApprovalResult(); 
		retval.approvalStatus = jsonContainer.getAsString("approvalStatus"); 
		retval.id = jsonContainer.getAsBigInteger("id"); 
		retval.referenceNumber = jsonContainer.getAsString( "referenceNumber"); 
		return retval;
	}

}
