package com.emmettbrown.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.emmettbrown.base.datos.base.Configuracion;
import com.emmettbrown.base.datos.base.GestionBD;
import com.emmettbrown.base.datos.base.Usuario;
import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.servidor.entidades.SvSala;

public class Servidor {
	// El puerto del servidor
	private int port;
	// El socket del servidor
	private ServerSocket serverSocket;
	// Lista de Sockets de los clientes conectados
	// private ArrayList<Socket> usuariosConectados;
	private ArrayList<ObjectOutputStream> usuariosConectados;
	// Listado de salas creadas en el server
	private ArrayList<SvSala> listaSalas;
	// private int nroCliente;
	public static int idSalas = 0;
	// Base de datos
	Configuracion configuracion;
	GestionBD gestion;
	// socket temporal
	private transient Socket readSocket;
	private transient Socket writeSocket;
	private int estaConectado = 0;

	public Servidor(int puerto) {
		this.port = puerto;
		this.usuariosConectados = new ArrayList<ObjectOutputStream>();
		this.listaSalas = new ArrayList<SvSala>();
		// base de datos
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		configuracion = new Configuracion();
		gestion = new GestionBD(configuracion.getFactory());

	}

	public static void main(String[] args) {
		Servidor miServidor = new Servidor(DefConst.PORT);
		miServidor.iniciarServidor();
	}

	public boolean validarUsuarioBD(String usuario, String contrasenia) {
		gestion.insertarRegistro(new Usuario(usuario, contrasenia));
		List<Object[]> lista = gestion.validarUsuario(new Usuario(usuario, contrasenia));
		return lista.size() == 0 ? false : true;
	}

	public void iniciarServidor() {
		try {
			serverSocket = new ServerSocket(this.port);
			// Sockets del cliente
			Socket writeSocket;
			Socket readSocket;
			while (true) {
				System.out.println("Servidor esperando clientes!");
				// Este método bloquea la ejecución del Thread hasta que se reciba una conexión
				// entrante
				// de un cliente

				// Aceptamos ambos sockets
				writeSocket = serverSocket.accept();
				readSocket = serverSocket.accept();

				// Añadimos el socket del cliente a la lista de sockets
				// this.usuariosConectados.add(writeSocket.get);
				// this.usuariosConectados.add(new
				// ObjectOutputStream(writeSocket.getOutputStream()));
				System.out.println("¡Conexion aceptada!");
				ConexionEntrante solicitudConexion = new ConexionEntrante(writeSocket, readSocket, this);
				solicitudConexion.run();

			}
		} catch (IOException ex) {
			System.out.println(ex);
		}

	}

	public void conectarClienteHilo() {
		estaConectado = 1;
	}

	public void iniciarHiloCliente(Socket readSocketHilo, Socket writeSocketHilo, ObjectOutputStream outputStream, ObjectInputStream inputStream) {
		// Creamos un hilo para el cliente (evitando así el bloqueo que se genera en
		// este mismo hilo)
		// Le envíamos como datos el socket del cliente, y la lista de usuarios
		// conectados

		HiloCliente hiloCliente = new HiloCliente(writeSocketHilo, readSocketHilo, outputStream, inputStream, usuariosConectados, listaSalas);
		// Iniciamos el hilo
		hiloCliente.start();
	}
	
	public boolean crearUsuarioValido(String usuario, String contrasenia) {
		return gestion.insertarRegistro(new Usuario(usuario,contrasenia));
	}

}
