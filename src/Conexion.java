//package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

// Listener para el boton que enviara los datos del cliente al servidor
public class Conexion implements ActionListener {
	
	// Elementos para TCP
	private Socket socket;
	private PrintWriter outputStream;

	// Elementos para UDP
	private DatagramSocket dsocket;
	private InetAddress hostIP;
	private int port;
	private boolean isUDP;
	
	private JTextField textField;
	private String user;
	private ArrayList<String> mensajes =new ArrayList<String>();
	
	// Constructor para TCP
	public Conexion(Socket _socket, JTextField _textField, String _user) {
		this.socket = _socket;
		this.textField = _textField;
		this.user = _user;
		this.isUDP = false;
		try {
			outputStream = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Problema al momento de crear salida de datos");
			System.out.println(e);
		}
	}
	
	// Constructor para UDP
	public Conexion (DatagramSocket _dsocket, String _hostIP, int _port, JTextField _textField, String _user) {
		this.dsocket = _dsocket;
		this.port = _port;
		this.textField = _textField;
		this.user = _user;
		this.isUDP = true;
		try {
			this.hostIP = InetAddress.getByName(_hostIP);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Problema al recibir la IP del servidor y el puerto");
			System.out.println(e);
		}
	}
	
	// Envia el mensaje por el socket dependiendo del tipo de conexion
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			int type = lookType(textField.getText());
			if(isUDP) {
				if(type != 6) {
					String mensaje = prepareMessege(type);
					mensajes.add(mensaje);
					System.out.println("Mensaje enviado");
				} else {
					String mensaje = textField.getText().substring(3);
					System.out.println("Se mandaria esto:" + mensaje);
					TwitterConexion.tweet(mensaje);
				}
			} else {
				outputStream.println(prepareMessege(type));
			}
			System.out.println("Mensaje enviado:" + prepareMessege(type));
			textField.setText("");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null,"Fallo al momento de enviar el mensaje al servidor");
			System.out.println(e);
		}
	}
	
	// Prepara el mensaje con cierto formato para ser enviado y lo codifia a JSON
	public String prepareMessege(int _typeMensaje) {
		
		String destino = null;
		String txt = null;
		int type = _typeMensaje;
		
		switch (_typeMensaje) {
		case 0:	// Broadcast
			destino = null;
			txt = user + " : "+ textField.getText() + "\0";
			break;
		case 2:	// Mensaje directo
			String aux[] = textField.getText().split(" ");
			destino = aux[1];
			txt = user + " : "+ textField.getText().substring(aux[1].length() + 4) + "\0";
			break;
		case 3:
			txt = textField.getText() + "\0";
			break;
		
		default:
			txt = textField.getText().substring(3) + "\0";
			break;
		}	
				
		return JsonManager.codeJson(type,user,destino,txt);
	}
	
	// Envia el ultimo mensaje almacenado en cola
	public void sendLastMessege() {
		if(mensajes.size() != 0) {
			try {
				String mensaje = mensajes.get(mensajes.size() - 1);
				DatagramPacket dPacketP = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, hostIP, port);
				dsocket.send(dPacketP);
				// No hagamos nada por ahora, evitemos el spam...
				//System.out.println("Ultimo mensaje es : " + mensajes.get(mensajes.size() - 1));
			} catch (Exception e) {
				System.out.println(e);
			}

		} 
		else {
			// No hagadas nada por ahora, evitemos el spam...
			//System.out.println("No hay mensajes");
		}
	}
	
	// Saca el ultimo mensaje de la cola
	public void popMessege() {
		mensajes.remove(mensajes.size()-1);
	}

	// Devuelve un numero dependiendo de el inicio del mensaje
	//		Nota: el tipo 1 esta reservado para Acknowledge
	public int lookType(String _mensaje) {
		String aux[] = _mensaje.split(" ");
		switch (aux[0]) {
		case "/p": return 2;	// Mensaje privado
		case "/c": return 3;	// Usuarios conectados
		case "/b": return 4;	// Bloquear usuario
		case "/d": return 5;	// Desbloquar usuario
		case "/t": return 6;	// Funciones de red social

		default: return 0;		// Broadcast
		}
	}
	
}
