package vue;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import controleur.Global;
import controleur.Controle;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

/**
 * Cette classe gère l'affichage de l'arène
 */
public class Arene extends JFrame implements Global{

	/**
	 * Identifiant unique pour la sérialisation de la classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * panel général où les composants graphiques sont ajoutés
	 */
	private JPanel contentPane; 
	/**
	 * Panel contenant les murs
	 */
	private JPanel jpnMurs; 
	/**
	 * Panel contenant les éléments du jeu (joueurs, objets, etc.)
	 */
	private JPanel jpnJeu; 
	/**
	 * Zone de saisie où l'utilisateur peut taper des messages dans le tchat
	 */
	private JTextField txtSaisie; 
	/**
	 * Zone d'affichage du tchat
	 */
	private JTextArea txtChat ; 
	/**
	 * Instance du contrôleur pour la communication avec la logique du jeu
	 */
	private Controle controle; 
	/**
	 * Permet de savoir si c'est une arène client ou serveur
	 */
	private Boolean client; 


	/**
	 * Getter pour récupérer le panel des murs
	 * @return the jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}

	/**
	 * Setter pour ajouter un panel de murs à l'arène
	 * @param jpnMurs the jpnMurs to set
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs); // Ajout du panel des murs
		this.jpnMurs.repaint(); // Repeinture du panel pour prendre en compte les changements
	}
	
	/**
	 * Getter pour récupérer le panel de jeu
	 * @return the jpnJeu
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}
	
	/**
	 * Setter pour ajouter un panel de jeu à l'arène 
	 * @param jpnJeu jPanel
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll(); // Retire tous les composants précédents du panel
		this.jpnJeu.add(jpnJeu); // Ajoute le nouveau panel de jeu
		this.jpnJeu.repaint(); // Repeinture du panel pour les nouveaux ajouts
		this.contentPane.requestFocus(); // Donne le focus à contentPane pour que les flèches soient prises en compte , à la fin du setter car c'est la dernière action faite avant que le client ait la main
	}
	
	/**
	 * Retourne le texte de la zone de chat
	 * @return txtChat
	 */
	public String getTxtChat() {
		return txtChat.getText();
	}
	
