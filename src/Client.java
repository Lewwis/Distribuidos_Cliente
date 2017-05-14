import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class Client {

	private Socket socket;
	private DatagramSocket dsocket;
	private BufferedReader input;
	private static ClientWindow window;
	private boolean isUDP;
	private static Conexion conexion;
	
	// Constructor para TCP
	public Client(Socket _socket) {
		this.isUDP = false;
		this.socket = _socket;
	}
	
	// Constructor para UDP
	public Client(DatagramSocket _dsocket) {
		this.isUDP = true;
		this.dsocket = _dsocket;
	}
	
	// Espera un mensaje del servidor y envia mensajes de la pila de mensajes
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
					// Verifica si se tienen que enviar mensajes de la pila
					if(messege != null && messege.substring(0,2).matches(new String("/1")))
						conexion.popMessege();	
					else if(messege != null) {
						window.chatTextArea.setText(window.chatTextArea.getText() + "\n" + messege);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Problema al recibir datos");
				System.out.println(e);
			}
		}
	}
	
	// Clase principal
    public static void main(String[] args) throws IOException {
    	
    	String username = JOptionPane.showInputDialog("Inserte nombre de usuario");
    	System.out.println(username);
    	
    	String hostIP = JOptionPane.showInputDialog("Inserte direccion del host");
    	System.out.println(hostIP);    	
    	
		try {
			// Init
			DatagramSocket dsocket = new DatagramSocket();
			
			Client client = new Client(dsocket);
			window = new ClientWindow();
			window.nameField.setText(username);
			window.ipField.setText(hostIP);
			
			// Conexin para ara UDP
			conexion = new Conexion(dsocket, hostIP, 9734, window.textField, window.chatTextArea, window.nameField.getText());
			
			window.frame.setVisible(true);
			window.btnSendTxt.addActionListener(conexion);
			window.textField.addActionListener(conexion);
			
			// Hilo que enviara los mensajes en cola
			ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					conexion.sendLastMessege();
				}
			}, 0, 1, TimeUnit.SECONDS);
			
			client.listenServer();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se ha podido establecer conexion con el servidor");
			System.out.println(e);
		}
    }
}
