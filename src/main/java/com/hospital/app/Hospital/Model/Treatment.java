package com.hospital.app.Hospital.Model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startDate;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endDate;

	@Column
	private double pricePerDay;

	@Column
	private double totalPrice;

	public double getTotalPrice() {
		double price = 0;
		if (startDate != null && endDate != null) {
			long milliseconds = endDate.getTime() - startDate.getTime();
			long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
			price = pricePerDay * days;
		}
		return price;
	}

	@ManyToOne
	private Patient patient;

	@ManyToOne
	private Room room;

}
