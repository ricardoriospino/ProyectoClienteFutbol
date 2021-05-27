package com.clienteFutbol.rest.cliente.security;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clienteFutbol.rest.cliente.util.RestUtilitario;



public class GestorTokenSeguridad {
	
	public static String obtenerToken() {
		
		
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJwcmlvcyIsImV4cCI6MTYyMjA3MTQ0OX0.DP3MfESNB-leay2oZuzPsk3E6DIsBVBq-7C2Z50iCSPIMnKdM_48UkchXkZLxjx2jXz5BNmqjF0YXe4QB4Bb1A";
		String endPointLogueo = "http://localhost:8090/login";
		HttpHeaders headers = new HttpHeaders();
		AutenticationSecurity seguridad = new AutenticationSecurity("prios","123");
		HttpEntity<AutenticationSecurity> request = new HttpEntity<>(seguridad,headers);
		
		ResponseEntity<?> respuesta = null;
		
		RestUtilitario resUtil = new RestUtilitario();
	
		respuesta = resUtil.consumeRestServicePOST(
				endPointLogueo, 
				request, 
				ResponseEntity.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			token = respuesta.getHeaders().get("Authorization").get(0);
			System.out.println("Token obtenido " + token );
			System.out.println("inserto correctamente");
		}else {
			System.out.println("fallo generacion de token ");
		}
		
		return token;
		
	}

}
