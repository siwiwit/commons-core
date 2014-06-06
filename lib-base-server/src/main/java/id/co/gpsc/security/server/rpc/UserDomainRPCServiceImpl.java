package id.co.gpsc.security.server.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.security.menu.UserDomain;
import id.co.gpsc.common.security.menu.UserDomainPaging;
import id.co.gpsc.common.security.rpc.UserDomainRPCService;
import id.co.gpsc.security.server.RedirectUrlUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

/**
 * 
 * @author I Gede Mahendra
 * @since Nov 29, 2012, 5:56:07 PM
 * @version $Id
 */
/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.UserDomainRPCServiceImpl" , 
		description="Servlet RPC untuk handle User Domain" , 
		urlPatterns={"/sigma-rpc/user-domain.app-rpc"})*/
public class UserDomainRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseSecurityRPCService<UserDomainRPCService> implements UserDomainRPCService{

	private static final long serialVersionUID = -486119248242224155L;
	
	@Autowired
	private RedirectUrlUtils redirectUrl;
	
	@SuppressWarnings("unused")
	@Override
	public PagedResultHolder<UserDomain> getUserDomainFromIIS(SigmaSimpleQueryFilter[] filter, int page, int pageSize) {
		String user = "null";
		String fullname = "null";
		String urlIISServer = redirectUrl.getUrlIisServer() + "get-user-domain.aspx?pagePosition=" + page + "&pageSize=" + pageSize;
		
		if(filter != null){
			for (int i = 0; i < filter.length; i++) {
				if(filter[i].getField().equals("username")){
					user = filter[i].getFilter();
					user = user.replace(" ", "%20");
					urlIISServer += "&username=" + user;
					break;
				}else{
					fullname = filter[i].getFilter();
					fullname = fullname.replace(" ", "%20");
					urlIISServer += "&fullname=" + fullname;
					break;
				}
			}
		}				
		System.out.println("URL : " + urlIISServer);
		RestTemplate rest = new RestTemplate();		
		String json = rest.getForObject(urlIISServer, String.class);		
		System.out.println("JSON : " + json);
		
		Gson gson = new Gson();
		UserDomainPaging userDomains = gson.fromJson(json, UserDomainPaging.class);
		
		List<UserDomain> holderData = getUserDomain(userDomains);
		if(holderData.size() == 0){
			return null;
		}
		
		PagedResultHolder<UserDomain> retval = new PagedResultHolder<UserDomain>();
		retval.setPage(page);
		retval.setPageSize(pageSize);
		retval.setTotalData(userDomains.getTotalData());
		retval.setHoldedData(holderData);		
		return retval;
	}
	
	/**
	 * Get user domain
	 * @param userPaging
	 * @return listUserDomain
	 */
	private List<UserDomain> getUserDomain(UserDomainPaging userPaging){
		List<UserDomain> result = new ArrayList<UserDomain>();
		for (UserDomain userDomain : userPaging.getUserDomains()) {
			UserDomain data = new UserDomain();
			data.setFullName(userDomain.getFullName());
			data.setUsername(userDomain.getUsername());
			result.add(data);
		}
		return result;
	}

	@Override
	public Class<UserDomainRPCService> implementedInterface() {
		return UserDomainRPCService.class;
	}
}