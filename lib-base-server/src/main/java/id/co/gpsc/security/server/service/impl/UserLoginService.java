/**
 * File Name : UserLoginService.java
 * Package   : id.co.gpsc.arium.security.server.service.impl
 * Project   : security-server
 */
package id.co.gpsc.security.server.service.impl;

import id.co.gpsc.common.security.LoginParameter;
import id.co.gpsc.common.security.LoginResultData;
import id.co.gpsc.common.security.MD5Utils;
import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.domain.SignonHistory;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.domain.UserPassword;
import id.co.gpsc.security.server.CurrentUserCurrentluUsedException;
import id.co.gpsc.security.server.dao.IUserGroupDao;
import id.co.gpsc.security.server.dao.impl.UserLoginDaoImpl;
import id.co.gpsc.security.server.service.IUserLoginService;

import java.math.BigInteger;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Service user login
 * @author I Gede Mahendra
 * @since Nov 19, 2012, 11:57:50 AM
 * @version $Id
 */
@Service
public class UserLoginService implements IUserLoginService {
	
	@Autowired
	private UserLoginDaoImpl userLoginDao;
	
	public static final Integer CODE_SUCCESS = 0;		
	private final Integer CODE_UNKNOWN_USER = 1;
	private final Integer CODE_INVALID_PASSWORD = 2;
	
