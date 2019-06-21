package com.emmettbrown.mensajes;

import com.emmettbrown.controles.Movimientos;
import com.emmettbrown.servidor.HiloCliente;

public class MsgMover extends Msg{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Movimientos mov;
	
	public MsgMover (Movimientos valor) {
		this.mov = valor;		
	}

	@Override
	public Object realizarAccion(Object obj) {
		//System.out.println("Me quise mover: "+mov);
		HiloCliente hilo = (HiloCliente) obj;
		hilo.getMovementThread().setMovimiento(mov);		
		return null;
	}
	
	
}
