package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	
	public void saveUser(User user) {
		this.userRepository.save(user);
		
	}

	
	public List<User> getAllUser() {
		
		return userRepository.findAll();
	}

	
	public void deleteUserById(long id) {
		
		this.userRepository.deleteById(id);
	}

}
