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

import com.clienteFutbol.bean.EquipoDTO;
import com.clienteFutbol.bean.TituloDTO;
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.Paginas;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;

@Controller
@RequestMapping("/titulo")
public class TituloController {
	private static final Logger log = LogManager.getLogger(TituloController.class);
	

	//http://localhost:9090/titulo/listaTitulos
	@GetMapping(value="/listaTitulos")
	public ModelAndView listaEntrenador (Model model ) {
		log.info("inicio listaEntrenador ");
								
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTATITULOS);					
		modelAndView.addObject("titulos",this.obtenerTitulo());			
		return modelAndView ;
	}
	//------------------------------------------------------
	
	//http://localhost:9090/titulo/agregarTitulo
	@GetMapping(value="/agregarTitulo")
	public ModelAndView agregarTitulo (Model model ) {
		log.info("inicio agregarTitulo ");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);	
		modelAndView.addObject("tituloSave" , new TituloDTO());	
		modelAndView.addObject("equipos",this.obtenerEquipos());
		return modelAndView ;
	}
	
	//http://localhost:9090/titulo/actualizarTitulo/
	@GetMapping(value="/actualizarTitulo/{idTitulo}")
	public ModelAndView actualizarTitulo (@PathVariable(name="idTitulo", required = true) int idTitulo,TituloDTO titulo,Model model ) {
			
		log.info("inicio actualizarTitulo ");
			
		titulo = this.obtenerTitulo(idTitulo);
					
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);	
		modelAndView.addObject("tituloSave" ,  titulo);
		modelAndView.addObject("equipos",this.obtenerEquipos());
			
		return modelAndView ;
		}
	
	//---------------------Guardar Titulo --------------------------------------------
	//http://localhost:9090/titulo/saveTitulo
	@PostMapping(value ="/saveTitulo")
	public ModelAndView guardarTitulo(TituloDTO titulo, Model model) {
		log.debug("ini: guardarTitulo modelAndView");
		
		titulo.setNacional(true);		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);
		log.info("Id titulo : " + titulo.getIdTitulo());		

		String endPoint ="http://localhost:8090/apiFutbol/titulo";
		String token = GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(titulo,headers);
		ResponseEntity<?> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();
		
		if(titulo.getIdTitulo() == 0) {
			
			modelAndView.addObject("tituloSave" , new TituloDTO());

			respuesta = resUtil.consumeRestServicePUT(endPoint, request, ResponseEntity.class);
			modelAndView = generarRespuestaInsertarActualizar(titulo, respuesta);
						
		}else {
			
			log.info("Actualizando Titulo");

			try {
				
				respuesta = resUtil.consumeRestServicePOST(endPoint, request, ResponseEntity.class);
				modelAndView = generarRespuestaInsertarActualizar(titulo, respuesta);

			} catch (RestClientException r) {
				r.printStackTrace();
				log.debug("fallo al actualizar");
			}
			
		}

		return modelAndView;		
	}
	
	// ------------------------------------------------------
	private ModelAndView generarRespuestaInsertarActualizar (TituloDTO titulo,ResponseEntity<?> respuesta) {
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);
				
		if(titulo.getIdEquipo() == 0) {
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
				log.info("inserto correctamente");			
				modelAndView.addObject("equipos",this.obtenerEquipos());
				modelAndView.addObject("mensajeExitoGuardar", "Se guardo Correctamente!!" );
				modelAndView.addObject("exito", true);
				modelAndView.addObject("tituloSave" , new TituloDTO());
				
			}else {
				
				log.info("fallo inserccion ");			
				modelAndView.addObject("equipos",this.obtenerEquipos());
				modelAndView.addObject("mensajeFalloGuardar", "Error no guardo correctamente!!" );
				modelAndView.addObject("fallo", true);
				modelAndView.addObject("tituloSave" , titulo );
			}
			
		}else {
			
			if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
				log.info("actualizacion correcta");	
				modelAndView.addObject("equipos",this.obtenerEquipos());
				modelAndView.addObject("mensajeExitoActualizar", "Se Actualizo Correctamente!!" );
				modelAndView.addObject("exitoActualizar", true);
				modelAndView.addObject("tituloSave" , new TituloDTO());
				
		    }else {
		    	log.error("No se pudo actualizar");
		    	modelAndView.addObject("equipos",this.obtenerEquipos());
		    	modelAndView.addObject("mensajeFalloActualizar", "Error no Actualizo Correctamente!!" );
		    	modelAndView.addObject("falloActualizar", true);
		    	modelAndView.addObject("tituloSave" , titulo);
		    }
			
		}
		return modelAndView;
	}
	
	// ------------------Cargar Titulo -------------------
	private TituloDTO obtenerTitulo(int idTitulo) {
		log.info("Cargando titulo");
			
		String endpoint = "http://localhost:8090/apiFutbol/idTitulo/" + idTitulo ;
		String token =GestorTokenSeguridad.obtenerToken();
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);			
		HttpEntity requestTitulo = new HttpEntity<>(headers);		
		ResponseEntity<TituloDTO> respuesta = null;		
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestTitulo, TituloDTO.class);
			
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			TituloDTO titulo = respuesta.getBody();
						
				return titulo;
					
		}else {
				return new TituloDTO();
			  }	
		}
	
	// -------------------Lista Equipos ------------------------
	private List<EquipoDTO> obtenerEquipos(){
		log.info("Cargando equipos");
				
		String endpoint = "http://localhost:8090/apiFutbol/equipos";
		String token =GestorTokenSeguridad.obtenerToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);				
		HttpEntity requestEquipos = new HttpEntity<>(headers);
		ResponseEntity<EquipoDTO[]> respuesta = null;				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestEquipos, EquipoDTO[].class);
				
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			EquipoDTO[] equipos = respuesta.getBody();
					
			List<EquipoDTO> lstEquipo = new ArrayList<EquipoDTO>();
			for(EquipoDTO equipo : equipos) {
				lstEquipo.add(equipo);
				}		
				return lstEquipo;
				
			}else {
				return new ArrayList<>();
			}
		}
	

	// -------------------Lista---------------------------------
	private List<TituloDTO> obtenerTitulo(){
		log.info("Cargando titulos");
				
		String endpoint = "http://localhost:8090/apiFutbol/titulos";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);			
		HttpEntity requestTitulos = new HttpEntity<>(headers);
		ResponseEntity<TituloDTO[]> respuesta = null;				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestTitulos, TituloDTO[].class);
				
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
			TituloDTO[] titulos = respuesta.getBody();
					
			List<TituloDTO> lstTitulo = new ArrayList<TituloDTO>();
			for(TituloDTO titulo : titulos) {
				lstTitulo.add(titulo);
				}		
				return lstTitulo;
				
			}else {
				return new ArrayList<>();
			}
		}
}
