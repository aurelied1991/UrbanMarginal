package modele;

import javax.swing.JPanel;
import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 * Cette classe hérite de la classe abstraite Jeu et implémente la logique spécifique pour le côté client d'un jeu en réseau
 * Elle gère les connexions avec le serveur, la réception des données et leur traitement, ainsi que l'envoi de messages
 */
public class JeuClient extends Jeu implements Global{
	
	/**
	 * Objets de connexion pour communiquer avec le serveur (stocke la connexion au serveur)
	 * La connexion est utilisée pour établir une communication avec le serveur distant
	 */
	private Connection connection;
	
	/**
	 * Booléen qui permet de savoir si les murs ont été initialisés
	 * Utilisé pour éviter d'ajouter plusieurs fois les murs dans l'arène
	 */
	private Boolean mursOk = false;
	
	/**
	 * Constructeur de la classe JeuClient
	 * Ce constructeur appelle le constructeur parent pour initialiser le contrôleur, puis il enregistre l'instance du contrôleur pour gérer les interactions
	 * @param controle L'instance du contrôleur pour gérer la communication avec le serveur
	 */
	public JeuClient(Controle controle) {
		super.controle = controle; // Le contrôleur est transmis à la classe mère
	}
	
	/**
	 * Méthode pour associer une connexion au client
	 * Cette méthode est appelée lors de l'établissement de la connexion avec le serveur
	 * @param connection La connexion établie avec le serveur
	 */
	@Override
	public void connexion(Connection connection) {
		this.connection = connection; // Stocke la connexion reçue dans l'objet connection
	}
	
	/**
	 * Méthode qui gère la réception des messages envoyés par le serveur
	 * Elle traite deux types d'informations : JPanel pour ajouter ou modifier l'état du jeu (murs et jeu), String pour modifier le contenu du tchat
	 * @param connection La connexion d'où provient l'information
	 * @param info L'information reçue, de type JPanel ou String
	 */
	@Override
	public void reception(Connection connection, Object info) {
		//Test pour vérifier si info recu en paramètre est bien une instance JPanel
		if(info instanceof JPanel) {
			if(!this.mursOk) {
				// Si les murs n'ont pas encore été ajoutés, les ajouter à l'arène
				this.controle.evenementJeuClient(AJOUTPANELMURS, info);
				this.mursOk = true; // Met à jour le flag pour indiquer que les murs ont été ajoutés
			}else {
				// Si les murs sont déjà ajoutés, on modifie l'état du jeu en fonction du nouveau JPanel reçu
				this.controle.evenementJeuClient(MODIFPANELJEU, info);
			}
		} else if(info instanceof String) {
			//Si ce qui est reçu est de type String, demander au contrôleur de modifier le contenu du tchat (ordre "modif tchat") en lui donnant aussi l'information reçue.
			this.controle.evenementJeuClient(MODIFTCHAT, info);
		}
	}
	
	/**
	 * Méthode appelée lors de la déconnexion du client
	 * Elle affiche un message dans la console pour indiquer que la déconnexion a réussi
	 */
	@Override
	public void deconnexion() {
		System.out.println("Déconnexion réussie"); // Affiche un message pour informer que la déconnexion est réussie
	}
	
	/**
	 * Méthode qui permet d'envoyer une information au serveur
	 * Elle utilise la méthode envoi définie dans la classe parente (Jeu) en précisant la connexion client
	 * @param info L'information à envoyer au serveur
	 */
	public void envoi(String info) {
		super.envoi(this.connection, info); // Appelle la méthode envoi de la classe parente avec la connexion client
	}
}
