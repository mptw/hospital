package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.dto.AppointmentDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

public interface AppointmentService {
	
	AppointmentDto get(int id) throws EntityNotFoundException;
	
	ListResponseDto getAppointments(int pageNo, int pageSize);
	
	ListResponseDto getAppointmentsWithDoctor(int doctorId, int pageNo, int pageSize);
	
	ListResponseDto getAppointmentsForPatient(int patientId, int pageNo, int pageSize);
	
	AppointmentDto update(int id, AppointmentDto appointmentDto) throws EntityNotFoundException;

	AppointmentDto create(AppointmentDto appointmentDto);
	
	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();


}
