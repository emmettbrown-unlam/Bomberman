package com.emmettbrown.mensajes.servidor;

import java.util.HashMap;
import java.util.List;

import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgActualizarPuntajes;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.entidades.SvBomberman;

public class MsgActualizarPts extends Msg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente h = (HiloCliente) obj;
		List<SvBomberman> bombers = h.getMap().obtenerListaBomberman();
		HashMap <String,Integer>puntajes = h.getPuntajes();
		for (SvBomberman bb : bombers) {
			int puntaje = 0;
			if (bb.estaVivo()) {
				puntaje = 1;
			}
			puntajes.put(bb.getNombre(), puntaje + puntajes.get(bb.getNombre() ));
		}
		
		h.guardarPuntaje(puntajes);
		h.enviarMsg(new MsgActualizarPuntajes(puntajes));
		return null;
	}
	
	

}
