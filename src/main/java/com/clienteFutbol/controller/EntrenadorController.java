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
		modelAndView.addObject("fallo", false);
		modelAndView.addObject("exito", false);
		return modelAndView ;
	}
	
	// -------------------Guardar--------------------------
	//http://localhost:9090/entrenador/saveEntrenador
	
	@PostMapping(value ="/saveEntrenador")
	public ModelAndView guardarEntrenador(EntrenadorDTO entrenador, Model model) {
		log.debug("ini: guardarEntrenador modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);
		
		
		String endPoint ="http://localhost:8090/apiFutbol/entrenador";
		String token = GestorTokenSeguridad.obtenerToken();
			
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(entrenador,headers);
		
		ResponseEntity<?> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServicePUT(
				endPoint, 
				request, 
				ResponseEntity.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			log.info("inserto correctamente");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);
			modelAndView.addObject("exito", true);
			modelAndView.addObject("entrenadorSave" , new EntrenadorDTO());
			
		}else {
			log.info("fallo inserccion ");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOENTRENADOR);
			modelAndView.addObject("fallo", true);
			modelAndView.addObject("entrenadorSave" , new EntrenadorDTO());
		}
		
		return modelAndView;
		
	}
	
	
	
	//--------------------Listar--------------------------
	
	private List<EntrenadorDTO> obtenerEntrenadores(){
		System.out.println("Cargando entrenadores");
				
		String endpoint = "http://localhost:8090/apiFutbol/entrenadores";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
				
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
				
		HttpEntity requestEntrenadores = new HttpEntity<>(headers);

		ResponseEntity<EntrenadorDTO[]> respuesta = null;
				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(
						endpoint, 
						requestEntrenadores, 
						EntrenadorDTO[].class);
				
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

}
