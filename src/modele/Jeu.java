package modele;
import controleur.Controle;
import outils.connexion.Connection;

/**
 * Informations et méthodes communes aux jeux client et serveur (classe quie sert de base pour un jeu en réseau en définissant les méthodes essentielles pour la communication entre les clients et le serveur)
 *
 */

public abstract class Jeu {
	/**
	 * Déclaration d'une propriété protégée de type Controle pour communiquer avec le contrôleur
	 */
	protected Controle controle;
	
	/**
	 * Réception d'une connexion (pour établir une connexion avec un ordinateur distant)
	 * @param connection TODO 
	 */
	public abstract void connexion(Connection connection) ;
	
	/**
	 * Pour recevoir des données depuis l'ordinateur distant
	 * @param connection : object de connexion d'où provient l'information
	 * @param info information recu 
	 */
	public abstract void reception(Connection connection, Object info) ;
	
	/**
	 * Déconnexion de l'ordinateur distant, pour fermer la connexion proprement
	 */
	public abstract void deconnexion() ;
	
	/**
	 * Envoi d'une information vers un ordinateur distant via le controleur
	 * @param connection objet de connexion pour accéder à l'ordinateur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		this.controle.envoi(connection, info);
	}
	
}
