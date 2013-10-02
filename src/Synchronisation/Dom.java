package Synchronisation;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Dom {
	
	private static Element racine= new Element("utilisateurs");
	public static org.jdom2.Document document = new Document(racine);

	public static void ajouterUtilisateur(Utilisateur u)
	{
		Element utilisateur= new Element("utilisateur");
		racine.addContent(utilisateur);
		
		Element id= new Element("id");
		utilisateur.addContent(id);
		id.setText(u.getId());
		
		Element nom= new Element("nom");
		utilisateur.addContent(nom);
		nom.setText(u.getNom());
		
		Element prenom= new Element("prenom");
		utilisateur.addContent(prenom);
		prenom.setText(u.getPrenom());
		
		Element mdp= new Element("mdp");
		utilisateur.addContent(mdp);
		mdp.setText(u.getMdp());
	}
	
	public static void supprimerUtilisateur(Utilisateur u)
	{
		int i=0;
		boolean b= false;
		
		List<Element> listeUtilisateurs= racine.getChildren("utilisateur");

		Iterator<Element> it = listeUtilisateurs.iterator();
		
		while(!b)
		{
			Element courant = (Element)it.next();
			if(!u.getId().equals(courant.getChild("id").getText()))
				i++;
			else
				b= true;
		}
		
		racine.removeContent(i);
	}
	
	public static boolean chercherUtilisateur(Utilisateur u)
	{
		boolean b= false;
		
		List<Element> listeUtilisateurs= racine.getChildren("utilisateur");
		
		Iterator<Element> it = listeUtilisateurs.iterator();
		
		while(it.hasNext() && !b)
		{
			Element courant = (Element)it.next();
			
			if(u.getId().equals(courant.getChild("id").getText()))
				b= true;
		}
		
		return b;
	}
	
	public static void affiche()
	{
		try
		{
			//On utilise ici un affichage classique avec getPrettyFormat()
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, System.out);
		}
		catch (java.io.IOException e){}
	}
	
	public static void enregistre(String fichier)
	{
		try
		{
			//On utilise ici un affichage classique avec getPrettyFormat()
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			//Remarquez qu'il suffit simplement de créer une instance de FileOutputStream
			//avec en argument le nom du fichier pour effectuer la sérialisation.
			sortie.output(document, new FileOutputStream(fichier));
		}
		catch (java.io.IOException e){}
	}
	
	public static void main(String[] args)
	{
		
		Utilisateur u1= new Utilisateur("kamin","ben", "ben002");
		Utilisateur u2= new Utilisateur("bride", "aymeric", "aym001");
		Utilisateur u3= new Utilisateur("rabat", "cyril", "cyril001");
		
		ajouterUtilisateur(u1);
		ajouterUtilisateur(u2);
		ajouterUtilisateur(u3);
		
		supprimerUtilisateur(u2);
		
		//affiche();
		enregistre("utilisateurs.xml");
		
		//Test de la methde chercherUtilisateur(Utilisateur u)
		//System.out.println(chercherUtilisateur(u2));
	}
}
