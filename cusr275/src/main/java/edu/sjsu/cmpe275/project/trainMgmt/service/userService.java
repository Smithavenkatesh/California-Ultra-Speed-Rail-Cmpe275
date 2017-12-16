package edu.sjsu.cmpe275.project.trainMgmt.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.project.trainMgmt.repository.userRepository;

import edu.sjsu.cmpe275.project.trainMgmt.model.user;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service("userService")
@Transactional
public class userService {
	
	@Autowired
	private final userRepository userRepository;
	
	
	@Autowired
    public userService(userRepository userRepository) { 
      this.userRepository = userRepository;
    }
    
	
	public void saveUser(user user) {
		userRepository.save(user);
	}	
	
	/**
     * Function definition to list all users
     */
	public List<user> findAll() {
		return userRepository.findAll();
	}
	
	public user findByEmail(String email) {
		return findByEmail(email);
	}
	
}




