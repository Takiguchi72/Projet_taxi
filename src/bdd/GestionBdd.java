package bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GestionBdd {
	public static Connection connexion = getInstance();
	
	/**
	 * Instancie la connexion avec la base de données postgreSQL
	 * 
	 * @return L'objet correspondant à la connexion [java.sql.Connexion]
	 */
	public static Connection getInstance()
	{
		// Localisation du driver JDBC
		try {
			Class.forName( "org.postgresql.Driver" );
		} catch ( Exception e ) {
			System.out.println( "Impossible de trouver le pilote JDBC...\nOù est votre pilote JDBC pour PostgreSQL ? "
					+ "Rentrez son chemin dans la liste des bibliothèques JRE !" );
			e.printStackTrace();
		}// Fin catch
		 // Création d'un objet de type Connection
		Connection connexion = null;
		try {
			// Connexion à la basse
			connexion = DriverManager.getConnection( "jdbc:postgresql://" + DonneesConnexion.getAddress()
					+ "/fthierry", DonneesConnexion.getLogin(), DonneesConnexion.getMdp() );
		} catch ( Exception e ) {
			System.out.println( "Erreur lors de la connexion à la base de donnée :\n" + e );
		}// Fin catch
		return connexion;
	}// fin getInstance
	
	/**
	 * Vérifie que le département passé en paramètre figure bien dans la base de
	 * données
	 * 
	 * @param departement Le numéro du département à vérifier [int]
	 * @throws Exception Si le département passé en paramètre ne figure pas dans
	 *             la base de données [Exception]
	 */
	public static void verifierDepartement( int departement ) throws Exception
	{
		boolean departementTrouve = false;
		
		try {
			PreparedStatement prepare = connexion.prepareStatement( "SELECT departement FROM \"Projet_taxi\".tarif" );
			
			ResultSet result = prepare.executeQuery(); // On exécute la requête
			
			// On parcourt le résultat renvoyé par postgreSQL
			while ( result.next() )
			{
				// Si le département saisi correspond à un des départements de
				// la bdd, on stoppe la boucle et on met un booleen à true
				if ( result.getInt( "departement" ) == departement )
				{
					departementTrouve = true;
					break;
				}
			}
		} catch ( Exception ex ) {
			System.err.println( "Une erreur est survenue lors de l'exécution de la requête :\n" + ex.getMessage() );
		}
		
		// Si après avoir parcouru le résultat de la base, le département passé
		// en paramètre n'a pas été trouvé, on lève une exception
		if ( departementTrouve == false )
		{
			throw new Exception( "/!\\ Le département saisi n'est pas prit en compte !" );
		}
		
		// Si aucune exception n'est levée, c'est que le département passé en
		// paramètre figure dans la base de données
	}// fin verifierDepartement
	
	/**
	 * Retourne la prise en charge et le tarif kilométrique, en fonction de
	 * l'indice du département et la période fournis en paramètres
	 * 
	 * @param departement Le département saisi [int]
	 * @param periode Le numéro de la période [int]
	 * @return Le montant de la prise en charge et le tarif kilométrique
	 *         correspondant à la période, sous la forme d'un tableau de nombres
	 *         entier [ double[] ]
	 * @throws Exception Problème d'exécution de la requête [Exception]
	 */
	public static double[] obtenirTarifs( int departement, int periode ) throws Exception
	{
		double tarifs[] = { 0.0, 0.0 };
		
		// En fonction de la période, on va modifier une partie de la requête
		// pour récupérer le bon champ
		String attributRecupere = "";
		
		switch ( periode )
		{
			case 1:
				attributRecupere = "km_jour_semaine";
				break;
			case 2:
				attributRecupere = "km_nuit_semaine";
				break;
			case 3:
				attributRecupere = "km_jour_weekend";
				break;
			default:
				attributRecupere = "km_nuit_weekend";
				break;
		}
		
		try {
			// On effectue une requête qui va retourner le montant de la prise
			// en charge et le tarif kilométrique correspondant à la période
			PreparedStatement prepare = connexion.prepareStatement( "SELECT prise_en_charge," + attributRecupere
					+ " FROM \"Projet_taxi\".tarif WHERE departement=?" );
			
			prepare.setInt( 1, departement ); // On renseigne le paramètre de la
											  // requête préparée
			ResultSet result = prepare.executeQuery(); // On exécute la requête
			
			// On s'assure que la base nous a retourné un résultat
			if ( result.next() )
			{
				tarifs[0] = result.getDouble( "prise_en_charge" );
				tarifs[1] = result.getDouble( attributRecupere );
			}
			else
			{
				throw new Exception( "La requête n'a retourné aucun résultat !" );
			}
		} catch ( Exception ex ) {
			System.err.println( "Une erreur est survenue lors de l'exécution de la requête :\n" + ex.getMessage() );
		}
		
		return tarifs;
	}// fin obtenirTarifs
}// fin classe