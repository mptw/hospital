package com.hospital.app.Hospital.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
@Entity
@Table(name = "Doctors")
public class Doctor extends Person {

	@Column
	private String qualification;
	
	@Transient
	@OneToMany(mappedBy = "doctors")
	private List<Patient> patients = new ArrayList<>();

	@ManyToOne
	private Ward ward;

	public void addPatient(Patient patient) {
		if(!patients.contains(patient)) {
			patients.add(patient);
		}
	}
}
