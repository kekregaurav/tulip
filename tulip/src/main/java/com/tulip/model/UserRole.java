package com.tulip.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(updatable = false, nullable = false)
	private Integer userRoleId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private AppUser appUser;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	
	public UserRole(Integer id, AppUser appUser, Role role) {
		super();
		this.userRoleId = id;
		this.appUser = appUser;
		this.role = role;
	}

	public UserRole() {}
	
	public UserRole(AppUser appUser, Role role) {
		this.appUser = appUser;
		this.role = role;
	}

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer id) {
		this.userRoleId = id;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
	
	
}
