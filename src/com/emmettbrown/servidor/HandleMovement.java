package com.emmettbrown.servidor;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.controles.Movimientos;
import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mensajes.cliente.MsgPonerBomba;
import com.emmettbrown.mensajes.cliente.MsgPosBomberman;

public class HandleMovement extends Thread {
	
	private HiloCliente hilo;
	//private ArrayList<Socket> socketsSala;
	private ArrayList<ObjectOutputStream> outputStreams;
	private Movimientos mov;
	
	public void setMovimiento(Movimientos mov) {
		this.mov = mov;
	}
	
	public HandleMovement(HiloCliente hilo, ArrayList<ObjectOutputStream> outputStreams) {
		this.hilo = hilo;
		this.mov = Movimientos.NULL;
		this.outputStreams = outputStreams;
	}
	
	public void handleInput() {
		switch(mov) {
		
		case IZQUIERDA:
			hilo.getMap().moverBombermanIzq(hilo.getBomber(), DefConst.VELOCIDAD);
			hilo.broadcast(new MsgPosBomberman(hilo.getBomber().obtenerID(), hilo.getBomber().getX(), hilo.getBomber().getY()), outputStreams);
			break;
			
		case DERECHA:
			hilo.getMap().moverBombermanDer(hilo.getBomber(), DefConst.VELOCIDAD);
			hilo.broadcast(new MsgPosBomberman(hilo.getBomber().obtenerID(), hilo.getBomber().getX(), hilo.getBomber().getY()), outputStreams);
			break;
		
		case ARRIBA: 
			hilo.getMap().moverBombermanArriba(hilo.getBomber(), DefConst.VELOCIDAD);
			hilo.broadcast(new MsgPosBomberman(hilo.getBomber().obtenerID(), hilo.getBomber().getX(), hilo.getBomber().getY()), outputStreams);
			break;
		
		case ABAJO:
			hilo.getMap().moverBombermanAbajo(hilo.getBomber(), DefConst.VELOCIDAD);
			hilo.broadcast(new MsgPosBomberman(hilo.getBomber().obtenerID(), hilo.getBomber().getX(), hilo.getBomber().getY()), outputStreams);
			break;
			
		case BOMBA:
			hilo.getMap().agregarBomba(hilo.getBomber().getX(), hilo.getBomber().getY(), hilo.getBomber());
			hilo.broadcast(new MsgPonerBomba(hilo.getBomber().getX(), hilo.getBomber().getY(), hilo.getBomber()), outputStreams);
			break;
			
		case NULL:
			break;
			
		default:
			break;
		}
	}
	
	
	public void run() {
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / DefConst.FPS;
		
		double deltaF = 0; // deltaU = 0, 

	    while (hilo.isEstaConectado()) {	    	
	        long currentTime = System.nanoTime();
	        deltaF += (currentTime - initialTime) / timeF;
	        initialTime = currentTime;
	        if (deltaF >= 1) {
	        	handleInput();
	            deltaF--;
	        }
	    }		
	}
}
