package com.restapipractice.restapi.services;

import java.util.List;
import com.restapipractice.restapi.dto.AddressDTO;

public interface AddressService {
	
	public List<AddressDTO> getAddresses();
	
	public AddressDTO getAddress(int customerId);
	
	public AddressDTO addAddress(int customerId, AddressDTO addressDTO);
	
	public AddressDTO updateAddress(int customerId, AddressDTO addressDTO);
	
    public void deleteAddress(int customerId);

}
