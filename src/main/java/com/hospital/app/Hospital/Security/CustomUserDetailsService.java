package com.hospital.app.Hospital.Security;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hospital.app.Hospital.Model.Role;
import com.hospital.app.Hospital.Model.UserEntity;
import com.hospital.app.Hospital.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserEntity> user = userRepository.findByUsername(username);
		if (user.isPresent()) {
			UserEntity existingUser = user.get();
			return new User(existingUser.getUsername(), existingUser.getPassword(),
					mapRolesToAuthorities(Collections.singletonList(existingUser.getRole())));
		}
		// TODO exceptions
		throw new RuntimeException("user not found");
		//// .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
       return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
