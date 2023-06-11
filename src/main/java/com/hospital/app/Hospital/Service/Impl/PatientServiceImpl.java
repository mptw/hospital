package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Dto.PatientDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Doctor;
import com.hospital.app.Hospital.Model.Patient;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Ward;
import com.hospital.app.Hospital.Repository.DoctorRepository;
import com.hospital.app.Hospital.Repository.PatientRepository;
import com.hospital.app.Hospital.Repository.WardRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private WardRepository wardRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public PatientDto get(int id) throws EntityNotFoundException {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient with id: " + id + " could not be retrieved"));
		return mapToDto(patient);
	}

	@Override
	public Optional<Patient> getPatientIdByUserName(String patientUsername) {
		return patientRepository.findAll().stream()
				.filter(d -> d.getPersonInfo().getUserEntity().getUsername().equals(patientUsername)).findFirst();
	}

	@Override
	public ListResponseDto getPatients(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Patient> patientPage = patientRepository.findAll(pageable);
		List<Patient> patients = patientPage.getContent();
		List<PatientDto> content = patients.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

		ListResponseDto patientListResponse = new ListResponseDto();
		patientListResponse.setContent(content);
		patientListResponse.setPageNo(patientPage.getNumber());
		patientListResponse.setPageSize(patientPage.getSize());
		patientListResponse.setTotalElements(patientPage.getTotalElements());
		patientListResponse.setTotalPages(patientPage.getTotalPages());
		patientListResponse.setLast(patientPage.isLast());

		return patientListResponse;
	}

	@Override
	public ListResponseDto getAllByDoctor(int doctorId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Patient> patientPage = patientRepository.findAll(pageable);
		List<Patient> patients = patientPage.getContent();
		List<PatientDto> content = patients.stream()
				.filter(p -> p.getDoctor() != null && p.getDoctor().getId() == doctorId).map(p -> mapToDto(p))
				.collect(Collectors.toList());

		ListResponseDto patientListResponse = new ListResponseDto();
		patientListResponse.setContent(content);
		patientListResponse.setPageNo(patientPage.getNumber());
		patientListResponse.setPageSize(patientPage.getSize());
		patientListResponse.setTotalElements(patientPage.getTotalElements());
		patientListResponse.setTotalPages(patientPage.getTotalPages());
		patientListResponse.setLast(patientPage.isLast());

		return patientListResponse;
	}

	@Override
	public ListResponseDto getAllByWard(int wardId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Patient> patientPage = patientRepository.findAll(pageable);
		List<Patient> patients = patientPage.getContent();
		List<PatientDto> content = patients.stream().filter(p -> p.getWard() != null && p.getWard().getId() == wardId)
				.map(p -> mapToDto(p)).collect(Collectors.toList());

		ListResponseDto patientListResponse = new ListResponseDto();
		patientListResponse.setContent(content);
		patientListResponse.setPageNo(patientPage.getNumber());
		patientListResponse.setPageSize(patientPage.getSize());
		patientListResponse.setTotalElements(patientPage.getTotalElements());
		patientListResponse.setTotalPages(patientPage.getTotalPages());
		patientListResponse.setLast(patientPage.isLast());

		return patientListResponse;
	}

	@Override
	public PatientDto update(int id, PatientDto patientDto) {
		Patient patientToUpdate = patientRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient with id: " + id + " could not be updated"));

		patientToUpdate.setId(patientDto.getId());
		patientToUpdate.getPersonInfo().setAge(patientDto.getAge());
		patientToUpdate.getPersonInfo().setFirstName(patientDto.getFirstName());
		patientToUpdate.getPersonInfo().setLastName(patientDto.getLastName());
		patientToUpdate.getPersonInfo().setNumber(patientDto.getNumber());
		patientToUpdate.setDisease(patientDto.getDisease());
		patientToUpdate.setTreatment(patientDto.getTreatment());

		int doctorId = patientDto.getDoctorId();
		if (doctorId > 0) {
			Doctor doctor = doctorRepository.findById(doctorId).get();
			patientToUpdate.setDoctor(doctor);
		}

		int wardId = patientDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			patientToUpdate.setWard(ward);
		}

		Patient updatedPatient = patientRepository.save(patientToUpdate);
		return mapToDto(updatedPatient);
	}

	@Override
	public PatientDto create(PatientDto patientDto) {
		Patient patient = mapToEntity(patientDto);
		Patient createdPatient = patientRepository.save(patient);
		return mapToDto(createdPatient);
	}

	@Override
	public PatientDto create(Patient patient) {
		Patient createdPatient = patientRepository.save(patient);
		return mapToDto(createdPatient);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Patient patientToDelete = patientRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient with id: " + id + " could not be deleted"));
		patientRepository.delete(patientToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.PATIENT)) {
			return true;
		}
		return false;
	}

	private PatientDto mapToDto(Patient patient) {
		PatientDto patientDto = new PatientDto();

		patientDto.setId(patient.getId());
		patientDto.setAge(patient.getPersonInfo().getAge());
		patientDto.setFirstName(patient.getPersonInfo().getFirstName());
		patientDto.setLastName(patient.getPersonInfo().getLastName());
		patientDto.setNumber(patient.getPersonInfo().getNumber());
		patientDto.setDisease(patient.getDisease());
		patientDto.setTreatment(patient.getTreatment());

		Doctor doctor = patient.getDoctor();
		if (doctor != null) {
			patientDto.setDoctorId(doctor.getId());
		}
		Ward ward = patient.getWard();
		if (ward != null) {
			patientDto.setWardId(ward.getId());
		}

		return patientDto;
	}

	private Patient mapToEntity(PatientDto patientDto) {
		Patient patient = new Patient();

		patient.getPersonInfo().setAge(patientDto.getAge());
		patient.getPersonInfo().setFirstName(patientDto.getFirstName());
		patient.getPersonInfo().setLastName(patientDto.getLastName());
		patient.getPersonInfo().setNumber(patientDto.getNumber());
		patient.setDisease(patientDto.getDisease());
		patient.setTreatment(patientDto.getTreatment());

		int doctorId = patientDto.getDoctorId();
		if (doctorId > 0) {
			Doctor doctor = doctorRepository.findById(doctorId).get();
			patient.setDoctor(doctor);
		}

		int wardId = patientDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			patient.setWard(ward);
		}

		return patient;
	}

	public boolean checkPermissionsForSpecificPatient(int id) {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.DOCTOR)) {
			return true;
		}
		return false;
	}
}
