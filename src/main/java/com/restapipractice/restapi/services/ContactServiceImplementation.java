package com.restapipractice.restapi.services;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.restapipractice.restapi.dao.CustomerContactDao;
import com.restapipractice.restapi.dao.CustomerDao;
import com.restapipractice.restapi.dto.ContactDTO;
import com.restapipractice.restapi.entities.Contact;
import com.restapipractice.restapi.entities.Customer;
import com.restapipractice.restapi.exception.ContactNotFoundException;
import com.restapipractice.restapi.exception.CustomerNotFoundException;

@Service
public class ContactServiceImplementation implements ContactService {

	private final CustomerContactDao customerContactDao;
	private final CustomerDao customerDao;
	private final ModelMapper modelMapper;

	public ContactServiceImplementation(CustomerContactDao customerContactDao, CustomerDao customerDao,
			ModelMapper modelMapper) {
		this.customerContactDao = customerContactDao;
		this.customerDao = customerDao;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ContactDTO> getContacts(int customerId) {

		Optional<Customer> customerDb = this.customerDao.findById(customerId);

		if (customerDb.isPresent()) {
			List<Contact> contacts = customerContactDao.findByCustomerId(customerId); // TODO here fetch specific
																						// customer all contacts from
																						// DAO
			if (contacts.isEmpty()) { // make new method in DAO
				throw new ContactNotFoundException(customerId);
			} else {
				// convert entity to DTO
				List<ContactDTO> contactResponse = contacts.stream().map(con -> modelMapper.map(con, ContactDTO.class))
						.collect(Collectors.toList());
				return contactResponse;
			}
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public ContactDTO getContact(int customerId, int contactId) {

//		Optional<Customer> customerDb = this.customerDao.findById(customerId);
//	
//		if(customerDb.isPresent()) {
//			Optional<Contact> contactDb = this.customerContactDao.findById(contactId); //TODO call getContacts() and find contactId
//			if(contactDb.isPresent()) {
//				// convert entity to DTO
//				ContactDTO contactResponse = modelMapper.map(contactDb, ContactDTO.class);
//				return contactResponse;
//			}else {
//				throw new ContactNotFoundException(customerId);
//			}
//		}else {
//			throw new CustomerNotFoundException(customerId);
//		}

		Optional<Customer> customerDb = this.customerDao.findById(customerId);

		if (customerDb.isPresent()) {
			List<ContactDTO> allContactsOfThatCustomer = getContacts(customerId);
			// convert DTO to entity
			List<Contact> contactlist = allContactsOfThatCustomer.stream()
					.map(con -> modelMapper.map(con, Contact.class)).collect(Collectors.toList());
               //TODO remove dto to entity mapping, stream.filter.findfirst orelse throw exception
			// Iterator
			Iterator<Contact> it = contactlist.iterator();
			while (it.hasNext()) {

			}

		}
	}

	@Override
	public ContactDTO addContact(int customerId, ContactDTO contactDTO) {

		Optional<Customer> customerDb = this.customerDao.findById(customerId);

		if (customerDb.isPresent()) {
			// convert DTO to entity
			Contact contactRequest = modelMapper.map(contactDTO, Contact.class);// TODO set customerId
            contactRequest.setCustomer(customerDb.get());
			contactRequest = customerContactDao.save(contactRequest);

			// convert entity to DTO
			ContactDTO contactResponse = modelMapper.map(contactRequest, ContactDTO.class);
			return contactResponse;
		} else {
			throw new CustomerNotFoundException(customerId);
		}

	}

	@Override
	public ContactDTO updateContact(int customerId, int contactId, ContactDTO contactDTO) {

		Optional<Customer> customerDb = this.customerDao.findById(customerId);

		if (customerDb.isPresent()) {
			Optional<Contact> contactDb = this.customerContactDao.findById(contactId); // TODO call getContacts()

			if (contactDb.isPresent()) {
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
			} else {
				throw new ContactNotFoundException(customerId);
			}
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public void deleteAllContactsOfSpecificCustomer(int customerId) {
		Optional<Customer> customerDb = this.customerDao.findById(customerId);// TODO variable name
		if (customerDb.isPresent()) {
			customerContactDao.deleteByCustomerId(customerId);
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public void deleteSpecificContactOfSpecificCustomer(int customerId, int contactId) {

		Optional<Customer> customerDb = this.customerDao.findById(customerId);
		if (customerDb.isPresent()) {
			Optional<Contact> contactDb = this.customerContactDao.findById(contactId); 

			if (contactDb.isPresent()) {//TODO check whether the contact is mapped to that customerID only 

				customerContactDao.delete(contactDb.get());
				Customer cust = customerDb.get();
				cust.setContact(null);
				customerDao.save(cust);

			} else {
				throw new ContactNotFoundException(customerId);
			}
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

}
