package com.clienteFutbol.bean;

import java.util.Date;

public class PartidoDTO {
	
	private int idPartido;
	private String codigoPartido;
	private Date fechaHora;
	private int numeroGoles;
	private int numeroFaltas;
	private int golesEquipo1;
	private int golesEquipo2;
	private int faltasEquipo1;
	private int faltasEquipo2;
	private String ganador;
	private String perdedor;
	private String arbitro;
	private String codigoEquipo1;
	private String codigoEquipo2;
	
	public PartidoDTO() {
		
	}

	public PartidoDTO(int idPartido, String codigoPartido, Date fechaHora, int numeroGoles, int numeroFaltas,
			int golesEquipo1, int golesEquipo2, int faltasEquipo1, int faltasEquipo2, String ganador, String perdedor,
			String arbitro, String codigoEquipo1, String codigoEquipo2) {
		super();
		this.idPartido = idPartido;
		this.codigoPartido = codigoPartido;
		this.fechaHora = fechaHora;
		this.numeroGoles = numeroGoles;
		this.numeroFaltas = numeroFaltas;
		this.golesEquipo1 = golesEquipo1;
		this.golesEquipo2 = golesEquipo2;
		this.faltasEquipo1 = faltasEquipo1;
		this.faltasEquipo2 = faltasEquipo2;
		this.ganador = ganador;
		this.perdedor = perdedor;
		this.arbitro = arbitro;
		this.codigoEquipo1 = codigoEquipo1;
		this.codigoEquipo2 = codigoEquipo2;
	}

	public int getIdPartido() {
		return idPartido;
	}

	public void setIdPartido(int idPartido) {
		this.idPartido = idPartido;
	}

	public String getCodigoPartido() {
		return codigoPartido;
	}

	public void setCodigoPartido(String codigoPartido) {
		this.codigoPartido = codigoPartido;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
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

	public int getGolesEquipo1() {
		return golesEquipo1;
	}

	public void setGolesEquipo1(int golesEquipo1) {
		this.golesEquipo1 = golesEquipo1;
	}

	public int getGolesEquipo2() {
		return golesEquipo2;
	}

	public void setGolesEquipo2(int golesEquipo2) {
		this.golesEquipo2 = golesEquipo2;
	}

	public int getFaltasEquipo1() {
		return faltasEquipo1;
	}

	public void setFaltasEquipo1(int faltasEquipo1) {
		this.faltasEquipo1 = faltasEquipo1;
	}

	public int getFaltasEquipo2() {
		return faltasEquipo2;
	}

	public void setFaltasEquipo2(int faltasEquipo2) {
		this.faltasEquipo2 = faltasEquipo2;
	}

	public String getGanador() {
		return ganador;
	}

	public void setGanador(String ganador) {
		this.ganador = ganador;
	}

	public String getPerdedor() {
		return perdedor;
	}

	public void setPerdedor(String perdedor) {
		this.perdedor = perdedor;
	}

	public String getArbitro() {
		return arbitro;
	}

	public void setArbitro(String arbitro) {
		this.arbitro = arbitro;
	}

	public String getCodigoEquipo1() {
		return codigoEquipo1;
	}

	public void setCodigoEquipo1(String codigoEquipo1) {
		this.codigoEquipo1 = codigoEquipo1;
	}

	public String getCodigoEquipo2() {
		return codigoEquipo2;
	}

	public void setCodigoEquipo2(String codigoEquipo2) {
		this.codigoEquipo2 = codigoEquipo2;
	}

	@Override
	public String toString() {
		return "PartidoDTO [idPartido=" + idPartido + ", codigoPartido=" + codigoPartido + ", fechaHora=" + fechaHora
				+ ", numeroGoles=" + numeroGoles + ", numeroFaltas=" + numeroFaltas + ", golesEquipo1=" + golesEquipo1
				+ ", golesEquipo2=" + golesEquipo2 + ", faltasEquipo1=" + faltasEquipo1 + ", faltasEquipo2="
				+ faltasEquipo2 + ", ganador=" + ganador + ", perdedor=" + perdedor + ", arbitro=" + arbitro
				+ ", codigoEquipo1=" + codigoEquipo1 + ", codigoEquipo2=" + codigoEquipo2 + "]";
	}
	
	

}
