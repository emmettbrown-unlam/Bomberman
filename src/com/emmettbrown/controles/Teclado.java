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
	private Movimientos mov ;
	public Teclado(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if (mov == Movimientos.NULL) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
					//Este if sirve para no enviar muchos mensajes en el caso que la tecla se mantenga presionada.
					this.mov = Movimientos.ABAJO;
					this.cliente.enviarMsg(new MsgMover(Movimientos.ABAJO));
				break;
			case KeyEvent.VK_RIGHT:
					this.cliente.enviarMsg(new MsgMover(Movimientos.DERECHA));
					this.mov = Movimientos.DERECHA;
				break;
			case KeyEvent.VK_LEFT:
					this.cliente.enviarMsg(new MsgMover(Movimientos.IZQUIERDA));
					this.mov = Movimientos.IZQUIERDA;
				break;
			case KeyEvent.VK_UP:
					this.cliente.enviarMsg(new MsgMover(Movimientos.ARRIBA));
					this.mov = Movimientos.ARRIBA;
				break;
			case KeyEvent.VK_ESCAPE:
				JVentanaInicial ventAct = new JVentanaInicial(cliente);
				ventAct.setVisible(true);
				cliente.getVentanaGrafica().dispose();
				break;
			case KeyEvent.VK_L:
				if(this.cliente.getBomber().cantBombasAct() < DefConst.CANTBOMBASMAX)
					this.cliente.enviarMsg(new MsgMover(Movimientos.BOMBA));
				break;
			default:
				break;
			}
		}

//		if (cliente.getBomber() != null && cliente.getBomber().verSiEsVisible()) {
//			if (key == KeyEvent.VK_ESCAPE) {
//				JVentanaInicial ventAct = new JVentanaInicial(cliente);
//				ventAct.setVisible(true);
//				cliente.getVentanaGrafica().dispose();
//			}
//			if (key == KeyEvent.VK_RIGHT) {
//				System.out.println("no solte");
//				if (mov == Movimientos.NULL) {
//					this.cliente.enviarMsg(new MsgMover(Movimientos.DERECHA));
//					this.mov = Movimientos.DERECHA;
//				}
//				
//			}
//			if (key == KeyEvent.VK_LEFT) {
//				if (mov == Movimientos.NULL) {
//					this.cliente.enviarMsg(new MsgMover(Movimientos.IZQUIERDA));
//					this.mov = Movimientos.IZQUIERDA;
//				}
////				this.cliente.enviarMsg(new MsgMover(Movimientos.IZQUIERDA));
//			}
//			if (key == KeyEvent.VK_UP) {
//				if (mov == Movimientos.NULL) {
//					this.cliente.enviarMsg(new MsgMover(Movimientos.ARRIBA));
//					this.mov = Movimientos.ARRIBA;
//				}
////				this.cliente.enviarMsg(new MsgMover(Movimientos.ARRIBA));
//			}
//			if (key == KeyEvent.VK_DOWN) {
//				
//				if (mov == Movimientos.NULL) {
//					this.mov = Movimientos.ABAJO;
//					this.cliente.enviarMsg(new MsgMover(Movimientos.ABAJO));
//					
//				}
////				this.cliente.enviarMsg(new MsgMover(Movimientos.ABAJO));
//			}
//			if (key == KeyEvent.VK_L) {
//				if(this.cliente.getBomber().cantBombasAct() < DefConst.CANTBOMBASMAX)
//					this.cliente.enviarMsg(new MsgMover(Movimientos.BOMBA));
//			}
//		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

//		int key = e.getKeyCode();

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			this.mov = Movimientos.NULL;
			break;
		case KeyEvent.VK_RIGHT:
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			this.mov = Movimientos.NULL;
			break;
		case KeyEvent.VK_LEFT:
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			this.mov = Movimientos.NULL;
			break;
		case KeyEvent.VK_UP:
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			this.mov = Movimientos.NULL;
			break;
		case KeyEvent.VK_ESCAPE:
			//Agregar comando para salir
			break;
		case KeyEvent.VK_L:
			this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
			break;
		default:
			break;
		}
		
		
//		if (cliente.getBomber() != null && cliente.getBomber().verSiEsVisible()) {
//			if (key == KeyEvent.VK_ESCAPE) {
//				//Agregar comando para salir
//			}
//			if (key == KeyEvent.VK_RIGHT) {
//				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
//				this.mov = Movimientos.NULL;
//			}
//			if (key == KeyEvent.VK_LEFT) {
//				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
//				this.mov = Movimientos.NULL;
//			}
//			if (key == KeyEvent.VK_UP) {
//				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
//				this.mov = Movimientos.NULL;
//			}
//			if (key == KeyEvent.VK_DOWN) {
//				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
//				this.mov = Movimientos.NULL;
//			}
//			
//			if (key == KeyEvent.VK_L ) {
//				this.cliente.enviarMsg(new MsgMover(Movimientos.NULL));
//				
//			}
//		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}