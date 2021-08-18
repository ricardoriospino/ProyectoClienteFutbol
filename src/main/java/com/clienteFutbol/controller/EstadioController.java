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
		
	// ---------------------------------------------------------------------
	
	//http://localhost:9090/estadio/agregarEstadio
	@GetMapping(value="/agregarEstadio")
	public ModelAndView agregarEstadio(Model model ) {
		log.info("inicio agregarEstadio ");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);	
		modelAndView.addObject("estadioSave" , new EstadioDTO());

		return modelAndView ;
	}
	
	// ------------------------------------------------------------------------
	
	//http://localhost:9090/estadio/actualizarEstadio/
	@GetMapping(value="/actualizarEstadio/{idEstadio}")
	public ModelAndView actualizarEstadio (@PathVariable(name="idEstadio", required = true) int idEstadio,EstadioDTO estadio,Model model ) {
			
		log.info("inicio actualizarEstadio ");
			
		estadio = this.obtenerEstadio(idEstadio);				
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);	
		modelAndView.addObject("estadioSave" ,  estadio);
		
		return modelAndView ;
	}  
	//--------------------Eliminar-------------------------
	//http://localhost:9090/estadio/eliminarEstadio/
	@GetMapping( value = "/eliminarEstadio/{idEstadio}")
	public ModelAndView eliminarEstadio (@PathVariable(name="idEstadio", required = true) String idEstadio ,Model model) {
			
		log.info("ini: eliminarEstadio modelAndView");

		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTAESTADIOS);			
		String endPoint ="http://localhost:8090/apiFutbol/borrarEstadio/" + idEstadio;
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
				System.out.println("Fallo eliminar Entrenador");
			}
			
			return modelAndView;
		}

	//---------------------Guardar Estadio --------------------------------------------
	//http://localhost:9090/estadio/saveEstadio
	@PostMapping(value ="/saveEstadio")
	public ModelAndView guardarEstadio(EstadioDTO estadio, Model model) {
		log.debug("ini: guardarEstadio modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);
		log.info("Id estadio : " + estadio.getIdEstadio());

		String endPoint ="http://localhost:8090/apiFutbol/estadio";
		String token = GestorTokenSeguridad.obtenerToken();
			
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(estadio,headers);
		RestUtilitario resUtil = new RestUtilitario();	
		ResponseEntity<?> respuesta = null;
		
		if(estadio.getIdEstadio()==0) {

			respuesta = resUtil.consumeRestServicePUT(endPoint, request, ResponseEntity.class);		
			modelAndView = generarRespuestasInsertarActualizarEstadio(estadio, respuesta);
			
		// --------------- actualizar ------------
		}else {
			
			log.info("Actualizando Estadio");
			try {
				respuesta = resUtil.consumeRestServicePOST(endPoint, request, ResponseEntity.class);				
				modelAndView = generarRespuestasInsertarActualizarEstadio(estadio, respuesta);
	
			} catch (RestClientException r) {
				r.printStackTrace();
				log.debug("fallo al actualizar");
			}
		}		
		return modelAndView;
		
	}
	
	// ----------------------------------------------------
	private ModelAndView generarRespuestasInsertarActualizarEstadio (EstadioDTO estadio, ResponseEntity<?> respuesta) {
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);
		
		if(estadio.getIdEstadio()== 0) {
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				log.info("inserto correctamente");			
				modelAndView.addObject("mensajeExitoGuardar", "Se guardo Correctamente!!" );
				modelAndView.addObject("exito", true);
				modelAndView.addObject("estadioSave" , new EstadioDTO());
				
			}else {
				log.info("fallo inserccion ");				
				modelAndView.addObject("mensajeFalloGuardar", "Error no guardo correctamente!!" );
				modelAndView.addObject("fallo", true);
				modelAndView.addObject("estadioSave" , estadio);
			}
		}else {
			
			if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
				log.info("actualizacion correcta");				
				modelAndView.addObject("mensajeExitoActualizar", "Se Actualizo Correctamente!!" );
				modelAndView.addObject("exitoActualizar", true);
				modelAndView.addObject("estadioSave" , new EstadioDTO());
				
			}else {
				log.error("No se pudo actualizar");	    	
		    	modelAndView.addObject("mensajeFalloActualizar", "Error no Actualizo Correctamente!!" );
		    	modelAndView.addObject("falloActualizar", true);
		    	modelAndView.addObject("estadioSave" , estadio);
			}
		}
			
		return modelAndView;	
	}
	
	// -----------------------------------
	private ModelAndView generarRespuestaEliminar (ResponseEntity<?> respuesta) {
			
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTAESTADIOS);	
			
		if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
			log.info("Se elimino correctamente");
					
			modelAndView.addObject("exitoEliminar", true);
			modelAndView.addObject("estadios",this.obtenerEstadios());
		}else {
			log.info("No se pudo eliminar Entrenador");
				
			modelAndView.addObject("falloEliminar", true);
			modelAndView.addObject("estadios",this.obtenerEstadios());
		}

		return modelAndView;
	}
	
	// ------------------Cargar Estadio -------------------
	private EstadioDTO obtenerEstadio(int idEstadio) {
		System.out.println("Cargando estadio");
			
		String endpoint = "http://localhost:8090/apiFutbol/estadioIdEstadio/" + idEstadio;
		String token =GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);			
		HttpEntity requestEstadio = new HttpEntity<>(headers);			
		ResponseEntity<EstadioDTO> respuesta = null;
			
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestEstadio, EstadioDTO.class);
			
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			EstadioDTO estadio = respuesta.getBody();
						
			return estadio;
					
		}else {
			return new EstadioDTO();
			}	
		}
	// ----------------Lista estadios----------------------
	
	private List<EstadioDTO> obtenerEstadios(){
		log.info("Cargando estadios");
				
		String endpoint = "http://localhost:8090/apiFutbol/estadios";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);				
		HttpEntity requestEstadio = new HttpEntity<>(headers);
		ResponseEntity<EstadioDTO[]> respuesta = null;			
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestEstadio, EstadioDTO[].class);
				
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
