package id.co.gpsc.common.security.dto;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Function DTO
 * @author I Gede Mahendra
 * @since Jan 31, 2013, 3:39:38 PM
 * @version $Id
 */
public class FunctionDTO implements Serializable,IsSerializable, IJSONFriendlyObject<FunctionDTO>{
	
	private static final long serialVersionUID = -7077085112270326039L;
	
	private BigInteger id;
	private BigInteger idParent;
	private String functionCodeParent;
	private String functionLabelParent;
	private String functionCode;
	private String functionLabel;
	private Boolean functionStatus;
	
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
	public String getFunctionCodeParent() {
		return functionCodeParent;
	}
	public void setFunctionCodeParent(String functionCodeParent) {
		this.functionCodeParent = functionCodeParent;
	}
	public String getFunctionLabelParent() {
		return functionLabelParent;
	}
	public void setFunctionLabelParent(String functionLabelParent) {
		this.functionLabelParent = functionLabelParent;
	}
	public String getFunctionCode() {
		return functionCode;
	}
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	public String getFunctionLabel() {
		return functionLabel;
	}
	public void setFunctionLabel(String functionLabel) {
		this.functionLabel = functionLabel;
	}
	public Boolean getFunctionStatus() {
		return functionStatus;
	}
	public void setFunctionStatus(Boolean functionStatus) {
		this.functionStatus = functionStatus;
	}
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("functionCode",getFunctionCode());
		jsonContainer.put("functionCodeParent",getFunctionCodeParent());
		jsonContainer.put("functionLabel",getFunctionLabel());
		jsonContainer.put("functionLabelParent",getFunctionLabelParent());
		jsonContainer.put("functionStatus",getFunctionStatus());
		jsonContainer.put("id",getId());
		jsonContainer.put("idParent",getIdParent());
	}
	
	@Override
	public FunctionDTO instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		FunctionDTO retval = new FunctionDTO();
		retval.setFunctionCode( (String)jsonContainer.get("functionCode" ,  String.class.getName()));
		retval.setFunctionCodeParent( (String)jsonContainer.get("functionCodeParent" ,  String.class.getName()));
		retval.setFunctionLabel( (String)jsonContainer.get("functionLabel" ,  String.class.getName()));
		retval.setFunctionLabelParent( (String)jsonContainer.get("functionLabelParent" ,  String.class.getName()));
		retval.setFunctionStatus( (Boolean)jsonContainer.get("functionStatus" ,  Boolean.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setIdParent( (BigInteger)jsonContainer.get("idParent" ,  BigInteger.class.getName()));
		return retval; 
	}
}