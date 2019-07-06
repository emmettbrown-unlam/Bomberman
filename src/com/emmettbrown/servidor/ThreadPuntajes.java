package com.emmettbrown.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mensajes.cliente.MsgActualizarDatosSala;
import com.emmettbrown.mensajes.cliente.MsgActualizarPuntajes;
import com.emmettbrown.servidor.entidades.SvBomberman;

public class ThreadPuntajes extends Thread{

	private int FPS; //Refresh rate
	public HashMap <String,Integer> puntajes;
	private List<SvBomberman> bombers;
	private HiloCliente hilo;
	
//	public ThreadPuntajes(HiloCliente hilo,List<SvBomberman> bombers) {
//		puntajes = new HashMap<>();
//		this.bombers = bombers;
//		this.hilo = hilo;
//		FPS = 1;
//	}
	
	public void run() {
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / FPS;
		double deltaF = 0;
		int cantSeg = 0;
		while (cantSeg <= DefConst.TIMEOUT-1) {
			long currentTime = System.nanoTime();
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;
			if (deltaF >= 1) {
				cantSeg ++;
				deltaF--;
			}
		}
		for (SvBomberman bb : bombers) {
			int puntaje = 0;
			if (bb.estaVivo()) {
				puntaje = 1;
			}
			puntajes.put(bb.getNombre(), puntaje);
		}
		
		hilo.broadcast(new MsgActualizarPuntajes(puntajes), hilo.getSalaConectada().getWriteSockets());
	}
}
