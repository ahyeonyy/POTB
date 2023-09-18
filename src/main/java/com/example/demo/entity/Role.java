package com.example.demo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	AMDIN("ADMIN","관리자"),
	USER("USER","사용자");
	
	private final String key;
	private final String title;
}




















