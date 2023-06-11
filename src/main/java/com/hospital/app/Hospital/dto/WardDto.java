package com.hospital.app.Hospital.dto;

import com.hospital.app.Hospital.Model.Ward;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WardDto {
	
	private int id;
	
	@NotEmpty(message = "Ward name should not be empty.")
	private String name;
	
	private int headDoctorId;

	public WardDto(Ward ward) {
		this.id = ward.getId();
		this.name = ward.getName();
		this.headDoctorId = ward.getHeadDoctor() != null ? ward.getHeadDoctor().getId() : null;
	}
}
