package com.siapri.broker.business;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.siapri.broker.business.model.Address;
import com.siapri.broker.business.model.Customer;
import com.siapri.broker.business.model.TypeCustomer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessStarter.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = { "dataSource" })
public class DaoTest extends AbstractTest {

	@Test
	public void contextLoads() {
		Assert.assertNotNull(daoService);
	}
	
	@Test
	public void CustomerTest() {
		// Assert that there is no Customer in the database.
		Assert.assertEquals(0, daoService.getAll(Customer.class).size());

		final Customer customerTest1 = new Customer("Jacki", "TRAORé", TypeCustomer.FEMALE);
		customerTest1.getAddresses().put("Domicile", new Address("12", "rue de la liberté", "", "13100", "Aix-en-Provence", "France", "Juste pour le test"));
		daoService.save(customerTest1);
		
		Assert.assertEquals(1, daoService.getAll(Customer.class).size());

		// retrieve the customer Object from database
		final Customer customerTest1FromDb = daoService.getAll(Customer.class).get(0);
		Assert.assertEquals("Jacki", customerTest1FromDb.getFirstName());
		Assert.assertEquals("TRAORé", customerTest1FromDb.getLastName());
		
		// Update the customer
		customerTest1.getAddresses().put("JOB", new Address("23", "Chemin de la Loire", "toto", "13290", "Avignon", "France", "Secoond Address"));
		daoService.save(customerTest1);

		final List<Customer> allCustomer = daoService.getAll(Customer.class);
		final Customer customerTest1FromDbUpdated = allCustomer.get(0);
		final Map<String, Address> addressesOfCustomerTest1 = customerTest1FromDbUpdated.getAddresses();
		
		Assert.assertEquals(1, allCustomer.size());
		Assert.assertEquals(2, customerTest1FromDbUpdated.getAddresses().size());
		Assert.assertTrue(addressesOfCustomerTest1.containsKey("JOB"));
		Assert.assertEquals("Chemin de la Loire", addressesOfCustomerTest1.get("JOB").getStreet());
	}

	@Test
	public void BrokerTest() {
		// Broker brokerTest1 = new Broker("jtraore", "jtraorePwd1", "Jacki", "TRAORé", "", );
	}

}
