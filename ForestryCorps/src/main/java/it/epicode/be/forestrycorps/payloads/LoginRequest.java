package it.epicode.be.forestrycorps.payloads;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
