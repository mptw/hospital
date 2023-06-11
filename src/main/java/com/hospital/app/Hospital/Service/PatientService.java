package com.hospital.app.Hospital.Service;

import java.util.Optional;

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Dto.PatientDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Patient;

public interface PatientService {

	PatientDto get(int id) throws EntityNotFoundException;

	ListResponseDto getPatients(int pageNo, int pageSize);

	ListResponseDto getAllByDoctor(int doctorId, int pageNo, int pageSize);

	ListResponseDto getAllByWard(int wardId, int pageNo, int pageSize);

	PatientDto update(int id, PatientDto patientDto) throws EntityNotFoundException;

	PatientDto create(PatientDto patientDto);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();

	PatientDto create(Patient patient);

	Optional<Patient> getPatientIdByUserName(String patientUsername);
}
