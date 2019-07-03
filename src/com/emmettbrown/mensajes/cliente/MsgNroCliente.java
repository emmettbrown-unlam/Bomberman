package com.emmettbrown.mensajes.cliente;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.mensajes.Msg;

public class MsgNroCliente extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nro;
	
	public MsgNroCliente(int nro) {
		this.nro = nro;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		Cliente cliente = (Cliente)obj;		
		cliente.setNroCliente(nro);
		return null;
	}
	
	

}
