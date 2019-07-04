package com.emmettbrown.servidor.entidades;

import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.mensajes.cliente.MsgIniciarMotor;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.mapa.ServerMap;

public class SvSala {

	private int idSala;
	private int idCreador;
	private String nombre;
	private int limJugadores;
	//private ArrayList<Socket> socketsUsuariosConectados;
	private ArrayList<HiloCliente> clientesConectados;
	private ArrayList<String> nombresUsuariosConectados;
	//El mapa de la sala
	private ServerMap map;

	public SvSala(int id, int idCreador, String nombre, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.limJugadores = limJugadores;
		//socketsUsuariosConectados = new ArrayList<Socket>();
		this.clientesConectados = new ArrayList<HiloCliente>();
		nombresUsuariosConectados = new ArrayList<String>();
	}

	public ArrayList<Socket> getSockets() {
		ArrayList<Socket> sockets = new ArrayList<Socket>();
		
		for (HiloCliente hilo : clientesConectados) {
			sockets.add(hilo.getClientSocket());
		}
		
		return sockets;		
	}
	
	public int getClientesConectadosSize() {
		return this.clientesConectados.size();
	}

	public int getId() {
		return this.idSala;
	}

	public int getIdCreador() {
		return this.idCreador;
	}

	public void agregarUsuario(HiloCliente cliente, String usuario) {
		//socketsUsuariosConectados.add(cliente);
		clientesConectados.add(cliente);
		nombresUsuariosConectados.add(usuario);
	}

	public String getNombre() {
		return nombre;
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
		
		for (HiloCliente hiloCliente : clientesConectados) {
			hiloCliente.inicializarCliente(map);
		}
		for (HiloCliente hiloCliente : clientesConectados) {
			hiloCliente.enviarMsg(new MsgIniciarMotor());
		}
	}

}