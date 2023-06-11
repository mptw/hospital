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

import com.hospital.app.Hospital.Dto.AppointmentDto;
import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Service.AppointmentService;

@RestController
@RequestMapping(value = "/api/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAppointment(@PathVariable int id) {
		if (appointmentService.checkPermissions()) {
			try {
				AppointmentDto appointmentDto = appointmentService.get(id);
				return ResponseEntity.status(HttpStatus.OK).body(appointmentDto);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping
	public ResponseEntity<ListResponseDto> getAppointments(
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (appointmentService.checkPermissions()) {
			return new ResponseEntity<>(appointmentService.getAppointments(pageNo, pageSize), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/doctor/{doctorId}")
	public ResponseEntity<ListResponseDto> getAppointmentsWithDoctor(@PathVariable int doctorId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (appointmentService.checkPermissions()) {
			return new ResponseEntity<>(appointmentService.getAppointmentsWithDoctor(doctorId, pageNo, pageSize),
					HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@GetMapping(value = "/patient/{patientId}")
	public ResponseEntity<ListResponseDto> getAppointmentsForPatient(@PathVariable int patientId,
			@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		if (appointmentService.checkPermissions()) {
			return new ResponseEntity<>(appointmentService.getAppointmentsForPatient(patientId, pageNo, pageSize),
					HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateAppointment(@PathVariable int id, @RequestBody AppointmentDto appointmentDto) {
		if (appointmentService.checkPermissions()) {
			try {
				AppointmentDto updatedAppointment = appointmentService.update(id, appointmentDto);
				return ResponseEntity.status(HttpStatus.OK).body(updatedAppointment);
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@PostMapping
	public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentDto appointmentDto) {
		if (appointmentService.checkPermissions()) {
			AppointmentDto createdAppointment = appointmentService.create(appointmentDto);
			return ResponseEntity.status(HttpStatus.OK).body(createdAppointment);
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable int id) {
		if (appointmentService.checkPermissions()) {
			try {
				appointmentService.delete(id);
				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (EntityNotFoundException ex) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
			}
		} else
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}

}
