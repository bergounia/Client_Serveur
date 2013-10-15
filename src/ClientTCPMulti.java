import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Classe correspondant à un client TCP.
 * Le client envoie la chaine 'Bonjour' et lit une reponse de la part du serveur.
 * Le client envoie ensuite la chaine 'Au revoir' et lit une reponse.
 * Le numero de port du serveur est specifie dans la classe ServeurTCPMulti.
 * @author Cyril Rabat
 * @version 07/10/2013
 */
public class ClientTCPMulti {

	public static void main(String[] args) {
	// Creation de la socket
	Socket socket = null;
	try {
	    socket = new Socket("localhost", ServeurTCPMulti.portEcoute);
	} catch(UnknownHostException e) {
	    System.err.println("Erreur sur l'hôte : " + e);
	    System.exit(-1);
	} catch(IOException e) {
	    System.err.println("Creation de la socket impossible : " + e);
	    System.exit(-1);
	}
	
	// Association d'un flux d'entree et de sortie
	BufferedReader input = null;
	PrintWriter output = null;
	try {
	    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	} catch(IOException e) {
	    System.err.println("Association des flux impossible : " + e);
	    System.exit(-1);
	}
	
	//Actions
	
	try {
		output.println(new FileOutputStream("fichier1.txt"));
	} catch (FileNotFoundException e1) {
		e1.printStackTrace();
	}

	// Fermeture des flux et de la socket
	try {
	    input.close();
	    output.close();
	    socket.close();
	} catch(IOException e) {
	    System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
	    System.exit(-1);
	}
    }

}