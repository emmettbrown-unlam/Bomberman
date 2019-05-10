package com.emmettbrown.entidades;

import java.util.List;
import java.util.Map;

import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;

public class Bomba extends Entidad {
	private static int nroBomba = 0;
	private int idBomba;
	private int segsExplosion;
	private int rango;

	public Bomba(final int posIniX, final int posIniY) {
		super(posIniX, posIniY);
		idBomba = nroBomba;
		nroBomba++;
		segsExplosion = 3;
		this.destructible = true;
		this.rango = 2;
	}

	public Bomba(Ubicacion miUbicacion) {
		super(miUbicacion);
		idBomba = nroBomba;
		nroBomba++;
		segsExplosion = 3;
	}

	private boolean explotarEnCadena(Ubicacion ubic, Mapa map) {
		List<Bomberman> listaBomb = map.obtenerListaBomberman();		
		Map<Ubicacion, Entidad> listaEnt = map.obtenerListaEntidades();
		Entidad ent = listaEnt.get(ubic);
		Bomba exp;
		if (ent != null && ent.destructible && ent.esVisible == true) {

			if (ent.getClass().getSimpleName().equals("Bomba") ) {
				exp = (Bomba) ent;
				exp.explotar(map);

			} else if (ent.getClass().getSimpleName().equals("Obstaculo")) {
				((Obstaculo)ent).destruir();
				listaEnt.remove(ubic);
			}

			return true;

		} else if (ent != null && !ent.destructible) {
			return false;
		}
		if (listaBomb != null) {
			for (Bomberman bomberman : listaBomb) {
				if (bomberman.ubicacion.equals(ubic)) {
					bomberman.morir();
				}
			}			
		}

		return false;
	}

	public void explotar(Mapa m) {
		System.out.println("BUM, la bomba " + idBomba + " Exploto");
		this.cambiarVisibilidad();
		explotarEnCadena(new Ubicacion(this.ubicacion.getPosX(),this.ubicacion.getPosY()),m);	
		explotarEnCadena(new Ubicacion(this.ubicacion.getPosX()+1,this.ubicacion.getPosY()),m);	
		explotarEnCadena(new Ubicacion(this.ubicacion.getPosX()-1,this.ubicacion.getPosY()),m);	
		explotarEnCadena(new Ubicacion(this.ubicacion.getPosX(),this.ubicacion.getPosY()+1),m);	
		explotarEnCadena(new Ubicacion(this.ubicacion.getPosX(),this.ubicacion.getPosY()-1),m);	
		m.eliminarBomba(this.ubicacion);
	}
}
