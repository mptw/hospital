package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Dto.AppointmentDto;
import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;

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
