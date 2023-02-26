package it.epicode.be.forestrycorps.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.epicode.be.forestrycorps.models.AccessDetails;
import it.epicode.be.forestrycorps.payloads.JwtResponse;
import it.epicode.be.forestrycorps.payloads.LoginRequest;
import it.epicode.be.forestrycorps.repositories.RoleRepository;
import it.epicode.be.forestrycorps.repositories.UserRepository;
import it.epicode.be.forestrycorps.utils.JwtUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager aM;
	@Autowired
	UserRepository uR;
	@Autowired
	RoleRepository rR;
	@Autowired
	PasswordEncoder pE;
	@Autowired
	JwtUtils jU;

	@PostMapping("login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication a = aM.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		return returnToken(a);
	}

	public ResponseEntity<?> returnToken(Authentication a) {
		SecurityContextHolder.getContext().setAuthentication(a);
		String jwt = jU.generateJwtToken(a);
		
		AccessDetails uD = (AccessDetails) a.getPrincipal();		
		List<String> roles = uD.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, uD.getId(), uD.getUsername(), uD.getEmail(), roles));
	}
}
