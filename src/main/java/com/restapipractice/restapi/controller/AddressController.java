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
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.services.AddressService;

@RestController
public class AddressController {
	
	
	private AddressService addressService;

	public AddressController(AddressService addressService) {
		this.addressService = addressService;
	}
	
	@GetMapping("/addresses")
	public ResponseEntity<List<AddressDTO>> getAddresses(){
		return ResponseEntity.ok().body(addressService.getAddresses());
	}
	
	@GetMapping("/customers/{customerId}/addresses")
	public ResponseEntity<AddressDTO> getAddress(@PathVariable int customerId){
		return ResponseEntity.ok().body(addressService.getAddress(customerId));
	}
	
	@PostMapping("/customers/{customerId}/addresses")
	public ResponseEntity<AddressDTO> addAddress(@PathVariable int customerId, @RequestBody AddressDTO addressDTO){
		return ResponseEntity.ok().body(this.addressService.updateAddress(customerId, addressDTO));
	}
	
	
	@PutMapping("/customers/{customerId}/addresses")
	public ResponseEntity<AddressDTO> updateAddress(@PathVariable int customerId, @RequestBody AddressDTO addressDTO){
		return ResponseEntity.ok().body(this.addressService.updateAddress(customerId, addressDTO));
	}
	
	
	@DeleteMapping("/customers/{customerId}/addresses")
	public HttpStatus deleteAddress(@PathVariable int customerId){
		this.addressService.deleteAddress(customerId);
		return HttpStatus.OK;
	}

}
