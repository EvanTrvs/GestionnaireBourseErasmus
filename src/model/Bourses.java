package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bourses {

	private int id_bourse;
	private int postes_disponible;
	private Destination destination;
	private Enseignant enseignant;

	public Bourses(int id_bourse, int postes_disponible, Destination destination, Enseignant enseignant) {
		super();
		this.id_bourse = id_bourse;
		this.postes_disponible = postes_disponible;
		this.destination = destination;
		this.enseignant = enseignant;
	}
	
	public Bourses() {
		//default
	}

	public int getId_bourse() {
		return id_bourse;
	}

	public void setId_bourse(int id_bourse) {
		this.id_bourse = id_bourse;
	}

	public int getPostes_disponible() {
		return postes_disponible;
	}

	public void setPostes_disponible(int postes_disponible) {
		this.postes_disponible = postes_disponible;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	public static void ajouter(Statement stmt, Bourses bourse) throws SQLException {
		//Requêtes d'insertion des données.
		String insert = "INSERT INTO Bourses "
				+ "VALUES('"+bourse.getId_bourse()+"','"+bourse.getPostes_disponible()
				+ "','"+bourse.getDestination().getIdDestination()+"','"+bourse.getEnseignant().getId_enseignant()+"');";
		
	
		stmt.executeUpdate(insert);
		
	}
	
	public static void supprimer(Statement stmt, Bourses bourse) throws SQLException {
		//Requêtes d'insertion des données.
		String delete = "DELETE FROM Bourses "
				+ "WHERE id_bourse = '"+bourse.getId_bourse()+"'";
		
		stmt.executeUpdate(delete);
	}
	
	public static void load(Connection connection, ArrayList<Bourses> listeBourse) throws SQLException {
	    String requete = "SELECT id_bourse, postes_disponible, id_destination, numero_enseignant FROM Bourses ";
	    Destination destination = null;
	    Enseignant enseignant = null;

	    try (Statement stmt = connection.createStatement()) {
	        ResultSet rs = stmt.executeQuery(requete);
	        while (rs.next()) {
	            int id_bourse = rs.getInt("id_bourse");
	            int postes_disponible = rs.getInt("postes_disponible");
	            String id_destination = rs.getString("id_destination");
	            int id_enseignant = rs.getInt("numero_enseignant");
	            
	            destination = new Destination(); // Créez une nouvelle instance de Destination
		        enseignant = new Enseignant(); // Créez une nouvelle instance d'Enseignant

	            destination = destination.getDestination(connection, id_destination);
	            enseignant = enseignant.getEnseignant(connection, id_enseignant);

	            listeBourse.add(new Bourses(id_bourse, postes_disponible, destination, enseignant));
	        }
	    }
	}
	
	public Bourses getBourse(Connection connection, int id_bourse) throws SQLException {
		String requete = "SELECT id_bourse, postes_disponible, id_destination, numero_enseignant FROM Bourses WHERE id_bourse='"+id_bourse+"'";
		Bourses bourse = null;
		Destination destination = null;
		Enseignant enseignant = null;
		try (Statement stmt = connection.createStatement()) {
	        ResultSet rsget = stmt.executeQuery(requete);
		    while (rsget.next())
		    {
		        String id_destination = rsget.getString("id_destination");
		        int postes_disponible = rsget.getInt("postes_disponible");
		        int numero_enseignant = rsget.getInt("numero_enseignant");
		        
		        destination = new Destination(); // Créez une nouvelle instance de Destination
		        enseignant = new Enseignant(); // Créez une nouvelle instance d'Enseignant
		        
		        destination = destination.getDestination(connection, id_destination);
		        enseignant = enseignant.getEnseignant(connection, numero_enseignant);
		        
		        bourse = new Bourses(id_bourse, postes_disponible, destination, enseignant);
		    }
		}
	    return bourse;
	}
}
