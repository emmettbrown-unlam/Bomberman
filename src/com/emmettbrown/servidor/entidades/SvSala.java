package com.emmettbrown.servidor.entidades;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.emmettbrown.mensajes.cliente.MsgActualizarPuntajes;
import com.emmettbrown.mensajes.cliente.MsgIniciarMotor;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.mapa.ServerMap;

public class SvSala {

	private int idSala;
	private int idCreador;
	private String nombre;
	private int limJugadores;
	private ArrayList<HiloCliente> clientesConectados;
	private ArrayList<String> nombresUsuariosConectados;
	//El mapa de la sala
	private ServerMap map;

	public SvSala(int id, int idCreador, String nombre, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.limJugadores = limJugadores;
		this.clientesConectados = new ArrayList<HiloCliente>();
		nombresUsuariosConectados = new ArrayList<String>();
	}

	public ArrayList<ObjectOutputStream> getWriteSockets() {
		//ArrayList<Socket> sockets = new ArrayList<Socket>();
		ArrayList<ObjectOutputStream> obs = new ArrayList<ObjectOutputStream>();
		
		for (HiloCliente hilo : clientesConectados) {
			//sockets.add(hilo.getWriteSocket());
			obs.add(hilo.getOutputStream());
		}
		
		return obs;		
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
		
		//Removemos la sala actual
		HiloCliente creador = clientesConectados.get(0);
		clientesConectados.get(0).eliminarSala(creador.getIdCliente());
		HashMap<String, Integer> h = new  HashMap<>();
		
		for (HiloCliente hiloCliente : clientesConectados) {
			hiloCliente.inicializarCliente(map);
			//Inicializamos el motor despues de todo así le damos la ilusión al cliente de que todo es rápido
			hiloCliente.enviarMsg(new MsgIniciarMotor());
			h.put(hiloCliente.getNombreUsuario(), 0);	
		}
		for (HiloCliente hiloCliente : clientesConectados) {
			hiloCliente.guardarPuntaje(h);
		}
		creador.broadcast(new MsgActualizarPuntajes(h), creador.getSalaConectada().getWriteSockets());
		
//		creador.comenzarPartida(creador.getIdCliente());	
	}

}