package com.siapri.broker.business.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

import com.siapri.broker.business.Application;
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
		Optional<User> result = getUser(login, password);
		if (!result.isPresent()) {
			repository.saveAndFlush(new User("admin", PasswordUtils.hash("admin".toCharArray()), "admin", "admin", "", Profile.ADMIN));
			result = getUser(login, password);
		}
		// result.ifPresent(user -> SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, user.getPassword())));
		result.ifPresent(user -> Application.setAuthenticatedUser(user));
		
		return result;
	}
	
	private Optional<User> getUser(final String login, final String password) {
		return repository.findAll().stream().filter(user -> user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(PasswordUtils.hash(password.toCharArray()))).findFirst();
	}

	@Override
	public User getCurrentUser() {
		return auditorAware.getCurrentAuditor();
	}

}
