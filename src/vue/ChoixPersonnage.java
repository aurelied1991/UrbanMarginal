package vue;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import controleur.Controle;
import outils.son.Son;
import controleur.Global;


/**
 * Frame du choix  du joueur
 * Cette classe gère la fenêtre où l'utilisateur choisit son personnage et entre son pseudo avant de commencer à jouer
 */
public class ChoixPersonnage extends JFrame implements Global{
	/**
	 * Identifiant unique pour la sérialisation de la classe
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Nombre de personnages disponibles à choisir
	 */
	private static final int NBPERSOS = 3; // 
	/**
	 * Panneau principal de la fenêtre
	 */
	private JPanel contentPane; // 
	/**
	 * Champ de texte pour saisir le pseudo
	 */
	private JTextField txtPseudo; // 
	/**
	 * Label qui affichera l'image du personnage choisi
	 */
	private JLabel lblAffichagePerso; // 
	/**
	 * Référence au contrôleur pour communiquer avec la logique du jeu
	 */
	private Controle controle; // 
	/**
	 * Numéro du personnage actuellement sélectionné
	 */
	private int numPerso; // 
	/**
	 * Son au démarrage du jeu
	 */
	private Son welcome;
	/**
	 * Son sur la flèche précédent
	 */
	private Son precedent;
	/**
	 * Son sur la flèche suivant
	 */
	private Son suivant;
	/**
	 * Son sur le clic Go
	 */
	private Son go;
	
	/**
	 * Clic sur la flèche de droite pour afficher le personnage suivant
	 */
	public void lblFlecheDroite_clic() {
		numPerso = (numPerso%NBPERSOS)+1 ; // Incrémente le numéro du personnage, boucle entre 1 et NBPERSOS
		affichePerso(); // Affiche le personnage sélectionné
		suivant.play();
	}
	
	/**
	 * Clic sur la flèche de gauche pour afficher le personnage précédent
	 */
	public void lblFlecheGauche_clic() {
		numPerso = ((numPerso+1)%NBPERSOS)+1; // Décrémente le numéro du personnage, boucle entre 1 et NBPERSOS
		affichePerso(); // Affiche le personnage sélectionné
		precedent.play();
	}
	
	/**
	 * Clic sur GO pour envoyer les informations et vérifier qu'un pseudo est bien entré
	 */
	private void lblGo_clic() {
		// Vérifie que l'utilisateur a bien saisi un pseudo
		if(this.txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire"); // Affiche un message si le pseudo est vide
			this.txtPseudo.requestFocus(); // Met le focus sur le champ de texte pour corriger l'erreur
		} else {
			// Envoie le pseudo et le personnage choisi au contrôleur
			this.controle.evenementChoixPersonnage(this.txtPseudo.getText(), numPerso);
			go.play();
		}
	}
	
	/**
	 * Affichage du personnage correspondant au numéro numPerso
	 */
	public void affichePerso() {
		// Définit le chemin de l'image du personnage en fonction de numPerso
		String chemin ="media/personnages/perso" + this.numPerso+"marche"+"1"+"d"+"1"+".gif";
		//URL resource = getClass().getClassLoader().getResource(chemin);
		// Charge l'image et l'affiche dans le label
		lblAffichagePerso.setIcon(new ImageIcon(chemin));		
		//this.lblAffichagePerso.setIcon(new ImageIcon(resource));
	}
	
