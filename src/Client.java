import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

	// Sockets
	private Socket socket;
	private DatagramSocket dsocket;
	
	private BufferedReader input;
	private static ClientWindow window;
	private boolean isUDP;
	
	// Para TCP
	public Client(Socket _socket) {
		this.isUDP = false;
		this.socket = _socket;
	}
	
	// Para UDP
	public Client(DatagramSocket _dsocket) {
		this.isUDP = true;
		this.dsocket = _dsocket;
	}
	
	// Espera un mensaje del servidor
	public void listenServer() {
		String messege = "";
		DatagramPacket dPacketR;
		try {
			if(isUDP) {
				// No hacemos nada...
			}
			else {
				input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problemas al crear el buffer de entrada con el servidor");
			System.out.println(e);
		}
		
		boolean connected = true;
		
		while(connected) {
			try {
					if(isUDP) {
						byte[] bufer = new byte[256];
						dPacketR = new DatagramPacket(bufer, bufer.length);
						dsocket.receive(dPacketR);
						messege = new String(dPacketR.getData(), "UTF-8");
					} else {
						messege = input.readLine();
					}
				if(messege != null)
					window.chatTextArea.setText(window.chatTextArea.getText() + "\n" + messege);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Problema al recibir datos");
				System.out.println(e);
			}
		}
	}
	
    public static void main(String[] args) throws IOException {
    	
    	String username = "Paloma";
    	//String username = JOptionPane.showInputDialog("Inserte nombre de usuario");
    	System.out.println(username);
    	
    	String hostIP = "148.201.110.189";
    	//String hostIP = JOptionPane.showInputDialog("Inserte direccion del host");
    	System.out.println(hostIP);    	
    	
		try {
			// Init
			DatagramSocket dsocket = new DatagramSocket();
			/*String mensaje = "Hola Mundo";
			byte[] bufer = new byte[1000];
			InetAddress dirIP = InetAddress.getByName(hostIP);
			// Pa enviar
			DatagramPacket dPacketP = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, dirIP, 9734);
			dsocket.send(dPacketP);
			// Pa recibir
			DatagramPacket dPacketR = new DatagramPacket(bufer, bufer.length);
			dsocket.receive(dPacketR);
			// Pa finalizar
			System.out.println(dPacketR.getData());
			dsocket.close();*/
			
			Client client = new Client(dsocket);
			window = new ClientWindow();
			window.nameField.setText(username);
			window.ipField.setText(hostIP);
			
			// Conexin para ara UDP
			Conexion conexion = new Conexion(dsocket, hostIP, 9734, window.textField, window.nameField.getText());
			
			window.frame.setVisible(true);
			window.btnSendTxt.addActionListener(conexion);
			window.textField.addActionListener(conexion);
			client.listenServer();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha podido establecer conexion con el servidor");
			System.out.println(e);
		}
    }
}
