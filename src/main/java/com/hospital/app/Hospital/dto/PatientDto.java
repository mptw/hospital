package com.hospital.app.Hospital.dto;

import com.hospital.app.Hospital.Model.Patient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
	private int id;
	private String firstName;
	private String lastName;
	private String disease;
	private String treatment;
	private int age;
	private long number;
	private int doctorId;
	private int wardId;

	public PatientDto(Patient patient) {
		this.id = patient.getId();
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.age = patient.getAge();
		this.disease = patient.getDisease();
		this.treatment = patient.getTreatment();
		this.number = patient.getNumber();
		this.doctorId = patient.getDoctor() != null ? patient.getDoctor().getId() : null;
		this.wardId = patient.getWard() != null ? patient.getWard().getId() : null;
	}
}
