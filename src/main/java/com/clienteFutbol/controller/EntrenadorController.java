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

import com.clienteFutbol.bean.EntrenadorDTO;
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.Paginas;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;

@Controller
@RequestMapping("/entrenador")
public class EntrenadorController {
	private static final Logger log = LogManager.getLogger(EquipoController.class);
	
	

	// -----------------------------------------------------	
	//http://localhost:9090/entrenador/listaEntrenadores
	@GetMapping(value="/listaEntrenadores")
	public ModelAndView listaEntrenador (Model model ) {
		log.info("inicio listaEntrenador ");
								
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOENTRENADORES);			
		modelAndView.addObject("entrenadores",this.obtenerEntrenadores());

		return modelAndView ;
	}
	
	// ---------------------------------------------------
	//http://localhost:9090/entrenador/agregarEntrenador
	@GetMapping(value="/agregarEntrenador")
	public ModelAndView agregarEntrenador (Model model ) {
		log.info("inicio agregarEntrenador ");
	
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);	
		modelAndView.addObject("entrenadorSave" , new EntrenadorDTO());
		return modelAndView ;
	}
	
	// --------------------------------------------------
	//http://localhost:9090/entrenador/actualizarEntrenador/
	@GetMapping(value="/actualizarEntrenador/{idEntrenador}")
	public ModelAndView actualizarEntrenador (@PathVariable(name="idEntrenador", required = true) int idEntrenador,EntrenadorDTO entrenador,Model model ) {	
		log.info("inicio actualizarEntrenador ");
		
		entrenador = this.obtenerEntrenador(idEntrenador);				
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);	
		modelAndView.addObject("entrenadorSave" ,  entrenador);
	
		return modelAndView ;
	}
	
	//--------------------Eliminar-------------------------
	//http://localhost:9090/entrenador/eliminarEntrenador/
	@GetMapping( value = "/eliminarEntrenador/{idEntrenador}")
	public ModelAndView eliminarEntrenador (@PathVariable(name="idEntrenador", required = true) String idEntrenador ,Model model) {
		
		log.info("ini: eliminarEntrenador modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOENTRENADORES);
		
		String endPoint ="http://localhost:8090/apiFutbol/borrarEntrenador/" + idEntrenador;
		String token = GestorTokenSeguridad.obtenerToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(headers);
		RestUtilitario resUtil = new RestUtilitario();
		ResponseEntity<?> respuesta = null;
		
		try {
			
			respuesta = resUtil.consumeRestServiceDELETE(endPoint, request, ResponseEntity.class);				
			modelAndView = generarRespuestaEliminar(respuesta);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Fallo eliminar Entrenador" );
		}
		
		return modelAndView;
	}
	
	
	// -------------------Guardar--------------------------
	//http://localhost:9090/entrenador/saveEntrenador
	
	@PostMapping(value ="/saveEntrenador")
	public ModelAndView guardarEntrenador(EntrenadorDTO entrenador, Model model) {
		log.debug("ini: guardarEntrenador modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);
		
		log.info("Id entrenador : " + entrenador.getIdEntrenador());

		String endPoint ="http://localhost:8090/apiFutbol/entrenador";
		String token = GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(entrenador,headers);	
		RestUtilitario resUtil = new RestUtilitario();
		
		ResponseEntity<?> respuesta = null;
		
		
		//------ Guardar---------------------
		if(entrenador.getIdEntrenador() == 0) {
			
			respuesta = resUtil.consumeRestServicePUT(endPoint, request, ResponseEntity.class);	
			modelAndView = generarRespuestaInsertarActualizar(entrenador, respuesta);
			
		}else {
			// --------------- Actualizar-------
			log.info("Actualizando Entrenador");
			try {			
				respuesta = resUtil.consumeRestServicePOST(endPoint, request, ResponseEntity.class);
				modelAndView = generarRespuestaInsertarActualizar(entrenador, respuesta);
				
			} catch (RestClientException r) {
				r.printStackTrace();
				log.debug("fallo al actualizar");
			}		
		}

		return modelAndView;		
	}
	
	// ------------------Cargar Entrenador -------------------
	private EntrenadorDTO obtenerEntrenador(int idEntrenador) {
		log.info("Cargando entrenador");
		
		String endpoint = "http://localhost:8090/apiFutbol/entrenadorIdEntrenador/" + idEntrenador;
		String token =GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);	
		HttpEntity requestEntrenador = new HttpEntity<>(headers);	
		ResponseEntity<EntrenadorDTO> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestEntrenador, EntrenadorDTO.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			
			EntrenadorDTO entrenador = respuesta.getBody();
					
				return entrenador;
				
			}else {
				return new EntrenadorDTO();
			}	
	}
	
	//--------------------Listar--------------------------
	
	private List<EntrenadorDTO> obtenerEntrenadores(){
		log.info("Cargando entrenadores");
				
		String endpoint = "http://localhost:8090/apiFutbol/entrenadores";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);			
		HttpEntity requestEntrenador = new HttpEntity<>(headers);
		RestUtilitario resUtil = new RestUtilitario();
		ResponseEntity<EntrenadorDTO[]> respuesta = null;
	
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestEntrenador, EntrenadorDTO[].class);
				
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			EntrenadorDTO[] entrenadores = respuesta.getBody();
					
			List<EntrenadorDTO> lstEntrenador = new ArrayList<EntrenadorDTO>();
			for(EntrenadorDTO entrenador : entrenadores) {
				lstEntrenador.add(entrenador);
				}		
				return lstEntrenador;
				
			}else {
				return new ArrayList<>();
			}
		}
	// -------------------------------------------------------------------
		private ModelAndView generarRespuestaEliminar ( ResponseEntity<?> respuesta) {
			
			ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOENTRENADORES);

			 if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
				log.info("Se elimino correctamente");
				modelAndView.addObject("mensajeExitoEliminar", "Se Elimino correctamente!!" );
				modelAndView.addObject("exitoEliminar", true);
				
			 }else if (respuesta.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
				log.debug("No se puede eliminar porque tiene datos en otra tabla ");
				 modelAndView.addObject("mensajeFalloEliminarData", "No se puede eliminar Entrenador tiene datos en otras tablas!!" );
				 modelAndView.addObject("EntrenadorData", true);
				
			 }else {	 
				log.info("No se pudo eliminar Entrenador");	
				 modelAndView.addObject("mensajeFalloEliminar", "Ocurrio un error al eliminar" );
				 modelAndView.addObject("falloEliminar", true);
				 
			 }
			
			 modelAndView.addObject("entrenadores",this.obtenerEntrenadores());
			
			 
			 return modelAndView;
		}
		
		private ModelAndView generarRespuestaInsertarActualizar (EntrenadorDTO entrenador ,ResponseEntity<?> respuesta) {
			
			ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);
			
			if(entrenador.getIdEntrenador() == 0) {
				
				if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
					log.info("inserto correctamente");
					modelAndView.addObject("mensajeExitoGuardar", "Se guardo Correctamente!!" );
					modelAndView.addObject("exito", true);
					modelAndView.addObject("entrenadorSave" , new EntrenadorDTO());
					
				}else {
					log.info("fallo inserccion ");
					modelAndView.addObject("mensajeFalloGuardar", "Error no guardo correctamente!!" );
					modelAndView.addObject("fallo", true);
					modelAndView.addObject("entrenadorSave" ,  entrenador);
				}
				
			}else {
				
				if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
					log.info("actualizacion correcta");
					modelAndView.addObject("mensajeExitoActualizar", "Se Actualizo Correctamente!!" );
					modelAndView.addObject("exitoActualizar", true);
					modelAndView.addObject("entrenadorSave" , new EntrenadorDTO());
					
				}else {
					log.error("No se pudo actualizar");
			    	modelAndView.addObject("mensajeFalloActualizar", "Error no Actualizo Correctamente!!" );
			    	modelAndView.addObject("falloActualizar", true);
			    	modelAndView.addObject("entrenadorSave" , entrenador);
					
				}			
			}
		
			return modelAndView;
		}

}
