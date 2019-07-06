package com.emmettbrown.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entorno.grafico.JVentanaLobby;
import com.emmettbrown.entorno.grafico.Sala;
import com.emmettbrown.entorno.grafico.Tablero;
import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.servidor.MsgActualizarNombre;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String host;
	private String mensajeError;
	private transient Socket clientSocket;
	private Bomberman bomber;
	private Mapa mapa;
	private int idCliente;
	private JVentanaLobby lobbyActual;
	private ConcurrentLinkedQueue<Sala> listaSalas;
	private HashMap<String,Integer> puntajesJugadores;
	private Tablero tab;
	private int rondaActual;

	public Cliente(String ip, int puerto, String username) {
		try {
			this.host = ip;
			this.clientSocket = new Socket(host, puerto);
			this.puntajesJugadores = new HashMap<String,Integer>();
			this.username = username;
			this.mapa = new Mapa();
			this.listaSalas = new ConcurrentLinkedQueue<Sala>();
			this.tab = new Tablero();
			ListenerThread listener = new ListenerThread(this);
			listener.start();
			enviarMsg(new MsgActualizarNombre(this.username));
		} catch (IOException e) {
			this.mensajeError = "No se encontro ningun servidor al cual conectarse!";
			System.out.println(mensajeError);
		}
	}
	
	public int getIdCliente() {
		return this.idCliente;
	}
	
	public void setIdCliente(int num) {
		this.idCliente = num;
	}
	
	public Tablero getTablero() {
		return tab;
	}
	
	public Bomberman getBomber() {
		return this.bomber;
	}
	
	public void setBomber(Bomberman bomber) {
		this.bomber = bomber;
	}
	
	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}
	
	public Mapa getMapa() {
		return this.mapa;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String obtenerMsgErr() {
		return mensajeError;
	}
		
	public void enviarMsg(Msg consultaAlServidor) {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			outputStream.writeObject(consultaAlServidor);
		} catch (IOException e) {
			System.out.println("Error al querer enviar peticion al sv " + e);
		}
	}

	public Object recibirMsg() {
		Object obj = null;
		try {
			ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
			obj = inputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			this.mensajeError = "Comunicacion cerrada en recibir msg1. " + e;
			System.out.println(mensajeError);
		}
		return obj;
	}

	public void cerrarComunicacion(ObjectInputStream inputStream, ObjectOutputStream outputStream) {
		try {
			inputStream.close();
			outputStream.close();
		} catch (IOException e) {
			this.mensajeError = "problemas al cerrar comunicacion. " + e;
			System.out.println(mensajeError);
		}
	}

	public ConcurrentLinkedQueue<Sala> getListaSalas() {
		return this.listaSalas;
	}

	public void limpiarSalas() {
		listaSalas.clear();		
	}

	public void setearLobby(JVentanaLobby lobby) {
		this.lobbyActual = lobby;
	}
	
	public JVentanaLobby getLobby() {
		return this.lobbyActual;
	}

	public HashMap<String,Integer> getPuntajes() {
		return this.puntajesJugadores;
	}
	
	public void agregarPuntaje(String k,int v) {
		this.tab.agregarPuntuacion(k, v);
	}

	public void setPuntajes(HashMap<String, Integer> hash) {
		this.puntajesJugadores = hash;
	}

	public void setRound(int cantidadRondas) {
		this.rondaActual = cantidadRondas;
	}
	
	public int getRoundActual() {
		return this.rondaActual;
	}
	
}