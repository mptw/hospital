package com.hospital.app.Hospital.Service.Impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Appointment;
import com.hospital.app.Hospital.Model.Doctor;
import com.hospital.app.Hospital.Model.Patient;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Repository.AppointmentRepository;
import com.hospital.app.Hospital.Repository.DoctorRepository;
import com.hospital.app.Hospital.Repository.PatientRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.AppointmentService;
import com.hospital.app.Hospital.dto.AppointmentDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public AppointmentDto get(int id) throws EntityNotFoundException {
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(
				() -> new EntityNotFoundException("Appointment with id: " + id + " could not be retrieved"));
		return mapToDto(appointment);
	}

	@Override
	public ListResponseDto getAppointments(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Appointment> appointmentPage = appointmentRepository.findAll(pageable);
		List<Appointment> appointments = appointmentPage.getContent();
		List<AppointmentDto> content = appointments.stream().map(a -> mapToDto(a)).collect(Collectors.toList());

		ListResponseDto appointmentListResponse = new ListResponseDto();
		appointmentListResponse.setContent(content);
		appointmentListResponse.setPageNo(appointmentPage.getNumber());
		appointmentListResponse.setPageSize(appointmentPage.getSize());
		appointmentListResponse.setTotalElements(appointmentPage.getTotalElements());
		appointmentListResponse.setTotalPages(appointmentPage.getTotalPages());
		appointmentListResponse.setLast(appointmentPage.isLast());

		return appointmentListResponse;
	}
	
	@Override
	public ListResponseDto getAppointmentsWithDoctor(int doctorId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Appointment> appointmentPage = appointmentRepository.findAll(pageable);
		List<Appointment> appointments = appointmentPage.getContent();
		List<AppointmentDto> content = appointments.stream()
				.filter(a -> a.getDoctor() != null && a.getDoctor().getId() == doctorId).map(a -> mapToDto(a))
				.collect(Collectors.toList());

		ListResponseDto appointmentListResponse = new ListResponseDto();
		appointmentListResponse.setContent(content);
		appointmentListResponse.setPageNo(appointmentPage.getNumber());
		appointmentListResponse.setPageSize(appointmentPage.getSize());
		appointmentListResponse.setTotalElements(appointmentPage.getTotalElements());
		appointmentListResponse.setTotalPages(appointmentPage.getTotalPages());
		appointmentListResponse.setLast(appointmentPage.isLast());

		return appointmentListResponse;
	}

	@Override
	public ListResponseDto getAppointmentsForPatient(int patientId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Appointment> appointmentPage = appointmentRepository.findAll(pageable);
		List<Appointment> appointments = appointmentPage.getContent();
		List<AppointmentDto> content = appointments.stream()
				.filter(a -> a.getPatient() != null && a.getPatient().getId() == patientId).map(a -> mapToDto(a))
				.collect(Collectors.toList());

		ListResponseDto appointmentListResponse = new ListResponseDto();
		appointmentListResponse.setContent(content);
		appointmentListResponse.setPageNo(appointmentPage.getNumber());
		appointmentListResponse.setPageSize(appointmentPage.getSize());
		appointmentListResponse.setTotalElements(appointmentPage.getTotalElements());
		appointmentListResponse.setTotalPages(appointmentPage.getTotalPages());
		appointmentListResponse.setLast(appointmentPage.isLast());

		return appointmentListResponse;
	}

	@Override
	public AppointmentDto update(int id, AppointmentDto appointmentDto) throws EntityNotFoundException {
		Appointment appointmentToUpdate = appointmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Appointment with id: " + id + " could not be updated"));

		
		Date appointmentDate = appointmentDto.getDate();
		if(isDateInWorkingHours(appointmentDate)) {
			throw new RuntimeException("Appointment time should be in wokring hours!");
		}
		appointmentToUpdate.setDate(appointmentDto.getDate());

		int doctorId = appointmentDto.getDoctorId();
		if (doctorId > 0) {
			Doctor doctor = doctorRepository.findById(doctorId).get();
			appointmentToUpdate.setDoctor(doctor);
		}

		int patientId = appointmentDto.getPatientId();
		if (patientId > 0) {
			Patient patient = patientRepository.findById(patientId).get();
			appointmentToUpdate.setPatient(patient);
		}

		Appointment updatedAppointment = appointmentRepository.save(appointmentToUpdate);
		return mapToDto(updatedAppointment);
	}

	@Override
	public AppointmentDto create(AppointmentDto appointmentDto) {
		Appointment appointment = mapToEntity(appointmentDto);
		
		
		//TODO DOESNT SIOPT EXC
		if(isDateInWorkingHours(appointmentDto.getDate())) {
			throw new RuntimeException("Appointment time should be in wokring hours!");
		}
		
		Appointment createdAppointment = appointmentRepository.save(appointment);
		return mapToDto(createdAppointment);
	}
	
	@SuppressWarnings("deprecation")
	private boolean isDateInWorkingHours(Date date) {
		Calendar appointmentDate = Calendar.getInstance();
		appointmentDate.setTime(date);

		if ((appointmentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (appointmentDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			return false;
		}
		if (date.getHours() < 8 || date.getHours() + 1 >= 6) {
			return false;
		}
		return true;
	}
	

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Appointment appointmentToDelete = appointmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Appointment with id: " + id + " could not be deleted"));
		appointmentRepository.delete(appointmentToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.PATIENT)) {
			return true;
		}
		return false;
	}

	private AppointmentDto mapToDto(Appointment appointment) {
		AppointmentDto appointmentDto = new AppointmentDto();

		appointmentDto.setId(appointment.getId());
		appointmentDto.setDate(appointment.getDate());

		Doctor doctor = appointment.getDoctor();
		if (doctor != null) {
			appointmentDto.setDoctorId(doctor.getId());
		}
		Patient patient = appointment.getPatient();
		if (patient != null) {
			appointmentDto.setPatientId(patient.getId());
		}

		return appointmentDto;
	}

	private Appointment mapToEntity(AppointmentDto appointmentDto) {
		Appointment appointment = new Appointment();

		appointment.setDate(appointmentDto.getDate());

		int doctorId = appointmentDto.getDoctorId();
		if (doctorId > 0) {
			Doctor doctor = doctorRepository.findById(doctorId).get();
			appointment.setDoctor(doctor);
		}

		int patientId = appointmentDto.getPatientId();
		if (patientId > 0) {
			Patient patient = patientRepository.findById(patientId).get();
			appointment.setPatient(patient);
		}

		return appointment;
	}

}
