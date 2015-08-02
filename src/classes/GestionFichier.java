package classes;

import java.io.BufferedReader;
import java.io.FileReader;

public class GestionFichier {
	protected static final String cheminFichier = "/home/takiguchi/Developpement/Projet_taxi/src/tarifs.txt";
	
	/**
	 * Retourne 
	 * @param departement
	 * @return
	 * @throws Exception
	 */
	public static int obtenirIndiceDepartement(int departement) throws Exception
	{
		int indiceDepartement = -1, i = 0;
		boolean departementTrouve = false;
		
		//On ouvre le fichier
		BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
		
		//On créer une variable pour récupérer les lignes du fichier texte
		String ligne = null;
		
		//On lit le fichier ligne par ligne
		while((ligne = reader.readLine()) != null)
		{
			//On découpe la ligne pour séparer les différents tarifs
			String[] tarifsDeLaLigne = ligne.split(",");
			
			//Si le département de la ligne correspond au département en paramètre, on arrête la boucle
			if(departement == Integer.parseInt(tarifsDeLaLigne[0]))
			{
				departementTrouve = true;
				indiceDepartement = i;
				break;
			}
			
			i++;
		}
		
		reader.close();	//On ferme le buffer
		
		//Si après avoir lu tout le fichier, le département passé en paramètre n'a pas été trouvé, on lève une exception
		if(departementTrouve == false)
		{
			throw new Exception("/!\\ Le département saisi n'est pas prit en compte !");
		}
		
		return indiceDepartement;
	}//fin obtenirIndiceDepartement
	
	public static void afficherFichier()
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
			String ligne = null;
			
			while((ligne = reader.readLine()) != null)
			{
				String[] tarifsDeLaLigne = ligne.split(",");
				
				for(int i=0 ; i < tarifsDeLaLigne.length ; i++)
				{
					System.out.print(tarifsDeLaLigne[i] + "\t");
				}
				System.out.println();
			}
			reader.close();
		} catch (Exception ex) {
			System.out.println("Erreur : " + ex.getMessage());
		}
	}//fin afficherFichier
	
}//fin classe