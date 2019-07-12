package com.emmettbrown.mensajes.servidor;

import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.servidor.HiloCliente;

public class MsgDesconectarDeServidor extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		hilo.desconectarDeServidor();
		return null;
	}

}
