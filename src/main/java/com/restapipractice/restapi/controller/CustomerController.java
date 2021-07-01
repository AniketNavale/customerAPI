package com.restapipractice.restapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restapipractice.restapi.dto.CustomerDTO;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.services.CustomerService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


@RestController
public class CustomerController {
	
	//Logger
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	private CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	/**
	 * GET method to get details of all the customer.
	 * 
	 * @param No parameters
	 * @return  returns all customers present
	 * @author Aniket Navale
	 */ 
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDTO>> getCustomers(){
		return ResponseEntity.ok().body(customerService.getCustomers());
	}
	
//	@GetMapping("/customers")
//	public List<CustomerDTO> getCustomers() {
//
//		return customerService.getCustomers().stream().map(customer -> modelMapper.map(customer, CustomerDTO.class))
//				.collect(Collectors.toList());
//	}
	
	/**
	 * GET method to get details of the customer whose Id is passed as Request Param.(Ex: /customers/{customerId} )
	 * 
	 * @param customer Id
	 * @return only one customer with specified customer Id
	 * @author Aniket Navale
	 */
	@GetMapping("/customers/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable int customerId){
		return ResponseEntity.ok().body(customerService.getCustomer(customerId));
	}
	
	
	/**
	 * POST method to add a customer and return the customer id generated for the customer.
	 * 
	 * @param customer object
	 * @return newly added customer
	 * @author Aniket Navale
	 */
	@PostMapping("/customers")
	public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerDTO customerDTO){
		return ResponseEntity.ok().body(this.customerService.addCustomer(customerDTO));
	}
	
	
	/**
	 * PUT method to update details of a given customer specified by customer id in request param.
	 * 
	 * @param customer object
	 * @return newly updated customer
	 * @author Aniket Navale
	 */
	@PutMapping("/customers/{customerId}")
	public ResponseEntity<CustomerDTO> updateCustomer(@Valid @PathVariable int customerId, @RequestBody CustomerDTO customerDTO){
		return ResponseEntity.ok().body(this.customerService.updateCustomer(customerId, customerDTO));
	}
	
	
	/**
	 * Delete method to delete a customer , given the customer id.
	 * 
	 * @param customer Id
	 * @return OK
	 * @author Aniket Navale
	 */
	@DeleteMapping("/customers/{customerId}")
	public HttpStatus deleteCustomer(@PathVariable int customerId){
		this.customerService.deleteCustomer(customerId);
		return HttpStatus.OK;
	}
}