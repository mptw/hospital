package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.app.Hospital.Model.Treatment;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {

}
