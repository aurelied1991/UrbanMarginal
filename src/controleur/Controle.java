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
import controleur.Global;

/**
 * Contrôleur et point d'entrée de l'applicaton 
 */
public class Controle implements AsyncResponse, Global {
	
	/**
	 * Frames DemarrageJeu, Arene et ChoixPersonnage
	 */
	private DemarrageJeu frmDemarrageJeu ;
	
	private Arene frmArene;
	
	private ChoixPersonnage frmChoixPersonnage;
	
	/**
	 * type du jeu : client ou serveur (jeuClient ou JeuServeur)
	 */
	private Jeu leJeu;
	


	 /**
     * Point d'entrée du programme
     */
    public static void main(String[] args) {
        new Controle(); // Création d'une instance de la classe contrôle qui lance l'application
    }

    /**
     * Constructeur qui crée la fenêtre de démarrage de l'application (frmDemarrageJeu), la rend visible et attend les interactions de l'utilisateur
     */
    private Controle() {
    	    this.frmDemarrageJeu = new DemarrageJeu(this);
			this.frmDemarrageJeu.setVisible(true);
    }

	/**
	 * Demandes provenants de la vue DemarragJeu selon le choix d'utilisateur (serveur ou client)
	 * @param info est l'information à traiter
	 */
	public void evenementEntreeJeu(String info) {
		if(info.equals("serveur")) {
			//instancier la classe ServeurSocket si le joueur clique sur start puis l'objet JeuServeur est initialisé, la fenêtre frmDemarrage se ferme et frmArene s'ouvre
			new ServeurSocket(this, PORT);
			this.leJeu = new JeuServeur(this);
			this.frmDemarrageJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		} else {
			//si l'utilisateur choisit clique sur connect, une connexion client est établie via ClientSocket, mais sans initialiser le serveur
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Méthode appelée lorsque le joueur choisit un pseudo et un personnage, informations de la vue ChoixJoueur
	 * @param pseudo le pseudo du joueur
	 * @param numPerso le numéro du personnage choisi par le joueur
	 */
	public void evenementChoixPersonnage(String pseudo, int numPerso) {
		this.frmChoixPersonnage.dispose();
		this.frmArene.setVisible(true);
		//On transtype leJeu en JeuClient car c'est lui qui possède l'objet de type Connection pour communiquer avec l'ordinateur distant (le jeu envoie les infos du personnage choisi au serveur en passant par la méthode envoi de la classe JeuClient)
		((JeuClient)this.leJeu).envoi(PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+numPerso);

	}

	/**
	 * Envoi d'informations vers l'ordinateur distant via l'objet Connection. Elle délègue simplement la tâche à la méthode envoi de la classe Connection.
	 * @param connection objet de connexion pour l'envoi vers l'ordinateur distant
	 * @param info information à envoyer
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}
	
	/**
	 * Cette méthode est appelée lorsque des informations sont reçues d'un autre ordinateur
	 */
	@Override
	public void reception(Connection connection, String ordre, Object info) {
		// TODO Auto-generated method stub
		switch(ordre){
		case CONNEXION:
			//Si ordre recu est CONNEXION, signifie qu'un joueur a demandé à se connecter. Si ce n'est pas déjà un serveur (JeuServeur), la connexion est traitée en tant que client, et la fenêtre choixPersonnage s'affiche.
			//Si c'est déjà un serveur, la connexion est simplement gérée
			if(!(this.leJeu instanceof JeuServeur)) {
				this.leJeu = new JeuClient(this);
				this.leJeu.connexion(connection);
				this.frmDemarrageJeu.dispose();
				this.frmArene = new Arene();
				this.frmChoixPersonnage = new ChoixPersonnage(this);
				this.frmChoixPersonnage.setVisible(true);
			} else {
			this.leJeu.connexion(connection);
			}
			break;
		case RECEPTION:
			//Si l'ordre est RECEPTION, le jeu traite l'information reçue aec la méthode reception de l'objet Jeu
			this.leJeu.reception(connection, info);
			break;
		case "deconnexion":
			break;
		}
		
	}
}