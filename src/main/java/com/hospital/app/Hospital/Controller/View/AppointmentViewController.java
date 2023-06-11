package com.hospital.app.Hospital.Controller.View;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.app.Hospital.Service.AppointmentService;
import com.hospital.app.Hospital.dto.AppointmentDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/appointments")
public class AppointmentViewController {

	@Autowired
	private AppointmentService appointmentService;

	@GetMapping(value = "/{id}")
	public String getAppointment(@PathVariable int id, Model model) {
		if (appointmentService.checkPermissions()) {
			AppointmentDto appointmentDto = appointmentService.get(id);
			model.addAttribute("appointments", Collections.singleton(appointmentDto));
			return "/appointments/appointments-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getAppointments(Model model) {
		if (appointmentService.checkPermissions()) {
			List<AppointmentDto> appointments = (List<AppointmentDto>) appointmentService.getAppointments(0, 10)
					.getContent();
			model.addAttribute("appointments", appointments);
			return "/appointments/appointments-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/doctor/{doctorId}")
	public String getAppointmentsWithDoctor(@PathVariable int doctorId, Model model) {
		if (appointmentService.checkPermissions()) {
			List<AppointmentDto> appointments = (List<AppointmentDto>) appointmentService
					.getAppointmentsWithDoctor(doctorId, 0, 10).getContent();
			model.addAttribute("appointments", appointments);
			return "/appointments/appointments-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/patient/{patientId}")
	public String getAppointmentsForPatient(@PathVariable int patientId, Model model) {
		if (appointmentService.checkPermissions()) {
			List<AppointmentDto> appointments = (List<AppointmentDto>) appointmentService
					.getAppointmentsForPatient(patientId, 0, 10).getContent();
			model.addAttribute("appointments", appointments);
			return "/appointments/appointments-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editAppointmentForm(@PathVariable int id, Model model) {
		if (appointmentService.checkPermissions()) {
			AppointmentDto appointmentDto = appointmentService.get(id);
			model.addAttribute("appointment", appointmentDto);
			return "/appointments/appointments-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateAppointment(@PathVariable("id") int id,
			@Valid @ModelAttribute("appointment") AppointmentDto appointmentDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("appointment", appointmentDto);
			return "/appointments/appointments-edit";
		}
		try {
			appointmentService.update(id, appointmentDto);
		} catch (RuntimeException ex) {
			model.addAttribute("appointment", appointmentDto);
			return "/appointments/appointments-create?error";
		}
		return "redirect:/ui/appointments";
	}

	@GetMapping(value = "/create")
	public String crateAppointmentForm(Model model) {
		if (appointmentService.checkPermissions()) {
			AppointmentDto appointmentDto = new AppointmentDto();
			model.addAttribute("appointment", appointmentDto);
			return "/appointments/appointments-create";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/create")
	public String saveAppointment(@Valid @ModelAttribute("appointment") AppointmentDto appointmentDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("appointment", appointmentDto);
			return "/appointments/appointments-create";
		}
		try {
			appointmentService.create(appointmentDto);
		} catch (RuntimeException ex) {
			model.addAttribute("appointment", appointmentDto);
			return "/appointments/appointments-create?error";
		}
		return "redirect:/ui/appointments";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteAppointmentForm(@PathVariable int id, Model model) {
		if (appointmentService.checkPermissions()) {
			appointmentService.delete(id);
			return "redirect:/ui/appointments";
		} else
			return "unauthorized";
	}

}
