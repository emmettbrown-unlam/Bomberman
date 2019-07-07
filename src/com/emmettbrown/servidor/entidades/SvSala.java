package com.emmettbrown.servidor.entidades;


import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.entorno.grafico.Tablero;
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
	private Tablero tablero;
	//El mapa de la sala
	private ServerMap map;
	private SvReloj reloj;

	public SvSala(int id, int idCreador, String nombre, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.limJugadores = limJugadores;
		this.clientesConectados = new ArrayList<HiloCliente>();
		nombresUsuariosConectados = new ArrayList<String>();
		this.tablero = new Tablero();
		this.reloj = new SvReloj(0, 0, DefConst.SEG);
	}

	public ArrayList<ObjectOutputStream> getOutputStreams() {
		ArrayList<ObjectOutputStream> obs = new ArrayList<ObjectOutputStream>();
		
		for (HiloCliente hilo : clientesConectados) {
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
	
	public Tablero obtenerTablero() {
		return tablero;
	}
	
	public ArrayList<HiloCliente> obtenerHilosSala() {
		return clientesConectados;
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
			//Inicializamos el motor despues de todo as� le damos la ilusi�n al cliente de que todo es r�pido
			hiloCliente.enviarMsg(new MsgIniciarMotor());
			h.put(hiloCliente.getNombreUsuario(), 0);
			tablero.agregarPuntuacion(hiloCliente.getNombreUsuario(), 0);
		}
//		for (HiloCliente hiloCliente : clientesConectados) {
//			hiloCliente.guardarPuntaje(h);
//		}
//		creador.broadcast(new MsgActualizarPuntajes(h),creador.getSalaConectada().getWriteSockets());
		reloj.startTimer(this);
	}
	
	public void reiniciarReloj() {
		reloj.startTimer(this);
	}
}