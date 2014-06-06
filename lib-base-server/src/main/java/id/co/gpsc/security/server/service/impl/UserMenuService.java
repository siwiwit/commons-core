package id.co.gpsc.security.server.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.security.menu.ApplicationMenuSecurity;
import id.co.gpsc.security.server.dao.impl.UserMenuDaoImpl;
import id.co.gpsc.security.server.service.IUserMenuService;

/**
 * Service menu
 * @author I Gede Mahendra
 * @since Nov 19, 2012, 11:58:06 AM
 * @version $Id
 */
@Service
public class UserMenuService implements IUserMenuService{

	@Autowired
	private UserMenuDaoImpl userDao;
	
	private Gson gson = new Gson();
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
	@Override
	public String createJsonApplicationMenu(Signon parameter) throws Exception {		
		String resultJson = "";
		try {
			List<ApplicationMenuSecurity> menu = getMenuApplication(parameter);
			if(menu != null){
				resultJson = gson.toJson(menu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return resultJson;
	}
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	@Override
	public List<ApplicationMenuSecurity> getMenuApplication(Signon parameter) throws Exception {
		List<ApplicationMenuSecurity> result = null;
		List<Function> resultFunction = getFunctionMenu(parameter);
						
		//Jika menu ada sesuai dg userId yg ditemukan
		if(resultFunction != null){
			result = convertToApplicationMenu(resultFunction);
		}		
		return result;
	}
	
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	@Override
	public List<ApplicationMenuSecurity> getMenuApplication(BigInteger userId) throws Exception {
		List<ApplicationMenuSecurity> result = null;
		List<Function> resultFunction = getAllowedMenusByUserId(userId);
		if(resultFunction != null){
			result = convertToApplicationMenu(resultFunction);
		}		
		return result;
		
		
	}
	
	/**
	 * Get function menu
	 * @param parameter - Object signon
	 * @return List of Function
	 * @throws Exception
	 */
	private List<Function> getFunctionMenu(Signon parameter) throws Exception{
		
		
		Signon resultSignon = userDao.getDataSignonByParam(parameter); //Get user_id
		if(resultSignon != null){
			return getAllowedMenusByUserId(resultSignon.getUserId());			
		}//end if
		return null;
	}

	
	
	
	
	
	
	/**
	 * mencari menu apa saja yang di miliki oleh user. ini di cari dengan ID dari user. <br/>
	 * apa yang di lakukan : 
	 * <ol>
	 * <li>cari group user dari table sec_user_assignment</li>
	 * <li>dari group user, locate function apa saja si user dapat nya</li>
	 * </ol>
	 * @param userId id dari user
	 **/
	public List<Function> getAllowedMenusByUserId (BigInteger userId)throws Exception  {
		List<Function> resultFunction = null;
		
		
		List<UserGroupAssignment> resultGroupAssignment = userDao.getGroupAssigmentByParam(userId); //Get group_id
		if(resultGroupAssignment != null){
			List<BigInteger> listGroupId = new ArrayList<BigInteger>();
			for (UserGroupAssignment groupAssignment : resultGroupAssignment) {
				listGroupId.add(groupAssignment.getGroupId());
			}//end for
			
			List<FunctionAssignment> resultFunctionAssignment = userDao.getFunctionAssignmentByGroupId(listGroupId); //get function_assignment
			if(resultFunctionAssignment != null){
				List<BigInteger> listFunctionId = new ArrayList<BigInteger>();
				for (FunctionAssignment functionAssignment : resultFunctionAssignment) {
					if (!listFunctionId.contains(functionAssignment.getFunctionId()))
						listFunctionId.add(functionAssignment.getFunctionId());
				}//end for
				
				resultFunction = userDao.getFunctionMenuByFunctionId(listFunctionId); //get function
			}//end if				
		}//end if
		return resultFunction ; 
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Konversi dari object function ke ApplicationMenuSecurity
	 * @param data
	 * @return
	 */
	private List<ApplicationMenuSecurity> convertToApplicationMenu(List<Function> data){
		List<ApplicationMenuSecurity> result = new ArrayList<ApplicationMenuSecurity>();
		try {			
			for (Function function : data) {
				ApplicationMenuSecurity appMenu = new ApplicationMenuSecurity();
				if(function.getPageDefinition() != null){
					appMenu.setActionCommand(function.getPageDefinition().getPageUrl());
				}							
				if(function.getPageId() == null){
					appMenu.setHaveChildren(true);
				}else{
					appMenu.setHaveChildren(false);
				}				
				appMenu.setLabel(function.getFunctionLabel());
				if ( function.getPageDefinition()!= null){
					appMenu.setMenuCode(function.getPageDefinition().getPageCode());
				}else{
					appMenu.setMenuCode(function.getFunctionCode());
				}
				
				
				appMenu.setMenuId(function.getId());
				appMenu.setParentId(function.getFunctionIdParent());
				appMenu.setLevel(function.getTreeLevelPosition());
				appMenu.setMenuTreeCode(function.getMenuTreeCode());
				result.add(appMenu);
				appMenu.setSequence(function.getSiblingOrder());
			}//end for			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result;
	}
}