package id.co.gpsc.common.server.service.system.impl;

import id.co.gpsc.common.server.service.system.ISimpleSessionRegistry;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;



/**
 * session registry. to be define
 **/
@Service(value="gpsc-session-registry")
public class SimpleSessionRegistryImpl extends SessionRegistryImpl implements ISimpleSessionRegistry{

}
