package pointDEntree;

import java.text.DecimalFormat;
import classes.GestionFichier;
import classes.Saisies;

public class ProjetTaxi {
	
	/**
	 * Arrondi un nombre à deux décimales derrière la virgule
	 * @param Le nombre à arrondir [double]
	 * @return Le nombre arrondi [String]
	 */
	public static String arrondirAuCentime(double nombre)
	{
		DecimalFormat f = new DecimalFormat();
		f.setMaximumFractionDigits(2);
		return f.format(nombre);
	}
	
	/**
	 * Retourne le prix total à payer en fonction du département, de la période et de la distance
	 * @param Le numéro du departement [int]
	 * @param Le numéro de la periode [int]
	 * @param La distance parcourue [int]
	 * @return Le prix total à payer [double]
	 */
	public static Double calculerTotal(int departement, int periode, int distance) throws Exception
	{
		//On récupère l'indice du département dans le fichier, pour pouvoir utiliser les tarifs correspondants
		int indiceDepartement = GestionFichier.obtenirIndiceDepartement(departement);
				
		//On récupère le montant de prise en charge et le tarif en fonction du département et de la période saisis
		double[] tarifsUtilises = GestionFichier.obtenirTarifs(indiceDepartement, periode);

		//On retourne le montant à payer calculé
		return tarifsUtilises[0] + distance * tarifsUtilises[1];
	}//fin calculerTotal
	
	/**
	 * Point d'entrée du programme
	 * @param args
	 */
	public static void main(String[] args) {
		//On affiche une bannière pour la décorration de l'application
		System.out.println("   =========================\n   = T a x i - t a r i f s =\n   =========================");
		
		//Demander le numéro d'appartement
		int departement = Saisies.saisirDepartement();
		
		//Demander si c'était un voyage en semaine ou le week-end
		int periode = Saisies.saisirPeriode();
		
		//Demander la distance
		int distance = Saisies.saisirDistance();
		
		//Afficher le prix à payer
		// Note : Pour je ne sais quelle raison, la JVM ne calcule pas correctement et me sortait des nombre à 6-7 décimales.
		//		  J'ai donc réalisé une petite méthode arrondissant le total à payer.
		try {
			System.out.println("\nVoici le montant à payer : " + arrondirAuCentime(calculerTotal(departement, periode, distance)) + " €");
		} catch (Exception ex) {
			System.out.println("\n/!\\ Une erreur est survenue lors du calcul du total :\n" + ex.getMessage());
		}
		Saisies.fermerScanner();
	}//fin main
}//fin classe