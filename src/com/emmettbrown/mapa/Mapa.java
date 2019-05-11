package com.emmettbrown.mapa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.emmettbrown.entidades.Bomba;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entidades.Entidad;
import com.emmettbrown.entidades.Muro;
import com.emmettbrown.entidades.Obstaculo;

public class Mapa {
	public static final int ANCHO = 9;
	public static final int ALTO = 9;
	private Map<Ubicacion, Entidad> conjuntoEntidades;
	private List<Bomberman> listaBomberman;

	
	/////////////////////////////////////// 
	//								    //
	//		    CONSTUCTORES		   //
	//								  //
	///////////////////////////////////	
	
	public Mapa() 
	{
		conjuntoEntidades = new TreeMap<Ubicacion, Entidad>();
		listaBomberman = new ArrayList<Bomberman>();
	}	
	
	/////////////////////////////////////// 
	//								    //
	//				METODOS			   //
	//								  //
	///////////////////////////////////	
	
	public void generarMapa() {
		for (int i = 0; i < ANCHO; i++) {
			for (int j = 0; j < ALTO; j++) {
				if (i % 2 != 0 && j % 2 != 0) { /// EN LAS POSICIONES I,J IMPARES PONDREMOS INDESTRUCTIBLES, EN CASO
												/// CONTRARIO EVALUAREMOS PONER OBSTACULOS
					conjuntoEntidades.put(new Ubicacion(i, j), new Muro(i, j));
				} else if ((posicionValida(i, j)) && Math.random() >= 0.25) {///75% DE PROBABILIDAD DE CREAR UN OBSTACULO
					conjuntoEntidades.put(new Ubicacion(i, j), new Obstaculo(i, j));
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
	
	public void mostrarMapa() {
		Ubicacion ver;
		Iterator<Ubicacion> it = conjuntoEntidades.keySet().iterator();
		double calculo;
		int i = 1;
		while (it.hasNext()) {
			ver = it.next();
			calculo = ver.getPosX() * ANCHO + ver.getPosY() + 1;
			for (; i < calculo; i++) {
				System.out.printf("NULO\t");
				if (i != 0 && i % ANCHO == 0)
					System.out.println();
			}
			System.out.printf("%s\t", conjuntoEntidades.get(ver).getClass().getSimpleName().substring(0, 4));
			if (i != 0 && i % ANCHO == 0)
				System.out.println();
			i++;
		}
		i--;
		for (; i < (ANCHO * ALTO); i++) {
			if (i != 0 && i % ANCHO == 0)
				System.out.println();
			System.out.printf("NULO\t");
		}
		System.out.println();
	}
	
	/////////////////////////////////////// 
	//								    //
	//			  ENTIDADES 		   //
	//								  //
	///////////////////////////////////
	
	
	public Map<Ubicacion, Entidad> obtenerListaEntidades() {
		return conjuntoEntidades;
	}
	
	/** Busca todas las posibles entidades en una unbicaci�n. 
	 * 
	 * @param ubic: la ubicaci�n a buscar.
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
	
	public Entidad obtenerEntidadDelConjunto(Ubicacion ubic) {
		return conjuntoEntidades.get(ubic);
	}
	
	/** Remueve una entidad del Treemap de entidades.
	 * 
	 * @param ubic: la ubicaci�n de la entidad
	 */
	
	public void removerEntidadDelConjunto(Ubicacion ubic) {
		conjuntoEntidades.remove(ubic);
	}

	/////////////////////////////////////// 
	//								    //
	//			  BOMBERMANS		   //
	//								  //
	///////////////////////////////////
	
	public void moverBomberman(Bomberman bomberman, double despX, double despY) {
		Ubicacion ubic = bomberman.obtenerUbicacion().clone();
		ubic.cambiarPosX(despX);
		ubic.cambiarPosY(despY);
		
		if (puedeMoverse(ubic)) {
			bomberman.obtenerUbicacion().cambiarPosX(despX);
			bomberman.obtenerUbicacion().cambiarPosY(despY);
		}			
	}
	
	/** Chequea si el bomberman puede moverse a la posici�n que recibe por par�metro.
	 * 
	 * @param ubic: ubicaci�n auxiliar que refleja la posible nueva ubicaci� del Bomberman
	 * @return true: puede moverse, false: no puede moverse
	 */
	public boolean puedeMoverse(Ubicacion ubic) {
		if (ubic.getPosX() < 0 || ubic.getPosX() >= Mapa.ANCHO) 
			return false;
		if (ubic.getPosY() < 0 || ubic.getPosY() >= Mapa.ALTO)
			return false;
		
		return estaLibre(ubic);		
	}
	
	/** Chequea si no existe ninguna otra entidad colisionable del juego presente 
	 * en la ubicaci�n que llega por par�metro.
	 * 
	 * @param ubic: ubicaci�n a revisar en buscar de entidades
	 * @return true: no hay ninguna entidad presente, false: hay una entidad en dicha posici�n
	 */
	
	public boolean estaLibre(Ubicacion ubic) {
		if (conjuntoEntidades.get(ubic) == null) {
			return true;
		}
		return false;
	}
	
	/** Reciben como par�metros el bomberman a mover, y el desplazamiento sin NINGUN tipo de signo. */
	
	public void moverBombermanArriba(Bomberman bomberman, double desplazamiento) {
		//Fool proof
		desplazamiento = Math.abs(desplazamiento);
		this.moverBomberman(bomberman, 0, -desplazamiento);
	}
	
	public void moverBombermanAbajo(Bomberman bomberman, double desplazamiento) {
		//Fool proof
		desplazamiento = Math.abs(desplazamiento);
		this.moverBomberman(bomberman, 0, desplazamiento);
	}
	
	public void moverBombermanIzq(Bomberman bomberman, double desplazamiento) {
		//Fool proof
		desplazamiento = Math.abs(desplazamiento);
		this.moverBomberman(bomberman, -desplazamiento, 0);
	}
	
	public void moverBombermanDer(Bomberman bomberman, double desplazamiento) {
		//Fool proof
		desplazamiento = Math.abs(desplazamiento);
		this.moverBomberman(bomberman, desplazamiento, 0);
	}	
	
	
	public void agregarBomberman(Bomberman b) {
		this.listaBomberman.add(b);
	}	
	
	public List<Bomberman> obtenerListaBomberman() {
		return listaBomberman;
	}
	
	public Bomberman obtenerBombermanEn(Ubicacion ubic) {
		for (Bomberman bomberman : listaBomberman) {
			if (bomberman.obtenerUbicacion().equals(ubic))
				return bomberman;
		}
		
		return null;
	}
	
	/////////////////////////////////////// 
	//								    //
	//				BOMBAS			   //
	//								  //
	///////////////////////////////////	

	public void agregarBomba(Ubicacion ubicacion) {
		Ubicacion copia = ubicacion.clone();
		conjuntoEntidades.put(copia, new Bomba(copia));
	}
		
	public void explotarBomba(int posX, int posY) {
		Bomba b = ((Bomba) conjuntoEntidades.get(new Ubicacion(posX, posY)));
		
		if (b != null)	
			b.explotar(this);
	}
	
	public void explotarBomba(Ubicacion u) {
		Bomba b = ((Bomba) conjuntoEntidades.get(u));
		
		if (b != null)	
			b.explotar(this);
	}	
	
	public void explotarBomba(Bomba bomba) {
		bomba.explotar(this);
	}
}
