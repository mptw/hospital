package com.hospital.app.Hospital.Controller.View;

import java.util.Arrays;
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

import com.hospital.app.Hospital.Model.StaffType;
import com.hospital.app.Hospital.Service.StaffService;
import com.hospital.app.Hospital.dto.StaffDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/staff")
public class StaffViewController {

	@Autowired
	private StaffService staffService;

	@GetMapping(value = "/{id}")
	public String getStaff(@PathVariable int id, Model model) {
		if (staffService.checkPermissions()) {
			StaffDto staffDto = staffService.get(id);
			model.addAttribute("staff", Collections.singleton(staffDto));
			return "/staff/staff-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getStaff(Model model) {
		if (staffService.checkPermissions()) {
			List<StaffDto> staff = (List<StaffDto>) staffService.getStaff(0, 10).getContent();
			model.addAttribute("staff", staff);
			return "/staff/staff-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editStaffForm(@PathVariable int id, Model model) {
		if (staffService.checkPermissions()) {
			StaffDto staffToUpdate = staffService.get(id);
			model.addAttribute("staff", staffToUpdate);
			model.addAttribute("types", Arrays.asList(StaffType.values()));
			return "/staff/staff-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateStaff(@PathVariable("id") int id, @Valid @ModelAttribute("staff") StaffDto staffDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("staff", staffDto);
			return "/staff/staff-edit";
		}
		staffService.update(id, staffDto);
		return "redirect:/ui/staff";
	}

	@GetMapping(value = "/create")
	public String crateStaffForm(Model model) {
		if (staffService.checkPermissions()) {
			StaffDto staff = new StaffDto();
			model.addAttribute("staff", staff);
			model.addAttribute("types", Arrays.asList(StaffType.values()));
			return "/staff/staff-create";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/create")
	public String saveStaff(@Valid @ModelAttribute("staff") StaffDto staffDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("staff", staffDto);
			return "/staff/staff-create";
		}
		staffService.create(staffDto);
		return "redirect:/ui/staff";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteStaffForm(@PathVariable int id, Model model) {
		if (staffService.checkPermissions()) {
			staffService.delete(id);
			return "redirect:/ui/staff";
		} else
			return "unauthorized";
	}

}