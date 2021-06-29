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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.clienteFutbol.bean.EquipoDTO;
import com.clienteFutbol.bean.JugadorDTO;
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.Paginas;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;

@Controller
@RequestMapping("/jugador")
public class JugadorController {
	private static final Logger log = LogManager.getLogger(JugadorController.class);

	
	// -----------------------------------------------------
	
	//http://localhost:9090/jugador/listaJugadores
	@GetMapping(value="/listaJugadores")
	public ModelAndView listaJugador (Model model ) {
		log.info("inicio lista Jugador ");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOJUGADORES);	
				
		modelAndView.addObject("jugadores",this.obtenerJugadores());
			
		return modelAndView ;
	
	}

	// ---------------------------------------------------------------------
	
	//http://localhost:9090/jugador/agregarJugador
	@GetMapping(value="/agregarJugador")
	public ModelAndView agregarJugador (Model model ) {
		log.info("inicio agregarJugador ");
	
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);	
		modelAndView.addObject("jugadorSave" , new JugadorDTO());
		modelAndView.addObject("fallo", false);
		modelAndView.addObject("exito", false);
		return modelAndView ;
	}
	
	
	// ---------------------Guardar-------------------------
	//http://localhost:9090/jugador/saveJugador
	@PostMapping(value ="/saveJugador")
	public ModelAndView guardarJugador(JugadorDTO jugador, Model model) {
		log.debug("ini: guardarJugador modelAndView");
			
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);
			
			
			String endPoint ="http://localhost:8090/apiFutbol/jugador";
			String token = GestorTokenSeguridad.obtenerToken();
				
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", token);
			HttpEntity request = new HttpEntity<>(jugador,headers);
			
			ResponseEntity<?> respuesta = null;
			RestUtilitario resUtil = new RestUtilitario();
			
			respuesta = resUtil.consumeRestServicePUT(
					endPoint, 
					request, 
					ResponseEntity.class);
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				log.info("inserto correctamente");
				modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);
				modelAndView.addObject("exito", true);
				modelAndView.addObject("jugadorSave" , new JugadorDTO());
				
			}else {
				log.info("fallo inserccion ");
				modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);
				modelAndView.addObject("fallo", true);
				modelAndView.addObject("jugadorSave" , new JugadorDTO());
			}
			
			return modelAndView;
			
		}	

	//---------------------Listar----------------------

	private List<JugadorDTO> obtenerJugadores(){
		System.out.println("Cargando jugadores");
				
		String endpoint = "http://localhost:8090/apiFutbol/jugadores";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
				
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
				
		HttpEntity requestJugadores = new HttpEntity<>(headers);

		ResponseEntity<JugadorDTO[]> respuesta = null;
				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(
						endpoint, 
						requestJugadores, 
						JugadorDTO[].class);
				
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			JugadorDTO[] jugadores = respuesta.getBody();
					
			List<JugadorDTO> lstJugador = new ArrayList<JugadorDTO>();
			for(JugadorDTO jugador : jugadores) {
				lstJugador.add(jugador);
				}		
				return lstJugador;
				
			}else {
				return new ArrayList<>();
			}
		}

}
