package com.emmettbrown.entorno.grafico;


import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.emmettbrown.mensajes.servidor.MsgReestablecerMotor;

public class GameLoop extends Thread {

	private JVentanaGrafica ventana;
	private int FPS; //Refresh rate
	private boolean corriendo;
	private int cantidadRondas;
	private int segXRonda;
	private int cantSeg;
	public GameLoop(JVentanaGrafica ventana, int FPS,int segXRonda) {
		this.ventana = ventana;
		this.FPS = FPS;
		this.corriendo = true;
		this.cantidadRondas = 1;
		ventana.obtenerCliente().setRound(cantidadRondas);
		this.segXRonda = segXRonda;
	}

	public void run() {
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / FPS;
		double deltaF = 0;
		cantSeg = 0;
		while (corriendo) {
			long currentTime = System.nanoTime();
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;
			
			if (deltaF >= 1) {
				ventana.repaint();
				cantSeg ++;
				deltaF--;
			}
			
			if ( cantSeg == segXRonda ) {
				//Pasan 60 segundos y tengo que cortar el thread
				
				if (cantidadRondas == DefConst.MAXROUND) {
					corriendo = false;
					JOptionPane.showMessageDialog(null, "La partida a finalizado!");
				}else {
					JOptionPane.showMessageDialog(null, "La ronda "+cantidadRondas+" ha finalizado!"); //Esto bloquea el repaint no el thread. no se porque tendriamos que buscar algo bloqueante
					System.out.println("paso!!");
					cantidadRondas++;
					ventana.obtenerCliente().setRound(cantidadRondas);
					ventana.obtenerCliente().enviarMsg(new MsgReestablecerMotor(ventana.obtenerCliente().getIdCliente()));
					ventana.obtenerCliente().getPanelGrafico().iniciarReloj();
					cantSeg = 0;
				}	
			}	
		}
	}
	
	public void matarThread() {
		this.corriendo = false;
		//ventana.dispo
	}

	public void refrescar() {
		ventana.repaint();
	}
}