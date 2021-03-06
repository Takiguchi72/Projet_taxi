package classes;

import java.util.Scanner;

import bdd.GestionBdd;

public class Saisies {
	protected static final String PERIODES[] = { "Semaine - Jour",
			"Semaine - Nuit",
			"Week-end - Jour",
			"Week-end - Nuit" };
	protected static final String INTITULE_DEPARTEMENT = "\nSaisissez le numéro du département :";
	protected static final String INTITULE_DISTANCE = "\nSaisissez la distance que vous avez parcouru :";
	protected static Scanner scanner = new Scanner( System.in );
	
	/**
	 * Affiche les périodes disponibles
	 */
	public static void afficherPeriodes()
	{
		System.out.println( "\nSaisissez le numéro correspondant à la période choisie :" );
		for ( int i = 0; i < PERIODES.length; i++ )
		{
			System.out.println( "(" + ( i + 1 ) + ") " + PERIODES[i] );
		}
	}// fin afficherPeriodes
	
	/**
	 * Effectue la saisie d'un nombre entier
	 * 
	 * @return L'entier saisi [int]
	 */
	public static int saisirUnEntier() throws Exception
	{
		int nombreSaisi = 0;
		try {
			nombreSaisi = scanner.nextInt();
		} catch ( Exception ex ) {
			// On vide le scanner
			String s = scanner.next();
			
			// On relève une exception pour avoir une erreur plus parlante
			throw new Exception( "\n/!\\ Veuillez saisir un nombre entier !" );
		}
		
		return nombreSaisi;
	}// fin saisirUnEntier
	
	/**
	 * Effectue la saisie du département
	 * 
	 * @return Le numéro du département saisi [int]
	 */
	public static int saisirDepartement()
	{
		int departementSaisi = 0;
		
		// On affiche le texte
		System.out.println( INTITULE_DEPARTEMENT );
		
		try {
			departementSaisi = saisirUnEntier(); // On effectue une saisie
			
			// On récupère l'indice du département saisi
			GestionBdd.verifierDepartement( departementSaisi );
		} catch ( Exception ex ) {
			// Si une exception a été levée, on affiche le message d'erreur, et
			// on réeffectue la saisie.
			System.out.println( ex.getMessage() );
			departementSaisi = saisirDepartement();
		}
		
		return departementSaisi;
	}// fin saisirDepartement
	
	/**
	 * Effectue la saisie de la période
	 * 
	 * @return Le numéro de la période [int]
	 */
	public static int saisirPeriode()
	{
		int periodeSaisie = 0;
		
		// On affiche les périodes possibles
		afficherPeriodes();
		
		try {
			periodeSaisie = saisirUnEntier(); // On effectue une saisie
			
			// Si l'utilisateur a saisit autre chose que 1 ou 2, on lève une
			// exception
			if ( periodeSaisie < 1 || periodeSaisie > PERIODES.length )
			{
				throw new Exception( "\n/!\\ Veuillez saisir le chiffre correspondant à l'une des périodes proposées !" );
			}
		} catch ( Exception ex ) {
			// Si une exception a été levée, on affiche le message d'erreur, et
			// on réeffectue la saisie.
			System.out.println( ex.getMessage() );
			periodeSaisie = saisirPeriode();
		}
		
		return periodeSaisie;
	}// fin saisirPeriode()
	
	/**
	 * Effectue la saisie de la distance
	 * 
	 * @return La distance saisie [int]
	 */
	public static int saisirDistance()
	{
		int distanceSaisie = 0;
		
		// On affiche le texte
		System.out.println( INTITULE_DISTANCE );
		
		try {
			distanceSaisie = saisirUnEntier();
			
			if ( distanceSaisie <= 0 )
			{
				throw new Exception( "La distance que vous avez saisi ne peut être inférieur ou égale à 0 !" );
			}
		} catch ( Exception ex ) {
			// Si une exception a été levée, on affiche le message d'erreur, et
			// on réeffectue la saisie.
			System.out.println( ex.getMessage() );
			distanceSaisie = saisirDistance();
		}
		
		return distanceSaisie;
	}// fin saisirDistance
	
	/**
	 * Ferme le scanner
	 */
	public static void fermerScanner()
	{
		scanner.close();
	}
}// fin classe
