package com.siapri.broker.business.service;

import java.util.Optional;

import com.siapri.broker.business.security.User;

public interface IAuthenticationService {

	Optional<User> connect(String login, String password);

	User getCurrentUser();
}
