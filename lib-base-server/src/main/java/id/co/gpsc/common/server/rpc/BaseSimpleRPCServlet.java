package id.co.gpsc.common.server.rpc;



import id.co.gpsc.common.server.dao.util.ServerSideDateTimeParser;
import id.co.gpsc.common.server.dao.util.ServerSideWrappedJSONParser;
import id.co.gpsc.common.util.json.SharedServerClientLogicManager;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * base class untuk RPC Servlet 
 * @author <a href='mailto:gede.sutarsa@gmail.com'>Gede Sutarsa</a>
 * @version $Id
 * @since 11 oct 2012
 **/
public abstract class BaseSimpleRPCServlet extends RemoteServiceServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6149080936892359972L;
	
	
	
	
	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		SharedServerClientLogicManager.getInstance().setJSONParser(  ServerSideWrappedJSONParser.getInstance());
		SharedServerClientLogicManager.getInstance().setDateTimeParser(ServerSideDateTimeParser.getInstance());
		
		
		super.onBeforeRequestDeserialized(serializedRequest);
	}

}
