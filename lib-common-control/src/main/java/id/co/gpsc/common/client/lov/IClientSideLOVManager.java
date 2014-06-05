package id.co.gpsc.common.client.lov;

import id.co.gpsc.common.data.lov.LOVRequestArgument;
import id.co.gpsc.common.data.lov.LOVSource;

import java.util.Collection;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Widget;

public interface IClientSideLOVManager {

	/**
	 * register change handler. dengan {@link LOVCapabledControl}
	 * @param parent widget parent dari kontrol
	 * @param control control yang bisa menerima LOV
	 **/
	public abstract HandlerRegistration registerLOVChangeListener(
			Widget parent, LOVCapabledControl control);

	/**
	 * register lov listener.Manager menangani request LOV ke server. setiap data LOV yang masuk akan di forward ke listener
	 * @param parent widget parent dari LOV. desain awal LOV akan di request bulk oleh parent widget.Ini akan membantu optimasi. kalau misalnya LOV sudah di cache
	 * @param id id dari LOV
	 * @param lovSource tipe LOV. ini untuk melocate LOV di server
	 * @param handler handler yang akan listen LOV change event
	 *  
	 **/
	public abstract HandlerRegistration registerLOVChangeListener(
			Widget parent, String id, LOVSource lovSource,
			LOVChangeHandler handler);

	/**
	 * request LOV manager agar id lov di update. jadinya LOV manager akan mengecek dulu, apakah di cache nya ada atau tidak lov yang di minta. kalau data ada, maka hanya data ini akan di kirim ke widget yang request, kalau misalnya belum berarti akan request ke server dan di cache, dan di teruskan ke semua listener
	 * @param parent parent widget yang request LOV data
	 * @param lovIdList list of ID parameter yang hendak di baca
	 **/
	public abstract void requestLOVUpdate(String localeCode, Widget parent,
			Collection<LOVRequestArgument> lovIdList);
	
	
	
	/**
	 * request update LOV data. versi ini akan mentrigger kalau proses sync sudah lengkap semua. ntah karena data di dari client semua sudah ada datanya
	 * atau karena dari server request nya sudah lengkap
	 * @param localeCode localization code
	 * @param parent parent object LOV
	 * @param lovIdList list of lov untuk di update
	 * @param commandAfterComplete command yang akan di trigger kembali kalau semua update LOV seelsai dilakukan
	 **/
	public abstract void requestLOVUpdate(String localeCode, Widget parent,
			Collection<LOVRequestArgument> lovIdList , Command commandAfterComplete);
	
	
	
	
	
	

}