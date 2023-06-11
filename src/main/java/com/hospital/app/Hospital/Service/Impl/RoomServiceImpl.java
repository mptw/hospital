package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Room;
import com.hospital.app.Hospital.Repository.RoomRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public Room get(int id) throws EntityNotFoundException {
		return roomRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Room with id: " + id + " could not be retrieved"));
	}

	@Override
	public ListResponseDto getRooms(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Room> roomPage = roomRepository.findAll(pageable);
		List<Room> content = roomPage.getContent().stream().collect(Collectors.toList());

		ListResponseDto roomListResponse = new ListResponseDto();
		roomListResponse.setContent(content);
		roomListResponse.setPageNo(roomPage.getNumber());
		roomListResponse.setPageSize(roomPage.getSize());
		roomListResponse.setTotalElements(roomPage.getTotalElements());
		roomListResponse.setTotalPages(roomPage.getTotalPages());
		roomListResponse.setLast(roomPage.isLast());

		return roomListResponse;
	}

	@Override
	public Room update(int id, Room room) throws EntityNotFoundException {
		Room roomToUpdate = roomRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Room with id: " + id + " could not be updated"));

		roomToUpdate.setType(room.getType());

		Room updatedRoom = roomRepository.save(roomToUpdate);
		return updatedRoom;
	}

	@Override
	public Room create(Room room) {
		return roomRepository.save(room);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		Room roomToDelete = roomRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Room with id: " + id + " could not be deleted"));
		roomRepository.delete(roomToDelete);
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.STAFF)) {
			return true;
		}
		return false;
	}

}
