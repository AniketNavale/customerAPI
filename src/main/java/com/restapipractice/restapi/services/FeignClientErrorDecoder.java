package com.restapipractice.restapi.services;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignClientErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String s, Response response) {
		
		final ErrorDecoder defaultErrorDecoder = new Default();
		
		System.out.println("An error occured!!!");
		 
        if (400 == response.status()) {
            System.out.println("It's a 400 Error!!!");
        }
 
        return defaultErrorDecoder.decode(s, response);
	}
	
	

}
