package com.emmettbrown.entorno.grafico;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.mensajes.MsgActualizarLista;
import com.emmettbrown.principal.Motor;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JVentanaSala extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Cliente cliente;

	public JVentanaSala(Cliente cliente2) {
		cliente = cliente2;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		JList list = new JList();
		list.setBounds(10, 29, 414, 172);
		contentPane.add(list);
		DefaultListModel<String> df = new DefaultListModel<>();
		list.setModel(df);
//		cliente.enviarMsg(new MsgActualizarLista(df));
		JButton btnCrearPartida = new JButton("Crear partida");
		btnCrearPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cliente.enviarMsg(new MsgActualizarLista(df));
//				Motor m = new Motor("",null);
//				m.iniciarJuego();
//				m.gameLoop();			
			}
		});
		btnCrearPartida.setBounds(134, 212, 162, 23);
		contentPane.add(btnCrearPartida);
	}
}
