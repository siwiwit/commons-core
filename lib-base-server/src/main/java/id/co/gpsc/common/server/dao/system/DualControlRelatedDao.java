package id.co.gpsc.common.server.dao.system;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.DualControlApprovalStatusCode;
import id.co.gpsc.common.data.app.SimplifiedDualControlContainerTable;
import id.co.gpsc.common.data.query.SimpleQueryFilter;
import id.co.gpsc.common.data.query.SimpleSortArgument;
import id.co.gpsc.common.server.dao.IBaseDao;

/**
 * dao untuk pekerjan dual control table
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface DualControlRelatedDao extends IBaseDao{
	
	
	
	
	/**
	 * 
	 * 
	 * membaca data yang hanya dalam proses approval. filter berikut otomatis akan di tambahkan
	 * <ol>
	 * 	<li>approval_status in {@link DualControlApprovalStatusCode#WAITING_APPROVE_CREATE}  , {@link DualControlApprovalStatusCode#WAITING_APPROVE_DELETE} , {@link DualControlApprovalStatusCode#WAITING_APPROVE_UPDATE}</li>
	 * 	<li>flag :{@link SimplifiedDualControlContainerTable#dualControlDefinition}dualControlledOnFlag </li>
	 * <li>strick vs not. kalau strick, akan di cari yang username nya tidak sama</li>
	 * <ol>
	 * 
	 **/
	public PagedResultHolder<SimplifiedDualControlContainerTable> getApprovalList ( SimpleQueryFilter[] filters , SimpleSortArgument[] sorts , String username , int page , int pageSize ) ;
	

}
