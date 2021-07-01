package com.restapipractice.restapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.restapipractice.restapi.dao.CustomerAddressDao;
import com.restapipractice.restapi.dao.CustomerDao;
import com.restapipractice.restapi.dto.AddressDTO;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.exception.AddressNotFoundException;
import com.restapipractice.restapi.exception.CustomerNotFoundException;
import com.restapipractice.restapi.exception.NoDataFoundException;

@Service
public class AddressServiceImplementation implements AddressService{
	
	private final CustomerAddressDao customerAddressDao;
	private final CustomerDao customerDao;
	private final ModelMapper modelMapper;

	public AddressServiceImplementation(CustomerAddressDao customerAddressDao, CustomerDao customerDao, ModelMapper modelMapper) {
		this.customerAddressDao = customerAddressDao;
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public List<AddressDTO> getAddresses() {
		//return this.customerAddressDao.findAll();

		List<Address> addresses = customerAddressDao.findAll();
		if(addresses.isEmpty()) {
			throw new NoDataFoundException();
		}else {
			// convert entity to DTO
			//AddressDTO addressResponse = modelMapper.map(address, AddressDTO.class);
			List<AddressDTO> addressResponse = addresses.stream().map(add -> modelMapper.map(add, AddressDTO.class)).collect(Collectors.toList());
			return addressResponse;
		}
		
	}

	@Override
	public AddressDTO getAddress(int customerId) {    
		
			Optional<Address> address = this.customerAddressDao.findById(customerId);
			
			if (address.isPresent()) {
				
				// convert entity to DTO
				AddressDTO addressResponse = modelMapper.map(address, AddressDTO.class);
				return addressResponse;
			}else {
				throw new AddressNotFoundException(customerId);
			}
	}


	@Override
	public AddressDTO updateAddress(int customerId, AddressDTO addressDTO) { //TODO change input to address dto, return address
//		Optional<Customer> customerDb = this.customerDao.findById(customerId);
//
//		if (customerDb.isPresent()) {
//			Customer cust = customerDb.get();
//			cust.setAddress(address);
//			customerDao.save(cust);
//			
//			// entity to DTO
//			AddressDTO addressResponse = modelMapper.map(cust, AddressDTO.class);
//			return addressResponse;
//			
//			}else {
//			 throw new CustomerNotFoundException(customerId);                 
//		 }
		
		// convert DTO to Entity
		Address addressRequest = modelMapper.map(addressDTO, Address.class);
		
		Optional<Address> addressDb = this.customerAddressDao.findById(addressRequest.getCustomer_id());
		
		if (addressDb.isPresent()) {
			Address addressUpdate = addressDb.get();
			addressUpdate.setCustomer_id(addressRequest.getCustomer_id());
			addressUpdate.setCity(addressRequest.getCity());
			addressUpdate.setCountry(addressRequest.getCountry());
			addressUpdate.setPostal_code(addressRequest.getPostal_code());
			
			addressUpdate = customerAddressDao.save(addressUpdate);
			
			// entity to DTO
			AddressDTO addressResponse = modelMapper.map(addressUpdate, AddressDTO.class);
			return addressResponse;
		} else {
			throw new AddressNotFoundException(addressRequest.getCustomer_id());
		}
	}


	
	@Override
	public void deleteAddress(int customerId) { 
		Optional<Customer> customerDb = this.customerDao.findById(customerId); 

		if (customerDb.isPresent()) {
			Optional<Address> address = customerAddressDao.findById(customerId);
			  if (address.isPresent()) {
				customerAddressDao.delete(address.get());
				Customer cust = customerDb.get();
				cust.setAddress(null);
				customerDao.save(cust);
			}else {
				throw new AddressNotFoundException(customerId);
			}
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}
}


