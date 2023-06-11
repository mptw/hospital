package com.hospital.app.Hospital.Service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.Staff;
import com.hospital.app.Hospital.Model.Ward;
import com.hospital.app.Hospital.Repository.StaffRepository;
import com.hospital.app.Hospital.Repository.WardRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.Service.StaffService;
import com.hospital.app.Hospital.dto.ListResponseDto;
import com.hospital.app.Hospital.dto.StaffDto;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@Autowired
	private WardRepository wardRepository;
/*
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PersonService personService;
	*/
	@Autowired
	private JWTGenerator tokenGenerator;

	@Override
	public StaffDto get(int id) throws EntityNotFoundException {
		Staff staff = staffRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Staff with id: " + id + " could not be retrieved"));
		return mapToDto(staff);
	}

	@Override
	public ListResponseDto getStaff(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Staff> staffPage = staffRepository.findAll(pageable);
		List<Staff> staff = staffPage.getContent();
		List<StaffDto> content = staff.stream().map(s -> mapToDto(s)).collect(Collectors.toList());

		ListResponseDto staffListResponse = new ListResponseDto();
		staffListResponse.setContent(content);
		staffListResponse.setPageNo(staffPage.getNumber());
		staffListResponse.setPageSize(staffPage.getSize());
		staffListResponse.setTotalElements(staffPage.getTotalElements());
		staffListResponse.setTotalPages(staffPage.getTotalPages());
		staffListResponse.setLast(staffPage.isLast());

		return staffListResponse;
	}

	@Override
	public ListResponseDto getAllByWard(int wardId, int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Staff> staffPage = staffRepository.findAll(pageable);
		List<Staff> staff = staffPage.getContent();
		List<StaffDto> content = staff.stream().filter(s -> s.getWard() != null && s.getWard().getId() == wardId)
				.map(s -> mapToDto(s)).collect(Collectors.toList());

		ListResponseDto staffListResponse = new ListResponseDto();
		staffListResponse.setContent(content);
		staffListResponse.setPageNo(staffPage.getNumber());
		staffListResponse.setPageSize(staffPage.getSize());
		staffListResponse.setTotalElements(staffPage.getTotalElements());
		staffListResponse.setTotalPages(staffPage.getTotalPages());
		staffListResponse.setLast(staffPage.isLast());

		return staffListResponse;
	}

	@Override
	public StaffDto update(int id, StaffDto staffDto) {
		Staff staffToUpdate = staffRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Staff with id: " + id + " could not be updated"));

		staffToUpdate.setId(staffDto.getId());
		staffToUpdate.getPersonInfo().setAge(staffDto.getAge());
		staffToUpdate.getPersonInfo().setFirstName(staffDto.getFirstName());
		staffToUpdate.getPersonInfo().setLastName(staffDto.getLastName());
		staffToUpdate.getPersonInfo().setNumber(staffDto.getNumber());
		staffToUpdate.setType(staffDto.getType());

		int wardId = staffDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			staffToUpdate.setWard(ward);
		}

		Staff updatedStaff = staffRepository.save(staffToUpdate);
		return mapToDto(updatedStaff);
	}

	@Override
	public StaffDto create(StaffDto staffDto) {
		Staff staff = mapToEntity(staffDto);
	//	UserEntity user = getUserEntity(staffDto);
	//	staff.getPersonInfo().setUserEntity(saveAndReturnUserEntity(user));
	//	staff.setPersonInfo(getPersonInfo(staff.getPersonInfo().getUserEntity(), staffDto));
		
		Staff createdStaff = staffRepository.save(staff);
		return mapToDto(createdStaff);
	}
/*
	private UserEntity getUserEntity(StaffDto staffDto) {
		UserEntity user = new UserEntity();
		user.setUsername(staffDto.getUsername());	
		user.setPassword(staffDto.getPassword());
		Role role = roleRepository.findByType(RoleType.STAFF).get();	
		user.setRole(role);
		return user;
	}
	
	private UserEntity saveAndReturnUserEntity(UserEntity user) {
		userService.saveUser(user);
		return userService.findByUsername(user.getUsername()).get();
	}
	
	private PersonInfo getPersonInfo(UserEntity user, StaffDto staffDto) {
		PersonInfo person = new PersonInfo();
		person.setFirstName(staffDto.getFirstName());
		person.setLastName(staffDto.getLastName());
		person.setAge(staffDto.getAge());
		person.setNumber(staffDto.getNumber());
		person.setUserEntity(user);

		return personService.create(person);
	}
*/	
	@Override
	public void delete(int id) throws EntityNotFoundException {
		Staff staffToDelete = staffRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Staff with id: " + id + " could not be deleted"));
		staffRepository.delete(staffToDelete);
	}

	private StaffDto mapToDto(Staff staff) {
		StaffDto staffDto = new StaffDto();

		staffDto.setId(staff.getId());
		staffDto.setAge(staff.getPersonInfo().getAge());
		staffDto.setFirstName(staff.getPersonInfo().getFirstName());
		staffDto.setLastName(staff.getPersonInfo().getLastName());
		staffDto.setNumber(staff.getPersonInfo().getNumber());
		staffDto.setType(staff.getType());

		Ward ward = staff.getWard();
		if (ward != null) {
			staffDto.setWardId(ward.getId());
		}

		return staffDto;
	}

	private Staff mapToEntity(StaffDto staffDto) {
		Staff staff = new Staff();

		staff.getPersonInfo().setAge(staffDto.getAge());
		staff.getPersonInfo().setFirstName(staffDto.getFirstName());
		staff.getPersonInfo().setLastName(staffDto.getLastName());
		staff.getPersonInfo().setNumber(staffDto.getNumber());
		staff.setType(staffDto.getType());
		
		int wardId = staffDto.getWardId();
		if (wardId > 0) {
			Ward ward = wardRepository.findById(wardId).get();
			staff.setWard(ward);
		}
		
		return staff;
	}

	@Override
	public boolean checkPermissions() {
		if (tokenGenerator.doesLoggedInUserHaveNeededRole(RoleType.STAFF)) {
			return true;
		}
		return false;
	}

}
