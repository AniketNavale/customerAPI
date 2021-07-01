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

	private final CustomerDao customerDao; //TODO order private final
	private final ModelMapper modelMapper; 

	public CustomerServiceImplementation(CustomerDao customerDao,ModelMapper modelMapper) {
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<CustomerDTO> getCustomers() {
		
//		boolean cust = customerDao.findAll().isEmpty();
//		if(cust) {
//			List<Customer> customer = customerDao.findAll();
//			// convert entity to DTO
//			CustomerDTO customerResponse = modelMapper.map(customer, CustomerDTO.class);
//            return customerResponse;
//		}else {
//			throw new NoDataFoundException();
//		}
		
		List<Customer> customers = customerDao.findAll();
		if(customers.isEmpty()) {
			throw new NoDataFoundException();
		}else {
			
			// convert entity to DTO
			// for each customer in the list, map to customerDTO. Then create customerDTO list. And return customerDTO list
			//CustomerDTO customerResponse = modelMapper.map(customers, CustomerDTO.class);
			List<CustomerDTO> customerResponse = customers.stream().map(cust -> modelMapper.map(cust, CustomerDTO.class)).collect(Collectors.toList());
            return customerResponse;
			
			
		}
	}

	@Override
	public CustomerDTO getCustomer(int customerId) {
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		
		if(customerDb.isPresent()) {
			
			// convert entity to DTO
			CustomerDTO customerResponse = modelMapper.map(customerDb, CustomerDTO.class);
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
		
		// convert DTO to Entity
		Customer customerRequest = modelMapper.map(customerDTO, Customer.class);

		Optional<Customer> customerDb = this.customerDao.findById(customerRequest.getId());

		if (customerDb.isPresent()) {
			Customer customerUpdate = customerDb.get();
			customerUpdate.setId(customerRequest.getId());
			customerUpdate.setName(customerRequest.getName());
			customerUpdate.setAge(customerRequest.getAge());

			customerUpdate = customerDao.save(customerUpdate);
			
			// entity to DTO
			CustomerDTO customerResponse = modelMapper.map(customerUpdate, CustomerDTO.class);

			return customerResponse;
		} else {
			throw new CustomerNotFoundException(customerRequest.getId());
		}
	}

	@Override
	public void deleteCustomer(int customerId) {

		Optional<Customer> customerDb = this.customerDao.findById(customerId);

		if (customerDb.isPresent()) {
			this.customerDao.delete(customerDb.get());
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}
}
