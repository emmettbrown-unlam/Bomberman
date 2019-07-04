package com.emmettbrown.servidor.entidades;

import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

public class SvSala {

	private static final long serialVersionUID = 1L;
	private int idSala;
	private int idCreador;
	private String nombre;
	private int jugConectados;
	private int limJugadores;
	private ArrayList<Socket> socketsUsuariosConectados;
	private ArrayList<String> nombresUsuariosConectados;

	public SvSala(int id, int idCreador, String nombre, int jugConectados, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.jugConectados = jugConectados;
		this.limJugadores = limJugadores;
		socketsUsuariosConectados = new ArrayList<Socket>();
		nombresUsuariosConectados = new ArrayList<String>();
	}

	public ArrayList<Socket> getUsuariosConectados() {
		return socketsUsuariosConectados;
	}

	public int getId() {
		return this.idSala;
	}

	public int getIdCreador() {
		return this.idCreador;
	}

	public void agregarUsuario(Socket cliente,String usuario) {
		socketsUsuariosConectados.add(cliente);
		jugConectados++;
		nombresUsuariosConectados.add(usuario);
	}

	public String getNombre() {
		return nombre + " ---- Jugadores conectados: " + socketsUsuariosConectados.size() + "/" + limJugadores;
	}

	public int getJConect() {
		// TODO Auto-generated method stub
		return socketsUsuariosConectados.size();
	}

	public ArrayList<String> obtenerUsuarios() {
		return nombresUsuariosConectados;
	}

}