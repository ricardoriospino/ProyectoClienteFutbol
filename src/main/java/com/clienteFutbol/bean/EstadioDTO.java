package com.clienteFutbol.bean;

public class EstadioDTO {
	
	private int idEstadio;
	private String codigoEstadio;
	private String nombreEstadio;
	private int capacidad;
	private String ubicacion;
	
	public EstadioDTO() {
		
	}

	public EstadioDTO(int idEstadio, String codigoEstadio, String nombreEstadio, int capacidad, String ubicacion) {
		super();
		this.idEstadio = idEstadio;
		this.codigoEstadio = codigoEstadio;
		this.nombreEstadio = nombreEstadio;
		this.capacidad = capacidad;
		this.ubicacion = ubicacion;
	}

	public int getIdEstadio() {
		return idEstadio;
	}

	public void setIdEstadio(int idEstadio) {
		this.idEstadio = idEstadio;
	}

	public String getCodigoEstadio() {
		return codigoEstadio;
	}

	public void setCodigoEstadio(String codigoEstadio) {
		this.codigoEstadio = codigoEstadio;
	}

	public String getNombreEstadio() {
		return nombreEstadio;
	}

	public void setNombreEstadio(String nombreEstadio) {
		this.nombreEstadio = nombreEstadio;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	@Override
	public String toString() {
		return "EstadioDTO [idEstadio=" + idEstadio + ", codigoEstadio=" + codigoEstadio + ", nombreEstadio="
				+ nombreEstadio + ", capacidad=" + capacidad + ", ubicacion=" + ubicacion + "]";
	}
	
	
	
}
