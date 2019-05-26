package com.emmettbrown.entidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import com.emmettbrown.mapa.Mapa;
import com.emmettbrown.mapa.Ubicacion;

public class Explosion extends Entidad {

	private Timer t;
	private ImageIcon fuegoArrAba;
	private ImageIcon fuegoIzqDer;
	
	public Explosion(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		fuegoArrAba = new ImageIcon("./src/resources/bomb/fuegoArrAba.png");
		fuegoIzqDer = new ImageIcon("./src/resources/bomb/fuegoIzqDer.png");
		img = new ImageIcon("./src/resources/bomb/fuegoCen.png");
	}
	
	class miOyente implements ActionListener {
		private Mapa map;
		private Ubicacion ubic;
		
		public miOyente(Mapa map, Ubicacion ubic) {
			this.map = map;
			this.ubic = ubic;
		}
		
		@Override
		public void actionPerformed(ActionEvent ae) {
			map.removerEntidadDelConjunto(ubic);
			t.stop();
		}
	}
	
	public void startTimer(Mapa map) {
		t = new Timer(3000, new miOyente(map, this.ubicacion));
		t.start();
	}

	@Override
	public void explotar(Mapa map) {
		// TODO Auto-generated method stub
		
	}
	
	public void cambiarImagenArrAba() {
		setImage(fuegoArrAba);
	}
	
	public void cambiarImagenIzqDer() {
		setImage(fuegoIzqDer);
	}
}
