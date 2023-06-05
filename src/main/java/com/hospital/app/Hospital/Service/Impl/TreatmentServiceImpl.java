package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Patient;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Room;
import com.hospital.app.Hospital.Model.Treatment;
import com.hospital.app.Hospital.Repository.PatientRepository;
import com.hospital.app.Hospital.Repository.RoomRepository;
import com.hospital.app.Hospital.Repository.TreatmentRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Security.TreatmentService;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.TreatmentDto;

@Service
public class TreatmentServiceImpl implements TreatmentService {

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public TreatmentDto get(int id) throws EntityNotFoundException {
		Treatment treatment = treatmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Treatment with id: " + id + " could not be retrieved"));
		return mapToDto(treatment);
	}

	@Override
	public ListResponseDto getTreatments(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Treatment> treatmentPage = treatmentRepository.findAll(pageable);
		List<Treatment> treatments = treatmentPage.getContent();
		List<TreatmentDto> content = treatments.stream().map(t -> mapToDto(t)).collect(Collectors.toList());

		ListResponseDto treatmentListResponse = new ListResponseDto();
		treatmentListResponse.setContent(content);
		treatmentListResponse.setPageNo(treatmentPage.getNumber());
		treatmentListResponse.setPageSize(treatmentPage.getSize());
		treatmentListResponse.setTotalElements(treatmentPage.getTotalElements());
		treatmentListResponse.setTotalPages(treatmentPage.getTotalPages());
		treatmentListResponse.setLast(treatmentPage.isLast());

		return treatmentListResponse;
	}

	@Override
	public ListResponseDto getTreatmentsForPatient(int patientId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Treatment> treatmentPage = treatmentRepository.findAll(pageable);
		List<Treatment> treatments = treatmentPage.getContent();
		List<TreatmentDto> content = treatments.stream()
				.filter(t -> t.getPatient() != null && t.getPatient().getId() == patientId).map(t -> mapToDto(t))
				.collect(Collectors.toList());

		ListResponseDto treatmentListResponse = new ListResponseDto();
		treatmentListResponse.setContent(content);
		treatmentListResponse.setPageNo(treatmentPage.getNumber());
		treatmentListResponse.setPageSize(treatmentPage.getSize());
		treatmentListResponse.setTotalElements(treatmentPage.getTotalElements());
		treatmentListResponse.setTotalPages(treatmentPage.getTotalPages());
		treatmentListResponse.setLast(treatmentPage.isLast());

		return treatmentListResponse;
	}

	@Override
	public TreatmentDto update(int id, TreatmentDto treatmentDto) throws EntityNotFoundException {
		Treatment treatmentToUpdate = treatmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Treatment with id: " + id + " could not be updated"));

		treatmentToUpdate.setStartDate(treatmentDto.getStartDate());
		treatmentToUpdate.setEndDate(treatmentDto.getEndDate());
		treatmentToUpdate.setPricePerDay(treatmentDto.getPricePerDay());

		// TODO - calculate total price
		treatmentToUpdate.setTotalPrice(treatmentDto.getTotalPrice());

		int patientId = treatmentDto.getPatientId();
		if (patientId > 0) {
			Patient patient = patientRepository.findById(patientId).get();
			treatmentToUpdate.setPatient(patient);
		}

		int roomId = treatmentDto.getRoomId();
		if (roomId > 0) {
			Room room = roomRepository.findById(roomId).get();
			treatmentToUpdate.setRoom(room);
		}

		Treatment updatedTreatment = treatmentRepository.save(treatmentToUpdate);
		return mapToDto(updatedTreatment);
	}

	@Override
	public TreatmentDto create(TreatmentDto treatmentDto) {
		Treatment treatment = mapToEntity(treatmentDto);
		Treatment createdTreatment = treatmentRepository.save(treatment);
		return mapToDto(createdTreatment);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Treatment treatmentToDelete = treatmentRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Treatment with id: " + id + " could not be deleted"));
		treatmentRepository.delete(treatmentToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.DOCTOR)) {
			return true;
		}
		return false;
	}

	private TreatmentDto mapToDto(Treatment treatment) {
		TreatmentDto treatmentDto = new TreatmentDto();

		treatmentDto.setId(treatment.getId());
		treatmentDto.setStartDate(treatment.getStartDate());
		treatmentDto.setEndDate(treatment.getEndDate());
		treatmentDto.setPricePerDay(treatment.getPricePerDay());

		// TODO - calculate total price
		treatmentDto.setTotalPrice(treatment.getTotalPrice());

		Patient patient = treatment.getPatient();
		if (patient != null) {
			treatmentDto.setPatientId(patient.getId());
		}

		Room room = treatment.getRoom();
		if (room != null) {
			treatmentDto.setRoomId(room.getId());
		}
		return treatmentDto;
	}

	private Treatment mapToEntity(TreatmentDto treatmentDto) {
		Treatment treatment = new Treatment();

		treatment.setStartDate(treatmentDto.getStartDate());
		treatment.setEndDate(treatmentDto.getEndDate());
		treatment.setPricePerDay(treatmentDto.getPricePerDay());

		// TODO - calculate total price
		treatment.setTotalPrice(treatmentDto.getTotalPrice());

		int patientId = treatmentDto.getPatientId();
		if (patientId > 0) {
			Patient patient = patientRepository.findById(patientId).get();
			treatment.setPatient(patient);
		}

		int roomId = treatmentDto.getRoomId();
		if (roomId > 0) {
			Room room = roomRepository.findById(roomId).get();
			treatment.setRoom(room);
		}
		return treatment;
	}

}
