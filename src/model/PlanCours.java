package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PlanCours {

	private Candidature candidature;
	private Enseignement enseignement;
	public PlanCours(Candidature candidature, Enseignement enseignement) {
		super();
		this.candidature = candidature;
		this.enseignement = enseignement;
	}
	
	public PlanCours() {
		//default
	}
	
	public Candidature getCandidature() {
		return candidature;
	}
	public void setCandidature(Candidature candidature) {
		this.candidature = candidature;
	}
	public Enseignement getEnseignement() {
		return enseignement;
	}
	public void setEnseignement(Enseignement enseignement) {
		this.enseignement = enseignement;
	}
	
	public static void ajouter(Statement stmt, PlanCours planCours) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO PlanCours "
				+ "VALUES('"+planCours.getCandidature().getId_candidature()+"','"+planCours.getEnseignement().getId_enseignements()+"');";
	
		stmt.executeUpdate(insert);
		
	}
	
	public static void supprimer(Statement stmt, PlanCours planCours) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM PlanCours "
				+ "WHERE id_candidature = '"+planCours.getCandidature().getId_candidature()
				+ "' AND id_Enseignement = '"+planCours.getEnseignement().getId_enseignements()+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<PlanCours> listePlanCours) throws SQLException {
		String requete = "SELECT id_candidature, id_Enseignement FROM PlanCours ";
		Candidature candidature = null;
		Enseignement enseignement = null;

		 try (Statement stmt = connection.createStatement()) {
		        ResultSet rs = stmt.executeQuery(requete);
		        while (rs.next())
		    {
		        int id_candidature = rs.getInt("id_candidature");
		        int id_Enseignement = rs.getInt("id_Enseignement");
		        
		        candidature = new Candidature();
				enseignement = new Enseignement();
		        
		        candidature = candidature.getCandidature(connection, id_candidature);
		        enseignement = enseignement.getEnseignement(connection, id_Enseignement);
		        
		        listePlanCours.add(new PlanCours(candidature, enseignement));
		    }
		 }
	}
	
}
