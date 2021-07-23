package com.restapipractice.restapi.services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "feignApplication", url = "localhost:8080/feign")
public interface FeignInterface {
	
	@GetMapping(path = "/client", produces = "application/json")
	public String getCustomerTypeFeign();

}
