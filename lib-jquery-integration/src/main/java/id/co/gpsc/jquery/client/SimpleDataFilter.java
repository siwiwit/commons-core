package id.co.gpsc.jquery.client;

/**
 * filter sederhana untuk mengatur data boleh visible atau tidak
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public interface SimpleDataFilter<DATA>  {
	boolean allowToVisible (DATA data) ; 

}
