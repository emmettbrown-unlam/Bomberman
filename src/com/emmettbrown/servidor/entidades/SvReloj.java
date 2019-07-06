package com.emmettbrown.servidor.entidades;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.Timer;

import com.emmettbrown.mensajes.cliente.MsgActualizarPuntajes;
import com.emmettbrown.servidor.HiloCliente;

public class SvReloj {

	private int hora,min,seg;
	private Timer t;
	
	public SvReloj (int h,int m,int s ) {
		this.hora = h;
		this.min = m;
		this.seg = s;
	}
	
	public void restarSegundo () {

		if (this.seg == 0) {
			if (this.min == 0) {
				if (this.hora > 0) {
					this.hora--;
					this.min = 59;
					this.seg = 59;
				}
			}else {
				this.min--;
				this.seg = 60;
			}
		}else {
			this.seg--;
		}
	}

	public boolean timeOut() {
		return this.hora==0 && this.min == 0 && this.seg == 0 ? true:false;
	}
	
	public void startTimer(SvSala sala) {
		t = new Timer(1000, new miOyente(sala,seg));
		t.start();
	}
	
	class miOyente implements ActionListener {
		private int limit;
		private SvSala s;
		public miOyente(SvSala s,int limit) {
			this.limit = limit;
			this.s = s;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			restarSegundo();
			if (timeOut()) {
				
				hora= 0;
				min = 0;
				seg = limit;
				
				HiloCliente h = s.obtenerHilosSala().get(0);
				List<SvBomberman> bombers = h.getMap().obtenerListaBomberman();
				HashMap <String,Integer> puntajes = s.obtenerTablero().getPuntuacion();
				for (SvBomberman bb : bombers) {
					int puntaje = 0;
					if (bb.estaVivo()) {
						puntaje = 1;
					}
					puntajes.put(bb.getNombre(), puntaje + puntajes.get(bb.getNombre() ));
				}
				
				h.broadcast(new MsgActualizarPuntajes(puntajes), h.getSalaConectada().getWriteSockets());
				t.stop();
			}	
		}
	}
}