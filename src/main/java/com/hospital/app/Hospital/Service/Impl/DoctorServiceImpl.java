package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Doctor;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Ward;
import com.hospital.app.Hospital.Repository.DoctorRepository;
import com.hospital.app.Hospital.Repository.WardRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.DoctorService;
import com.hospital.app.Hospital.dto.DoctorDto;
import com.hospital.app.Hospital.dto.ListResponseDto;

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

		doctorToUpdate.setAge(doctorDto.getAge());
		doctorToUpdate.setFirstName(doctorDto.getFirstName());
		doctorToUpdate.setLastName(doctorDto.getLastName());
		doctorToUpdate.setNumber(doctorDto.getNumber());
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
		doctorDto.setAge(doctor.getAge());
		doctorDto.setFirstName(doctor.getFirstName());
		doctorDto.setLastName(doctor.getLastName());
		doctorDto.setNumber(doctor.getNumber());
		doctorDto.setQualification(doctor.getQualification());

		Ward ward = doctor.getWard();
		if (ward != null) {
			doctorDto.setWardId(ward.getId());
		}
		return doctorDto;
	}

	private Doctor mapToEntity(DoctorDto doctorDto) {
		Doctor doctor = new Doctor();

		doctor.setAge(doctorDto.getAge());
		doctor.setFirstName(doctorDto.getFirstName());
		doctor.setLastName(doctorDto.getLastName());
		doctor.setNumber(doctorDto.getNumber());
		doctor.setQualification(doctorDto.getQualification());

		int wardId = doctorDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			doctor.setWard(ward);
		}
		return doctor;
	}

}
