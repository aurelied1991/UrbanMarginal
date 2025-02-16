package modele;
import controleur.Controle;
import outils.connexion.Connection;


/**
 * Informations et méthodes communes aux jeux client et serveur
 * Cette classe sert de base pour un jeu en réseau en définissant les méthodes essentielles pour la communication entre les clients et le serveur
 * Elle est abstraite, donc elle ne peut pas être instanciée directement, mais elle sera étendue par des classes concrètes (client ou serveur)
 */
public abstract class Jeu {
	/**
	 * Déclaration d'une propriété protégée de type Controle pour communiquer avec le contrôleur
	 * Le contrôleur gère la logique du jeu et coordonne les actions entre la vue et le modèle
	 */
	protected Controle controle;
	
	/**
	 * Constructeur par défaut de la classe Jeu.
	 * Il initialise simplement la classe sans effectuer d'action spécifique.
	 */
	public Jeu() {
	    // Constructeur vide
	}
	
	/**
	 * Méthode abstraite pour établir une connexion avec un ordinateur distant
	 * Cette méthode sera implémentée dans les sous-classes (client ou serveur) pour gérer la connexion spécifique
	 * @param connection Objet de type Connection qui permettra d'établir la connexion réseau.
	 */
	public abstract void connexion(Connection connection) ;
	
	/**
	 * Méthode abstraite pour recevoir des données depuis un ordinateur distant
	 * Cette méthode sera implémentée dans les sous-classes pour gérer les données reçues
	 * @param connection : object de connexion d'où provient l'information
	 * @param info L'information reçue de l'ordinateur distant
	 */
	public abstract void reception(Connection connection, Object info) ;
	
	/**
	 * Méthode abstraite pour fermer proprement la connexion avec l'ordinateur distant
	 * Cette méthode sera implémentée dans les sous-classes pour gérer la déconnexion
	 */
	public abstract void deconnexion() ;
	
	/**
	 * Envoi d'une information vers un ordinateur distant via le contrôleur
	 * Le contrôleur s'occupe de transmettre les données via la connexion
	 * @param connection Objet de connexion pour accéder à l'ordinateur distant et envoyer des informations
	 * @param info L'information à envoyer à l'ordinateur distant
	 */
	public void envoi(Connection connection, Object info) {
		this.controle.envoi(connection, info); // Appel de la méthode 'envoi' du contrôleur pour envoyer les données.
	}
	
}
