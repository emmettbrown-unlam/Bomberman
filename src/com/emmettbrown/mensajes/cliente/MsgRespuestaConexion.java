package com.emmettbrown.mensajes.cliente;

import com.emmettbrown.entorno.grafico.Login;
import com.emmettbrown.mensajes.Msg;

public class MsgRespuestaConexion extends Msg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean respuestaConexion;
	
	public MsgRespuestaConexion(boolean respuestaConex)
	{
		this.respuestaConexion = respuestaConex;
	}
	@Override
	public Object realizarAccion(Object obj) {
		Login cliente = (Login) obj;
		System.out.println("respuesta Conex");
		if (respuestaConexion == true) {
			
			System.out.println("Resp si");
			cliente.setRespuestaRecibida(1);
		}	
		else
		{
			System.out.println("Resp no");
			cliente.setRespuestaRecibida(2);
			
		}
			
		return this;
	}

}
