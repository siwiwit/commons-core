package id.co.gpsc.security.server;

import id.co.gpsc.common.security.domain.Application;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.security.server.service.BaseSecurityService;
import id.co.gpsc.security.server.service.IUserLoginService;
import id.co.gpsc.security.server.service.impl.UserLoginService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Success autenfikasi implemen dari AuthenticationSuccessHandler dari spring security
 * @author I Gede Mahendra
 * @since Jan 16, 2013, 4:35:39 PM
 * @version $Id
 */
public class SimpleAuthentificationSuccess extends BaseSecurityService implements AuthenticationSuccessHandler , InitializingBean{

	@Autowired
	private IUserLoginService loginService;
	
	@Resource(name="securityApplicationId")
	private String securityApplicationId;
	
	@Resource(name="securityApplicationIdDev")
	private String securityApplicationIdDev;
	
	@Resource(name="securityHostAddress")
	private String securityHostAddress;
	
	private BigInteger securityApplicationIdAsBigInt ; 
	private BigInteger securityApplicationIdDevAsBigInt;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse respone, 
			Authentication auth) throws IOException, ServletException {
		
		UserDetailDTO user = getUserDetailDTOFromSecurityContext();
		if(user != null){
			SimpleUserDetail userApp = getUserDetailFromSecurityContext();		
			
			//Pengecekan apakah user login sebagai QA atau developer
			if(user.getApplicationId().compareTo(securityApplicationIdAsBigInt) == 0 || 
					user.getApplicationId().compareTo(securityApplicationIdDevAsBigInt) == 0){			
				securityAppHandler(request, respone, userApp);
			}else{
				try {
					otherAppHandler(request, respone, userApp);
				} catch (Exception e) {				
					e.printStackTrace();
				}
			}
		}else{			
			String url = securityHostAddress + request.getContextPath() + "/AriumSecurityLogin.jsp";
			respone.sendRedirect(url);
		}
	}
	
	
	/**
	 * ini kalau login ke arium_security
	 **/
	protected void securityAppHandler(HttpServletRequest request, HttpServletResponse respone,SimpleUserDetail userSigma) {
		try {
			List<SimpleUserAuthority> authorities = (List<SimpleUserAuthority>) userSigma.getAuthorities();
			if(authorities != null && authorities.size() > 0){
				System.out.println("ROLE : " + authorities.get(0).getAuthority());
				if(authorities.get(0).getAuthority().equals(SimpleAuthorityEnum.SUPER_ADMIN.toString())){
					respone.sendRedirect(userSigma.getApplicationUrl() + "AriumSecurity.jsp");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
		
	/**
	 * ini kalau mau login as other app
	 **/
	protected void otherAppHandler(HttpServletRequest request, HttpServletResponse respone,SimpleUserDetail userSigma) throws Exception {		
		Signon signonData = loginService.createSignOnDataAndKickPrevUser(userSigma.getApplicationId(), userSigma.getUserInternalId());
		loginService.notifyRequesterHostOnSuccessLogin(signonData);		
		Application app =  loginService.getApplication(signonData.getApplicationId());		
		String redirectUrl = loginService.getUrlRedirectToApplication(app, app.getId(),UserLoginService.CODE_SUCCESS, signonData.getUuid(), userSigma.getUsername(), respone);				
		respone.sendRedirect(redirectUrl);	
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		securityApplicationIdAsBigInt = new  BigInteger(securityApplicationId) ;
		securityApplicationIdDevAsBigInt = new BigInteger(securityApplicationIdDev);
	}
}