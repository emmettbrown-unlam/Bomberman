package com.emmettbrown.mensajes.cliente;

import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.entorno.grafico.Login;
import com.emmettbrown.mensajes.Msg;

public class MsgPudoCrearUsuario extends Msg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean verificar;
	
	public MsgPudoCrearUsuario(boolean verificar) {
		this.verificar = verificar;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		Login login = (Login)obj;
		System.out.println("VerificarCrearUsuario");
		if(verificar == DefConst.DUPLICADO)
			login.setRespuestaCrearUsuario(DefConst.DUPLICADO);
		else
			login.setRespuestaCrearUsuario(DefConst.EXITO);
		return null;
	}

}
