package com.hospital.app.Hospital.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Patients")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column
	private String disease;

	@Column
	private String treatment;

	@ManyToOne
	private Doctor doctor;

	@ManyToOne
	private Ward ward;

	@OneToOne
	private PersonInfo personInfo = new PersonInfo();

	public Patient(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public Patient() {
	}
}
