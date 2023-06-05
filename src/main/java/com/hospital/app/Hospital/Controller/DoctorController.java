package com.hospital.app.Hospital.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Service.DoctorService;
import com.hospital.app.Hospital.dto.DoctorDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

@RestController
@RequestMapping(value = "/api/doctors")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getDoctor(@PathVariable int id) {
		if (doctorService.checkPermissions()) {
			try {
				DoctorDto doctorDto = doctorService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(doctorDto);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@GetMapping
	public ResponseEntity<ListResponseDto> getDoctors(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (doctorService.checkPermissions()) {
			return new ResponseEntity<>(doctorService.getDoctors(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/ward/{wardId}")
	public ResponseEntity<ListResponseDto> getDoctorsInWard(@PathVariable int wardId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (doctorService.checkPermissions()) {
			return new ResponseEntity<>(doctorService.getAllInWard(wardId, pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDoctor(@PathVariable int id, @RequestBody DoctorDto doctorDto) {
		if (doctorService.checkPermissions()) {
			try {
				DoctorDto updatedDoctor = doctorService.update(id, doctorDto);
				return ResponseEntity.status(HttpStatus.OK).body(updatedDoctor);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PostMapping
	public ResponseEntity<DoctorDto> saveDoctor(@RequestBody DoctorDto doctorDto) {
		if (doctorService.checkPermissions()) {
			DoctorDto createdDoctor = doctorService.create(doctorDto);
			return ResponseEntity.status(HttpStatus.OK).body(createdDoctor);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
		if (doctorService.checkPermissions()) {
			try {
				doctorService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
