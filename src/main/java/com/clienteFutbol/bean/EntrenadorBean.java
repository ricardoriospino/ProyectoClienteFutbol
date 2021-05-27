package com.clienteFutbol.bean;

import java.io.Serializable;

public class EntrenadorBean implements Serializable {
	
	private int idEntrenador;
	private String codigoEntrenador;
	private String nombreEntrenador;
	private String nacionalidad;
	
	public EntrenadorBean() {
		
	}

	public EntrenadorBean(int idEntrenador, String codigoEntrenador, String nombreEntrenador, String nacionalidad) {
		super();
		this.idEntrenador = idEntrenador;
		this.codigoEntrenador = codigoEntrenador;
		this.nombreEntrenador = nombreEntrenador;
		this.nacionalidad = nacionalidad;
	}

	public int getIdEntrenador() {
		return idEntrenador;
	}

	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}

	public String getCodigoEntrenador() {
		return codigoEntrenador;
	}

	public void setCodigoEntrenador(String codigoEntrenador) {
		this.codigoEntrenador = codigoEntrenador;
	}

	public String getNombreEntrenador() {
		return nombreEntrenador;
	}

	public void setNombreEntrenador(String nombreEntrenador) {
		this.nombreEntrenador = nombreEntrenador;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	@Override
	public String toString() {
		return "EntrenadorBean [idEntrenador=" + idEntrenador + ", codigoEntrenador=" + codigoEntrenador
				+ ", nombreEntrenador=" + nombreEntrenador + ", nacionalidad=" + nacionalidad + "]";
	}
	
	

}
