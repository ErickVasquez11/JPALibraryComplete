package com.erickvasquez.documentos.models.dtos.users;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {

	
	@NotBlank(message = "Warn! required identification")
	private String id;
	
	@NotBlank(message = "Warn! required password")
	private String password;
	
}
