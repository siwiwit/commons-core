package id.co.gpsc.common.server.rpc.system;

import id.co.gpsc.common.data.lov.Common2ndLevelLOVHeader;
import id.co.gpsc.common.data.lov.CommonLOV;
import id.co.gpsc.common.data.lov.CommonLOVHeader;
import id.co.gpsc.common.data.lov.ILookupDetail;
import id.co.gpsc.common.data.lov.ILookupHeader;
import id.co.gpsc.common.data.lov.LOV2ndLevelRequestArgument;
import id.co.gpsc.common.data.lov.LOVProviderRPCService;
import id.co.gpsc.common.data.lov.LOVRequestArgument;
import id.co.gpsc.common.data.lov.LOVSource;
import id.co.gpsc.common.exception.CacheStillUpToDateException;
import id.co.gpsc.common.exception.UnknownLookupValueProviderException;
import id.co.gpsc.common.server.dao.DirectTableLookupProviderDao;
import id.co.gpsc.common.server.lov.Base2ndLevelLOVProvider;
import id.co.gpsc.common.server.lov.BaseLOVProvider;
import id.co.gpsc.common.server.lov.ISelfRegisterLovManager;
import id.co.gpsc.common.server.rpc.BaseServerRPCService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Lov Provider servlet
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @author <a href="gede.mahendra@sigma.co.id">Gede Mahendra</a>
 **/
/*@WebServlet(name="id.co.sigma.common.server.rpc.system.LOVProviderRPCServiceImpl" ,
			description="servlet provider Lookup value. custom maupun yang simple" , 
			urlPatterns={
				"/corelib-rpc/lov-data.app-rpc"
			}, loadOnStartup=1
		)*/
public class LOVProviderRPCServiceImpl extends /*BaseSelfRegisteredRPCService*/BaseServerRPCService<LOVProviderRPCService> implements LOVProviderRPCService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6506373837260010220L;
	
	
	

	@Autowired(required=true )
	@Qualifier(value="common-lov.directTableProvider")
	private DirectTableLookupProviderDao directTableLookupProviderDao ;
	
	
	
	@Autowired
	private ISelfRegisterLovManager selfRegisterLovManager;
	
	
	@Override
	public List<CommonLOVHeader> getModifiedLOVs(String localizationCode,
			List<LOVRequestArgument> dataRequest) {
		
		
		
		try {
			List<LOVRequestArgument> directTable = new ArrayList<LOVRequestArgument>(); 
			List<CommonLOVHeader> retval = new ArrayList<CommonLOVHeader>();
			for ( LOVRequestArgument scn : dataRequest){
				if ( LOVSource.directFromLookupTable.equals(scn.getSource()))
					directTable.add(scn); 
				else if ( LOVSource.useCustomProvider.equals(scn.getSource())){
					BaseLOVProvider provider =selfRegisterLovManager.get(scn.getId());
					if ( provider!=null){
						String dbVersion = provider.getVersion();
						if (dbVersion.equals(  scn.getCacheVersion()))
							continue ;	
						
						CommonLOVHeader  headCustom = provider.getLookupValues(localizationCode, scn); //customLOVProviders.get(scn.getId()).getLookupValues(localizationCode, scn);
						if ( headCustom!=null)
							retval.add(headCustom); 
					}
					
					 
				}	
			}
			
			if ( !directTable.isEmpty()){
				Collection<CommonLOVHeader> directData = getDirectLookupTableLOVData(localizationCode , directTable);
				if(directData!=null&&!directData.isEmpty())
					retval.addAll(directData);
			}
			
			
			return retval;
		} catch (Exception e) {
			e.printStackTrace(); 
			return null ; 
		}
		
	}
	 
	
		
	/**
	 * LOV yang di ambil langsung dari table lendor_data.m_lookup_details
	 * @param lovIDs list of lov id yang hendak di cek
	 **/
	private Collection<CommonLOVHeader> getDirectLookupTableLOVData(String localizationCode,List<LOVRequestArgument> lovIDs){
		if ( lovIDs==null||lovIDs.isEmpty())
			return null ; 
		// baca dulu semua header def 
		List<String> ids = new ArrayList<String>();
		for ( LOVRequestArgument scn : lovIDs){
			ids.add(scn.getId());
		}
		List<ILookupHeader> heads = this.directTableLookupProviderDao.getLookupHeaders(localizationCode , ids) ;
		if ( heads==null||heads.isEmpty())
			return null ; 
		
		// pastikan bahwa yang masih up to date masuk
		Map<String, LOVRequestArgument> indexed= new HashMap<String, LOVRequestArgument>(); 
		for(LOVRequestArgument scn :  lovIDs){
			if ( scn.getCacheVersion()==null|| scn.getCacheVersion().length()==0)
				continue ; 
			indexed.put(scn.getId(), scn) ; 
		}
		List<CommonLOVHeader> needSync = new ArrayList<CommonLOVHeader>(); 
		
		//Map<String, LookupHeader> indexedHeader= new HashMap<String, LookupHeader>();
		Map<String, CommonLOVHeader> indexedUnSync = new HashMap<String, CommonLOVHeader>();
		
		// check mana saja yang perlu baca ulang()
		for ( ILookupHeader lh : heads){
			if ( indexed.containsKey(lh.getLovId())  &&  lh.isCacheable() 
					&& lh.getVersion()!=null){
				LOVRequestArgument ar= indexed.get(lh.getLovId());
				// check versi nya sama ndak
				if (lh.getVersion().equals(  ar.getCacheVersion())){
					continue ; 
				}
			}
			CommonLOVHeader notSyncHead = new CommonLOVHeader();
			notSyncHead.setCacheable(lh.isCacheable());
			notSyncHead.setLovId(lh.getLovId());
			notSyncHead.setLovRemark(lh.getLovRemark());
			notSyncHead.setSource(LOVSource.directFromLookupTable);
			notSyncHead.setVersion(lh.getVersion());
			notSyncHead.setDetails(new ArrayList<CommonLOV>());
				
			needSync.add(notSyncHead); 	
			indexedUnSync.put(notSyncHead.getLovId(), notSyncHead);
			
		}
		/// check dari lookup details 
		if ( needSync==null||needSync.isEmpty()){
			return null ; 
		}
		List<ILookupDetail> allDetails = this.directTableLookupProviderDao.getLookupDetails(localizationCode , indexedUnSync.keySet()); 
		if ( allDetails==null|| allDetails.isEmpty())
			return null ; 
		 
		 
		for ( ILookupDetail scn : allDetails){
			
			if ( !indexedUnSync.containsKey(scn.getParentId()))
				continue ;
			
			CommonLOV cLov = new CommonLOV();
			
			cLov.setDataValue(scn.getDataValue()); 
			cLov.setLabel(scn.getLabel()); 
			cLov.setAdditionalData1(scn.getAdditionalData1()); 
			cLov.setAdditionalData2(scn.getAdditionalData2()); 
			indexedUnSync.get(scn.getParentId()).getDetails().add(cLov);
			 
			
		}
		
		return indexedUnSync.values() ; 
		
		
		
	}



	@Override
	public Common2ndLevelLOVHeader getModifiedLOV(String localizationCode,
			LOV2ndLevelRequestArgument dataRequest) throws UnknownLookupValueProviderException, CacheStillUpToDateException , Exception{
		Base2ndLevelLOVProvider provider =  this.selfRegisterLovManager.get2ndLevelProvider(dataRequest.getLookupId().getId());
		if(provider==null)
			 throw new UnknownLookupValueProviderException("Lookup value untuk :"   + dataRequest.getLookupId().getId() +",tidak di temukan. Cek apakah anda salah id, atau item ini belum di registerd di server"
				, dataRequest.getLookupId().getId()	)   ;
		return  provider.getLookupValues(localizationCode, dataRequest);
	}


