package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Dto.DirectorDto;
import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Director;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Repository.DirectorRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.DirectorService;

@Service
public class DirectorServiceImpl implements DirectorService {

	@Autowired
	private DirectorRepository directorRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public DirectorDto get(int id) throws EntityNotFoundException {
		Director director = directorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Director with id: " + id + " could not be retrieved"));
		return mapToDto(director);
	}

	@Override
	public ListResponseDto getDirectors(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Director> directorPage = directorRepository.findAll(pageable);
		List<Director> directors = directorPage.getContent();
		List<DirectorDto> content = directors.stream().map(d -> mapToDto(d)).collect(Collectors.toList());

		ListResponseDto directorListResponse = new ListResponseDto();
		directorListResponse.setContent(content);
		directorListResponse.setPageNo(directorPage.getNumber());
		directorListResponse.setPageSize(directorPage.getSize());
		directorListResponse.setTotalElements(directorPage.getTotalElements());
		directorListResponse.setTotalPages(directorPage.getTotalPages());
		directorListResponse.setLast(directorPage.isLast());

		return directorListResponse;
	}

	@Override
	public DirectorDto update(int id, DirectorDto directorDto) throws EntityNotFoundException {
		Director directorToUpdate = directorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Director with id: " + id + " could not be updated"));

		directorToUpdate.getPersonInfo().setAge(directorDto.getAge());
		directorToUpdate.getPersonInfo().setFirstName(directorDto.getFirstName());
		directorToUpdate.getPersonInfo().setLastName(directorDto.getLastName());
		directorToUpdate.getPersonInfo().setNumber(directorDto.getNumber());

		Director updatedDirector = directorRepository.save(directorToUpdate);
		return mapToDto(updatedDirector);
	}

	@Override
	public DirectorDto create(DirectorDto directorDto) {
		Director director = mapToEntity(directorDto);
		Director createdDirector = directorRepository.save(director);
		return mapToDto(createdDirector);
	}

	@Override
	public DirectorDto create(Director director) {
		Director createdDirector = directorRepository.save(director);
		return mapToDto(createdDirector);
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

	private DirectorDto mapToDto(Director director) {
		DirectorDto directorDto = new DirectorDto();
		directorDto.setId(director.getId());
		directorDto.setAge(director.getPersonInfo().getAge());
		directorDto.setFirstName(director.getPersonInfo().getFirstName());
		directorDto.setLastName(director.getPersonInfo().getLastName());
		directorDto.setNumber(director.getPersonInfo().getNumber());
		return directorDto;
	}

	private Director mapToEntity(DirectorDto directorDto) {
		Director director = new Director();
		director.getPersonInfo().setAge(directorDto.getAge());
		director.getPersonInfo().setFirstName(directorDto.getFirstName());
		director.getPersonInfo().setLastName(directorDto.getLastName());
		director.getPersonInfo().setNumber(directorDto.getNumber());
		return director;
	}
}
