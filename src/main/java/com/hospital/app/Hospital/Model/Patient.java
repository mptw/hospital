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
@Table(name = "Patients")
public class Patient extends Person {

	@Column
	private String disease;

	@Column
	private String treatment;

	@ManyToOne
	private Doctor doctor;

	@ManyToOne
	private Ward ward;

}
