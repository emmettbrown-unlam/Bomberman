package com.emmettbrown.mensajes.cliente;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entorno.grafico.Sala;
import com.emmettbrown.mensajes.Msg;

public class MsgActualizarDatosSala extends Msg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idSala;
	private int jugConectados;
	
	public MsgActualizarDatosSala (int idSala, int jugConectados) {
		this.idSala = idSala;
		this.jugConectados = jugConectados;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		Cliente cliente = (Cliente) obj;
		
		for (Sala sala : cliente.getListaSalas()) {
			if (sala.getId() == idSala) {		
				sala.setJugConectados(jugConectados);
			}
		} 
		
		return null;
	}

	/**
	 * 
	 */
	/*private static final long serialVersionUID = 1L;
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
	}*/
}
