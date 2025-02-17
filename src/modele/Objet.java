package modele;

import java.util.Collection;

import javax.swing.JLabel;

/**
 * Informations communes à tous les objets (joueurs, murs, boules)
 * Cette classe abstraite permet de mémoriser la position des objets dans l'arène et de gérer les collisions entre ces objets
 *
 */
public abstract class Objet {
	/**
	 * Position X de l'objet, Coordonnée horizontale de l'objet dans l'arène
	 */
	protected Integer posX ;
	/**
	 * Position Y de l'objet, Coordonnée verticale de l'objet dans l'arène.
	 */
	protected Integer posY ;
	
	/**
	 * Label contenant l'objet graphique (personnage, mur, boule), c'est l'élément graphique de l'objet affiché à l'écran.
	 */
	protected JLabel jLabel;
	
	/**
	 * Getter
	 * @return the posX la position Y de l'objet
	 */
	public Integer getPosX() {
		return posX;
	}

	/**
	 * Getter
	 * @return the posY la position X de l'objet
	 */
	public Integer getPosY() {
		return posY;
	}
	
	/**
	 * Getter pour obtenir le label de l'objet
	 * @return jLabel : le label graphique de l'objet
	 */
	public JLabel getjLabel() {
		return jLabel;
	}
	
	/**
	 * Contrôle si l'objet actuel touche l'objet passé en paramètre (par exemple, un mur ne doit pas toucher un autre mur ou un joueur)
	 * Cette méthode vérifie si les zones de l'objet actuel et de l'objet passé en paramètre se chevauchent, indiquant ainsi une collision
	 * @param objet : l'objet à contrôler pour la collision
	 * @return true si les deux objets se touchent (collision), false sinon
	 */
	public Boolean toucheObjet (Objet objet) {
		// Si l'un des objets n'a pas de jLabel (c'est-à-dire qu'il n'est pas encore affiché), on ne peut pas vérifier la collision
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
	
	/**
	 * Vérifie si l'objet actuel touche un des objets de la collection
	 * @param lesObjets collection d'objets (murs, joueurs ou boules)
	 * @return l'objet touché ou null
	 */
	public Objet toucheCollectionObjets (Collection<Objet> lesObjets) {
		for (Objet unObjet : lesObjets) {
			if (!unObjet.equals(this)) {
				if (toucheObjet(unObjet)) {
					return unObjet ;
				}
			}
		}
		return null;
	}
}
