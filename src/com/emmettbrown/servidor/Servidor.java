package com.emmettbrown.servidor;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.servidor.entidades.SvSala;

public class Servidor {
	//El puerto del servidor
	private int port;
	//El socket del servidor
	private ServerSocket serverSocket;
	//Lista de Sockets de los clientes conectados
	private ArrayList<Socket> usuariosConectados;
	//Listado de salas creadas en el server
	private ArrayList<SvSala> listaSalas;
	//private int nroCliente;
	public static int idSalas = 0;

	public Servidor(int puerto) {
		this.port = puerto;
		this.usuariosConectados = new ArrayList<Socket>();
		this.listaSalas = new ArrayList<SvSala>();
	}

	public static void main(String[] args) {
		Servidor sv = new Servidor(DefConst.PORT);
		sv.iniciarServidor();
	}
	
	public void iniciarServidor() {		
		try {
			serverSocket = new ServerSocket(this.port);
			// Sockets del cliente
			Socket writeSocket;
			Socket readSocket;
			while (true) {
				System.out.println("Servidor esperando clientes!");
				//Este m�todo bloquea la ejecuci�n del Thread hasta que se reciba una conexi�n entrante
				//de un cliente
				
				//Aceptamos ambos sockets
				writeSocket = serverSocket.accept();
				readSocket = serverSocket.accept();
				//A�adimos el socket del cliente a la lista de sockets
				this.usuariosConectados.add(writeSocket);				
				System.out.println("�Conexion aceptada!");
				
				//Creamos un hilo para el cliente (evitando as� el bloqueo que se genera en este mismo hilo)
				//Le env�amos como datos el socket del cliente, y los la lista de usuarios conectados
				HiloCliente hiloCliente = new HiloCliente(writeSocket, readSocket, usuariosConectados, listaSalas);
				//Iniciamos el hilo
				hiloCliente.start();

			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
		
	}

}
