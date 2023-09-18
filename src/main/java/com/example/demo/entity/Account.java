package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="account")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	@Id
	String aid;
	String pwd;
	String email;
	String regdate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	Role role;
	
	@Builder
	public Account(String aid, String email,Role role) {
		this.aid = aid;
		this.email = email;
		this.role = role;
	};
}
