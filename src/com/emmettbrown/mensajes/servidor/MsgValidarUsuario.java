package com.emmettbrown.mensajes.servidor;

import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgRespuestaConexion;
import com.emmettbrown.servidor.ConexionEntrante;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.Servidor;

public class MsgValidarUsuario extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String contrasenia;
	public MsgValidarUsuario(String usuario,String contrasenia)
	{
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}
	@Override
	public Object realizarAccion(Object obj) {
		ConexionEntrante conexion = (ConexionEntrante) obj;
		Servidor servidor = conexion.getServidor();
		boolean pudoConectar = servidor.validarUsuarioBD(usuario, contrasenia);
		Msg respuestaConexion = new MsgRespuestaConexion(pudoConectar);
		conexion.enviarMsg(respuestaConexion);
		if(pudoConectar == true)
			conexion.desconectar();
		return null;
	}

}
