import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Panel;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JPanel;

public class ClientWindow {

	public JFrame frame;
	public JTextField textField;
	public JTextField ipField;
	public JTextArea chatTextArea;
	public JTextField nameField;
	public Panel settingsPanel;
	public JPanel ipPanel;
	public JScrollPane scrollPane;
	public JTextPane nameText;
	public JPanel panel;
	public JTextPane ipText;
	public JButton btnSendTxt;
	public Panel textPanel;
	private JButton btnNewButton_1;
	
	/**
	 * Create the application.
	 */
	public ClientWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		textPanel = new Panel();
		frame.getContentPane().add(textPanel, BorderLayout.SOUTH);
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textPanel.add(textField);
		textField.setColumns(10);
		
		btnSendTxt = new JButton("Enviar");
		textPanel.add(btnSendTxt);
		
		settingsPanel = new Panel();
		frame.getContentPane().add(settingsPanel, BorderLayout.NORTH);
		settingsPanel.setLayout(new BorderLayout(0, 0));
		
		ipPanel = new JPanel();
		settingsPanel.add(ipPanel, BorderLayout.SOUTH);
		ipPanel.setLayout(new BorderLayout(0, 0));
		
		ipField = new JTextField();
		ipField.setEditable(false);
		ipPanel.add(ipField, BorderLayout.CENTER);
		ipField.setColumns(10);
		
		ipText = new JTextPane();
		ipPanel.add(ipText, BorderLayout.WEST);
		ipText.setText("IP Server: ");
		ipText.setEditable(false);
		
		btnNewButton_1 = new JButton("New button");
		ipPanel.add(btnNewButton_1, BorderLayout.EAST);
		
		panel = new JPanel();
		settingsPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		nameText = new JTextPane();
		nameText.setText("Nombre de usuario:");
		nameText.setEditable(false);
		panel.add(nameText, BorderLayout.WEST);
		
		nameField = new JTextField();
		nameField.setEditable(false);
		nameField.setText("Default");
		nameField.setColumns(10);
		panel.add(nameField, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		scrollPane.setViewportView(chatTextArea);
		chatTextArea.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret) chatTextArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

}
