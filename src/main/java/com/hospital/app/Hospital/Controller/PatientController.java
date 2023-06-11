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

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Dto.PatientDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Service.PatientService;

@RestController
@RequestMapping(value = "/api/patients")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getPatient(@PathVariable int id) {
		if (patientService.checkPermissions()) {
			try {
				PatientDto patientDto = patientService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(patientDto);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping
	public ResponseEntity<ListResponseDto> getPatients(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (patientService.checkPermissions()) {
			return new ResponseEntity<>(patientService.getPatients(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/doctor/{doctorId}")
	public ResponseEntity<ListResponseDto> getPatientsByDoctor(@PathVariable int doctorId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (patientService.checkPermissions()) {
			return new ResponseEntity<>(patientService.getAllByDoctor(doctorId, pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/ward/{wardId}")
	public ResponseEntity<ListResponseDto> getPatientsByWard(@PathVariable int wardId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (patientService.checkPermissions()) {
			return new ResponseEntity<>(patientService.getAllByWard(wardId, pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updatePatient(@PathVariable int id, @RequestBody PatientDto patientDto) {
		if (patientService.checkPermissions()) {
			try {
				PatientDto updatedPatient = patientService.update(id, patientDto);
				return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PostMapping
	public ResponseEntity<PatientDto> savePatient(@RequestBody PatientDto patientDto) {
		if (patientService.checkPermissions()) {
			PatientDto createdPatient = patientService.create(patientDto);
			return ResponseEntity.status(HttpStatus.OK).body(createdPatient);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable int id) {
		if (patientService.checkPermissions()) {
			try {
				patientService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
