package com.emmettbrown.entorno.grafico;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.io.Serializable;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.mensajes.servidor.MsgCrearUsuario;
import com.emmettbrown.mensajes.servidor.MsgLogin;

public class Login extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private Cliente cliente;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() throws UnknownHostException, IOException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/resources/icons/bomb.png")));
		setTitle("Iniciar sesi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblIngreseUsuario = new JLabel("Ingrese Usuario");
		lblIngreseUsuario.setBounds(50, 50, 126, 14);
		contentPane.add(lblIngreseUsuario);

		JLabel lblIngreseContrasea = new JLabel("Ingrese Contrase\u00F1a");
		lblIngreseContrasea.setBounds(50, 75, 126, 14);
		contentPane.add(lblIngreseContrasea);

		txtUsername = new JTextField();
		txtUsername.setBounds(197, 47, 107, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.setBounds(197, 72, 107, 20);
		contentPane.add(txtPassword);
		JButton btnIniciarSesin = new JButton("Iniciar Sesi\u00F3n");
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cliente != null) {
					cliente.enviarMsg(new MsgLogin(txtUsername.getText(),new String(txtPassword.getPassword())));
				}else {
					cliente = new Cliente(DefConst.IP, DefConst.PORT, txtUsername.getText());
					cliente.enviarMsg(new MsgLogin(txtUsername.getText(),new String(txtPassword.getPassword())));
					setPantallaLoginEnCliente();
				}

					
			}
		});
		btnIniciarSesin.setBounds(162, 100, 142, 23);
		contentPane.add(btnIniciarSesin);

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cliente != null) {
					cliente.enviarMsg(new MsgCrearUsuario(txtUsername.getText(),new String(txtPassword.getPassword())));
				}else {
					cliente = new Cliente(DefConst.IP, DefConst.PORT, txtUsername.getText());
					cliente.enviarMsg(new MsgCrearUsuario(txtUsername.getText(),new String(txtPassword.getPassword())));
				}
				
				
				setPantallaLoginEnCliente();			
			}
		});
		btnCrearUsuario.setBounds(50, 213, 142, 23);
		contentPane.add(btnCrearUsuario);

	}
	
	private void setPantallaLoginEnCliente() {
		cliente.setPantallaLogin(this);
	}
}
