package com.restapipractice.restapi.services;

import java.util.List;
import com.restapipractice.restapi.dto.ContactDTO;

public interface ContactService {
	
	public List<ContactDTO> getContacts(int customerId);
	
	public ContactDTO getContact(int customerId, int contactId);
	
	public ContactDTO addContact(int customerId, ContactDTO contactDTO);
	
	public ContactDTO updateContact(int customerId, int contactId, ContactDTO contactDTO);
	
	public void deleteAllContactsOfSpecificCustomer(int customerId);
	
    public void deleteSpecificContactOfSpecificCustomer(int customerId, int contactId);

}
