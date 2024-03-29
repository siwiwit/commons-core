package id.co.gpsc.security.server.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.domain.User;
import id.co.gpsc.common.security.dto.UserDTO;
import id.co.gpsc.common.security.dto.UserDetailDTO;
import id.co.gpsc.common.security.exception.PasswordPolicyException;
import id.co.gpsc.common.security.rpc.UserRPCService;
import id.co.gpsc.common.server.dao.IGeneralPurposeDao;
import id.co.gpsc.security.server.UserDetailUtils;
import id.co.gpsc.security.server.service.IUserService;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * User RPC Impl
 * @author I Gede Mahendra
 * @since Dec 10, 2012, 1:42:47 PM
 * @version $Id
 */
public class UserRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseSecurityRPCService<UserRPCService> implements UserRPCService{

	private static final long serialVersionUID = 2632805041653674567L;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IGeneralPurposeDao generalPurposeDao;
	
	private Branch getBranchByCode(String code) {
		List<Serializable> filters = new ArrayList<Serializable>();
		filters.add(code);
		
		List<Branch> data = null;
		try {
			data = generalPurposeDao.loadDataByKeys(Branch.class, "branchCode", filters);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data == null || data.isEmpty() ? null : data.get(0);
	}
	
	
	@Override
	public PagedResultHolder<UserDTO> getUserByParameter(SimpleQueryFilter[] filter, int page, int pageSize) throws Exception{		
		return userService.getUserByParameter(filter, page, pageSize);
	}

	@Override
	public PagedResultHolder<UserDTO> getUserByParameter(BigInteger applicationId, SimpleQueryFilter[] filter,int page, int pageSize) throws Exception {	
		return userService.getUserAtWorklistByParam(applicationId, filter, page, pageSize);
	}

	@Override
	public PagedResultHolder<User> getUserByFilter(
			SimpleQueryFilter[] filters, int pagePosition, int pageSize)
			throws Exception {
		return userService.getUserByFilter(filters, pagePosition, pageSize);
	}

	@Override
	public void insert(User data) throws Exception, PasswordPolicyException {
		try {
			userService.insert(data);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void update(User data) throws Exception {
		userService.update(data);
	}

	@Override
	public void remove(BigInteger id) throws Exception {
		userService.remove(id);
	}

	@Override
	public void resetPassword(User data) throws Exception, PasswordPolicyException {
		userService.updateUserPassword(data);
	}

	@Override
	public UserDetailDTO getCurrentUserLogin() {		
		UserDetailDTO userDetails = UserDetailUtils.getInstance().getUserDetailDTOFromSecurityContext();
		
		Branch b = getBranchByCode(userDetails.getCurrentBranch());
		userDetails.setCurrentBranchName(b.getBranchName());
		
		return userDetails;
	}

	@Override
	public Class<UserRPCService> implementedInterface() {
		return UserRPCService.class;
	}
}