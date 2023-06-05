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
import com.hospital.app.Hospital.Service.WardService;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.WardDto;

@RestController
@RequestMapping(value = "/api/wards")
public class WardController {

	@Autowired
	private WardService wardService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getWard(@PathVariable int id) {
		if (wardService.checkPermissions()) {
			try {
				WardDto wardDto = wardService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(wardDto);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@GetMapping
	public ResponseEntity<ListResponseDto> getWards(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (wardService.checkPermissions()) {
			return new ResponseEntity<>(wardService.getWards(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateWard(@PathVariable int id, @RequestBody WardDto wardDto) {
		if (wardService.checkPermissions()) {
			try {
				WardDto updatedWard = wardService.update(id, wardDto);
				return ResponseEntity.status(HttpStatus.OK).body(updatedWard);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PostMapping
	public ResponseEntity<WardDto> saveWard(@RequestBody WardDto wardDto) {
		if (wardService.checkPermissions()) {
			WardDto createdWard = wardService.create(wardDto);
			return ResponseEntity.status(HttpStatus.OK).body(createdWard);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteWard(@PathVariable int id) {
		if (wardService.checkPermissions()) {
			try {
				wardService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
}
