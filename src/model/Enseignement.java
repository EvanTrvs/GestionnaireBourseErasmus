package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Enseignement {

	private int id_enseignements;
	private String libelle;
	private int ECTS;
	private int nb_heures;
	private Destination destination;
	public Enseignement(int id_enseignements, String libelle, int eCTS, int nb_heures, Destination destination) {
		super();
		this.id_enseignements = id_enseignements;
		this.libelle = libelle;
		ECTS = eCTS;
		this.nb_heures = nb_heures;
		this.destination = destination;
	}
	
	public Enseignement() {
		//default
	}
	
	public int getId_enseignements() {
		return id_enseignements;
	}
	public void setId_enseignements(int id_enseignements) {
		this.id_enseignements = id_enseignements;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public int getECTS() {
		return ECTS;
	}
	public void setECTS(int eCTS) {
		ECTS = eCTS;
	}
	public int getNb_heures() {
		return nb_heures;
	}
	public void setNb_heures(int nb_heures) {
		this.nb_heures = nb_heures;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	
	public static void ajouter(Statement stmt, Enseignement enseignements) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO Enseignement "
				+ "VALUES('"+enseignements.getId_enseignements()+"','"+enseignements.getLibelle()
				+ "','"+enseignements.getECTS()+ "','"+enseignements.getNb_heures()
				+ "','"+enseignements.getDestination().getIdDestination()+"');";
	
		stmt.executeUpdate(insert);
		
	}
	
	public static void supprimer(Statement stmt, Enseignement enseignements) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM Enseignement "
				+ "WHERE id_enseignement = '"+enseignements.getId_enseignements()+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<Enseignement> listeEnseignements) throws SQLException {
		String requete = "SELECT id_enseignement, nom, credits_reconnus, volume_horaire, id_destination FROM Enseignement ";
		Destination destination = null;

		try (Statement stmt = connection.createStatement()) {
	        ResultSet rs = stmt.executeQuery(requete);
	        while (rs.next())
		    {
		        int id_enseignements = rs.getInt("id_enseignement");
		        String libelle = rs.getString("nom");
		        int ECTS = rs.getInt("credits_reconnus");
		        int volume_horaire = rs.getInt("volume_horaire");
		        String id_destination = rs.getString("id_destination");
		        
		        destination = new Destination();
		        
		        destination = destination.getDestination(connection, id_destination);
		        
		        listeEnseignements.add(new Enseignement(id_enseignements, libelle, ECTS, volume_horaire, destination));
		    }
		}
	}
	
	public Enseignement getEnseignement(Connection connection, int id_enseignements) throws SQLException {
		String requete = "SELECT id_enseignement, nom, credits_reconnus, volume_horaire, id_destination FROM Enseignement WHERE id_enseignements='"+id_enseignements+"'";
		Enseignement enseignements = null;
		Destination destination = null;

		 try (Statement stmt = connection.createStatement()) {
		        ResultSet rsget = stmt.executeQuery(requete);
		        while (rsget.next())
		    {
		    	int id_enseignements1 = rsget.getInt("id_enseignement");
		        String libelle = rsget.getString("nom");
		        int ECTS = rsget.getInt("credits_reconnus");
		        int volume_horaire = rsget.getInt("volume_horaire");
		        String id_destination = rsget.getString("id_destination");
		        
		        destination = new Destination(); 
		       
		        destination = destination.getDestination(connection, id_destination);
		        
		        enseignements = new Enseignement(id_enseignements1, libelle, ECTS, volume_horaire, destination);
		    }
		 }
	    return enseignements;
	}
}
