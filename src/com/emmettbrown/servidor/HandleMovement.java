package com.emmettbrown.servidor;

import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.emmettbrown.controles.Movimientos;
import com.emmettbrown.entorno.grafico.DefConst;
import com.emmettbrown.mensajes.cliente.MsgEliminarBomberman;
import com.emmettbrown.mensajes.cliente.MsgPonerBomba;
import com.emmettbrown.mensajes.cliente.MsgPosBomberman;
import com.emmettbrown.servidor.entidades.SvBomberman;

public class HandleMovement extends Thread {

	private HiloCliente hilo;
	private ArrayList<ObjectOutputStream> outputStreams;
	private Movimientos mov;
	private SvBomberman bomberActual;
	private boolean run;

	public void setMovimiento(Movimientos mov) {
		this.mov = mov;
	}

	public HandleMovement(HiloCliente hilo, ArrayList<ObjectOutputStream> outputStreams) {
		this.hilo = hilo;
		this.mov = Movimientos.NULL;
		this.outputStreams = outputStreams;
		this.run = true;
	}

	public void handleInput() {
		bomberActual = hilo.getBomber();
		if (bomberActual.estaVivo()) {
			switch (mov) {
			case IZQUIERDA:
				hilo.getMap().moverBombermanIzq(bomberActual, DefConst.VELOCIDAD);
				if (bomberActual.estaVivo() == false)
					hilo.broadcast(new MsgEliminarBomberman(bomberActual.getIdBomberman()), outputStreams);
				else
					hilo.broadcast(
							new MsgPosBomberman(bomberActual.obtenerID(), bomberActual.getX(), bomberActual.getY()),
							outputStreams);
				break;

			case DERECHA:
				hilo.getMap().moverBombermanDer(bomberActual, DefConst.VELOCIDAD);
				if (bomberActual.estaVivo() == false)
					hilo.broadcast(new MsgEliminarBomberman(bomberActual.getIdBomberman()), outputStreams);
				else
					hilo.broadcast(
							new MsgPosBomberman(bomberActual.obtenerID(), bomberActual.getX(), bomberActual.getY()),
							outputStreams);
				break;

			case ARRIBA:
				hilo.getMap().moverBombermanArriba(bomberActual, DefConst.VELOCIDAD);
				if (bomberActual.estaVivo() == false)
					hilo.broadcast(new MsgEliminarBomberman(bomberActual.getIdBomberman()), outputStreams);
				else
					hilo.broadcast(
							new MsgPosBomberman(bomberActual.obtenerID(), bomberActual.getX(), bomberActual.getY()),
							outputStreams);
				break;

			case ABAJO:
				hilo.getMap().moverBombermanAbajo(bomberActual, DefConst.VELOCIDAD);
				if (bomberActual.estaVivo() == false)
					hilo.broadcast(new MsgEliminarBomberman(bomberActual.getIdBomberman()), outputStreams);
				else
					hilo.broadcast(
							new MsgPosBomberman(bomberActual.obtenerID(), bomberActual.getX(), bomberActual.getY()),
							outputStreams);
				break;

			case BOMBA:
				hilo.getMap().agregarBomba(bomberActual.getX(), bomberActual.getY(), bomberActual);
				hilo.broadcast(new MsgPonerBomba(bomberActual.getX(), bomberActual.getY(), bomberActual),
						outputStreams);
				break;

			case NULL:
				break;

			default:
				break;
			}
		}
	}

	public void matarHandle() {
		this.run = false;
	}
	
	public void run() {
		long initialTime = System.nanoTime();
		final double timeF = 1000000000 / DefConst.FPS;

		double deltaF = 0; // deltaU = 0,

		while (run && hilo.siEstaConectado()) {
			long currentTime = System.nanoTime();
			deltaF += (currentTime - initialTime) / timeF;
			initialTime = currentTime;
			if (deltaF >= 1) {
				handleInput();
				deltaF--;
			}
		}
	}
}