	/**
	 * Permet de mettre à jour le texte de la zone de chat
	 * @param txtChat chat
	 */
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat); // Met à jour le contenu de la zone de chat
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength()); // Positionne le curseur à la fin du texte et permet que l'ascenseur reste toujours en bas afin de voir la dernière phrase affichée
	}
	
	/**
	 * Méthode pour ajouter un mur (JLabel représentant un mur) au panel des murs
	 * @param unMur mur
	 */
	public void ajoutMurs(Object unMur) {
	    jpnMurs.add((JLabel)unMur); // Ajout du mur au panel
	    jpnMurs.repaint(); // Repeinture du panel pour afficher le mur ajouté
	}
	
	/**
	 * Méthode pour ajouter un joueur (ou un élément de jeu) dans le panel du jeu
	 * @param unJLabel le label à ajouter
	 */
	public void ajoutJLabelJeu(JLabel unJLabel) {
		this.jpnJeu.add(unJLabel); // Ajout du joueur (ou objet) dans le panel
		this.jpnJeu.repaint(); // Repeinture du panel pour afficher l'élément ajouté
	}
	
	/**
	 * Ajoute un message à la fin du chat et fait défiler la zone de texte
	 * @param phrase phrase à insérer
	 */
	public void ajoutTchat(String phrase) {
		this.txtChat.setText(this.txtChat.getText()+phrase+"\r\n"); // Ajout du texte et retour à la ligne
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength()); // Positionnement du curseur à la fin
	}
	
	/**
	 * Méthode pour gérer l'événement lorsqu'une touche est pressée dans la zone de saisie du chat
	 * @param e la touche sur laquelle appuyée pour valider
	 */
	public void txtSaisie_KeyPressed(KeyEvent e) {
		// Vérifie si la touche pressée est la touche "Entrée"
		if(e.getKeyCode() == KeyEvent.VK_ENTER) { 
			//Vérifier que la zone de saisie n'est pas vide
			if(!this.txtSaisie.getText().equals("")) {
				//Solliciter le contrôleur pour envoyer la phrase saisie : Le contrôleur n'étant pas accessible dans Arene, il faut procéder comme pour les autres frames (en le recevant dans le constructeur). Après l'appel d'evenementArene, il faut vider la zone de saisie.
				this.controle.evenementArene(this.txtSaisie.getText()); // Demande au contrôleur de traiter l'événement
				this.txtSaisie.setText(""); // Vide la zone de saisie après l'envoi
			}
			this.contentPane.requestFocus(); // Redonner le focus à contentPane après l'envoi du texte
		}
	}
	
	/**
	 * Événement déclenché lorsqu'une touche est pressée sur le panneau général
	 * @param e informations sur la touche pressée
	 */
	public void contentPane_KeyPressed(KeyEvent e) {
		int touche = -1; // Initialisation d'une variable pour stocker la touche appuyée, avec une valeur par défaut invalide
		// Récupère le code de la touche pressée et teste sa valeur
		switch(e.getKeyCode()) {
		// Si la touche pressée est la flèche gauche
		case KeyEvent.VK_LEFT :
		// Si la touche pressée est la flèche droite
		case KeyEvent.VK_RIGHT :
		// Si la touche pressée est la flèche haute
		case KeyEvent.VK_UP :
		// Si la touche pressée est la flèche bas
		case KeyEvent.VK_DOWN :
			// On stocke la valeur de la touche pressée
			touche = e.getKeyCode();
			break;
		}
		// Si une touche valide (flèche directionnelle) a été pressée...
		if(touche != -1) {
			// On transmet l'événement au contrôleur du jeu
			this.controle.evenementArene(touche); 
		}
	}

	/**
	 * Constructeur de la classe Arene en initialisant tous les composants graphiques et en configurant les paramètres de la fenêtre
	 * @param controle controle
	 * @param typeJeu type du jeu
	 */
	public Arene(Controle controle, String typeJeu) {
		this.client = typeJeu.equals(CLIENT); // Détermine si l'arène est en mode client ou serveur
		
		// Configuration de la taille de la fenêtre en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(LARGEURARENE, HAUTEURARENE + 25 + 140));
		this.pack();
		this.setResizable(false); // La taille de la fenêtre ne peut pas être changée
		
		// Configuration de la fenêtre
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel(); 
		// Mise en écoute de touche sur contentPane, comme pour txtSaisie
		contentPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				contentPane_KeyPressed(e);
			}
		});
		setContentPane(contentPane);
		contentPane.setLayout(null);  // Utilisation d'un layout nul pour un placement manuel des composants
		
		// Initialisation du panel de jeu (où se déroulera l'action du jeu)
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnJeu.setOpaque(false); // Le panel est transparent
		jpnJeu.setLayout(null);	// Placement manuel des composants	
		contentPane.add(jpnJeu);
		
		// Initialisation du panel des murs
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false); // Le panel est transparent
		jpnMurs.setLayout(null); // Placement manuel des composants		
		contentPane.add(jpnMurs);
		
		// Si c'est un client, ajouter un champ de texte pour saisir des messages dans le chat. Le contrôleur n'étant pas accessible dans Arene, il faut procéder comme pour les autres frames (en le recevant dans le constructeur)
		if(this.client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					txtSaisie_KeyPressed(e); // Gestion de l'événement de touche pressée
				}
			});
			txtSaisie.setBounds(0, 600, 800, 25); // Positionnement du champ de texte
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10); // Taille du champ de texte
		}
		
		// Configuration du panel de chat (avec ascenseur vertical)
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, 800, 140); // Positionnement du panel de chat
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		// Si jamais le joueur clique sur la zone de tchat, pour redonner le focus à ContentPane s'il appuie sur une touche
		txtChat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				contentPane_KeyPressed(e);
			}
		});
		txtChat.setEditable(false); // Rendre la zone de chat non modifiable
		jspChat.setViewportView(txtChat); // Ajout de la zone de texte dans le scrollpane
		
		JLabel lblFond = new JLabel("");
		String chemin = "media/fonds/fondarene.jpg"; // Chemin de l'image de fond
		lblFond.setIcon(new ImageIcon(chemin));	// Définition de l'icône comme image de fond	 
		lblFond.setBounds(0, 0, 800, 600); // Positionnement de l'image de fond
		contentPane.add(lblFond);
		
		// Récupération de l'instance du contrôleur pour communiquer avec la logique du jeu
		this.controle = controle;
	}
}
