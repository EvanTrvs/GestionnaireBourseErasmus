package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Enseignant {

	private int id_enseignant;
	private String nom;
	private String prenom;

	public Enseignant(int id_enseignant, String nom, String prenom) {
		super();
		this.id_enseignant = id_enseignant;
		this.nom = nom;
		this.prenom = prenom;
	}

	/**
	 * 
	 */
	public Enseignant() {
		// default
	}

	public int getId_enseignant() {
		return id_enseignant;
	}

	public void setId_enseignant(int id_enseignant) {
		this.id_enseignant = id_enseignant;
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

	public static void ajouter(Statement stmt, Enseignant enseignant) throws SQLException {
		// Requêtes d'insertion des données.
		String insert = "INSERT INTO Enseignant " + "VALUES('" + enseignant.getId_enseignant() + "','"
				+ enseignant.getNom() + "','" + enseignant.getPrenom() + "');";

		stmt.executeUpdate(insert);
	}

	public static void supprimer(Statement stmt, Enseignant enseignant) throws SQLException {
		// Requêtes d'insertion des données.
		String delete = "DELETE FROM Enseignant " + "WHERE numero_enseignant = '" + enseignant.getId_enseignant() + "'";

		stmt.executeUpdate(delete);
	}

	public static void load(Connection connection, ArrayList<Enseignant> listeEnseignant) throws SQLException {
		String requete = "SELECT numero_enseignant, nom, prenom FROM Enseignant ";

		try (Statement stmt = connection.createStatement()) {
	        ResultSet rs = stmt.executeQuery(requete);
			while (rs.next()) {
				int id_enseignant = rs.getInt("numero_enseignant");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				listeEnseignant.add(new Enseignant(id_enseignant, nom, prenom));
			}
		}
	}
	
	public Enseignant getEnseignant(Connection connection, int id_enseignant) throws SQLException {
		String requeteGet = "SELECT numero_enseignant, nom, prenom FROM Enseignant WHERE numero_enseignant='"+id_enseignant+"'";
		Enseignant enseignant = null;
		
		try (Statement stmt = connection.createStatement()) {
	        ResultSet rsget = stmt.executeQuery(requeteGet);
	        int idEnseignant = 999999;
	        String nom = null;
	        String prenom = null;
		    while (rsget.next())
		    {
		        idEnseignant = rsget.getInt("numero_enseignant");
		        nom = rsget.getString("nom");
		        prenom = rsget.getString("prenom");
		    }
		    enseignant = new Enseignant(idEnseignant, nom, prenom);
		}
	    return enseignant;
	}

}
