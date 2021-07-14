package com.restapipractice.restapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.restapipractice.restapi.dao.CustomerDao;
import com.restapipractice.restapi.dto.CustomerDTO;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.exception.CustomerNotFoundException;
import com.restapipractice.restapi.exception.NoDataFoundException;

@Service
public class CustomerServiceImplementation implements CustomerService {

	private final CustomerDao customerDao; 
	private final ModelMapper modelMapper; 

	public CustomerServiceImplementation(CustomerDao customerDao,ModelMapper modelMapper) {
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<CustomerDTO> getCustomers() {
		List<Customer> customers = customerDao.findAll();
		if(customers.isEmpty()) {
			throw new NoDataFoundException();
		}else {
			// convert entity to DTO
			List<CustomerDTO> customerResponse = customers.stream().map(customer -> modelMapper.map(customer, CustomerDTO.class))
					.collect(Collectors.toList());
            return customerResponse;
		}
	}

	@Override
	public CustomerDTO getCustomer(int customerId) {
		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);
		if(optionalOfCustomer.isPresent()) {
			// convert entity to DTO
			CustomerDTO customerResponse = modelMapper.map(optionalOfCustomer, CustomerDTO.class);
			return customerResponse;
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public CustomerDTO addCustomer(CustomerDTO customerDTO) {
	
		// TODO check error handling
		// convert DTO to entity
		Customer customerRequest = modelMapper.map(customerDTO, Customer.class);
		
		customerRequest = customerDao.save(customerRequest);
		// convert entity to DTO
		CustomerDTO customerResponse = modelMapper.map(customerRequest,CustomerDTO.class);
		
		return customerResponse;
	}

	@Override
	public CustomerDTO updateCustomer(int customerId, CustomerDTO customerDTO) {
		
		
		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			// convert DTO to Entity
			Customer customerRequest = modelMapper.map(customerDTO, Customer.class);
			
			Customer customerUpdate = optionalOfCustomer.get();
			customerUpdate.setId(customerRequest.getId());
			customerUpdate.setName(customerRequest.getName());
			customerUpdate.setAge(customerRequest.getAge());
			customerUpdate = customerDao.save(customerUpdate);
			
			// entity to DTO
			CustomerDTO customerResponse = modelMapper.map(customerUpdate, CustomerDTO.class);

			return customerResponse;
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public void deleteCustomer(int customerId) {

		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			this.customerDao.delete(optionalOfCustomer.get());
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}
}
