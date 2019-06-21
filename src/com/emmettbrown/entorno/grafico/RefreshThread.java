package com.emmettbrown.entorno.grafico;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entidades.DefConst;

public class RefreshThread extends Thread {
	
	private JVentanaGrafica ventana;
	//private Cliente cliente;
	public RefreshThread(JVentanaGrafica ventana, Cliente cliente) {
		this.ventana = ventana;
		//this.cliente = cliente;
	}

	public void run() {
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / DefConst.FPS;
		double deltaF = 0; // deltaU = 0, 

	    while (true) {	    	
	        long currentTime = System.nanoTime();
	        deltaF += (currentTime - initialTime) / timeF;
	        initialTime = currentTime;
	        if (deltaF >= 1) {
	        	refrescar();
	            deltaF--;
	        }
	    }		
	}
	
	public void refrescar() {
		
		/*if (ventana.getTeclado().isArriba()) {
			//miMapa.moverBombermanArriba(miBomber, DefConst.VELOCIDAD,cliente);
		}
		else if (ventana.getTeclado().isIzq()) {
			//miMapa.moverBombermanIzq(miBomber, DefConst.VELOCIDAD,cliente);
		}
		else if (ventana.getTeclado().isDer()) {
			
			//miMapa.moverBombermanDer(miBomber, DefConst.VELOCIDAD,cliente);
		}
		else if (ventana.getTeclado().isAbajo()) {
			//miMapa.moverBombermanAbajo(miBomber, DefConst.VELOCIDAD,cliente);
		}
		else if (ventana.getTeclado().isL()) {
			//miMapa.agregarBomba(miBomber.getX(), miBomber.getY(), miBomber);
		}*/		

		ventana.repaint();
	}
}