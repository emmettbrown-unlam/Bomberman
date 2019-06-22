package com.emmettbrown.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.entidades.DefConst;
import com.emmettbrown.servidor.mapa.ServerMap;

public class Servidor {
	//El puerto del servidor
	private int port;
	//El socket del servidor
	private ServerSocket serverSocket;
	//Lista de Sockets de los clientes conectados
	private ArrayList<Socket> usuariosConectados;
	private ServerMap map;
	private int nroCliente;

	public Servidor(int puerto) {
		this.port = puerto;
		this.usuariosConectados = new ArrayList<Socket>();	
		this.map = new ServerMap();
		this.map.generarMapa();
		this.nroCliente = 0;
	}

	public static void main(String[] args) {
		Servidor sv = new Servidor(DefConst.PORT);
		sv.iniciarServidor();
	}
	
	public void iniciarServidor() {		
		try {
			serverSocket = new ServerSocket(this.port);
			// Socket del cliente
			Socket clientSocket;
			while (true) {
				System.out.println("Servidor esperando clientes!");
				//Este m�todo bloquea la ejecuci�n del Thread hasta que se reciba una conexi�n entrante
				//de un cliente
				clientSocket = serverSocket.accept();
				//A�adimos el socket del cliente a la lista de sockets
				this.usuariosConectados.add(clientSocket);				
				System.out.println("�Conexion aceptada!");
				
				//Creamos un hilo para el cliente (evitando as� el bloqueo que se genera en este mismo hilo)
				//Le env�amos como datos el socket del cliente, y los la lista de usuarios conectados
				HiloCliente hiloCliente = new HiloCliente(clientSocket, usuariosConectados, map, nroCliente++);
				//Iniciamos el hilo
				hiloCliente.start();

			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
		
	}

}
