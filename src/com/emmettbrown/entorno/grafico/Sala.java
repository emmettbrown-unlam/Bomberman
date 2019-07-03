package com.emmettbrown.entorno.grafico;

import java.io.Serializable;

public class Sala implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private int jugConectados;
	private int limJugadores;
	
	public Sala (int id, String nombre, int jugConectados, int limJugadores) {
		this.id = id;
		this.nombre = nombre;
		this.jugConectados = jugConectados;
		this.limJugadores = limJugadores;
	}

	@Override
	public String toString() {
		return "Sala [nombre=" + nombre + ", jugConectados=" + jugConectados + ", limJugadores=" + limJugadores + "]";
	}
	
	public int getId() {
		return this.id;
	}
	
	
}
