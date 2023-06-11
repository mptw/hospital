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

import com.hospital.app.Hospital.Service.TreatmentService;
import com.hospital.app.Hospital.dto.TreatmentDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/treatments")
public class TreatmentViewController {

	@Autowired
	private TreatmentService treatmentService;

	@GetMapping(value = "/{id}")
	public String getTreatment(@PathVariable int id, Model model) {
		if (treatmentService.checkPermissions()) {
			TreatmentDto treatmentDto = treatmentService.get(id);
			model.addAttribute("treatments", Collections.singleton(treatmentDto));
			return "/treatments/treatments-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getTreatments(Model model) {
		if (treatmentService.checkPermissions()) {
			List<TreatmentDto> treatments = (List<TreatmentDto>) treatmentService.getTreatments(0, 10).getContent();
			model.addAttribute("treatments", treatments);
			return "/treatments/treatments-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/patient/{patientId}")
	public String getTreatmentsForPatient(@PathVariable int patientId, Model model) {
		if (treatmentService.checkPermissions()) {
			List<TreatmentDto> treatmentsForPatient = (List<TreatmentDto>) treatmentService
					.getTreatmentsForPatient(patientId, 0, 10).getContent();
			model.addAttribute("treatments", treatmentsForPatient);
			return "/treatments/treatments-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editTreatmentForm(@PathVariable int id, Model model) {
		if (treatmentService.checkPermissions()) {
			TreatmentDto treatmentToUpdate = treatmentService.get(id);
			model.addAttribute("treatment", treatmentToUpdate);
			return "/treatments/treatments-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateTreatment(@PathVariable("id") int id,
			@Valid @ModelAttribute("treatment") TreatmentDto treatmentDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("treatment", treatmentDto);
			return "/treatments/treatments-edit";
		}
		treatmentService.update(id, treatmentDto);
		return "redirect:/ui/treatments";
	}

	@GetMapping(value = "/create")
	public String crateTreatmentForm(Model model) {
		if (treatmentService.checkPermissions()) {
			TreatmentDto treatment = new TreatmentDto();
			model.addAttribute("treatment", treatment);
			return "/treatments/treatments-create";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/create")
	public String saveTreatment(@Valid @ModelAttribute("treatment") TreatmentDto treatmentDto, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("treatment", treatmentDto);
			return "/treatments/treatments-create";
		}
		treatmentService.create(treatmentDto);
		return "redirect:/ui/treatments";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteTreatmentForm(@PathVariable int id, Model model) {
		if (treatmentService.checkPermissions()) {
			treatmentService.delete(id);
			return "redirect:/ui/treatments";
		} else
			return "unauthorized";
	}

}