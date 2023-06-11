package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.app.Hospital.Model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

}
