package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Destination {

	private String idDestination;
	
	private String libelle;

	public Destination(String idDestination, String libelle) {
		super();
		this.idDestination = idDestination;
		this.libelle = libelle;
	}

	/**
	 * 
	 */
	public Destination() {
		//default
	}

	public String getIdDestination() {
		return idDestination;
	}

	public void setIdDestination(String idDestination) {
		this.idDestination = idDestination;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public static void ajouter(Statement stmt, Destination destination) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO Destination "
				+ "VALUES('"+destination.getIdDestination()+",'"+destination.getLibelle()+"')";
	
		stmt.executeUpdate(insert);
		
	}
	
	public static void supprimer(Statement stmt, String idDestination) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM Destination "
				+ "WHERE id_destination = '"+idDestination+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<Destination> listeDestination) throws SQLException {
		String requete = "SELECT id_destination, nom_destination FROM Destination ";
		
		try (Statement stmt = connection.createStatement()) {
	        ResultSet rsget = stmt.executeQuery(requete);
	        while (rsget.next())
		    {
		    	String id = rsget.getString("id_destination");
		        String libelle = rsget.getString("nom_destination");
		        listeDestination.add(new Destination(id,libelle));
		    }
		}
	}
	
	public Destination getDestination(Connection connection, String id_destination) throws SQLException {
	    String requeteGet = "SELECT id_destination, nom_destination FROM Destination WHERE id_destination='" + id_destination + "'";
	    Destination destinationGet = null;

	    try (Statement stmt = connection.createStatement()) {
	        ResultSet rsget = stmt.executeQuery(requeteGet);
	        String libelle = null;

	        while (rsget.next()) {
	            libelle = rsget.getString("nom_destination");
	        }

	        destinationGet = new Destination(id_destination, libelle);
	    }

	    return destinationGet;
	}
}
