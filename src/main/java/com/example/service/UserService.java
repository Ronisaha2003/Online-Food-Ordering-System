package com.example.service;

import java.util.List;


import com.example.model.User;

public interface UserService  {
	void saveUser(User user);
	List<User> getAllUser();
	void deleteUserById(long id);
}
