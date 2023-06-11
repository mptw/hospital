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

import com.hospital.app.Hospital.Service.WardService;
import com.hospital.app.Hospital.dto.WardDto;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/wards")
public class WardViewController {
	@Autowired
	private WardService wardService;

	@GetMapping(value = "/{id}")
	public String getWard(@PathVariable int id, Model model) {
		if (wardService.checkPermissions()) {
			WardDto wardDto = wardService.get(id);
			model.addAttribute("wards", Collections.singleton(wardDto));
			return "/wards/wards-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getWards(Model model) {
		if (wardService.checkPermissions()) {
			List<WardDto> wards = (List<WardDto>) wardService.getWards(0, 10).getContent();
			model.addAttribute("wards", wards);
			return "/wards/wards-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editWardForm(@PathVariable int id, Model model) {
		if (wardService.checkPermissions()) {
			WardDto wardToUpdate = wardService.get(id);
			model.addAttribute("ward", wardToUpdate);
			return "/wards/wards-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateWard(@PathVariable("id") int id, @Valid @ModelAttribute("ward") WardDto wardDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("ward", wardDto);
			return "/wards/wards-edit";
		}
		wardService.update(id, wardDto);
		return "redirect:/ui/wards";
	}

	@GetMapping(value = "/create")
	public String crateWardForm(Model model) {
		if (wardService.checkPermissions()) {
			WardDto ward = new WardDto();
			model.addAttribute("ward", ward);
			return "/wards/wards-create";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/create")
	public String saveWard(@Valid @ModelAttribute("ward") WardDto wardDto, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("ward", wardDto);
			return "/wards/wards-create";
		}
		wardService.create(wardDto);
		return "redirect:/ui/wards";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteWardForm(@PathVariable int id, Model model) {
		if (wardService.checkPermissions()) {
			wardService.delete(id);
			return "redirect:/ui/wards";
		} else
			return "unauthorized";
	}

}
