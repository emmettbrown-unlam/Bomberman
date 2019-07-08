package com.emmettbrown.base.datos.base;

public class Usuario {
	private String usuario;
	private String contrase�a;
	private int puntaje;
	
//	<!--
//    <property name="hbm2ddl.auto">create</property>
//-->

	public Usuario() {
		
	}
	
	public Usuario( String usuario, String contrase�a) {
		
		this.usuario=usuario;
		this.contrase�a=contrase�a;
		this.puntaje=0;
		
	}
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
	
	public String getContrase�a() {
		return contrase�a;
	}
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	public int getPuntaje() {
		return puntaje;
	}
	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}

	@Override
	public String toString() {
		return "Usuario [usuario=" + usuario + ", contrase�a=" + contrase�a + ", puntaje=" + puntaje + "]";
	}

	
	
}
