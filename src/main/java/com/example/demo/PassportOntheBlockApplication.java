package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class PassportOntheBlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassportOntheBlockApplication.class, args);
	}
	@SpringBootApplication
	public class JpaBoard3Application {
		@Bean
		public PasswordEncoder passwordEncoder() {
			//return new BCryptPasswordEncoder(); // 단방향 인코더
			return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		}
 
	}
}
