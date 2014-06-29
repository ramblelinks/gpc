package com.ramblelinks.golfprocompanion.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ramblelinks.golfprocompanion.domain.User;

@Service
public class ImplementUserManager implements UserManager{

	private List<User> ul = new ArrayList<User>();
	
	@Override
	public List<User> getUser() {
		init();
		return ul;
	}

	private void init()
	{
		User user = new User();   
		user.setId(1);   
		user.setFirstname ("John");   
		user.setLastname("Smith");   
		ul.add(user);       
		
		user = new User();   
		user.setId(2);   
		user.setFirstname("Jane");   
		user.setLastname("Adams");   
		ul.add(user);       
		
		user = new User();   
		user.setId(3);   
		user.setFirstname("Jeff");   
		user.setLastname("Mayer");   
		ul.add(user); 
	}
	
}
