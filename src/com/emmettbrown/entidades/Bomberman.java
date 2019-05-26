package com.emmettbrown.entidades;

import javax.swing.ImageIcon;

import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;

public class Bomberman extends Entidad {
	
	public static final int defaultWidth = 60;
	public static final int defaultHeight = 60;
	
	private static int nroBomberman = 0;
	private int idBomberman;
	public static final int VELOCIDAD = 5;
	private ImageIcon bomberIzq;
	private ImageIcon bomberDer;
	private ImageIcon bomberArr;
	private ImageIcon bomberAba;

	public Bomberman(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		idBomberman = nroBomberman++;
		this.destructible = true;
		this.img = new ImageIcon("./src/resources/character/bomberman.png");
		bomberIzq = new ImageIcon("./src/resources/character/bombermanIzq.png");
		bomberDer = new ImageIcon("./src/resources/character/bombermanDer.png");
		bomberArr = new ImageIcon("./src/resources/character/bombermanArr.png");
		bomberAba = new ImageIcon("./src/resources/character/bombermanAba.png");
	}
	
	/** PARAM
	 * 
	 * @param despX: desplazamiento en el eje X
	 * @param despY: desplazamiento en el eje Y
	 */
	
	public void morir() {
		esVisible = false;
	}
	
	public void cambiarPosX(double despX) {
		this.x += despX;
	}
	
	public void cambiarPosY(double despY) {
		this.y += despY;
	}
	
	public void cambiarImagenIzquierda() {
		setImage(bomberIzq);
	}
	public void cambiarImagenDerecha() {
		setImage(bomberDer);
	}
	public void cambiarImagenArriba() {
		setImage(bomberArr);
	}
	public void cambiarImagenAbajo() {
		setImage(bomberAba);
	}
	
	public void cambiarUbicacion(Ubicacion ubic) {
		this.ubicacion = ubic;
	}

	@Override
	public void explotar(Mapa map) {
		
	}
}
