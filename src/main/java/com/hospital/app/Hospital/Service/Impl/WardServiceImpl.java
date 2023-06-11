package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Dto.WardDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Doctor;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Ward;
import com.hospital.app.Hospital.Repository.DoctorRepository;
import com.hospital.app.Hospital.Repository.WardRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.WardService;

@Service
public class WardServiceImpl implements WardService {

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private WardRepository wardRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public WardDto get(int id) throws EntityNotFoundException {
		Ward ward = wardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ward with id: " + id + " could not be retrieved"));
		return mapToDto(ward);
	}

	@Override
	public ListResponseDto getWards(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Ward> wardPade = wardRepository.findAll(pageable);
		List<Ward> wards = wardPade.getContent();
		List<WardDto> content = wards.stream().map(w -> mapToDto(w)).collect(Collectors.toList());

		ListResponseDto doctorListResponse = new ListResponseDto();
		doctorListResponse.setContent(content);
		doctorListResponse.setPageNo(wardPade.getNumber());
		doctorListResponse.setPageSize(wardPade.getSize());
		doctorListResponse.setTotalElements(wardPade.getTotalElements());
		doctorListResponse.setTotalPages(wardPade.getTotalPages());
		doctorListResponse.setLast(wardPade.isLast());

		return doctorListResponse;
	}

	@Override
	public WardDto update(int id, WardDto wardDto) throws EntityNotFoundException {
		Ward wardToUpdate = wardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ward with id: " + id + " could not be updated"));

		wardToUpdate.setName(wardDto.getName());

		int doctorId = wardDto.getHeadDoctorId();
		if (doctorId > 0) {
			Doctor headDoctor = doctorRepository.findById(doctorId).get();
			wardToUpdate.setHeadDoctor(headDoctor);
		}

		Ward updatedWard = wardRepository.save(wardToUpdate);
		return mapToDto(updatedWard);
	}

	@Override
	public WardDto create(WardDto wardDto) {
		Ward ward = mapToEntity(wardDto);
		Ward createdWard = wardRepository.save(ward);
		return mapToDto(createdWard);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Ward wardToDelete = wardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ward with id: " + id + " could not be deleted"));
		wardRepository.delete(wardToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.DOCTOR)) {
			return true;
		}
		return false;
	}

	private WardDto mapToDto(Ward ward) {
		WardDto wardDto = new WardDto();

		wardDto.setId(ward.getId());
		wardDto.setName(ward.getName());

		Doctor headDoctor = ward.getHeadDoctor();
		if (headDoctor != null) {
			wardDto.setHeadDoctorId(headDoctor.getId());
		}

		return wardDto;
	}

	private Ward mapToEntity(WardDto wardDto) {
		Ward ward = new Ward();

		ward.setName(wardDto.getName());

		int doctorId = wardDto.getHeadDoctorId();
		if (doctorId > 0) {
			Doctor headDoctor = doctorRepository.findById(doctorId).get();
			ward.setHeadDoctor(headDoctor);
		}
		return ward;
	}

}
