package com.emmettbrown.mensajes.servidor;

import java.util.ArrayList;

import com.emmettbrown.entorno.grafico.Sala;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgActualizarListaUsuarios;
import com.emmettbrown.mensajes.cliente.MsgEnviarListaUsuarios;
import com.emmettbrown.mensajes.cliente.MsgListaActualizar;
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
		ArrayList<Sala> salas1 = new ArrayList<>();
		SvSala salaSeleccionada = null;
		//ESTE FOR SIRVE PARA ACTUALIZAR NOMBRE DE SALAS DE LA VENTANA DE SALAS
		for (SvSala sala : listaSalas) {
			if (sala.getId() == idSala) {
				salaSeleccionada = sala;
				sala.agregarUsuario(hilo.getClientSocket(),"Usuario "+this.idCliente);
			}
			salas1.add(new Sala(sala.getId(), sala.getIdCreador(), sala.getNombre(), sala.getJConect(), 4));
		}
		hilo.broadcast(new MsgListaActualizar(salas1), hilo.getUsuariosConectados());
		//////////////////////////////
		hilo.broadcast(new MsgActualizarListaUsuarios(salaSeleccionada.obtenerUsuarios()),salaSeleccionada.getUsuariosConectados());
		return null;
	}

}
