package com.emmettbrown.mensajes.cliente;

import java.util.ArrayList;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.mensajes.Msg;

public class MsgActualizarListaUsuarios extends Msg {
	private ArrayList<String> usuarios;
	public MsgActualizarListaUsuarios(ArrayList<String> usuarios) {
		this.usuarios = usuarios;
	}
	@Override
	public Object realizarAccion(Object obj) {
		Cliente cliente = (Cliente) obj;
		cliente.setListaUsuariosXSala(usuarios);
		System.out.println(usuarios.get(0));
		System.out.println(usuarios.get(1));
		return null;
	}

}
