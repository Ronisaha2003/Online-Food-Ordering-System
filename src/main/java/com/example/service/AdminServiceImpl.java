package com.example.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	
	public void saveAdmin(Admin admin) {
		
		this.adminRepository.save(admin);
		
	}

	
	public List<Admin> getAllAdmin() {
		return adminRepository.findAll();
	}

	
	public Admin getAdminById(long id) {
	
		Optional<Admin> optional=adminRepository.findById(id);
		Admin admin = null;
		if (optional.isPresent()) {
			admin = optional.get();
		} else {
			throw new RuntimeException(" admin not found for id :: " + id);
		}
		return admin;
	}

	
	public void deleteAdminById(long id) {

		this.adminRepository.deleteById(id);
	}

}