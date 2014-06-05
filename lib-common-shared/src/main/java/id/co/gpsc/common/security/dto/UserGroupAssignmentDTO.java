package id.co.gpsc.common.security.dto;

import id.co.gpsc.common.util.json.IJSONFriendlyObject;
import id.co.gpsc.common.util.json.ParsedJSONContainer;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * DTO dari user group assignment
 * @author I Gede Mahendra
 * @since Dec 7, 2012, 12:08:34 PM
 * @version $Id
 */
public class UserGroupAssignmentDTO implements Serializable,IsSerializable, IJSONFriendlyObject<UserGroupAssignmentDTO>{

	private static final long serialVersionUID = 1150676999888785821L;
	
	private BigInteger id;
	private BigInteger userId;
	private String username;
	private String fullname;
	private BigInteger groupId;
	private String groupCode;
	private String groupName;
	
	public UserGroupAssignmentDTO() {
		super();
	}
	
	public UserGroupAssignmentDTO(BigInteger id,String username,String fullname){
		this.id = id;
		this.username = username;
		this.fullname = fullname;
	}
	
	public UserGroupAssignmentDTO(String groupCode, String groupName){
		this.groupCode = groupCode;
		this.groupName = groupName;
	}	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}	
	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}	
	public BigInteger getGroupId() {
		return groupId;
	}
	public void setGroupId(BigInteger groupId) {
		this.groupId = groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}	
	
	@Override
	public void translateToJSON(ParsedJSONContainer jsonContainer) {
		jsonContainer.put("fullname",getFullname());
		jsonContainer.put("groupCode",getGroupCode());
		jsonContainer.put("groupId",getGroupId());
		jsonContainer.put("groupName",getGroupName());
		jsonContainer.put("id",getId());
		jsonContainer.put("userId",getUserId());
		jsonContainer.put("username",getUsername());
	}
	
	@Override
	public UserGroupAssignmentDTO instantiateFromJSON(ParsedJSONContainer jsonContainer) {
		UserGroupAssignmentDTO retval = new UserGroupAssignmentDTO();
		retval.setFullname( (String)jsonContainer.get("fullname" ,  String.class.getName()));
		retval.setGroupCode( (String)jsonContainer.get("groupCode" ,  String.class.getName()));
		retval.setGroupId( (BigInteger)jsonContainer.get("groupId" ,  BigInteger.class.getName()));
		retval.setGroupName( (String)jsonContainer.get("groupName" ,  String.class.getName()));
		retval.setId( (BigInteger)jsonContainer.get("id" ,  BigInteger.class.getName()));
		retval.setUserId( (BigInteger)jsonContainer.get("userId" ,  BigInteger.class.getName()));
		retval.setUsername( (String)jsonContainer.get("username" ,  String.class.getName()));
		return retval; 
	}
}