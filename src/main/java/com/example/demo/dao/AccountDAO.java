package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Account;

public interface AccountDAO extends JpaRepository<Account, String> {
	public Account findByAid(String aid);
	public Account findByEmail(String email);
}
