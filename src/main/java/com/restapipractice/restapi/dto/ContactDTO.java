package com.restapipractice.restapi.dto;

import javax.validation.constraints.NotBlank;


public class ContactDTO {
	
	private int contactId;
	
	@NotBlank(message="No null contact number allowed")
	private long contactNumber;
	
	public ContactDTO() {
		
	}

	public ContactDTO(int contactId, long contactNumber) {
		this.contactId = contactId;
		this.contactNumber = contactNumber;
	}
	
	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(long contactNumber) {
		this.contactNumber = contactNumber;
	}

	@Override
	public String toString() {
		return "Contact [contactId=" + contactId + ", contactNumber=" + contactNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contactId;
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
		ContactDTO other = (ContactDTO) obj;
		if (contactId != other.contactId)
			return false;
		return true;
	}
	
    
	
	
	

}
