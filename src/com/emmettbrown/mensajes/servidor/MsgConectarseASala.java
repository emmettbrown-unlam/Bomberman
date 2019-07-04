package com.emmettbrown.mensajes.servidor;

import java.util.ArrayList;

import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgActualizarListaUsuarios;
import com.emmettbrown.mensajes.cliente.MsgActualizarDatosSala;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.entidades.SvSala;

public class MsgConectarseASala extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idSala;
	private int idCliente;
	private ArrayList<SvSala> listaSalas;

	public MsgConectarseASala(int idSala, int idCliente) {
		this.idSala = idSala;
		this.idCliente = idCliente;
	}

	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		listaSalas = hilo.getSalas();

		SvSala salaSeleccionada = null;
		//ESTE FOR SIRVE PARA ACTUALIZAR NOMBRE DE SALAS DE LA VENTANA DE SALAS
		for (SvSala sala : listaSalas) {
			if (sala.getId() == idSala) {
				salaSeleccionada = sala;
				sala.agregarUsuario(hilo.getClientSocket(),"Usuario "+this.idCliente);
			}
		}
		hilo.broadcast(new MsgActualizarDatosSala(idSala, salaSeleccionada.getUsuariosConectados().size()), hilo.getUsuariosConectados());
		
		hilo.broadcast(new MsgActualizarListaUsuarios(salaSeleccionada.getId(), salaSeleccionada.obtenerUsuarios()), salaSeleccionada.getUsuariosConectados());
		return null;
	}

}
