package com.hospital.app.Hospital.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.hospital.app.Hospital.Model.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

	private int id;

	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date date;

	private int doctorId;
	private int patientId;

	public AppointmentDto(Appointment appointment) {
		this.id = appointment.getId();
		this.date = appointment.getDate();
		this.doctorId = appointment.getDoctor() != null ? appointment.getDoctor().getId() : null;
		this.patientId = appointment.getPatient() != null ? appointment.getPatient().getId() : null;
	}
}
