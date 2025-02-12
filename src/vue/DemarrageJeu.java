package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import controleur.Controle;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class DemarrageJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIP;
	private Controle controle; // Déclaration de la variable d'instance
	
	
	/**
	 * clic sur le bouton Start pour lancer le jeu
	 */
	private void btnStart_clic() {
		// Lors du clic sur bntStart, envoie "serveur" au contrôleur pour démarrer un serveur
		this.controle.evenementEntreeJeu("serveur");
	}
	
	/**
	 * clic sur le bouton Connect pour se connecter à un serveur 
	 */
	private void btnConnect_clic() {
		// Passe par Controle pour savoir quelle fenêtre ouvrir et fermer l'actuelle et récup
        this.controle.evenementEntreeJeu(this.txtIP.getText());
	}	
	
	/**
	 * Clic sur le bouton Exit pour quitter le jeu
	 */
	private void btnExit_clic() {
		System.exit(0);
	}

	/**
	 * Create the frame.
	 * @param controle 
	 */
	public DemarrageJeu(Controle controle) {
		this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
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
				btnExit_clic();
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
		
		JLabel lblNewLabel = new JLabel("Start a server : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(24, 26, 129, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Connect an existing server :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(24, 107, 244, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("IP server :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_2.setBounds(24, 147, 84, 17);
		contentPane.add(lblNewLabel_2);
		
		// récupération de l'instance de Controle
		this.controle = controle;
	}
}
