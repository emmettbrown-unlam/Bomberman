package com.emmettbrown.entorno.grafico;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.emmettbrown.entidades.Reloj;
public class Sala {

	private int idSala;
	private int idCreador;
	private String nombre;
	private int limJugadores;
	private int jugConectados;
	private ArrayList<String> usuarios;
	private Tablero tableroPuntos;
	private Timer t;
	private Reloj reloj;
	
	public Sala (int id, int idCreador, String nombre, int jugConectados, int limJugadores) {
		this.idSala = id;
		this.idCreador = idCreador;
		this.nombre = nombre;
		this.jugConectados = jugConectados;
		this.limJugadores = limJugadores;
		this.usuarios = new ArrayList<String>();
		this.tableroPuntos = new Tablero();
		
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
	
	public Tablero obtenerTablero() {
		return tableroPuntos;
	}
	
	public void startTimer(int limit) {
		t = new Timer(1000, new miOyente(limit));
		t.start();
	}
	
	public Reloj obtenerReloj () {
		return reloj;
	}
	class miOyente implements ActionListener {
		private int limit;
		public miOyente(int limit) {
			this.limit = limit;
			reloj = new Reloj(00,00,limit);
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			reloj.restarSegundo();
			if (reloj.timeOut()) {
				//Se acabo el tiempo. TIMEOUT
				reloj = new Reloj(00,00,limit);
				t.stop();
			}	
		}
	}
}
