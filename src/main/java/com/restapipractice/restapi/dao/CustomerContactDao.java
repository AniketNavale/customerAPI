package com.restapipractice.restapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restapipractice.restapi.entities.Contact;

public interface CustomerContactDao extends JpaRepository<Contact, Integer>{

}
