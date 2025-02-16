package modele;

import javax.swing.JLabel;

/**
 * Informations communes � tous les objets (joueurs, murs, boules)
 * permet de m�moriser la position de l'objet et de g�rer les  collisions
 *
 */
public abstract class Objet {

	/**
	 * position X de l'objet
	 */
	protected Integer posX ;
	/**
	 * position Y de l'objet
	 */
	protected Integer posY ;
	
	/**
	 * label contenant l'objet graphique (personnage, mur, boule)
	 */
	protected JLabel jLabel;
	
	/**
	 * @return the jLabel
	 */
	public JLabel getjLabel() {
		return jLabel;
	}
	
	/**
	 * contr�le si l'objet actuel touche l'objet passe en parametre (par ex, un mur ne doit pas toucher un autre mur ou un joueur)
	 * @param objet contient l'objet a contr�ler
	 * @return true si les 2 objets se touchent
	 */
	public Boolean toucheObjet (Objet objet) {
		if(this.jLabel == null || objet.jLabel == null) {
			return false;
		}else {
			// la premiere comparaison vérifie si le bord droit de this dépasse le bord gauche de objet
			// la deuxieme comparaison vérifie si le bord gauche de this est avant le bord droit de objet.
			// la troisieme comparaison vérifie si le bord inférieur de this dépasse le bord supérieur de objet.
			// la quatrieme comparaison vérifie si le bord supérieur de this est avant le bord inférieur de objet.
			return(this.posX+this.jLabel.getWidth()>objet.posX && this.posX<objet.posX+objet.jLabel.getWidth() &&
					this.posY+this.jLabel.getHeight()>objet.posY && this.posY<objet.posY+objet.jLabel.getHeight());
		}
	}
	
}
