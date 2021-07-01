package com.restapipractice.restapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.restapipractice.restapi.dao.CustomerContactDao;
import com.restapipractice.restapi.dao.CustomerDao;
import com.restapipractice.restapi.dto.AddressDTO;
import com.restapipractice.restapi.dto.ContactDTO;
import com.restapipractice.restapi.dto.CustomerDTO;
import com.restapipractice.restapi.entities.Address;
import com.restapipractice.restapi.entities.Contact;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.exception.ContactNotFoundException;
import com.restapipractice.restapi.exception.CustomerNotFoundException;
import com.restapipractice.restapi.exception.NoDataFoundException;

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
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		
		if(customerDb.isPresent()) {
			List<Contact> contacts = customerContactDao.findAll();
			if(contacts.isEmpty()) {
				throw new ContactNotFoundException(customerId);
			}else {
				
				// convert entity to DTO
				List<ContactDTO> contactResponse = customerDb.stream().map(con -> modelMapper.map(con, ContactDTO.class)).collect(Collectors.toList());
	            return contactResponse;
				
				
			}
		}else {
			throw new CustomerNotFoundException(customerId);
		}
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
	public ContactDTO addContact(int customerId, ContactDTO contactDTO) {
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		
		if(customerDb.isPresent()) {
			// convert DTO to entity
			Contact contactRequest = modelMapper.map(contactDTO, Contact.class);
			
			contactRequest = customerContactDao.save(contactRequest);
			
			// convert entity to DTO
			ContactDTO contactResponse = modelMapper.map(contactRequest,ContactDTO.class);
			
			return contactResponse;
		}else {
			throw new CustomerNotFoundException(customerId);
		}
		
	}

	@Override
	public ContactDTO updateContact(int customerId, int contactId, ContactDTO contactDTO) {
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		
		if(customerDb.isPresent()) {
			
			Optional<Contact> contactDb = this.customerContactDao.findById(contactId);
			
			
			if(contactDb.isPresent()) {
				

				// convert DTO to Entity
				Contact contactRequest = modelMapper.map(contactDTO, Contact.class);
				
				Optional<Contact> con = this.customerContactDao.findById(contactRequest.getContactId());
				
				Contact contactUpdate = con.get();
				contactUpdate.setContactId(contactRequest.getContactId());
				contactUpdate.setContactNumber(contactRequest.getContactNumber());
				
				contactUpdate = customerContactDao.save(contactUpdate);
				
				// entity to DTO
				ContactDTO contactResponse = modelMapper.map(contactUpdate, ContactDTO.class);
				return contactResponse;

				}else {
				throw new ContactNotFoundException(customerId);
			}
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public void deleteAllContactsOfSpecificCustomer(int customerId) {
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		
		if(customerDb.isPresent()) {
			
			List<Contact> allContacts = customerContactDao.findAll();
			
			List<Contact> specificContacts = allContacts.stream().filter().collect(Collectors.toList());
			
			
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public void deleteSpecificContactOfSpecificCustomer(int customerId, int contactId) {
		
		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		
		if(customerDb.isPresent()) {
			
			Optional<Contact> contactDb = this.customerContactDao.findById(contactId);
			
			if(contactDb.isPresent()) {
				
				customerContactDao.delete(contactDb.get());
				Customer cust = customerDb.get();
				cust.setContact(null);
				customerDao.save(cust);
				
			}else {
				throw new ContactNotFoundException(customerId);
			}
		}else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	

}
