package com.restapipractice.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapipractice.restapi.dto.AddressDTO;
import com.restapipractice.restapi.dto.ContactDTO;
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.entities.Contact;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.services.ContactService;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
public class ContactControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@MockBean
	private ContactService contactService;
	
	@Test
	public void testAddContact_WhenValidInput_ContactAdded() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact = new Contact();

		mockContact.setContactId(25);
		mockContact.setContactNumber(2556548);
		
		String inputInJson = this.mapToJson(mockContact);
		
		String URI = "/customers/12/contacts";
		
		// convert entity to DTO
		ContactDTO contactResponse = modelMapper.map(mockContact, ContactDTO.class);
				
		Mockito.when(contactService.addContact(12,contactResponse)).thenReturn(contactResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		Assertions.assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
	}
	
	@Test
	public void testAddContact_WhenInvalidContactNumber_ThenErrorMessage() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact = new Contact();

		mockContact.setContactId(25);
		mockContact.setContactNumber(null);
		
		String inputInJson = this.mapToJson(mockContact);
		
		String URI = "/customers/12/contacts";
		
		// convert entity to DTO
		ContactDTO contactResponse = modelMapper.map(mockContact, ContactDTO.class);
				
		Mockito.when(contactService.addContact(12,contactResponse)).thenReturn(contactResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}
	
	@Test
	public void testGetContacts_WhenValidInput_AllContactsOfSpecificCustomerFetched() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact1 = new Contact();

		mockContact1.setContactId(25);
		mockContact1.setContactNumber(2556547);
		
		Contact mockContact2 = new Contact();

		mockContact2.setContactId(28);
		mockContact2.setContactNumber(6654785);
		
		List<Contact> contactList = new ArrayList<>();
		contactList.add(mockContact1);
		contactList.add(mockContact2);
		
		List<ContactDTO> contactResponse = contactList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
				.collect(Collectors.toList());
		
		Mockito.when(contactService.getContacts(12)).thenReturn(contactResponse);
		
		String URI = "/customers/12/contacts";
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = this.mapToJson(contactList);
		String outputInJson = result.getResponse().getContentAsString();
		Assertions.assertThat(outputInJson).isEqualTo(expectedJson);
		
	}
	
	@Test
	public void testGetContact_WhenValidInput_SingleContactOfSpecificCustomerFetched() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact = new Contact();

		mockContact.setContactId(25);
		mockContact.setContactNumber(2556547);
		
		String inputInJson = this.mapToJson(mockContact);
		
		String URI = "/customers/12/contacts/25";	
		
		// convert entity to DTO
		ContactDTO contactResponse = modelMapper.map(mockContact, ContactDTO.class);
				
		Mockito.when(contactService.getContact(12,25)).thenReturn(contactResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		Assertions.assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testUpdateContact_WhenValidInput_ContactNumberUpdated() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact = new Contact();

		mockContact.setContactId(25);
		mockContact.setContactNumber(2556547);
		
		String inputInJson = this.mapToJson(mockContact);
		
		String URI = "/customers/12/contacts/25";
		
		// convert entity to DTO
		ContactDTO contactResponse = modelMapper.map(mockContact, ContactDTO.class);
				
		Mockito.verify(contactService, times(1)).updateContact(12,25,contactResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testDeleteAllContactsOfSpecificCustomer_WhenValidInput_AllContactsDeleted() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact1 = new Contact();

		mockContact1.setContactId(25);
		mockContact1.setContactNumber(2556547);
		
		Contact mockContact2 = new Contact();

		mockContact2.setContactId(28);
		mockContact2.setContactNumber(6654785);
		
		List<Contact> contactList = new ArrayList<>();
		contactList.add(mockContact1);
		contactList.add(mockContact2);
		
		List<ContactDTO> contactResponse = contactList.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
				.collect(Collectors.toList());
		
		String expectedJson = this.mapToJson(contactResponse);
		
		String URI = "/customers/12/contacts";
		
		Mockito.verify(contactService, times(1)).deleteAllContactsOfSpecificCustomer(12);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(expectedJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testDeleteSpecificContactOfSpecificCustomer_WhenValidInput_ContactDeleted() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		Contact mockContact = new Contact();

		mockContact.setContactId(25);
		mockContact.setContactNumber(2556547);
		
		String inputInJson = this.mapToJson(mockContact);
		
		String URI = "/customers/12/contacts/25";
		
		Mockito.verify(contactService, times(1)).deleteSpecificContactOfSpecificCustomer(12,25);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}

}
