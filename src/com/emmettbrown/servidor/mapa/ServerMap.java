package com.emmettbrown.servidor.mapa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.emmettbrown.entidades.DefConst;
import com.emmettbrown.mapa.Ubicacion;
import com.emmettbrown.servidor.entidades.Entidad;
import com.emmettbrown.servidor.entidades.Muro;
import com.emmettbrown.servidor.entidades.SvObstaculo;
import com.emmettbrown.servidor.entidades.SvBomberman;
import com.sun.javafx.geom.Rectangle;

public class ServerMap {
	
	private HashMap<Ubicacion, Entidad> conjuntoEntidades;
	private ArrayList<SvObstaculo> obstaculos;
	private List<SvBomberman> listaBomberman;
	//private double seed;
	
	///////////////////////////////////////
	// 									//
	// 			CONSTUCTORES		   //
	// 								  //
	///////////////////////////////////

	public ServerMap() {
		conjuntoEntidades = new HashMap<Ubicacion, Entidad>();
		listaBomberman = new ArrayList<SvBomberman>();
		obstaculos = new ArrayList<SvObstaculo>();
	}

	////////////////////////////////////////
	// 									 //
	// 				METODOS 			//
	// 								   //
	////////////////////////////////////

	public void generarMapa() {
		//Genera los muros exteriores
		for(int l = 0; l < DefConst.ALTOMAPA; l++) {
			conjuntoEntidades.put(new Ubicacion(0, l), new Muro(0 * DefConst.TILESIZE, l * DefConst.TILESIZE));
			conjuntoEntidades.put(new Ubicacion(l, 0), new Muro(l * DefConst.TILESIZE, 0 * DefConst.TILESIZE));
			conjuntoEntidades.put(new Ubicacion(DefConst.ANCHOMAPA-1, l), new Muro((DefConst.ANCHOMAPA-1) *DefConst.TILESIZE, l * DefConst.TILESIZE));
			conjuntoEntidades.put(new Ubicacion(l, DefConst.ALTOMAPA-1), new Muro(l * DefConst.TILESIZE, (DefConst.ALTOMAPA-1)* DefConst.TILESIZE));
		}
				
		//Genera los obstaculos
		for (int i = 1; i < DefConst.ANCHOMAPA-1; i++) {
			for (int j = 1; j < DefConst.ALTOMAPA-1; j++) {
				if (i % 2 == 0 && j % 2 == 0) { 
					//EN LAS POSICIONES I,J IMPARES PONDREMOS INDESTRUCTIBLES, EN CASO
					//CONTRARIO EVALUAREMOS PONER OBSTACULOS
					conjuntoEntidades.put(new Ubicacion(i, j), new Muro(i * DefConst.TILESIZE, j * DefConst.TILESIZE));
				} else if ((posicionValida(i, j)) && Math.random() >= DefConst.TOLCREAROBST) {
					//75% DE PROBABLIDAD DE CREAR UN OBSTACULO
					SvObstaculo obs =  new SvObstaculo(i * DefConst.TILESIZE, j * DefConst.TILESIZE);
					conjuntoEntidades.put(new Ubicacion(i, j), obs);
					obstaculos.add(obs);
				}
			}
		}
	}
	/// LIBERA LAS POSICIONES 11 12 21 PARA QUE EL BOMBERMAN PUEDA RESPAWNEAR EN ESAS POSICIONES
	private boolean posicionValida(int posX, int posY) {
		if (posX == 1 && posY == 1 || posX == 1 && posY == 2 || posX == 2 && posY == 1) {
			return false;
		}

		if (posX == DefConst.ANCHOMAPA - 2 && posY == 1 || posX == DefConst.ANCHOMAPA - 3 && posY == 1 || posX == DefConst.ANCHOMAPA - 2 && posY == 2) {
			return false;
		}

		if (posX == 1 && posY == DefConst.ALTOMAPA - 2 || posX == 1 && posY == DefConst.ALTOMAPA - 3 || posX == 2 && posY == DefConst.ALTOMAPA - 2) {
			return false;
		}

		if (posX == DefConst.ANCHOMAPA - 2 && posY == DefConst.ALTOMAPA - 2 || posX == DefConst.ANCHOMAPA - 3 && posY == DefConst.ALTOMAPA - 2
				|| posX == DefConst.ANCHOMAPA - 2 && posY == DefConst.ALTOMAPA - 3) {
			return false;
		}
		return true;
	}
	
	
	///////////////////////////////////////
	// 									//
	// 			ENTIDADES 				//
	// 									//
	/////////////////////////////////////

