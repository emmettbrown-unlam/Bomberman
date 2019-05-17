package com.emmettbrown.entidades;

import javax.swing.ImageIcon;

public class Obstaculo extends Entidad{
	private static int nroObstaculo = 0;
	public int idObstaculo;
	
	public Obstaculo(final int posIniX,final int posIniY) {
		super(posIniX,posIniY);
		idObstaculo = nroObstaculo;
		nroObstaculo++;
		this.destructible = true;
		this.img = new ImageIcon("./src/resources/game-map/brick/obstaculo.png");
	}
	
	public void destruir() {
		if(esVisible == true) {
			esVisible = false;
			System.out.println("Obstaculo "+ idObstaculo +" Destruido");
		}
	}
	
	@Override
	public boolean verSiEsVisible() {
		return this.esVisible;
	}
	
}
