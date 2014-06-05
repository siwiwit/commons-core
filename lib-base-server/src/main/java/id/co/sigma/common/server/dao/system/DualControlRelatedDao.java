package id.co.sigma.common.server.dao.system;

import id.co.sigma.common.data.PagedResultHolder;
import id.co.sigma.common.data.app.DualControlApprovalStatusCode;
import id.co.sigma.common.data.app.SimplifiedDualControlContainerTable;
import id.co.sigma.common.data.query.SigmaSimpleQueryFilter;
import id.co.sigma.common.data.query.SigmaSimpleSortArgument;
import id.co.sigma.common.server.dao.IBaseDao;

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
	public PagedResultHolder<SimplifiedDualControlContainerTable> getApprovalList ( SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sorts , String username , int page , int pageSize ) ;
	

}
