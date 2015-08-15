package pointDEntree;

import classes.Saisies;

public class ProjetTaxi {
	/**
	 * Retourne le tarif correspondant au département et à la période saisie
	 * 
	 * @param L'indice du département dans le tableau [int]
	 * @param Le numéro de la période [int]
	 * @return Le tarif à utiliser [double]
	 * @throws Lève une exception si la période choisie n'est pas prise en
	 *             charge
	 */
	public static double obtenirTarifPourLaPeriode( int indiceDepartement, int periode ) throws Exception
	{
		double tarifPritEnCompte = 0.0;
		
		switch ( periode )
		{
			case 1:
				tarifPritEnCompte = Saisies.TARIFS[indiceDepartement][1];
				break;
			case 2:
				tarifPritEnCompte = Saisies.TARIFS[indiceDepartement][2];
				break;
			case 3:
				tarifPritEnCompte = Saisies.TARIFS[indiceDepartement][3];
				break;
			case 4:
				tarifPritEnCompte = Saisies.TARIFS[indiceDepartement][4];
				break;
			default:
				throw new Exception( "Erreur de période pour la selection du tarif" );
		}
		
		return tarifPritEnCompte;
	}// fin obtenirTarifPourLaPeriode
	
	/**
	 * Retourne le prix total à payer en fonction du département, de la période
	 * et de la distance
	 * 
	 * @param Le numéro du departement [int]
	 * @param Le numéro de la periode [int]
	 * @param La distance parcourue [int]
	 * @return Le prix total à payer [double]
	 */
	public static double calculerTotal( int departement, int periode, int distance ) throws Exception
	{
		double total = 0.0;
		
		// On récupère l'indice du département dans le tableau, pour pouvoir
		// utiliser les tarifs correspondants
		int indiceDepartement = -1; // On initialise l'indice à -1 car 0 est une
									// valeur possible, et donc ne serait pas
									// utilisable pour detecter une erreur
		
		for ( int i = 0; i < Saisies.DEPARTEMENTS.length; i++ )
		{
			if ( departement == Saisies.DEPARTEMENTS[i] )
			{
				indiceDepartement = i;
				break;
			}
		}
		
		// On vérifie que l'indice du département saisi dans le tableau a bien
		// été récupéré (si = -1 -> l'indice n'aura pas été récupéré)
		if ( indiceDepartement == -1 )
		{
			throw new Exception( "Problème d'indice pour le département saisi !" );
		}
		
		// On récupère le tarif en fonction du département et de la période
		// saisis
		double tarifUtilise = obtenirTarifPourLaPeriode( indiceDepartement, periode );
		
		// On calcule le montant à payer
		total = Saisies.TARIFS[indiceDepartement][0] + distance * tarifUtilise;
		
		return total;
	}// fin calculerTotal
	
	/**
	 * Point d'entrée du programme
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		// On affiche une bannière pour la décorration de l'application
		System.out.println( "   =========================\n   = T a x i - t a r i f s =\n   =========================" );
		
		// Demander le numéro d'appartement
		int departement = Saisies.saisirDepartement();
		
		// Demander si c'était un voyage en semaine ou le week-end
		int periode = Saisies.saisirPeriode();
		
		// Demander la distance
		int distance = Saisies.saisirDistance();
		
		// Afficher le prix à payer
		try {
			System.out.println( "\nVoici le montant à payer : " + calculerTotal( departement, periode, distance )
					+ " €" );
		} catch ( Exception ex ) {
			System.out.println( "\n/!\\ Une erreur est survenue lors du calcul du total :\n" + ex.getMessage() );
		}
		Saisies.fermerScanner();
	}// fin main
}// fin classe