package com.emmettbrown.mapa;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import com.sun.javafx.geom.Rectangle;

import com.emmettbrown.entidades.Bomba;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entidades.DefConst;
import com.emmettbrown.entidades.Entidad;
import com.emmettbrown.entidades.Muro;
import com.emmettbrown.entidades.Obstaculo;

public class Mapa{
	
	private TreeMap<Ubicacion, Entidad> conjuntoEntidades;
	private List<Bomberman> listaBomberman;
	private ImageIcon fondo;	
	

	///////////////////////////////////////
	// 									//
	// 			CONSTUCTORES		   //
	// 								  //
	///////////////////////////////////

	public Mapa() {
		conjuntoEntidades = new TreeMap<Ubicacion, Entidad>();
		listaBomberman = new ArrayList<Bomberman>();
		this.fondo = new ImageIcon("./src/resources/game-map/fondo.jpg");
	}

	////////////////////////////////////////
	// 									 //
	// 				METODOS 			//
	// 								   //
	////////////////////////////////////

	public void generarMapa() {
			for(int l = 0;l<DefConst.ALTOMAPA;l++) {
				conjuntoEntidades.put(new Ubicacion(0, l), new Muro(0 * DefConst.TILESIZE, l * DefConst.TILESIZE));
				conjuntoEntidades.put(new Ubicacion(l, 0), new Muro(l * DefConst.TILESIZE, 0 * DefConst.TILESIZE));
				conjuntoEntidades.put(new Ubicacion(DefConst.ANCHOMAPA-1, l), new Muro((DefConst.ANCHOMAPA-1) *DefConst.TILESIZE, l * DefConst.TILESIZE));
				conjuntoEntidades.put(new Ubicacion(l, DefConst.ALTOMAPA-1), new Muro(l * DefConst.TILESIZE, (DefConst.ALTOMAPA-1)* DefConst.TILESIZE));
			}
				
		for (int i = 1; i < DefConst.ANCHOMAPA-1; i++) {
			for (int j = 1; j < DefConst.ALTOMAPA-1; j++) {
				if (i % 2 == 0 && j % 2 == 0) { 
					//EN LAS POSICIONES I,J IMPARES PONDREMOS INDESTRUCTIBLES, EN CASO
					//CONTRARIO EVALUAREMOS PONER OBSTACULOS
					conjuntoEntidades.put(new Ubicacion(i, j), new Muro(i * DefConst.TILESIZE, j * DefConst.TILESIZE));
				} else if ((posicionValida(i, j)) && Math.random() >= DefConst.TOLCREAROBST) {
					//75% DE PROBABILIDAD DE CREAR UN OBSTACULO
					conjuntoEntidades.put(new Ubicacion(i, j), new Obstaculo(i * DefConst.TILESIZE, j * DefConst.TILESIZE));
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

	public Image obtenerImagen() {
		return fondo.getImage();
	}
	///////////////////////////////////////
	// 									//
	// 			ENTIDADES 				//
	// 									//
	/////////////////////////////////////

	public TreeMap<Ubicacion, Entidad> obtenerListaEntidades() {
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
	
	public void agregarEntidadAlConjunto(Ubicacion ubic, Entidad ent) {
		if(conjuntoEntidades.containsKey(ubic)==false)
			conjuntoEntidades.put(ubic, ent);
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
	 * @param ubic: ubicacion auxiliar que refleja la posible nueva ubicacion del
	 *              Bomberman
	 * @return true: puede moverse, false: no puede moverse
	 */
	public boolean puedeMoverse(int x, int y, Bomberman bomberman) {
		return !chequearColisiones(bomberman, x, y);
	}

	/**
	 * Chequea si no existe ninguna otra entidad colisionable del juego presente en
	 * la ubicacion que llega por parametro.
	 * 
	 * @param ubic: ubicacion a revisar en buscar de entidades
	 * @return true: no hay ninguna entidad presente, false: hay una entidad en
	 *         dicha posicion
	 */

	public boolean hayEntidadEn(Ubicacion ubic) {
		return conjuntoEntidades.get(ubic) == null;
	}
	
	/** Chequea si una entidad est� colisionando con alguna del conjunto de entidades
	 * 
	 * @param ent: la entidad con la que se va a chequear si hay colision
	 * @return true: hay colision, false: no hay
	 */
	
	public boolean chequearColisiones(Bomberman bomberman, int x, int y) {
		//Como puede haber mas de una colision al mismo tiempo, usamos una variable booleana
		//en vez de retornar el valor individual de una colision
		boolean col = false;		
		
		//Hitbox del bomberman
		Rectangle hitBoxBman = new Rectangle(x, y, bomberman.getWidth(), bomberman.getHeight());
		
		//Recorremos el conjunto de entidades
		for(Map.Entry<Ubicacion, Entidad> entry : conjuntoEntidades.entrySet()) {
			//Agarramos cada entry
			Entidad ent = entry.getValue();
			//Y calculamos su rectangulo
			Rectangle hitBoxEnt = ent.getHitBox();
			//Vemos si existe una interseccion entre ambos rectangulos
			Rectangle intersection = hitBoxBman.intersection(hitBoxEnt);		  			
						
			//Si la interseccion no es vacia, entonces retornamos que hay colision
			if (!intersection.isEmpty()) {
				if (bomberman.manejarColisionCon(ent)) {
					col = true; 		  
				}					
			}
		}
		//Devolvemos el valor almacenado en col
		return col;
	}

	/**
	 * Reciben como parametros el bomberman a mover, y el desplazamiento sin NINGUN
	 * tipo de signo. 
	 * 
	 * De igual forma, los valores est�n forzados a un math.abs. Est� todo fool proofeado.
	 */

	public void moverBombermanArriba(Bomberman bomberman, int desplazamiento) {
		bomberman.cambiarImagenArriba();
		this.moverBomberman(bomberman, 0, -Math.abs(desplazamiento));
		
	}

	public void moverBombermanAbajo(Bomberman bomberman, int desplazamiento) {
		bomberman.cambiarImagenAbajo();
		this.moverBomberman(bomberman, 0, Math.abs(desplazamiento));
	}

	public void moverBombermanIzq(Bomberman bomberman, int desplazamiento) {
		bomberman.cambiarImagenIzquierda();
		this.moverBomberman(bomberman, -Math.abs(desplazamiento), 0);
		
	}

	public void moverBombermanDer(Bomberman bomberman, int desplazamiento) {
		bomberman.cambiarImagenDerecha();
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
	
	/** Agrega una bomba dependiendo de la posici�n del bomberman. Realiza un c�lculo para ver en qu� casillero
	 *  es mejor ubicarla. La bomba es agregada al conjunto de entidades del mapa. Tambi�n se crea un timer
	 *  que la detona pasados algunos segundos.
	 * 
	 * @param x: posici�n X del bomberman
	 * @param y: posici�n Y del bomberman
	 */
	
	public void agregarBomba(int x, int y, Bomberman creador) {		
//		if (System.currentTimeMillis() - nextBomb > bombDelay) {			
			Ubicacion ubic = new Ubicacion(x/DefConst.TILESIZE, y/DefConst.TILESIZE);
			
			//Si el m�dulo de la posici�n con el tama�o del tile da mayor a la mitad del tama�o,
			//movemos la posicion en un casillero para que la bomba se cree en el casillero aleda�o
			if (x % DefConst.TILESIZE > DefConst.TOLCAMBIOPOS)
				ubic.cambiarPosX(1);
			if (y % DefConst.TILESIZE > DefConst.TOLCAMBIOPOS)
				ubic.cambiarPosY(1);
			
			Bomba bomb = new Bomba(ubic, creador);
			//Agregamos una bomba a la lista de bombas del creador
			creador.agregarBomba(bomb);
			conjuntoEntidades.put(bomb.obtenerUbicacion(), bomb);
			bomb.startTimer(this);
			/*Temporizador t = new Temporizador(bomb.getMs(), bomb, this);
			t.iniciarTimer();*/
//			nextBomb = System.currentTimeMillis();
//		}
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
	 * Explota una bomba a traves de su instancia
	 * 
	 * @param bomba: instancia de la bomba a explotar
	 */
	public void explotarBomba(Bomba bomba) {
		bomba.explotar(this);
	}
}
