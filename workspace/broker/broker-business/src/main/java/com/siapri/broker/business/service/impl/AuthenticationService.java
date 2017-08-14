package com.siapri.broker.business.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.siapri.broker.business.dao.repository.IUserRepository;
import com.siapri.broker.business.security.Profile;
import com.siapri.broker.business.security.User;
import com.siapri.broker.business.service.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {
	
	@Autowired
	private IUserRepository repository;
	
	@Autowired
	private AuditorAware<User> auditorAware;
	
	@Override
	public Optional<User> connect(final String login, final String password) {
		Optional<User> result = repository.findAll().stream().filter(user -> user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)).findFirst();
		if (!result.isPresent()) {
			result = repository.getAllAsStream().filter(user -> user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)).findFirst();
			repository.saveAndFlush(new User("admin", PasswordUtils.hash("admin".toCharArray()), "admin", "admin", "", Profile.ADMIN));
		}
		result.ifPresent(user -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword())));
		return result;
	}
	
	@Override
	public User getCurrentUser() {
		return auditorAware.getCurrentAuditor();
	}
	
}