	@Autowired
	private IUserGroupDao   userGroupDao; 
	
	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	@Override
	public LoginResultData loginApplication(LoginParameter parameter) throws Exception {
		LoginResultData resultData = new LoginResultData();
		
		
		//1. Select user_id From sec_user berdasarkan username
		User user = userLoginDao.getUserByUsername(parameter.getUsername());
		if(user != null){
			String passwordAtDb = user.getChipperText();
			String passwordInput = "";
			if(passwordAtDb.length() != 32){
				//FIXME: ganti menjadi mempergunakan password encoder
				passwordInput = MD5Utils.getInstance().hashMD5(parameter.getPassword());
			}else{
				passwordInput = passwordAtDb;
			}
			 
			if (passwordAtDb.equals(passwordInput)){
				parameter.setUserId(user.getId());	
				
				//1.1 Pengecekan password yg di tabel sec_user dg tabel sec_password_hs
				UserPassword userPassword = userLoginDao.getPasswordAtHistory(parameter.getUserId());
				if(userPassword == null){
					resultData.setErrorCode(CODE_INVALID_PASSWORD);
					return resultData;
				}else{
					if(userPassword.getCipherText().equals(passwordInput)){
						//2. Cek ke tabel sec_signon
						Signon signon = createSignOnDataAndKickPrevUser(parameter.getApplicationId() , user.getId()); 
						resultData.setUuid(signon.getUuid());
						resultData.setSigonData(signon);
						resultData.setErrorCode(CODE_SUCCESS);
						return resultData;
					}else{
						resultData.setErrorCode(CODE_INVALID_PASSWORD);
						return resultData;
					}
				}
			}else{ //jika password tidak sama
				resultData.setErrorCode(CODE_INVALID_PASSWORD);
				return resultData;
			}
		}else{ //jika null, user tidak ditemukan
			resultData.setErrorCode(CODE_UNKNOWN_USER);
			return resultData;
		}			
	}
	
	
	/**
	 * worker untuk notifikasi server(misal cams), kalau user : YYY sudah berhasil login
	 **/
	public void notifyRequesterHostOnSuccessLogin ( Signon signonData ) {
		RestTemplate restTemplate = new RestTemplate();
		
		//FIXME : load app by id
		Application currentApp = signonData.getApplication();		
		String clientAppNotifyUrl = currentApp.generateNotificationAuthenticationUrl();
		String url =clientAppNotifyUrl + (clientAppNotifyUrl.contains("?")?"&":"?")  +
				"userHost=" + signonData.getTerminal()+
				"&userName=" + signonData.getUser().getUserCode()+ 
				"&uuid=" + signonData.getUuid()+
				"&fullName=" + signonData.getUser().getRealName() + 
				"&email=" + signonData.getUser().getEmail();
		
		// cari roles 
		List<String> roles =  userGroupDao.getUserGroups(signonData.getUserId(), signonData.getApplicationId());
		if ( roles!=null&& !roles.isEmpty()){
			for ( String role : roles){
				url+="&roles=" + role;
			}
		}
		
		
		
		System.out.println("menu url :" + url);
		String resultFromRest = restTemplate.getForObject(url, String.class);
		System.out.println(resultFromRest);
		
	}
	
	
	
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW)
	@Override
	public LoginResultData loginApplicationViaNTLM(LoginParameter parameter) throws Exception {
		LoginResultData resultData = new LoginResultData();
		//1. Select user_id From sec_user berdasarkan username
		User user = userLoginDao.getUserByUsername(parameter.getUsername());
		if(user != null){
			//2. Cek ke tabel sec_signon
			parameter.setUserId(user.getId());
			Signon signon = createSignOnDataAndKickPrevUser(parameter.getApplicationId() , parameter.getUserId()); 
			resultData.setUuid(signon.getUuid());
			resultData.setSigonData(signon);
			resultData.setErrorCode(CODE_SUCCESS);
			return resultData;						
		}else{ //jika null, user tidak ditemukan
			resultData.setErrorCode(CODE_UNKNOWN_USER);
			return resultData;
		}
	}
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRES_NEW)
	@Override
	public Application getApplication(BigInteger applicationId)throws Exception {		
		return userLoginDao.getApplicationData(applicationId);
	}
		
	/**
	 * insert ke dalam table sign. 
	 * @param user
	 * @throws Exception 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public Signon createSignOnDataAndKickPrevUser(BigInteger appId , BigInteger userId) throws CurrentUserCurrentluUsedException,  Exception{
		List<Signon> listSignOn = userLoginDao.getSignOnByUserId(userId);
		if(listSignOn != null){			
			//3. Cek application id yg ada
			for (Signon signon : listSignOn) {
				if(signon.getApplicationId().compareTo(appId) == 0){
					
					// kalau kick prev login = true maka tenda login existing
					if ( !"Y".equalsIgnoreCase( signon.getApplication().getKickPreviousLogin())){
						throw new CurrentUserCurrentluUsedException("User " + signon.getUser().getUserCode() + "currently login on IP : " + signon.getTerminal()+ ",but policy not allow to kick this session. If this was used by you, please logoff prev login first", signon.getTerminal(), signon.getUserBrowser());
					}
					
					// update data sign on, jadi current terminal
					signon.setTerminal(signon.getTerminal());
					signon.setLogonTime(new Date()); 
					signon.setLogoutTime(null);
					signon.setUserBrowser(signon.getUserBrowser());
					// bikin history
					archiveAndUpdateSignOnData(signon);
					
					
					// kick user yang sedang di pakai 
					this.requestKickUser(signon.getApplicationId(), signon.getUuid());
					return signon;
				}
			}
			
			//4. insert ke tabel signOn
			Signon sigonBaru  = insertDataSignOn(userId , appId);
			
			return sigonBaru;
			
		}else{ // jika tidak ada data pd tabel sigon
			
			Signon sigonBaruWithoutExistingAtAll  = insertDataSignOn(userId , appId);
			return sigonBaruWithoutExistingAtAll;
		}
	}
	
	
	
	
	/**
	 * worker untuk request kick session dari app client
	 **/
	//@Async
	protected void requestKickUser (BigInteger applicationId , String uuid){
		//FIXME : 1 include async lib
		//FIXME : 2 kirim ke app client agar user dengan uud =xxx di kick session nya
	}
	
	/**
	 * Insert data ke dalam tabel sign on
	 * @param parameter
	 * @throws Exception
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private Signon insertDataSignOn(LoginParameter parameter) throws Exception{
		Signon data = new Signon();
		data.setUserId(parameter.getUserId());
		data.setApplicationId(parameter.getApplicationId());
		data.setLogonTime(new Date());
		data.setUuid(UUID.randomUUID().toString().replace("-", ""));
		data.setTerminal(InetAddress.getLocalHost().getHostAddress());		
		userLoginDao.insert(data);
		return data;
	}
	
	
	
	
	/**
	 * populate ke table sec_signon
	 **/
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private Signon insertDataSignOn(BigInteger userId , BigInteger applicationId) throws Exception{
		Signon data = new Signon();
		data.setUserId(userId);
		data.setApplicationId(applicationId);
		data.setLogonTime(new Date());
		data.setUuid(UUID.randomUUID().toString().replace("-", ""));
		data.setTerminal(InetAddress.getLocalHost().getHostAddress());		
		userLoginDao.insert(data);
		return data;
	}
	
	
	/**
	 * <ol>
	 * <li>archive data login lama -> ini di salin ke table lain :</li>
	 * <li>update data signon</li>
	 * </ol> 
	 **/
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	private void archiveAndUpdateSignOnData (Signon signOnData) throws Exception{
		//FIXME : masukan proses archiving
		Signon dataToDuplicate =  userLoginDao.getSigonData(signOnData.getId());
		SignonHistory h = new SignonHistory(); 
		BeanUtils.copyProperties(dataToDuplicate, h);
		h.setId(null);
		userLoginDao.insert(h);
		userLoginDao.update(signOnData);
		
	}

	@Override
	public User getUserByUserName(String userName) {
		User user=null;
		try {
			user = userLoginDao.getUserByUsername(userName);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<Branch> getUserBranches(BigInteger userId) {
		
		return null;
	}

	@Override
	public Signon getSigonData(BigInteger sigonId) {
		return userLoginDao.getSigonData(sigonId);
	}
	
	
	/**
	 * Generate URL Redirect ke aplikasi yg memanggil security ini
	 * @param applicationId
	 * @param errorCode
	 * @param uuid
	 * @return URL
	 * @throws Exception
	 */
	public String getUrlRedirectToApplication(Application application ,BigInteger applicationId, Integer errorCode, String uuid, String userName , HttpServletResponse response ) throws Exception{
				
		String url = "re-post-to-requester.security-rpc?errorCode="  + errorCode + "&uuid=" + uuid + "&userName=" + userName + "&url=" + response.encodeURL(   application.generateAutomaticLoginUrl());
		System.out.println("URL Security : " + url);
		return url;
	}
}