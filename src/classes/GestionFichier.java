package classes;

import java.io.BufferedReader;
import java.io.FileReader;

public class GestionFichier {
	protected static final String cheminFichier = "/home/takiguchi/Developpement/Projet_taxi/src/tarifs.txt";
	
	/**
	 * Vérifie que le département passé en paramètre est présent dans le fichier des tarifs
	 * @param Le numéro du département à vérifier
	 * @throws Exception Le département ne figure pas dans le fichier
	 */
	public static void verifierDepartement(int departement) throws Exception
	{
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
				break;
			}
		}

		reader.close();	//On ferme le buffer

		//Si après avoir lu tout le fichier, le département passé en paramètre n'a pas été trouvé, on lève une exception
		if(departementTrouve == false)
		{
			throw new Exception("/!\\ Le département saisi n'est pas prit en compte !");
		}
		
		//Si aucune exception n'est levée, c'est que le département passé en paramètre figure dans le fichier
	}//fin verifierDepartement
	
	/**
	 * Retourne l'indice de la ligne correspondant au département saisi, dans le fichier de tarifs
	 * @param Le numéro du département [int]
	 * @return L'indice du département [int]
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
	
	/**
	 * Retourne la prise en charge et le tarif kilométrique, en fonction de l'indice du département et la période fournis en paramètres
	 * @param L'indice du département dans le fichier [int]
	 * @param Le code de la période [int] 
	 * @return Le montant de la prise en charge et le tarif kilométrique [double[2]]
	 * @throws Exception
	 */
	public static double[] obtenirTarifs(int indiceDepartement, int periode) throws Exception
	{
		double tarifs[] = {0.0, 0.0};
		int i = 0;
		
		//On ouvre le fichier
		BufferedReader reader = new BufferedReader(new FileReader(cheminFichier));
		
		//On créer une variable pour récupérer les lignes du fichier texte
		String ligne = null;
		
		//On va placer le curseur sur la bonne ligne
		do {
			ligne = reader.readLine();
			i++;
		} while (i < (indiceDepartement + 1));
		
		//On découpe la ligne pour séparer les différents tarifs
		String[] tarifsDeLaLigne = ligne.split(",");
		
		//On enregistre le montant de la prise en charge
		tarifs[0] = Double.parseDouble(tarifsDeLaLigne[1]);
		
		//On enregistre le tarif kilométrique
		tarifs[1] = Double.parseDouble(tarifsDeLaLigne[periode + 1]);
		
		return tarifs;
	}//fin obtenirTarif
}//fin classe