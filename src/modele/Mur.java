package modele;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import controleur.Global;

/**
 * Gestion des murs
 * Classe qui hérite de la classe Objet, représentant un mur dans l'arène
 * Cette classe permet de gérer la création, la position et l'affichage du mur
 */

public class Mur extends Objet implements Global{

	
	/**
	 * Constructeur pour créer un mur à une position aléatoire dans l'arène et définir son image
	 * Ce constructeur vérifie si l'image du mur existe à l'emplacement donné, et si elle existe, il initialise la position du mur et crée un JLabel pour afficher l'image.
	 */
	public Mur() {
		// Définir le chemin du fichier image du mur
		File imageFile = new File("C:/Users/aurel/Desktop/JAva2/UrbanMarginal/media/murs/mur.gif");
		// Calcul position aléatoire du mur dans l'arène
		posX = (int)Math.round(Math.random() * (LARGEURARENE - LARGEURMUR));
		posY = (int)Math.round(Math.random() * (HAUTEURARENE - HAUTEURMUR));
		// Création du JLabel pour afficher l'image du mur
		jLabel = new JLabel();
		//System.out.println("JLabel créé"); // Ajout de ce message pour vérifier
		// Caractéristiques du mur : ajout de l'image du mur et définition de sa taille et position
		String cheminMur = "C:/Users/aurel/Desktop/JAva2/UrbanMarginal/media/murs/mur.gif";
		jLabel.setIcon(new ImageIcon(cheminMur));	
		jLabel.setBounds(posX, posY, LARGEURMUR, HAUTEURMUR);
        }
	

}
