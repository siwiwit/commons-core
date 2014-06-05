package id.co.gpsc.common.client.widget;


/**
 * event handler untuk paging change
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa(gede.sutarsa@gmail.com)</a>
 * @version $Id
 * 
 **/
public interface PageChangeHandler {
	/**
	 * di fire kalau paging berubah
	 * @param newPage page baru
	 **/
	public void onPageChange (int newPage ); 

}
