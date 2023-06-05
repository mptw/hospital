package com.hospital.app.Hospital.Model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private int age;

	@Column
	private long number;
	
	// put user here instead.
//	@Nonnull
  //  private Role role;

}
