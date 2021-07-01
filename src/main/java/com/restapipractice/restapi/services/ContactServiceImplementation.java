package com.restapipractice.restapi.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.restapipractice.restapi.dao.CustomerContactDao;
import com.restapipractice.restapi.dao.CustomerDao;
import com.restapipractice.restapi.dto.ContactDTO;
import com.restapipractice.restapi.dto.CustomerDTO;
import com.restapipractice.restapi.entities.Contact;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.exception.ContactNotFoundException;
import com.restapipractice.restapi.exception.CustomerNotFoundException;

@Service
public class ContactServiceImplementation implements ContactService{
	
	private final CustomerContactDao customerContactDao;
	private final CustomerDao customerDao;
	private final ModelMapper modelMapper;
	
	
	public ContactServiceImplementation(CustomerContactDao customerContactDao, CustomerDao customerDao, ModelMapper modelMapper) {
		this.customerContactDao = customerContactDao;
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ContactDTO> getContacts(int customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContactDTO getContact(int customerId, int contactId) {
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
	
		if(customerDb.isPresent()) {
			
			Optional<Contact> contactDb = this.customerContactDao.findById(contactId);
			
			if(contactDb.isPresent()) {
				// convert entity to DTO
				ContactDTO contactResponse = modelMapper.map(contactDb, ContactDTO.class);
				return contactResponse;
			}else {
				throw new ContactNotFoundException(customerId);
			}
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public ContactDTO addContact(int contactId, ContactDTO contactDTO) {
		
	}

	@Override
	public ContactDTO updateContact(int customerId, int contactId, ContactDTO contactDTO) {
		
	}

	@Override
	public void deleteAllContactsOfSpecificCustomer(int customerId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSpecificContactOfSpecificCustomer(int customerId, int contactId) {
		// TODO Auto-generated method stub
		
	}

	

}
