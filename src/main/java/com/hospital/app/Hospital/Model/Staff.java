package com.hospital.app.Hospital.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Staff")
public class Staff extends Person {

	@Column
	private String type;
	
	@ManyToOne
	private Ward ward;
}
