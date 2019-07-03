package com.emmettbrown.mensajes;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.servidor.HiloCliente;

public class MsgConectarseASala extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idSala;
	
	public MsgConectarseASala(int idSala) {
		this.idSala = idSala;
	}
	

	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		return null;
	}
	
	
	
}
