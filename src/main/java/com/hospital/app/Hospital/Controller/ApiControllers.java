package com.hospital.app.Hospital.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.hospital.app.Hospital.Model.PersonInfo;
import com.hospital.app.Hospital.Model.Role;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.UserEntity;
import com.hospital.app.Hospital.Repository.RoleRepository;
import com.hospital.app.Hospital.Service.PersonService;
import com.hospital.app.Hospital.Service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ApiControllers {

	private UserService userService;
	private PersonService personService;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/index")
	public String getIndex(Model model, Authentication authentication) {
		return "index";
	}

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/register")
	public String getRegisterForm(Model model) {
		UserEntity user = new UserEntity();
		model.addAttribute("user", user);
		PersonInfo person = new PersonInfo();
		model.addAttribute("person", person);
		return "register";
	}

	@PostMapping("/register/save")
	public String register(@ModelAttribute("user") UserEntity user, @ModelAttribute("person") PersonInfo person,
			BindingResult result, Model model) {

		Optional<UserEntity> existingUser = userService.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			return "redirect:/register?fail";
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("person", person);
			return "register";
		}

		Role role = roleRepository.findByType(RoleType.ANNONYMOUS).get();
		user.setRole(role);
		userService.saveUser(user);

		UserEntity createdUser = userService.findByUsername(user.getUsername()).get();
		person.setUserEntity(createdUser);
		personService.create(person);
		return "redirect:/index?success";
	}
}
