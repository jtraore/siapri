package com.siapri.broker.business;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.siapri.broker.business.service.impl.BasicDaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessStarter.class)
@ActiveProfiles("dev")
public class AbstractTest {
	
	@Autowired
	protected BasicDaoService daoService;
}
