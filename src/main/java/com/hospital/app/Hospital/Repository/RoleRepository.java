package com.hospital.app.Hospital.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.app.Hospital.Model.Role;
import com.hospital.app.Hospital.Model.RoleType;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByType(RoleType type);
}
