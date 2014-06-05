/**
 * 
 */
package id.co.gpsc.common.security;

import java.util.logging.Level;
import java.util.logging.Logger;

import id.co.gpsc.common.data.serializer.AbstractObjectGenerator;

/**
 * object generator untuk security
 * @author Dode
 *
 */
public class SecuritySharedObjectGenerator extends AbstractObjectGenerator {
	
	
	
	private static final Logger logger = Logger.getLogger(SecuritySharedObjectGenerator.class.getName()); 

	private static final Class<?> [] CLS ={
		id.co.gpsc.common.security.dto.ApplicationDTO.class,
		id.co.gpsc.common.security.dto.ApplicationMenuDTO.class,
		id.co.gpsc.common.security.dto.BranchDTO.class,
		id.co.gpsc.common.security.dto.FunctionDTO.class,
		id.co.gpsc.common.security.dto.PageDefinitionDTO.class,
		id.co.gpsc.common.security.dto.UserDetailDTO.class,
		id.co.gpsc.common.security.dto.UserDTO.class,
		id.co.gpsc.common.security.dto.UserGroupAssignmentDTO.class,
		id.co.gpsc.common.security.dto.UserGroupDTO.class,
		id.co.gpsc.common.security.exception.MenuHaveChildException.class,
		id.co.gpsc.common.security.exception.PasswordPolicyException.class,
		id.co.gpsc.common.security.menu.ApplicationMenuSecurity.class,
		id.co.gpsc.common.security.menu.UserDomain.class,
		id.co.gpsc.common.security.menu.UserDomainPaging.class,
		id.co.gpsc.common.security.LoginParameter.class,
		id.co.gpsc.common.security.LoginResultData.class, 
		id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable.class, 
		id.co.gpsc.common.security.ApplicationSessionRegistry.class
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T instantiateSampleObject(String objectFQCN) {
		if (id.co.gpsc.common.security.dto.ApplicationDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.ApplicationDTO();
		if (id.co.gpsc.common.security.dto.ApplicationMenuDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.ApplicationMenuDTO();
		if (id.co.gpsc.common.security.dto.BranchDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.BranchDTO();
		if (id.co.gpsc.common.security.dto.FunctionDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.FunctionDTO();
		if (id.co.gpsc.common.security.dto.PageDefinitionDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.PageDefinitionDTO();
		if (id.co.gpsc.common.security.dto.UserDetailDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.UserDetailDTO();
		if (id.co.gpsc.common.security.dto.UserDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.UserDTO();
		if (id.co.gpsc.common.security.dto.UserGroupAssignmentDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.UserGroupAssignmentDTO();
		if (id.co.gpsc.common.security.dto.UserGroupDTO.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.dto.UserGroupDTO();
		if (id.co.gpsc.common.security.exception.MenuHaveChildException.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.exception.MenuHaveChildException();
		if (id.co.gpsc.common.security.exception.PasswordPolicyException.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.exception.PasswordPolicyException();
		if (id.co.gpsc.common.security.menu.ApplicationMenuSecurity.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.menu.ApplicationMenuSecurity();
		if (id.co.gpsc.common.security.menu.UserDomain.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.menu.UserDomain();
		if (id.co.gpsc.common.security.menu.UserDomainPaging.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.menu.UserDomainPaging();
		if (id.co.gpsc.common.security.LoginParameter.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.LoginParameter();
		if (id.co.gpsc.common.security.LoginResultData.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.LoginResultData();
		if (id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable.class.getName().equals(objectFQCN))return (T)new id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable();
		if ( id.co.gpsc.common.security.ApplicationSessionRegistry.class.getName().equals(objectFQCN)) return (T) new id.co.gpsc.common.security.ApplicationSessionRegistry(); 
		logger.log(Level.SEVERE , "FQCN :" + objectFQCN +", tidak di temukan dalam class :" + this.getClass().getName()  + ",menyerah sekarang, di lempar ke next chain");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] instantiateArray(String objectFQCN, int size) {
		if (id.co.gpsc.common.security.dto.ApplicationDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.ApplicationDTO[size];
		if (id.co.gpsc.common.security.dto.ApplicationMenuDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.ApplicationMenuDTO[size];
		if (id.co.gpsc.common.security.dto.BranchDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.BranchDTO[size];
		if (id.co.gpsc.common.security.dto.FunctionDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.FunctionDTO[size];
		if (id.co.gpsc.common.security.dto.PageDefinitionDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.PageDefinitionDTO[size];
		if (id.co.gpsc.common.security.dto.UserDetailDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.UserDetailDTO[size];
		if (id.co.gpsc.common.security.dto.UserDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.UserDTO[size];
		if (id.co.gpsc.common.security.dto.UserGroupAssignmentDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.UserGroupAssignmentDTO[size];
		if (id.co.gpsc.common.security.dto.UserGroupDTO.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.dto.UserGroupDTO[size];
		if (id.co.gpsc.common.security.exception.MenuHaveChildException.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.exception.MenuHaveChildException[size];
		if (id.co.gpsc.common.security.exception.PasswordPolicyException.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.exception.PasswordPolicyException[size];
		if (id.co.gpsc.common.security.menu.ApplicationMenuSecurity.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.menu.ApplicationMenuSecurity[size];
		if (id.co.gpsc.common.security.menu.UserDomain.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.menu.UserDomain[size];
		if (id.co.gpsc.common.security.menu.UserDomainPaging.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.menu.UserDomainPaging[size];
		if (id.co.gpsc.common.security.LoginParameter.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.LoginParameter[size];
		if (id.co.gpsc.common.security.LoginResultData.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.LoginResultData[size];
		if ( id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable.class.getName().equals(objectFQCN)) return (T[])new id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable[size];
		if( id.co.gpsc.common.security.ApplicationSessionRegistry.class.getName().equals(objectFQCN)) return (T[]) new id.co.gpsc.common.security.ApplicationSessionRegistry[size];
		return null;
	}

	@Override
	public Class<?>[] generatedClass() {
		return CLS;
	}

}
