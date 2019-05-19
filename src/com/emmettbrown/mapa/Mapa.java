package com.emmettbrown.mapa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.emmettbrown.entidades.Bomba;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entidades.Entidad;
import com.emmettbrown.entidades.Muro;
import com.emmettbrown.entidades.Obstaculo;
import com.emmettbrown.principal.Motor;
import com.sun.javafx.geom.Rectangle;

public class Mapa {
	public static final int ANCHO = 9;
	public static final int ALTO = 9;
	private Map<Ubicacion, Entidad> conjuntoEntidades;
	private List<Bomberman> listaBomberman;

	///////////////////////////////////////
	// 									//
	// 			CONSTUCTORES		   //
	// 								  //
	///////////////////////////////////

	public Mapa() {
		conjuntoEntidades = new TreeMap<Ubicacion, Entidad>();
		listaBomberman = new ArrayList<Bomberman>();
	}

	////////////////////////////////////////
	// 									 //
	// 				METODOS 			//
	// 								   //
	////////////////////////////////////

	public void generarMapa() {
		for (int i = 0; i < ANCHO; i++) {
			for (int j = 0; j < ALTO; j++) {
				if (i % 2 != 0 && j % 2 != 0) { 
					//EN LAS POSICIONES I,J IMPARES PONDREMOS INDESTRUCTIBLES, EN CASO
					//CONTRARIO EVALUAREMOS PONER OBSTACULOS
					conjuntoEntidades.put(new Ubicacion(i, j), new Muro(i * Motor.tileSize, j * Motor.tileSize));
				} else if ((posicionValida(i, j)) && Math.random() >= 0.25) {
					//75% DE PROBABILIDAD DE CREAR UN	OBSTACULO
					conjuntoEntidades.put(new Ubicacion(i, j), new Obstaculo(i * Motor.tileSize, j * Motor.tileSize));
				}
			}
		}
	}

	private boolean posicionValida(int posX, int posY) {
		if (posX == 0 && posY == 0 || posX == 0 && posY == 1 || posX == 1 && posY == 0) {
			return false;
		}

		if (posX == ANCHO - 1 && posY == 0 || posX == ANCHO - 2 && posY == 0 || posX == ANCHO - 1 && posY == 1) {
			return false;
		}

		if (posX == 0 && posY == ALTO - 1 || posX == 0 && posY == ALTO - 2 || posX == 1 && posY == ALTO - 1) {
			return false;
		}

		if (posX == ANCHO - 1 && posY == ALTO - 1 || posX == ANCHO - 2 && posY == ALTO - 1
				|| posX == ANCHO - 1 && posY == ALTO - 2) {
			return false;
		}
		return true;
	}


	///////////////////////////////////////
	// 									//
	// 			ENTIDADES 				//
	// 									//
	/////////////////////////////////////

	public Map<Ubicacion, Entidad> obtenerListaEntidades() {
		return conjuntoEntidades;
	}

	/**
	 * Busca todas las posibles entidades en una ubicacion.
	 * 
	 * @param ubic: la ubicacion a buscar.
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
	 * Devuelve una entidad del Treemap de entidades del mapa.
	 * 
	 * @param ubic: la ubicacion en la que se encuentra la entidad (KEY).
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

	///////////////////////////////////////
	// 									//
	// 			BOMBERMANS			   //
	// 								  //
	///////////////////////////////////

	/**
	 * Realiza una serie de chequeos y en caso de validar correctamente, mueve el
	 * bomberman a una nueva ubicacion en el mapa.
	 * 
	 * @param bomberman: el bomberman a mover
	 * @param despX:     el desplazamiento en el eje X que realizara el bomberman
	 * @param despY:     el desplazamiento en el eje Y que realizara el bomberman
	 */

	private void moverBomberman(Bomberman bomberman, int despX, int despY) {
		/**/
		
		//Ubicacion ubic = new Ubicacion(bomberman.getX()+despX, bomberman.getY()+despY);

		if (puedeMoverse(bomberman.getX()+despX, bomberman.getY()+despY, bomberman)) {
			Ubicacion ubic = bomberman.obtenerUbicacion().clone();
			ubic.cambiarPosX(despX);
			ubic.cambiarPosY(despY);
			
			bomberman.cambiarPosX(despX);
			bomberman.cambiarPosY(despY);		
			bomberman.cambiarUbicacion(ubic);
			System.out.println(bomberman.getX());
		}
	}

	/**
	 * Chequea si el bomberman puede moverse a la posicion que recibe por parametro.
	 * 
	 * @param ubic: ubicacion auxiliar que refleja la posible nueva ubicacion del
	 *              Bomberman
	 * @return true: puede moverse, false: no puede moverse
	 */
	public boolean puedeMoverse(int x, int y, Entidad ent) {
		if (x < 0 || x >= Motor.ANCHO)
			return false;
		if (y < 0 || y >= Motor.ALTO)
			return false;
		
		return !chequearColisiones(ent, x, y); //estaLibre(ubic) && 
	}

