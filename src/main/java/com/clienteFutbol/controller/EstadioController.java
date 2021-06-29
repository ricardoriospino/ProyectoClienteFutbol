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

import com.clienteFutbol.bean.EntrenadorDTO;
import com.clienteFutbol.bean.EquipoDTO;
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
		modelAndView.addObject("fallo", false);
		modelAndView.addObject("exito", false);
		return modelAndView ;
	}
	
	//---------------------Guardar Estadio --------------------------------------------
	//http://localhost:9090/estadio/saveEstadio
	@PostMapping(value ="/saveEstadio")
	public ModelAndView guardarEstadio(EstadioDTO estadio, Model model) {
		log.debug("ini: guardarEstadio modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);
		
		
		String endPoint ="http://localhost:8090/apiFutbol/estadio";
		String token = GestorTokenSeguridad.obtenerToken();
			
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(estadio,headers);
		
		ResponseEntity<?> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServicePUT(
				endPoint, 
				request, 
				ResponseEntity.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			log.info("inserto correctamente");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);
			modelAndView.addObject("exito", true);
			modelAndView.addObject("estadioSave" , new EstadioDTO());
			
		}else {
			log.info("fallo inserccion ");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOESTADIO);
			modelAndView.addObject("fallo", true);
			modelAndView.addObject("estadioSave" , new EstadioDTO());
		}
		
		return modelAndView;
		
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
