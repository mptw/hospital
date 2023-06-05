package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.PatientDto;

public interface PatientService {

	PatientDto get(int id) throws EntityNotFoundException;
	
	ListResponseDto getPatients(int pageNo, int pageSize);
	
	ListResponseDto getAllByDoctor(int doctorId, int pageNo, int pageSize);
	
	ListResponseDto getAllByWard(int wardId, int pageNo, int pageSize);
	
	PatientDto update(int id, PatientDto patientDto) throws EntityNotFoundException;

	PatientDto create(PatientDto patientDto);
	
	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
