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
		modelAndView.addObject("fallo", false);
		modelAndView.addObject("exito", false);
		return modelAndView ;
	}
	
	//---------------------Guardar Titulo --------------------------------------------
	//http://localhost:9090/titulo/saveTitulo
	@PostMapping(value ="/saveTitulo")
	public ModelAndView guardarTitulo(TituloDTO titulo, Model model) {
		log.debug("ini: guardarTitulo modelAndView");
		
		titulo.setNacional(true);
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);
		modelAndView.addObject("tituloSave" , new TituloDTO());
		
		String endPoint ="http://localhost:8090/apiFutbol/titulo";
		String token = GestorTokenSeguridad.obtenerToken();
			
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(titulo,headers);
		
		ResponseEntity<?> respuesta = null;
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServicePUT(
				endPoint, 
				request, 
				ResponseEntity.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			
			log.info("inserto correctamente");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);
			modelAndView.addObject("exito", true);
			modelAndView.addObject("tituloSave" , new TituloDTO());
			
		}else {
			
			log.info("fallo inserccion ");
			modelAndView = new ModelAndView(Paginas.PAGINAFORMULARIOTITULO);
			modelAndView.addObject("fallo", true);
			modelAndView.addObject("tituloSave" , new TituloDTO());
		}
		
		return modelAndView;
		
	}
	
	// -------------------Lista---------------------------------
	private List<TituloDTO> obtenerTitulo(){
		System.out.println("Cargando titulos");
				
		String endpoint = "http://localhost:8090/apiFutbol/titulos";
		String token =GestorTokenSeguridad.obtenerToken();

		// Consumimos servicio Rest tipo Get
				
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
				
		HttpEntity requestTitulos = new HttpEntity<>(headers);

		ResponseEntity<TituloDTO[]> respuesta = null;
				
		RestUtilitario resUtil = new RestUtilitario();
			
		respuesta = resUtil.consumeRestServiceGET(
						endpoint, 
						requestTitulos, 
						TituloDTO[].class);
				
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
