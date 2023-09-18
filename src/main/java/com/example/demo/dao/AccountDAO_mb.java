package com.example.demo.dao;

import org.springframework.stereotype.Repository;

import com.example.demo.db.DBManager;
import com.example.demo.entity.Account;

@Repository
public class AccountDAO_mb {
	public Account emailCheckWithEmail(String email,String id) {
		return DBManager.emailCheckWithEmail(email, id);
	}
}
