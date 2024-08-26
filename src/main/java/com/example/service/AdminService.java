package com.example.service;

import java.util.List;

import com.example.model.Admin;

public interface AdminService {
	void saveAdmin(Admin admin);
	List<Admin> getAllAdmin();
	Admin getAdminById(long id);
	
	void deleteAdminById(long id);
}
