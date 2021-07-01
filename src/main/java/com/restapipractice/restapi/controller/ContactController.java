package com.restapipractice.restapi.controller;

import java.util.List;
import java.util.Optional;

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
	
	@GetMapping("/customers/{customerId}/contacts")
	public ResponseEntity<List<ContactDTO>> getContacts(@PathVariable int customerId){
		return ResponseEntity.ok().body(contactService.getContacts(customerId));
	}
	
	@GetMapping("/customers/{customerId}/contacts/{contactId}")
	public ResponseEntity<ContactDTO> getContact(@PathVariable int customerId, @PathVariable int contactId){
		return ResponseEntity.ok().body(contactService.getContact(customerId, contactId));
	}
	
	@PostMapping("/customers/{customerId}/contacts")
	public ResponseEntity<ContactDTO> addContact(@PathVariable int customerId, @RequestBody ContactDTO contactDTO){
		return ResponseEntity.ok().body(this.contactService.addContact(customerId, contactDTO));
	}
	
	@PutMapping("/customers/{customerId}/contacts/{contactId}")
	public ResponseEntity<ContactDTO> updateContact(@PathVariable int customerId, @PathVariable int contactId, @RequestBody ContactDTO contactDTO){
		return ResponseEntity.ok().body(this.contactService.updateContact(customerId, contactId, contactDTO));
	}
	
	@DeleteMapping("/customers/{customerId}/contacts")
	public HttpStatus deleteAllContactsOfSpecificCustomer(@PathVariable int customerId){
		this.contactService.deleteAllContactsOfSpecificCustomer(customerId);
		return HttpStatus.OK;
	}//TODO add delete specific contact
	
	@DeleteMapping("/customers/{customerId}/contacts/{contactId}")
	public HttpStatus deleteSpecificContactOfSpecificCustomer(@PathVariable int customerId, @PathVariable int contactId){
		this.contactService.deleteSpecificContactOfSpecificCustomer(customerId,contactId);
		return HttpStatus.OK;
	}
	

}
