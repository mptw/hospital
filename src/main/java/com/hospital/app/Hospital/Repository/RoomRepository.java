package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.app.Hospital.Model.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

}
