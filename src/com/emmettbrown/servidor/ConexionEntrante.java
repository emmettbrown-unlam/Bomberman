package com.emmettbrown.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.emmettbrown.base.datos.base.Usuario;
import com.emmettbrown.mensajes.Msg;

public class ConexionEntrante extends Thread {

	private transient Socket readSocket;
	private transient Socket writeSocket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Servidor servidor;
	private boolean buscaConexion = true;

	public ConexionEntrante(Socket readSocket, Socket writeSocket,ObjectOutputStream output,ObjectInputStream input, Servidor servidor) throws IOException {
		this.readSocket = readSocket;
		this.writeSocket = writeSocket;
		this.servidor = servidor;
		this.outputStream = output;
		this.inputStream = input;
	}

	public void desconectar() {
		this.buscaConexion = false;
	}

	@Override
	public void run() {
		while (buscaConexion) {
			try {
				/* Recibo Consulta de cliente */
				Msg msgRecibo = (Msg) inputStream.readObject();
				msgRecibo.realizarAccion(this);

			} catch (IOException | ClassNotFoundException ex) {
				System.out.println("NO SE RECIBIO EL MENSAJE EN CONEXION ENTRANTE");
			}
		}
		servidor.iniciarHiloCliente(readSocket, writeSocket, outputStream, inputStream);
	}

	public void enviarMsg(Msg msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.reset();
		} catch (Exception e) {
			System.out.println("NO SE ENVIO EL MENSAJE EN CONEXION ENTRANTE");
		}
	}

	public Servidor getServidor() {
		return servidor;
	}

}
