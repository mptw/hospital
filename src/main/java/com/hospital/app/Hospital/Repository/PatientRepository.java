package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.app.Hospital.Model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
