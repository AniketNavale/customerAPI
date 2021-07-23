package com.restapipractice.restapi.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.restapipractice.restapi.dao.CustomerContactDao;
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.entities.Contact;
import com.restapipractice.restapi.entities.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactServiceImplementationTest {

	@Autowired
	private ContactService contactService;
	
	@MockBean
	private CustomerContactDao customerContactDao;
	
	@Test
	@DisplayName("Fetching all contacts from database")
	public void test_DisplayAllContacts() {
		when(customerContactDao.findAll()).thenReturn(Stream.of(new Contact(1,9823564), new Contact(2,8952146))
				.collect(Collectors.toList()));
		when(customerContactDao.findAll()).thenReturn(Stream.of(new Customer(1,"Arihant",28,new Address(1,"Mumbai","India",400052,new Contact(1,9823564123))), 
				new Customer(2, "Rakesh", 30,new Address(2,"Shimla","India",400065)))
				.collect(Collectors.toList()));
		assertEquals(2,contactService.getContacts(1).size());
	}
	
	@Test
	@DisplayName("Adding a new contact in database")
	public void test_AddContact_addNewContactInDatabase() {
		Contact contact = new Contact(1,521456264);
		customerContactDao.save(contact);
		ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class); 
		Mockito.verify(customerContactDao, times(1)).save(contactCaptor.capture());
	}
	
	@Test
	@DisplayName("Updating existing contact in database")
	public void test_UpdateContact_updateExistingContactInDatabase() {
		Contact contact = new Contact(1,521456264);
		customerContactDao.save(contact);
		ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class); 
		Mockito.verify(customerContactDao, times(1)).save(contactCaptor.capture());
		assertEquals(1,contactCaptor.getValue().getContactId());
		assertEquals(521456264,contactCaptor.getValue().getContactNumber());
		
	}
	
	@Test
	@DisplayName("Deleting specific contact of specific customer from the database")
	public void test_DeleteSpecificContactOfSpecificCustomer() {
		Optional<Customer> customer = Optional.of(new Customer(1,"Arihant",28,"normal"));
		Optional<Contact> contact = Optional.of(new Contact(1,521456264));
		when(customerContactDao.findById(1)).thenReturn(contact);
		contactService.deleteSpecificContactOfSpecificCustomer(1,1);                 
		ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
		Mockito.verify(customerContactDao, times(1)).delete(contactCaptor.capture());
		assertEquals(1, contactCaptor.getValue().getContactId());
		assertEquals(521456264,contactCaptor.getValue().getContactNumber());
		
		
	}
}
