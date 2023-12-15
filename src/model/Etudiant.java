package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Etudiant {

	
	private String numero_etudiant;
	private String nom_etudiant;
	private String prenom_etudiant;
	private float note_moyenne;
	
	public Etudiant(String numero_etudiant, String nom_etudiant, String prenom_etudiant, float note_moyenne) {
		super();
		this.numero_etudiant = numero_etudiant;
		this.nom_etudiant = nom_etudiant;
		this.prenom_etudiant = prenom_etudiant;
		this.note_moyenne = note_moyenne;
	}

	/**
	 * 
	 */
	public Etudiant() {
		//default
	}

	public String getNumero_etudiant() {
		return numero_etudiant;
	}

	public void setNumero_etudiant(String numero_etudiant) {
		this.numero_etudiant = numero_etudiant;
	}

	public String getNom_etudiant() {
		return nom_etudiant;
	}

	public void setNom_etudiant(String nom_etudiant) {
		this.nom_etudiant = nom_etudiant;
	}

	public String getPrenom_etudiant() {
		return prenom_etudiant;
	}

	public void setPrenom_etudiant(String prenom_etudiant) {
		this.prenom_etudiant = prenom_etudiant;
	}

	public float getNote_moyenne() {
		return note_moyenne;
	}

	public void setNote_moyenne(float note_moyenne) {
		this.note_moyenne = note_moyenne;
	}
	
	public static void ajouter(Statement stmt, Etudiant etudiant) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO Etudiant "
				+ "VALUES('"+etudiant.getNumero_etudiant()+"','"+etudiant.getNom_etudiant()
				+ "','"+etudiant.getPrenom_etudiant()+"','"+etudiant.getNote_moyenne()+"');";
		
	
		stmt.executeUpdate(insert);
		
	}
	
	public static void supprimer(Statement stmt, Etudiant etudiant) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM Etudiant "
				+ "WHERE numero_Etudiant = '"+etudiant.getNumero_etudiant()+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<Etudiant> listeEtudiant) throws SQLException {
		String requete = "SELECT numero_Etudiant, nom, prenom, note_moyenne FROM Etudiant ";
		
		try (Statement stmt = connection.createStatement()) {
	        ResultSet rs = stmt.executeQuery(requete);
	        while (rs.next())
		    {
		        String numero_etudiant = rs.getString("numero_etudiant");
		        String nom = rs.getString("nom");
		        String prenom = rs.getString("prenom");
		        float note_moyenne = rs.getFloat("note_moyenne");
		        listeEtudiant.add(new Etudiant(numero_etudiant,nom, prenom, note_moyenne));
		    }
		}
	}

	public Etudiant getEtudiant(Connection connection, String numero_Etudiant) throws SQLException {
		String requete = "SELECT numero_etudiant, nom, prenom, note_moyenne FROM Etudiant WHERE numero_etudiant='"+numero_Etudiant+"'";
		Etudiant etudiant = null;
		
		try (Statement stmt = connection.createStatement()) {
	        ResultSet rsget = stmt.executeQuery(requete);
	        while (rsget.next())
	        {
	    	 String numero_etudiant = rsget.getString("numero_etudiant");
		     String nom = rsget.getString("nom");
		     String prenom = rsget.getString("prenom");
		     float note_moyenne = rsget.getFloat("note_moyenne");
		     etudiant = new Etudiant(numero_etudiant,nom, prenom, note_moyenne);
	        }
		}
	return etudiant;
	}

}
