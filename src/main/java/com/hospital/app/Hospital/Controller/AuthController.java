package com.hospital.app.Hospital.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.app.Hospital.Model.Role;
import com.hospital.app.Hospital.Model.RoleType;
import com.hospital.app.Hospital.Model.UserEntity;
import com.hospital.app.Hospital.Repository.RoleRepository;
import com.hospital.app.Hospital.Repository.UserRepository;
import com.hospital.app.Hospital.Security.JWTGenerator;
import com.hospital.app.Hospital.dto.AuthResponseDto;
import com.hospital.app.Hospital.dto.LoginDto;
import com.hospital.app.Hospital.dto.RegisterDto;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JWTGenerator jwtGenerator;

	
	@GetMapping
    public String getIndex(Model model, Authentication authentication) {
        final String welcomeMessage = "Welcome to the Auto Service Management System!";
        model.addAttribute("welcome", welcomeMessage);

        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", authentication.getName());

    //    User principal = (User) authentication.getPrincipal();
        
      //  String userRoleType = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().findFirst().get().toString();

        
      //  model.addAttribute("username", principal.getAuthorities());

        return "index";
    }

	@PostMapping("login")
	public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtGenerator.generateToken(authentication);
		return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
	}
	
	@PostMapping("logut")
	public ResponseEntity<AuthResponseDto> logut(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager
				.authenticate(null);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("register")
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
		
		Optional<UserEntity> potentielUser = userRepository.findByUsername(registerDto.getUsername());
		if (potentielUser.isPresent()) {
			return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
		}

		UserEntity user = new UserEntity();
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

		RoleType requestRole = RoleType.valueOf(registerDto.getRole());
		Role role = roleRepository.findByType(requestRole).get();
		user.setRole(role);

		userRepository.save(user);

		return new ResponseEntity<>("User registered success!", HttpStatus.OK);
	}
}