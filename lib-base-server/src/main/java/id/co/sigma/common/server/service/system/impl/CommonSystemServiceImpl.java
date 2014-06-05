package id.co.sigma.common.server.service.system.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import id.co.sigma.common.data.SystemParamDrivenClass;
import id.co.sigma.common.data.app.SystemSimpleParameter;
import id.co.sigma.common.server.dao.IGeneralPurposeDao;
import id.co.sigma.common.server.data.system.CommonSequenceTable;
import id.co.sigma.common.server.service.system.ICommonSystemService;
import id.co.sigma.common.server.service.AbstractSigmaService;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class CommonSystemServiceImpl extends AbstractSigmaService implements ICommonSystemService{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonSystemServiceImpl.class);	
	
	
	@Autowired
	private IGeneralPurposeDao generalPurposeDao ;
	
	
	@Autowired
	@Qualifier(value="transactionManager")
	private  AbstractPlatformTransactionManager transactionManager;
	
	
	
	
	/**
	 * parameter yg di cache
	 */
	private Map<String, SystemSimpleParameter> cachedParameters = new HashMap<String, SystemSimpleParameter>();
	
	
	private Map<String, SystemParameterFieldMetadataComposer<?>> cachedSystemParameterDrivenDataComposer = new HashMap<String, SystemParameterFieldMetadataComposer<?>>(); 
	
	
	

	@Override
	
	public Long getSequence(final String sequenceName, final String remarkForSequence) {
		 
		
		TransactionTemplate tmpl = new TransactionTemplate(transactionManager ); 
		return tmpl.execute(new TransactionCallback<Long>() {
			@Override
			public Long doInTransaction(TransactionStatus arg0) {
				try {
					CommonSequenceTable swap = generalPurposeDao.get(CommonSequenceTable.class, sequenceName);
					if ( swap == null){
						CommonSequenceTable baru = new CommonSequenceTable() ; 
						baru.setId(sequenceName) ; 
						baru.setRemark(remarkForSequence); 
						baru.setLatesSequence(1L); 
						generalPurposeDao.insert(baru); 
						return 1L ;
					}else{
						Long retval = swap.getLatesSequence(); 
						retval+=1L ; 
						swap.setLatesSequence(retval); 
						generalPurposeDao.update( swap); 
						return retval ;
					}
				} catch (Exception e) {
					logger.error("gagal membaca sequence. error : " + e.getMessage() , e); 
					return null ; 
				}
			}
		}); 
		
		
		 
	}

	@Override
	
	public Long[] getSequences(final String sequenceName,final  int sizeOfSequenceToFetch,
			final String remarkForSequence) {
		
		if ( sequenceName== null|| sequenceName.isEmpty()|| sizeOfSequenceToFetch==0)
			return null ;
		
		TransactionTemplate tmpl = new TransactionTemplate(transactionManager );
		return tmpl.execute(new TransactionCallback<Long[]>() {
			@Override
			public Long[] doInTransaction(TransactionStatus arg0) {
				try {
					CommonSequenceTable swap = generalPurposeDao.get(CommonSequenceTable.class, sequenceName);
					if ( swap == null){
						CommonSequenceTable baru = new CommonSequenceTable() ; 
						baru.setId(sequenceName) ; 
						baru.setRemark(remarkForSequence); 
						baru.setLatesSequence((long)sizeOfSequenceToFetch); 
						generalPurposeDao.insert(baru); 
						 Long[]  retval = new Long[sizeOfSequenceToFetch] ;
						 for ( int i = 0 ; i <  sizeOfSequenceToFetch ; i++){
							 retval[i] = (long)i+ 1L ; 
						 }
						return retval ;
					}else{
						 
						 
						
						Long retval = swap.getLatesSequence();
						long start = retval; 
						retval+=(long)sizeOfSequenceToFetch ; 
						swap.setLatesSequence(retval); 
						generalPurposeDao.update( swap);
						Long [] actualRetval = new Long[sizeOfSequenceToFetch]; 
						for(int i = 0 ; i< sizeOfSequenceToFetch; i++){
							actualRetval[i] = start + (long)i + 1 ; 
						}
						return actualRetval ;
					}
				} catch (Exception e) {
					logger.error("gagal membaca sequence. error : " + e.getMessage() , e); 
					return null ; 
				}
			}
		});
		
	}

	@Override
	public String generate0LeadedNumber(long number, int lengthOfString) {
		String tmp = number +""; 
		tmp  = tmp.replaceAll("\\,", ""); 
		if ( tmp.length()< lengthOfString){
			int s = lengthOfString -   tmp.length(); 
			for ( int i= 0 ; i < s ; i++){
				tmp = "0" + tmp; 
			}
		}
		return tmp;
	}

	@Override
	public SystemSimpleParameter getParameterByKey(String key) {
		if ( cachedParameters.containsKey(key))
			return cachedParameters.get(key); 
		try {
			SystemSimpleParameter p = generalPurposeDao.get(SystemSimpleParameter.class, key); 
			if ( p== null)
				return null ; 
			cachedParameters.put(key, p); 
			return p ; 
		} catch (Exception e) {
			logger.error("gagal membaca key system dengan key : " + key + ", error : " + e.getLocalizedMessage(), e ); 
			return null;
		}
		
	}

	@Override
	public void reloadCachedResource() {
		cachedParameters.clear(); 
		
	}

	@Override
	public Map<String, SystemSimpleParameter> getParameterByKeys(String[] keys)   {
		Map<String, SystemSimpleParameter> retval = new HashMap<String, SystemSimpleParameter>() ; 
		List<Serializable> notCached = new ArrayList<Serializable>(); 
		for ( String scn : keys){
			if ( cachedParameters.containsKey(scn))
				retval.put(scn, cachedParameters.get(scn)); 
			else{
				notCached.add(scn); 
			}
		}
		if ( !notCached.isEmpty()){
			 try {
				List<SystemSimpleParameter> params =  generalPurposeDao.loadDataByKeys(SystemSimpleParameter.class, "id", notCached);
				if ( params!= null){
					for (SystemSimpleParameter scn : params ){
						retval.put(scn.getId()	, scn); 
						cachedParameters.put(scn.getId(), scn);
					}
				}
			} catch (Exception e) {
				logger.error("gagal membaca key system (bulk), error : " + e.getLocalizedMessage(), e );
				e.printStackTrace();
			} 
		}
 		return retval;
	}

	@Override
	public String generateRefNumber(String key, String prefix,
			int lengthOfSequence, String defaultRemarkForSequence) {
		Long seq = getSequence(key, defaultRemarkForSequence); 
		return ( prefix!= null ? prefix : "" ) +  this.generate0LeadedNumber(seq, lengthOfSequence); 
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <D extends SystemParamDrivenClass<?,?>> D loadConfiguration(
			Class<D> clazz) throws Exception{
		String key = clazz.getName(); 
		if ( !cachedSystemParameterDrivenDataComposer.containsKey( key)){
			cachedSystemParameterDrivenDataComposer.put(clazz.getName(), new SystemParameterFieldMetadataComposer(clazz));
		}
		SystemParameterFieldMetadataComposer p = cachedSystemParameterDrivenDataComposer.get(key);
		String[] dbKeys =  p.getSystemParamKeys();
		D retval = BeanUtils.instantiate(clazz); 
		Map<String, SystemSimpleParameter> x =   getParameterByKeys(dbKeys);
		p.putValueToObject(retval, x.values());
		return retval;
	}

	@Override
	@Transactional(readOnly=false )
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <D extends SystemParamDrivenClass<?,?>> void saveConfiguration(
			D configuration) throws Exception {
		
		
		Class<D> clazz = (Class<D>) configuration.getClass(); 
		String key = clazz.getName(); 
		if ( !cachedSystemParameterDrivenDataComposer.containsKey( key)){
			cachedSystemParameterDrivenDataComposer.put(clazz.getName(), new SystemParameterFieldMetadataComposer(clazz));
		}
		//FIXME: masukan encyption di sini 
		SystemParameterFieldMetadataComposer p = cachedSystemParameterDrivenDataComposer.get(key);
		List<SystemSimpleParameter> params =  p.extractParameter(configuration); 
		generalPurposeDao.delete(SystemSimpleParameter.class , "id"  , p.getSystemParamKeys());
		generalPurposeDao.inserts(params);
		
	}

	
	
}
