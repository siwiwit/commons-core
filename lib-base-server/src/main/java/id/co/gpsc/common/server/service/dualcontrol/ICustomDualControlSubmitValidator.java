package id.co.gpsc.common.server.service.dualcontrol;

import id.co.gpsc.common.data.app.CommonDualControlContainerTable;
import id.co.gpsc.common.data.app.DualControlEnabledData;
import id.co.gpsc.common.data.app.DualControlEnabledOperation;

/**
 * 
 * 
 * validasi untuk submit ke dual control table. ini di pergunakan untuk validasi tambahan terhadap data yang di post oleh user
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface ICustomDualControlSubmitValidator<DATA extends DualControlEnabledData<?, ?>> {
	
	
	
	/**
	 * validasi proses approval. kalau misalnya validasi gagal maka exception akan di throw dari sini
	 * @param dualControlledData data dual control yang perlu di cek
	 * @param operation operasi yang hendak di lakukan terhadap data 
	 *  
	 */
	public void validateApprovalRequest ( CommonDualControlContainerTable dualControlledData,
			DualControlEnabledOperation operation)  throws Exception ; 
	
	
	/**
	 * tipe yang akan di validasi
	 */
	public Class<DATA> getValidatedType () ; 

}
