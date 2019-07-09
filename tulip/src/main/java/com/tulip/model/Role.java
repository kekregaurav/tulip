package com.tulip.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
  
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer roleId;
	
	private String name;
	
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<UserRole> userRoles = new  HashSet<>();
	
	public Role() {}

	public Role(int roleId, String name, Set<UserRole> userRoles) {
		this.roleId = roleId;
		this.name = name;
		this.userRoles = userRoles;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer role_id) {
		this.roleId = role_id;
	}

	public String getRoleName() {
		return name;
	}

	public void setRoleName(String roleName) {
		this.name = roleName;
	}
	
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	
	
}
