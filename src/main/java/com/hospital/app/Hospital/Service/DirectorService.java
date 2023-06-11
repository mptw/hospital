package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Dto.DirectorDto;
import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Director;

public interface DirectorService {

	DirectorDto get(int id) throws EntityNotFoundException;

	ListResponseDto getDirectors(int pageNo, int pageSize);

	DirectorDto update(int id, DirectorDto directorDto) throws EntityNotFoundException;

	DirectorDto create(DirectorDto directorDto);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();

	DirectorDto create(Director director);
}
