package id.co.gpsc.security.server.dao.impl;

import id.co.gpsc.common.security.domain.FunctionAssignment;
import id.co.gpsc.security.server.dao.BaseGenericDao;
import id.co.gpsc.security.server.dao.IFunctionAssignmentDao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

/**
 * Function assignment dao impl
 * @author I Gede Mahendra
 * @since Dec 13, 2012, 3:47:29 PM
 * @version $Id
 */
@Repository
public class FunctionAssignmentDaoImpl extends BaseGenericDao implements IFunctionAssignmentDao{

	@Override
	public void deleteFunctionByGroupId(BigInteger groupId) throws Exception {
		Query qry = getEntityManager().createQuery("DELETE FROM FunctionAssignment a WHERE a.groupId=:GROUP_ID");
		qry.setParameter("GROUP_ID", groupId);
		qry.executeUpdate();
	}
	
	
	@Override
	public void deleteUserGroupAssigmentByGroupId(BigInteger groupId)
			throws Exception {
		Query qry =getEntityManager().createQuery("delete from UserGroupAssignment ua where ua.groupId=:GROUP_ID") ; 
		qry.setParameter("GROUP_ID", groupId);
		qry.executeUpdate();
				
		
	} 

	@Override
	public List<BigInteger> getFunctionIdByGroupId(List<BigInteger> groupIds)
			throws Exception {
		String hql = "SELECT DISTINCT A.functionId FROM FunctionAssignment A WHERE A.groupId IN :GROUP_IDS";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("GROUP_IDS", groupIds);
		return query.getResultList();
	}

	@Override
	public List<FunctionAssignment> getFunctionAssignmentByIdAndGroupId(
			List<FunctionAssignment> data) throws Exception {
		String hql = "SELECT A FROM FunctionAssignment A WHERE 1=1";
		List<BigInteger> ids = extractFunctionIdFromData(data);
		if (ids != null && !ids.isEmpty())
			hql += " AND A.functionId in :LIST_FUNCTION_ID";
		List<BigInteger> groupIds = extractGroupIdFromData(data);
		if (groupIds != null && !groupIds.isEmpty())
			hql += " AND A.groupId in :LIST_GROUP_ID";
		Query query = getEntityManager().createQuery(hql);
		if (ids != null && !ids.isEmpty())
			query.setParameter("LIST_FUNCTION_ID", ids);
		if (groupIds != null && !groupIds.isEmpty())
			query.setParameter("LIST_GROUP_ID", groupIds);
		return query.getResultList();
	}
	
	/**
	 * extract id from list function assignment
	 * @param data list function assignment
	 * @return list id function assignment
	 */
	private List<BigInteger> extractFunctionIdFromData(List<FunctionAssignment> data) {
		if (data == null || data.isEmpty())
			return null;
		List<BigInteger> ids = new ArrayList<BigInteger>();
		for (FunctionAssignment itemData : data) {
			ids.add(itemData.getFunctionId());
		}
		return ids;
	}
	
	/**
	 * extract group id from function assignment
	 * @param data list function assignment
	 * @return list group id
	 */
	private List<BigInteger> extractGroupIdFromData(List<FunctionAssignment> data) {
		if (data == null || data.isEmpty())
			return null;
		List<BigInteger> groupIds = new ArrayList<BigInteger>();
		for (FunctionAssignment itemData : data) {
			groupIds.add(itemData.getGroupId());
		}
		return groupIds;
	}

	@Override
	public void deleteFunctionAssigmentByFunctionId(BigInteger functionId)
			throws Exception {
		
		String queryString = "DELETE FROM FunctionAssignment a WHERE a.functionId = :ID_FUNCTION";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("ID_FUNCTION", functionId);
		query.executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<FunctionAssignment> getFunctionAssigmentByGroupId(
			BigInteger groupId) {
		String hql ="SELECT a from FunctionAssignment a where a.groupId = :GROUP_ID" ; 
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("GROUP_ID", groupId);
		return query.getResultList();
	}

	
}