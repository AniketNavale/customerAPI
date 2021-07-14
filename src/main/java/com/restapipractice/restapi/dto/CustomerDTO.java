package com.restapipractice.restapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


public class CustomerDTO {
	
	private int customer_id;
	
	@NotBlank(message="No null name allowed")
	private String name;
	
	@Min(5)
	@Max(110)
	private int age; 
	
	
	
	
	public CustomerDTO(int customer_id, String name, int age) {
		super();
		this.customer_id = customer_id;
		this.name = name;
		this.age = age;
	}
	
	

	public CustomerDTO() {
		super();
	
	}



	public int getId() {
		return customer_id;
	}

	public void setId(int customer_id) {
		this.customer_id = customer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", name=" + name + ", age=" + age + "]";
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
		CustomerDTO other = (CustomerDTO) obj;
		if (customer_id != other.customer_id)
			return false;
		return true;
	}
	
//	public Address getAddress() {
//		return address;
//	}
//	
//	public void setAddress(Address address) {
//		this.address = address;
//	}
//	
//	public List<Contact> getContact() {
//		return contact;
//	}
//	public void setContact(List<Contact> contact) {
//		this.contact = contact;
//	}

	

}
