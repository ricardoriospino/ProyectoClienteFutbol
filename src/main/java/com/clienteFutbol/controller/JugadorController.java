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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.servlet.ModelAndView;

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
		return modelAndView ;
	}
	
	
	//------------------------------------------------------------------------	
	//http://localhost:9090/jugador/actualizarJugador/
	@GetMapping(value="/actualizarJugador/{idJugador}")
	public ModelAndView actualizarJugador (@PathVariable(name="idJugador", required = true) int idJugador ,JugadorDTO jugador, Model model ) {
			
		log.info("inicio actualizarJugador ");
			
		jugador = this.obtenerJugador(idJugador);					
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);	
		modelAndView.addObject("jugadorSave" , jugador);
			
		return modelAndView ;
	}

	//--------------------Eliminar-------------------------
	//http://localhost:9090/jugador/eliminarJugador/
	@GetMapping( value = "/eliminarJugador/{idJugador}")
	public ModelAndView eliminarJugador (@PathVariable(name="idJugador", required = true) String idJugador ,Model model) {
		
		log.info("ini: eliminarJugador modelAndView");

		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOJUGADORES);		
		String endPoint ="http://localhost:8090/apiFutbol/borrarJugador/" + idJugador;
		String token = GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(headers);		
		ResponseEntity<?> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();	
		
		try {
			
			respuesta = resUtil.consumeRestServiceDELETE(endPoint, request, ResponseEntity.class);
			modelAndView = generarRespuestaEliminarJugador(respuesta);
						
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("Fallo eliminar Entrenador");
		}
		
		return modelAndView;
	}
	
	
	
	// ---------------------Guardar-------------------------
	//http://localhost:9090/jugador/saveJugador
	@PostMapping(value ="/saveJugador")
	public ModelAndView guardarJugador(JugadorDTO jugador, Model model) {
		log.info("ini: guardarJugador modelAndView");
			
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);
		
		log.info("Id jugador : " + jugador.getIdJugador());
		
		int idJugador = jugador.getIdJugador();
		
		String endPoint ="http://localhost:8090/apiFutbol/jugador";
		String token = GestorTokenSeguridad.obtenerToken();
		RestUtilitario resUtil = new RestUtilitario();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(jugador,headers);
		ResponseEntity<?> respuesta = null;
		
		if(idJugador == 0) {
		
			respuesta = resUtil.consumeRestServicePUT(endPoint, request, ResponseEntity.class);
			modelAndView = generarRespuestasInsertarActualizarJugador(jugador, respuesta);
			
		}else {
			
			log.info("Actualizando Jugador");
			try {

				respuesta = resUtil.consumeRestServicePOST(endPoint, request, ResponseEntity.class);
				modelAndView = generarRespuestasInsertarActualizarJugador(jugador, respuesta);
		
			} catch (RestClientException r) {
				r.printStackTrace();
				log.debug("fallo al actualizar");
				
			}	
		}
			return modelAndView;
			
	}
	
	// ------------------------------------------------------
	private ModelAndView generarRespuestaEliminarJugador(ResponseEntity<?> respuesta) {
			
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOJUGADORES);	
			
		if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
				 
			log.info("Se elimino correctamente");				
			modelAndView.addObject("exitoEliminar", true);
			modelAndView.addObject("jugadores",this.obtenerJugadores());
		}else {
					 
			log.info("No se pudo eliminar Entrenador");				
			modelAndView.addObject("falloEliminar", true);
			modelAndView.addObject("jugadores",this.obtenerJugadores());
		}
			
		return modelAndView;
	}	
	
	//----------------------------------------------------
	private ModelAndView generarRespuestasInsertarActualizarJugador (JugadorDTO jugador, ResponseEntity<?> respuesta) {
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOJUGADOR);
		
		if(jugador.getIdJugador()== 0) {
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				log.info("inserto correctamente");
				modelAndView.addObject("mensajeExitoGuardar", "Se Guardo Correctamente!!" );
				modelAndView.addObject("exito", true);
				modelAndView.addObject("jugadorSave" , new JugadorDTO());
				
			}else {
				log.info("fallo inserccion ");
				modelAndView.addObject("mensajeFalloGuardar", "Error no guardo correctamente!!" );
				modelAndView.addObject("fallo", true);
				modelAndView.addObject("jugadorSave" , jugador);
			}
			
		}else {
			
			if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
				log.info("actualizacion correcta");
				modelAndView.addObject("mensajeExitoActualizar", "Se Actualizo Correctamente!!" );
				modelAndView.addObject("exitoActualizar", true);
				modelAndView.addObject("jugadorSave" , new JugadorDTO());
				
			}else {
				log.error("No se pudo actualizar");	
		    	modelAndView.addObject("mensajeFalloActualizar", " Error no Actualizo Correctamente!!" );
		    	modelAndView.addObject("falloActualizar", true);
		    	modelAndView.addObject("jugadorSave" , jugador);
			}
		}
		
		return modelAndView;
	}
	
	// ------------------Cargar Jugador -------------------
	private JugadorDTO obtenerJugador(int idJugador) {
		log.info("Cargando jugador");
		
		String endpoint = "http://localhost:8090/apiFutbol/jugadorIdJugador/" + idJugador;
		String token =GestorTokenSeguridad.obtenerToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);		
		HttpEntity requestJugador = new HttpEntity<>(headers);		
		ResponseEntity<JugadorDTO> respuesta = null;		
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestJugador, JugadorDTO.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			
			JugadorDTO jugador = respuesta.getBody();
					
				return jugador;
				
			}else {
				return new JugadorDTO();
			}	
	}

	//---------------------Listar----------------------

	private List<JugadorDTO> obtenerJugadores(){
		log.info("Cargando jugadores");
				
		String endpoint = "http://localhost:8090/apiFutbol/jugadores";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
					
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
				
		HttpEntity requestJugadores = new HttpEntity<>(headers);
		ResponseEntity<JugadorDTO[]> respuesta = null;				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestJugadores, JugadorDTO[].class);
				
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
