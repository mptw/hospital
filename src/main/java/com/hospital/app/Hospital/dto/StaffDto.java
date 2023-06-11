package com.hospital.app.Hospital.dto;

import com.hospital.app.Hospital.Model.PersonInfo;
import com.hospital.app.Hospital.Model.Staff;
import com.hospital.app.Hospital.Model.StaffType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {

	public int id;
	
	@NotEmpty(message = "Staff firstName should not be empty.")
	private String firstName;
	
	@NotEmpty(message = "Staff lastName should not be empty.")
	private String lastName;
	
	@Min(value = 18)
	@Max(value = 100)
	private int age;
	
	@Min(value = 0, message = "Staff number should not be empty.")
	private long number;

	private StaffType type;
	
	private int wardId;
	
	private String username;
	private String password;

	public StaffDto(Staff staff) {
		this.id = staff.getId();
		this.firstName = staff.getPersonInfo().getFirstName();
		this.lastName = staff.getPersonInfo().getLastName();
		this.age = staff.getPersonInfo().getAge();
		this.type = staff.getType();
		this.number = staff.getPersonInfo().getNumber();
		this.wardId = staff.getWard() != null ? staff.getWard().getId() : null;
		this.username=staff.getPersonInfo().getUserEntity().getUsername();
		this.password=staff.getPersonInfo().getUserEntity().getPassword();
	}

	public StaffDto(PersonInfo personInfo) {
		this.firstName = personInfo.getFirstName();
		this.lastName = personInfo.getLastName();
		this.age = personInfo.getAge();
		this.number = personInfo.getNumber();
		this.username=personInfo.getUserEntity().getUsername();
		this.password=personInfo.getUserEntity().getPassword();
	}
}
