package com.restapipractice.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapipractice.restapi.dto.CustomerDTO;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.services.CustomerService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class CustomerControllerTest {
	
	// logging - troubleshooting logs, log rotation 
	// transaction management
	// java 11 OCP certification
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private CustomerController customerController;
	
	
	
	@Test
	public void testAddCustomer_WhenValidInput_CustomerAdded() throws Exception {
		
		Customer mockCustomer = new Customer();

		mockCustomer.setId(12);
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(30);
		
		String inputInJson = this.mapToJson(mockCustomer);
		
		String URI = "/customers";
		

		// convert entity to DTO
		CustomerDTO customerResponse = modelMapper.map(mockCustomer, CustomerDTO.class);
		
		Mockito.when(customerService.addCustomer(Mockito.any(CustomerDTO.class))).thenReturn(customerResponse);
		
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
	public void testAddCustomer_WhenInvalidCustomerName_ThenErrorMessage() throws Exception {
		// input name invalid - proper error message returned
		Customer mockCustomer = new Customer();
		
		mockCustomer.setName(null);
		mockCustomer.setAge(30);
		
		String inputInJson = this.mapToJson(mockCustomer);
		
		String URI = "/customers";
		
		// convert entity to DTO
		CustomerDTO customerResponse = modelMapper.map(mockCustomer, CustomerDTO.class);
				
		Mockito.when(customerService.addCustomer(Mockito.any(CustomerDTO.class))).thenReturn(customerResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	
	
	
	//TODO remove id
	@Test
	public void testAddCustomer_WhenInvalidCustomerAge_ThenErrorMessage() throws Exception {
		// input name invalid - proper error message returned
		Customer mockCustomer = new Customer();
		
		mockCustomer.setName("Ashish");
		mockCustomer.setAge(180);
		
		String inputInJson = this.mapToJson(mockCustomer);
		
		String URI = "/customers";
		
		// convert entity to DTO
		CustomerDTO customerResponse = modelMapper.map(mockCustomer, CustomerDTO.class);
				
		Mockito.when(customerService.addCustomer(Mockito.any(CustomerDTO.class))).thenReturn(customerResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	
	
	@Test
	public void testGetCustomers_WhenValidInput_AllCustomersFetched() throws Exception {
		
		Customer mockCustomer1 = new Customer();
		
		mockCustomer1.setName("Akshay");
		mockCustomer1.setAge(30);
		
		Customer mockCustomer2 = new Customer();
		
		mockCustomer2.setName("Suresh");
		mockCustomer2.setAge(32);
		
		List<Customer> customerList = new ArrayList<>();
		customerList.add(mockCustomer1);
		customerList.add(mockCustomer2);
		
		List<CustomerDTO> customerResponse = customerList.stream().map(cust -> modelMapper.map(cust, CustomerDTO.class))
				.collect(Collectors.toList());
		
		Mockito.when(customerService.getCustomers()).thenReturn(customerResponse);
		
		String URI = "/customers";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = this.mapToJson(customerList);
		String outputInJson = result.getResponse().getContentAsString();
		Assertions.assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetCustomer_WhenValidInput_SingleCustomersFetched() throws Exception {
		
		Customer mockCustomer = new Customer();
		
		mockCustomer.setId(10);
		mockCustomer.setName("Satish");
		mockCustomer.setAge(45);
		
		String inputInJson = this.mapToJson(mockCustomer);
		
		String URI = "/customers/10";
		
		// convert entity to DTO
		CustomerDTO customerResponse = modelMapper.map(mockCustomer, CustomerDTO.class);
		
		Mockito.when(customerService.getCustomer(10)).thenReturn(customerResponse);
		
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
	public void testUpdateCustomer_WhenValidInput_CustomerUpdated() throws Exception {
		
		Customer mockCustomer = new Customer();
		
		mockCustomer.setId(15);
		mockCustomer.setName("Aakash");
		mockCustomer.setAge(32);
		
		String inputInJson = this.mapToJson(mockCustomer);
		
		String URI = "/customers/15";
		
		// convert entity to DTO
		CustomerDTO customerResponse = modelMapper.map(mockCustomer, CustomerDTO.class);
		
		Mockito.verify(customerService, times(1)).updateCustomer(15, customerResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
	}
	
	
	@Test
	public void testDeleteCustomer_WhenValidInput_CustomerDeleted() throws Exception {
		
		Customer mockCustomer = new Customer();
		
		mockCustomer.setId(15);
		mockCustomer.setName("Aakash");
		mockCustomer.setAge(32);
		
		String inputInJson = this.mapToJson(mockCustomer);
		
		String URI = "/customers/15";
		
		Mockito.verify(customerService, times(1)).deleteCustomer(15);
		
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
