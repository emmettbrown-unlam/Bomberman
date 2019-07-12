package com.emmettbrown.base.datos.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="USUARIO")
public class Usuario implements Serializable{

	@Id
	@Column(name="usuario")
	private String usuario;
	@Column(name="contrase�a")
	private String contrase�a;
	@Column(name="puntaje")
	private int puntaje;
	
//	<!--
//    <property name="hbm2ddl.auto">create</property>
//-->
	public Usuario(){}
	public Usuario( String usuario, String contrase�a,int puntaje) {
		
		this.usuario=usuario;
		this.contrase�a=contrase�a;
		this.puntaje= puntaje;
		
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
