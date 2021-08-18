package com.clienteFutbol.rest.cliente.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class RestUtilitario {
	
	//<t> puede devolver cualquier tipo
	
	public <T> ResponseEntity consumeRestServicePUT(String endPoint,HttpEntity request, Class<T> responseType) {  
	    try {
	    	RestTemplate restCliente = new RestTemplate();
	        return restCliente.exchange(endPoint,HttpMethod.PUT,request, responseType);
	    } catch (RestClientResponseException e) {
	        return ResponseEntity
	            .status(e.getRawStatusCode())
	            .body(e.getResponseBodyAsString());
	    }
	    
	    catch(Exception e) {
	    	return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		            
	    }
	}
	

	public <T> ResponseEntity consumeRestServicePOST(String endPoint,HttpEntity request, Class<T> responseType) {  
	    try {
	    	RestTemplate restCliente = new RestTemplate();
	        return restCliente.exchange(endPoint,HttpMethod.POST,request, responseType);
	    } catch (RestClientResponseException e) {
	        return ResponseEntity
	            .status(e.getRawStatusCode())
	            .body(e.getResponseBodyAsString());
	    }
	    catch(Exception e) {
	    	return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		            
	    }
	}
	
	public <T> ResponseEntity consumeRestServiceGET(String endPoint,HttpEntity request, Class<T> responseType) {  
	    try {
	    	RestTemplate restCliente = new RestTemplate();
	        return restCliente.exchange(endPoint,HttpMethod.GET,request, responseType);
	    } catch (RestClientResponseException e) {
	        return ResponseEntity
	            .status(e.getRawStatusCode())
	            .body(e.getResponseBodyAsString());
	    }
	    
	    catch(Exception e) {
	    	return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		            
	    }
	}
	
	public <T> ResponseEntity consumeRestServiceDELETE(String endPoint,HttpEntity request, Class<T> responseType) {  
	    try {
	    	RestTemplate restCliente = new RestTemplate();
	        return restCliente.exchange(endPoint,HttpMethod.DELETE,request, responseType);
	    } catch (RestClientResponseException e) {
	        return ResponseEntity
	            .status(e.getRawStatusCode())
	            .body(e.getResponseBodyAsString());
	    }
	    
	    catch(Exception e) {
	    	return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		            
	    }
	}
	
	
	

}
