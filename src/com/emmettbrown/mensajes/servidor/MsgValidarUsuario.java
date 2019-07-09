package com.emmettbrown.mensajes.servidor;

import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgRespuestaConexion;
import com.emmettbrown.servidor.HiloCliente;

public class MsgValidarUsuario extends Msg {

	private String usuario;
	private String contrasenia;
	public MsgValidarUsuario(String usuario,String contrasenia)
	{
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}
	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		
		boolean pudoConectar;
		Msg respuestaConexion = new MsgRespuestaConexion(pudoConectar);
		hilo.enviarMsg(respuestaConexion);
		return null;
	}

}
