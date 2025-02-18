package controleur;
import modele.JeuServeur;
import modele.Jeu;
import modele.JeuClient;
import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixPersonnage;
import vue.DemarrageJeu;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Contrôleur principal du jeu, qui gère la logique de communication entre les différentes vues et les objets JeuServeur / JeuClient, en plus de gérer les connexions et les événements du jeu
 */
public class Controle implements AsyncResponse, Global {
	
	/**
	 * Fenêtres utilisées tout au long du jeu
	 * DemarrageJeu (où démarre le jeu)
	 */
	private DemarrageJeu frmDemarrageJeu ;
	/**
	 * Fenêtre Arene (où se passe l'action)
	 */
	private Arene frmArene;
	/**
	 * Fenêtre ChoixPersonnage (où on choisi son personnage et son pseudo)
	 */
	private ChoixPersonnage frmChoixPersonnage;
	
	/**
	 * Objet représentant le type du jeu : client ou serveur (jeuClient ou JeuServeur)
	 */
	private Jeu leJeu;
	
	/**
	 * Point d'entrée du programme, créer d'une instance du contrôleur et lance l'application
	 * @param args non utilisé
	 */
    public static void main(String[] args) {
        new Controle(); // Création de l'instance du contrôleur qui lance l'application
    }

    
    /**
     * Constructeur qui crée la fenêtre de démarrage du jeu (frmDemarrageJeu), la rend visible et attend les interactions de l'utilisateur
     */
    private Controle() {
    	    this.frmDemarrageJeu = new DemarrageJeu(this);
			this.frmDemarrageJeu.setVisible(true); // Affiche la fenêtre de démarrage
    }

	/**
	 * Gère les choix effectués dans la fenêtre de démarrage du jeu (soit serveur, soit client)
	 * @param info La chaîne de caractères indiquant le choix de l'utilisateur ("serveur" ou "client")
	 */
	public void evenementEntreeJeu(String info) {
		if(info.equals("serveur")) {
			//Si l'utilisateur choisit "serveur" en cliquant sur "Start", on initialise le serveur et l'objet JeuServeur
			new ServeurSocket(this, PORT); // Crée une connexion serveur
			this.leJeu = new JeuServeur(this); // Initialise un objet JeuServeur
			this.frmDemarrageJeu.dispose(); // Ferme la fenêtre de démarrage
			this.frmArene = new Arene(this, SERVEUR); // Crée la fenêtre de jeu en mode serveur
			((JeuServeur)this.leJeu).constructionMurs(); // transtypage de leJeu en JeuServeur et configure les murs dans le jeu
			this.frmArene.setVisible(true); // Affiche la fenêtre de jeu
		} else {
			//Si l'utilisateur choisit "client" en cliquant sur "Connect", une connexion client est établie via ClientSocket, mais sans initialiser le serveur
			new ClientSocket(this, info, PORT); // Crée une connexion client
		}
	}
	
	/**
	 * Méthode qui gère le choix du personnage et du pseudo par l'utilisateur, reçu depuis la fenêtre ChoixPersonnage
	 * @param pseudo Le pseudo du joueur
	 * @param numPerso Le numéro du personnage choisi par le joueur
	 */
	public void evenementChoixPersonnage(String pseudo, int numPerso) {
		this.frmChoixPersonnage.dispose(); // Ferme la fenêtre de choix de personnage
		this.frmArene.setVisible(true); // Affiche la fenêtre de jeu
		// Envoie le pseudo et le personnage choisi au serveur (ou au client selon le mode) en passant par la méthode envoi de la classe JeuClient. On transtype leJeu en JeuClient car c'est lui qui possède l'objet de type Connection pour communiquer avec l'ordinateur distant
		((JeuClient)this.leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+numPerso);

	}
	
	/**
	 * Gère les événements provenant du JeuServeur.
	 * @param ordre L'ordre à exécuter (par exemple, ajout d'un mur)
	 * @param info L'information à traiter
	 */
	public void evenementJeuServeur(String ordre, Object info) {
		switch(ordre) {
		case AJOUTMUR:
			 // Ajoute un mur dans l'arène (via un JLabel) via la méthode ajoutMurs
            if (info instanceof JLabel) {
                frmArene.ajoutMurs((JLabel) info);
            } else {
                System.out.println("Erreur : l'objet n'est pas un JLabel.");
            }
            break;
        // Envoie le panel des murs au client
		case AJOUTPANELMURS:
			this.leJeu.envoi((Connection)info, this.frmArene.getJpnMurs());
			break;
		// On transtype info en JLabel et on l'ajoute à l'arène via la méthode ajoutJLabelJeu
		case AJOUTJLABELJEU:
			this.frmArene.ajoutJLabelJeu((JLabel)info);
			break;
		// Envoie le panel du jeu au client
		case MODIFPANELJEU:
			this.leJeu.envoi((Connection) info, this.frmArene.getJpnJeu());
			break;
		// // Ajoute un message dans le chat et l'envoie à tous les clients en appelant la méthode envoi sur leJeu (qu'on aura transtypé en JeuServeur et en lui passant en paramètre le getter sur txtChat). On transtype aussi info en String.
		case AJOUTPHRASE :
			this.frmArene.ajoutTchat((String)info);
			((JeuServeur)this.leJeu).envoi(this.frmArene.getTxtChat());
			break;
		}
	}
	
