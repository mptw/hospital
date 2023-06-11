package com.hospital.app.Hospital.Service;

import com.hospital.app.Hospital.Exception.EntityNotFoundException;
import com.hospital.app.Hospital.Model.PersonInfo;

public interface PersonService {

	PersonInfo get(int id) throws EntityNotFoundException;

	PersonInfo update(int id, PersonInfo person) throws EntityNotFoundException;

	PersonInfo create(PersonInfo person);

	void delete(int id) throws EntityNotFoundException;

	PersonInfo getByUserId(int id) throws EntityNotFoundException;
}
