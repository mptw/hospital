package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Dto.DoctorDto;
import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Doctor;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Ward;
import com.hospital.app.Hospital.Repository.DoctorRepository;
import com.hospital.app.Hospital.Repository.WardRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private WardRepository wardRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public DoctorDto get(int id) throws EntityNotFoundException {
		Doctor doctor = doctorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor with id: " + id + " could not be retrieved"));
		return mapToDto(doctor);
	}

	@Override
	public Optional<Doctor> getDoctorIdByUserName(String doctorUsername) {
		return doctorRepository.findAll().stream()
				.filter(d -> d.getPersonInfo().getUserEntity().getUsername().equals(doctorUsername)).findFirst();
	}

	@Override
	public ListResponseDto getDoctors(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
		List<Doctor> doctors = doctorPage.getContent();
		List<DoctorDto> content = doctors.stream().map(d -> mapToDto(d)).collect(Collectors.toList());

		ListResponseDto doctorListResponse = new ListResponseDto();
		doctorListResponse.setContent(content);
		doctorListResponse.setPageNo(doctorPage.getNumber());
		doctorListResponse.setPageSize(doctorPage.getSize());
		doctorListResponse.setTotalElements(doctorPage.getTotalElements());
		doctorListResponse.setTotalPages(doctorPage.getTotalPages());
		doctorListResponse.setLast(doctorPage.isLast());

		return doctorListResponse;
	}

	@Override
	public ListResponseDto getAllInWard(int wardId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Doctor> doctorPage = doctorRepository.findAll(pageable);
		List<Doctor> doctors = doctorPage.getContent();
		List<DoctorDto> content = doctors.stream().filter(d -> d.getWard() != null && d.getWard().getId() == wardId)
				.map(p -> mapToDto(p)).collect(Collectors.toList());

		ListResponseDto doctorListResponse = new ListResponseDto();
		doctorListResponse.setContent(content);
		doctorListResponse.setPageNo(doctorPage.getNumber());
		doctorListResponse.setPageSize(doctorPage.getSize());
		doctorListResponse.setTotalElements(doctorPage.getTotalElements());
		doctorListResponse.setTotalPages(doctorPage.getTotalPages());
		doctorListResponse.setLast(doctorPage.isLast());

		return doctorListResponse;
	}

	@Override
	public DoctorDto update(int id, DoctorDto doctorDto) {
		Doctor doctorToUpdate = doctorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor with id: " + id + " could not be updated"));

		doctorToUpdate.getPersonInfo().setAge(doctorDto.getAge());
		doctorToUpdate.getPersonInfo().setFirstName(doctorDto.getFirstName());
		doctorToUpdate.getPersonInfo().setLastName(doctorDto.getLastName());
		doctorToUpdate.getPersonInfo().setNumber(doctorDto.getNumber());
		doctorToUpdate.setQualification(doctorDto.getQualification());

		int wardId = doctorDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			doctorToUpdate.setWard(ward);
		}

		Doctor updatedDoctor = doctorRepository.save(doctorToUpdate);
		return mapToDto(updatedDoctor);
	}

	@Override
	public DoctorDto create(DoctorDto doctorDto) {
		Doctor doctor = mapToEntity(doctorDto);
		Doctor createdDoctor = doctorRepository.save(doctor);
		return mapToDto(createdDoctor);
	}

	@Override
	public DoctorDto create(Doctor doctor) {
		Doctor createdDoctor = doctorRepository.save(doctor);
		return mapToDto(createdDoctor);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Doctor doctorToDelete = doctorRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor with id: " + id + " could not be deleted"));
		doctorRepository.delete(doctorToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.DOCTOR)) {
			return true;
		}
		return false;
	}

	private DoctorDto mapToDto(Doctor doctor) {
		DoctorDto doctorDto = new DoctorDto();

		doctorDto.setId(doctor.getId());
		doctorDto.setAge(doctor.getPersonInfo().getAge());
		doctorDto.setFirstName(doctor.getPersonInfo().getFirstName());
		doctorDto.setLastName(doctor.getPersonInfo().getLastName());
		doctorDto.setNumber(doctor.getPersonInfo().getNumber());
		doctorDto.setQualification(doctor.getQualification());

		Ward ward = doctor.getWard();
		if (ward != null) {
			doctorDto.setWardId(ward.getId());
		}
		return doctorDto;
	}

	private Doctor mapToEntity(DoctorDto doctorDto) {
		Doctor doctor = new Doctor();

		doctor.getPersonInfo().setAge(doctorDto.getAge());
		doctor.getPersonInfo().setFirstName(doctorDto.getFirstName());
		doctor.getPersonInfo().setLastName(doctorDto.getLastName());
		doctor.getPersonInfo().setNumber(doctorDto.getNumber());
		doctor.setQualification(doctorDto.getQualification());

		int wardId = doctorDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			doctor.setWard(ward);
		}
		return doctor;
	}
}
