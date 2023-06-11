package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Dto.ListResponseDto;
import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.Room;

public interface RoomService {

	Room get(int id) throws EntityNotFoundException;

	ListResponseDto getRooms(int pageNo, int pageSize);

	Room update(int id, Room room) throws EntityNotFoundException;

	Room create(Room room);

	void delete(int id) throws EntityNotFoundException;

	boolean checkPermissions();
}
