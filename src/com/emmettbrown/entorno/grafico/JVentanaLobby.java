package com.emmettbrown.entorno.grafico;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.emmettbrown.cliente.Cliente;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class JVentanaLobby extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanelLobby contentPane;
	private Cliente cliente;
	private JList lstJugadores;
	private DefaultListModel<String> df;
	private RefreshThread refreshThread;
	private ArrayList<String> usuariosConectados = new ArrayList<String>();

	public JVentanaLobby(Cliente cliente2, boolean puedeCrearPartida) {
		setTitle("Sala: ");
		cliente = cliente2;
		cliente.getListaUsuariosSala().add("Usuario "+cliente.getIdCliente());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanelLobby(this);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
	    lstJugadores = new JList();
		lstJugadores.setBounds(10, 36, 414, 165);
		contentPane.add(lstJugadores);
		df = new DefaultListModel<>();
		lstJugadores.setModel(df);
		JButton btnCrearPartida = new JButton("Comenzar partida");
		btnCrearPartida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
			}
		});
		btnCrearPartida.setBounds(134, 212, 162, 23);
		contentPane.add(btnCrearPartida);
		
		JLabel lblNewLabel = new JLabel("Jugadores conectados en esta sala:");
		lblNewLabel.setBounds(10, 11, 196, 14);
		contentPane.add(lblNewLabel);
		
		refreshThread = new RefreshThread(this, 1);
		refreshThread.start();
		btnCrearPartida.setEnabled(puedeCrearPartida);
	}

	public void refrescarListaUsuarios() {
		this.usuariosConectados = cliente.getListaUsuariosSala();
		df.clear();
		for (String usr : usuariosConectados) {
			df.addElement(usr);
		}
	}
}
