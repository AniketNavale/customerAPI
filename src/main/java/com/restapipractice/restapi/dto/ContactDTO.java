package com.restapipractice.restapi.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ContactDTO {
	
	private int contactId;
	
	@NotBlank(message="No null contact number allowed")
	private int contactNumber;

}
