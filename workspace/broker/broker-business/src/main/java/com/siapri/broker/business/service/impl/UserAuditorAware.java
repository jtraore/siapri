package com.siapri.broker.business.service.impl;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.siapri.broker.business.security.User;

@Component
public class UserAuditorAware implements AuditorAware<User> {

	@Override
	public User getCurrentAuditor() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null) {
			return null;
		}

		return (User) authentication.getPrincipal();
	}
}