package com.restapipractice.restapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
		
		Mockito.when(addressService.updateAddress(20,addressResponse).thenReturn(addressResponse);
		
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
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	
}
