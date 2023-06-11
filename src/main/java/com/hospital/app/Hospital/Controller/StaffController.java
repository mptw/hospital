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
import com.hospital.app.Hospital.Dto.StaffDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Service.StaffService;

@RestController
@RequestMapping(value = "/api/staff")
public class StaffController {

	@Autowired
	private StaffService staffService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getStaffPerson(@PathVariable int id) {
		if (staffService.checkPermissions()) {
			try {
				StaffDto staffDto = staffService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(staffDto);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping
	public ResponseEntity<ListResponseDto> getStaff(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (staffService.checkPermissions()) {
			return new ResponseEntity<>(staffService.getStaff(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/ward/{wardId}")
	public ResponseEntity<ListResponseDto> getStaffByWard(@PathVariable int wardId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (staffService.checkPermissions()) {
			return new ResponseEntity<>(staffService.getAllByWard(wardId, pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDoctor(@PathVariable int id, @RequestBody StaffDto staffDto) {
		if (staffService.checkPermissions()) {
			try {
				StaffDto updatedStaff = staffService.update(id, staffDto);
				return ResponseEntity.status(HttpStatus.OK).body(updatedStaff);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PostMapping
	public ResponseEntity<StaffDto> saveDoctor(@RequestBody StaffDto staffDto) {
		if (staffService.checkPermissions()) {
			StaffDto createdStaff = staffService.create(staffDto);
			return ResponseEntity.status(HttpStatus.OK).body(createdStaff);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
		if (staffService.checkPermissions()) {
			try {
				staffService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
