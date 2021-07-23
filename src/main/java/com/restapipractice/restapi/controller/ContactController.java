package com.restapipractice.restapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.restapipractice.restapi.dto.ContactDTO;
import com.restapipractice.restapi.services.ContactService;

@RestController
public class ContactController {
	
	private ContactService contactService;
	
	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}
	
	/**
	 * GET method to get all contacts of specific customer.
	 * 
	 * @param customerId
	 * @return  returns all contacts of that customer 
	 * @author Aniket Navale
	 */ 
	@GetMapping("/customers/{customerId}/contacts")
	public ResponseEntity<List<ContactDTO>> getContacts(@PathVariable int customerId){
		return ResponseEntity.ok().body(contactService.getContacts(customerId));
	}
	
	/**
	 * GET method to get specific contact of specific customer
	 * 
	 * @param customerId
	 * @param contactId
	 * @return only one contact with specified customerId and contactId
	 * @author Aniket Navale
	 */
	@GetMapping("/customers/{customerId}/contacts/{contactId}")
	public ResponseEntity <ContactDTO> getContact(@PathVariable int customerId, @PathVariable int contactId){
		return ResponseEntity.ok().body(contactService.getContact(customerId, contactId));
	}
	
	/**
	 * POST method to add a new contact of existing customer
	 * 
	 * @param customerId
	 * @param ContactDTO
	 * @return newly added contact
	 * @author Aniket Navale
	 */
	@PostMapping("/customers/{customerId}/contacts")
	public ResponseEntity<ContactDTO> addContact(@Valid @PathVariable int customerId, @RequestBody ContactDTO contactDTO){
		return ResponseEntity.ok().body(this.contactService.addContact(customerId, contactDTO));
	}
	
	/**
	 * PUT method to update contact of existing customer
	 * 
	 * @param customerId
	 * @param contactId
	 * @param ContactDTO
	 * @return newly updated contact
	 * @author Aniket Navale
	 */
	@PutMapping("/customers/{customerId}/contacts/{contactId}")
	public ResponseEntity<ContactDTO> updateContact(@Valid @PathVariable int customerId, @PathVariable int contactId, @RequestBody ContactDTO contactDTO){
		return ResponseEntity.ok().body(this.contactService.updateContact(customerId, contactId, contactDTO));
	}
	

	/**
	 * Delete method to delete all contacts of specific customer
	 * 
	 * @param customer Id
	 * @return OK
	 * @author Aniket Navale
	 */
	@DeleteMapping("/customers/{customerId}/contacts")
	public HttpStatus deleteAllContactsOfSpecificCustomer(@PathVariable int customerId){
		this.contactService.deleteAllContactsOfSpecificCustomer(customerId);
		return HttpStatus.OK;
	}
	
	
	/**
	 * Delete method to delete specific contact of specific customer
	 * 
	 * @param customerId
	 * @param contactId
	 * @return OK
	 * @author Aniket Navale
	 */
	@DeleteMapping("/customers/{customerId}/contacts/{contactId}")
	public HttpStatus deleteSpecificContactOfSpecificCustomer(@PathVariable int customerId, @PathVariable int contactId){
		this.contactService.deleteSpecificContactOfSpecificCustomer(customerId,contactId);
		return HttpStatus.OK;
	}
	

}
