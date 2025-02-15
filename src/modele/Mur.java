package modele;

import java.io.File;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion des murs
 * Classe qui hérite de objet 
 *
 */

public class Mur extends Objet implements Global{

	private JLabel lblMurs; // Déclaration de lblMurs comme variable d'instance
	/**
	 * Constructeur pour créer un mur
	 */
	public Mur() {
		
		File imageFile = new File("C:/Users/aurel/Desktop/JAva2/UrbanMarginal/media/murs/mur.gif");

		if (!imageFile.exists()) {
		    System.out.println("L'image n'existe pas à cet emplacement.");
		} else {
		    System.out.println("L'image existe à cet emplacement.");
		}
		//calcul position aléatoire du mur
		posX = (int)Math.round(Math.random() * (LARGEURARENE - LARGEURMUR));
		posY = (int)Math.round(Math.random() * (HAUTEURARENE - HAUTEURMUR));
		//création du Jlabel pour ce mur
		lblMurs = new JLabel();
		System.out.println("JLabel créé"); // Ajout de ce message pour vérifier
		//Caractéristiques du  mur : position et image
		String cheminMur = "C:/Users/aurel/Desktop/JAva2/UrbanMarginal/media/murs/mur.gif";
		lblMurs.setIcon(new ImageIcon(cheminMur));	
		lblMurs.setBounds(posX, posY, LARGEURMUR, HAUTEURMUR);
        }
	
	// Méthode pour récupérer le JLabel
    public JLabel getjLabel() {
        if (lblMurs == null) {
            System.out.println("Erreur : lblMurs est toujours null dans getjLabel.");
        } else {
            System.out.println("lblMurs récupéré correctement.");
        }

        return lblMurs;
    }
}
