package id.co.gpsc.security.server.service.impl;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.MD5Utils;
import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.domain.ApplicationUser;
import id.co.gpsc.common.security.domain.PasswordPolicy;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.common.security.domain.UserPassword;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserGroupDTO;
import id.co.gpsc.common.security.exception.PasswordPolicyException;
import id.co.gpsc.common.server.util.ExtendedBeanUtils;
import id.co.gpsc.security.server.dao.IUserGroupAssignmentDao;
import id.co.gpsc.security.server.dao.impl.PasswordPolicyDaoImpl;
import id.co.gpsc.security.server.dao.impl.UserDaoImpl;
import id.co.gpsc.security.server.service.IUserService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 2:00:04 PM
 * @version $Id
 */
@Service
public class UserServiceImpl implements IUserService{

	private final String USER_CODE = "userCode";
	private final String REAL_NAME = "realName";
	private final String EMAIL = "email";
	private final String ID = "id";	
	
	@Autowired
	private UserDaoImpl userDao;
	
	@Autowired
	private PasswordPolicyDaoImpl passwordPolicyDao;
	
	@Autowired
	private IUserGroupAssignmentDao groupAssignDao;
	
	@Transactional(readOnly=true)
	@Override
	public PagedResultHolder<UserDTO> getUserByParameter(SimpleQueryFilter[] filter,int pagePosition, int pageSize) throws Exception {
		
		PagedResultHolder<UserDTO> retval = new PagedResultHolder<UserDTO>();
		List<User> actualData = new ArrayList<User>();
		User parameter = null;
		
		if(filter != null){
			parameter = new User();			
			for (int i = 0; i < filter.length; i++) {
				setParameterByFilter(filter[i], parameter);
			}
		}		
		
		actualData = userDao.getUserByParameter(parameter, pagePosition, pageSize);
		Integer count = userDao.countUserByParameter(parameter);		
		List<UserDTO> holderData = new ArrayList<UserDTO>();
		if(count == null || count == 0){
			return null;
		}
		
		for (User user : actualData) {
			UserDTO dto = new UserDTO();
			dto.setIdUser(user.getId());
			dto.setFullName(user.getRealName());
			dto.setUsername(user.getUserCode());
			holderData.add(dto);
		}
		
		retval.setHoldedData(holderData);
		retval.setPage(pagePosition);		
		retval.setPageSize(pageSize);
		retval.setTotalData(count);	
		retval.adjustPagination();	
		return retval;
	}
	
	/**
	 * Dipergunakan untuk mendapatkan list user pada modul application user.
	 * Jadi yg tampil adalah user berpasangan dg group user
	 */
	@Transactional(readOnly=true)
	@Override
	public PagedResultHolder<UserDTO> getUserAtWorklistByParam(BigInteger applicationId, SimpleQueryFilter[] filter, int pagePosition, int pageSize) throws Exception {		
		PagedResultHolder<UserDTO> retval = new PagedResultHolder<UserDTO>();
		List<User> resultUser = new ArrayList<User>();
		User parameter = null;		
		List<UserGroupAssignment> resultGroupAssignment = null;
		List<UserGroupDTO> listUserGroupDTO = null;
		
		if(filter != null){					
			for (int i = 0; i < filter.length; i++) {					
				if(!filter[i].getFilter().isEmpty()){
					parameter = new User();
					setParameterByFilter(filter[i], parameter);
				}				
			}
		}				
		
		/*SELECT tabel sec_application_user*/
		List<ApplicationUser> resulApplicationUser = userDao.getApplicationUser(applicationId,parameter,pagePosition,pageSize);				
		if(!resulApplicationUser.isEmpty()){
			List<BigInteger> listUserId = new ArrayList<BigInteger>();
			for (ApplicationUser user : resulApplicationUser) {
				listUserId.add(user.getApplicationUser().getUserId());
			}
			
			/*SELECT tabel sec_user*/
			resultUser = userDao.getUserByUserId(listUserId);
			
			/*SELECT tabel sec_group_assignment*/
			resultGroupAssignment = userDao.getGroupAssignmentByUserId(listUserId);
		}		
			
		if(parameter == null){
			parameter = new User();
		}
		parameter.setDefaultApplicationId(applicationId.intValue());
		Integer count = userDao.countUserByParameter(parameter);		
		List<UserDTO> holderData = new ArrayList<UserDTO>();
		if(count == null || count == 0){
			return null;
		}
				
		/*Menyusun User dan user group*/
		for (User user : resultUser) {
			UserDTO dto = new UserDTO();
			listUserGroupDTO = new ArrayList<UserGroupDTO>();
			
			dto.setIdUser(user.getId());
			dto.setFullName(user.getRealName());
			dto.setUsername(user.getUserCode());
			dto.setEmail(user.getEmail());
			dto.setUserGroups(listUserGroupDTO);
			
			for (UserGroupAssignment groupAssignment : resultGroupAssignment) {				
				if(groupAssignment.getUserId().compareTo(user.getId()) == 0){
					UserGroupDTO userGroupDTO = new UserGroupDTO();
					userGroupDTO.setGroupCode(groupAssignment.getUserGroup().getGroupCode());
					userGroupDTO.setGroupName(groupAssignment.getUserGroup().getGroupName());
					listUserGroupDTO.add(userGroupDTO);					
				}
			}			
			holderData.add(dto);
		}
		
		retval.setHoldedData(holderData);
		retval.setPage(pagePosition);		
		retval.setPageSize(pageSize);
		retval.setTotalData(count);	
		retval.adjustPagination();	
		return retval;
	}
	
