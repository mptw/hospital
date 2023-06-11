package com.hospital.app.Hospital.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PersonInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@Column
	@NotEmpty(message = "firstName should not be empty.")
	private String firstName;

	@Column
	@NotEmpty(message = "lastName should not be empty.")
	private String lastName;

	@Column
	@Min(value = 18)
	@Max(value = 100)
	private int age;

	@Column
	@Min(value = 0, message = "number should be valid.")
	private long number;
	
	@OneToOne
	private UserEntity userEntity;
	
	// put user here instead.
//	@Nonnull
  //  private Role role;

}
