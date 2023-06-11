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

import com.hospital.app.Hospital.Dto.DoctorDto;
import com.hospital.app.Hospital.Service.DoctorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/doctors")
public class DoctorViewController {

	@Autowired
	private DoctorService doctorService;

	@GetMapping(value = "/{id}")
	public String getDoctor(@PathVariable int id, Model model) {
		if (doctorService.checkPermissions()) {
			DoctorDto doctorDto = doctorService.get(id);
			model.addAttribute("doctors", Collections.singleton(doctorDto));
			return "/doctors/doctors-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getDoctors(Model model) {
		if (doctorService.checkPermissions()) {
			List<DoctorDto> doctors = (List<DoctorDto>) doctorService.getDoctors(0, 10).getContent();
			model.addAttribute("doctors", doctors);
			return "/doctors/doctors-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/ward/{wardId}")
	public String getDoctorsInWard(@PathVariable int wardId, Model model) {
		if (doctorService.checkPermissions()) {
			List<DoctorDto> doctorsInWard = (List<DoctorDto>) doctorService.getAllInWard(wardId, 0, 10).getContent();
			model.addAttribute("doctors", doctorsInWard);
			return "/doctors/doctors-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editDoctorForm(@PathVariable int id, Model model) {
		if (doctorService.checkPermissions()) {
			DoctorDto doctorToUpdate = doctorService.get(id);
			model.addAttribute("doctor", doctorToUpdate);
			return "/doctors/doctors-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateDoctor(@PathVariable("id") int id, @Valid @ModelAttribute("doctor") DoctorDto doctorDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("doctor", doctorDto);
			return "/doctors/doctors-edit";
		}
		doctorService.update(id, doctorDto);
		return "redirect:/ui/doctors";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteDoctorForm(@PathVariable int id, Model model) {
		if (doctorService.checkPermissions()) {
			doctorService.delete(id);
			return "redirect:/ui/doctors";
		} else
			return "unauthorized";
	}

}
