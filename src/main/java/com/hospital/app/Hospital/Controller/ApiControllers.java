package com.hospital.app.Hospital.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiControllers {

	@GetMapping(value = "/")
	public String getPage() {
		return "Welcome";
	}
}
