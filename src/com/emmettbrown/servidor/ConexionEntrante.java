package com.emmettbrown.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.emmettbrown.base.datos.base.Usuario;
import com.emmettbrown.mensajes.Msg;

public class ConexionEntrante implements Runnable {

	private transient Socket readSocket;
	private transient Socket writeSocket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Servidor servidor;
	private boolean buscaConexion = true;

	public ConexionEntrante(Socket readSocket, Socket writeSocket, Servidor servidor) throws IOException {
		this.readSocket = readSocket;
		this.writeSocket = writeSocket;
		this.servidor = servidor;
		this.outputStream = new ObjectOutputStream(writeSocket.getOutputStream());
		this.inputStream = new ObjectInputStream(readSocket.getInputStream());
	}

	public void desconectar() {
		this.buscaConexion = false;
	}

	@Override
	public void run() {
		while(buscaConexion)
		{
			try {			
					/* Recibo Consulta de cliente */
					Msg msgRecibo = (Msg) inputStream.readObject();
					msgRecibo.realizarAccion(this);
					
			} catch (IOException | ClassNotFoundException ex) {
				this.desconectar();
			}
			
			
		}
		
		servidor.iniciarHiloCliente(readSocket, writeSocket);
		
	}

	public void enviarMsg(Msg msg) {
		try {
			outputStream.writeObject(msg);
			outputStream.reset();
		} catch (Exception e) {
			System.out.println("¡No se pudo enviar el mensaje! :)");
		}
	}

	public Servidor getServidor() {
		return servidor;
	}

}
