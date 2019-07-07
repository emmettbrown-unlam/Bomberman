package com.emmettbrown.mensajes.servidor;

import java.util.List;
import com.emmettbrown.mapa.Ubicacion;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgReset;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.entidades.SvBomberman;
import com.emmettbrown.servidor.mapa.ServerMap;

public class MsgReestablecerMotor extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int cliente;

	
	public MsgReestablecerMotor(int idc) {
		this.cliente = idc;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		if (cliente == hilo.getSalaConectada().getIdCreador()) {
			ServerMap mapa = new ServerMap();
			mapa.generarMapa();
			//HashMap<Ubicacion, SvEntidad> ent = mapa.getListaEntidades();
			hilo.getMap().setObstaculos(mapa.getObstaculos());
			Ubicacion ubicBomber = null;
			List<SvBomberman> bomber = hilo.getMap().obtenerListaBomberman();
			for (SvBomberman bomberman : bomber) {
				Ubicacion ubic = mapa.obtenerUbicacionInicio();
				if (!bomberman.estaVivo()) {
					bomberman.cambiarVisibilidad();
				}
				bomberman.setUbicacion(ubic);
				if (bomberman.getIdBomberman() == hilo.getBomber().getIdBomberman()) {
					ubicBomber = new Ubicacion(ubic.getPosX(), ubic.getPosY());
				}
				System.out.println("ubcacion x bomber: "+bomberman.getX()+","+bomberman.getY());
			}
			if (!hilo.getBomber().estaVivo()) {
				hilo.getBomber().cambiarVisibilidad();
			}
			
			hilo.getBomber().setUbicacion(ubicBomber);
			
			hilo.broadcast(new MsgReset(hilo.getMap().getObstaculos(),bomber,hilo.getSalaConectada().obtenerTablero().getPuntuacion()), hilo.getSalaConectada().getOutputStreams());
			hilo.getSalaConectada().reiniciarReloj();
		}
		return null;
	}

}
