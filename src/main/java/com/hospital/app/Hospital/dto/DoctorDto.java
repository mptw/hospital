package com.hospital.app.Hospital.dto;

import com.hospital.app.Hospital.Model.Doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {
	
	private int id;
	private String firstName;
	private String lastName;
	private String qualification;
	private int age;
	private long number;
	private int wardId;

	public DoctorDto(Doctor doctor) {
		this.id = doctor.getId();
		this.firstName = doctor.getFirstName();
		this.lastName = doctor.getLastName();
		this.age = doctor.getAge();
		this.qualification = doctor.getQualification();
		this.number = doctor.getNumber();
		this.wardId = doctor.getWard() != null ? doctor.getWard().getId() : null;
	}
	
}
