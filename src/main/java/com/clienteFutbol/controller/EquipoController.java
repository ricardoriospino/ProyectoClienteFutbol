package com.clienteFutbol.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.clienteFutbol.bean.EquipoDTO;
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;

import static com.clienteFutbol.rest.cliente.util.Paginas.*;


@Controller
@RequestMapping("/equipoc")
public class EquipoController {
	private static final Logger log = LogManager.getLogger(EquipoController.class);
	
	
	//http://localhost:9090/equipoc/init
	@GetMapping(value = "/initEquipo")
	public ModelAndView inicioEquipo() {
		log.info("ini: inicioEquipo");
		
		ModelAndView modelAndView = new ModelAndView(PAGINAEQUIPO);
		
		return modelAndView;
	}
	// --------------- Mostrar equipo por nombre ---------------------------------
	
	//http://localhost:9090/equipoc/mostrar/
	@GetMapping({"/mostrar"  ,"/mostrar/{p_nombreEquipo}"})
	public ModelAndView mostrarEquipo(@PathVariable(name ="p_nombreEquipo", required = false ) String nombreEquipo) {	
		log.info("ini: mostrarEquipo");
		log.debug("nombre equipo: " + nombreEquipo);
		
		ModelAndView modelAndView = new ModelAndView(PAGINAEQUIPO);
		
		if(nombreEquipo!= null) {
			modelAndView.addObject("equipoFront",invocarServicioPorNombreEquipo(nombreEquipo) );
		}
		
		return modelAndView;
	}
	
	
	private EquipoDTO invocarServicioPorNombreEquipo(String nombreEquipo) {
		
		log.info("Cargando Equipo");
		
		String endPoint = "http://localhost:8090/apiFutbol/equipo/"+nombreEquipo;
		String token = GestorTokenSeguridad.obtenerToken();
		
		// consumimos el servicio Rest tipo Get
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		
		HttpEntity requestEquipo = new HttpEntity<>(headers);
		
		ResponseEntity<EquipoDTO> respuesta = null;
		
		RestUtilitario restUtil = new RestUtilitario();
		
		EquipoDTO equipo = new EquipoDTO();
		
		respuesta = restUtil.consumeRestServiceGET(
													endPoint, 
													requestEquipo, 
													EquipoDTO.class);
		
		if (respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			
			 equipo = respuesta.getBody();
					
		}else {
			log.error("Fallo invocarServicioPorNombreEquipo ");
		}
		return equipo;
	
	}
}
