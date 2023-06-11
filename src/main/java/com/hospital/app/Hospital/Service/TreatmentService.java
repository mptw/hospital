package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.TreatmentDto;

public interface TreatmentService {

	TreatmentDto get(int id) throws EntityNotFoundException;
	
	ListResponseDto getTreatments(int pageNo, int pageSize);
	
	ListResponseDto getTreatmentsForPatient(int patientId, int pageNo, int pageSize);
	
	TreatmentDto update(int id, TreatmentDto treatmentDto) throws EntityNotFoundException;

	TreatmentDto create(TreatmentDto treatmentDto);
	
	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