/*
	@Override
	public Common2ndLevelLOVHeader getSameParent2ndLevelLOV(
			String localizationCode, StrongTyped2ndLevelLOVID lovId,
			String lookupValue, String currentClientLovVersion) throws UnknownLookupValueProviderException , CacheStillUpToDateException , Exception{
		Base2ndLevelLOVProvider provider =  this.selfRegisterLovManager.get2ndLevelProvider(lovId.getId());
		if(provider==null)
			 throw new UnknownLookupValueProviderException("Lookup value untuk :"   + lovId.getId() +",tidak di temukan. Cek apakah anda salah id, atau item ini belum di registerd di server"
				, lovId.getId()	)   ;
		return provider.getSameLevelLookupValues(localizationCode, lookupValue, currentClientLovVersion);
	}
	*/
	@Override
	public Common2ndLevelLOVHeader getSameParent2ndLevelLOV(
			String localizationCode,
			LOV2ndLevelRequestArgument lovRequestArgument)
			throws UnknownLookupValueProviderException,
			CacheStillUpToDateException, Exception {
		Base2ndLevelLOVProvider provider =  this.selfRegisterLovManager.get2ndLevelProvider(lovRequestArgument.getLookupId().getId());
		if(provider==null)
			 throw new UnknownLookupValueProviderException("Lookup value untuk :"   + lovRequestArgument.getLookupId().getId() +",tidak di temukan. Cek apakah anda salah id, atau item ini belum di registerd di server"
				, lovRequestArgument.getLookupId().getId()	)   ;
		return provider.getSameLevelLookupValues(localizationCode, lovRequestArgument.getParentLovValueId(), lovRequestArgument.getCacheVersion());
	}



	@Override
	public Class<LOVProviderRPCService> implementedInterface() {
		return LOVProviderRPCService.class;
	}
	
	
	

}
