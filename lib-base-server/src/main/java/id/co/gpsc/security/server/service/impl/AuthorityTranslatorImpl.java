package id.co.gpsc.security.server.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import id.co.gpsc.common.server.data.security.SigmaAuthority;
import id.co.gpsc.common.server.data.security.UserLoginNotificationData;
import id.co.gpsc.common.server.service.system.AuthorityTranslator;

@Service
public class AuthorityTranslatorImpl implements AuthorityTranslator{

	@Override
	public Collection<SigmaAuthority> generateAuthorities(UserLoginNotificationData notificationResult) {
		return null;
	}
}