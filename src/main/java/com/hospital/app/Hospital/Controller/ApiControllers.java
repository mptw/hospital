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
import com.hospital.app.Hospital.Security.SecurityUtil;
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
        final String welcomeMessage = "Welcome to the Auto Service Management System!";
        model.addAttribute("welcome", welcomeMessage);

       // Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
       // model.addAttribute("username", authentication.getName());

      //  User principal = (User) authentication.getPrincipal();
        
     //   User principal2 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        
    //    model.addAttribute("username", principal.getAuthorities());
        model.addAttribute("userche", SecurityUtil.getSessionUser());

        return "index";
    }
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
    
	//Unneeded since UserDetails does this 
	/*
    @PostMapping("/login")
	public String login(Model model, @ModelAttribute("user") UserEntity user) {
    	Optional<UserEntity> existingUserUsername = userService.findByUsername(user.getUsername());
    	if(!existingUserUsername.isPresent()) {
    		return "redirect:/login?error";
    	}
    	
    	if(!userService.areUserDetailsValid(user)) {
    		return "redirect:/login?error";
		}
    	
    	//TODO   
		//model.addAttribute("usercheto", user.getUsername());
		return "redirect:/index?success";
	}*/

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        UserEntity user = new UserEntity();
        model.addAttribute("user", user);
        PersonInfo person = new PersonInfo();
        model.addAttribute("person", person);
        return "register";
    }

	@PostMapping("/register/save")
	public String register(@ModelAttribute("user") UserEntity user, @ModelAttribute("person") PersonInfo person, BindingResult result, Model model) {

		Optional<UserEntity> existingUser = userService.findByUsername(user.getUsername());
		if (existingUser.isPresent()) {
			return "redirect:/register?fail";
		}
		if (result.hasErrors()) {
			model.addAttribute("user", user);
			model.addAttribute("person", person);
			return "register";
		}
		
		
		/*RoleType requestRole = RoleType.valueOf(user.getRole());
		Role role = roleRepository.findByType(requestRole).get();
		user.setRole(role);
		*/
		
		//UserEntity userEntity= 
		// get wnated role and 
		//set the role as snnonymous
		
		
		Role role = roleRepository.findByType(RoleType.ANNONYMOUS).get();	
		user.setRole(role);
		
		userService.saveUser(user);

		UserEntity createdUser = userService.findByUsername(user.getUsername()).get();
		person.setUserEntity(createdUser);
		personService.create(person);
		
		return "redirect:/index?success";
	}
	
	/*
	
    @GetMapping("login")
    public String login(Model model) {
        final String welcomeMessage = "Welcome tsso the Hospital Application!";
        model.addAttribute("welcome", welcomeMessage);
        return "login";
    }
    
    @GetMapping("logout")
    public String logout(Model model) {
        final String welcomeMessage = "Welcome to the Hospital Application!";
        model.addAttribute("welcome", welcomeMessage);
        return "login";
    }
    
    @GetMapping("unauthorized")
    public String unauthorized(Model model) {
        final String welcomeMessage = "Welcome to the Hospital Application!";
        model.addAttribute("welcome", welcomeMessage);
        return "unauthorized";
    }
	
	@Bean
    public String currentUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if( authentication!=null ) {
    	//if (!(authentication instanceof AnonymousAuthenticationToken)) {
    	    String currentUserName = authentication.getName();
    	    return currentUserName;
    	}
    	return null;
    }*/
}
