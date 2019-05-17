package com.emmettbrown.entidades;

import javax.swing.ImageIcon;

public class Muro extends Entidad{
	
	public Muro(final int posIniX,final int posIniY) {
		super(posIniX,posIniY);
		this.destructible = false;
		this.img = new ImageIcon("./src/resources/game-map/environment.png");
	}
}
