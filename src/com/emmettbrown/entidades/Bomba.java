package com.emmettbrown.entidades;

import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;

public class Bomba extends Entidad {
	private static int nroBomba = 0;
	private int idBomba;
	private int segsExplosion;
	private int rango;

	
	/////////////////////////////////////// 
	//								    //
	//	       CONSTRUCTORES 		   //
	//								  //
	///////////////////////////////////	
	
	public Bomba(int posIniX, int posIniY) {
		super(posIniX, posIniY);
		idBomba = nroBomba++;
		segsExplosion = 3;
		this.destructible = true;
		this.rango = 1;
	}

	public Bomba(Ubicacion ubic) {
		super(ubic);
		idBomba = nroBomba++;
		segsExplosion = 3;
		this.destructible = true;
		this.rango = 1;
	}

	/////////////////////////////////////// 
	//								    //
	//	      GETTERS Y SETTERS		   //
	//								  //
	///////////////////////////////////	
	
	public int getIdBomba() {
		return idBomba;
	}
	
	/////////////////////////////////////// 
	//								    //
	//	      	  METODOS 		       //
	//								  //
	///////////////////////////////////	
	
	
	private void explosion(Ubicacion ubic, Mapa map) {
		//Buscamos una entidad en dicha ubicación. Solo puede haber una, así que buscamos 
		//la que esté ahí
		Entidad ent = map.obtenerEntidadEn(ubic);
		
		if (ent != null && ent.destructible && ent.esVisible) {
			//Si la entidad es una instancia de bomba...
			if (ent instanceof Bomba) {
				//Casteo furioso a Bomba
				((Bomba) ent).explotar(map);
			} else if (ent instanceof Obstaculo) {
				//Casteo furioso a Obstaculo
				((Obstaculo) ent).destruir();
				//Removemos a la entidad
				map.removerEntidadDelConjunto(ubic);
			} else if (ent instanceof Bomberman) {
				//Casteo furioso a Bomberman
				((Bomberman) ent).morir();
			}
		} 
	}

	public void explotar(Mapa map) {
		System.out.println("BUM, la bomba " + idBomba + " Exploto");
		//Seteamos visible = false para dejar de renderizar la bomba
		this.cambiarVisibilidad();
		
		//Creamos una "explosión" en en lugar
		explosion(new Ubicacion(this.ubicacion.getPosX(), this.ubicacion.getPosY()), map);
		
		//Creamos "explosiones" en las cuatro direcciones, dependiendo del rango
		for (int i = 1; i <= this.rango; i++) {
			explosion(new Ubicacion(this.ubicacion.getPosX()+i, this.ubicacion.getPosY()), map);	
			explosion(new Ubicacion(this.ubicacion.getPosX()-i, this.ubicacion.getPosY()), map);	
			explosion(new Ubicacion(this.ubicacion.getPosX(), this.ubicacion.getPosY()+i), map);	
			explosion(new Ubicacion(this.ubicacion.getPosX(), this.ubicacion.getPosY()-i), map);	
		}
		
		//Removemos la bomba de la lista de entidades
		map.removerEntidadDelConjunto(this.ubicacion);
	}
}
