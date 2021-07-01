package com.restapipractice.restapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddressDTO {
	
	private int customer_id;
	
	@NotBlank(message="No null city allowed")
	private String city;
	
	@NotBlank(message="No null country allowed")
	private String country;
	
	@Min(1)
	@Max(999999)
	private int postal_code;

}
