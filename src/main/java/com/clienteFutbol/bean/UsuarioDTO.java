package com.clienteFutbol.bean;

public class UsuarioDTO {
	
	private int idUsuario;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String codigoUsuario;
	private String usuario;
	private String clave;
	private boolean isEnable;
	
	
	public UsuarioDTO() {
		
	}


	public UsuarioDTO(int idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno,
			String codigoUsuario, String usuario, String clave, boolean isEnable) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.codigoUsuario = codigoUsuario;
		this.usuario = usuario;
		this.clave = clave;
		this.isEnable = isEnable;
	}


	public int getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellidoPaterno() {
		return apellidoPaterno;
	}


	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}


	public String getApellidoMaterno() {
		return apellidoMaterno;
	}


	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}


	public String getCodigoUsuario() {
		return codigoUsuario;
	}


	public void setCodigoUsuario(String codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getClave() {
		return clave;
	}


	public void setClave(String clave) {
		this.clave = clave;
	}


	public boolean isEnable() {
		return isEnable;
	}


	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}


	@Override
	public String toString() {
		return "UsuarioDTO [idUsuario=" + idUsuario + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno
				+ ", apellidoMaterno=" + apellidoMaterno + ", codigoUsuario=" + codigoUsuario + ", usuario=" + usuario
				+ ", clave=" + clave + ", isEnable=" + isEnable + "]";
	}
	
	
}
