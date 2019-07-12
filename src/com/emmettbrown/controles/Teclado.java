package com.emmettbrown.controles;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.entorno.grafico.JVentanaGrafica;
import com.emmettbrown.entorno.grafico.JVentanaInicial;
import com.emmettbrown.mensajes.servidor.MsgDesconectarDeSala;
import com.emmettbrown.mensajes.servidor.MsgMover;

public class Teclado implements KeyListener {
	
	private Cliente cliente;
	public Teclado(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (cliente.getBomber() != null && cliente.getBomber().verSiEsVisible()) {
			if (key == KeyEvent.VK_ESCAPE) {
				JVentanaInicial ventAct = new JVentanaInicial(cliente);
				ventAct.setVisible(true);
				JVentanaGrafica ventGraf = cliente.getVentanaGrafica();
				ventGraf.detenerSonido();
				ventGraf.dispose();
				cliente.enviarMsg(new MsgDesconectarDeSala());
			}
			if (key == KeyEvent.VK_RIGHT) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.DERECHA));
			}
			if (key == KeyEvent.VK_LEFT) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.IZQUIERDA));
			}
			if (key == KeyEvent.VK_UP) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.ARRIBA));
			}
			if (key == KeyEvent.VK_DOWN) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.ABAJO));
			}
			if (key == KeyEvent.VK_L) {
				if(this.cliente.getBomber().cantBombasAct() < DefConst.CANTBOMBASMAX)
				this.cliente.enviarMsg(new MsgMover(Movimientos.BOMBA));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		if (cliente.getBomber() != null && cliente.getBomber().verSiEsVisible()) {
			if (key == KeyEvent.VK_ESCAPE) {
				//Agregar comando para salir
			}
			if (key == KeyEvent.VK_RIGHT) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			}
			if (key == KeyEvent.VK_LEFT) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			}
			if (key == KeyEvent.VK_UP) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			}
			if (key == KeyEvent.VK_DOWN) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			}
			
			if (key == KeyEvent.VK_L ) {
				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			}
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}