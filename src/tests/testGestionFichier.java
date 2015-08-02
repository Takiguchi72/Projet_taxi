package tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import classes.GestionFichier;

public class testGestionFichier {

	@Test
	public void testObtenirIndiceDepartement() {
		int departement = 72;
		int indiceAttendu = 3;
		try {
			assertEquals("Indice obtenu : " + GestionFichier.obtenirIndiceDepartement(departement),
						GestionFichier.obtenirIndiceDepartement(departement),
						indiceAttendu);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}//fin testObtenirIndiceDepartement

}
