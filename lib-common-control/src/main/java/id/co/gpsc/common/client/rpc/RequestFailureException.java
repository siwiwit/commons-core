package id.co.gpsc.common.client.rpc;

/**
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa</a>
 */
public class RequestFailureException extends Exception {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6551650691670873514L;

	public RequestFailureException(String message){
		super(message); 
	}

}
