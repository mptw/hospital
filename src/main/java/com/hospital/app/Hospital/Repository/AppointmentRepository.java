package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.app.Hospital.Model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

}
