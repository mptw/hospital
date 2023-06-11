package com.hospital.app.Hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.app.Hospital.Model.PersonInfo;

public interface PersonRepository extends JpaRepository<PersonInfo, Integer> {

}
