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
import com.hospital.app.Hospital.Service.TreatmentService;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.TreatmentDto;

@RestController
@RequestMapping(value = "/api/treatments")
public class TreatmentController {
	@Autowired
	private TreatmentService treatmentService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getTreatment(@PathVariable int id) {
		if (treatmentService.checkPermissions()) {
			try {
				TreatmentDto treatmentDto = treatmentService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(treatmentDto);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping
	public ResponseEntity<ListResponseDto> getTreatments(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (treatmentService.checkPermissions()) {
			return new ResponseEntity<>(treatmentService.getTreatments(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/patient/{patientId}")
	public ResponseEntity<ListResponseDto> getTreatmentsForPatient(@PathVariable int patientId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (treatmentService.checkPermissions()) {
			return new ResponseEntity<>(treatmentService.getTreatmentsForPatient(patientId, pageNo, pageSize),
					HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateTreatment(@PathVariable int id, @RequestBody TreatmentDto treatmentDto) {
		if (treatmentService.checkPermissions()) {
			try {
				TreatmentDto updatedTreatment = treatmentService.update(id, treatmentDto);
				return ResponseEntity.status(HttpStatus.OK).body(updatedTreatment);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PostMapping
	public ResponseEntity<TreatmentDto> saveTreatment(@RequestBody TreatmentDto treatmentDto) {
		if (treatmentService.checkPermissions()) {
			TreatmentDto createdTreatment = treatmentService.create(treatmentDto);
			return ResponseEntity.status(HttpStatus.OK).body(createdTreatment);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteTreatment(@PathVariable int id) {
		if (treatmentService.checkPermissions()) {
			try {
				treatmentService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
