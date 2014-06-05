package id.co.gpsc.common.client.common;

import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.Window;

/**
 *
 *@author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public abstract class CommonRunAsynCallback implements RunAsyncCallback {

	@Override
	public void onFailure(Throwable reason) {
		if ( Window.confirm("gagal load script. di sarankan anda untuk mereload halaman anda.\nReload sekarang?"))
			Window.Location.reload(); 

	}

	

}
