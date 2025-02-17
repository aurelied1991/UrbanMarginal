package modele;

import controleur.Controle;
import outils.connexion.Connection;
import controleur.Global;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JLabel;

/**
 * Gestion du jeu côté serveur qui gère plusieurs joueurs et la logique du jeu
 * Cette classe hérite de la classe Jeu et implémente l'interface Global
 */
public class JeuServeur extends Jeu implements Global{

	/**
	 * Liste contenant tous les murs du jeu. Chaque mur est un objet de la classe Mur
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	
	/**
	 * Dictionnaire de joueurs indexé sur leur objet de connexion (associe chaque connexion à un joueur et permet donc de savoir qui est qui en fonction de la connexion réseau)
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	
	/**
	 * Constructeur de la classe JeuServeur
	 * Le contrôleur est récupéré pour gérer les communications et les événements du jeu
	 * @param controle instance du contrôleur permettant les interactions
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Méthode appelée lorsqu'un joueur se connecte au serveur
	 * Elle crée un objet Joueur et l'ajoute au dictionnaire lesJoueurs
	 * @param connection la connexion du joueur
	 */
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}
	
	/**
	 * Méthode appelée lors de la réception d'un message du serveur. Elle découpe le message reçu et le traite en fonction de l'ordre
	 * Si l'ordre est "PSEUDO", le pseudo du joueur est récupéré et initialisé, et un message de bienvenue est envoyé au chat. Si l'ordre est "TCHAT", la phrase est ajoutée au chat avec le pseudo du joueur
	 * @param connection la connexion du joueur qui envoie le message
	 * @param info l'objet contenant le message reçu
	 */
	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(STRINGSEPARE);
		String ordre = infos[0];
		switch(ordre) {
		case PSEUDO :
			// Envoie des murs au client
			controle.evenementJeuServeur(AJOUTPANELMURS, connection);
			// Si ordre = PSEUDO alors on récupère le pseudo et le numéro du personnage, puis on initialise le personnaeg du joueur associé à la connexion
			String pseudo = infos[1];
			int numPerso = Integer.parseInt(infos[2]);
			// Initialise le personnage du joueur avec ses informations
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			//Pour pallier à un problème : avant, , en saisissant un message chez le nouveau client et en validant, alors la zone de tchat se rempli de tout l'historique. Petite astuce : dès qu'un client se connecte, le but est de le dire dans le tchat
			String premierMessage = "*** "+pseudo+" vient de se connecter ***";
			this.controle.evenementJeuServeur(AJOUTPHRASE, premierMessage);
			break;
		case TCHAT :
			// Afficher devant la phrase le pseudo de la personne suivi de '>' et récupérer le pseudo du joueur dont la connexion a été reçue en paramètre
			String phrase = infos[1];
			phrase = this.lesJoueurs.get(connection).getPseudo()+" > "+phrase;
			this.controle.evenementJeuServeur(AJOUTPHRASE, phrase);
			break;
			
		case ACTION:
			// Appeler la méthode action sur le joueur concerné  et envoyer en paramètre l'info recu mais aussi les joueurs et les murs pour gérer les collisioons
			Integer action = Integer.parseInt(infos[1]);
			this.lesJoueurs.get(connection).action(action, this.lesJoueurs.values(), this.lesMurs);
			break;
		}
	}
	
	/**
	 * Méthode appelée lors de la déconnexion d'un joueur
	 * Vide pour le moment mais devrait retirer un joueur du Hashtable lesJoueurs lors de sa deconnexion
	 */
	@Override
	public void deconnexion() {
	}
	
	/**
	 * Envoie un JLabel (représentant un élément du jeu, par exemple un mur) à l'arène
	 * @param jLabel l'élément graphique à ajouter à l'arène
	 */
	public void ajoutJLabelJeuArene(JLabel jLabel) {
		this.controle.evenementJeuServeur(AJOUTJLABELJEU, jLabel);
	}

	/**
	 * Envoie un message à tous les joueurs du jeu via leur connexion
	 * @param info l'information à envoyer à chaque joueur
	 */
	public void envoi(Object info) {
		// Envoie l'information à chaque joueur connecté
		for(Connection connection : this.lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}
	}

	/**
	 * Méthode pour générer les murs du jeu. Elle crée plusieurs murs et les ajoute à la liste des murs, puis envoie un JLabel pour chaque mur à l'arène.
	 */
	public void constructionMurs() {
		for(int k=0; k < NBMURS; k++) {
			Mur mur = new Mur();  // Création du Mur
	        lesMurs.add(mur);     // Ajout du Mur dans à la liste des murs
	        // Récupère le JLabel du mur
	        JLabel lblMur = mur.getjLabel();
	        // Ajoute le mur à l'arène
	        this.controle.evenementJeuServeur(AJOUTMUR, lblMur);
	    }
	}
	
	/**
	 * Envoie le panel de jeu à tous les joueurs connectés. Cette méthode itère sur les connexions et met à jour l'interface du jeu pour chaque joueur.
	 */
	public void envoiJeuATous() {
		// Boucle sur toutes les connexions et envoie le panel de jeu à chaque joueur. (récupérables avec la méthode keySet sur le dictionnaire)
		for(Connection connection : this.lesJoueurs.keySet()) {
			//pour chaque connexion, fait appel au contrôleur en envoyant cet ordre accompagné de la connexion du joueur
			this.controle.evenementJeuServeur(MODIFPANELJEU, connection);
		}
	}
	
}
