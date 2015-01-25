package com.mal.orm;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="login")

public class Login {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="create_time")
	private Timestamp createdTime;
	
	public Integer getUserId() {
		return id;
	}
	
	public void setUserId(Integer id) {
		this.id = id;
	}
	
	public String getUserName() {
		return name;
	}
	
	public void setUserName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	
	public void setCreatedTime(Timestamp newTime) {
		this.createdTime = newTime;
	}
}
