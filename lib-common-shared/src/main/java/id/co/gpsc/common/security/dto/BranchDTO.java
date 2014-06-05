package id.co.gpsc.common.security.dto;

import id.co.gpsc.common.data.SingleKeyEntityData;
import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Branch DTO
 * @author I Gede Mahendra
 * @since Jan 30, 2013, 5:25:22 PM
 * @version $Id
 */
public class BranchDTO implements SingleKeyEntityData<BigInteger>,IsSerializable,Serializable, IJSONFriendlyObject<BranchDTO>{

	private static final long serialVersionUID = -6677691800656625680L;
	
	private BigInteger id;
	private BigInteger idParent;
	private String branchParentCode;
	private String branchCode;
	private String branchName;
	private String branchAddress;
	private String branchDescription;
	private String branchStatus;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getIdParent() {
		return idParent;
	}
	public void setIdParent(BigInteger idParent) {
		this.idParent = idParent;
	}	
	public String getBranchParentCode() {
		return branchParentCode;
	}
	public void setBranchParentCode(String branchParentCode) {
		this.branchParentCode = branchParentCode;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	public String getBranchDescription() {
		return branchDescription;
	}
	public void setBranchDescription(String branchDescription) {
		this.branchDescription = branchDescription;
	}
	public String getBranchStatus() {
		return branchStatus;
	}
	public void setBranchStatus(String branchStatus) {
		this.branchStatus = branchStatus;
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("branchAddress",getBranchAddress());
		jsonContainer.put("branchCode",getBranchCode());
		jsonContainer.put("branchDescription",getBranchDescription());
		jsonContainer.put("branchName",getBranchName());
		jsonContainer.put("branchParentCode",getBranchParentCode());
		jsonContainer.put("branchStatus",getBranchStatus());
		jsonContainer.put("id",getId());
		jsonContainer.put("idParent",getIdParent());
	}
	
	@Override
	public BranchDTO instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		BranchDTO retval = new BranchDTO();
		retval.setBranchAddress( (String)jsonContainer.get("branchAddress" ,  String.class.getName()));
		retval.setBranchCode( (String)jsonContainer.get("branchCode" ,  String.class.getName()));
		retval.setBranchDescription( (String)jsonContainer.get("branchDescription" ,  String.class.getName()));
		retval.setBranchName( (String)jsonContainer.get("branchName" ,  String.class.getName()));
		retval.setBranchParentCode( (String)jsonContainer.get("branchParentCode" ,  String.class.getName()));
		retval.setBranchStatus( (String)jsonContainer.get("branchStatus" ,  String.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setIdParent( (BigInteger)jsonContainer.get("idParent" ,  BigInteger.class.getName()));
		return retval; 
	}
}