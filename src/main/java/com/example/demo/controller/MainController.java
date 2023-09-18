package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.AccountDAO;
import com.example.demo.entity.Account;
import com.example.demo.entity.Role;
import com.example.demo.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Setter;

@Setter
@Controller
public class MainController {
	   @Autowired
	   private PasswordEncoder passwordEncoder;
	   
	   @Autowired
	   private AccountDAO dao;
	   
	   @Autowired
	   private AccountService as;
	
	@GetMapping("/main")
	public ModelAndView main(){
		ModelAndView mav = new ModelAndView("/main");
//		System.out.println("main 로드됨");
		return mav;
		}
	@GetMapping("/report")
	public ModelAndView report(){
		ModelAndView mav = new ModelAndView("/report");
		return mav;
	}
	@GetMapping("/login")
	   public ModelAndView loginform() {
	      ModelAndView mav = new ModelAndView("/login");
	      
	      return mav;
	   }
	   @GetMapping("/join")
	   public ModelAndView join(Model model) {	      
	      ModelAndView mav = new ModelAndView("/join");
	      return mav;
	   }
	   
	   @PostMapping("/join")

	   public ModelAndView join(Account a,  HttpServletRequest request) {
	      ModelAndView mav = new ModelAndView("redirect:/login");
	      LocalDate today = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd");
	        String regdate = today.format(formatter);
	      a.setRole(Role.USER);
	      a.setRegdate(regdate);
	      a.setPwd(passwordEncoder.encode(a.getPwd()));
	      dao.save(a);
	      return mav;

	   }
	   
	   private UserDetails createUserDetails(String id) {
	       List<GrantedAuthority> authorities = new ArrayList();
	       authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	      //  System.out.println("롤부여했니 ?");
	       return new User(id, dao.findByAid(id).getPwd(), authorities);
	   }

}
