package com.emmettbrown.mensajes.cliente;

import java.util.ArrayList;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entorno.grafico.Sala;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.servidor.entidades.SvSala;

public class MsgListaActualizar extends Msg {

	private ArrayList<Sala> salas;
	public MsgListaActualizar(ArrayList<Sala> lsSalas) {
		this.salas = lsSalas;
	}
	@Override
	public Object realizarAccion(Object obj) {
		Cliente cliente = (Cliente)obj;
		cliente.limpiarSalas();
		for (Sala sala : salas) {
			cliente.getListaSalas().add(sala.toString());
		}
		return null;
	}
}
