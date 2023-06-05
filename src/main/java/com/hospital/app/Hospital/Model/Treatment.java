package com.hospital.app.Hospital.Model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Treatments")
public class Treatment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private Date startDate;

	@Column
	private Date endDate;

	@Column
	private double pricePerDay;

	@Column
	private double totalPrice;
	
	@ManyToOne
	private Patient patient;
	
	@ManyToOne
	private Room room;
	
}
