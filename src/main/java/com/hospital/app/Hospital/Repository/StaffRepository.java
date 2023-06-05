package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.app.Hospital.Model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

}
