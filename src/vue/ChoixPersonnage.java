package vue;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import controleur.Controle;


/**
 * Frame du choix  du joueur
 */
public class ChoixPersonnage extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int NBPERSOS = 3;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private JLabel lblAffichagePerso;
	private Controle controle;
	private int numPerso;
	
	/**
	 * Clic sur la flèche de droite pour afficher le personnage suivant
	 */
	public void lblFlecheDroite_clic() {
		numPerso = (numPerso%NBPERSOS)+1 ;
		affichePerso();
	}
	
	/**
	 * Clic sur la flèche de gauche pour afficher le personnage précédent
	 */
	public void lblFlecheGauche_clic() {
		numPerso = ((numPerso+1)%NBPERSOS)+1;
		affichePerso();
	}
	
	/**
	 * Clic sur GO pour envoyer les informations et vérifier qu'un pseudo est bien entré
	 */
	private void lblGo_clic() {
		if(this.txtPseudo.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			this.txtPseudo.requestFocus();
		} else {
			this.controle.evenementChoixPersonnage(this.txtPseudo.getText(), numPerso);
		}
	}
	
	/**
	 * Affichage du personnage correspondant au numéro numPerso
	 */
	public void affichePerso() {
		String chemin ="media/personnages/perso" + this.numPerso+"marche"+"1"+"d"+"1"+".gif";
		//URL resource = getClass().getClassLoader().getResource(chemin);
		lblAffichagePerso.setIcon(new ImageIcon(chemin));		
		//this.lblAffichagePerso.setIcon(new ImageIcon(resource));
	}
	
	/**
	 * Pointeur de la souris classique 
	 */
	public void sourisNormale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * Le pointeur de la souris se change en doigt
	 */
	public void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	/**
	 * Create the frame.
	 * @param controle 
	 */
	public ChoixPersonnage(Controle controle) {
		// Dimension de la frame en fonction de son contenu
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
	    this.pack();
	    // interdiction de changer la taille
		this.setResizable(false);
		 
		setTitle("Choice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblAffichagePerso = new JLabel("");
		lblAffichagePerso.setBounds(163, 129, 79, 92);
		lblAffichagePerso.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblAffichagePerso);
		
		//Gestion de la flèche gauche, de son clic et de l'apparence du pointeur de la souris à cet endroit
		JLabel lblFlecheGauche = new JLabel("");
		lblFlecheGauche.setBounds(54, 140, 56, 53);
		lblFlecheGauche.setOpaque(false);
		contentPane.add(lblFlecheGauche);
		lblFlecheGauche.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblFlecheGauche_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
	
		
		//Gestion de la flèche droite, de son clic et de l'apparence du pointeur de la souris à cet endroit
		JLabel lblFlecheDroite = new JLabel("");
		lblFlecheDroite.setBounds(286, 140, 56, 53);
		lblFlecheDroite.setOpaque(false);
		contentPane.add(lblFlecheDroite);
		lblFlecheDroite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblFlecheDroite_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		
		//Gestion du champ pseudo
		txtPseudo = new JTextField();
		txtPseudo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPseudo.setText("");
		txtPseudo.setBounds(141, 243, 121, 26);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		JLabel lblGo = new JLabel("");
		lblGo.setBounds(296, 192, 87, 77);
		lblGo.setOpaque(false);
		contentPane.add(lblGo);
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = "media/fonds/fondchoix.jpg";
		lblFond.setIcon(new ImageIcon(chemin));		
		contentPane.add(lblFond);
		
		// récupération de l'instance de Controle
		this.controle = controle;
				
		// affichage du premier personnage
		this.numPerso = 1;
		this.affichePerso();

		// positionnement sur la zone de saisie
		txtPseudo.requestFocus();
	}
}
