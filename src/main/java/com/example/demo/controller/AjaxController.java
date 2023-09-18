package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.AccountDAO;
import com.example.demo.dao.AccountDAO_mb;
import com.example.demo.entity.Account;


import jakarta.servlet.http.HttpSession;
import lombok.Setter;
@Setter
@RestController
public class AjaxController {
	@Autowired
	private AccountDAO dao;

	@Autowired
	private AccountDAO_mb mb_dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	
	@GetMapping("/ajax/duplicateEmail")
	public String duplicateEmail(String email) {
		// System.out.println("duplicate Check email : "+email);
		if(dao.findByEmail(email)==null) {
			return "";
		}
		return dao.findByEmail(email).getEmail();
	}
	
	@GetMapping("/ajax/emailCheckWithEmail")
	public String emailCheckWithEmail(String email, String id) {
		Account a = mb_dao.emailCheckWithEmail(email, id);
		if (a == null) {
			return "";
		}
		return a.getEmail();
	}

	@GetMapping("/ajax/idCheck")
	public String idCHeck(String id) {
		if (dao.findByAid(id) != null) {
			return dao.findByAid(id).getAid();
		}
		return "";
	}
	

	@GetMapping("/ajax/confirmCheck")
	public String confirmCheck(HttpSession session) {
		Account a = (Account)session.getAttribute("a");
		
		return "";
	}
	
	@GetMapping("/ajax/getID")
	public String getID(String email) {
		return dao.findByEmail(email).getAid();
	}
	

}
