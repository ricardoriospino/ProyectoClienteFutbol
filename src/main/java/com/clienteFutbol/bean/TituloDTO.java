package com.clienteFutbol.bean;

public class TituloDTO {
	
	private int idTitulo;
	private String anioTitulo;
	private String nombreTitulo;
	private boolean isNacional;
	private int idEquipo;
	private String codigoEquipo;
	private EquipoDTO equipo;
	
	public TituloDTO() {
		
	}

	public TituloDTO(int idTitulo, String anioTitulo, String nombreTitulo, boolean isNacional, int idEquipo,
			String codigoEquipo, EquipoDTO equipo) {
		super();
		this.idTitulo = idTitulo;
		this.anioTitulo = anioTitulo;
		this.nombreTitulo = nombreTitulo;
		this.isNacional = isNacional;
		this.idEquipo = idEquipo;
		this.codigoEquipo = codigoEquipo;
		this.equipo = equipo;
	}

	public int getIdTitulo() {
		return idTitulo;
	}

	public void setIdTitulo(int idTitulo) {
		this.idTitulo = idTitulo;
	}

	public String getAnioTitulo() {
		return anioTitulo;
	}

	public void setAnioTitulo(String anioTitulo) {
		this.anioTitulo = anioTitulo;
	}

	public String getNombreTitulo() {
		return nombreTitulo;
	}

	public void setNombreTitulo(String nombreTitulo) {
		this.nombreTitulo = nombreTitulo;
	}

	public boolean isNacional() {
		return isNacional;
	}

	public void setNacional(boolean isNacional) {
		this.isNacional = isNacional;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getCodigoEquipo() {
		return codigoEquipo;
	}

	public void setCodigoEquipo(String codigoEquipo) {
		this.codigoEquipo = codigoEquipo;
	}

	public EquipoDTO getEquipo() {
		return equipo;
	}

	public void setEquipo(EquipoDTO equipo) {
		this.equipo = equipo;
	}

	@Override
	public String toString() {
		return "TituloDTO [idTitulo=" + idTitulo + ", anioTitulo=" + anioTitulo + ", nombreTitulo=" + nombreTitulo
				+ ", isNacional=" + isNacional + ", idEquipo=" + idEquipo + ", codigoEquipo=" + codigoEquipo
				+ ", equipo=" + equipo + "]";
	}
	
	

}
