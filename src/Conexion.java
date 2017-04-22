//package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
	
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			if(isUDP) {
				String mensaje = prepareMessege();
				DatagramPacket dPacketP = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, hostIP, port);
				dsocket.send(dPacketP);
			} else {
				outputStream.println(prepareMessege());
			}
			System.out.println("Mensaje enviado:" + prepareMessege());
			textField.setText("");
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null,"Fallo al momento de enviar el mensaje al servidor");
			System.out.println(e);
		}
	}
	
	public String prepareMessege() {
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		
		String date = " [" + Integer.toString(hour) + " : " 
							+ Integer.toString(minute) + " : " 
							+ Integer.toString(second) + "]" + "\t";
		
		String txt = date + user + " : "+ textField.getText() + "\0";
		
		// Pruebas con JSON --------------------
		
		/*JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("C:/Users/LuisR/workspace/client/JSON.txt"));
			JSONObject jsonObj = (JSONObject) obj;
			//txt = jsonObj.toJSONString();
			//txt = (String ) jsonObj.get("Name");
		} catch (Exception e) {
			System.out.println("Problemas con el JSON");
		}*/
				
		return txt;
	}
	
	// Aun sin implementar en la interfaz grafica
	public void setUser(String _user) {
		this.user = _user;
	}
	
}
