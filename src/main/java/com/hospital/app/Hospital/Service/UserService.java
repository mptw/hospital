package com.hospital.app.Hospital.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.UserEntity;

@Service
public interface UserService extends UserDetailsService {

	UserDetails loadUserByUsername(String username) throws EntityNotFoundException;

	void saveUser(UserEntity userEntity);

	Optional<UserEntity> findById(int id);
	
	Optional<UserEntity> findByUsername(String username);

	boolean areUserDetailsValid(UserEntity userEntity);

	List<UserEntity> getUsersWaitingForApproval();

	boolean checkPermissions();

	void updateUser(UserEntity userEntity);

	void delete(String username) throws EntityNotFoundException;
	
	void delete(int id) throws EntityNotFoundException;
}
