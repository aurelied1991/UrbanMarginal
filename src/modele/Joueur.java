package modele;
import java.awt.Font;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;
/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global {

	/**
	 * pseudo saisi
	 */
	private String pseudo ;
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 * numéro d'�tape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * la boule du joueur
	 */
	private Boule boule ;
	
	/**
	* vie restante du joueur
	*/
	private int vie ; 
	/**
	* tourné vers la gauche (0) ou vers la droite (1)
	*/
	private int orientation ;
	/**
	 * message affiché sous le personnage (avec pseudo et vie)
	 */
	private JLabel message;
	
	
	/**
	 * Constructeur récupératon de jeuServeur et initialisaton de certaines propriétés
	 * @param jeuServeur instance de JeuServeur pour lui envoyer des informations
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE;
		this.etape = 1;
		this.orientation = DROITE;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 * @param pseudo nom du joueur
	 * @param numPerso numéro du personnage
	 * @param lesJoueurs collection qui contient tous les joueurs
	 * @param lesMurs collection qui contient tous les murs
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
		// création du label du personnage à partir de la classe mere
		super.jLabel = new JLabel();
		// création du label du message sous le personnage pour enregistrer le message (celui-ci doit être centré horizontalement et avec la font "Dialog" de taille 8)
		this.message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		// appel de première position pour calculer la première position du personnage
		this.premierePosition(lesJoueurs, lesMurs);
		// demande d'ajout du personnage et du message dans l'arène du serveur
		this.jeuServeur.ajoutJLabelJeuArene(jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(message);
		// demande d'affichage du personnage en appelant la méthode affiche qui doit recevoir en paramètre l'état du personnage (de type String, qui contiendra "marche", "touche" ou "mort") et l'étape dans cet état (de type int). Lors de cet appel, il faut envoyer "marche" et etape
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur), on boucle tant que c'est le cas
	 * @param lesJoueurs collection qui contient tous les joueurs
	 * @param lesMurs collection qui contient tous les murs
	 */
	private void premierePosition(Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO)) ;
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE)) ;
		}while(this.toucheJoueur(lesJoueurs) || this.toucheMur(lesMurs));
	}
	
	/**
	 * Affiche le personnage et son message
	 * @param etat etat du personnage (marche, touche ou mort)
	 * @param etape etape dans le mouvement du personnage
	 */
	public void affiche(String etat, int etape) {
		// positionnement du personnage et affectation de la bonne image
		super.jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		// Chemin vers l'image du personnage
	    String chemin = "media/personnages/perso" + this.numPerso + etat + etape + "d" + this.orientation + EXTFICHIERPERSO;
	    File file = new File(chemin);

	    // Vérifier si l'image existe
	    if (!file.exists()) {
	        System.out.println("⚠️ Fichier introuvable : " + file.getAbsolutePath());
	    } else {
	        super.jLabel.setIcon(new ImageIcon(file.getAbsolutePath()));
	    }
		// positionnement et remplissage du message sous le perosnnage
		this.message.setBounds(posX-10, posY+HAUTEURPERSO, LARGEURPERSO+10, HAUTEURMESSAGE);
		this.message.setText(pseudo+" : "+vie);
		// demande d'envoi à tous des modifications d'affichage
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action() {
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private void deplace() { 
	}

	/**
	 * Contrôle si le joueur touche un des autres joueurs
	 * @param lesJoueurs collection qui contient tous les joueurs
	 * @return true si deux joueurs se touchent
	 */
	private Boolean toucheJoueur(Collection<Joueur> lesJoueurs) {
		for(Joueur unJoueur : lesJoueurs) {
			if(!this.equals(unJoueur)) {
				if(super.toucheObjet(unJoueur)) {
					return true;	
				}		
			}
		}
		return false;
	}

	/**
	* Contrôle si un joueur touche un des murs
	* @param lesMurs collection qui contient tous les murs
	* @return true si un joueur touche un mur
	*/
	private Boolean toucheMur(ArrayList<Mur> lesMurs) {
		for(Mur unMur : lesMurs) {
			if(super.toucheObjet(unMur)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return null;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}
	
}
