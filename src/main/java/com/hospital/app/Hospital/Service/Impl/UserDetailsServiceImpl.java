package com.hospital.app.Hospital.Service.Impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.PersonInfo;
import com.hospital.app.Hospital.Model.Role;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.UserEntity;
import com.hospital.app.Hospital.Repository.RoleRepository;
import com.hospital.app.Hospital.Repository.UserRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.DirectorService;
import com.hospital.app.Hospital.Service.DoctorService;
import com.hospital.app.Hospital.Service.PatientService;
import com.hospital.app.Hospital.Service.PersonService;
import com.hospital.app.Hospital.Service.StaffService;
import com.hospital.app.Hospital.Service.UserService;
import com.hospital.app.Hospital.dto.DirectorDto;
import com.hospital.app.Hospital.dto.DoctorDto;
import com.hospital.app.Hospital.dto.PatientDto;
import com.hospital.app.Hospital.dto.StaffDto;

@Service
public class UserDetailsServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PersonService personService;

	@Autowired
	private DirectorService directorService;

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private StaffService staffService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException("User with username: " + username + " could not be retrieved"));

		return new User(user.getUsername(), user.getPassword(),
				mapRolesToAuthorities(Collections.singletonList(user.getRole())));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public void saveUser(UserEntity userEntityDto) {
		UserEntity user = new UserEntity();
		user.setUsername(userEntityDto.getUsername());
		user.setPassword(passwordEncoder.encode(userEntityDto.getPassword()));

		RoleType requestRole = RoleType.valueOf(userEntityDto.getRole().getName());
		Role role = roleRepository.findByType(requestRole).get();
		user.setRole(role);
		user.setWantedRole(userEntityDto.getWantedRole());

		userRepository.save(user);
	}

	@Override
	public void updateUser(UserEntity userEntity) {
		UserEntity userToUpdate = userRepository.findByUsername(userEntity.getUsername())
				.orElseThrow(() -> new EntityNotFoundException(
						"User with username: " + userEntity.getUsername() + " could not be retrieved"));

		userToUpdate.setUsername(userEntity.getUsername());
		RoleType requestRole = RoleType.valueOf(userEntity.getRole().getName());
		Role role = roleRepository.findByType(requestRole).get();
		userToUpdate.setRole(role);
		UserEntity user = userRepository.save(userToUpdate);

		PersonInfo personInfo = personService.getByUserId(userEntity.getId());
		if (requestRole.equals(RoleType.DIRECTOR)) {
			DirectorDto directorDto = new DirectorDto(personInfo);
			directorService.create(directorDto);
		} else if (requestRole.equals(RoleType.DOCTOR)) {
			DoctorDto doctorDto = new DoctorDto(personInfo);
			doctorService.create(doctorDto);
		} else if (requestRole.equals(RoleType.STAFF)) {
			StaffDto staffDto = new StaffDto(personInfo);
			staffService.create(staffDto);
		} else if (requestRole.equals(RoleType.PATIENT)) {
			PatientDto patientDto = new PatientDto(personInfo);
			patientService.create(patientDto);
		}
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Optional<UserEntity> findById(int id) {
		return userRepository.findById(id);
	}

	@Override
	public boolean areUserDetailsValid(UserEntity userEntity) {
		Optional<UserEntity> user = userRepository.findByUsername(userEntity.getUsername());
		if (user.isPresent()) {
			if (passwordEncoder.matches(userEntity.getPassword(), user.get().getPassword())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<UserEntity> getUsersWaitingForApproval() {
		return userRepository.findAll().stream().filter(u -> u.getRole().getType().equals(RoleType.ANNONYMOUS))
				.toList();
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.ADMIN)) {
			return true;
		}
		return false;
	}

	@Override
	public void delete(String username) throws EntityNotFoundException {
		UserEntity userToDelete = userRepository.findByUsername(username).orElseThrow(
				() -> new EntityNotFoundException("User with username: " + username + " could not be retrieved"));

		userRepository.delete(userToDelete);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		UserEntity userToDelete = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " could not be retrieved"));

		userRepository.delete(userToDelete);
	}

}
