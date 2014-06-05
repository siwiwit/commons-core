package id.co.sigma.common.server.service.system.impl;

import id.co.sigma.common.server.service.system.ISigmaSessionRegistry;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;



/**
 * session registry. to be define
 **/
@Service(value="sigma-session-registry")
public class SigmaSessionRegistryImpl extends SessionRegistryImpl implements ISigmaSessionRegistry{

}
