package com.restapipractice.restapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.restapipractice.restapi.services.FeignClientErrorDecoder;

@SpringBootApplication
@EnableFeignClients
public class RestapiApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
    public FeignClientErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }

	public static void main(String[] args) {
		SpringApplication.run(RestapiApplication.class, args);
	}
	
	

}
