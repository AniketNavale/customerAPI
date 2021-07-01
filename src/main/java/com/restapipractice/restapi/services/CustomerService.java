package com.restapipractice.restapi.services;


import java.util.List;

import com.restapipractice.restapi.dto.CustomerDTO;

public interface CustomerService {
	
	public List<CustomerDTO> getCustomers();
	
	public CustomerDTO getCustomer(int customerId);
	
	public CustomerDTO addCustomer(CustomerDTO customerDTO);
	
	public CustomerDTO updateCustomer(int customerId, CustomerDTO customerDTO);
	
    public void deleteCustomer(int customerId);
	
}


















//public Optional<Customer> getCustomer(int customerId);
//public Customer deleteCustomer(int customerId);