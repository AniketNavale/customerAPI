package com.restapipractice.restapi.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restapipractice.restapi.entities.Contact;

public interface CustomerContactDao extends JpaRepository<Contact, Integer>{
	
	//making custom query to fetch only specific customers with that specific customerId
	
	@Query(value = "SELECT * FROM CONTACT u WHERE u.customer_id = :customerId", nativeQuery = true)
	List<Contact> findByCustomerId(@Param("customerId") Integer customerId);

	@Query(value = "DELETE FROM CONTACT u  WHERE u.customer_id = :customerId;", nativeQuery = true)
	void deleteByCustomerId(@Param("customerId") Integer customerId);
}
