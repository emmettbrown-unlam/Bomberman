package com.emmettbrown.base.datos.base;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.emmettbrown.entorno.grafico.DefConst;

public class GestionBD {
	private SessionFactory factory;
	
	
	public GestionBD(SessionFactory factory) {
		this.factory=factory;
	}
	
	public SessionFactory getFactory() {
		return factory;
	}
	
//
//	public List<Object[]> validarUsuario(Usuario usuario) {
//		String consulta = "select p.contraseña from Usuario p " + "where p.usuario=" + "'" + usuario.getUsuario() + "'" + 
//				" and p.contraseña=" + "'" + usuario.getContraseña() + "'";
//		return obtenerRegistro(consulta);
//	}
//	
//	public List<Object[]> obtenerRegistro(String consultaString) {
//		Session session = factory.openSession();
//		Query q = session.createQuery(consultaString);
//		@SuppressWarnings("unchecked")
//		List<Object[]> listaUsuarios = q.getResultList();
//		
//		for(int i=0; i< listaUsuarios.size() ; i++ ) {
//			System.out.println(listaUsuarios.get(i));
//		}
//		session.close();	
//		return listaUsuarios;
//	}
//
//	@SuppressWarnings("unchecked")
//	public boolean insertarRegistro(Usuario usuario) {
//		Session session = factory.openSession();
//		List<Object[]> listaUsuarios = null;
//        String consulta = "select pp.usuario from Usuario pp ";
//		consulta += "where pp.usuario=" + "'"+usuario.getUsuario()+"'";
//		Query q = session.createQuery(consulta);
//		listaUsuarios=q.getResultList();
//		System.out.println(listaUsuarios.size());
//		
//		if (listaUsuarios.size()==1 ) {
//			return 1; 	
//		}
//		else {
//			Transaction tx = session.beginTransaction();			
//			try {
//				session.save(usuario);
//				tx.commit();
//			}
//			catch (Exception e) {
//				if (tx != null)
//					tx.rollback();
//				e.printStackTrace();
//				System.out.println("Error");
//				return false;
//			}
//			session.close();
//			return DefConst.EXITO;
//		}
//	}
//	
//	public void actualizarRegistro(Usuario usuario) {
//		Session session = factory.openSession();
//		List<Object[]> listaUsuarios = null;
//        String consulta = "select pp.usuario from Usuario pp ";
//		consulta += "where pp.usuario=" + "'"+usuario.getUsuario()+"'";
//		Query q = session.createQuery(consulta);
//		listaUsuarios=q.getResultList();
//		if(listaUsuarios!=null && listaUsuarios.size()==1) {
//			Transaction tx = session.beginTransaction();
//			
//			try {
//				session.update(usuario);
//				tx.commit();
//			}
//			catch (HibernateException e) {
//				if (tx != null)
//					tx.rollback();
//				e.printStackTrace();
//			}
//			session.close();
//	 }
//	}
//	
//	
	
	public static void main(String[] args) {
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		Configuracion conf = new Configuracion();
		GestionBD g = new GestionBD(conf.getFactory());
		Session session = g.getFactory().openSession();
		Transaction t = session.beginTransaction();
		List<Object[]> listaUsuarios = null;
		Query q = session.createQuery("select pp.usuario,pp.contraseña,pp.puntaje from Usuario pp");
		listaUsuarios=q.getResultList();
		t.commit();
		session.close();
		for(int i=0; i< listaUsuarios.size() ; i++ ) {
			Object fila[] = listaUsuarios.get(i);
			System.out.println("Usuario: "+(String)fila[0]+ " Contrasenia: "+(String)fila[1] + " Puntaje: "+ (Integer)fila[2]);
		}
		
	}
}
