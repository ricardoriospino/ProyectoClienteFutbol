package com.clienteFutbol.bean;


public class EntrenadorDTO {
	
	private int idEntrenador;

	private String codigoEntrenador;
	
	private String nombreEntrenador;

	private String fechaNacimiento;

	private String nacionalidad;

	private String aniosInicioLaboral;

	private int titulosGanados;
	
	public EntrenadorDTO() {
		
	}

	public EntrenadorDTO(int idEntrenador, String codigoEntrenador, String nombreEntrenador, String fechaNacimiento,
			String nacionalidad, String aniosInicioLaboral, int titulosGanados) {
		super();
		this.idEntrenador = idEntrenador;
		this.codigoEntrenador = codigoEntrenador;
		this.nombreEntrenador = nombreEntrenador;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.aniosInicioLaboral = aniosInicioLaboral;
		this.titulosGanados = titulosGanados;
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

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getAniosInicioLaboral() {
		return aniosInicioLaboral;
	}

	public void setAniosInicioLaboral(String aniosInicioLaboral) {
		this.aniosInicioLaboral = aniosInicioLaboral;
	}

	public int getTitulosGanados() {
		return titulosGanados;
	}

	public void setTitulosGanados(int titulosGanados) {
		this.titulosGanados = titulosGanados;
	}

	@Override
	public String toString() {
		return "EntrenadorDTO [idEntrenador=" + idEntrenador + ", codigoEntrenador=" + codigoEntrenador
				+ ", nombreEntrenador=" + nombreEntrenador + ", fechaNacimiento=" + fechaNacimiento + ", nacionalidad="
				+ nacionalidad + ", aniosInicioLaboral=" + aniosInicioLaboral + ", titulosGanados=" + titulosGanados
				+ "]";
	}
	
	

}
