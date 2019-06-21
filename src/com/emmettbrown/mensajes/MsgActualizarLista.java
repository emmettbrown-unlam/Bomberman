package com.emmettbrown.mensajes;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.emmettbrown.servidor.HiloCliente;

public class MsgActualizarLista extends Msg {

	private DefaultListModel d;

	public  MsgActualizarLista(DefaultListModel d) {
		this.d = d;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		ArrayList<String> usuariosConectados = hilo.getUsuarios();
		for (String string : usuariosConectados) {
			d.addElement(string);
		}
		return d;
//		ArrayList<Socket> usuariosConectados = hilo.getUsuariosConectados();
//		for (Socket clientSocket : usuariosConectados) {
//			
//			d.addElement();
//			try {
//				ObjectOutputStream salidaACliente = new ObjectOutputStream(clientSocket.getOutputStream());
//				salidaACliente.writeObject(m);
//			} catch (IOException e) {
//				System.out.println(e);
//			}
//		}
//		
//		
//		return null;
		
	}

}
