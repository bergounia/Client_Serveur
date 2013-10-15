import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe correspondant à un serveur TCP multithreade.
 * Le client envoie la chaine 'Bonjour' et lit une reponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une reponse.
 * Le numero de port du serveur est specifie dans la classe ServeurTCP.
 * @author Cyril Rabat
 * @version 07/10/2013
 */
public class ServeurTCPMulti {

    public static final int portEcoute = 5003;

    public static void main(String[] args) {
	// Creation de la socket serveur
	ServerSocket socketServeur = null;
	try {	
	    socketServeur = new ServerSocket(portEcoute);
	} catch(IOException e) {
	    System.err.println("Creation de la socket impossible : " + e);
	    System.exit(-1);
	}

	// Attente des connexions des clients
	try {
	    Socket socketClient;
	    while(true) {
		socketClient = socketServeur.accept();
		ThreadConnexion t = new ThreadConnexion(socketClient);
		t.start();
	    }
	} catch(IOException e) {
	    System.err.println("Erreur lors de l'attente d'une connexion : " + e);
	    System.exit(-1);
	}
	
	// Fermeture de la socket
	try {
	    socketServeur.close();
	} catch(IOException e) {
	    System.err.println("Erreur lors de la fermeture de la socket : " + e);
	    System.exit(-1);
	}
    }

}