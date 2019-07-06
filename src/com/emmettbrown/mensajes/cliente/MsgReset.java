package com.emmettbrown.mensajes.cliente;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entidades.Entidad;
import com.emmettbrown.entidades.Obstaculo;
import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.servidor.entidades.SvBomberman;
import com.emmettbrown.servidor.entidades.SvObstaculo;

public class MsgReset extends Msg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<SvObstaculo> lista;
	private List<SvBomberman> bomber;
	private HashMap<String, Integer> puntajes;

	public MsgReset(ArrayList<SvObstaculo> arrayList, List<SvBomberman> bomber, HashMap<String, Integer> hashMap) {
		this.lista = arrayList;
		this.bomber = bomber;
		this.puntajes = hashMap;
	}

	@Override
	public Object realizarAccion(Object obj) {
		Cliente c = (Cliente) obj;
		c.getMapa().limpiarEntidades();
		if (!c.getBomber().verSiEsVisible()) {
			c.getBomber().cambiarVisibilidad();
		}
		
		Mapa m = new Mapa();
		for (SvObstaculo obs : lista) {
			c.getMapa().agregarEntidadAlConjunto(obs.obtenerUbicacion(), new Obstaculo(obs.getX(), obs.getY()));
		}
		HashMap<Ubicacion, Entidad> aux = m.getListaEntidades();
		for (Entry<Ubicacion, Entidad> entry : aux.entrySet()) {
			c.getMapa().agregarEntidadAlConjunto(entry.getKey(),entry.getValue());
		}
		c.getMapa().limpiarBombermans();
		for (SvBomberman bomberman : bomber) {
			Bomberman bomber = new Bomberman(bomberman.getX(), bomberman.getY(), DefConst.DEFAULTWIDTH, DefConst.DEFAULTHEIGHT,bomberman.getIdBomberman(),bomberman.getNombre());
			c.getMapa().agregarBomberman(bomber);
//			c.getMapa().obtenerBombermanEn(new Ubicacion(bomberman.getX(), bomberman.getY())).cambiarUbicacion(new Ubicacion(bomberman.getX(), bomberman.getY()));
		}
		
		for (Entry<String, Integer> entry : puntajes.entrySet()) {
			c.getTablero().agregarPuntuacion(entry.getKey(),entry.getValue());
		}
		
//		c.getPanelGrafico().resetearVariables(c.getMapa().getListaEntidades(),c.getMapa().obtenerListaBomberman(),c.getTablero().getPuntuacion());
//		c.getPanelGrafico().iniciarReloj();
		return null;
	}

}