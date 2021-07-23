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
		List<Address> addresses = customerAddressDao.findAll();
		if(addresses.isEmpty()) {
			throw new NoDataFoundException();
		}else {
			// convert entity to DTO
			List<AddressDTO> addressResponse = addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class))
					.collect(Collectors.toList());
			return addressResponse;
		}
		
	}

	@Override
	public AddressDTO getAddress(int customerId) {    
		Optional<Address> optionalOfAddress = this.customerAddressDao.findById(customerId);
		if (optionalOfAddress.isPresent()) {
//			// convert entity to DTO
//			AddressDTO addressResponse = modelMapper.map(optionalOfAddress, AddressDTO.class);
//			return addressResponse;
			
			List<AddressDTO> allAddresses = getAddresses();
			return allAddresses.stream().filter(address -> address.getCustomer_id() == customerId).findFirst()
					.orElseThrow(() -> new AddressNotFoundException(customerId));
		}else {
			throw new AddressNotFoundException(customerId);
		}
	}
	
	@Override
	public AddressDTO addAddress(int customerId, AddressDTO addressDTO) {

		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			// convert DTO to entity
			Address addressRequest = modelMapper.map(addressDTO, Address.class);
			addressRequest.setCustomer(optionalOfCustomer.get());
			addressRequest = customerAddressDao.save(addressRequest);

			// convert entity to DTO
			AddressDTO addressResponse = modelMapper.map(addressRequest, AddressDTO.class);
			return addressResponse;
		} else {
			throw new CustomerNotFoundException(customerId);
		}

	}


	@Override
	public AddressDTO updateAddress(int customerId, AddressDTO addressDTO) { 

//		// convert DTO to Entity
//		Address addressRequest = modelMapper.map(addressDTO, Address.class);
//		
//		Optional<Address> optionalOfAddress = this.customerAddressDao.findById(addressRequest.getCustomer_id());
//		
//		
//		if (optionalOfAddress.isPresent()) {
//			Address addressUpdate = optionalOfAddress.get();
//			addressUpdate.setCustomer_id(addressRequest.getCustomer_id());
//			addressUpdate.setCity(addressRequest.getCity());
//			addressUpdate.setCountry(addressRequest.getCountry());
//			addressUpdate.setPostal_code(addressRequest.getPostal_code());
//			addressUpdate = customerAddressDao.save(addressUpdate);
//			
//			// entity to DTO
//			AddressDTO addressResponse = modelMapper.map(addressUpdate, AddressDTO.class);
//			return addressResponse;
//		} else {
//			throw new AddressNotFoundException(addressRequest.getCustomer_id());
//		}
		
		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			Optional<Address> optionalOfAddress = this.customerAddressDao.findById(customerId);
			if (optionalOfAddress.isPresent()) {
				
				// convert DTO to Entity
				Address addressRequest = modelMapper.map(addressDTO, Address.class);
				
				Address addressUpdate = optionalOfAddress.get();
				addressUpdate.setCustomer_id(addressRequest.getCustomer_id());
				addressUpdate.setCity(addressRequest.getCity());
				addressUpdate.setCountry(addressRequest.getCountry());
				addressUpdate.setPostal_code(addressRequest.getPostal_code());
				addressUpdate = customerAddressDao.save(addressUpdate);
				
				// entity to DTO
				AddressDTO addressResponse = modelMapper.map(addressUpdate, AddressDTO.class);
				return addressResponse;
			}else {
				throw new AddressNotFoundException(customerId);
			}
			
		}else {
			throw new CustomerNotFoundException(customerId);
		}
		
	}

	@Override
	public void deleteAddress(int customerId) { 
		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId); 

		if (optionalOfCustomer.isPresent()) {
			Optional<Address> optionalOfAddress = customerAddressDao.findById(customerId);
			  if (optionalOfAddress.isPresent()) {
				customerAddressDao.delete(optionalOfAddress.get());
				Customer customer = optionalOfCustomer.get();
				customer.setAddress(null);
				customerDao.save(customer);
			}else {
				throw new AddressNotFoundException(customerId);
			}
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}
}


