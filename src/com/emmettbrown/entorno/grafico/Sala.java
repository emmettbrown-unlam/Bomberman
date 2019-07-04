package com.emmettbrown.entorno.grafico;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class Sala implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idSala;
	private int idCreador;
	private String nombre;
	private int jugConectados;
	private int limJugadores;
	
	public Sala (int id, int idCreador, String nombre, int jugConectados, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.jugConectados = jugConectados;
		this.limJugadores = limJugadores;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public int getId() {
		return this.idSala;
	}
	
	public int getIdCreador() {
		return this.idCreador;
	}
	
}
