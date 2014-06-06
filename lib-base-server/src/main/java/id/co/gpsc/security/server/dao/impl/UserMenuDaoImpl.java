package id.co.gpsc.security.server.dao.impl;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.common.security.domain.Signon;
import id.co.gpsc.common.security.domain.UserGroupAssignment;
import id.co.gpsc.security.server.dao.BaseGenericDao;
import id.co.gpsc.security.server.dao.IUserMenuDao;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Dao untuk mendapatkan menu berdasarkan usernya
 * @author I Gede Mahendra
 * @since Nov 12, 2012, 12:25:45 PM
 * @version $Id
 */
@Repository
public class UserMenuDaoImpl extends BaseGenericDao implements IUserMenuDao{

	@SuppressWarnings("unchecked")
	@Override
	public Signon getDataSignonByParam(Signon parameter) throws Exception {
		Signon result = null;		
		Query qry = getEntityManager().createNamedQuery("SECURITY-GET-USERID");
		qry.setParameter("UUID", parameter.getUuid());
		qry.setParameter("APPLICATION_ID", parameter.getApplicationId());
		qry.setMaxResults(1);
		List<Signon> resultList = qry.getResultList();
		if(resultList.size() > 0){
			result = resultList.get(0);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserGroupAssignment> getGroupAssigmentByParam(BigInteger userId) throws Exception {
		List<UserGroupAssignment> result = null;
		Query qry = getEntityManager().createNamedQuery("SECURITY-GET-GROUPASSIGNMENT");
		qry.setParameter("USER_ID", userId);
		result = qry.getResultList();
		if(result.size() == 0){
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionAssignment> getFunctionAssignmentByGroupId(List<BigInteger> parameter) throws Exception {
		List<FunctionAssignment> result = null;
		Query qry = getEntityManager().createNamedQuery("SECURITY-GET-FUNCTIONASSIGNMENT");
		qry.setParameter("LIST_GROUP_ID", parameter);		
		result = qry.getResultList();
		if(result.size() == 0){
			result = null;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Function> getFunctionMenuByFunctionId(List<BigInteger> parameter) throws Exception {
		List<Function> result = null;
		Query qry = getEntityManager().createNamedQuery("SECURITY-GET-FUNCTIONMEU");
		qry.setParameter("LIST_FUNCTION_ID", parameter);
		qry.setParameter("STATUS", "A");
		result = qry.getResultList();
		if(result.size() == 0){
			result = null;
		}
		return result;
	}
}