import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Classe correspondant au thread associe a chaque connexion d'un client.
 * @author Cyril Rabat
 * @version 08/10/2013
 */
public class ThreadConnexion extends Thread {

	private BufferedReader input;
	private PrintWriter output;
	private Socket socketClient;
	private FileInputStream fichier;

	public ThreadConnexion(Socket socketClient) {
		this.socketClient = socketClient;

		// Association d'un flux d'entree et de sortie
		try {
			input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
		} catch(IOException e) {
			System.err.println("Association des flux impossible : " + e);
			System.exit(-1);
		}
	}

	@Override
	public void run() {

		try {
			input.readLine();
		} catch (IOException e) {
			System.err.println("Erreur lors de la lecture : " + e);
		    System.exit(-1);
		}

		// Envoi de 'Fichier bien recu'
		String message = "fichier bien recu";
		System.out.println("Envoi: " + message);
		output.println(message);

		// Fermeture des flux et des sockets
		try {
			input.close();
			output.close();
			socketClient.close();
		} catch(IOException e) {
			System.err.println("Erreur lors de la fermeture des flux et des sockets : " + e);
			System.exit(-1);
		}
	}
}

