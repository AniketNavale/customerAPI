package com.restapipractice.restapi.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.restapipractice.restapi.dao.CustomerDao;
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.entities.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplementationTest {

	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerDao customerDao;
	
	@Test
	@DisplayName("Fetching all customers from database")
	public void test_DisplayAllCustomers() {
		when(customerDao.findAll()).thenReturn(Stream.of(new Customer(1,"Arihant",28,new Address(1,"Mumbai","India",400052)), new Customer(2, "Rakesh", 30,new Address(2,"Shimla","India",400065)))
				.collect(Collectors.toList()));
		assertEquals(2,customerService.getCustomers().size());
	}
	
	@Test
	@DisplayName("Fetching a single customer from database")
	public void test_DisplaySpecificCustomer() {
		Optional<Customer> customer = Optional.of(new Customer(2,"Hardik",45,new Address(1,"Mumbai","India",400052)));
		when(customerDao.findById(2)).thenReturn(customer);
		assertEquals(customer, customerDao.findById(2));
	}
	
	@Test
	@DisplayName("Adding a new customer in database")
	public void test_AddCustomer_addNewCustomerInDatabase() {
		Customer customer = new Customer(5,"Sachin",32,new Address(1,"Mumbai","India",400052));
		when(customerDao.save(customer)).thenReturn(customer);
		assertEquals(customer, customerService.addCustomer(customer)); 
		Mockito.verify(customerDao,times(1)).save(customer);  
	}
	
	@Test
	@DisplayName("Updating existing customer in database")
	public void test_UpdateCustomer_updateExistingCustomerInDatabase() {
		Customer customer = new Customer(5,"Sachin",32,new Address(1,"Mumbai","India",400052));
		customerDao.save(customer);
		ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class); 
		Mockito.verify(customerDao, times(1)).save(customerCaptor.capture());
		assertEquals("Sachin", customerCaptor.getValue().getName());
		assertEquals(5,customerCaptor.getValue().getId());
		assertEquals(32,customerCaptor.getValue().getAge());
		
	}
	
	@Test
	@DisplayName("Deleting customer from the database if it is present")
	public void test_DeleteCustomer_ifCustomerIsPresentInDatabase() {
		Optional<Customer> customer = Optional.of(new Customer(5,"Sachin",32,new Address(1,"Mumbai","India",400052)));
		when(customerDao.findById(5)).thenReturn(customer);
		customerService.deleteCustomer(5);                 
		ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
		Mockito.verify(customerDao, times(1)).delete(customerCaptor.capture());
		assertEquals("Sachin", customerCaptor.getValue().getName());
	    assertEquals(5,customerCaptor.getValue().getId());
		
	}
	
	
}

