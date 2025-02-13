package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 *
 */
public class JeuClient extends Jeu {
	
	/**
	 * objet de connexion pour communiquer avec le serveur (stocke la connexion au serveur)
	 */
	private Connection connection;
	
	/**
	 * Constructeur qui appelle le constructeur parent (super.controle = controle;) et qui stocke le contrôleur pour gérer les interactions
	 */
	public JeuClient(Controle controle) {
		super.controle = controle;
	}
	
	/**
	 * Méthode qui associe la connexion reçue au client
	 */
	@Override
	public void connexion(Connection connection) {
		this.connection = connection;
	}
	
	/**
	 * Méthode qui servira à gérer les messages envoyés par le serveur
	 */
	@Override
	public void reception(Connection connection, Object info) {
	}
	
	/**
	 * Méthode qui affiche un message lors de la deconnexion
	 */
	@Override
	public void deconnexion() {
		System.out.println("Déconnexion réussie");
	}
	
	/**
	 * Envoi d'une information vers le serveur
	 * fais appel une fois à la méthode envoi définie dans la classe Jeu mais an précisant la connexion client
	 */
	public void envoi(String info) {
		super.envoi(this.connection, info);
	}

}
