package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.Admin;
import com.example.model.User;
import com.example.repository.AdminRepository;
import com.example.service.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminRepository adminrepo;
	
	@GetMapping("/admin")
	public String admin()
	{
		return "admin";
	}
	
	
	
	@GetMapping("/adminreg")
	public String adminRegister(Model model)
	{
		Admin admin=new Admin();
		model.addAttribute("admin",admin);
		return "admin_reg";
	}
	
	@PostMapping("/saveAdmin")
	public String saveAdmin(@ModelAttribute("admin") Admin admin)
	{
		adminService.saveAdmin(admin);
		return "redirect:/admin";
		
	}
	

	@GetMapping("/adminlog")
	public String login(Model model) {
		  Admin admin=new Admin();
		  model.addAttribute("Admin",admin);
		  return "adminlogin";
	}
	@PostMapping("/adminlogin")
	public String loginuser(@ModelAttribute("admin")Admin admin) {
		  
		  String email=admin.getEmail();
		  
		Optional<Admin>userdata=adminrepo.findByEmail(email);
		  if(admin.getPassword().equals(userdata.get().getPassword())) {
			  return "redirect:/admin";
		  }
		  else {
			  return "error";
		  }
	}
	
	@GetMapping("/admin/vadmin")
	public String viewAdmin(Model model)
	{
		model.addAttribute("listAdmin",adminService.getAllAdmin());
		return "viewadmin";
		
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get admin from the service
		Admin admin = adminService.getAdminById(id);
		
		
		model.addAttribute("admin", admin);
		return "updateadmin";
	}
	
	@GetMapping("/deleteAdmin/{id}")
	public String deleteAdmin(@PathVariable (value = "id") long id) {
		
		// call delete admin method 
		this.adminService.deleteAdminById(id);
		return "redirect:/admin/vadmin";
	}
	
	@GetMapping("/adminjoin")
	public String adminJoin(Model model)
	{
		Admin admin=new Admin();
		model.addAttribute("admin",admin);
		return "add_admin";
	}
	
	@PostMapping("/joinAdmin")
	public String joinAdmin(@ModelAttribute("admin") Admin admin)
	{
		adminService.saveAdmin(admin);
		return "redirect:/admin/vadmin";
		
	}
	
}