	/**
	 * Chequea si no existe ninguna otra entidad colisionable del juego presente en
	 * la ubicacion que llega por parametro.
	 * 
	 * @param ubic: ubicacion a revisar en buscar de entidades
	 * @return true: no hay ninguna entidad presente, false: hay una entidad en
	 *         dicha posicion
	 */

	public boolean estaLibre(Ubicacion ubic) {
		return conjuntoEntidades.get(ubic) == null;
	}
	
	/** Chequea si una entidad est· colisionando con alguna del conjunto de entidades
	 * 
	 * @param ent: la entidad con la que se va a chequear si hay colision
	 * @return true: hay colision, false: no hay
	 */
	
	public boolean chequearColisiones(Entidad ent, int x, int y) {
		//Hitbox de la primera entidad
		Rectangle hitBoxEnt = new Rectangle(x, y, ent.getWidth(), ent.getHeight());
		
		//Recorremos el conjunto de entidades
		for(Map.Entry<Ubicacion, Entidad> entry : conjuntoEntidades.entrySet()) {
			//Agarramos cada entry
			Entidad obj = entry.getValue();
			//Y calculamos su rectangulo
			Rectangle hitBoxObj = obj.getHitBox();
			//Vemos si existe una interseccion entre ambos rectangulos
			Rectangle intersection = hitBoxEnt.intersection(hitBoxObj);		  
			
			//Si la interseccion no es vacia, entonces retornamos que hay colision
			if (!intersection.isEmpty()) {
			  return true;	
		  }
		}
		//No se dectectaron colisiones en ninguna entidad, retornamos false
		return false;
	}

	/**
	 * Reciben como parametros el bomberman a mover, y el desplazamiento sin NINGUN
	 * tipo de signo.
	 */

	public void moverBombermanArriba(Bomberman bomberman, int desplazamiento) {
		// Fool proof
		this.moverBomberman(bomberman, 0, -Math.abs(desplazamiento));
	}

	public void moverBombermanAbajo(Bomberman bomberman, int desplazamiento) {
		// Fool proof
		this.moverBomberman(bomberman, 0, Math.abs(desplazamiento));
	}

	public void moverBombermanIzq(Bomberman bomberman, int desplazamiento) {
		// Fool proof
		this.moverBomberman(bomberman, -Math.abs(desplazamiento), 0);
	}

	public void moverBombermanDer(Bomberman bomberman, int desplazamiento) {
		// Fool proof
		this.moverBomberman(bomberman, Math.abs(desplazamiento), 0);
	}

	/**
	 * Agrega un bomberman nuevo a la lista de bombermans del mapa.
	 * 
	 * @param b: bomberman a agregar
	 */

	public void agregarBomberman(Bomberman b) {
		this.listaBomberman.add(b);
	}

	/**
	 * Retorna la lista de bombermans del mapa.
	 * 
	 * @return List<Bomberman> listaBomberman
	 */

	public List<Bomberman> obtenerListaBomberman() {
		return listaBomberman;
	}

	/**
	 * Recorre la lista de bombermans y retorna al que encuentre en la ubicacion
	 * indicada.
	 * 
	 * @param ubic: la ubicacion a buscar
	 * @return instanceof Bomberman si lo encuentra, null si no
	 */

	public Bomberman obtenerBombermanEn(Ubicacion ubic) {
		for (Bomberman bomberman : listaBomberman) {
			if (bomberman.obtenerUbicacion().equals(ubic))
				return bomberman;
		}

		return null;
	}

	///////////////////////////////////////
	// 									//
	// 				BOMBAS 				//
	// 									//
	/////////////////////////////////////

	/**
	 * Agregar una bomba en el conjunto de entidades de la clase Mapa
	 * 
	 * @param ubicacion: la ubicacion en la que se encuentra la bomba
	 */

	public void agregarBomba(Bomba bomb) {
		conjuntoEntidades.put(bomb.obtenerUbicacion().clone(), bomb);
	}
	
	public void agregarBomba(Ubicacion ubicacion) {
		Ubicacion copia = ubicacion.clone();
		conjuntoEntidades.put(copia, new Bomba(copia));
	}

	/**
	 * Explota una bomba a traves de sus coordenadas
	 * 
	 * @param posX: coord. eje X
	 * @param posY: coord. eje Y
	 */

	public void explotarBomba(int posX, int posY) {
		Bomba b = ((Bomba) conjuntoEntidades.get(new Ubicacion(posX, posY)));

		if (b != null)
			b.explotar(this);
	}

	/**
	 * Explota una bomba a traves de su ubicacion
	 * 
	 * @param ubic: la ubicacion en la que se encuentra la bomba
	 */

	public void explotarBomba(Ubicacion ubic) {
		Bomba b = ((Bomba) conjuntoEntidades.get(ubic));

		if (b != null)
			b.explotar(this);
	}

	/**
	 * Explota una bomba a trav√©s de su instancia
	 * 
	 * @param bomba: instancia de la bomba a explotar
	 */
	public void explotarBomba(Bomba bomba) {
		bomba.explotar(this);
	}
}
