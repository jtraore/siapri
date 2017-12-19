package com.siapri.broker.business;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.siapri.broker.business.security.User;
import com.siapri.broker.business.service.IAuthenticationService;
import com.siapri.broker.business.service.IBasicDaoService;
import com.siapri.broker.business.service.impl.DaoCacheService;

@Component
public class Application implements ApplicationContextAware {
	
	private static ApplicationContext context;

	private static User authenticatedUser;
	
	public static IBasicDaoService getDaoService() {
		return context.getBean(IBasicDaoService.class);
	}
	
	public static DaoCacheService getDaoCacheService() {
		return context.getBean(DaoCacheService.class);
	}
	
	public static IAuthenticationService getAuthenticationService() {
		return context.getBean(IAuthenticationService.class);
	}

	public static User getAuthenticatedUser() {
		return authenticatedUser;
	}

	public static void setAuthenticatedUser(final User authenticatedUser) {
		Application.authenticatedUser = authenticatedUser;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
