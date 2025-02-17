package modele;

import java.io.File;
import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Global, Runnable{

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur;
	
	/**
	 * Collection de murs
	 */
	private Collection lesMurs;
	
	/**
	 * joueur qui lance la boule
	 */
	private Joueur attaquant ;
	
	/**
	 * Constructeur pour créer le label de la boule
	 * @param jeuServeur Pour communiquer avec jeuServeur
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		super.jLabel = new JLabel();
		super.jLabel.setVisible(false);
		 // Vérifier si le fichier image existe
	    String chemin = "C:/Users/aurel/Desktop/JAva2/UrbanMarginal/media/boules/boule.gif";
	    File fichierImage = new File(chemin);
	    
	    if (fichierImage.exists()) {
	        System.out.println("Image trouvée : " + chemin);
	        super.jLabel.setIcon(new ImageIcon(chemin));
	    } else {
	        System.out.println("⚠ Erreur : image non trouvée à " + chemin);
	    }
		super.jLabel.setBounds(0, 0, LARGEURBOULE, HAUTEURBOULE);
	}
	
	/**
	 * Méthode qui gère le lancer d'une boule
	 * @param attaquant Joueur qui lance la boule
	 * @param lesMurs collection de murs
	 */
	public void tireBoule(Joueur attaquant, Collection lesMurs) {
		this.lesMurs = lesMurs;
		this.attaquant = attaquant;
		// Positionnement de la boule
		if(attaquant.getOrientation()==GAUCHE) {
			posX = attaquant.getPosX() - LARGEURBOULE - 1 ;
		}else{
			posX = attaquant.getPosX() + LARGEURPERSO + 1 ;
		}
		posY = attaquant.getPosY() + HAUTEURPERSO/2 ;
		// Mise à jour de l'affichage de la boule
	    super.jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
	    super.jLabel.setVisible(true);  // Assure-toi que la boule est visible
		// Démarrer le thread pour gérer le tir de la boule
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		// Afficher l'attaquant à l'étape stop de la marche (position de tir)
		this.attaquant.affiche(MARCHE, 1);
		// Rendre la boule visible
		super.jLabel.setVisible(true);
		// Déclaration d'une variable victime de type Joueur et l'initialiser à null (si un joueur est touché, elle recevra son instance)
		Joueur victime = null;
		// Mémoriser dans une variable local lePas, soit la valeur du pas si l'orientation de l'attaquant est vers la droite, soit la même valeur en négatif (de façon à faire avancer la boule vers la gauche ou la droite)
		int lePas;
		if (attaquant.getOrientation() == GAUCHE) {
			lePas = - PAS;
		}else{
			lePas = PAS;
		}
		// Boucle pour gérer la trajectoire de la boule, boucle tantque posX est dans la limite de la taille de l'arène, qu'il n'y a pas de victime et qu'aucun mur n'est touché (réutiliser la méthode toucheCollectionObjets avec lesMurs).
		do {
			// Incrémenter posX avec lePas (pour faire avancer ou reculer la boule)
			posX += lePas;
			// Modifier la position du jLabel de la boule en tenant compte de posX et posY (avec la méthode setBounds)
			jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
			// Utiliser la méthode envoiJeuATous de JeuServeur pour envoyer la nouvelle position de la boule  (pour que tous voient la boule avancer)
			this.jeuServeur.envoiJeuATous();
			// Récupèrer la collection actuelle de joueurs dans une variable locale
			Collection lesJoueurs = this.jeuServeur.getLesJoueurs();
			// Affecter dans victime le résultat de l'appel de la méthode toucheCollectionObjets en lui envoyant la collection de joueurs
			victime = (Joueur)super.toucheCollectionObjets(lesJoueurs);
		}while(posX>=0 && posX<=LARGEURARENE && this.toucheCollectionObjets(lesMurs)==null && victime==null);
		// Contrôler s'il y a une victime et si elle n'est pas déjà morte
		if(victime != null && !victime.estMort()) {
			victime.perteVie();
			attaquant.gainVie();
			// Boucle pour jouer l'animation du personnage blessé (en appelant à chaque fois la méthode affiche en lui envoyant en premier paramètre "touche" (à définir en constante) et en second paramètre le numéro de l'étape (l'indice de la boucle)
			for(int k=1 ; k<=NBETAPESTOUCHE ; k++) {
				victime.affiche(TOUCHE, k);
				pause(80, 0);
			}
			// contrôle si la victime est morte
			if(victime.estMort()) {
				// Si elle est morte, jouer l'animation du personnage mort
				for(int k=1 ; k<=NBETAPESMORT ; k++) {
					victime.affiche(MORT, k);
					pause(80, 0);
				}
			} else {
				// Si elle n'est pas morte, remettre le joueur dans la position de repos (marche)
				victime.affiche(MARCHE, 1);
			}
		}
		// rendre à nouveau la boule invisible
		this.jLabel.setVisible(false);
		// envoyer le nouveau jeu à tous
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Méthode pour faire une pause (bloque le processus) d'une durée précise sinon les animations seraient trop rapides
	 * @param millis millisecondes
	 * @param nanos nanosecondes
	 */
	private void pause(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.out.println("erreur pause");
		}
	}
	
}
