package com.restapipractice.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapipractice.restapi.entities.Address;

public interface CustomerAddressDao extends JpaRepository<Address, Integer>{
             
}
