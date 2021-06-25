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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.clienteFutbol.bean.UsuarioDTO;
import com.clienteFutbol.rest.cliente.security.GestorTokenSeguridad;
import com.clienteFutbol.rest.cliente.util.Paginas;
import com.clienteFutbol.rest.cliente.util.RestUtilitario;




@Controller
@RequestMapping("/usuarioController")
public class UsuarioController {
	private static final Logger log = LogManager.getLogger(UsuarioController.class);
	
	//http://localhost:9090/usuarioController/login
		@GetMapping(value="/login")
		public ModelAndView loginUsuario (Model model ) {
			log.info("inicio de loginUsuario ");
	
			
			ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALOGIN);
			modelAndView.addObject("usuarioLogin" , new UsuarioDTO());
			modelAndView.addObject("fallo", false);
			return modelAndView;
		}
		
		//------------------------------------------------------------
	
	//http://localhost:9090/usuarioController/register
	@GetMapping(value="/register")
	public ModelAndView registerUsuario (Model model ) {
		log.info("inicio de registerUsuario ");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAREGISTER);
		modelAndView.addObject("usuario" , new UsuarioDTO());
		
		return modelAndView;
	}
	
	//--------------------------------------------------------------------
	
	//http://localhost:9090/usuarioController/mostrarRegistre
	@GetMapping(value="/mostrarRegistre")
	public ModelAndView mostrarRegistre (Model model ) {
		log.info("inicio de mostrarRegistre ");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAREGISTER);
		modelAndView.addObject("usuario" , new UsuarioDTO());
		return modelAndView;
	
	}
	
	//------------------------------------------------------------------
	
	//http://loalhost:9090/usuarioController/saveUsuario
	@PostMapping(value ="/saveUsuario")
	public ModelAndView guardarUsuario(UsuarioDTO usuario , Model model) {
		log.debug("ini: guardarUsuario modelAndView");
		
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINALOGIN);
		modelAndView.addObject("usuarioLogin" , new UsuarioDTO());
					
		usuario.setEnable(true);
		
		String endPoint ="http://localhost:8090/apiFutbol/usuario";
		String token = GestorTokenSeguridad.obtenerToken();
			
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(usuario,headers);
		
		ResponseEntity<?> respuesta = null;
		
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServicePUT(
				endPoint, 
				request, 
				ResponseEntity.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			log.info("inserto correctamente");
			
		}else {
			log.info("fallo inserccion ");
			modelAndView = new ModelAndView(Paginas.PAGINAREGISTER);
		}
		
		modelAndView.addObject("usuario", this.loginUsuario(usuario, model));
				
		return modelAndView;
	}
	
	//http://localhost:9090/equipoController/loginUsuario
	@PostMapping("/loginUsuario")
	public ModelAndView loginUsuario (UsuarioDTO usuario , Model model) {
		log.debug("ini: loginUsuario modelAndView");
		ModelAndView modelAndView = new ModelAndView(Paginas.PAGINAINDEX);
		
		
		String endPoint ="http://localhost:8090/apiFutbol/usuarioNC";
		String token = GestorTokenSeguridad.obtenerToken();
		
		RestTemplate restCliente = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", token);
		HttpEntity request = new HttpEntity<>(usuario,headers);
		
		ResponseEntity<UsuarioDTO> respuesta = null;
		
		RestUtilitario resUtil = new RestUtilitario();
		
		respuesta = resUtil.consumeRestServicePOST(
							endPoint, 
							request,
							UsuarioDTO.class);
		
		if(respuesta.getStatusCodeValue()== HttpStatus.OK.value()) {
			log.info("Usuario Valido");
		}else {
			log.info("Usuario invalido");
			
			modelAndView = new ModelAndView(Paginas.PAGINALOGIN);
			modelAndView.addObject("fallo", true);
			modelAndView.addObject("usuarioLogin" , new UsuarioDTO());
		}
		
		return modelAndView;
		
		
	}
	
	
	
	
}
