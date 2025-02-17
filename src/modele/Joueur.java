package modele;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import controleur.Global;

/**
 * Gestion des joueurs dans le jeu. Cette classe gère la création, l'affichage, les déplacements, les collisions et la gestion de la vie des joueurs dans l'arène
 */
public class Joueur extends Objet implements Global {

	/**
	 * Pseudo du joueur, utilisé pour identifier le joueur dans le jeu
	 */
	private String pseudo ;
	/**
	 * Numéro correspondant au personnage (avatar) du joueur, utilisé pour charger l'image du personnage
	 */
	private int numPerso ; 
	/**
	 * Instance de JeuServeur pour communiquer avec le serveur et envoyer/recevoir des informations
	 */
	private JeuServeur jeuServeur ;
	/**
	 * Numéro d'étape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * La boule du joueur, utilisée pour les actions comme les tirs
	 */
	private Boule boule ;
	/**
	* Vie restante du joueur, utilisée pour déterminer la santé du joueur dans le jeu
	*/
	private int vie ; 
	/**
	* L'orientation du joueur, 0 pour gauche et 1 pour droite
	*/
	private int orientation ;
	/**
	 * Message affiché sous le personnage (comprend le pseudo du joueur et sa vie restante)
	 */
	private JLabel message;
	
	
	/**
	 * Constructeur pour initialiser un joueur, en recevant l'instance de JeuServeur
	 * Cela permet de lier le joueur au serveur pour envoyer des informations.
	 * @param jeuServeur Instance de JeuServeur pour la communication avec le serveur
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		this.vie = MAXVIE; // Initialisation de la vie avec une valeur maximale
		this.etape = 1; // L'animation commence à l'étape 1
		this.orientation = DROITE; // L'orientation par défaut est vers la droite
	}
	
	/**
	 * Getter pour récupérer le pseudo du joueur
	 * @return pseudo du joueur
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	/**
	 * Getter sur l'orientation du joueur
	 * @return orientation
	 */
	public int getOrientation() {
		return orientation;
	}


	/**
	 * Initialisation du joueur avec un pseudo, un numéro de personnage, et une première position aléatoire
	 * Cette méthode met également en place l'affichage du joueur et de son message
	 * @param pseudo Nom du joueur
	 * @param numPerso Numéro du personnage (utilisé pour l'image du personnage)
	 * @param lesJoueurs Liste des autres joueurs présents dans le jeu
	 * @param lesMurs Liste des murs dans l'arène
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
		//Création du label pour afficher le personnage à partir de la classe mère
		super.jLabel = new JLabel();
		// Création du label pour afficher le message sous le personnage (pseudo et vie)
		this.message = new JLabel();
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("Dialog", Font.PLAIN, 8));
		// Création de la boule
		this.boule = new Boule(this.jeuServeur);
		// Calcul de la première position du personnage
		this.premierePosition(lesJoueurs, lesMurs);
		// Ajout du personnage, de la boule et du message dans l'arène du serveur
		this.jeuServeur.ajoutJLabelJeuArene(jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(message);
		this.jeuServeur.ajoutJLabelJeuArene(boule.getjLabel());
		// Affichage du personnage en état "marche" à l'étape 1
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Calcul de la première position aléatoire du joueur, tout en s'assurant que le joueur ne chevauche pas un autre joueur ou un mur. Si tel est le cas, on refait le calcul jusqu'à ce que la position soit valide
	 * @param lesJoueurs  Liste des joueurs
	 * @param lesMurs Liste des murs
	 */
	private void premierePosition(Collection lesJoueurs, Collection lesMurs) {
		// Initialisation des coordonnées du joueur
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		// Calcul de la position tant qu'elle touche un autre joueur ou un mur
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO)) ;
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE)) ;
		}while(toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null);
	}
	
	/**
	 * Affichage du personnage et de son message sous forme de label
	 * Cette méthode affecte également l'image à afficher en fonction de l'état du personnage
	 * @param etat L'état du personnage ("marche", "touche", "mort")
	 * @param etape L'étape dans l'animation de l'état
	 */
	public void affiche(String etat, int etape) {
		// Positionnement du personnage et affectation de l'image correspondante
		super.jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		// Définition du chemin vers l'image du personnage
	    String chemin = "media/personnages/perso" + this.numPerso + etat + etape + "d" + this.orientation + EXTFICHIERPERSO;
	    File file = new File(chemin);
	    super.jLabel.setIcon(new ImageIcon(file.getAbsolutePath()));
		// Positionnement du message sous le personnage
		this.message.setBounds(posX-10, posY+HAUTEURPERSO, LARGEURPERSO+10, HAUTEURMESSAGE);
		this.message.setText(pseudo+" : "+vie);
		// Envoi des modifications à tous les clients
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue, telle qu'un déplacement ou un tir de boule en fonction de la touche appuyée
	 * @param action action action a exécutée (déplacement ou tir de boule)
	 * @param lesJoueurs collection des joueurs présents dans l'arène
	 * @param lesMurs liste des murs présents dans l'arène
	 */
	public void action(Integer action, Collection lesJoueurs, Collection lesMurs) {
		switch(action) {
		// Cas selon la direction avec touche appuyée : gauche, droite, haut, bas
		case KeyEvent.VK_LEFT :
			orientation = GAUCHE; 
			posX = deplace(posX, action.intValue(), -PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_RIGHT :
			orientation = DROITE;
			posX = deplace(posX, action.intValue(), PAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_UP :
			posY = deplace(posY, action.intValue(), -PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_DOWN :
			posY = deplace(posY,  action.intValue(), PAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
			break;
		case KeyEvent.VK_SPACE:
			// Controler que la boule n'est pas visible, et dans ce cas appelé la méthode tireBoule  sur la propriété boule (avec comme paramètres : attaquant pour éventuellement incrémenter sa vie s'il touche un autre joueur, mais aussi avec la collection lesMurs pour gérer les collisions)
			if(!this.boule.getjLabel().isVisible()) {
				this.boule.tireBoule(this, lesMurs);
			}
			break;
		}
		//appel de la méthode affiche
		this.affiche(MARCHE, this.etape);
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private int deplace(int position, int action, int lepas, int max, Collection lesJoueurs, Collection lesMurs ) { 		
		int ancpos = position ;
		position += lepas ;
		position = Math.max(position, 0) ;
		position = Math.min(position,  max) ;
		if (action==KeyEvent.VK_LEFT || action==KeyEvent.VK_RIGHT) {
			posX = position ;
		}else{
			posY = position ;
		}
		// controle s'il y a collision, dans ce cas, le personnage reste sur place
		if (toucheCollectionObjets(lesJoueurs) != null || toucheCollectionObjets(lesMurs) != null) {
			position = ancpos ;
		}
		// passe à l'étape suivante de l'animation de la marche
		etape = (etape % NBETAPESMARCHE) + 1 ;
		return position ;
	}
	
	/**
	 * Gain de points de vie après avoir touché un autre joueur
	 */
	public void gainVie() {
		this.vie += GAIN;
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		this.vie = Math.max(0, this.vie - PERTE);
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return (this.vie == 0);
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
	}
	
}
