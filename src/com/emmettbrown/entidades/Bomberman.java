package com.emmettbrown.entidades;

import com.emmettbrown.mapa.*;

public class Bomberman extends Entidad {
	private static int nroBomberman = 0;
	private int idBomberman;
	public static final double VELOCIDAD = 1;

	public Bomberman(int posIniX,int posIniY) {
		super(posIniX, posIniY);
		idBomberman = nroBomberman;
		nroBomberman++;
		this.destructible = true;
	}
	
	/** PARAM
	 * 
	 * @param despX: desplazamiento en el eje X
	 * @param despY: desplazamiento en el eje Y
	 */
	
//	public void moverse(double despX, double despY) {
//		Ubicacion ubic = this.ubicacion.clone();
//		ubic.cambiarPosX(despX);
//		ubic.cambiarPosY(despY);
//		
//		if (puedeMoverse(ubic)) {
//			this.ubicacion.cambiarPosX(despX);
//			this.ubicacion.cambiarPosY(despY);
//		}		
//	}
//	
//	
//	
//	public void moverIzquierda() {
//		moverse(-VELOCIDAD, 0);
//	}
//	
//	public void moverDerecha() {
//		moverse(VELOCIDAD, 0);
//	}
//	
//	public void moverArriba() {
//		moverse(0, -VELOCIDAD);
//	}
//	
//	public void moverAbajo() {
//		moverse(0, VELOCIDAD);
//	}
		
	@Override
	public boolean verSiEsVisible() {
		return this.esVisible;
	}

	public void morir() {
		if (this.esVisible == true) {
			esVisible = false;
			System.out.println("El bomberman " + idBomberman + " ha muerto");
		}

	}

	public void ponerBomba() {
		System.out.println("SE HA PUESTO LA BOMBA");
	}

	public Ubicacion obtenerUbicacion() {
		return this.ubicacion;
	}
}
