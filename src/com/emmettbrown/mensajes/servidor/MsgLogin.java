package com.emmettbrown.mensajes.servidor;

import java.io.ObjectOutputStream;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.emmettbrown.base.datos.base.GestionBD;
import com.emmettbrown.base.datos.base.Usuario;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgResLogin;
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
	public Object realizarAccion(Object obj) {
		HiloCliente hilo =(HiloCliente) obj;
		GestionBD g = hilo.getGestionBD();
		
		Session s = g.getFactory().openSession();
		Transaction t = s.beginTransaction();
		Usuario user = s.get(Usuario.class, this.usuario);
		t.commit();
		s.close();
		
		boolean resultado = true;
		if (user == null || !user.getContraseña().equals(clave)) {
			resultado = false;
		}
		hilo.enviarMsg(new MsgResLogin(resultado));
		
//		try { 
//			ObjectOutputStream salidaACliente = new ObjectOutputStream(hilo.getWriteSocket().getOutputStream()); 
//			
//			if ((this.usuario.equals("Usuario") && this.clave.equals("clave"))
//					|| (this.usuario.equals("Usuario2") && this.clave.equals("clave2"))) {
//
//				//hilo.getUsuariosConectados().add(hilo.getWriteSocket());			
//				//Le mandamos un OK al cliente
//				salidaACliente.writeObject("OK");				
//			}
//			else {
//				//Le mandamos un FAIL al cliente
//				salidaACliente.writeObject("FAIL");
//			}
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		return null;
	}
}