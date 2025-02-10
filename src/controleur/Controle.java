package controleur;

import vue.DemarrageJeu;

/**
 * Contrôleur et point d'entrée de l'applicaton 
 */
public class Controle {
	
	private DemarrageJeu frmEntreeJeu ;

	/**
	 * Méthode de démarrage
	 */
	public static void main(String[] args) {
		new Controle();
	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new DemarrageJeu() ;
		this.frmEntreeJeu.setVisible(true);
	}	

}