	/**
	 * Set filter ke dalam bentuk object User
	 * @param filter
	 * @param objParam
	 */
	private void setParameterByFilter(SimpleQueryFilter filter, User objParam){		
		if(filter.getField().equals(USER_CODE)){
			objParam.setUserCode(filter.getFilter());
		}else if(filter.getField().equals(REAL_NAME)){
			objParam.setRealName(filter.getFilter());
		}else if(filter.getField().equals(ID)){
			objParam.setId(new BigInteger(filter.getFilter()));
		}else if(filter.getField().equals(EMAIL)){
			objParam.setEmail(filter.getFilter());
		}		
	}	
	
	@Override
	public PagedResultHolder<User> getUserByFilter(
			SimpleQueryFilter[] filters, int pagePosition, int pageSize)
			throws Exception {
		Integer count = userDao.countUserByFilters(filters);
		if (count == null) 
			return null;
		
		List<User> actualData = userDao.getUserByFilters(filters, pagePosition, pageSize);
		copyApplicationInUser(actualData);
		PagedResultHolder<User> retval = new PagedResultHolder<User>();
		retval.setHoldedData(actualData);
		retval.setPage(pagePosition);
		retval.setPageSize(pageSize);
		retval.setTotalData(count);
		retval.adjustPagination();
		return retval;
	}
	
