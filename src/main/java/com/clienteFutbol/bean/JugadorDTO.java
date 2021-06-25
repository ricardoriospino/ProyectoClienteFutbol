package com.clienteFutbol.bean;

public class JugadorDTO {
	
	private int idJugador;
	private String codigoJugador;
	private String nombreJugador;
	private String fechaNacimiento;
	private int aniosDeDebut;
	private String nacionalidad;
	private int dorsal;
	private String posicion;
	private int numeroGoles;
	private int numeroFaltas;
	
	
	public JugadorDTO() {
		
	}


	public JugadorDTO(int idJugador, String codigoJugador, String nombreJugador, String fechaNacimiento,
			int aniosDeDebut, String nacionalidad, int dorsal, String posicion, int numeroGoles, int numeroFaltas) {
		super();
		this.idJugador = idJugador;
		this.codigoJugador = codigoJugador;
		this.nombreJugador = nombreJugador;
		this.fechaNacimiento = fechaNacimiento;
		this.aniosDeDebut = aniosDeDebut;
		this.nacionalidad = nacionalidad;
		this.dorsal = dorsal;
		this.posicion = posicion;
		this.numeroGoles = numeroGoles;
		this.numeroFaltas = numeroFaltas;
	}


	public int getIdJugador() {
		return idJugador;
	}


	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
	}


	public String getCodigoJugador() {
		return codigoJugador;
	}


	public void setCodigoJugador(String codigoJugador) {
		this.codigoJugador = codigoJugador;
	}


	public String getNombreJugador() {
		return nombreJugador;
	}


	public void setNombreJugador(String nombreJugador) {
		this.nombreJugador = nombreJugador;
	}


	public String getFechaNacimiento() {
		return fechaNacimiento;
	}


	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}


	public int getAniosDeDebut() {
		return aniosDeDebut;
	}


	public void setAniosDeDebut(int aniosDeDebut) {
		this.aniosDeDebut = aniosDeDebut;
	}


	public String getNacionalidad() {
		return nacionalidad;
	}


	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}


	public int getDorsal() {
		return dorsal;
	}


	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}


	public String getPosicion() {
		return posicion;
	}


	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}


	public int getNumeroGoles() {
		return numeroGoles;
	}


	public void setNumeroGoles(int numeroGoles) {
		this.numeroGoles = numeroGoles;
	}


	public int getNumeroFaltas() {
		return numeroFaltas;
	}


	public void setNumeroFaltas(int numeroFaltas) {
		this.numeroFaltas = numeroFaltas;
	}


	@Override
	public String toString() {
		return "JugadorDTO [idJugador=" + idJugador + ", codigoJugador=" + codigoJugador + ", nombreJugador="
				+ nombreJugador + ", fechaNacimiento=" + fechaNacimiento + ", aniosDeDebut=" + aniosDeDebut
				+ ", nacionalidad=" + nacionalidad + ", dorsal=" + dorsal + ", posicion=" + posicion + ", numeroGoles="
				+ numeroGoles + ", numeroFaltas=" + numeroFaltas + "]";
	}
	
	
}
