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

import com.restapipractice.restapi.dao.CustomerAddressDao;
import com.restapipractice.restapi.entities.Address;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceImplementationTest {
	
	@Autowired
	private AddressService addressService;
	
	@MockBean
	private CustomerAddressDao customerAddressDao;
	
	@Test
	@DisplayName("Fetching all addresses from database")
	public void test_DisplayAllAddresses() {
		when(customerAddressDao.findAll()).thenReturn(Stream.of(new Address(1,"Mumbai","India",400052), new Address(2,"Delhi","India",452145))
				.collect(Collectors.toList()));
		assertEquals(2,addressService.getAddresses().size());
	}
	
	@Test
	@DisplayName("Fetching a single address from database")
	public void test_DisplaySpecificAddress() {
		Optional<Address> address = Optional.of(new Address(1,"Mumbai","India",400052));
		when(customerAddressDao.findById(1)).thenReturn(address);
		assertEquals(address, customerAddressDao.findById(1));
	}
	
	@Test
	@DisplayName("Adding a new address in database")
	public void test_AddAddress_addNewAddressInDatabase() {
//		Address address = new Address(1,"Mumbai","India",400052);
//		when(customerAddressDao.save(address)).thenReturn(address);
//		assertEquals(address, addressService.updateAddress(1,address)); 
//		Mockito.verify(customerAddressDao,times(1)).save(address);  
		
		Address address = new Address(1,"Mumbai","India",400052);
		customerAddressDao.save(address);
		ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class); 
		Mockito.verify(customerAddressDao, times(1)).save(addressCaptor.capture());
	}
	
	@Test
	@DisplayName("Updating existing customer in database")
	public void test_UpdateAddress_updateExistingAddressInDatabase() {
		Address address = new Address(1,"Mumbai","India",400052);
		customerAddressDao.save(address);
		ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class); 
		Mockito.verify(customerAddressDao, times(1)).save(addressCaptor.capture());
		assertEquals(1, addressCaptor.getValue().getCustomer_id());
		assertEquals("Mumbai",addressCaptor.getValue().getCity());
		assertEquals("India",addressCaptor.getValue().getCountry());
		assertEquals(400052,addressCaptor.getValue(). getPostal_code());
	}
	
	@Test
	@DisplayName("Deleting address from the database if it is present")
	public void test_DeleteAddress_ifAddressIsPresentInDatabase() {
		Optional<Address> address = Optional.of(new Address(1,"Mumbai","India",400052));
		when(customerAddressDao.findById(1)).thenReturn(address);
		addressService.deleteAddress(1);                 
		ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
		Mockito.verify(customerAddressDao, times(1)).delete(addressCaptor.capture());
		assertEquals(1, addressCaptor.getValue().getCustomer_id());
		assertEquals("Mumbai",addressCaptor.getValue().getCity());
		assertEquals("India",addressCaptor.getValue().getCountry());
		assertEquals(400052,addressCaptor.getValue(). getPostal_code());
		
	}
	
	

}
