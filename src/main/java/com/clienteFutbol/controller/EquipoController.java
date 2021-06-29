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
import org.springframework.web.client.RestTemplate;
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
		modelAndView.addObject("fallo", false);
		modelAndView.addObject("exito", false);
		return modelAndView ;
	}
	
	

	// ------------------------------------------------------------------
	
	private List<EquipoDTO> obtenerEquipos(){
		System.out.println("Cargando equipos");
				
		String endpoint = "http://localhost:8090/apiFutbol/equipos";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
				
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
				
		HttpEntity requestEquipos = new HttpEntity<>(headers);

		ResponseEntity<EquipoDTO[]> respuesta = null;
				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(
						endpoint, 
						requestEquipos, 
						EquipoDTO[].class);
				
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
	//---------------------Guardar Equipo --------------------------------------------
	//http://localhost:9090/equipoc/saveEquipo
	@PostMapping(value ="/saveEquipo")
	public ModelAndView guardarEquipo(EquipoDTO equipo, Model model) {
		log.debug("ini: guardarEquipo modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);
	
		String endPoint ="http://localhost:8090/apiFutbol/equipo";
		String token = GestorTokenSeguridad.obtenerToken();
			
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(equipo,headers);
		
		ResponseEntity<?> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServicePUT(
				endPoint, 
				request, 
				ResponseEntity.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			log.info("inserto correctamente");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);
			modelAndView.addObject("exito", true);
			modelAndView.addObject("equipoSave" , new EquipoDTO());
			
		}else {
			log.info("fallo inserccion ");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOQUIPO);
			modelAndView.addObject("fallo", true);
			modelAndView.addObject("equipoSave" , new EquipoDTO());
		}
		
		return modelAndView;
		
	}
	

	// --------------------- lista de equipos ------------------------------------------
	
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
