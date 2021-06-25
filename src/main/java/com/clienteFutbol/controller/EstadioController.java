package com.clienteFutbol.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.clienteFutbol.bean.EntrenadorDTO;
import com.clienteFutbol.bean.EstadioDTO;
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.Paginas;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;

@Controller
@RequestMapping("/estadio")
public class EstadioController {
	private static final Logger log = LogManager.getLogger(EstadioController.class);
	
	
	//------------------------------------------------------------------------
	
	//http://localhost:9090/estadio/listaEstadios
	@GetMapping(value="/listaEstadios")
	public ModelAndView listaEstadios (Model model ) {
		log.info("inicio listaEstadios ");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTAESTADIOS);	
				
		modelAndView.addObject("estadios",this.obtenerEstadios());
			
		return modelAndView ;
	}
		
		
	
	// ------------------------------------------------
	
	private List<EstadioDTO> obtenerEstadios(){
		System.out.println("Cargando estadios");
				
		String endpoint = "http://localhost:8090/apiFutbol/estadios";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
				
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
				
		HttpEntity requestEstadio = new HttpEntity<>(headers);

		ResponseEntity<EstadioDTO[]> respuesta = null;
				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(
						endpoint, 
						requestEstadio, 
						EstadioDTO[].class);
				
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			EstadioDTO[] estadios = respuesta.getBody();
					
			List<EstadioDTO> lstEstadio = new ArrayList<EstadioDTO>();
			for(EstadioDTO estadio : estadios) {
				lstEstadio.add(estadio);
				}		
				return lstEstadio;
				
			}else {
				return new ArrayList<>();
			}
		}	
	
	

}
