package vue;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controleur.Global;

import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class Arene extends JFrame implements Global{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane; // panel général où les composants graphiques sont ajoutés
	private JPanel jpnMurs; // Panel contenant les murs
	private JPanel jpnJeu; // Panel contenant les joueurs
	private JTextField txtSaisie; // zone de saisie où l'utilisateur peut taper des messages dans le tchat
	private JTextArea txtChat ; // zone d'affichage du tchat


	/**
	 * Méthode qui créée une instance de la fenêtre arene et la rend visible
	 * Cette méthode est généralement utilisée pour tester l'interface indépendamment du reste de l'application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Arene frame = new Arene();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * @return the jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}

	/**
	 * @param jpnMurs the jpnMurs to set
	 */
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}
	
	/**
	 * 
	 */
	public JPanel getJpnJeu() {
		return jpnJeu;
	}
	
	/**
	 * 
	 */
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
	}
	
	/**
	 * Ajoute un mur au panel jpnMurs après l'avoir transtyper en JLabel puis repeindre le mur
	 * @param unMur
	 */
	public void ajoutMurs(JLabel unMur) {
	    jpnMurs.add(unMur);
	    jpnMurs.repaint();
	}
	
	/**
	 * Ajout d'un joueur, son message ou sa boule, dans le panel de jeu
	 * @param unJLabel le label à ajouter
	 */
	public void ajoutJLabelJeu(JLabel unJLabel) {
		this.jpnJeu.add(unJLabel);
		this.jpnJeu.repaint();
	}
	

	/**
	 * Constructeur créant la fenêtre de l'arène en initialisant tous les composants graphiques et en configurant les paramètres de la fenêtre
	 */
	public Arene() {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(800, 600 + 25 + 140));
		this.pack();
		// interdiction de changer la taille
		this.setResizable(false);
		
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);		
		contentPane.add(jpnJeu);
		
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false); //transparent
		jpnMurs.setLayout(null);		
		contentPane.add(jpnMurs);
		
		txtSaisie = new JTextField();
		txtSaisie.setBounds(0, 600, 800, 25);
		contentPane.add(txtSaisie);
		txtSaisie.setColumns(10);
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, 800, 140);
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);
		
		JLabel lblFond = new JLabel("");
		String chemin = "media/fonds/fondarene.jpg";
		lblFond.setIcon(new ImageIcon(chemin));		
		lblFond.setBounds(0, 0, 800, 600);
		contentPane.add(lblFond);
	}

}
