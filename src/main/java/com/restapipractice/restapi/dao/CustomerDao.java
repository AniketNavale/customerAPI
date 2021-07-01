package com.restapipractice.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapipractice.restapi.entities.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
