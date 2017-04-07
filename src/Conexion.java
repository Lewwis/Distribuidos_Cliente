//package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

// Listener para el boton que enviara los datos del cliente al servidor
public class Conexion implements ActionListener {
	
	private Socket socket;
	private PrintWriter outputStream;
	private JTextField textField;
	private String user;
	
	public Conexion(Socket _socket, JTextField _textField, String _user) {
		this.socket = _socket;
		this.textField = _textField;
		this.user = _user;
		try {
			outputStream = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Problema al momento de crear salida de datos");
			System.out.println(e);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String txt = prepareMessege(); // <--- Solo para debugueo
			outputStream.println(prepareMessege());
			//outputStream.println(textField.getText());
			textField.setText("");
			System.out.println("Mensaje enviado:" + txt);
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
		
		return txt;
	}
	
	// Aun sin implementar en la interfaz grafica
	public void setUser(String _user) {
		this.user = _user;
	}
	
}
