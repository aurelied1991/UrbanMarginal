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

/**
 * Fenêtre de démarrage du jeu. Cette classe représente l'interface graphique du jeu où l'utilisateur peut démarrer un serveur, se connecter à un serveur existant ou quitter le jeu. */
public class DemarrageJeu extends JFrame {

	/**
	 * Identifiant unique pour la sérialisation de la classe
	 */
	private static final long serialVersionUID = 1L; 
	/**
	 * Panneau principal où tous les composants graphiques (boutons, champs de texte, etc.) seront ajoutés
	 */
	private JPanel contentPane;  
	/**
	 * Champ de texte pour que l'utilisateur puisse entrer l'adresse IP du serveur
	 */
	private JTextField txtIP; 
	/**
	 * Instance du contrôleur qui gère la logique du jeu
	 */
	private Controle controle; 
	
	/**
	 * Méthode appelée lors du clic sur le bouton Start pour lancer le jeu
	 * Lorsque l'utilisateur clique sur "Start", cette méthode envoie un événement "serveur" au contrôleur pour lancer un serveur de jeu
	 */
	private void btnStart_clic() {
		// Envoie de l'événement au contrôleur pour démarrer un serveur
		this.controle.evenementEntreeJeu("serveur");
	}
	
	/**
	 * Méthode appelée lors du clic sur le bouton Connect pour se connecter à un serveur 
	 * Lors du clic sur "Connect", cette méthode récupère l'adresse IP entrée par l'utilisateur dans le champ de texte et envoie cette information au contrôleur pour se connecter à un serveur existant
	 */
	private void btnConnect_clic() {
		// Récupération de l'adresse IP du serveur dans le champ de texte et envoi au contrôleur
        this.controle.evenementEntreeJeu(this.txtIP.getText());
	}	
	
	/**
	 * Méthode appelée lors du Clic sur le bouton Exit pour quitter le jeu
	 * Lors du clic sur "Exit", cette méthode ferme l'application
	 */
	private void btnExit_clic() {
		// Quitte l'application
		System.exit(0);
	}

	/**
	 * Constructeur qui crée la fenêtre de démarrage du jeu
	 * @param controle Instance du contrôleur pour gérer les événements
	 */
	public DemarrageJeu(Controle controle) {
		this.setVisible(true);  // Affiche la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ferme l'application lors de la fermeture de la fenêtre
		setBounds(100, 100, 548, 301); // Définit la taille et la position de la fenêtre
		contentPane = new JPanel(); // Création d'un nouveau panneau
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // Définit une bordure vide pour le panneau principal

		setContentPane(contentPane); // Définit le panneau principal comme conteneur pour la fenêtre
		contentPane.setLayout(null); // Utilisation d'un layout null, c'est-à-dire un placement manuel des composants
		
		// Création du bouton "Start"
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart_clic(); // Appel de la méthode pour démarrer le jeu
			}
		});
		btnStart.setBackground(SystemColor.activeCaption); // Définir la couleur de fond
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16)); // Définir la police et la taille du texte
		btnStart.setBounds(326, 22, 120, 23); // Positionnement du bouton dans la fenêtre
		contentPane.add(btnStart); // Ajout du bouton au panneau
		
		// Création du bouton "Connect"
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect_clic(); // Appel de la méthode pour se connecter à un serveur
			}
		});
		btnConnect.setBackground(SystemColor.activeCaption); // Définir la couleur de fond
		btnConnect.setFont(new Font("Tahoma", Font.BOLD, 16)); // Définir la police et la taille du texte
		btnConnect.setBounds(326, 144, 120, 23); // Positionnement du bouton
		contentPane.add(btnConnect); // Ajout du bouton au panneau
		
		// Création du bouton "Exit"
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_clic(); // Appel de la méthode pour quitter le jeu
			}
		});
		btnExit.setBackground(SystemColor.activeCaption); // Définir la couleur de fond
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 16)); // Définir la police et la taille du texte
		btnExit.setBounds(326, 203, 120, 23); // Positionnement du bouton
		contentPane.add(btnExit); // Ajout du bouton au panneau
		
		// Création du champ de texte pour l'IP du serveur
		txtIP = new JTextField();
		txtIP.setText("127.0.0.1"); // Valeur par défaut de l'IP
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 16)); // Définir la police et la taille du texte
		txtIP.setBounds(130, 147, 138, 20); // Positionnement du champ de texte
		contentPane.add(txtIP); // Ajout du champ de texte au panneau
		txtIP.setColumns(10); // Définir le nombre de colonnes visibles dans le champ de texte
		
		// Création des étiquettes (labels) pour le texte descriptif
		JLabel lblNewLabel = new JLabel("Start a server : ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16)); // Police et taille
		lblNewLabel.setBounds(24, 26, 129, 14); // Positionnement
		contentPane.add(lblNewLabel); // Ajout de l'étiquette au panneau
		
		JLabel lblNewLabel_1 = new JLabel("Connect an existing server :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16)); // Police et taille
		lblNewLabel_1.setBounds(24, 107, 244, 14); // Positionnement
		contentPane.add(lblNewLabel_1); // Ajout de l'étiquette au panneau
		
		JLabel lblNewLabel_2 = new JLabel("IP server :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16)); // Police et taille
		lblNewLabel_2.setBounds(24, 147, 84, 17); // Positionnement
		contentPane.add(lblNewLabel_2); // Ajout de l'étiquette au panneau
		
		// Récupération de l'instance du contrôleur pour gérer les événements
		this.controle = controle;
	}
}
