package com.emmettbrown.principal;

import java.net.Socket;
import java.util.ArrayList;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entidades.Bomberman;
import com.emmettbrown.entidades.DefConst;
import com.emmettbrown.entorno.grafico.JVentanaGrafica;
import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mensajes.BuzonMsg;
import com.emmettbrown.mensajes.MsgGenerarListaBomberman;
import com.emmettbrown.mensajes.MsgGenerarMapa;

public class Motor {

	private Mapa miMapa;
	private JVentanaGrafica miVentana;
	private boolean iniciado;
	private Cliente cliente = new Cliente(DefConst.IP, DefConst.PORT, "");
	private BuzonMsg buzon;

	
	public Motor(String miUsuario,ArrayList<Socket> listaClientesConectados) {
//		this.buzon = new BuzonMsg(cliente,miMapa);
//		this.buzon.execute();
		cliente.enviarMsg(new MsgGenerarMapa());
//		miMapa = new Mapa();
//		miMapa.generarMapa();
		cliente.enviarMsg(new MsgGenerarListaBomberman(miMapa));
		String resultado = (String) cliente.recibirMsg();
		//Bomberman propio del usuario conectado.
		Bomberman miBomber = new Bomberman(75, 75, DefConst.DEFAULTWIDTH,DefConst.DEFAULTHEIGHT); 

		miVentana = new JVentanaGrafica(miMapa,DefConst.ANCHO, DefConst.ALTO,miBomber,this.cliente);
	}

	public void iniciarJuego() {
		this.iniciado = true;
		miVentana.setVisible(true);
	}

	public void gameLoop() {
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / DefConst.FPS;
		double deltaF = 0; // deltaU = 0, 

	    while (iniciado) {
	    	
	        long currentTime = System.nanoTime();
	        deltaF += (currentTime - initialTime) / timeF;
	        initialTime = currentTime;
	        if (deltaF >= 1) {
	            actualizar();
	            deltaF--;
	        }
	    }
	}
	
	private void actualizar() {
		miVentana.refrescar();
	}

	public void cerrarJuego() {
		iniciado = false;
	}

//	public void jugar(String u){
//		Motor m = new Motor(u);
//		m.iniciarJuego();
//		m.gameLoop();
//	}
//	
//	public static void main(String[] args) {
//		String miUsuario = "Usuario1";
//		Motor m = new Motor(miUsuario);
//		m.iniciarJuego();
//		m.gameLoop();
//	}

}
