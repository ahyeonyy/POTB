package com.example.demo.entity;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Inspect {
	String country;
	MultipartFile[] uploadfile;
}
