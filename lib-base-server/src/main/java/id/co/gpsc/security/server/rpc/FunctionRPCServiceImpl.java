/**
 * 
 */
package id.co.gpsc.security.server.rpc;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.exception.DataNotFoundException;
import id.co.gpsc.common.security.domain.Function;
import id.co.gpsc.common.security.domain.PageDefinition;
import id.co.gpsc.common.security.dto.ApplicationMenuDTO;
import id.co.gpsc.common.security.dto.PageDefinitionDTO;
import id.co.gpsc.common.security.rpc.FunctionRPCService;
import id.co.gpsc.common.server.dao.IGeneralPurposeDao;
import id.co.gpsc.common.server.util.IDTOGenerator;
import id.co.gpsc.security.server.service.IApplicationService;
import id.co.gpsc.security.server.service.IFunctionService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * rpc untuk function
 * @author Dode
 * @version $Id
 * @since Jan 7, 2013, 10:30:26 AM
 */
/*@WebServlet(
		name="id.co.sigma.arium.security.server.rpc.FunctionRPCServiceImpl" , 
		description="Servlet RPC untuk handle Function Domain" , 
		urlPatterns={"/sigma-rpc/function.app-rpc"})*/
public class FunctionRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/ BaseSecurityRPCService<FunctionRPCService>
		implements FunctionRPCService {

	private static final long serialVersionUID = -4371019903401691889L;
	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionRPCServiceImpl.class); 
	@Autowired
	private IFunctionService functionService;
	
	
	@Autowired
	private IApplicationService applicationService ; 
	
	@Autowired
	private IGeneralPurposeDao generalPurposeDao ; 

	@Override
	public List<Function> getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(
			List<BigInteger> groupIds) throws Exception {
		return functionService.getFunctionByGroupIdOrderByTreeLevelAndSiblingOrder(groupIds);
	}
	
	@Override
	public List<Function> getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(
			BigInteger applicationId) throws Exception {
		List<Function> swap =  functionService.getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(applicationId);
		if ( swap != null&& !swap.isEmpty()){
			for ( Function scn : swap){
				scn.setApplication(null);
			}
		}
		return swap ; 
	}

	@Override
	public PagedResultHolder<PageDefinitionDTO> getCurrentAppAvailablePages(SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sortArgs , int pageSize , int page) throws Exception {
		
		
		BigInteger appId = applicationService.getCurrentApplicationId() ; 
		SigmaSimpleQueryFilter appIdFilter = new SigmaSimpleQueryFilter("applicationId"  ,SimpleQueryFilterOperator.equal ,appId) ; 
		SigmaSimpleQueryFilter[]  actual = appendToArray(filters, appIdFilter);
		
		PagedResultHolder<PageDefinitionDTO>  retval =  selectDataPaged(PageDefinition.class, actual, sortArgs, pageSize, page , new IDTOGenerator<PageDefinition, PageDefinitionDTO>() {
			@Override
			public PageDefinitionDTO generateDTO(PageDefinition sourceData) {
				PageDefinitionDTO retval = new PageDefinitionDTO(sourceData); 
				 
				return retval;
			}
		
		} );
		return retval ; 
		
	}

	@Override
	public List<Function> getCurrentAppMenusOrderByTreeLevelAndSiblingOrder()
			throws Exception {
		BigInteger currentAppId =  applicationService.getCurrentApplicationId();
		return getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(currentAppId);
	}

	@Override
	public List<ApplicationMenuDTO> getCurrentAppMenuDToByAppIdOrderByTreeLevelAndSiblingOrder()
			throws Exception {
		BigInteger appId = applicationService.getCurrentApplicationId() ;
		List<Function> swap =  functionService.getFunctionByApplicationIdOrderByTreeLevelAndSiblingOrder(appId);
		if ( swap != null && !swap.isEmpty()){
			List<ApplicationMenuDTO> retval = new ArrayList<ApplicationMenuDTO>();
			for ( Function scn : swap ){
				ApplicationMenuDTO dto = new ApplicationMenuDTO(scn); 
				retval.add(dto); 
			}
			return retval ;
		}
		return null;
	}

	@Override
	public void eraseApplicationMenu(BigInteger applicationMenuId)
			throws Exception {
		// TODO ini berat, implement yang detail, ke sema node
		try {
			functionService.eraseApplicationMenu(applicationMenuId);
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
		
	}

	@Override
	public void updateApplicationMenu(ApplicationMenuDTO menuData)
			throws   DataNotFoundException , Exception   {
		Function  f =  functionService.getFunctionById(menuData.getId()); 
		if ( f ==null ){
			throw new DataNotFoundException("unable to find menu with id: " + menuData.getId() +", data probable already erased");
		}
		//f.setModifiedBy(getCurrentUser().getUsername());
		f.setFunctionLabel(menuData.getLabel());
		f.setFunctionCode(menuData.getCode());
		f.setPageId(menuData.getPageId());
		try {
			functionService.updateFunction(f);
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
		
		
	}

	@Override
	public ApplicationMenuDTO appendNewMenuNode(ApplicationMenuDTO menuData) throws Exception {
		try {
			ApplicationMenuDTO retval = functionService.insertNewApplicationMenu(menuData);
			return retval;
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
	}

	@Override
	public Class<FunctionRPCService> implementedInterface() {
		return FunctionRPCService.class;
	}

	@Override
	public PageDefinition getPageDefinition(BigInteger page) {
		 if ( page== null){
			 LOGGER.error("page null  , request di abaikan");
			 return null ; 
		 }
		try {
			return generalPurposeDao.get(PageDefinition.class, page);
		} catch (Exception e) {
			LOGGER.error("gagal membaca data page untuk id : " + page  + " , error : " + e.getMessage() , e );
			e.printStackTrace();
			return null ; 
		}
	}
}
