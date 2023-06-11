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

import com.hospital.app.Hospital.Dto.DirectorDto;
import com.hospital.app.Hospital.Service.DirectorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/directors")
public class DirectorViewController {

	@Autowired
	private DirectorService directorService;

	@GetMapping(value = "/{id}")
	public String getDirector(@PathVariable int id, Model model) {
		if (directorService.checkPermissions()) {
			DirectorDto directorDto = directorService.get(id);
			model.addAttribute("directors", Collections.singleton(directorDto));
			return "/directors/directors-list";
		} else
			return "unauthorized";
	}

	@SuppressWarnings("unchecked")
	@GetMapping
	public String getDirectors(Model model) {
		if (directorService.checkPermissions()) {
			List<DirectorDto> directors = (List<DirectorDto>) directorService.getDirectors(0, 10).getContent();
			model.addAttribute("directors", directors);
			return "/directors/directors-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editDirectorForm(@PathVariable int id, Model model) {
		if (directorService.checkPermissions()) {
			DirectorDto directorToUpdate = directorService.get(id);
			model.addAttribute("director", directorToUpdate);
			return "/directors/directors-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateDirector(@PathVariable("id") int id, @Valid @ModelAttribute("director") DirectorDto directorDto,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("director", directorDto);
			return "/directors/directors-edit";
		}
		directorService.update(id, directorDto);
		return "redirect:/ui/directors";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteDirectorForm(@PathVariable int id, Model model) {
		if (directorService.checkPermissions()) {
			directorService.delete(id);
			return "redirect:/ui/directors";
		} else
			return "unauthorized";
	}

}
