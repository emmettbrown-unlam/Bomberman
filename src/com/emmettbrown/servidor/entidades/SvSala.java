package com.emmettbrown.servidor.entidades;

import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.servidor.mapa.ServerMap;

public class SvSala {

	private int idSala;
	private int idCreador;
	private String nombre;
	private int limJugadores;
	private ArrayList<Socket> socketsUsuariosConectados;
	private ArrayList<String> nombresUsuariosConectados;
	//El mapa de la sala
	private ServerMap map;

	public SvSala(int id, int idCreador, String nombre, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
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
		nombresUsuariosConectados.add(usuario);
	}

	public String getNombre() {
		return nombre;
	}

	public int getJConect() {
		return socketsUsuariosConectados.size();
	}

	public ArrayList<String> obtenerUsuarios() {
		return nombresUsuariosConectados;
	}
	
	public int getLimJugadores() {
		return this.limJugadores;
	}
	
	public void iniciarPartida() {
		this.map = new ServerMap();
		this.map.generarMapa();
	}

}