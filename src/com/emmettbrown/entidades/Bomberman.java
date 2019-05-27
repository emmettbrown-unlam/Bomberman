package com.emmettbrown.entidades;

import java.util.ArrayList;
import javax.swing.ImageIcon;

import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;
import com.sun.javafx.geom.Rectangle;

public class Bomberman extends Entidad {
	
	public static final int defaultWidth = 60;
	public static final int defaultHeight = 60;
	public static final int VELOCIDAD = 5;
	
	private static int nroBomberman = 0;
	private int idBomberman;
	private ImageIcon bomberIzq;
	private ImageIcon bomberDer;
	private ImageIcon bomberArr;
	private ImageIcon bomberAba;
	private ArrayList<Bomba> bombas;

	public Bomberman(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		idBomberman = nroBomberman++;
		this.destructible = true;
		this.img = new ImageIcon("./src/resources/character/bomberman.png");
		bomberIzq = new ImageIcon("./src/resources/character/bombermanIzq.png");
		bomberDer = new ImageIcon("./src/resources/character/bombermanDer.png");
		bomberArr = new ImageIcon("./src/resources/character/bombermanArr.png");
		bomberAba = new ImageIcon("./src/resources/character/bombermanAba.png");
		this.bombas = new ArrayList<Bomba>();
	}

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
	
	public void actualizarColBomba() {
		for (Bomba bomba : bombas) {
			Rectangle hitBoxBomber = this.getHitBox();
			Rectangle hitBoxBomba = bomba.getHitBox();
			//Vemos si existe una interseccion entre ambos rectangulos
			Rectangle intersection = hitBoxBomber.intersection(hitBoxBomba);		
			
			if (intersection.isEmpty()) {
				bomba.setIgnorarColisionCreador(false);
			}
		}
		
	}
	
	public boolean manejarColisionCon(Entidad ent) {
		if (ent instanceof Bomba) {
			return ((Bomba) ent).hayColisionConCreador(this);
		}
		
		if (ent instanceof Explosion) {
			this.morir();
			return true;
		}
		
		return true;
	}
	
	public void agregarBomba(Bomba bomba) {
		this.bombas.add(bomba);
	}
	
	public void removerBomba(Bomba bomba) {
		this.bombas.remove(bomba);
	}
}
