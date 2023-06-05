package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Director;
import com.hospital.app.Hospital.dto.ListResponseDto;

public interface DirectorService {

	Director get(int id) throws EntityNotFoundException;

	ListResponseDto getDirectors(int pageNo, int pageSize);

	Director update(int id, Director director) throws EntityNotFoundException;

	Director create(Director director);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
