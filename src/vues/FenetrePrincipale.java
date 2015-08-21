package vues;

import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bdd.GestionBdd;

public class FenetrePrincipale extends JFrame {
	private JComboBox<String> cbbDepartements;
	private JRadioButton rdbJourSemaine;
	private JRadioButton rdbNuitSemaine;
	private JRadioButton rdbJourWeekend;
	private JRadioButton rdbNuitWeekend;
	private JTextField txbDistance;
	private JButton btnCalculer;
	private JLabel lblAffichage;
	private JLabel image;
	private ButtonGroup btnGrp;
	
	public FenetrePrincipale() {
		// Configuration de la fenetre principale
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setBounds( new Rectangle( 0, 0, 600, 340 ) );
		setResizable( false );
		setTitle( "Calcul d'un trajet en taxi" );
		
		JPanel panel = new JPanel();
		panel.setLayout( null );
		setContentPane( panel );
		
		// Saisie departement
		JLabel lblDepartement = new JLabel( "Choisissez un département :" );
		lblDepartement.setBounds( 50, 15, 210, 20 );
		getContentPane().add( lblDepartement );
		
		cbbDepartements = new JComboBox<String>();
		cbbDepartements.setBounds( 270, 15, 281, 24 );
		initialiserListeDeroulante();
		getContentPane().add( cbbDepartements );
		
		// Boutons radio
		btnGrp = new ButtonGroup(); // Lie les boutons radio pour qu'il n'y en
		// ai qu'un seul qui puisse être coché
		
		rdbJourSemaine = new JRadioButton( "Jour - Semaine" );
		rdbJourSemaine.setBounds( 70, 60, 149, 23 );
		btnGrp.add( rdbJourSemaine ); // Ajoute le bouton radio au groupe
		getContentPane().add( rdbJourSemaine );
		
		rdbNuitSemaine = new JRadioButton( "Nuit - Semaine" );
		rdbNuitSemaine.setBounds( 70, 90, 149, 23 );
		btnGrp.add( rdbNuitSemaine );
		getContentPane().add( rdbNuitSemaine );
		
		rdbJourWeekend = new JRadioButton( "Jour - Weekend" );
		rdbJourWeekend.setBounds( 70, 120, 149, 23 );
		btnGrp.add( rdbJourWeekend );
		getContentPane().add( rdbJourWeekend );
		
		rdbNuitWeekend = new JRadioButton( "Nuit - Weekend" );
		rdbNuitWeekend.setBounds( 70, 150, 149, 23 );
		btnGrp.add( rdbNuitWeekend );
		getContentPane().add( rdbNuitWeekend );
		
		// Saisie distance
		JLabel lblDistance = new JLabel( "Distance parcourue :" );
		lblDistance.setBounds( 50, 200, 150, 15 );
		getContentPane().add( lblDistance );
		
		txbDistance = new JTextField();
		txbDistance.setBounds( 210, 200, 114, 19 );
		getContentPane().add( txbDistance );
		
		// Bouton pour calculer le total
		btnCalculer = new JButton( "Calculer" );
		btnCalculer.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				try {
					afficherTotal();
				} catch ( Exception ex ) {
					afficherUneErreur( ex.getMessage() );
				}
			}
		} );
		btnCalculer.setBounds( 400, 230, 100, 20 );
		getContentPane().add( btnCalculer );
		
		// Affichage d'erreurs ou du résultat
		lblAffichage = new JLabel( "Erreur : null" );
		lblAffichage.setHorizontalAlignment( SwingConstants.CENTER );
		lblAffichage.setBounds( 40, 270, 530, 15 );
		lblAffichage.setVisible( false );
		getContentPane().add( lblAffichage );
		
		// Affichage d'une image de taxi
		image = new JLabel( new ImageIcon( new ImageIcon( "./images/taxi.png" ).getImage().getScaledInstance( 180,
				110, Image.SCALE_DEFAULT ) ) );
		image.setBounds( 310, 65, 180, 110 );
		getContentPane().add( image );
		
		setVisible( true ); // affiche la fenêtre à l'écran
		setLocationRelativeTo( null ); // centre la fenêtre au milieu de l'écran
	}
	
	/**
	 * Initialise la liste déroulante à partir du numéro des départements
	 * présents dans la base de données
	 */
	private void initialiserListeDeroulante()
	{
		try {
			// On récupère les départements de la base
			List<String> listeDepartements = GestionBdd.getDepartements();
			
			cbbDepartements.addItem( " " );
			
			for ( String departement : listeDepartements )
			{
				cbbDepartements.addItem( departement );
			}
		} catch ( Exception ex ) {
			System.err.println( "Une erreur est survenue lors de l'initialisation de la liste déroulante :\n"
					+ ex.getMessage() );
		}
	}
	
	/**
	 * Retourne une valeur en fonction du bouton radio qui est coché
	 * 
	 * @return 1 : jour semaine | 2 : nuit semaine | 3 : jour weekend | 4 : nuit
	 *         weekend [int]
	 */
	private int getPeriodeSelectionnee()
	{
		int periodeSaisie = 0;
		
		if ( rdbJourSemaine.isSelected() )
		{
			periodeSaisie = 1;
		}
		else if ( rdbNuitSemaine.isSelected() )
		{
			periodeSaisie = 2;
		}
		else if ( rdbJourWeekend.isSelected() )
		{
			periodeSaisie = 3;
		}
		else
		{
			periodeSaisie = 4;
		}
		
		return periodeSaisie;
	}
	
	/**
	 * Retourne la distance saisie par l'utilisateur après avoir vérifié qu'il
	 * n'a pas saisi de nombre négatif
	 * 
	 * @return La distance saisie [int]
	 * @throws Exception La zone de saisie est vide [Exception]
	 * @throws Exception La distance saisie n'est pas un entier positif
	 *             [Exception]
	 */
	private int getDistanceSaisie() throws Exception
	{
		int distanceSaisie = 0;
		
		try {
			distanceSaisie = Integer.parseInt( txbDistance.getText() );
		} catch ( Exception ex ) {
			throw new Exception( "Veuillez saisir un nombre entier comme distance parcourue !" );
		}
		
		// Si l'utilisateur a saisi une distance négative ou nulle, on lève une
		// exception
		if ( distanceSaisie < 1 )
		{
			throw new Exception( "Veuillez saisir une distance positive !" );
		}
		
		return distanceSaisie;
	}
	
	/**
	 * Retourne le montant à payer après avoir effectuer tous les contrôles de
	 * saisies nécessaires.
	 * 
	 * @return Le montant à payer [double]
	 * @throws Exception Aucun département n'a été selectionné dans la liste
	 *             déroulante
	 * @throws Exception Aucun bouton radio n'a été coché
	 * @throws Exception Aucune distance n'a été saisie
	 * @throws Exception La distance saisie est négative ou n'est pas un entier
	 */
	private double calculerTotal() throws Exception
	{
		// Vérification de la liste déroulante
		if ( cbbDepartements.getSelectedIndex() == 0 )
		{
			throw new Exception( "Veuillez saisir un département dans la liste déroulante !" );
		}
		
		// Vérification des boutons radio
		// Si aucun bouton radio n'est coché, on lève une excepion
		if ( !rdbJourSemaine.isSelected() && !rdbNuitSemaine.isSelected() && !rdbJourWeekend.isSelected()
				&& !rdbNuitWeekend.isSelected() )
		{
			throw new Exception( "Veuillez cocher une période" );
		}
		
		// Vérification de la zone de saisie
		if ( txbDistance.getText().equals( "" ) )
		{
			throw new Exception( "Veuillez saisir la distance parcourue !" );
		}
		
		// On récupère les saisies
		int distanceSaisie = getDistanceSaisie();
		
		int departementSelectionne = Integer.parseInt( cbbDepartements.getSelectedItem().toString() );
		
		int periodeSelectionnee = getPeriodeSelectionnee();
		
		// On récupère le montant de prise en charge et le tarif en fonction du
		// département et de la période sélectionés
		double[] tarifsUtilises = GestionBdd.obtenirTarifs( departementSelectionne, periodeSelectionnee );
		
		// On retourne le montant à payer calculé
		return tarifsUtilises[0] + distanceSaisie * tarifsUtilises[1];
	}
	
	/**
	 * Arrondi un nombre à deux décimales derrière la virgule
	 * 
	 * @param Le nombre à arrondir [double]
	 * @return Le nombre arrondi [String]
	 */
	private static String arrondirAuCentime( double nombre )
	{
		DecimalFormat f = new DecimalFormat();
		f.setMaximumFractionDigits( 2 );
		return f.format( nombre );
	}
	
	/**
	 * Affiche le montant à payer en vert foncé dans le label d'affichage
	 * [String]
	 * 
	 * @throws Exception
	 */
	private void afficherTotal() throws Exception
	{
		lblAffichage.setForeground( new Color( 0, 196, 39 ) );
		lblAffichage.setText( "Voici le montant à payer : " + arrondirAuCentime( calculerTotal() ) + " €" );
		lblAffichage.setVisible( true );
	}
	
	/**
	 * Affiche le message, passé en paramètres, en rouge dans le label
	 * d'affichage.
	 * 
	 * @param erreur Le message d'erreur à afficher [String]
	 */
	private void afficherUneErreur( String erreur )
	{
		lblAffichage.setForeground( Color.RED );
		lblAffichage.setText( "Erreur : " + erreur );
		lblAffichage.setVisible( true );
	}
}// fin classe