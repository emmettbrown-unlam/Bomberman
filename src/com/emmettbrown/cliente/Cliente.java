package com.emmettbrown.cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entorno.grafico.JPanelGrafico;
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
	private transient Socket readSocket;
	private transient Socket writeSocket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Bomberman bomber;
	private Mapa mapa;
	private int idCliente;
	private JVentanaLobby lobbyActual;
	private ConcurrentLinkedQueue<Sala> listaSalas;
	private HashMap<String,Integer> puntajesJugadores;
	private Tablero tab;
	private int rondaActual;
	private JPanelGrafico panelGrafico;

	public Cliente(String ip, int puerto, String username) {
		try {
			this.username = username;
			this.host = ip;
			//Creamos los sockets de escritura y lectura
			this.readSocket = new Socket(host, puerto);
			this.writeSocket = new Socket(host, puerto);
			
			this.outputStream = new ObjectOutputStream(writeSocket.getOutputStream());
			this.inputStream = new ObjectInputStream(readSocket.getInputStream());
			
			this.mapa = new Mapa();
			this.puntajesJugadores = new HashMap<String,Integer>();
			this.listaSalas = new ConcurrentLinkedQueue<Sala>();
			this.tab = new Tablero();

			//Creamos un hilo escucha que se encargar� de leer las cosas que se env�en al readSocket
			ListenerThread listener = new ListenerThread(this);
			listener.start();
			//Enviamos un mensaje al servidor para que setee el nombre de usuario del cliente
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
			//Reseteamos el outputStream para enviar un nuevo mensaje
			outputStream.reset();
			outputStream.writeObject(consultaAlServidor);
		} catch (IOException e) {
			System.out.println("Error al querer enviar peticion al sv " + e);
		}
	}

	public Object recibirMsg() {
		Object obj = null;
		try {
			obj = inputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			this.mensajeError = "Comunicacion cerrada en recibir msg1. " + e;
			System.out.println(mensajeError);
		}
		return obj;
	}

	public void cerrarComunicacion() {
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

	public void setPanelGrafico(JPanelGrafico jPanelGrafico) {
		this.panelGrafico = jPanelGrafico;
	}
	
	public JPanelGrafico getPanelGrafico() {
		return this.panelGrafico ;
	}
	
}