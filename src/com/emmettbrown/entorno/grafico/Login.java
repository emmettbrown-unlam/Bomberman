package com.emmettbrown.entorno.grafico;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.emmettbrown.cliente.Cliente;
import com.emmettbrown.entidades.DefConst;
import com.emmettbrown.mensajes.MsgLogin;
import com.emmettbrown.principal.Motor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JPasswordField passwordField;
	private Cliente cliente ;

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

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/resources/icons/bomb.png")));
		setTitle("Iniciar sesi\u00F3n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		JLabel lblIngreseUsuario = new JLabel("Ingrese Usuario");
		lblIngreseUsuario.setBounds(50, 50, 126, 14);
		contentPane.add(lblIngreseUsuario);

		JLabel lblIngreseContrasea = new JLabel("Ingrese Contrase\u00F1a");
		lblIngreseContrasea.setBounds(50, 75, 126, 14);
		contentPane.add(lblIngreseContrasea);

		txtUsuario = new JTextField();
		txtUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				validarUsuario(txtUsuario.getText(),new String (passwordField.getPassword()));
			}
		});
		txtUsuario.setBounds(197, 47, 107, 20);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				validarUsuario(txtUsuario.getText(),new String (passwordField.getPassword()));
			}
		});
		passwordField.setBounds(197, 72, 107, 20);
		contentPane.add(passwordField);
		cliente = new Cliente(DefConst.IP, DefConst.PORT, txtUsuario.getText());
		JButton btnIniciarSesin = new JButton("Iniciar Sesi\u00F3n");
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					
					cliente.enviarMsg(new MsgLogin(txtUsuario.getText(),passwordField.getText()));
					String res = (String)cliente.recibirMsg();
					System.out.println(res);
					if (res.equals("OK")) {
						JVentanaSala v = new JVentanaSala(cliente);
						v.setVisible(true);
						dispose();
					}else {
						JOptionPane.showMessageDialog(null, "Incorrecto, intente de nuevo", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
					}


			}
		});
		btnIniciarSesin.setBounds(162, 100, 142, 23);
		contentPane.add(btnIniciarSesin);

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBounds(50, 213, 142, 23);
		contentPane.add(btnCrearUsuario);
	}

	public boolean validarUsuario(String user, String pass){
		if(user.equals("bomber")&&pass.equals("1234")){
//			Motor m = new Motor("s");
//			m.jugar("s");
//			dispose();
			return true;
		}else{
			JOptionPane.showMessageDialog(null, "Incorrecto, intente de nuevo", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
}
