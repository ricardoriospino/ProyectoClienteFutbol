package com.clienteFutbol.rest.cliente.security;

public class AutenticationSecurity {
	
	private String usuario;
	private String clave;
	
	public AutenticationSecurity() {
		
	}

	public AutenticationSecurity(String usuario, String clave) {
		super();
		this.usuario = usuario;
		this.clave = clave;
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

	@Override
	public String toString() {
		return "AutenticationSecurity [usuario=" + usuario + ", clave=" + clave + "]";
	}

	
}
