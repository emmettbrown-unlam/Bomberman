package com.emmettbrown.servidor;

import java.io.IOException;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mapa.Ubicacion;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgAgregarBomberman;
import com.emmettbrown.mensajes.cliente.MsgEliminarBomberman;
import com.emmettbrown.mensajes.cliente.MsgEliminarSala;
import com.emmettbrown.mensajes.cliente.MsgGenerarObstaculos;
import com.emmettbrown.mensajes.cliente.MsgIdCliente;
import com.emmettbrown.servidor.entidades.SvBomberman;
import com.emmettbrown.servidor.entidades.SvSala;
import com.emmettbrown.servidor.mapa.ServerMap;


public class HiloCliente extends Thread {

	private int idCliente;
	private boolean estaConectado;
	private transient Socket readSocket;
	private transient Socket writeSocket;
	//Bomberman relacionado a este cliente
	private SvBomberman bomber;
	//Lista de usuarios conectados (de todo el server)
	private ArrayList<Socket> usuariosConectados;
	//Thread de movimiento
	private HandleMovement movimiento;	
	//Listado de salas del servidor
	private ArrayList<SvSala> listaSalas;
	//Contador estático de ids de los clientes
	private static int idCounter = 0;	
	private SvSala salaConectada;
	private ServerMap map;
	private String nombreUsuario;
	private HashMap<String, Integer> puntajes;
	
	public HiloCliente(Socket writeSocket, Socket readSocket, ArrayList<Socket> usuariosConectados, ArrayList<SvSala> salas) {
		this.idCliente = idCounter++;
		//this.clientSocket = cliente;
		this.writeSocket = writeSocket;
		this.readSocket = readSocket;
		this.usuariosConectados = usuariosConectados;
		this.estaConectado = true;
		this.listaSalas = salas;
		//Le comunicamos al cliente cual es su ID
		enviarMsg(new MsgIdCliente(this.idCliente));
		this.puntajes = new HashMap<>();
	}
	
	public Socket getWriteSocket() {
		return this.writeSocket;
	}

	public boolean isEstaConectado() {
		return estaConectado;
	}

	public void setEstaConectado(boolean estaConectado) {
		this.estaConectado = estaConectado;
	}

	public ArrayList<Socket> getUsuariosConectados() {
		return usuariosConectados;
	}

	public void setUsuariosConectados(ArrayList<Socket> usuariosConectados) {
		this.usuariosConectados = usuariosConectados;
	}
	
	public ServerMap getMap() {
		return this.map;
	}
	
	public HandleMovement getMovementThread() {
		return this.movimiento;
	}
	
	public SvBomberman getBomber() {
		return this.bomber;
	}
	
	public ArrayList<SvSala> getSalas() {
		return this.listaSalas;
	}
	
	public SvSala getSalaConectada() {
		return this.salaConectada;
	}

	public void inicializarCliente(ServerMap map) {
		this.map = map;
		Ubicacion ubic = map.obtenerUbicacionInicio();		
		this.bomber = new SvBomberman(ubic.getPosX()*75, ubic.getPosY()*75, DefConst.DEFAULTWIDTH, DefConst.DEFAULTHEIGHT,this.getNombreUsuario());
		this.movimiento = new HandleMovement(this, salaConectada.getWriteSockets());
		this.movimiento.start();
		//Enviamos los obtasculos a todos los clientes
		this.broadcast(new MsgGenerarObstaculos(map.getObstaculos()), salaConectada.getWriteSockets());
		//Agregamos el bomber del cliente al mapa
		map.agregarBomberman(bomber);
		//Le decimos al cliente que añada el bomber
		this.broadcast(new MsgAgregarBomberman(bomber, idCliente), salaConectada.getWriteSockets());
	}
	
	public void enviarMsg(Msg msg) {
		try {
			ObjectOutputStream salidaACliente = new ObjectOutputStream(writeSocket.getOutputStream());
			salidaACliente.writeObject(msg);
		} catch (Exception e) {
			System.out.println("¡No se pudo enviar el mensaje! :)");
		}
	}
	
	public void broadcast(Msg msg, ArrayList<Socket> usuariosConectados) {		
		for (Socket writeSocket : usuariosConectados) {
			try {
				ObjectOutputStream salidaACliente = new ObjectOutputStream(writeSocket.getOutputStream());
				salidaACliente.writeObject(msg);
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}

	@Override
	public void run() {
		try {
			ObjectInputStream reciboMsg = new ObjectInputStream(readSocket.getInputStream());

			while (estaConectado) {
				/* Recibo Consulta de cliente */
				Msg msgRecibo = (Msg) reciboMsg.readObject();
				msgRecibo.realizarAccion(this);
				reciboMsg = new ObjectInputStream(readSocket.getInputStream());
			}

			reciboMsg.close();
			readSocket.close();
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problemas al querer leer otra petición: " + ex.getMessage());
			
			
			this.map.eliminarBomberman(this.bomber);
			this.usuariosConectados.remove(this.writeSocket);			
			eliminarSala(this.idCliente);
			broadcast(new MsgEliminarBomberman(this.bomber.getIdBomberman()), usuariosConectados);
			this.estaConectado = false;
		}
	}

	public void agregarSala(SvSala sala) {
		this.listaSalas.add(sala);
	}	
	
	public void setSalaConectada(SvSala sala) {
		this.salaConectada = sala;
	}
	
	//Elimina una sala tanto del lado servidor como cliente
	public void eliminarSala(int idCreador) {
		Iterator<SvSala> iter = listaSalas.iterator();
		
		while (iter.hasNext()) {
			SvSala sala = iter.next();
			
			if (sala.getIdCreador() == idCreador) {
				broadcast(new MsgEliminarSala(sala.getId()), usuariosConectados);
				iter.remove();
			}
		}
	}
	
	public int getIdCliente() {
		return this.idCliente;
	}

	public void setNombreUsuario(String nombre) {
		nombreUsuario = nombre;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void guardarPuntaje(HashMap<String, Integer> puntajes) {
		this.puntajes = puntajes;
//		for (Entry<String, Integer> entry : puntajes.entrySet()) {
//			this.puntajes.put(entry.getKey(),entry.getValue());
//		}
	}

	public HashMap<String, Integer> getPuntajes() {
		return puntajes;
	}
}
