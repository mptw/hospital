package com.hospital.app.Hospital.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.PersonInfo;
import com.hospital.app.Hospital.Repository.PersonRepository;
import com.hospital.app.Hospital.Service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public PersonInfo get(int id) throws EntityNotFoundException {
		PersonInfo person = personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Person with id: " + id + " could not be updated"));
		return person;
	}
	
	@Override
	public PersonInfo getByUserId(int id) throws EntityNotFoundException {
		PersonInfo person = personRepository.findAll().stream().filter(p -> p.getUserEntity().getId()==id).findFirst().get();
		return person;
	}
	
	@Override
	public PersonInfo update(int id, PersonInfo person) throws EntityNotFoundException {
		PersonInfo personToUpdate = personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Person with id: " + id + " could not be updated"));
		
		//personToUpdate.setType(person.getType());

		PersonInfo updatedPerson = personRepository.save(personToUpdate);
		return updatedPerson;
	}

	@Override
	public PersonInfo create(PersonInfo person) {
		return personRepository.save(person);
	}

	@Override
	public void delete(int id) throws EntityNotFoundException {
		PersonInfo personToDelete = personRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Person with id: " + id + " could not be deleted"));
		personRepository.delete(personToDelete);
	}

}
