package controleur;
import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixPersonnage;
import vue.DemarrageJeu;

/**
 * Contrôleur et point d'entrée de l'applicaton 
 */
public class Controle implements AsyncResponse {
	
	/**
	 * Creation d'une constante PORT pour mémoriser la valeur du port (6666) et utiliser cette constante
	 */
	private static final int PORT = 6666;
	
	/**
	 * Frames DemarrageJeu, Arene et ChoixPersonnage
	 */
	private DemarrageJeu frmDemarrageJeu ;
	
	private Arene frmArene;
	
	private ChoixPersonnage frmChoixPersonnage;
	
	/**
	 * type du jeu : client ou serveur
	 */
	private String typeJeu;
	


	 /**
     * Méthode de démarrage
     */
    public static void main(String[] args) {
        new Controle(); // Initialisation de Controle
    }

    /**
     * Constructeur
     */
    private Controle() {
    	    this.frmDemarrageJeu = new DemarrageJeu(this);
			this.frmDemarrageJeu.setVisible(true);
    }

	/**
	 * Demandes provenants de la vue EntreeJeu
	 * @param info est l'information à traiter
	 */
	public void evenementEntreeJeu(String info) {
		if(info.equals("serveur")) {
			this.typeJeu = "serveur";
			//instancier la classe ServeurSocket
			new ServeurSocket(this, PORT);
			this.frmDemarrageJeu.dispose();
			this.frmArene = new Arene();
			this.frmArene.setVisible(true);
		} else {
			this.typeJeu = "client";
			new ServeurSocket(this, PORT);
			new ClientSocket(this, info, PORT);
		}
	}
	
	/**
	 * Informations de la vue ChoixJoueur
	 * @param pseudo le pseudo du joueur
	 * @param numPerso le numéro du personnage choisi par le joueur
	 */
	public void evenementChoixPersonnage(String pseudo, int numPerso) {
		this.frmChoixPersonnage.dispose();
		this.frmArene.setVisible(true);
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		// TODO Auto-generated method stub
		switch(ordre){
		case "connexion":
			if(this.typeJeu.equals("client")) {
				this.frmDemarrageJeu.dispose();
				this.frmArene = new Arene();
				this.frmChoixPersonnage = new ChoixPersonnage(this);
				this.frmChoixPersonnage.setVisible(true);
			}
			break;
		case "reception":
			break;
		case "deconnexion":
			break;
		}
		
	}
}