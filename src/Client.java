import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Client {

	private Socket socket;
	private BufferedReader input;
	private static ClientWindow window;
	
	public Client(Socket _socket)
	{
		this.socket = _socket;
	}
	
	public void listenServer() {
		String messege = "";
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problemas al crear el buffer de entrada con el servidor");
			System.out.println(e);
		}
		
		boolean connected = true;
		
		while(connected) {
			try {
				messege = input.readLine();
				if(messege != null)
					window.chatTextArea.setText(window.chatTextArea.getText() + "\n" + messege);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Problema al recibir datos");
				System.out.println(e);
			}
		}
	}
	
    public static void main(String[] args) throws IOException {
    	
    	String username = JOptionPane.showInputDialog("Inserte nombre de usuario");
    	System.out.println(username);
    	
    	String hostIP = JOptionPane.showInputDialog("Inserte direccion del host");
    	System.out.println(hostIP);    	
    	
		try {
			Socket socket = new Socket(hostIP, 9734);
			Client client = new Client(socket);
			window = new ClientWindow();
			window.nameField.setText(username);
			window.ipField.setText(hostIP);
			Conexion conexion = new Conexion(socket, window.textField, window.nameField.getText());
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
