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
import com.hospital.app.Hospital.Service.DirectorService;
import com.hospital.app.Hospital.dto.DirectorDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

@RestController
@RequestMapping(value = "/api/directors")
public class DirectorController {
	
	@Autowired
	private DirectorService directorService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getDirector(@PathVariable int id) {
		if (directorService.checkPermissions()) {
			try {
				DirectorDto director = directorService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(director);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@GetMapping
	public ResponseEntity<ListResponseDto> getDirectors(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (directorService.checkPermissions()) {
			return new ResponseEntity<>(directorService.getDirectors(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDirector(@PathVariable int id, @RequestBody DirectorDto director) {
		if (directorService.checkPermissions()) {
			try {
				DirectorDto updatedDirector = directorService.update(id, director);
				return ResponseEntity.status(HttpStatus.OK).body(updatedDirector);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PostMapping
	public ResponseEntity<DirectorDto> saveDirector(@RequestBody DirectorDto director) {
		if (directorService.checkPermissions()) {
			DirectorDto createdDirector = directorService.create(director);
			return ResponseEntity.status(HttpStatus.OK).body(createdDirector);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteDirector(@PathVariable int id) {
		if (directorService.checkPermissions()) {
			try {
				directorService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
