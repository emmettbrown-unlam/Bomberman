package com.emmettbrown.mensajes.cliente;

import com.emmettbrown.entorno.grafico.Login;
import com.emmettbrown.mensajes.Msg;

public class MsgRespuestaConexion extends Msg {
	private boolean respuestaConexion;
	
	public MsgRespuestaConexion(boolean respuestaConex)
	{
		this.respuestaConexion = respuestaConex;
	}
	@Override
	public Object realizarAccion(Object obj) {
		Login cliente = (Login) obj;
		if (respuestaConexion == true)
			cliente.setRespuestaRecibida(1);
		else
			cliente.setRespuestaRecibida(2);
		return null;
	}

}
