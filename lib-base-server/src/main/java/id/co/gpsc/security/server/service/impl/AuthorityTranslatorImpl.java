package id.co.gpsc.security.server.service.impl;

import java.util.Collection;

import org.springframework.stereotype.Service;

import id.co.gpsc.common.server.data.security.SimpleAuthority;
import id.co.gpsc.common.server.data.security.UserLoginNotificationData;
import id.co.gpsc.common.server.service.system.AuthorityTranslator;

@Service
public class AuthorityTranslatorImpl implements AuthorityTranslator{

	@Override
	public Collection<SimpleAuthority> generateAuthorities(UserLoginNotificationData notificationResult) {
		return null;
	}
}