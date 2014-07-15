package com.mal.orm;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name="user")

public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="us_id")
	private Long id;

	@Column(name="us_name")
	private String name;

	@Column(name="us_time_creation")
	private Timestamp createdTime;

	public Long getUserId() {
		return id;
	}

	public void setUserId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return name;
	}

	public void setUserName(String name) {
		this.name = name;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp newTime) {
		createdTime = newTime;
	}
}
