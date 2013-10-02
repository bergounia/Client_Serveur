package Synchronisation;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utilisateur {
	
	private String id;
	private String mdp;
	private String nom;
	private String prenom;
	private static int incr;
	
	public Utilisateur(String unom, String uprenom, String umdp) {
		
		this.mdp = encode(umdp);
		this.nom = unom;
		this.prenom = uprenom;
		this.id=unom.substring(0, 3) + uprenom.substring(0, 3) + this.incr++;
	}
	
	public Utilisateur(String unom, String uprenom) {
		
		this.mdp = encode("P@ssw0rd");
		this.nom = unom;
		this.prenom = uprenom;
		this.id=unom.substring(0, 3) + uprenom.substring(0, 3) + this.incr++;
	}

	public String getId() {
		return id;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", mdp=" + mdp + ", nom=" + nom
				+ ", prenom=" + prenom + "]";
	}
	
	private static String encode(String password)
    {
        byte[] uniqueKey = password.getBytes();
        byte[] hash      = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
    }
	
	public static void main(String [] args){
		
		Utilisateur U = new Utilisateur("Kaminski","Benjamin");
		System.out.println(U);
		
		
	}
	
	

}
