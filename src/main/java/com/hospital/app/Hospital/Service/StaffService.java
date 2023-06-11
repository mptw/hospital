package com.hospital.app.Hospital.Service;

import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Dto.StaffDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Staff;

@Service
public interface StaffService {

	StaffDto get(int id) throws EntityNotFoundException;

	ListResponseDto getStaff(int pageNo, int pageSize);

	ListResponseDto getAllByWard(int wardId, int pageNo, int pageSize);

	StaffDto update(int id, StaffDto staffDto) throws EntityNotFoundException;

	StaffDto create(StaffDto staffDto);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();

	StaffDto create(Staff staff);
}