	public HashMap<Ubicacion, Entidad> getListaEntidades() {
		return conjuntoEntidades;
	}

	/**
	 * Busca todas las posibles entidades en una ubicacion.
	 * 
	 * @param ubic la ubicacion a buscar.
	 * @return
	 */

	public Entidad obtenerEntidadEn(Ubicacion ubic) {
		Entidad ent;

		ent = conjuntoEntidades.get(ubic);

		if (ent != null)
			return ent;

		ent = obtenerBombermanEn(ubic);

		if (ent != null)
			return ent;

		return null;
	}

	/**
	 * Devuelve una entidad del HashMap de entidades del mapa.
	 * 
	 * @param ubic la ubicacion en la que se encuentra la entidad (KEY).
	 * @return
	 */

	public Entidad obtenerEntidadDelConjunto(Ubicacion ubic) {
		return conjuntoEntidades.get(ubic);
	}

	/**
	 * Remueve una entidad del Treemap de entidades del mapa.
	 * 
	 * @param ubic: la ubicacion de la entidad
	 */

	public void removerEntidadDelConjunto(Ubicacion ubic) {
		conjuntoEntidades.remove(ubic);
	}
	
	public void agregarEntidadAlConjunto(Ubicacion ubic, Entidad ent) {
		if(conjuntoEntidades.containsKey(ubic)==false)
			conjuntoEntidades.put(ubic, ent);
	}
	
	/**
	 * Agrega un bomberman nuevo a la lista de bombermans del mapa.
	 * 
	 * @param b bomberman a agregar
	 */

	public void agregarBomberman(SvBomberman b) {
		this.listaBomberman.add(b);
	}

	/**
	 * Retorna la lista de bombermans del mapa.
	 * 
	 * @return List<Bomberman> listaBomberman
	 */

	public List<SvBomberman> obtenerListaBomberman() {
		return listaBomberman;
	}	

	/**
	 * Recorre la lista de bombermans y retorna al que encuentre en la ubicacion
	 * indicada.
	 * 
	 * @param ubic la ubicacion a buscar
	 * @return instanceof Bomberman si lo encuentra, null si no
	 */

	public SvBomberman obtenerBombermanEn(Ubicacion ubic) {
		for (SvBomberman bomberman : listaBomberman) {
			if (bomberman.obtenerUbicacion().equals(ubic))
				return bomberman;
		}

		return null;
	}
	
	public void moverBomberman(SvBomberman bomberman, int despX, int despY) {
	
		if (puedeMoverse(bomberman.getX()+despX, bomberman.getY()+despY, bomberman)) {
			bomberman.cambiarPosX(despX);
			bomberman.cambiarPosY(despY);	
			//Actualizamos la ubicacion relativa en la matriz
			Ubicacion ubic = new Ubicacion(bomberman.getX()/DefConst.TILESIZE, bomberman.getY()/DefConst.TILESIZE);
			bomberman.cambiarUbicacion(ubic);	
			//Actualizamos el flag "ignorarColisionCreador" de las bombas del bomberman
			bomberman.actualizarColBomba();
		}
	}
	
	/**
	 * Chequea si el bomberman puede moverse a la posicion que recibe por parametro.
	 * 
	 * @param ubic ubicacion auxiliar que refleja la posible nueva ubicacion del
	 *              Bomberman
	 * @return true: puede moverse, false: no puede moverse
	 */
	public boolean puedeMoverse(int x, int y, SvBomberman bomberman) {
		return !chequearColisiones(bomberman, x, y);
	}
	
	/**
	 * Chequea si no existe ninguna otra entidad colisionable del juego presente en
	 * la ubicacion que llega por parametro.
	 * 
	 * @param ubic ubicacion a revisar en buscar de entidades
	 * @return true: no hay ninguna entidad presente, false: hay una entidad en
	 *         dicha posicion
	 */
	
	public boolean hayEntidadEn(Ubicacion ubic) {
		return conjuntoEntidades.get(ubic) == null;
	}
	
	/** Chequea si una entidad está colisionando con alguna del conjunto de entidades
	 * 
	 * @param ent la entidad con la que se va a chequear si hay colision
	 * @return true: hay colision, false: no hay
	 */
	
