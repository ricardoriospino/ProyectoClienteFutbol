package com.clienteFutbol.bean;

public class PosicionesDTO {
	
	private int idPosiciones;
	private int temporada;
	private String nombreEquipo;
	private int partidosJugados;
	private int partidosGanados;
	private int partidosEmpatados;
	private int partidosPerdidos;
	private int puntos;
	
	public PosicionesDTO() {
		
	}

	public PosicionesDTO(int idPosiciones, int temporada, String nombreEquipo, int partidosJugados, int partidosGanados,
			int partidosEmpatados, int partidosPerdidos, int puntos) {
		super();
		this.idPosiciones = idPosiciones;
		this.temporada = temporada;
		this.nombreEquipo = nombreEquipo;
		this.partidosJugados = partidosJugados;
		this.partidosGanados = partidosGanados;
		this.partidosEmpatados = partidosEmpatados;
		this.partidosPerdidos = partidosPerdidos;
		this.puntos = puntos;
	}

	public int getIdPosiciones() {
		return idPosiciones;
	}

	public void setIdPosiciones(int idPosiciones) {
		this.idPosiciones = idPosiciones;
	}

	public int getTemporada() {
		return temporada;
	}

	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}

	public String getNombreEquipo() {
		return nombreEquipo;
	}

	public void setNombreEquipo(String nombreEquipo) {
		this.nombreEquipo = nombreEquipo;
	}

	public int getPartidosJugados() {
		return partidosJugados;
	}

	public void setPartidosJugados(int partidosJugados) {
		this.partidosJugados = partidosJugados;
	}

	public int getPartidosGanados() {
		return partidosGanados;
	}

	public void setPartidosGanados(int partidosGanados) {
		this.partidosGanados = partidosGanados;
	}

	public int getPartidosEmpatados() {
		return partidosEmpatados;
	}

	public void setPartidosEmpatados(int partidosEmpatados) {
		this.partidosEmpatados = partidosEmpatados;
	}

	public int getPartidosPerdidos() {
		return partidosPerdidos;
	}

	public void setPartidosPerdidos(int partidosPerdidos) {
		this.partidosPerdidos = partidosPerdidos;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	

}
