package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="report")
@AllArgsConstructor
@NoArgsConstructor
public class Report {
	@Id
	int rno;
	String title;
	String reported;
	String ppnum;
	String ppdate;
	String name;
	String email;
	String phone;
	String consent_status;
}
