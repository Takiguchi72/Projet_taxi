package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import classes.GestionFichier;

public class testGestionFichier {

	@Test
	public void testObtenirIndiceDepartement() {
		int departement = 72, indiceAttendu = 3;
		try {
			assertEquals("Indice obtenu : " + GestionFichier.obtenirIndiceDepartement(departement),
						GestionFichier.obtenirIndiceDepartement(departement),
						indiceAttendu);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}//fin testObtenirIndiceDepartement
	
	@Test
	public void testObtenirTarif()
	{
		int indiceDepartement = 3, periode = 1;
		double  montantPriseEnChargeAttendu = 2.15,
				tarifKilometriqueAttendu = 0.79;
		try {
			double[] tarifsObtenus = GestionFichier.obtenirTarifs(indiceDepartement, periode);
			assertEquals("Tarif obtenu : " + tarifsObtenus[0] + " et " + tarifsObtenus[1], montantPriseEnChargeAttendu == tarifsObtenus[0] && tarifKilometriqueAttendu == tarifsObtenus[1], true);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

}
