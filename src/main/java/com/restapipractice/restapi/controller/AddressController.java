package com.restapipractice.restapi.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restapipractice.restapi.dto.AddressDTO;
import com.restapipractice.restapi.services.AddressService;

@RestController
public class AddressController {
	
	
	private AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	/**
	 * GET method to get all addresses of customer.
	 * 
	 * @param No parameters
	 * @return  returns all addresses present
	 * @author Aniket Navale
	 */ 
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAddresses(){
		return ResponseEntity.ok().body(addressService.getAddresses());
	}
	
	/**
	 * GET method to get address of single customer whose customerId is passed
	 * 
	 * @param customer Id
	 * @return only one address with specified customer Id
	 * @author Aniket Navale
	 */
	@GetMapping("/customers/{customerId}/addresses")
	public ResponseEntity<AddressDTO> getAddress(@PathVariable int customerId){
		return ResponseEntity.ok().body(addressService.getAddress(customerId));
	}
	
	/**
	 * POST method to add a new address of existing customer
	 * 
	 * @param customerId
	 * @param AddressDTO
	 * @return newly added address
	 * @author Aniket Navale
	 */
	@PostMapping("/customers/{customerId}/addresses")
	public ResponseEntity<AddressDTO> addAddress(@Valid @PathVariable int customerId, @RequestBody AddressDTO addressDTO){
		return ResponseEntity.ok().body(this.addressService.addAddress(customerId, addressDTO));
	}
	
	/**
	 * PUT method to update details of existing address
	 * 
	 * @param customerId
	 * @param AddressDTO
	 * @return newly updated address
	 * @author Aniket Navale
	 */
	@PutMapping("/customers/{customerId}/addresses")
	public ResponseEntity<AddressDTO> updateAddress(@Valid @PathVariable int customerId, @RequestBody AddressDTO addressDTO){
		return ResponseEntity.ok().body(this.addressService.updateAddress(customerId, addressDTO));
	}
	
	/**
	 * Delete method to delete an existing address
	 * 
	 * @param customer Id
	 * @return OK
	 * @author Aniket Navale
	 */
	@DeleteMapping("/customers/{customerId}/addresses")
	public HttpStatus deleteAddress(@PathVariable int customerId){
		this.addressService.deleteAddress(customerId);
		return HttpStatus.OK;
	}

}
