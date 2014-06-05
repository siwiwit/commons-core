package id.co.gpsc.common.client;





/**
 * simpler callback. tanpa error handler. wrapper operasi UI sederhana yang tipikal nya callback
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 * @version $Id
 * @since 20-sept-2012
 **/
public interface SimpleAsyncCallback<RETVAL> {
	

	void callback(RETVAL callback);
}
