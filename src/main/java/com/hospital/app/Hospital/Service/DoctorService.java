package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.dto.DoctorDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

public interface DoctorService {

	DoctorDto get(int id) throws EntityNotFoundException;
	
	ListResponseDto getDoctors(int pageNo, int pageSize);
	
	ListResponseDto getAllInWard(int wardId, int pageNo, int pageSize);
	
	DoctorDto update(int id, DoctorDto doctorDto) throws EntityNotFoundException;

	DoctorDto create(DoctorDto doctorDto);
	
	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
