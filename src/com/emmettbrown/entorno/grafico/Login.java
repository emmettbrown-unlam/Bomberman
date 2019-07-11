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
import com.emmettbrown.mensajes.Msg;
import com.emmettbrown.mensajes.cliente.MsgPudoCrearUsuario;
import com.emmettbrown.mensajes.servidor.MsgCrearUsuario;
import com.emmettbrown.mensajes.servidor.MsgValidarUsuario;

public class Login extends JFrame implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private Cliente cliente;
	private transient Socket readSocket;
	private transient Socket writeSocket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private int respuestaRecibida = 0;
	private int respuestaCrearUsuario = 0;
	private static final int CICLAR = 1;
	private static final int CREADO = 2;
	private static final int DUPLICADO = 3;

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

	public void setRespuestaCrearUsuario(boolean exito) {
		if (exito == DefConst.DUPLICADO)
			this.respuestaCrearUsuario = DUPLICADO;
		else
			this.respuestaCrearUsuario = CREADO;
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
		txtUsername.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// validarUsuario(txtUsername.getText(),new String (txtPassword.getPassword()));
			}
		});
		txtUsername.setBounds(197, 47, 107, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);

		txtPassword = new JPasswordField();
		txtPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// validarUsuarioBD(txtUsername.getText(),new String
				// (txtPassword.getPassword()));
			}
		});
		txtPassword.setBounds(197, 72, 107, 20);
		contentPane.add(txtPassword);
//		txtUsername.setText("Nico");
//		txtPassword.setText("1234");
		JButton btnIniciarSesin = new JButton("Iniciar Sesi\u00F3n");
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtUsername.getText().isEmpty() || new String(txtPassword.getPassword()).isEmpty())
					JOptionPane.showMessageDialog(null, "Los dos campos deben estar completados", "Acceso denegado",
							JOptionPane.ERROR_MESSAGE);
				else {

					try {
						outputStream.reset();
						Msg consultaCuenta = new MsgValidarUsuario(txtUsername.getText(),
								new String(txtPassword.getPassword()));
						outputStream.writeObject(consultaCuenta);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					while (respuestaRecibida == 0) {
						Object obj = null;
						try {
							obj = inputStream.readObject();
						} catch (IOException | ClassNotFoundException e) {
							String mensajeError = "Comunicacion cerrada en recibir msg1. " + e;
							System.out.println(mensajeError);
						}
						// Recibo mensajes del servidor
						Msg msgRecibido = (Msg) obj;
						// Ejecuto la acción
						msgRecibido.realizarAccion(obtenerVentana());
					}

					if (respuestaRecibida == 1) {
						try {
							readSocket.close();
							writeSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						cliente = new Cliente(DefConst.IP, DefConst.PORT, txtUsername.getText());
						JVentanaInicial inicial = new JVentanaInicial(cliente);
						inicial.setVisible(true);
						dispose();
					} else
						JOptionPane.showMessageDialog(null, "Usuario o Contraseña invalida", "Acceso denegado",
								JOptionPane.ERROR_MESSAGE);
					respuestaRecibida = 0;
				}
			}
		});
		btnIniciarSesin.setBounds(162, 100, 142, 23);
		contentPane.add(btnIniciarSesin);

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBounds(50, 213, 142, 23);
		contentPane.add(btnCrearUsuario);
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (txtUsername.getText().isEmpty() || new String(txtPassword.getPassword()).isEmpty())
					JOptionPane.showMessageDialog(null, "Los dos campos deben estar completados", "Acceso denegado",
							JOptionPane.ERROR_MESSAGE);
				else {
					try {
						outputStream.reset();
						Msg consultaCuenta = new MsgCrearUsuario(txtUsername.getText(),
								new String(txtPassword.getPassword()));
						outputStream.writeObject(consultaCuenta);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					respuestaCrearUsuario = CICLAR;
					while (respuestaCrearUsuario == CICLAR) {
						Object obj = null;
						try {
							obj = inputStream.readObject();
						} catch (IOException | ClassNotFoundException e) {
							String mensajeError = "Comunicacion cerrada en recibir msg1. " + e;
							System.out.println(mensajeError);
						}
						// Recibo mensajes del servidor
						MsgPudoCrearUsuario msgRecibido = (MsgPudoCrearUsuario) obj;
						// Ejecuto la acción
						msgRecibido.realizarAccion(obtenerVentana());

						if (respuestaCrearUsuario == DUPLICADO)
							JOptionPane.showMessageDialog(null, "El usuario ya existe", "Error",
									JOptionPane.ERROR_MESSAGE);
						if (respuestaCrearUsuario == CREADO)
							JOptionPane.showMessageDialog(null, "Usuario creado exitosamente", "Felicidades",
									JOptionPane.INFORMATION_MESSAGE);
					}

				}
			}
		});

		/**
		 * Inicializamos sockets para conectar al servidor FALTA AGREGAR ESTO, NO
		 * ARRANCA EL LOGIN
		 * 
		 */
		this.writeSocket = new Socket(DefConst.IP, DefConst.PORT);
		this.readSocket = new Socket(DefConst.IP, DefConst.PORT);

		this.inputStream = new ObjectInputStream(readSocket.getInputStream());
		this.outputStream = new ObjectOutputStream(writeSocket.getOutputStream());

		/// termina
	}

	public Login obtenerVentana() {
		return this;
	}

	public void setRespuestaRecibida(int respuesta) {
		this.respuestaRecibida = respuesta;
	}

	// Esto deberï¿½a ser server side...
//	public boolean validarUsuario(String user, String pass){
//		if (pass.equals("1234")){ //if (user.equals("bomber") && pass.equals("1234")){
//			return true;
//		} else {
//			JOptionPane.showMessageDialog(null, "Incorrecto, intente de nuevo", "Acceso denegado", JOptionPane.ERROR_MESSAGE);
//			return false;
//		}
//	}
}