	/**
	 * Change le curseur de la souris en curseur classique (flèche)
	 */
	public void sourisNormale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * Change le curseur de la souris en forme de doigt (pointeur)
	 */
	public void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Constructeur qui crée la fenêtre de choix du personnage
	 * @param controle Référence au contrôleur pour interagir avec la logique du jeu
	 */
	public ChoixPersonnage(Controle controle) {
		// Définit la taille préférée de la fenêtre selon son contenu
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
	    this.pack(); // Ajuste la taille de la fenêtre
		this.setResizable(false); // Interdiction de changer la taille
		 
		setTitle("Choice"); // Définit le titre de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ferme l'application quand on ferme la fenêtre
		contentPane = new JPanel(); // Crée le panneau de contenu
		setContentPane(contentPane); // Définit le panneau de contenu pour la fenêtre
		contentPane.setLayout(null); // Utilise un layout nul pour un placement personnalisé des composants
		
		lblAffichagePerso = new JLabel(""); // Crée un label pour afficher le personnage
		lblAffichagePerso.setBounds(163, 129, 79, 92); // Définit la position et la taille du label
		lblAffichagePerso.setHorizontalAlignment(SwingConstants.CENTER); // Centre l'image dans le label
		contentPane.add(lblAffichagePerso); // Ajoute le label au panneau de contenu
		
		// Gestion de la flèche gauche pour changer de personnage (clic et apparence du curseur)
		JLabel lblFlecheGauche = new JLabel("");
		lblFlecheGauche.setBounds(54, 140, 56, 53); // Positionne la flèche gauche
		lblFlecheGauche.setOpaque(false); // Rendre l'arrière-plan transparent
		contentPane.add(lblFlecheGauche); // Ajoute la flèche au panneau de contenu
		lblFlecheGauche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblFlecheGauche_clic(); // Gère le clic sur la flèche gauche
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt(); // Change le curseur en doigt quand la souris entre dans la zone
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale(); // Restaure le curseur classique quand la souris sort de la zone
			}
		});
	
		
		// Gestion de la flèche droite pour changer de personnage (clic et apparence du curseur)
		JLabel lblFlecheDroite = new JLabel("");
		lblFlecheDroite.setBounds(286, 140, 56, 53); // Positionne la flèche droite
		lblFlecheDroite.setOpaque(false); // Rendre l'arrière-plan transparent
		contentPane.add(lblFlecheDroite); // Ajoute la flèche au panneau de contenu
		lblFlecheDroite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblFlecheDroite_clic(); // Gère le clic sur la flèche droite
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt(); // Change le curseur en doigt quand la souris entre dans la zone
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale(); // Restaure le curseur classique quand la souris sort de la zone
			}
		});
		
		// Gestion du champ de texte pour saisir le pseudo
		txtPseudo = new JTextField();
		txtPseudo.setFont(new Font("Tahoma", Font.PLAIN, 16)); // Définit la police du texte
		txtPseudo.setText(""); // Vide le champ de texte
		txtPseudo.setBounds(141, 243, 121, 26); // Positionne le champ de texte
		contentPane.add(txtPseudo); // Ajoute le champ de texte au panneau de contenu
		txtPseudo.setColumns(10); // Définit le nombre de colonnes du champ de texte
		
		// Gestion du bouton GO (clic et apparence du curseur)
		JLabel lblGo = new JLabel("");
		lblGo.setBounds(296, 192, 87, 77); // Positionne le bouton GO
		lblGo.setOpaque(false); // Rendre l'arrière-plan transparent
		contentPane.add(lblGo); // Ajoute le bouton GO au panneau de contenu
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic(); // Gère le clic sur GO
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt(); // Change le curseur en doigt quand la souris entre dans la zone
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale(); // Restaure le curseur classique quand la souris sort de la zone
			}
		});
		
		// Ajout du fond de la fenêtre (image de fond)
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275); // Positionne le fond
		String chemin = "media/fonds/fondchoix.jpg"; // Chemin de l'image de fond
		lblFond.setIcon(new ImageIcon(chemin));	// Charge l'image de fond
		contentPane.add(lblFond); // Ajoute l'image de fond au panneau de contenu
		
		// Récupère l'instance de Controle pour la gestion du jeu
		this.controle = controle;
				
		// Affiche le premier personnage au démarrage
		this.numPerso = 1;
		this.affichePerso();
		
		// récupération des sons
		precedent = new Son(getClass().getClassLoader().getResource(SONPRECEDENT));
		suivant = new Son(getClass().getClassLoader().getResource(SONSUIVANT));
		go = new Son(getClass().getClassLoader().getResource(SONGO));
		welcome = new Son(getClass().getClassLoader().getResource(SONWELCOME));
		welcome.play();

		// Positionne le focus sur la zone de saisie pour le pseudo
		txtPseudo.requestFocus();
	}
}
