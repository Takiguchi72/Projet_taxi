package pointDEntree;

import vues.FenetrePrincipale;

public class ProjetTaxi {
	/**
	 * Point d'entrée du programme
	 * 
	 * @param args
	 */
	public static void main( String[] args ) {
		try {
			new FenetrePrincipale();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}// fin main
}// fin classe