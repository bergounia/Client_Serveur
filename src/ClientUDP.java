import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import Synchronisation.Utilisateur;

/**
 * Classe correspondant à un client UDP
 * @author Cyril Rabat
 * @version 27/09/2013
 */
public class ClientUDP {

	private static Element message= new Element("message");
	public static org.jdom2.Document document = new Document(message);
	
	public static void connexion(String identifiant, String motDePasse)
	{	
		Element utilisateur= new Element("utilisateur");
		message.addContent(utilisateur);
		
		Element id= new Element("id");
		id.setText(identifiant);
		utilisateur.addContent(id);
		
		Element mdp= new Element("mdp");
		mdp.setText(Utilisateur.encode(motDePasse));
		utilisateur.addContent(mdp);
	}
	
	public static void suppression(String idMoi, String mdpMoi, String persASupp)
	{
		connexion(idMoi,mdpMoi);
		
		Attribute type= new Attribute("type", "suppression");
		message.setAttribute(type);
		
		Element idASupp= new Element("idASupp");
		idASupp.setText(persASupp);
		message.addContent(idASupp);
	}
	
	public static void ajout(String idMoi, String mdpMoi, String nomPersAjout, String prenomPersAjout, String mdpPersAjout)
	{
		connexion(idMoi,mdpMoi);
		
		Attribute type= new Attribute("type", "ajout");
		message.setAttribute(type);
		
		Element utilisateurAjoute= new Element("utilisateurAjoute");
		message.addContent(utilisateurAjoute);
		
		Element nom= new Element("nom");
		nom.setText(nomPersAjout);
		utilisateurAjoute.addContent(nom);
		
		Element prenom= new Element("prenom");
		prenom.setText(prenomPersAjout);
		utilisateurAjoute.addContent(prenom);
		
		Element mdp= new Element("mdp");
		mdp.setText(Utilisateur.encode(mdpPersAjout));
		utilisateurAjoute.addContent(mdp);
	}
	
	public static void envoyer() throws IOException
	{
		XMLOutputter sortie = new XMLOutputter(Format.getCompactFormat());
	    ByteArrayOutputStream b = new ByteArrayOutputStream();
	    
	    sortie.output(document,b);
	    
		byte[] tampon = b.toByteArray();
		DatagramSocket socket = null;

		// Creation du socket
		try {
		    socket = new DatagramSocket();
		} catch(Exception e) {
		    System.err.println("Erreur lors de la creation du socket");
		    System.exit(-1);
		}

		// Creation du message
		DatagramPacket msg = null;
		try {
		    InetAddress adresse = InetAddress.getByName("192.168.43.9");
		    msg = new DatagramPacket(tampon,
					     tampon.length,
					     adresse,
					     2025);

		} catch(Exception e) {
		    System.err.println("Erreur lors de la creation du message");
		    System.exit(-1);
		}

		// Envoi du message
		try {
		    socket.send(msg);
		} catch(Exception e) {
		    System.err.println("Erreur lors de l'envoi du message");
		    System.exit(-1);
		}

		// Fermeture du socket
		try {
		    socket.close();
		} catch(Exception e) {
		    System.err.println("Erreur lors de la fermeture du socket");
		    System.exit(-1);
		}
	}
	
	public static void console()
	{
		System.out.println("Que voulez-vous faire ?");
		System.out.println("1) Ajouter un utilisateur");
		System.out.println("2) Supprimer un utilisateur");
	}
	
    public static void main(String[] args) throws IOException{
    
	    suppression("kamben0", "ben002", "rabcyr2");
	    //ajout("kamben0", "ben002", "Vite", "Benchamin","benchamin");
	    
	    //Envoyer du paquet
	    envoyer();
    }
}