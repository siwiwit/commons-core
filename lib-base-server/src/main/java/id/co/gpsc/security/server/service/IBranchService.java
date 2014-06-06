package id.co.gpsc.security.server.service;

import java.math.BigInteger;
import java.util.List;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.security.domain.Branch;
import id.co.gpsc.common.security.dto.BranchDTO;

/**
 * Branch Service Interface
 * @author I Gede Mahendra
 * @since Jan 30, 2013, 4:16:37 PM
 * @version $Id
 */
public interface IBranchService {
	
	/**
	 * Get all branch
	 * @param parameter
	 * @param pagePosition
	 * @param pageSize
	 * @return PageResultHolder
	 * @throws Exception
	 */
	public PagedResultHolder<BranchDTO> getUserByParameter(SimpleQueryFilter[] filter, int pagePosition, int pageSize) throws Exception;
	
	/**
	 * Save or update
	 * @param data
	 * @throws Exception
	 */
	public void saveOrUpdate(Branch data) throws Exception;
	
	/**
	 * Remove branch
	 * @param id
	 * @throws Exception
	 */
	public void remove(BigInteger id) throws Exception;
	
	/**
	 * Get branch assignment by user id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<BranchDTO> getBranchAssignmentByUserId(BigInteger userId) throws Exception;
}