import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.net.*;

import Synchronisation.Dom;
import Synchronisation.Utilisateur;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.*;


/**
 * Classe correspondant à un serveur UDP
 * @author Cyril Rabat
 * @version 27/09/2013
 */
public class ServeurUDP {
	
	//static Dom dom;
	
	public static void ParserXML(ByteArrayInputStream bais)
	   {
	      //On crée une instance de SAXBuilder
	      SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé ;)
	         lireMessage(sxb.build(bais));
	      }
	      catch(Exception e){}

	      //On initialise un nouvel élément racine avec l'élément racine du document
	   }
	
	public static void lireMessage(org.jdom2.Document document){
		
		Element message = document.getRootElement();
		
		System.out.println("lecture");
		
		boolean res = demandeConnexion(new Utilisateur(message.getChild("utilisateur").getChild("id").getText(),message.getChild("utilisateur").getChild("mdp").getText()));
		

		if(message.getAttribute("type").getValue().equals("suppression") && res){
				
				System.out.println("suppression");
				suppUtilisateur(message.getChild("idASupp").getText());
				System.out.println(message.getChild("idASupp").getText());
				
		}
		else if (message.getAttribute("type").equals("ajout") && res){
			
		}
		
				
	}
	
	public static boolean demandeConnexion(Utilisateur u){
		boolean res=true;
		
		if (Dom.chercherUtilisateur(u)) res=true;
		
		return res;
	}
	
	public void ajoutUtilisateur(){
		
	}
	
	public static void suppUtilisateur(String id){
		Dom.supprimerUtilisateur(id);
		System.out.println("suppression ok");
	}

    public static void main(String[] args) {
	
    	
    byte[] tampon = new byte[1024];
	DatagramSocket socket = null;

	// Creation du socket
	try {	    
	    socket = new DatagramSocket(2025);
	} catch(Exception e) {
	    System.err.println("Erreur lors de la creation du socket");
	    System.exit(-1);
	}

	// Creation du message
	DatagramPacket msg = new DatagramPacket(tampon, tampon.length);

	// Lecture du message du client
	try {
	    socket.receive(msg);
	    String texte = new String(msg.getData(), 0, msg.getLength());
	    System.out.println(texte);
	    
	    ByteArrayInputStream bais = new ByteArrayInputStream(texte.getBytes());
	    ParserXML(bais);
	    
	    
	} catch(Exception e) {
	    System.err.println("Erreur lors de la reception du message");
	    System.exit(-1);
	}

	// Fermeture du socket
	try {
	    socket.close();
	} catch(Exception e) {
	    System.err.println("Erreur de la fermeture du socket");
	}
    }

}