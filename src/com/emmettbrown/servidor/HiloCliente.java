package com.emmettbrown.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.MsgActualizarLista;

public class HiloCliente extends Thread {

	private Socket clientSocket;
	private boolean estaConectado;
	private ArrayList<Socket> usuariosConectados;
	private ArrayList<Socket> usuariosConectadosXSala;
	private ArrayList<String> usuarios;
	
	public HiloCliente(Socket cliente, ArrayList<Socket> usuariosConectados, ArrayList<String> usuarios) {
		this.clientSocket = cliente;
		this.usuariosConectados = usuariosConectados;
		this.estaConectado = true;
		this.usuarios = usuarios;
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
			ObjectInputStream reciboMsg = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream salidaACliente = new ObjectOutputStream(clientSocket.getOutputStream());
			while (estaConectado) {
				/* Recibo Consulta de cliente */
				Msg msgRecibo = (Msg) reciboMsg.readObject();
				if (msgRecibo instanceof MsgActualizarLista) {
					System.out.println("Paso!");
				}
				Object obj = msgRecibo.realizarAccion(this);			
				/*Envio respuesta al Cliente */
				salidaACliente.writeObject(obj);
//					reciboMsg = new ObjectInputStream(clientSocket.getInputStream());	

			}

			reciboMsg.close();
			salidaACliente.close();
			clientSocket.close();
		} catch (IOException | ClassNotFoundException ex) {
			System.out.println("Problemas al querer leer otra petición: " + ex.getMessage());
			this.usuariosConectados.remove(clientSocket);
			this.estaConectado = false;
		}
	}

	public ArrayList<String> getUsuarios() {
		return this.usuarios;
	}	
}
