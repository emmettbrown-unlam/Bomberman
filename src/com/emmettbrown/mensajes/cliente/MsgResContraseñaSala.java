package com.emmettbrown.mensajes.cliente;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.mensajes.Msg;

public class MsgResContrase�aSala extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean puedeConectarse;
	
	public MsgResContrase�aSala(boolean puedeConectarse) {
		this.puedeConectarse = puedeConectarse;
	}

	@Override
	public Object realizarAccion(Object obj) {
		Cliente cliente = (Cliente) obj;
		
		if (puedeConectarse) {
			cliente.getVentanaInicial().crearLobbyInvitado();
		} else {
			//mostrar un cartelito, ni idea
		}
		
		
		return null;
	}

}
