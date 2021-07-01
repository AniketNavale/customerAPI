package com.restapipractice.restapi.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CustomerDTO {
	
	private int customer_id;
	
	@NotBlank(message="No null name allowed")
	private String name;
	
	@Min(5)
	@Max(110)
	private int age; 

}
