package com.emmettbrown.entidades;

import javax.swing.ImageIcon;

public class Bomberman extends Entidad {
	private static int nroBomberman = 0;
	private int idBomberman;
	public static final double VELOCIDAD = 1;

	public Bomberman(int posX, int posY) {
		super(posX, posY);
		idBomberman = nroBomberman++;
		this.destructible = true;
		this.img = new ImageIcon("./src/resources/character/bomberman.png");
	}
	
	/** PARAM
	 * 
	 * @param despX: desplazamiento en el eje X
	 * @param despY: desplazamiento en el eje Y
	 */
	
	public void morir() {
		esVisible = false;
		System.out.println("El bomberman " + idBomberman + " ha muerto");
	}
}
