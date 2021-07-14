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
import com.restapipractice.restapi.dto.CustomerDTO;
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.services.AddressService;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class AddressControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@MockBean
	private AddressService addressService;
	
	@Test
	public void testAddAddress_WhenValidInput_AddressAdded() throws Exception {
		
		Address mockAddress = new Address();

		mockAddress.setCustomer_id(20);
		mockAddress.setCity("London");
		mockAddress.setCountry("UK");
		mockAddress.setPostal_code(254568);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/20/addresses";
		

		// convert entity to DTO
		AddressDTO addressResponse = modelMapper.map(mockAddress, AddressDTO.class);
		
		Mockito.when(addressService.addAddress(20,addressResponse).thenReturn(addressResponse);
		
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
	public void testAddAddress_WhenInvalidCityName_ThenErrorMessage() throws Exception {
		
		Address mockAddress = new Address();

		mockAddress.setCustomer_id(20);
		mockAddress.setCity(null);
		mockAddress.setCountry("UK");
		mockAddress.setPostal_code(254568);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/20/addresses";
		
		// convert entity to DTO
		AddressDTO addressResponse = modelMapper.map(mockAddress, AddressDTO.class);
				
		Mockito.when(addressService.addAddress(20,addressResponse).thenReturn(addressResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	
	@Test
	public void testAddAddress_WhenInvalidCountryName_ThenErrorMessage() throws Exception {
		
		Address mockAddress = new Address();

		mockAddress.setCustomer_id(20);
		mockAddress.setCity("London");
		mockAddress.setCountry(null);
		mockAddress.setPostal_code(254568);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/20/addresses";
		
		// convert entity to DTO
		AddressDTO addressResponse = modelMapper.map(mockAddress, AddressDTO.class);
				
		Mockito.when(addressService.addAddress(20,addressResponse).thenReturn(addressResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void testAddAddress_WhenInvalidPostalCode_ThenErrorMessage() throws Exception {
		
		Address mockAddress = new Address();

		mockAddress.setCustomer_id(20);
		mockAddress.setCity("London");
		mockAddress.setCountry("UK");
		mockAddress.setPostal_code(2545682);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/20/addresses";
		
		// convert entity to DTO
		AddressDTO addressResponse = modelMapper.map(mockAddress, AddressDTO.class);
				
		Mockito.when(addressService.addAddress(20,addressResponse).thenReturn(addressResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		
	}
	
	@Test
	public void testGetAddresses_WhenValidInput_AllAddressesFetched() throws Exception {
		
		Address mockAddress1 = new Address();
		
		mockAddress1.setCity("London");
		mockAddress1.setCountry("UK");
		mockAddress1.setPostal_code(254568);
		
		Address mockAddress2 = new Address();
		
		mockAddress2.setCity("Tokyo");
		mockAddress2.setCountry("Japan");
		mockAddress2.setPostal_code(545562);
		
		List<Address> addressList = new ArrayList<>();
		addressList.add(mockAddress1);
		addressList.add(mockAddress2);
		
		List<AddressDTO> addressResponse = addressList.stream().map(address -> modelMapper.map(address, AddressDTO.class))
				.collect(Collectors.toList());
		
		Mockito.when(addressService.getAddresses()).thenReturn(addressResponse);
		
		String URI = "/addresses";
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		String expectedJson = this.mapToJson(addressList);
		String outputInJson = result.getResponse().getContentAsString();
		Assertions.assertThat(outputInJson).isEqualTo(expectedJson);
	}
	
	@Test
	public void testGetAddress_WhenValidInput_SingleAddressFetched() throws Exception {
		
		Address mockAddress = new Address();
		
		mockAddress.setCustomer_id(10);
		mockAddress.setCity("London");
		mockAddress.setCountry("UK");
		mockAddress.setPostal_code(254568);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/10/addresses";
		
		// convert entity to DTO
		AddressDTO addressResponse = modelMapper.map(mockAddress, AddressDTO.class);
		
		Mockito.when(addressService.getAddress(10)).thenReturn(addressResponse);
		
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
	public void testUpdateAddress_WhenValidInput_AddressUpdated() throws Exception {
		
		Address mockAddress = new Address();
		
		mockAddress.setCustomer_id(10);
		mockAddress.setCity("London");
		mockAddress.setCountry("UK");
		mockAddress.setPostal_code(254568);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/10/addresses";
		
		// convert entity to DTO
		AddressDTO addressResponse = modelMapper.map(mockAddress, AddressDTO.class);
		
		Mockito.verify(addressService, times(1)).updateAddress(15, addressResponse);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URI)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
	}
	
	@Test
	public void testDeleteAddress_WhenValidInput_CustomerDeleted() throws Exception {
		
		Address mockAddress = new Address();
		
		mockAddress.setCustomer_id(10);
		mockAddress.setCity("London");
		mockAddress.setCountry("UK");
		mockAddress.setPostal_code(254568);
		
		String inputInJson = this.mapToJson(mockAddress);
		
		String URI = "/customers/10/addresses";
		
		Mockito.verify(addressService, times(1)).deleteAddress(10);
		
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
