package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import bdd.GestionBdd;

public class testGestionBdd {
	@Test
	public void testObtenirTarifs()
	{
		int departement = 72, periode = 1;
		double montantPriseEnChargeAttendu = 2.15, tarifKilometriqueAttendu = 0.79;
		try {
			double[] tarifsObtenus = GestionBdd.obtenirTarifs( departement, periode );
			assertEquals( "\nTarif obtenu : " + tarifsObtenus[0] + " et " + tarifsObtenus[1] + "\n",
					montantPriseEnChargeAttendu == tarifsObtenus[0] && tarifKilometriqueAttendu == tarifsObtenus[1],
					true );
		} catch ( Exception ex ) {
			ex.printStackTrace();
		}
	}
}
