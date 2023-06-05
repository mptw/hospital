package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.WardDto;

public interface WardService {

	WardDto get(int id) throws EntityNotFoundException;

	ListResponseDto getWards(int pageNo, int pageSize);

	WardDto update(int id, WardDto wardDto) throws EntityNotFoundException;

	WardDto create(WardDto wardDto);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
