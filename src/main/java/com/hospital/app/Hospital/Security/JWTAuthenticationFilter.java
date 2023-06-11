package com.hospital.app.Hospital.Security;

import org.springframework.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;

public class JWTAuthenticationFilter {
	// extends OncePerRequestFilter {

	/*
	 * @Autowired private JWTGenerator tokenGenerator;
	 * 
	 * @Autowired private UserDetailsServiceImpl customUserDetailsService;
	 * 
	 * @Autowired private UserRepository userRepository;
	 * 
	 * /*
	 * 
	 * @Override protected void doFilterInternal(HttpServletRequest request,
	 * HttpServletResponse response, FilterChain filterChain) throws
	 * ServletException, IOException { String token = getJWTFromRequest(request); if
	 * (StringUtils.hasText(token) && tokenGenerator.validateToken(token)) { String
	 * username = tokenGenerator.getUsernameFromJWT(token);
	 * 
	 * UserDetails userDetails =
	 * customUserDetailsService.loadUserByUsername(username);
	 * UsernamePasswordAuthenticationToken authenticationToken = new
	 * UsernamePasswordAuthenticationToken( userDetails, null,
	 * userDetails.getAuthorities()); authenticationToken.setDetails(new
	 * WebAuthenticationDetailsSource().buildDetails(request));
	 * SecurityContextHolder.getContext().setAuthentication(authenticationToken); }
	 * filterChain.doFilter(request, response); }
	 */

	private String getJWTFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}