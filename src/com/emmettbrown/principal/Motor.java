package com.emmettbrown.principal;

import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entorno.grafico.JVentanaGrafica;
import com.emmettbrown.mapa.Mapa;

public class Motor {

	private Mapa miMapa;
	private JVentanaGrafica miVentana;
	private Bomberman miBomber;
	private boolean iniciado;
	private long segundos;

	public Motor() {
		miMapa = new Mapa();
		miMapa.generarMapa();
		miBomber = new Bomberman(0, 0); //Bomberman propio del usuario conectado.
		miMapa.agregarBomberman(miBomber);
		miVentana = new JVentanaGrafica(miMapa,miBomber);
		segundos = 0;
	}

	private void iniciarJuego() {
		this.iniciado = true;
		miVentana.setVisible(true);
	}

	private void gameLoop() {
		while (iniciado) {
			try {
				actualizar();
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void actualizar() {
		miVentana.refrescar();

	}

	public void cerrarJuego() {
		iniciado = false;
	}

	public static void main(String[] args) {
		Motor m = new Motor();
		m.iniciarJuego();
		m.gameLoop();
	}

}
