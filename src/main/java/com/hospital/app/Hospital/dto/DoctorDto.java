package com.hospital.app.Hospital.dto;

import com.hospital.app.Hospital.Model.Doctor;
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
public class DoctorDto {

	private int id;

	@NotEmpty(message = "Doctor firstName should not be empty.")
	private String firstName;

	@NotEmpty(message = "Doctor lastName should not be empty.")
	private String lastName;

	private String qualification;

	@Min(value = 18)
	@Max(value = 100)
	private int age;

	@Min(value = 0, message = "Doctor number should be valid.")
	private long number;

	private int wardId;
	
	private String username;
	private String password;

	public DoctorDto(Doctor doctor) {
		this.id = doctor.getId();
		this.firstName = doctor.getPersonInfo().getFirstName();
		this.lastName = doctor.getPersonInfo().getLastName();
		this.age = doctor.getPersonInfo().getAge();
		this.qualification = doctor.getQualification();
		this.number = doctor.getPersonInfo().getNumber();
		this.wardId = doctor.getWard() != null ? doctor.getWard().getId() : null;
		this.username=doctor.getPersonInfo().getUserEntity().getUsername();
		this.password=doctor.getPersonInfo().getUserEntity().getPassword();
	}

	public DoctorDto(PersonInfo personInfo) {
		this.firstName = personInfo.getFirstName();
		this.lastName = personInfo.getLastName();
		this.age = personInfo.getAge();
		this.number = personInfo.getNumber();
		this.username = personInfo.getUserEntity().getUsername();
		this.password = personInfo.getUserEntity().getPassword();
	}

}
