package com.emmettbrown.entidades;

public class Reloj {

	private int hora,min,seg;
	
	public Reloj (int h,int m,int s ) {
		this.hora = h;
		this.min = m;
		this.seg = s;
	}
	
	public Reloj restarSegundo () {
		Reloj copy = new Reloj (this.hora,this.min,this.seg);
		
		if (copy.seg == 0) {
			if (copy.min == 0) {
				if (copy.hora > 0) {
					copy.hora--;
					copy.min = 59;
					copy.seg = 59;
				}
			}else {
				copy.min--;
				copy.seg = 60;
			}
		}else {
			copy.seg--;
		}
		return copy;
	}
	
	public String toString() {
		return String.format("%02d:%02d:%02d",hora,min,seg);
	}
}
