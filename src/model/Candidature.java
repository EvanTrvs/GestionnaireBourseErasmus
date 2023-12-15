package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Candidature {

	private int id_candidature;
	private Etudiant etudiant;
	private Bourses bourse;
	
	public Candidature(int id_candidature, Etudiant etudiant, Bourses bourse) {
		super();
		this.id_candidature = id_candidature;
		this.etudiant = etudiant;
		this.bourse = bourse;
	}
	
	public Candidature() {
		//default
	}
	public int getId_candidature() {
		return id_candidature;
	}
	public void setId_candidature(int id_candidature) {
		this.id_candidature = id_candidature;
	}
	public Etudiant getEtudiant() {
		return etudiant;
	}
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	public Bourses getBourse() {
		return bourse;
	}
	public void setBourse(Bourses bourse) {
		this.bourse = bourse;
	}
	
	public static void ajouter(Statement stmt, Candidature candidature) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO Candidature "
				+ "VALUES('"+candidature.getId_candidature()+"','"+candidature.getEtudiant().getNote_moyenne()
				+ "','"+candidature.getBourse().getId_bourse()+"');";
	
		stmt.executeUpdate(insert);
	}
	
	public static void supprimer(Statement stmt, Candidature candidature) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM Candidature "
				+ "WHERE id_candidature = '"+candidature.getId_candidature()+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<Candidature> listeCandidature) throws SQLException {
		String requete = "SELECT id_candidature, numero_etudiant, id_bourse FROM Candidature ";
		Bourses bourse = null;
		Etudiant etudiant = null;

		try (Statement stmt = connection.createStatement()) {
	        ResultSet rs = stmt.executeQuery(requete);
	        while (rs.next())
		    {
		        int id_candidature = rs.getInt("id_candidature");
		        int id_bourse = rs.getInt("id_bourse");
		        String numero_etudiant = rs.getString("numero_etudiant");
		        
		        bourse = new Bourses();
				etudiant = new Etudiant();
				
		        bourse = bourse.getBourse(connection, id_bourse);
		        etudiant = etudiant.getEtudiant(connection, numero_etudiant);
		        
		        listeCandidature.add(new Candidature(id_candidature, etudiant, bourse));
		    }
		}
	}
	
	public Candidature getCandidature(Connection connection, int id_candidature) throws SQLException {
		String requete = "SELECT id_candidature, numero_etudiant, id_bourse FROM Candidature  WHERE id_candidature='"+id_candidature+"'";
		Candidature candidature = null;
		Bourses bourse = null;
		Etudiant etudiant = null;

		try (Statement stmt = connection.createStatement()) {
	        ResultSet rsget = stmt.executeQuery(requete);
	        while (rsget.next())
		    {
		        int id_bourse = rsget.getInt("id_bourse");
		        String numero_etudiant = rsget.getString("numero_etudiant");
		        
		        bourse = new Bourses();
				etudiant = new Etudiant();
		        
		        bourse = bourse.getBourse(connection, id_bourse);
		        
		        etudiant = etudiant.getEtudiant(connection, numero_etudiant);
		        
		        candidature = new Candidature(id_bourse, etudiant, bourse);
		    }
		}
	    return candidature;
	}
}
