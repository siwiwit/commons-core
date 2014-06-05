package id.co.sigma.common.server.rpc.impl;

import id.co.sigma.common.rpc.common.SessionTimerRpcService;
import id.co.sigma.common.server.rpc.BaseServerRPCService;

import javax.annotation.Resource;




/**
 * provider time out dari session
 */
public class SessionTimerRPCServiceImpl extends BaseServerRPCService<SessionTimerRpcService> implements SessionTimerRpcService {
	
	@Resource(name="http.session.timeout")
	private Long httpSessionTimeout;

	@Override
	public Class<SessionTimerRpcService> implementedInterface() {
		return SessionTimerRpcService.class;
	}

	/**
	 * method ini membaca definisi session timeout dari properties file "http.session.timeout". 
	 * 
	 * @return Session timeout dalam milliseconds.
	 */
	@Override
	public Long getSessionTimeoutLength() {
		return httpSessionTimeout;
	}

}
