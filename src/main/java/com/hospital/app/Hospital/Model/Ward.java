package com.hospital.app.Hospital.Model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIdentityInfo(property = "id", generator = ObjectIdGenerators.PropertyGenerator.class)
@Entity
@Table(name = "Wards")
public class Ward {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column
	private String name;

	@Transient
	@OneToMany(mappedBy = "wards")
	private List<Doctor> doctors = new ArrayList<>();

	@Transient
	@OneToMany(mappedBy = "wards")
	private List<Patient> patients = new ArrayList<>();

	@Transient
	@OneToMany(mappedBy = "wards")
	private List<Staff> staff = new ArrayList<>();

	@OneToOne
	@JoinColumn(name = "headDoctor")
	private Doctor headDoctor;
}
