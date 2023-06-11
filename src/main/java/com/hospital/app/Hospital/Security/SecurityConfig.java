package com.hospital.app.Hospital.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public static final long JWT_EXPIRATION = 900000; // 15mins
	public static final String JWT_SECRET = System.getenv("JWT_SIGNATURE_SECRET");

	private JwtAuthEntryPoint authEntryPoint;

	// private UserDetailsServiceImpl userDetailsService;

	/*
	 * @Autowired public SecurityConfig(CustomUserDetailsService userDetailsService,
	 * JwtAuthEntryPoint authEntryPoint) { this.userDetailsService =
	 * userDetailsService; this.authEntryPoint = authEntryPoint; }
	 */
	/*
	 * @Bean public DaoAuthenticationProvider authProvider() {
	 * DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	 * authProvider.setUserDetailsService(userService);
	 * authProvider.setPasswordEncoder(passwordEncoder()); return authProvider; }
	 */

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.requestMatchers("/login", "/register", "/ui/doctors", "/ui/directors", "/ui/patients", "/ui/wards",
						"/ui/appointments", "/ui/rooms", "/ui/treatments", "/ui/staff")
				.permitAll().and()
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/index").loginProcessingUrl("/login")
						.failureUrl("/login?error").permitAll())
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll());

		return http.build();

	}
	/*
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	 * Exception {
	 * 
	 * http.csrf().disable().authorizeRequests().requestMatchers(HttpMethod.GET).
	 * permitAll().anyRequest() .authenticated().and().httpBasic(); return
	 * http.build();
	 * 
	 * /*http.csrf().disable().exceptionHandling().authenticationEntryPoint(
	 * authEntryPoint).and().sessionManagement()
	 * .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().
	 * authorizeRequests()
	 * .requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated().and
	 * ().httpBasic(); http.addFilterBefore(jwtAuthenticationFilter(),
	 * UsernamePasswordAuthenticationFilter.class); return http.build();
	 * 
	 * // }
	 */

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JWTAuthenticationFilter jwtAuthenticationFilter() {
		return new JWTAuthenticationFilter();
	}

}
