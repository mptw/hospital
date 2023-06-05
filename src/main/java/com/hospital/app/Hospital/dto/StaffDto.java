package com.hospital.app.Hospital.dto;

import com.hospital.app.Hospital.Model.Staff;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {

	public int id;
	private String firstName;
	private String lastName;
	private int age;
	private long number;
	private String type;
	private int wardId;

	public StaffDto(Staff staff) {
		this.id = staff.getId();
		this.firstName = staff.getFirstName();
		this.lastName = staff.getLastName();
		this.age = staff.getAge();
		this.type = staff.getType();
		this.number = staff.getNumber();
		this.wardId = staff.getWard() != null ? staff.getWard().getId() : null;
	}
}
