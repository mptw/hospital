package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.dto.DirectorDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

public interface DirectorService {

	DirectorDto get(int id) throws EntityNotFoundException;

	ListResponseDto getDirectors(int pageNo, int pageSize);

	DirectorDto update(int id, DirectorDto directorDto) throws EntityNotFoundException;

	DirectorDto create(DirectorDto directorDto);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
