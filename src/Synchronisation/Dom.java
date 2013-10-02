package Synchronisation;

import java.util.Iterator;
import java.util.List;

import org.jdom2.Element;

public class Dom {
	
	private Element racine= new Element("utilisateurs");

	public void ajoutUtilisateur(Utilisateur u)
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
	
	public void supprimerUtilisateur(Utilisateur u)
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
}
