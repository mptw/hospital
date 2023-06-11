package com.hospital.app.Hospital.Service;

import java.util.Optional;

import com.hospital.app.Hospital.Dto.DoctorDto;
import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Doctor;

public interface DoctorService {

	DoctorDto get(int id) throws EntityNotFoundException;

	ListResponseDto getDoctors(int pageNo, int pageSize);

	ListResponseDto getAllInWard(int wardId, int pageNo, int pageSize);

	DoctorDto update(int id, DoctorDto doctorDto) throws EntityNotFoundException;

	DoctorDto create(DoctorDto doctorDto);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();

	DoctorDto create(Doctor doctor);

	Optional<Doctor> getDoctorIdByUserName(String doctorUsername);
}
