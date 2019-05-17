package com.emmettbrown.entorno.grafico;

import java.awt.Color;

import javax.swing.JFrame;

import com.emmettbrown.controles.Teclado;
import com.emmettbrown.entidades.Bomba;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.principal.Temporizador;

public class JVentanaGrafica extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelGrafico contentPane;
	private Teclado teclado;
	private Bomberman miBomber;
	private static final int ANCHO=1000;
	private static final int ALTO=1000;
	private Mapa miMapa;
	
	
	public JVentanaGrafica(Mapa miMapa,Bomberman miB) {
		super("Bomberman");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(20, 20, ANCHO, ALTO);
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
			miMapa.moverBombermanArriba(miBomber, 1);
		}	
		if (this.teclado.isIzq() ) {
			miMapa.moverBombermanIzq(miBomber,  1);
		}	
		if ( this.teclado.isDer()  ) {
			miMapa.moverBombermanDer(miBomber, 1);
		}	
		if (  this.teclado.isAbajo() ) {
			miMapa.moverBombermanAbajo(miBomber,  1);
		}	
		if(this.teclado.isPonerBomba()) {
			Bomba bomb = new Bomba(miBomber.obtenerUbicacion().clone());
			miMapa.agregarBomba(bomb);
			Temporizador t = new Temporizador(bomb.getMs(),bomb,this.miMapa);
			t.iniciarTimer();
			
		}
		repaint();
	}
}
