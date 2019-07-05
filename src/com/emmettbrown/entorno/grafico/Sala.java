package com.emmettbrown.entorno.grafico;

import java.util.ArrayList;

public class Sala {

	private int idSala;
	private int idCreador;
	private String nombre;
	private int limJugadores;
	private int jugConectados;
	private ArrayList<String> usuarios;
	
	public Sala (int id, int idCreador, String nombre, int jugConectados, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.jugConectados = jugConectados;
		this.limJugadores = limJugadores;
		this.usuarios = new ArrayList<String>();
	}

	@Override
	public String toString() {
		return nombre + " ---- Jugadores conectados: " + jugConectados + "/" + limJugadores;
	}
	
	public int getId() {
		return this.idSala;
	}
	
	public int getIdCreador() {
		return this.idCreador;
	}
	
	public void setJugConectados(int jugConectados) {
		this.jugConectados = jugConectados;
	}
	
	public ArrayList<String> getUsuarios() {
		return this.usuarios;
	}
	
	public void agregarUsuario(String cliente) {
		usuarios.add(cliente);
	}
	
	public void setUsuarios(ArrayList<String> usuarios) {
		this.usuarios = usuarios;
	}
}
