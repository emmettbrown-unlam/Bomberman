package com.emmettbrown.mensajes;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entorno.grafico.Sala;

public class MsgActualizarListaSalas extends Msg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Sala sala;
	
	public MsgActualizarListaSalas(Sala s) {
		this.sala = s;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		Cliente c = (Cliente) obj;
		c.getListaSalas().add(sala);
		System.out.println("sdasdasd + sdasdasd "+c.getListaSalas().size());
		
		return null;
	}

}
