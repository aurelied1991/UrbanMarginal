package modele;

import controleur.Controle;
import outils.connexion.Connection;
import controleur.Global;

import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;

/**
 * Gestion du jeu côté serveur qui gère plusieurs joueurs et la logique du jeu
 * 
 */
public class JeuServeur extends Jeu implements Global{

	/**
	* Collection de murs qui stocke donc la liste des murs du jeu
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	
	/**
	 * Dictionnaire de joueurs indexé sur leur objet de connexion (associe chaque connexion à un joueur et permet donc de savoir qui est qui en fonction de la connexion réseau)
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	
	/**
	 * Constructeur qui récupère le contrôleur pour gérer la communication et les interactions
	 * @param controle instance du contrôleur pour les échanges
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Méthode qui, quand un joueur se connecte, crée un objet Joueur et on l'ajoute à lesJoueurs
	 */
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}
	
	/**
	 * Quand le serveur recoit un message, il transforme info en String et le découpe, puis il récupère "ordre" (le premier élément du tableau) et il vérifie si "ordre" est PSEUDO
	 */

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(STRINGSEPARE);
		String ordre = infos[0];
		switch(ordre) {
		case PSEUDO :
			//envoi des murs au client
			controle.evenementJeuServeur(AJOUTPANELMURS, connection);
			//si ordre = PSEUDO alors on récupère le pseudo et le numéro du personnage, puis on initialise le personnaeg du joueur associé à la connexion
			String pseudo = infos[1];
			int numPerso = Integer.parseInt(infos[2]);
			//La classe JeuServeur ne doit pas envoyer le dictionnaire mais juste les joueurs, en utilisant la méthode values sur le dictionnaire.
			this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
			break;
		}
	}
	
	/**
	 * Vide pour le moment mais devrait retirer un joueur du Hashtable lesJoueurs lors de sa deconnexion
	 */
	@Override
	public void deconnexion() {
	}
	
	/**
	 * Envoie comme ordre au controleur : "ajout jlabel jeu"
	 */
	public void ajoutJLabelJeuArene(JLabel jLabel) {
		this.controle.evenementJeuServeur(AJOUTJLABELJEU, jLabel);
		System.out.println("🛠️ JLabel ajouté à l'arène !");
	}

	/**
	 * Envoi d'une information vers tous les clients (tous les joueurs)
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */
	public void envoi() {
	}

	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for(int k=0; k < NBMURS; k++) {
			Mur mur = new Mur();  // Création du Mur
	        lesMurs.add(mur);     // Ajout du Mur dans la liste

	        // Récupération du JLabel du Mur
	        JLabel lblMur = mur.getjLabel();
	        
	        // Vérification que lblMur n'est pas null
	        if (lblMur != null) {
	            this.controle.evenementJeuServeur(AJOUTMUR, lblMur);
	        } else {
	            System.out.println("Erreur : Le JLabel est null.");
	        }
		}
	}
	
	/**
	 * Envoi du panel de jeu à tous les joueurs
	 */
	public void envoiJeuATous() {
		//boucle sur les connexions du dictionnaire lesJoueurs (récupérables avec la méthode keySet sur le dictionnaire)
		for(Connection connection : this.lesJoueurs.keySet()) {
			//pour chaque connexion, fait appel au contrôleur en envoyant cet ordre accompagné de la connexion du joueur
			this.controle.evenementJeuServeur(MODIFPANELJEU, connection);
		}
	}
	
}
