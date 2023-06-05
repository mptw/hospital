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
import com.hospital.app.Hospital.Model.Room;
import com.hospital.app.Hospital.Service.RoomService;
import com.hospital.app.Hospital.dto.ListResponseDto;

@RestController
@RequestMapping(value = "/api/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getRoom(@PathVariable int id) {
		if (roomService.checkPermissions()) {
			try {
				Room room = roomService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(room);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@GetMapping
	public ResponseEntity<ListResponseDto> getRooms(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (roomService.checkPermissions()) {
			return new ResponseEntity<>(roomService.getRooms(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDoctor(@PathVariable int id, @RequestBody Room room) {
		if (roomService.checkPermissions()) {
			try {
				Room updatedRoom = roomService.update(id, room);
				return ResponseEntity.status(HttpStatus.OK).body(updatedRoom);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PostMapping
	public ResponseEntity<Room> saveDoctor(@RequestBody Room room) {
		if (roomService.checkPermissions()) {
			Room createdRoom = roomService.create(room);
			return ResponseEntity.status(HttpStatus.OK).body(createdRoom);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteDoctor(@PathVariable int id) {
		if (roomService.checkPermissions()) {
			try {
				roomService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
}
