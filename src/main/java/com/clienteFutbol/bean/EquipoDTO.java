package com.clienteFutbol.bean;


public class EquipoDTO  {
	

	private int idEquipo;
	
	private String codigoEquipo;
	
	private String nombreEquipo;
	
	private String anioFundacion;

	private String imagenEscudo;

	private String duenioActual;

	private int cantidadTituloNacional;

	private int cantidadTituloInternacional;

	private String patrocinadorPrincipal;
	
	public EquipoDTO() {
		
		
	}

	public EquipoDTO(int idEquipo, String codigoEquipo, String nombreEquipo, int cantidadTituloNacional) {
		super();
		this.idEquipo = idEquipo;
		this.codigoEquipo = codigoEquipo;
		this.nombreEquipo = nombreEquipo;
		this.cantidadTituloNacional = cantidadTituloNacional;
	}


	

	public EquipoDTO(String codigoEquipo, String nombreEquipo, int cantidadTituloNacional, String anioFundacion,
			String imagenEscudo, String duenioActual, int cantidadTituloInternacional, String patrocinadorPrincipal) {
		super();
		this.codigoEquipo = codigoEquipo;
		this.nombreEquipo = nombreEquipo;
		this.cantidadTituloNacional = cantidadTituloNacional;
		this.anioFundacion = anioFundacion;
		this.imagenEscudo = imagenEscudo;
		this.duenioActual = duenioActual;
		this.cantidadTituloInternacional = cantidadTituloInternacional;
		this.patrocinadorPrincipal = patrocinadorPrincipal;
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

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public int getCantidadTituloNacional() {
		return cantidadTituloNacional;
	}

	public void setCantidadTituloNacional(int cantidadTituloNacional) {
		this.cantidadTituloNacional = cantidadTituloNacional;
	}

	public String getAnioFundacion() {
		return anioFundacion;
	}

	public void setAnioFundacion(String anioFundacion) {
		this.anioFundacion = anioFundacion;
	}

	public String getImagenEscudo() {
		return imagenEscudo;
	}

	public void setImagenEscudo(String imagenEscudo) {
		this.imagenEscudo = imagenEscudo;
	}

	public String getDuenioActual() {
		return duenioActual;
	}

	public void setDuenioActual(String duenioActual) {
		this.duenioActual = duenioActual;
	}

	public int getCantidadTituloInternacional() {
		return cantidadTituloInternacional;
	}

	public void setCantidadTituloInternacional(int cantidadTituloInternacional) {
		this.cantidadTituloInternacional = cantidadTituloInternacional;
	}

	public String getPatrocinadorPrincipal() {
		return patrocinadorPrincipal;
	}

	public void setPatrocinadorPrincipal(String patrocinadorPrincipal) {
		this.patrocinadorPrincipal = patrocinadorPrincipal;
	}

	@Override
	public String toString() {
		return "EquipoDTO [idEquipo=" + idEquipo + ", codigoEquipo=" + codigoEquipo + ", nombreEquipo=" + nombreEquipo
				+ ", cantidadTituloNacional=" + cantidadTituloNacional + ", anioFundacion=" + anioFundacion
				+ ", imagenEscudo=" + imagenEscudo + ", duenioActual=" + duenioActual + ", cantidadTituloInternacional="
				+ cantidadTituloInternacional + ", patrocinadorPrincipal=" + patrocinadorPrincipal + "]";
	}

	
	
}