	public boolean chequearColisiones(SvBomberman bomberman, int x, int y) {
		//Como puede haber mas de una colision al mismo tiempo, usamos una variable booleana
		//en vez de retornar el valor individual de una colision
		boolean col = false;	
		
		//Recibo el X, Y del bomberman con un desplazamiento...
		//Ubicacion del desplazamiento
		Ubicacion ubicDesp = new Ubicacion(x/DefConst.TILESIZE, y/DefConst.TILESIZE);
		
		/* Solo tengo que fijarme si en los casilleros aledaños hay una colisión con el Bomberman
		* El bomberman siempre va a tener 9 casilleros aledaños:
		* 	Tres arriba suyo		
		*	Tres en la misma fila
		* 	Y tres abajo suyo
		*/
		
		for (int i = 0; i < 3; i++) {
			//Entidad que se encuentra en la fila superior a la del bomberman
			Entidad entidadSup = conjuntoEntidades.get(new Ubicacion((ubicDesp.getPosX()-1)+i, ubicDesp.getPosY()-1));		
			//Entidad que se encuentra en la misma fila que la del bomberman
			Entidad entidadSame = conjuntoEntidades.get(new Ubicacion((ubicDesp.getPosX()-1)+i, ubicDesp.getPosY()));
			//Entidad que se encuentra en la fila inferior a la del bomberman
			Entidad entidadInf = conjuntoEntidades.get(new Ubicacion((ubicDesp.getPosX()-1)+i, ubicDesp.getPosY()+1));
			
			//Si existe colision con alguna de las tres...
			if (hayColision(bomberman, x, y, entidadSup) || hayColision(bomberman, x, y, entidadSame) 
					|| hayColision(bomberman, x, y, entidadInf)) {
				col = true;
			}			
		}		
		
		return col;
	}
	
	/** Chequea que haya una colisión, y en caso de haberlo, maneja las mismas. 
	 * 
	 * @param bman
	 * @param x
	 * @param y
	 * @param ent
	 * @return
	 */
	
	public boolean hayColision(SvBomberman bman, int x, int y, Entidad ent)  {
		if (ent != null) {
			//Si existe una colision de hitboxes
			if (interseccionHitBox(bman, x, y, ent)) {
				//Manejamos la colision con la entidad
				if (bman.manejarColisionCon(ent)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	/** Chequea si existe una intereseccion entre los hitboxes de un bomberman y una entidad cualquiera.
	 *  
	 * 
	 * @param bman el bomberman
	 * @param x posicion X del bomberman, puede ser la posicion de desplazamiento o no
	 * @param y posicion Y del bomberman, puede ser la posicion de desplazamiento o no
	 * @param ent la entidad
	 * @return true: hay interseccion de hitbox, false: no hay
	 */
	
	public boolean interseccionHitBox(SvBomberman bman, int x, int y, Entidad ent) {
		//Hitbox del bomberman
		Rectangle hitBoxBman = new Rectangle(x, y, bman.getWidth(), bman.getHeight());
		//Hitbox de la entidad
		Rectangle hitBoxEnt = ent.getHitBox();
		//Vemos si existe una interseccion entre ambos rectangulos
		Rectangle intersection = hitBoxBman.intersection(hitBoxEnt);
		//Si la interseccion no es vacia, entonces retornamos que hay colision
		if (!intersection.isEmpty()) {
			return true;
		}
		
		return false;		
	}
	
	public void moverBombermanArriba(SvBomberman bomberman, int desplazamiento) {
		this.moverBomberman(bomberman, 0, -Math.abs(desplazamiento));
		
	}

	public void moverBombermanAbajo(SvBomberman bomberman, int desplazamiento) {		
		this.moverBomberman(bomberman, 0, Math.abs(desplazamiento));
	}

	public void moverBombermanIzq(SvBomberman bomberman, int desplazamiento) {		
		this.moverBomberman(bomberman, -Math.abs(desplazamiento), 0);
		
	}

	public void moverBombermanDer(SvBomberman bomberman, int desplazamiento) {
		this.moverBomberman(bomberman, Math.abs(desplazamiento), 0);
	}
	
	public SvBomberman getBombermanById(int id) {
		return listaBomberman.get(id);		
	}
	
	public void eliminarBomberman(SvBomberman bomb) {
		listaBomberman.remove(bomb);
	}
	
	public ArrayList<SvObstaculo> getObstaculos() {
		return this.obstaculos;
	}

}
