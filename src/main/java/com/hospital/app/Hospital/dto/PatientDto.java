package com.hospital.app.Hospital.Dto;

import com.hospital.app.Hospital.Model.Patient;
import com.hospital.app.Hospital.Model.PersonInfo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
	private int id;

	@NotEmpty(message = "Patient firstName should not be empty.")
	private String firstName;

	@NotEmpty(message = "Patient lastName should not be empty.")
	private String lastName;

	private String disease;
	private String treatment;

	@Min(value = 0)
	@Max(value = 120)
	private int age;

	@Min(value = 0, message = "Patient number should not be empty.")
	private long number;

	private int doctorId;
	private int wardId;

	private String username;
	private String password;

	public PatientDto(Patient patient) {
		this.id = patient.getId();
		this.firstName = patient.getPersonInfo().getFirstName();
		this.lastName = patient.getPersonInfo().getLastName();
		this.age = patient.getPersonInfo().getAge();
		this.disease = patient.getDisease();
		this.treatment = patient.getTreatment();
		this.number = patient.getPersonInfo().getNumber();
		this.doctorId = patient.getDoctor() != null ? patient.getDoctor().getId() : null;
		this.wardId = patient.getWard() != null ? patient.getWard().getId() : null;
		this.username = patient.getPersonInfo().getUserEntity().getUsername();
		this.password = patient.getPersonInfo().getUserEntity().getPassword();
	}

	public PatientDto(PersonInfo personInfo) {
		this.firstName = personInfo.getFirstName();
		this.lastName = personInfo.getLastName();
		this.age = personInfo.getAge();
		this.number = personInfo.getNumber();
		this.username = personInfo.getUserEntity().getUsername();
		this.password = personInfo.getUserEntity().getPassword();
	}
}
