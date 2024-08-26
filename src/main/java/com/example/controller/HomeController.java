package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;
	@Autowired
	UserRepository userrepo;
	
	@GetMapping("/navbar")
	public String navbar()
	{
		return "navbar";
	}
	
	@GetMapping("/index")
	public String index()
	{
		return "index";
	}

	 
	@GetMapping("/home")
	public String home()
	{
		return "home";
	}
	
	
	
	@GetMapping("/admin/viewuser")
	public String viewuser(Model model)
	{
		model.addAttribute("listUser",userService.getAllUser());
		return "viewuser";
	}
	
	
	@GetMapping("/main")
	public String register(Model model)
	{
		User user=new User();
		model.addAttribute("user",user);
		return "new_employee";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user, Model model)
	{
		model.addAttribute("message","Registered successfully");
		userService.saveUser(user);
		
		return "redirect:/home";
	}
		
	@GetMapping("/login")
	public String login(Model model) {
		  User user=new User();
		  model.addAttribute("User",user);
		  return "login";
	}
	@PostMapping("/userlogin")
	public String loginuser(@ModelAttribute("user")User user) {
		  
		  String email=user.getEmail();
		  
		Optional<User>userdata=userrepo.findByEmail(email);
		  if(user.getPassword().equals(userdata.get().getPassword())) {
			  return "redirect:/home";
		  }
		  else {
			  return "error";
		  }
		
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteAdmin(@PathVariable (value = "id") long id) {
		
		// call delete admin method 
		this.userService.deleteUserById(id);
		return "redirect:/viewuser";
	}
	@GetMapping("/contact")
	public String contact()
	{
		return "contact";
	}
	@GetMapping("/feedback")
	public String feedback()
	{
		return "feedback";
	}
	@GetMapping("/about")
	public String about()
	{
		return "about";
	}

	
	
}

