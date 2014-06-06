/**
 * 
 */
package id.co.gpsc.security.server.service.impl;

import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;
import id.co.gpsc.common.security.exception.MenuHaveChildException;
import id.co.gpsc.security.server.dao.IFunctionAssignmentDao;
import id.co.gpsc.security.server.dao.IFunctionDao;
import id.co.gpsc.security.server.service.IFunctionService;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dode
 * @version $Id
 * @since Jan 7, 2013, 10:14:51 AM
 */
@Service
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private IFunctionDao functionDao;
	
	@Autowired
	private IFunctionAssignmentDao functionAssignmentDao;
	
	@Resource(name="security.securityApplicationId")
	private String currentApplicationIdAsString ;

	@Override
	public List<Function> getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(List<BigInteger> groupIds)
			throws Exception {
		List<BigInteger> functionIds = functionAssignmentDao.getFunctionIdByGroupId(groupIds);
		if (functionIds == null || functionIds.isEmpty())
			return null;
		List<Function> result = functionDao.getFunctionByFunctionIdOrderByTreeLevelAndSiblingOrder(functionIds);
		javaAssistDodge(result);
		return result;
	}
	
	/**
	 * menghidari java assist dengan menulkan field2 yang bertipe object pojo
	 * @param listData list dari data yang di nulkan, sifat param yang diinginkan pass by reference
	 */
	private void javaAssistDodge(List<Function> listData) {
		for (Function data : listData) {
			data.setApplication(null);
			data.setPageDefinition(null);
		}
	}
	
	@Override
	public List<Function> getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(
			BigInteger applicationId) throws Exception {
		List<Function> result = functionDao.getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(applicationId);
		javaAssistDodge(result);
		return result;
	}

	@Override
	public Function getFunctionById(BigInteger functionId) {
		try {
			return this.functionDao.get(Function.class, functionId);
		} catch (Exception e) {
			e.printStackTrace();
			return null ; 
		}  
	}

	@Override
	@Transactional(readOnly=false , propagation=Propagation.REQUIRED)
	public void updateFunction(Function function) throws Exception {
		functionDao.update(function);
		
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void eraseApplicationMenu(BigInteger applicationMenuId)
			throws Exception {
		// TODO ini berat, implement yang detail, ke sema node
		
		boolean functionHaveChild = isFunctionHaveChild(applicationMenuId);
		if (functionHaveChild) {
			throw new MenuHaveChildException("Hapus menu gagal. Menu merupakan parent dan memiliki child menu. Silahkan hapus child menunya terlebih dahulu.");
		}
		// 1. hapus dari sec_function_assigment sesuai yang id dari menu yang di delete
		functionAssignmentDao.deleteFunctionAssigmentByFunctionId(applicationMenuId);
		
		// delete from FunctionAssignment where functionId = :<<applicationMenuId>>
		// 2. hapus dari sec_function
		functionDao.deleteFunctionById(applicationMenuId);
	}
	
	/**
	 * is function have child
	 * @param parentId function id parent
	 * @return true = have child, false = not
	 * @throws Exception
	 */
	private boolean isFunctionHaveChild(BigInteger parentId) throws Exception {
		Long numberOfChild = functionDao.getFunctionByFunctionIdParent(parentId);
		boolean retval = numberOfChild > 0 ? true : false;
		return retval;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ApplicationMenuDTO insertNewApplicationMenu(ApplicationMenuDTO newData)
			throws Exception {
		//1. set field2 yang belum di set
		//2. set sibling order
		Function data = translateApplicationMenuDTOToFunction(newData);
		data.setApplicationId(new BigInteger(currentApplicationIdAsString));
		//set status menu active
		data.setStatus("A");
		Integer siblingOrder = functionDao.getNextSiblingOrder(data.getFunctionIdParent(), data.getApplicationId());
		data.setSiblingOrder(siblingOrder);
		//2. save function ke db
		//3. update tree level position jika tidak null
		functionDao.insertAndFlush(data);
		if (data.getMenuTreeCode() != null) {
			data.setMenuTreeCode(data.getMenuTreeCode() + "." + data.getId() );
			functionDao.update(data);
		}
		newData.setOrder(data.getSiblingOrder());
		newData.setMenuTreeCode(data.getMenuTreeCode());
		newData.setId(data.getId());
		
		return newData;
	}
	
	/**
	 * translate dari dto ke function
	 * @param dtoData
	 * @return
	 */
	private Function translateApplicationMenuDTOToFunction(ApplicationMenuDTO dtoData) {
		Function data = new Function();
		data.setFunctionCode(dtoData.getCode());
		data.setFunctionLabel(dtoData.getLabel());
		data.setPageId(dtoData.getPageId());
		data.setSiblingOrder(dtoData.getOrder());
		data.setStatus(dtoData.getStatus());
		data.setTreeLevelPosition(dtoData.getTreeLevel());
		data.setFunctionIdParent(dtoData.getParentId());
		data.setMenuTreeCode(dtoData.getMenuTreeCode());
		
		data.setCreatedBy(dtoData.getCreatedBy());
		data.setCreatedOn(dtoData.getCreatedDate());
		data.setCreatorIPAddress(dtoData.getCreatedIpAddr());
		data.setModifiedBy(dtoData.getModifiedBy());
		data.setModifiedOn(dtoData.getModifiedDate());
		data.setModifiedByIPAddress(dtoData.getModifiedIpAddr());
		return data;
	}
}
