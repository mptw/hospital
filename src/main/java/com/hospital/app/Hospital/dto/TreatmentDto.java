package com.hospital.app.Hospital.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hospital.app.Hospital.Model.Treatment;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreatmentDto {

	private int id;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date endDate;

	@Min(value = 0)
	private double pricePerDay;

	private double totalPrice;
	private int patientId;
	private int roomId;

	public TreatmentDto(Treatment treatment) {
		this.id = treatment.getId();
		this.startDate = treatment.getStartDate();
		this.endDate = treatment.getEndDate();
		this.pricePerDay = treatment.getPricePerDay();
		this.totalPrice = treatment.getTotalPrice();
		this.patientId = treatment.getPatient() != null ? treatment.getPatient().getId() : null;
		this.roomId = treatment.getRoom() != null ? treatment.getRoom().getId() : null;
	}
}
