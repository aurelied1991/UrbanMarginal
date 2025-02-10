package vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DemarrageJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtStart;
	private JTextField txtConnect;
	private JTextField txtIpServer;
	private JTextField txtIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DemarrageJeu frame = new DemarrageJeu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * clic sur le bouton Connect pour se connecter à un serveur 
	 */
	private void btnConnect_clic() {
		// Ouvre la fenêtre ChoixPersonnage
		(new ChoixPersonnage()).setVisible(true);
		// Ferme la fenêtre actuelle
		this.dispose();
	}	
	
	/**
	 * clic sur le bouton Start pour lancer le jeu
	 */
	private void btnStart_clic() {
		// Ouvre la fenêtre de l'arène
		(new Arene()).setVisible(true);
		// Ferme la fenêtre actuelle
		this.dispose();
	}

	/**
	 * Create the frame.
	 */
	public DemarrageJeu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 507, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtStart = new JTextField();
		txtStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtStart.setText("Start a server :");
		txtStart.setBounds(24, 25, 129, 20);
		contentPane.add(txtStart);
		txtStart.setColumns(10);
		
		txtConnect = new JTextField();
		txtConnect.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtConnect.setText("Connect an existing server :");
		txtConnect.setBounds(24, 103, 244, 20);
		contentPane.add(txtConnect);
		txtConnect.setColumns(10);
		
		txtIpServer = new JTextField();
		txtIpServer.setFont(new Font("Tahoma", Font.BOLD, 16));
		txtIpServer.setText("IP server :");
		txtIpServer.setBounds(24, 145, 96, 20);
		contentPane.add(txtIpServer);
		txtIpServer.setColumns(10);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart_clic(); // Appel de la méthode btnStart_clic
			}
		});
		btnStart.setBackground(SystemColor.activeCaption);
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBounds(326, 22, 120, 23);
		contentPane.add(btnStart);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect_clic(); // Appel de la méthode btnConnect_clic
			}
		});
		btnConnect.setBackground(SystemColor.activeCaption);
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnConnect.setBounds(326, 144, 120, 23);
		contentPane.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBackground(SystemColor.activeCaption);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnExit.setBounds(326, 203, 120, 23);
		contentPane.add(btnExit);
		
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1");
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtIP.setBounds(130, 147, 138, 20);
		contentPane.add(txtIP);
		txtIP.setColumns(10);
	}
}
