package com.hospital.app.Hospital.dto;

import java.util.Date;

import com.hospital.app.Hospital.Model.Appointment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

		private int id;
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