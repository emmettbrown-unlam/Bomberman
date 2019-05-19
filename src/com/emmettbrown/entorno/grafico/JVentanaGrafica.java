package com.emmettbrown.entorno.grafico;

import java.awt.Color;

import javax.swing.JFrame;

import com.emmettbrown.controles.Teclado;
import com.emmettbrown.entidades.Bomba;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;
import com.emmettbrown.principal.Motor;
import com.emmettbrown.principal.Temporizador;

public class JVentanaGrafica extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelGrafico contentPane;
	private Teclado teclado;
	private Bomberman miBomber;
	private Mapa miMapa;
	
	
	public JVentanaGrafica(Mapa miMapa, Bomberman miB, int ancho, int alto) {
		super("Bomberman");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(20, 20, ancho, alto);
		setBackground(Color.RED);
		setLocationRelativeTo(null);
		contentPane = new JPanelGrafico(miMapa);
		setBackground(Color.WHITE);
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		this.miBomber = miB;
		this.miMapa = miMapa;
		this.teclado = new Teclado();
		addKeyListener(teclado);
	}
	
	
	public void refrescar() {
		if ( this.teclado.isArriba() ) {
			miMapa.moverBombermanArriba(miBomber, Bomberman.VELOCIDAD);
		}	
		if (this.teclado.isIzq() ) {
			miMapa.moverBombermanIzq(miBomber,  Bomberman.VELOCIDAD);
		}	
		if ( this.teclado.isDer()  ) {
			miMapa.moverBombermanDer(miBomber, Bomberman.VELOCIDAD);
		}	
		if (  this.teclado.isAbajo() ) {
			miMapa.moverBombermanAbajo(miBomber,  Bomberman.VELOCIDAD);
		}	
		if(this.teclado.isPonerBomba()) {
			//Creamos una ubicaci�n nueva basandonos en la ubicacion del bomberman y el tama�o del tile
			Bomba bomb = new Bomba(new Ubicacion(miBomber.getX()/Motor.tileSize, miBomber.getY()/Motor.tileSize));
			miMapa.agregarBomba(bomb);
			Temporizador t = new Temporizador(bomb.getMs(),bomb,this.miMapa);
			t.iniciarTimer();
			
		}
		repaint();
	}
}
