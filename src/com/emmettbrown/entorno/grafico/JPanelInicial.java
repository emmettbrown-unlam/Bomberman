package com.emmettbrown.entorno.grafico;


import java.awt.Graphics;
import javax.swing.JPanel;

public class JPanelInicial extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JVentanaInicial ventana;
	
	public JPanelInicial(JVentanaInicial ventana) {
		this.ventana = ventana;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ventana.refrescarListaSalas();

	}

}