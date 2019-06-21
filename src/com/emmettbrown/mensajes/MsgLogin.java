package com.emmettbrown.mensajes;

import java.io.ObjectOutputStream;

import com.emmettbrown.servidor.HiloCliente;


public class MsgLogin extends Msg {

	private static final long serialVersionUID = 1L;
	private String usuario;
	private String clave;

	public MsgLogin(String usuario, String contrasena) {
		this.usuario = usuario;
		this.clave = contrasena;
	}

	@Override
	public String realizarAccion(Object obj) {
		HiloCliente hilo =(HiloCliente) obj;
		String res = "FAIL";

//			ObjectOutputStream salidaACliente = new ObjectOutputStream(hilo.getClientSocket().getOutputStream()); 
			
			if ((this.usuario.equals("Usuario") && this.clave.equals("clave"))
					|| (this.usuario.equals("Usuario2") && this.clave.equals("clave2"))) {
				
//				hilo.getUsuariosConectados().add(hilo.getClientSocket());			
				//Le mandamos un OK al cliente
//				salidaACliente.writeObject("OK");
				hilo.getUsuarios().add(this.usuario);
				res = "OK";
			}
			else {
				//Le mandamos un FAIL al cliente
//				salidaACliente.writeObject("FAIL");
			}

		return res;
	}
}