package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Evaluation {
	
	private Enseignant enseignant;
	private Candidature candidature;
	private int note;
	
	public Evaluation(Enseignant enseignant, Candidature candidature, int note) {
		super();
		this.enseignant = enseignant;
		this.candidature = candidature;
		this.note = note;
	}
	
	public Evaluation() {
		//default
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public Candidature getCandidature() {
		return candidature;
	}

	public void setCandidature(Candidature candidature) {
		this.candidature = candidature;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public static void ajouter(Statement stmt, Evaluation evaluation) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO Evaluation "
				+ "VALUES('"+evaluation.getEnseignant().getId_enseignant()+"','"+evaluation.getCandidature().getId_candidature()
				+ "','"+evaluation.getNote()+"');";
	
		stmt.executeUpdate(insert);
		
	}
	
	public static void supprimer(Statement stmt, Evaluation evaluation) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM Evaluation "
				+ "WHERE numero_enseignant = '"+evaluation.getEnseignant().getId_enseignant()
				+ "' AND id_candidature = '"+evaluation.getCandidature().getId_candidature()+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<Evaluation> listeEvaluation) throws SQLException {
		String requete = "SELECT numero_enseignant, id_candidature, note FROM Evaluation ";
		Candidature candidature = null;
		Enseignant enseignant = null;

		 try (Statement stmt = connection.createStatement()) {
		        ResultSet rs = stmt.executeQuery(requete);
		        while (rs.next())
		    {
		        int id_enseignant = rs.getInt("numero_enseignant");
		        int id_destination = rs.getInt("id_candidature");
		        int note = rs.getInt("note");
		        
		        candidature = new Candidature();
				enseignant = new Enseignant();
		        
		        candidature = candidature.getCandidature(connection, id_destination);
		        enseignant = enseignant.getEnseignant(connection, id_enseignant);
		        
		        listeEvaluation.add(new Evaluation(enseignant, candidature, note));
		    }
		 }
	}
}
