package com.hospital.app.Hospital.Dto;

import com.hospital.app.Hospital.Model.Director;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
	private int id;

	@NotEmpty(message = "Doctor firstName should not be empty.")
	private String firstName;

	@NotEmpty(message = "Doctor lastName should not be empty.")
	private String lastName;

	@Min(value = 18)
	@Max(value = 100)
	private int age;

	@Min(value = 0, message = "Doctor number should be valid.")
	private long number;

	private String username;
	private String password;

	public DirectorDto(Director director) {
		this.id = director.getId();
		this.firstName = director.getPersonInfo().getFirstName();
		this.lastName = director.getPersonInfo().getLastName();
		this.age = director.getPersonInfo().getAge();
		this.number = director.getPersonInfo().getNumber();
		this.username = director.getPersonInfo().getUserEntity().getUsername();
		this.password = director.getPersonInfo().getUserEntity().getPassword();
	}
}
