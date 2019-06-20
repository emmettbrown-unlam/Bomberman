package com.emmettbrown.mensajes;

import java.io.Serializable;

import com.emmettbrown.servidor.HiloCliente;

public abstract class Msg implements Serializable {

	private static final long serialVersionUID = 1L;

	public abstract void realizarAccion(HiloCliente hilo);
}
