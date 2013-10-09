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
		message.addContent(idASupp);
	}
	
    public static void main(String[] args) throws IOException {
    
    suppression("kamben0", "ben002", "rabcyr2");
    
    XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
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
	    InetAddress adresse = InetAddress.getByName(null);
	    msg = new DatagramPacket(tampon,
				     tampon.length,
				     adresse,
				     1025);

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

}