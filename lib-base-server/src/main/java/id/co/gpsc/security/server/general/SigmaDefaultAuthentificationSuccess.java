package id.co.gpsc.security.server.general;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.security.server.dao.impl.UserLoginDaoImpl;
import id.co.gpsc.security.server.service.BaseSecurityService;

/**
 * Default authentifikasi saat login berhasil
 * @author I Gede Mahendra
 * @since Apr 22, 2013, 11:14:03 AM
 * @version $Id
 */
public class SigmaDefaultAuthentificationSuccess extends BaseSecurityService implements AuthenticationSuccessHandler{

	@Autowired
	private UserLoginDaoImpl userLoginDao;
	
	@Resource(name="defaultApplicationURL")
	private String singleApplicationURL;
	
	@Transactional(readOnly=false)
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse respon, Authentication auth) throws IOException,ServletException {				
		try {
			//Insert data ke dalam signon data
			Signon data = new Signon();
			data.setApplicationId(BigInteger.ONE); //statis karena aplikasi hanya ada 1
			data.setLogonTime(new Date());
			data.setTerminal(request.getRemoteHost());
			data.setUserBrowser(request.getHeader("user-agent"));
			data.setUserId(getSigmaUserDetailFromSecurityContext().getUserInternalId());
			data.setUuid(getSigmaUserDetailFromSecurityContext().getUuid());
																							
			userLoginDao.insert(data);
			respon.sendRedirect(singleApplicationURL);
		} catch (Exception e) {			
			e.printStackTrace();
		}
	}
}