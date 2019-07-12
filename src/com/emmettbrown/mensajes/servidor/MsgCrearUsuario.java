package com.emmettbrown.mensajes.servidor;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.emmettbrown.base.datos.base.GestionBD;
import com.emmettbrown.base.datos.base.Usuario;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgResCrearUsuario;
import com.emmettbrown.servidor.HiloCliente;

public class MsgCrearUsuario extends Msg {

	private String usuario;
	private String clave;

	public MsgCrearUsuario(String text, String string) {
		this.usuario = text;
		this.clave = string;
	}

	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo  = (HiloCliente)obj;
		GestionBD g = hilo.getGestionBD();
		
		Session s = g.getFactory().openSession();
		Transaction t = s.beginTransaction();
		
		Usuario userExistente = s.get(Usuario.class,this.usuario);
		boolean resultado = true;
		if (userExistente != null) {
			//El usuario existe. no puede crearse de nuevo
			resultado = false;
		}else {
			s.save(new Usuario(this.usuario,this.clave,0));
		}
		hilo.enviarMsg(new MsgResCrearUsuario(resultado));
		t.commit();
		s.close();
		
		return null;
	}

}
