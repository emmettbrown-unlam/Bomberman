package com.emmettbrown.entorno.grafico;

import java.awt.Color;
import java.awt.Font;


import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entidades.Entidad;
import com.emmettbrown.entidades.Reloj;
import com.emmettbrown.mapa.Ubicacion;
import com.emmettbrown.mensajes.servidor.MsgActualizarPts;

public class JPanelGrafico extends JPanel {

	private Map<Ubicacion, Entidad> conjuntoEntidades;
	private List<Bomberman> listaBomberman;
	private Cliente cliente;
	private HashMap<String, Integer> puntajes;
	private static final long serialVersionUID = 1L;
	private Reloj rl;
	
	public JPanelGrafico(Cliente cliente) {
		this.cliente = cliente;
		conjuntoEntidades = this.cliente.getMapa().getListaEntidades();
		listaBomberman = this.cliente.getMapa().obtenerListaBomberman();
		puntajes = this.cliente.getTablero().getPuntuacion();
		rl = new Reloj(00, 00, DefConst.SEG);
		iniciarReloj();
		cliente.setPanelGrafico(this);
	}
	
//	public void resetearVariables(HashMap<Ubicacion, Entidad> a , List<Bomberman> b , HashMap<String, Integer> c ) {
//		this.conjuntoEntidades = a;
//		this.listaBomberman = b;
//		this.puntajes = c;
//		iniciarReloj();
//	}
	public void iniciarReloj() {
		rl.startTimer();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Entidad entidades[] = conjuntoEntidades.values().toArray(new Entidad[0]);
			
		for (int i = 0; i < entidades.length; i++) {
			g.drawImage(entidades[i].getImagen(), entidades[i].getX(), entidades[i].getY(), DefConst.TILESIZE, DefConst.TILESIZE, null);
		}
		
		g.setColor(Color.GREEN);
		
		Bomberman bombermans[] = listaBomberman.toArray(new Bomberman[0]);
		for (int i = 0; i < bombermans.length; i++) {
			if (bombermans[i].verSiEsVisible()) {
				g.setColor(Color.BLACK);
				g.drawString(bombermans[i].getNombre(), bombermans[i].getX()+DefConst.DXNAME, bombermans[i].getY()+DefConst.DYNAME);
				g.drawImage(bombermans[i].getImagen(), bombermans[i].getX(), bombermans[i].getY(), DefConst.DEFAULTWIDTH, DefConst.DEFAULTHEIGHT, null);
			}
		}
		int dy = 0;
		
        g.setFont(new Font("Monospaced", Font.BOLD, 36));
		g.drawString(DefConst.TITLETAB, 750, 30);
		g.drawString("Ronda: "+cliente.getRoundActual(), 750, 55);
		
		for (Entry<String, Integer> entry : puntajes.entrySet()) {
			g.drawString(entry.getKey()+": "+entry.getValue(),680,85+dy);
			dy+=30;
		}
		
		g.drawString(rl.toString(), 760,9*75-75);
	}
}