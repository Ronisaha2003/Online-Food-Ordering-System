package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
	Optional<Admin> findByEmail(String email);
}
