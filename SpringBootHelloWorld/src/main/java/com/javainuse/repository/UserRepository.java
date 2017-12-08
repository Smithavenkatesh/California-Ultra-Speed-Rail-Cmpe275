package com.javainuse.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.javainuse.model.User;



@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
     
}