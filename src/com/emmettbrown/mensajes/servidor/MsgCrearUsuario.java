package com.emmettbrown.mensajes.servidor;

import com.emmettbrown.base.datos.base.Usuario;
import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgPudoCrearUsuario;
import com.emmettbrown.servidor.ConexionEntrante;

public class MsgCrearUsuario extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String contrasenia;
	
	public MsgCrearUsuario(String usuario, String contrasenia) {
		this.usuario = usuario;
		this.contrasenia = contrasenia;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		boolean verificar;
		ConexionEntrante conexion = (ConexionEntrante) obj;
		verificar = conexion.getServidor().crearUsuarioValido(usuario, contrasenia);
		conexion.enviarMsg(new MsgPudoCrearUsuario(verificar));
		return null;
	}

}
