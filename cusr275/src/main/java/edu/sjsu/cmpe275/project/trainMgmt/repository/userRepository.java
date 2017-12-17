package edu.sjsu.cmpe275.project.trainMgmt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.sjsu.cmpe275.project.trainMgmt.model.user;
//import com.javainuse.model.User;

public interface userRepository extends JpaRepository<user,Long>  {
	 
}

