package com.emmettbrown.controles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.mensajes.MsgMover;

public class Teclado implements KeyListener {
	
	private boolean arriba;
	private boolean abajo;
	private boolean izq;
	private boolean der;
	private boolean l;
	private boolean esc;
	private boolean w;
	private boolean a;
	private boolean d;
	private boolean s;	
	private boolean f;
	private Cliente cliente;
	
	public boolean isF() {
		return f;
	}	
	
	public boolean isW() {
		return w;
	}

	public boolean isA() {
		return a;
	}

	public boolean isD() {
		return d;
	}

	public boolean isS() {
		return s;
	}
	
	public boolean isArriba() {
		return arriba;
	}

	public boolean isAbajo() {
		return abajo;
	}

	public boolean isIzq() {
		return izq;
	}

	public boolean isEsc() {
		return esc;
	}

	public boolean isDer() {
		return der;
	}

	public boolean isL() {
		return l;
	}
	
	public Teclado(Cliente cliente) {
		this.arriba = false;
		this.abajo = false;
		this.izq = false;
		this.der = false;
		this.esc = false;
		this.f = false;
		this.l = false;
		this.w = false;
		this.a = false;
		this.s = false;
		this.d = false;
		this.cliente = cliente;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		//Jugador 1 (local)
		
		if (key == KeyEvent.VK_ESCAPE) {
			this.esc = true;
		}
		if (key == KeyEvent.VK_RIGHT) {
			//this.der = true;
			this.cliente.enviarMsg(new MsgMover(Movimientos.DERECHA));
		}
		if (key == KeyEvent.VK_LEFT) {
			//this.izq = true;
			this.cliente.enviarMsg(new MsgMover(Movimientos.IZQUIERDA));
		}
		if (key == KeyEvent.VK_UP) {
			//this.arriba = true;
			this.cliente.enviarMsg(new MsgMover(Movimientos.ARRIBA));
		}
		if (key == KeyEvent.VK_DOWN) {
			//this.abajo = true;
			this.cliente.enviarMsg(new MsgMover(Movimientos.ABAJO));
		}
		
		if (key == KeyEvent.VK_L) {
			//this.l = true;
			this.cliente.enviarMsg(new MsgMover(Movimientos.BOMBA));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		//Jugador 1 (local)

		if (key == KeyEvent.VK_ESCAPE) {
			this.esc = false;
		}
		if (key == KeyEvent.VK_RIGHT) {
			this.der = false;	
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
		}
		if (key == KeyEvent.VK_LEFT) {
			this.izq = false;
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
		}
		if (key == KeyEvent.VK_UP) {
			this.arriba = false;
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
		}
		if (key == KeyEvent.VK_DOWN) {
			this.abajo = false;
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
		}
		
		if (key == KeyEvent.VK_L) {
			this.l = false;
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
		}
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}