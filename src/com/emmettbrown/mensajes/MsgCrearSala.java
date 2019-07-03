package com.emmettbrown.mensajes;

import com.emmettbrown.entorno.grafico.Sala;
import com.emmettbrown.servidor.HiloCliente;
import com.emmettbrown.servidor.Servidor;

public class MsgCrearSala extends Msg {

	private int idC ;
	
	public MsgCrearSala(int s) {
		this.idC = s;
	}
	
	@Override
	public Object realizarAccion(Object obj) {
		HiloCliente hilo = (HiloCliente) obj;
		Sala sala = new Sala(Servidor.idSalas++,"Sala de "+this.idC,0,Integer.MAX_VALUE);
		hilo.agregarSala(sala);
		System.out.println("MsgCrearSala");
		hilo.broadcast(new MsgActualizarListaSalas(sala), hilo.getUsuariosConectados());
		
		return null;
	}

}
