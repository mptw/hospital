package com.hospital.app.Hospital.Controller.View;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.app.Hospital.Service.PatientService;
import com.hospital.app.Hospital.dto.PatientDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/patients")
public class PatientViewController {

	@Autowired
	private PatientService patientService;

	@GetMapping(value = "/{id}")
	public String getPatient(@PathVariable int id, Model model) {
		if (patientService.checkPermissions()) {
			PatientDto patientDto = patientService.get(id);
			model.addAttribute("patients", Collections.singleton(patientDto));
			return "/patients/patients-list";
		} else
			return "unauthorized";
	}

//TODO getPatientsByDoctor
// if user is a doctor retrn list of just his patients ; -- or have another button to view all patiets but not edit them

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getPatients(Model model) {
		if (patientService.checkPermissions()) {
			
				
			User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		        
			String userRoleType = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString();
			
			//principa==username(administrator);userRoleType==type(ADMIN)
			
			
			List<PatientDto> patients = (List<PatientDto>) patientService.getPatients(0, 10).getContent();
			model.addAttribute("patients", patients);
			return "/patients/patients-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/ward/{wardId}")
	public String getPatientsInWard(@PathVariable int wardId, Model model) {
		if (patientService.checkPermissions()) {
			List<PatientDto> patientsInWard = (List<PatientDto>) patientService.getAllByWard(wardId, 0, 10).getContent();
			model.addAttribute("patients", patientsInWard);
			return "/patients/patients-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/doctor/{doctorId}")
	public String getPatientsByDoctor(@PathVariable int doctorId, Model model) {
		if (patientService.checkPermissions()) {
			List<PatientDto> patientsInWard = (List<PatientDto>) patientService.getAllByDoctor(doctorId, 0, 10).getContent();
			model.addAttribute("patients", patientsInWard);
			return "/patients/patients-list";
		} else
			return "unauthorized";
	}
	
	@GetMapping(value = "/{id}/edit")
	public String editPatientForm(@PathVariable int id, Model model) {
		if (patientService.checkPermissions()) {
			PatientDto patientToUpdate = patientService.get(id);
			model.addAttribute("patient", patientToUpdate);
			return "/patients/patients-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updatePatient(@PathVariable("id") int id, @Valid @ModelAttribute("patient") PatientDto patientDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("patient", patientDto);
			return "/patients/patients-edit";
		}
		patientService.update(id, patientDto);
		return "redirect:/ui/patients";
	}

	@GetMapping(value = "/create")
	public String cratePatientForm(Model model) {
		if (patientService.checkPermissions()) {
			PatientDto patient = new PatientDto();
			model.addAttribute("patient", patient);
			return "/patients/patients-create";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/create")
	public String savePatient(@Valid @ModelAttribute("patient") PatientDto patientDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("patient", patientDto);
			return "/patients/patients-create";
		}
		patientService.create(patientDto);
		return "redirect:/ui/patients";
	}

	@GetMapping(value = "/{id}/delete")
	public String deletePatientForm(@PathVariable int id, Model model) {
		if (patientService.checkPermissions()) {
			patientService.delete(id);
			return "redirect:/ui/patients";
		} else
			return "unauthorized";
	}

}