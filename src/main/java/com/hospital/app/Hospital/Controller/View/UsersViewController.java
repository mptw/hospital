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

import com.hospital.app.Hospital.Model.UserEntity;
import com.hospital.app.Hospital.Service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/ui/users")
public class UsersViewController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/{id}")
	public String getUser(@PathVariable int id, Model model) {
		if (userService.checkPermissions()) {
			UserEntity user = userService.findById(id).get();
			model.addAttribute("users", Collections.singleton(user));
			return "/users/users-list";
		} else
			return "unauthorized";
	}

	@GetMapping
	public String getUsers(Model model) {
		if (userService.checkPermissions()) {
			List<UserEntity> users = userService.getUsersWaitingForApproval();
			model.addAttribute("users", users);
			return "/users/users-list";
		} else
			return "unauthorized";
	}

	@GetMapping(value = "/{id}/edit")
	public String editUserForm(@PathVariable int id, Model model) {
		if (userService.checkPermissions()) {
			UserEntity userToUpdate = userService.findById(id).get();
			model.addAttribute("user", userToUpdate);
			return "/users/users-edit";
		} else
			return "unauthorized";
	}

	@PostMapping(value = "/{id}/edit")
	public String updateUser(@PathVariable("id") int id, @Valid @ModelAttribute("user") UserEntity user,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			return "/users/users-edit";
		}
		userService.updateUser(user);
		return "redirect:/ui/users";
	}

	@GetMapping(value = "/{id}/delete")
	public String deleteUserForm(@PathVariable int id, Model model) {
		if (userService.checkPermissions()) {
			userService.delete(id);
			return "redirect:/ui/users";
		} else
			return "unauthorized";
	}
}
