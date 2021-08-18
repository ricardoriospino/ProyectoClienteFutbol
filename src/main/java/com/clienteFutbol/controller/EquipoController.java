package com.clienteFutbol.controller;

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
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.Paginas;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;

import static com.clienteFutbol.rest.cliente.util.Paginas.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/equipoc")
public class EquipoController {
	private static final Logger log = LogManager.getLogger(EquipoController.class);
	
	//http://localhost:9090/equipoc/initEquipo
	@GetMapping(value = "/initEquipo")
	public ModelAndView inicioEquipo() {
		log.info("ini: inicioEquipo");
		
		ModelAndView model = new ModelAndView(Paginas.PAGINAINDEX);	
		return model  ;
	}
	
	//------------------------------------------------------------------------
	
	//http://localhost:9090/equipoc/listaEquipo
	@GetMapping(value="/listaEquipo")
	public ModelAndView listaEquipo (Model model ) {
		log.info("inicio listaEquipo ");

		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOEQUIPOS);		
		modelAndView.addObject("equipos",this.obtenerEquipos());
		return modelAndView ;
	}
	
	// ---------------------------------------------------------------------
	
	//http://localhost:9090/equipoc/agregarEquipo
	@GetMapping(value="/agregarEquipo")
	public ModelAndView agregarEquipo (Model model ) {
		log.info("inicio agregarEquipo ");
	
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);	
		modelAndView.addObject("equipoSave" , new EquipoDTO());
		return modelAndView ;
	}
	
	//------------------------------------------------------------------------
	//http://localhost:9090/equipoc/actualizarEquipo/
	@GetMapping(value="/actualizarEquipo/{idEquipo}")
	public ModelAndView actualizarEquipo (@PathVariable(name="idEquipo", required = true) int idEquipo,EquipoDTO equipo,Model model ) {		
		log.info("inicio actualizarEquipo ");
			
		equipo = this.obtenerEquipo(idEquipo);					
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);	
		modelAndView.addObject("equipoSave" ,  equipo);		
		return modelAndView ;
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
	
	//--------------------Eliminar-------------------------
	//http://localhost:9090/equipoc/eliminarEquipo/
	@GetMapping( value = "/eliminarEquipo/{idEquipo}")
	public ModelAndView eliminarEquipo (@PathVariable(name="idEquipo", required = true) String idEquipo ,Model model) {
			
		log.info("ini: eliminarEquipo modelAndView");

		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOEQUIPOS);		
		String endPoint ="http://localhost:8090/apiFutbol/borrarEquipo/" + idEquipo;
		String token = GestorTokenSeguridad.obtenerToken();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(headers);
		RestUtilitario resUtil = new RestUtilitario();	
		ResponseEntity<?> respuesta = null;
			
			try {					
				respuesta = resUtil.consumeRestServiceDELETE(endPoint, request, ResponseEntity.class);
				modelAndView = generarRespuestaEliminarEquipo(respuesta);
		
			} catch (Exception e) {
				e.printStackTrace();
				log.debug("Fallo eliminar Entrenador");
			}
			
		return modelAndView;
	}
	


	//---------------------Guardar Equipo --------------------------------------------
	//http://localhost:9090/equipoc/saveEquipo
	@PostMapping(value ="/saveEquipo")
	public ModelAndView guardarEquipo(EquipoDTO equipo, Model model) {
		log.debug("ini: guardarEquipo modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);		
		log.info("Id equipo : " + equipo.getIdEquipo());
		
		String endPoint ="http://localhost:8090/apiFutbol/equipo";
		String token = GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(equipo,headers);
		ResponseEntity<?> respuesta = null;		
		RestUtilitario resUtil = new RestUtilitario();
		
		// ----------Guardar --------------
		if(equipo.getIdEquipo() == 0) {
			
			respuesta = resUtil.consumeRestServicePUT(endPoint, request, ResponseEntity.class);
			modelAndView = generarRespuestaInsertarActualizar(equipo, respuesta);
		
		// --------------- actualizar -----------
		}else {	
			log.info("Actualizando Equipo");	
			try {		
				
				respuesta = resUtil.consumeRestServicePOST(endPoint, request, ResponseEntity.class);	
				modelAndView = generarRespuestaInsertarActualizar(equipo, respuesta);
				
			} catch (RestClientException r) {
				log.debug("fallo al actualizar");
			}
		}
	
		return modelAndView;
		
	}
	
	// -------------------------------------------------------------------------
	private ModelAndView generarRespuestaInsertarActualizar (EquipoDTO equipo,ResponseEntity<?> respuesta) {
			
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);
		
		if(equipo.getIdEquipo() == 0) {
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				log.info("inserto correctamente");				
				modelAndView.addObject("mensajeExitoGuardar", "Se guardo Correctamente!!" );
				modelAndView.addObject("exito", true);
				modelAndView.addObject("equipoSave" , new EquipoDTO());
				
			}else {
				log.info("fallo inserccion ");
				modelAndView.addObject("mensajeFalloGuardar", "Error no guardo correctamente!!" );
				modelAndView.addObject("fallo", true);
				modelAndView.addObject("equipoSave" , equipo);
			}
			
		}else {
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				log.info("actualizacion correcta");
				modelAndView.addObject("mensajeExitoActualizar", "Se Actualizo Correctamente!!" );
				modelAndView.addObject("exitoActualizar", true);
				modelAndView.addObject("equipoSave" , new EquipoDTO());
				
			}else if(respuesta.getStatusCodeValue()== HttpStatus.BAD_REQUEST.value()) {
				log.info("Nombre Usuario Ya existe ");
				modelAndView.addObject("mensajeExiste", "Nombre " + equipo.getNombreEquipo() + " Equipo ya existe" );
				modelAndView.addObject("existe", true);
				modelAndView.addObject("equipoSave" , equipo);
			}else {
				log.error("No se pudo actualizar");
		    	modelAndView.addObject("mensajeFalloActualizar", "Error no Actualizo Correctamente!!" );
		    	modelAndView.addObject("falloActualizar", true);
		    	modelAndView.addObject("equipoSave" , equipo);
				
			}			
		}	
		return modelAndView;			
	}
	
	// ------------------------------------------------------------------------------
	private ModelAndView generarRespuestaEliminarEquipo ( ResponseEntity<?> respuesta) {
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALISTADOEQUIPOS);
		 
		if(respuesta.getStatusCode().equals(HttpStatus.OK)) {
			log.info("Se elimino correctamente");
			modelAndView.addObject("exitoEliminar", true);
			modelAndView.addObject("equipos",this.obtenerEquipos());
		}else {
			log.info("No se pudo eliminar Equipo");
			modelAndView.addObject("falloEliminar", true);
			modelAndView.addObject("equipos",this.obtenerEquipos());
		}
 
		 return modelAndView;		 
	}

	// ------------------Cargar Equipo -------------------
	private EquipoDTO obtenerEquipo(int idEquipo) {
		log.info("Cargando equipo");
			
		String endpoint = "http://localhost:8090/apiFutbol/equipoIdEquipo/" + idEquipo;
		String token =GestorTokenSeguridad.obtenerToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
			
		HttpEntity requestEquipo = new HttpEntity<>(headers);		
		ResponseEntity<EquipoDTO> respuesta = null;			
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(endpoint, requestEquipo, EquipoDTO.class);
			
			if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
				
				EquipoDTO equipo = respuesta.getBody();				
				return equipo;
					
				}else {
					return new EquipoDTO();
				}	
		}
		
	// --------------------- lista de equipos ------------------------------------------
	
	private EquipoDTO invocarServicioPorNombreEquipo(String nombreEquipo) {
		
		log.info("Cargando Equipo");
		
		String endPoint = "http://localhost:8090/apiFutbol/equipo/"+nombreEquipo;
		String token = GestorTokenSeguridad.obtenerToken();
		
		// consumimos el servicio Rest tipo Get
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		
		HttpEntity requestEquipo = new HttpEntity<>(headers);		
		ResponseEntity<EquipoDTO> respuesta = null;		
		RestUtilitario restUtil = new RestUtilitario();		
		EquipoDTO equipo = new EquipoDTO();
		
		respuesta = restUtil.consumeRestServiceGET(endPoint, requestEquipo, EquipoDTO.class);
		
		if (respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			 equipo = respuesta.getBody();
					
		}else {
			log.error("Fallo invocarServicioPorNombreEquipo ");
		}
		return equipo;
	
	}
	// ------------------------------------------------------------------
	
	private List<EquipoDTO> obtenerEquipos(){
		log.info("Cargando equipos");
					
		String endpoint = "http://localhost:8090/apiFutbol/equipos";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get

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
}
