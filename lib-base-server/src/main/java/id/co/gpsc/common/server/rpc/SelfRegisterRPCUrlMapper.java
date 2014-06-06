package id.co.gpsc.common.server.rpc;


/**
 * interface utnuk url mapper yang self regiser. jadinya url mapper bisa menerima rpc yang self register
 * @author <a href="mailto:gede.sutarsa@gmail.com">Gede Sutarsa(gede.sutarsa@gmail.com)</a>
 * @version $Id
 * @since 5-aug-2012
 **/
public interface SelfRegisterRPCUrlMapper {

	/**
	 * register RPC service
	 **/
	public void registerRPCService(RPCServletWrapperController wrappedRPCservlet); 
}
