package com.restapipractice.restapi.services;

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

		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			List<Contact> contacts = customerContactDao.findContactsByCustomerId(customerId); //custom query from contactDao
			if (contacts.isEmpty()) {
				throw new ContactNotFoundException(customerId);
			} else {
				// convert entity to DTO
				List<ContactDTO> contactResponse = contacts.stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
						.collect(Collectors.toList());
				return contactResponse;
			}
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public ContactDTO getContact(int customerId, int contactId) {
		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);
		if (optionalOfCustomer.isPresent()) {
			List<ContactDTO> allContactsOfThatCustomer = getContacts(customerId);
			return allContactsOfThatCustomer.stream().filter(contact -> contact.getContactId() == contactId).findFirst()
					.orElseThrow(() -> new ContactNotFoundException(customerId));
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public ContactDTO addContact(int customerId, ContactDTO contactDTO) {

		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			// convert DTO to entity
			Contact contactRequest = modelMapper.map(contactDTO, Contact.class);// TODO set customerId
			contactRequest.setCustomer(optionalOfCustomer.get());
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

		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);

		if (optionalOfCustomer.isPresent()) {
			Optional<Contact> optionalOfcontact = this.customerContactDao.findById(contactId);
			
			// convert DTO to Entity
			Contact contactRequest = modelMapper.map(contactDTO, Contact.class);

			if (optionalOfcontact.isPresent() && optionalOfcontact.get().getCustomer().getId() == customerId) {
				//TODO add check, inside of optionalofcontact, customerId same as input customerID
				
				Contact contactUpdate = optionalOfcontact.get();
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
		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);
		if (optionalOfCustomer.isPresent()) {
			customerContactDao.deleteByCustomerId(customerId); //custom query from contactDao
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}

	@Override
	public void deleteSpecificContactOfSpecificCustomer(int customerId, int contactId) {

		Optional<Customer> optionalOfCustomer = this.customerDao.findById(customerId);
		if (optionalOfCustomer.isPresent()) {
			Optional<Contact> optionalOfcontact = this.customerContactDao.findById(contactId);

			if (optionalOfcontact.isPresent()) {// TODO check whether the contact is mapped to that customerID only
				
				if(optionalOfcontact.get().getCustomer().getId() == customerId) {
					customerContactDao.delete(optionalOfcontact.get());
					Customer customer = optionalOfCustomer.get();
					customer.setContact(null);
					customerDao.save(customer);
				}
			} else {
				throw new ContactNotFoundException(customerId);
			}
		} else {
			throw new CustomerNotFoundException(customerId);
		}
	}
}