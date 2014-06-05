package id.co.gpsc.common.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

import id.co.gpsc.common.data.PagedResultHolder;
import id.co.gpsc.common.data.app.SimpleDualControlData;
import id.co.gpsc.common.data.query.SigmaSimpleQueryFilter;
import id.co.gpsc.common.data.query.SigmaSimpleSortArgument;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 *@deprecated di curigai tidak ada yang mempergunakan
 */
@Deprecated
public interface CommonDualControlRPCAsync   {
	
	
	
	
	
	/**
	 * versi ini menerima class dari dual control. ini akan memanfaatkan method 
	 **/
	public void getCurrentAvailableData(Class<? extends SimpleDualControlData<?>> objClass,
			SigmaSimpleQueryFilter[] filters,
			SigmaSimpleSortArgument[] sortArgs, int pageSize, int page,
			AsyncCallback<PagedResultHolder<SimpleDualControlData<?>>> callback)  ; 
	
	/**
	 * ini membaca semua data yang avaliable(dalam artian belum di hapus, masih berlaku) yang di tampilkan adalah data yang : 
	 * <ol
	 * <li>data yang blm di edit sama sekali</li>
	 * <li>data yang masih dalam proses editing</li>
	 * </ol>
	 * @param objectFQCN fqcn dari object
	 * @param filters filter untuk membaca data
	 * @param sortArgs sort argument, data di sort dengan column mana
	 * @param pageSize ukuran page per pembacaan data
	 * @param page page berapa yang perlu di baca
	 **/
	public void getCurrentAvailableData (  
			String objectFQCN ,   SigmaSimpleQueryFilter[] filters , SigmaSimpleSortArgument[] sortArgs , 
			int pageSize , int page  ,
			AsyncCallback<PagedResultHolder<SimpleDualControlData<?>>> callback)  ;

}
