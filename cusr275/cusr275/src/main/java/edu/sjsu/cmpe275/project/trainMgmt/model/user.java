package edu.sjsu.cmpe275.project.trainMgmt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//import java.io.Serializable;



@Entity
@Table(name = "user")
public class user {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "username")
	
	private String username;
	
	@Column(name = "email", nullable = false, unique = true)
	
	
	private String email;
	
	@Column(name = "password")
	
	private String password;
	
	
	
		
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}



	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public user(String username,String email, String password ) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public user() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}