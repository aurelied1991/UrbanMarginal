package vue;

import java.awt.EventQueue;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChoixPersonnage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private JLabel lblFlecheGauche;
	private JLabel lblFlecheDroite;
	private JLabel lblGo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChoixPersonnage frame = new ChoixPersonnage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChoixPersonnage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 407, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = "fonds/fondchoix.jpg";
		URL resource = getClass().getClassLoader().getResource(chemin);
		
		lblFlecheGauche = new JLabel("");
		lblFlecheGauche.setBounds(54, 140, 56, 53);
		lblFlecheGauche.setOpaque(false);
		contentPane.add(lblFlecheGauche);
		lblFlecheGauche.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	lblFlecheGauche.setText("précédent");
		    }
		});
		
		lblFlecheDroite = new JLabel("");
		lblFlecheDroite.setBounds(286, 140, 56, 53);
		lblFlecheDroite.setOpaque(false);
		contentPane.add(lblFlecheDroite);
		lblFlecheDroite.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	lblFlecheDroite.setText("suivant");
		    }
		});
		
		txtPseudo = new JTextField();
		txtPseudo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPseudo.setText("dfdfdfdfdfd");
		txtPseudo.setBounds(141, 243, 121, 26);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		lblFond.setIcon(new ImageIcon("C:\\Users\\aurel\\Desktop\\JAva2\\UrbanMarginal\\media\\fonds\\fondchoix.jpg"));		
		contentPane.add(lblFond);
		
		lblGo = new JLabel("New label");
		lblGo.setBounds(296, 192, 87, 77);
		lblGo.setOpaque(false);
		contentPane.add(lblGo);
		
		lblGo.addMouseListener((MouseListener) new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Lancer la vue "arene"
		        new Arene().setVisible(true);
		        // Ferme la fenêtre actuelle
		        ChoixPersonnage.this.dispose(); // Ferme la fenêtre ChoixPersonnage
		    }
		});
	}
}
