package com.emmettbrown.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.mensajes.Msg;

public class HiloCliente extends Thread {

	private Socket clientSocket;
	private boolean estaConectado;
	private ArrayList<Socket> usuariosConectados;
	
	public HiloCliente(Socket cliente, ArrayList<Socket> usuariosConectados) {
		this.clientSocket = cliente;
		this.usuariosConectados = usuariosConectados;
		this.estaConectado = true;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public boolean isEstaConectado() {
		return estaConectado;
	}

	public void setEstaConectado(boolean estaConectado) {
		this.estaConectado = estaConectado;
	}

	public ArrayList<Socket> getUsuariosConectados() {
		return usuariosConectados;
	}

	public void setUsuariosConectados(ArrayList<Socket> usuariosConectados) {
		this.usuariosConectados = usuariosConectados;
	}


	@Override
	public void run() {

		try {
			ObjectInputStream reciboMsg = new ObjectInputStream(clientSocket.getInputStream());;

			while (estaConectado) {
				/* Recibo Consulta de cliente */
				Msg msgRecibo = (Msg) reciboMsg.readObject();
				msgRecibo.realizarAccion(this);
				
				reciboMsg = new ObjectInputStream(clientSocket.getInputStream());
			}

			reciboMsg.close();
			//salidaACliente.close();
			clientSocket.close();
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problemas al querer leer otra petición: " + ex.getMessage());
			this.usuariosConectados.remove(clientSocket);
			this.estaConectado = false;
		}
	}	
}
