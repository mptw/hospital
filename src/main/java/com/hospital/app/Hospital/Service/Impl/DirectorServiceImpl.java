package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Director;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Repository.DirectorRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.DirectorService;
import com.hospital.app.Hospital.dto.ListResponseDto;

@Service
public class DirectorServiceImpl implements DirectorService {

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public Director get(int id) throws EntityNotFoundException {
		return directorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Director with id: " + id + " could not be retrieved"));
	}

	@Override
	public ListResponseDto getDirectors(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Director> directorPage = directorRepository.findAll(pageable);
		List<Director> directorssContent = directorPage.getContent().stream().collect(Collectors.toList());

		ListResponseDto doctorListResponse = new ListResponseDto();
		doctorListResponse.setContent(directorssContent);
		doctorListResponse.setPageNo(directorPage.getNumber());
		doctorListResponse.setPageSize(directorPage.getSize());
		doctorListResponse.setTotalElements(directorPage.getTotalElements());
		doctorListResponse.setTotalPages(directorPage.getTotalPages());
		doctorListResponse.setLast(directorPage.isLast());

		return doctorListResponse;
	}

	@Override
	public Director update(int id, Director director) throws EntityNotFoundException {
		Director directorToUpdate = directorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Director with id: " + id + " could not be updated"));

		directorToUpdate.setAge(director.getAge());
		directorToUpdate.setFirstName(director.getFirstName());
		directorToUpdate.setLastName(director.getLastName());
		directorToUpdate.setNumber(director.getNumber());

		Director updatedDirector = directorRepository.save(directorToUpdate);
		return updatedDirector;
	}

	@Override
	public Director create(Director director) {
		return directorRepository.save(director);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Director directorToDelete = directorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Director with id: " + id + " could not be deleted"));
		directorRepository.delete(directorToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.DIRECTOR)) {
			return true;
		}
		return false;
	}

}
