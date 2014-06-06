package id.co.gpsc.common.server.rpc;


import id.co.gpsc.common.data.AppConfigurationDrivenDetaiResultHolder;
import id.co.gpsc.common.data.InvalidSpreadSheetCellFormatException;
import id.co.gpsc.common.data.InvalidSpreadSheetCellFormatExceptionWrapper;
import id.co.gpsc.common.data.InvalidSpreadSheetRowException;
import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.SpreadSheetExceptionType;
import id.co.gpsc.common.data.SystemParamDrivenClass;
import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlDataRPCService;
import id.co.gpsc.common.data.app.DualControlDefinition;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;
import id.co.gpsc.common.data.app.HeaderDataOnlyCommonDualControlContainerTable;
import id.co.gpsc.common.data.app.SimpleMasterDataDualControlApprovalResult;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.data.query.SimpleQueryFilterOperator;
import id.co.gpsc.common.exception.InvalidExcelFileException;
import id.co.gpsc.common.server.dao.IGeneralPurposeDao;
import id.co.gpsc.common.server.service.system.DualControlDataService;
import id.co.gpsc.common.server.service.system.ICommonSystemService;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




/**
 * servlet untuk serve dual control
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 **/
public class DualControlDataRPCServiceImpl extends BaseServerRPCService<DualControlDataRPCService> implements DualControlDataRPCService{

	
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DualControlDataRPCServiceImpl.class);
	
	
	@Autowired
	private DualControlDataService dualControlDataService ;
	
	
	@Autowired
	ICommonSystemService commonSystemService ; 
	
	public DualControlDataRPCServiceImpl() {
		super();
		cellMap.put(0,"A");
		cellMap.put(1,"B");
		cellMap.put(2,"C");
		cellMap.put(3,"D");
		cellMap.put(4,"E");
		cellMap.put(5,"F");
		cellMap.put(6,"G");
		cellMap.put(7,"H");
		cellMap.put(8,"I");
		cellMap.put(9,"J");
		cellMap.put(10,"K");
		cellMap.put(11,"L");
		cellMap.put(12,"M");
		cellMap.put(13,"N");
		cellMap.put(14,"O");
		cellMap.put(15,"P");
		cellMap.put(16,"Q");
		cellMap.put(17,"R");
		cellMap.put(18,"S");
		cellMap.put(19,"T");
		cellMap.put(20,"U");
		cellMap.put(21,"V");
		cellMap.put(22,"W");
		cellMap.put(23,"X");
		cellMap.put(24,"Y");
		cellMap.put(25,"Z");

	}

	
	@Autowired
	private IGeneralPurposeDao generalPurposeDao ; 
	@Override
	public CommonDualControlContainerTable getDataById(BigInteger id)
			throws Exception {
		return generalPurposeDao.get(CommonDualControlContainerTable.class, id);
	}
	
	
	
	

	@Override
	public BigInteger submitDataForApproval(
			CommonDualControlContainerTable dualControlledData,
			DualControlEnabledOperation operation) throws Exception{
		try {
			CommonDualControlContainerTable swap =  dualControlDataService.submitDataForApproval(dualControlledData, operation);
			return swap.getId(); 
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
		
	}

	
	@Override
	public SimpleMasterDataDualControlApprovalResult submitMasterDataDataForApproval(
			CommonDualControlContainerTable dualControlledData,
			DualControlEnabledOperation operation) throws Exception {
		try {
			CommonDualControlContainerTable swap =  dualControlDataService.submitDataForApproval(dualControlledData, operation);
			SimpleMasterDataDualControlApprovalResult retval = new SimpleMasterDataDualControlApprovalResult() ; 
			
			retval.setId(swap.getId());
			retval.setApprovalStatus(swap.getApprovalStatus());
			retval.setReferenceNumber(swap.getReffNo());
			
			return retval ; 
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
	}
	
	@Override
	public String getTargetDataAsJSonString(String classFQCN,
			String dataIdAsCompactedString) throws Exception {
		
		return null;
	}
	
	
	@Override
	public void approveAndApplyData(
			CommonDualControlContainerTable dualControlledData) throws Exception {
		try {
			dualControlDataService.applyDataModification(dualControlledData.getId() , dualControlledData.getApprovalRemark());	
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
		
		
	}

	@Override
	public PagedResultHolder<CommonDualControlContainerTable> getDataRequiredApproval(
			String objectFQCN, SimpleQueryFilter[] filters,
			SimpleSortArgument[] sortArguments, int pageSize, int page) throws Exception{
		SimpleQueryFilter f = new SimpleQueryFilter("targetObjectFQCN", SimpleQueryFilterOperator.equal, objectFQCN);
		SimpleQueryFilter [] swap = 
		( filters==null) ? 
			  new SimpleQueryFilter[1] :  new SimpleQueryFilter[filters.length +1 ];   
		swap[swap.length-1] = f ; 
		if ( filters!= null){
			for (int i =0 ; i<  filters.length; i++){
				swap[i] = filters[i];
			}
		}
		return this.selectDataPaged(CommonDualControlContainerTable.class, swap, sortArguments, pageSize, page);
	}

	@Override
	public void rejectData(BigInteger dataId, String rejectReason)
			throws Exception {
		try {
			dualControlDataService.rejectData(dataId, rejectReason);
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
		
	}





	
	
	


	@Override
	public Class<DualControlDataRPCService> implementedInterface() {
		return DualControlDataRPCService.class;
	}




	@Override
	public PagedResultHolder<? extends DualControlEnabledData<?,?>> getDataForEditList(
			String objectFQCN, SimpleQueryFilter[] filters,
			SimpleSortArgument[] sortArguments, int pageSize, int page)
			throws Exception {
		try {
			return  this.dualControlDataService.getDataForEditList(objectFQCN, filters, sortArguments, pageSize, page);
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
	}





	@Override
	public <DATA extends DualControlEnabledData<?, ?>> PagedResultHolder<DATA> getBulkApprovalDataDetails(
			BigInteger approvalDataId, int pageSize, int page) throws Exception {
		return dualControlDataService.getBulkApprovalDataDetails(approvalDataId, pageSize, page);
	}





	@Override
	public List<DualControlDefinition> getMasterDataDualControlDefinitions() {
		return generalPurposeDao.list(DualControlDefinition.class, null);
	}





	@Override
	public void approveAndApplyBulkData(BigInteger bulkDataId) throws Exception {
		dualControlDataService.approveAndApplyBulkData(bulkDataId);
		
	}





	@Override
	public void rejectBulkData(BigInteger bulkDataId, String rejectReason) throws Exception {
		try {
			dualControlDataService.rejectBulkData(bulkDataId, rejectReason);
		} catch (Exception e) {
			throw translateToSerializableException(e);
		}
		
	}
	
	private Map<Integer, String> cellMap = new HashMap<Integer, String>();
	
	

	/**
	 * handler upload bulk upload
	 * @param uploadedDataKey key untuk file upload. ini unutk mencari file dalam session
	 * @param targetClassFQCN fqcn dari item yang di handle
	 * @param remark catatan dari data
	 *  
	 */
	public  HeaderDataOnlyCommonDualControlContainerTable handleUploadedMasterFile(
			  String  uploadedDataKey,
			   String targetClassFQCN ,
			  String remark 
			 ) throws InvalidExcelFileException , Exception{
		
		HeaderDataOnlyCommonDualControlContainerTable container = null;
		
		try {
			container =	dualControlDataService.handleUploadedMasterFile(uploadedDataKey, targetClassFQCN , remark);
		}
		
		catch ( InvalidSpreadSheetCellFormatExceptionWrapper invExc){
			
			
			StringBuffer exceptionMessages = new StringBuffer(); 
			
			for(InvalidSpreadSheetRowException rowException  : invExc.getExceptions()) {
				
				StringBuffer internalException  = new StringBuffer();
				
				for(InvalidSpreadSheetCellFormatException cellException : rowException.getExceptionList()) {

					String colMessage = " pada kolom ["+cellException.getBusinessColumnName()+"]";
					
					if(cellException.getExceptionType().equals(SpreadSheetExceptionType.DATE_EXCEPTION)) {
						internalException.append("Format Tanggal Salah "+cellException.getExceptionValue()+colMessage+", ");
					} else if(cellException.getExceptionType().equals(SpreadSheetExceptionType.NUMERIC_EXCEPTION)) {
						
						internalException.append("Format Angka Salah "+cellException.getExceptionValue()+colMessage+", ");
					} else if(cellException.getExceptionType().equals(SpreadSheetExceptionType.STRING_EXCEPTION)) {
						
						internalException.append("Format Text Salah "+cellException.getExceptionValue()+colMessage+", ");
					} else if(cellException.getExceptionType().equals(SpreadSheetExceptionType.BOOLEAN_EXCEPTION)) {

						internalException.append("Format Boolean Salah "+cellException.getExceptionValue()+colMessage+", ");
					}
					
				}
				
				if(!internalException.toString().isEmpty()) {
					exceptionMessages.append("\nBaris "+rowException.getRowNumber()+":\n");
					exceptionMessages.append(internalException.substring(0, internalException.length()-2));
				}
					
			}
			
			
			if(!exceptionMessages.toString().isEmpty()) {
				throw new Exception(exceptionMessages.toString());
			}
		}
		catch (Exception e) {
			throw e;
		}
		
		return container;
		
	}





	@Override
	public void rejectAllWaitingApprovalData(Date latestDateToFetch,
			String remarkForAllRejected) throws Exception {
		dualControlDataService.rejectAllWaitingApprovalData(latestDateToFetch, remarkForAllRejected);
	}




	@SuppressWarnings( value={"unchecked", "rawtypes"})
	@Override
	public <D extends SystemParamDrivenClass<?, ?>>  AppConfigurationDrivenDetaiResultHolder<D> getSytemConfigurationDrivenData(
			String fqcn) throws Exception {
		
		Class<D> cls = (Class<D>) Class.forName(fqcn);
		AppConfigurationDrivenDetaiResultHolder<D> retval = new AppConfigurationDrivenDetaiResultHolder<D>(); 
		
		SystemParamDrivenClass cnf =  commonSystemService.loadConfiguration(cls); 
		retval.setConfigurationData((D) cnf);
		retval.setLatestWaitingApprovalData(dualControlDataService.getLatestWaitingApprovalData(fqcn));
		return retval;
	}

}
