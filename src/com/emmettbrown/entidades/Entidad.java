package com.emmettbrown.entidades;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.emmettbrown.mapa.*;

public abstract class Entidad {
	protected Ubicacion ubicacion;
	protected boolean esVisible;
	protected boolean destructible = true;
	protected ImageIcon img;
	
	public Entidad(final int posX,final int posY)
	{
		this.ubicacion = new Ubicacion(posX,posY);
		this.esVisible = true;
	}
	
	public Entidad(Ubicacion ubicacionAct)
	{
		this.ubicacion = ubicacionAct;
		this.esVisible = true;
	}
	
	public boolean verSiEsVisible() {
		return this.esVisible;
	}
	
	public void cambiarVisibilidad() {
		esVisible = !esVisible;
	}
	
	public Ubicacion obtenerUbicacion() {
		return this.ubicacion;
	}
	
	public Image getImagen() {
		return img.getImage();
	}
}
