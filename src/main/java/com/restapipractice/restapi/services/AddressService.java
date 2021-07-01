package com.restapipractice.restapi.services;

import java.util.List;
import java.util.Optional;

import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.dto.AddressDTO;
import com.restapipractice.restapi.entities.Address;

public interface AddressService {
	
	public List<AddressDTO> getAddresses();
	
	public AddressDTO getAddress(int customerId);
	
	//public Customer addAddress(int customerId, Address customerAddress);
	
	public AddressDTO updateAddress(int customerId, AddressDTO addressDTO);
	
    public void deleteAddress(int customerId);

}