	/**
	 * Gère les événements provenant du JeuClient
	 * @param ordre L'ordre à exécuter
	 * @param info L'information à traiter
	 */
	public void evenementJeuClient(String ordre, Object info) {
		// Tester l'ordre reçu
		switch(ordre) {
		// Si cet ordre est bien l'ajout du panel des murs, il faut appeler le setter correspondant dans Arene en lui envoyant info en parametre apres l'avoir transtypé en JPanel
		case AJOUTPANELMURS :
			this.frmArene.setJpnMurs((JPanel)info);
			break;
		// Si cet ordre est bien l'ajout du panel du jeu, il faut appeler le setter correspondant dans Arene en lui envoyant info en parametre apres l'avoir transtypé en JPanel
		case MODIFPANELJEU :
			this.frmArene.setJpnJeu((JPanel)info);
			break;
		// Solliciter l'arène en appelant le setter sur txtChat pour modifier le contenu du tchat dans l'arène (sans oublier de transtyper l'information en String avant de l'envoyer à l'arène) 
		case MODIFTCHAT :
			this.frmArene.setTxtChat((String)info);
			break;
		case JOUESON :
			System.out.println("📢 Ordre JOUESON reçu avec info = " + info);
			this.frmArene.joueSon((Integer)info);
			break;
		}
	}
	
	/**
	 * Envoyer l'information reçue en paramètre, vers l'ordinateur distant, en appelant la méthode envoi sur leJeu, après l'avoir transtypé en JeuClient. Mais attention, l'information à envoyer (donc le texte) doit être précédé d'un ordre pour que JeuServeur puisse savoir de quoi il s'agit. Il faut donc envoyer "tchat", suivi du caractère de séparation (~) suivi du texte.
	 * @param info Le message du chat à envoyer.
	 */
	public void evenementArene(Object info) {
		// Si info est une instance de String, envoie le message de chat avec un ordre "tchat" à l'autre joueur (client ou serveur)
		if(info instanceof String) {
			((JeuClient)this.leJeu).envoi(TCHAT+STRINGSEPARE+info);
		// Si info est une instance de Integer, envoie le message de faire une action avec un ordre "action"
		}else if (info instanceof Integer) {
			((JeuClient)this.leJeu).envoi(ACTION+STRINGSEPARE+info);
		}
	}

	/**
	 * Envoie une information vers l'ordinateur distant via l'objet Connection. Elle délègue simplement la tâche à la méthode envoi de la classe Connection.
	 * @param connection L'objet Connection utilisé pour envoyer l'information
	 * @param info L'information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info); // Délègue l'envoi de l'information à l'objet Connection.
	}
	
	/**
	 * Méthode appelée lors de la réception d'informations depuis un autre ordinateur
	 * @param connection La connexion depuis laquelle l'information a été reçue
	 * @param ordre L'ordre à exécuter (par exemple, "connexion", "réception")
	 * @param info L'information reçue
	 */
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		// TODO Auto-generated method stub
		switch(ordre){
		case CONNEXION:
			// Si l'ordre reçu est "CONNEXION", un joueur a demandé à se connecter
			// Si on est déjà dans un jeu serveur, on gère la connexion, sinon on passe en mode client et la fenêtre choixPersonnage s'affiche
			if(!(this.leJeu instanceof JeuServeur)) {
				this.leJeu = new JeuClient(this); // Change le mode de jeu en client
				this.leJeu.connexion(connection); // Etablit la connexion avec le serveur
				this.frmDemarrageJeu.dispose(); // Ferme la fenêtre de démarrage
				this.frmArene = new Arene(this, CLIENT); // Crée la fenêtre du jeu en mode client
				this.frmChoixPersonnage = new ChoixPersonnage(this); // Crée la fenêtre de choix du personnage
				this.frmChoixPersonnage.setVisible(true); // Affiche la fenêtre de choix du personnage
			} else {
			this.leJeu.connexion(connection); // Gère la connexion du joueur au serveur
			}
			break;
		case RECEPTION:
			// Si l'ordre est "RECEPTION", traite l'information reçue, avec la méthode reception de la classe Jeu
			this.leJeu.reception(connection, info);
			break;
		case "deconnexion":
			// Gère la déconnexion (pas encore implémentée ici).
			break;
		}
	}
}