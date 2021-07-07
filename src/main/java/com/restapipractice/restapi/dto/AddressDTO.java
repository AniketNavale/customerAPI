package com.restapipractice.restapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.entities.Customer;

import lombok.Data;


public class AddressDTO {
	
	private int customer_id;
	
	@NotBlank(message="No null city allowed")
	private String city;
	
	@NotBlank(message="No null country allowed")
	private String country;
	
	@Min(1)
	@Max(999999)
	private int postal_code;

	public AddressDTO() {
		super();
	}

	public AddressDTO(int customer_id, String city, String country, int postal_code) {
		super();
		this.customer_id = customer_id;
		this.city = city;
		this.country = country;
		this.postal_code = postal_code;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}


	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public int getPostal_code() {
		return postal_code;
	}


	public void setPostal_code(int postal_code) {
		this.postal_code = postal_code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customer_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressDTO other = (AddressDTO) obj;
		if (customer_id != other.customer_id)
			return false;
		return true;
	}
	

//	public Customer getCustomer() {
//		return customer;
//	}
//
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}


	
	

	
	
}