	@SuppressWarnings("static-access")
	private void copyApplicationInUser(List<User> users) {
		try {
			Application original = null;
			Application duplikat = null;
			for (User user : users) {
				original = user.getDefaultApplication();
				duplikat = new Application();
				if (original != null)
					ExtendedBeanUtils.getInstance().copyProperties(original, duplikat);
				user.setDefaultApplication(duplikat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void insert(User data)
			throws Exception, PasswordPolicyException {
			
		//password checking
		try {
			data.setUserCode(data.getUserCode().toUpperCase());
			passwordChecking(data.getChipperText());
		} catch (Exception e) {
			throw e;
		}
		
		//set chipper text dengan md5 dari password
		data.setChipperText(MD5Utils.getInstance().hashMD5(data.getChipperText()));
		
		userDao.insert(data);
		insertUserPassword(data);
	}
	
	/**
	 * checking password dengan password policy
	 * @param password password yang dicek
	 * @throws Exception
	 * @throws PasswordPolicyException jika passsword tidak memenuhi policy maka akan throws exception ini 
	 */
	private void passwordChecking(String password) throws Exception, PasswordPolicyException { 
		List<PasswordPolicy> passwordPolicys = passwordPolicyDao.getPasswordPolicy();
		if (passwordPolicys == null || passwordPolicys.isEmpty()) 
			return ;
		//inisialisasi variabel yang diperlukan
		int minimumLength = 0;
		int minimumAlphabet = 0;
		int minimumNumeric = 0;
		Pattern pattern = null;
		Matcher matcher = null;
		List<String> detailMessages = new ArrayList<String>();
		List<String> i18nDetailMessages = new ArrayList<String>();
		List<String> passValues = new ArrayList<String>();
		
		//looping cheking regex password policy matching
		for (PasswordPolicy passwordPolicy : passwordPolicys) {
			if (passwordPolicy.getMinimumLength() > minimumLength)
				minimumLength = passwordPolicy.getMinimumLength();
			if (passwordPolicy.getMinimumAlphabet() > minimumAlphabet)
				minimumAlphabet = passwordPolicy.getMinimumAlphabet();
			if (passwordPolicy.getMinimumNumeric() > minimumNumeric)
				minimumNumeric = passwordPolicy.getMinimumNumeric();
			
			pattern = Pattern.compile(passwordPolicy.getRegularExpression());
			matcher = pattern.matcher(password);
			if (!matcher.matches()) {
				detailMessages.add(passwordPolicy.getRegexDesc());
				i18nDetailMessages.add(passwordPolicy.getRegexDesc());
				passValues.add("");
			}
		}
		
		//minimum length password checking
		if (password.length() < minimumLength) {
			detailMessages.add("Password doesnt match minimum length password policy ! Minimum password length : ");
			i18nDetailMessages.add("security.user.alert.errorpassworddoesntmatchminimumlength");
			passValues.add("" + minimumLength);
		}
		
		//minimum alphabet & minimum number checking
		if (!minimumAlphabetOrNumberChecking(minimumAlphabet, password, passwordPolicyDao.ALPHABET_REGEX)) {
			detailMessages.add("Password doesnt match minimum alphabet password policy ! Minimum alpabhet in password : ");
			i18nDetailMessages.add("security.user.alert.errorpassworddoesntmatchminimumlengthalphabet");
			passValues.add("" + minimumAlphabet);
		}
		if (!minimumAlphabetOrNumberChecking(minimumNumeric, password, passwordPolicyDao.NUMERIC_REGEX)) {
			detailMessages.add("Password doesnt match minimum numeric password policy ! Minimum numeric in password : ");
			i18nDetailMessages.add("security.user.alert.errorpassworddoesntmatchminimumlengthnumeric");
			passValues.add("" + minimumNumeric);
		}
		
		if (!detailMessages.isEmpty())
			throw new PasswordPolicyException("security.user.alert.errorpassworddoesntmatchcombination","Password doesnt match combination password policy !", i18nDetailMessages, detailMessages, passValues);
	}
	
	/**
	 * checking apakah password sesuai dengan minimum alphabet atau minimum number
	 * @param minimumAppearance minimum appearance dalam sebuah password
	 * @param password password yang di cek
	 * @param regex regular expression untuk mengecek passwordnya
	 * @return true jika valid, false jika tidak
	 */
	private boolean minimumAlphabetOrNumberChecking(int minimumAppearance, String password, String regex) {
		int counter = 0;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password);
		while (matcher.find() && counter != minimumAppearance) {
			counter++;
		}
		if (counter < minimumAppearance)
			return false;
		return true;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private void insertUserPassword(User data) throws Exception {
		if ("Y".equals(data.getNtlmUser()))
			return ;
		//create object userPassword
		UserPassword userPassword = new UserPassword();
		userPassword.setUserId(data.getId());
		userPassword.setCipherText(data.getChipperText());
		userPassword.setCreatedBy(data.getCreatedBy());
		userPassword.setCreatedOn(data.getCreatedOn());
		userDao.insert(userPassword);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void update(User data) throws Exception {
		data.setUserCode(data.getUserCode().toUpperCase());
		userDao.update(data);
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void remove(BigInteger id) throws Exception {
		removeUserPassword(id);
		groupAssignDao.deleteGroupAssignmentByUserId(id);
		User dataToRemove = userDao.getUserById(id);
		userDao.delete(dataToRemove);
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private void removeUserPassword(BigInteger userId) throws Exception {
		List<UserPassword> listData = userDao.getUserPasswordByUserId(userId);
		for (UserPassword dataToRemove : listData)
		{
			userDao.delete(dataToRemove);
		}
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateUserPassword(User data) throws Exception, PasswordPolicyException {
		//password checking
		try {
			passwordChecking(data.getChipperText());
		} catch (Exception e) {
			throw e;
		}
		
		//set chipper text dengan md5 dari password
		data.setChipperText(MD5Utils.getInstance().hashMD5(data.getChipperText()));
		
		userDao.update(data);
		insertUserPassword(data);
	}

	@Override
	public Boolean isUserCodeExist(String userCode, Integer applicationId) throws Exception {
		User result = userDao.isUserNameExist(userCode, applicationId);
		if (result == null)
			return false;
		return true;
	}
}