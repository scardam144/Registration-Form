package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
	 @Query("SELECT u FROM User u WHERE u.email = ?1")
	    public User findByEmail(String email);

}
