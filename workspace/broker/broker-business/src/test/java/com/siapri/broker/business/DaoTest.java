package com.siapri.broker.business;

import java.util.ArrayList;
import java.util.List;

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
import com.siapri.broker.business.model.Broker;
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
		
		final Address addressCustomerTest1 = new Address("Domicile", "12", "rue de la liberté", "", "13100", "Aix-en-Provence", "France", "Juste pour le test");
		final List<Address> listAddressCustomerTest1 = new ArrayList<>();
		listAddressCustomerTest1.add(addressCustomerTest1);
		
		final Customer customerTest1 = new Customer("Jacki", "TRAORé", listAddressCustomerTest1, TypeCustomer.FEMALE);
		daoService.save(customerTest1);

		Assert.assertEquals(1, daoService.getAll(Customer.class).size());
		
		// retrieve the customer Object from database
		final Customer customerTest1FromDb = daoService.getAll(Customer.class).get(0);
		Assert.assertEquals("Jacki", customerTest1FromDb.getFirstName());
		Assert.assertEquals("TRAORé", customerTest1FromDb.getLastName());

		// Update the customer
		final Address secondAddressCustomer1 = new Address("JOB", "23", "Chemin de la Loire", "toto", "13290", "Avignon", "France", "Secoond Address");
		customerTest1.getAddresses().add(secondAddressCustomer1);
		daoService.save(customerTest1);
		
		final List<Customer> allCustomer = daoService.getAll(Customer.class);
		final Customer customerTest1FromDbUpdated = allCustomer.get(0);
		final List<Address> addressesOfCustomerTest1 = customerTest1FromDbUpdated.getAddresses();

		Assert.assertEquals(1, allCustomer.size());
		Assert.assertEquals(2, customerTest1FromDbUpdated.getAddresses().size());
		Assert.assertEquals("JOB", addressesOfCustomerTest1.get(1).getLabel());
		Assert.assertEquals("Chemin de la Loire", addressesOfCustomerTest1.get(1).getStreet());
	}
	
	@Test
	public void BrokerTest() {
		//Broker brokerTest1 = new Broker("jtraore", "jtraorePwd1", "Jacki", "TRAORé", "", );
	}
	
}
