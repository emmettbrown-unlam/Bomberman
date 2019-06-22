package com.emmettbrown.servidor.entidades;
import com.emmettbrown.servidor.entidades.Bomba;
import com.emmettbrown.servidor.entidades.Entidad;
import com.emmettbrown.servidor.entidades.Explosion;
import com.emmettbrown.servidor.mapa.ServerMap;
import com.emmettbrown.mapa.Ubicacion;

import java.io.Serializable;
import java.util.ArrayList;
import com.sun.javafx.geom.Rectangle;

public class SvBomberman extends Entidad implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int nroBomberman = 0;
	private int idBomberman;
	private ArrayList<Bomba> bombas;

	public SvBomberman(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		idBomberman = nroBomberman++;
		this.destructible = true;
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
		
	public void cambiarUbicacion(Ubicacion ubic) {
		this.ubicacion = ubic;
	}
	
	public int getIdBomberman() {
		return this.idBomberman;
	}

	@Override
	public void explotar(ServerMap map) {
		
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

	public int obtenerID() {
	
		return this.idBomberman;
	}
}
