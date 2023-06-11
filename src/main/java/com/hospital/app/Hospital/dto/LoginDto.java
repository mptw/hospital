package com.hospital.app.Hospital.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {

	@NotEmpty(message = "Username should not be empty.")
	private String username;

	@NotEmpty(message = "Password should not be empty.")
	private String password;
}