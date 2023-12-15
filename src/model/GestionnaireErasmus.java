package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Hugo Hovhannessian
 */
public class GestionnaireErasmus {

	/**
	 * objects contain
	 */
	 public ArrayList<Bourses> ListBourse;
	 public ArrayList<Candidature> ListCandidature;
	 public ArrayList<Destination> ListDestination;
	 public ArrayList<Enseignant> ListEnseignant;
	 public ArrayList<Enseignement> ListEnseignement;
	 public ArrayList<Etudiant> ListEtudiant;
	 public ArrayList<Evaluation> ListEvaluation;
	    
	 static Statement stmt;
	 
	 static Connection connect;

	 public GestionnaireErasmus() {
	    	super();
	    	ListBourse = new ArrayList<Bourses>();
	    	ListCandidature = new ArrayList<Candidature>();
	    	ListDestination = new ArrayList<Destination>();
	    	ListEnseignant = new ArrayList<Enseignant>();
	    	ListEnseignement = new ArrayList<Enseignement>();
	    	ListEtudiant = new ArrayList<Etudiant>();
	    	ListEvaluation = new ArrayList<Evaluation>();		
	    }


	/**
	 * Méthode de récupération des données de la base de données.
	 * 
	 * @throws SQLException
	 */
	public void load() throws SQLException {
		
		//clear every stored objects
    	ListBourse.clear();
    	ListCandidature.clear();
    	ListDestination.clear();
    	ListEnseignant.clear();
    	ListEnseignement.clear();
    	ListEtudiant.clear();
    	ListEvaluation.clear();
    	
		Destination.load(connect, ListDestination);
		System.out.println("Destination Loaded");
		
		Etudiant.load(connect, ListEtudiant);
		System.out.println("Etudiant Loaded");
		
		Enseignant.load(connect, ListEnseignant);
		System.out.println("Enseignant Loaded");

		// Load des classes un peu plus complexe.
		Bourses.load(connect, ListBourse);
		System.out.println("Bourses Loaded");
		
		Candidature.load(connect, ListCandidature);
		System.out.println("Candidature Loaded");
		
		Enseignement.load(connect, ListEnseignement);
		System.out.println("Enseignements Loaded");
		
		Evaluation.load(connect, ListEvaluation);
		System.out.println("Evaluation Loaded");
	}
	
	public void kill() {
		try {
			stmt.close();
		} catch (Exception exc) {
			// exc.printStackTrace();
		}
	}
    
    public static void createTablesIfNotExist() {
        try {
            DatabaseMetaData metaData = stmt.getConnection().getMetaData();

            // Check if each table exists, and create it if not
            createTableIfNotExists(metaData, "Etudiant", "CREATE TABLE Etudiant("
            		+ "numero_etudiant SMALLINT,"
            		+ "nom VARCHAR(50) NOT NULL,"
            		+ "prenom VARCHAR(50) NOT NULL,"
            		+ "note_moyenne DECIMAL(4,2) NOT NULL,"
            		+ "PRIMARY KEY(numero_etudiant)"
            		+ ");");
            createTableIfNotExists(metaData, "Destination", "CREATE TABLE Destination("
            		+ "id_destination VARCHAR(4),"
            		+ "nom_destination VARCHAR(255) NOT NULL,"
            		+ "PRIMARY KEY(id_destination)"
            		+ ");");
            createTableIfNotExists(metaData, "Enseignant", "CREATE TABLE Enseignant("
            		+ "numero_enseignant SMALLINT,"
            		+ "nom VARCHAR(50) NOT NULL,"
            		+ "prenom VARCHAR(50) NOT NULL,"
            		+ "PRIMARY KEY(numero_enseignant)"
            		+ ");");
            createTableIfNotExists(metaData, "Enseignement", "CREATE TABLE Enseignement("
            		+ "id_enseignement INT NOT NULL AUTO_INCREMENT,"
            		+ "nom VARCHAR(255) NOT NULL,"
            		+ "credits_reconnus TINYINT NOT NULL,"
            		+ "volume_horaire SMALLINT NOT NULL,"
            		+ "id_destination VARCHAR(4) NOT NULL,"
            		+ "PRIMARY KEY(id_enseignement),"
            		+ "FOREIGN KEY(id_destination) REFERENCES Destination(id_destination) ON DELETE CASCADE"
            		+ ");");
            createTableIfNotExists(metaData, "Bourses", "CREATE TABLE Bourses("
            		+ "id_bourse INT NOT NULL AUTO_INCREMENT,"
            		+ "postes_disponible INT NOT NULL,"
            		+ "id_destination VARCHAR(4) NOT NULL,"
            		+ "numero_enseignant SMALLINT NOT NULL,"
            		+ "PRIMARY KEY(id_bourse),"
            		+ "FOREIGN KEY(id_destination) REFERENCES Destination(id_destination) ON DELETE CASCADE,"
            		+ "FOREIGN KEY(numero_enseignant) REFERENCES Enseignant(numero_enseignant) ON DELETE CASCADE"
            		+ ");");
            createTableIfNotExists(metaData, "Candidature", "CREATE TABLE Candidature("
            		+ "id_candidature INT NOT NULL AUTO_INCREMENT,"
            		+ "numero_etudiant SMALLINT NOT NULL,"
            		+ "id_bourse INT NOT NULL,"
            		+ "PRIMARY KEY(id_candidature),"
            		+ "FOREIGN KEY(numero_etudiant) REFERENCES Etudiant(numero_etudiant)  ON DELETE CASCADE,"
            		+ "FOREIGN KEY(id_bourse) REFERENCES Bourses(id_bourse) ON DELETE CASCADE"
            		+ ");");
            createTableIfNotExists(metaData, "PlanCours", "CREATE TABLE PlanCours("
            		+ "id_candidature INT,"
            		+ "id_enseignement INT,"
            		+ "PRIMARY KEY(id_candidature, id_enseignement),"
            		+ "FOREIGN KEY(id_candidature) REFERENCES Candidature(id_candidature) ON DELETE CASCADE,"
            		+ "FOREIGN KEY(id_enseignement) REFERENCES Enseignement(id_enseignement) ON DELETE CASCADE"
            		+ ");");
            createTableIfNotExists(metaData, "Evaluation", "CREATE TABLE Evaluation("
            		+ "numero_enseignant SMALLINT,"
            		+ "id_candidature INT,"
            		+ "note DECIMAL(4,2),"
            		+ "PRIMARY KEY(numero_enseignant, id_candidature),"
            		+ "FOREIGN KEY(numero_enseignant) REFERENCES Enseignant(numero_enseignant) ON DELETE CASCADE,"
            		+ "FOREIGN KEY(id_candidature) REFERENCES Candidature(id_candidature) ON DELETE CASCADE"
            		+ ");");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTableIfNotExists(DatabaseMetaData metaData, String tableName, String createQuery) throws SQLException {
        // Check if the table exists
        if (!tableExists(metaData, tableName)) {
            // Create the table if it doesn't exist
            try {
                stmt.executeUpdate(createQuery);
                System.out.println("Table '" + tableName + "' created.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Table '" + tableName + "' already exists.");
        }
    }

    public static boolean tableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (java.sql.ResultSet resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }
    
    public static void dropAllTables() {
        try {
            DatabaseMetaData metaData = stmt.getConnection().getMetaData();

            // Retrieve the list of tables
            ResultSet resultSet = metaData.getTables(null, null, null, new String[]{"TABLE"});

            // Drop each table
            while (resultSet.next()) {
                String tableName = resultSet.getString("TABLE_NAME");
                dropTable(tableName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dropTable(String tableName) {
        try {
            // Execute the DROP TABLE statement
            try {
                stmt.executeUpdate("SET foreign_key_checks = 0");
                stmt.executeUpdate("DROP TABLE IF EXISTS " + tableName);
                System.out.println("Table '" + tableName + "' dropped.");
            } finally {
                stmt.executeUpdate("SET foreign_key_checks = 1");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void insertPresetData() {
        try {
            // Insertion d'etudiants
            stmt.executeUpdate("INSERT INTO Etudiant (numero_etudiant, nom, prenom, note_moyenne) VALUES " +
                    "(1, 'Dupont', 'Jean', 15.5), " +
                    "(2, 'Martin', 'Alice', 16.2), " +
                    "(3, 'Leclerc', 'Pierre', 14.8)");

            // Insertion de destinations
            stmt.executeUpdate("INSERT INTO Destination (id_destination, nom_destination) VALUES " +
                    "('FRPR', 'Paris'), " +
                    "('DEBL', 'Berlin'), " +
                    "('SPBC', 'Barcelone')");

            // Insertion d'enseignants
            stmt.executeUpdate("INSERT INTO Enseignant (numero_enseignant, nom, prenom) VALUES " +
                    "(101, 'Girard', 'Sophie'), " +
                    "(102, 'Dubois', 'Marc'), " +
                    "(104, 'Durand', 'Jeanne'), " +
                    "(103, 'Lefevre', 'Isabelle')");

            // Insertion d'enseignements
            stmt.executeUpdate("INSERT INTO Enseignement (nom, credits_reconnus, volume_horaire, id_destination) VALUES " +
                    "('Mathematiques avancees', 5, 15, 'FRPR'), " +
                    "('Programmation BDD', 10, 20, 'FRPR'), " +
                    "('Physique', 5, 15, 'FRPR'), " +
                    "('Histoire de art', 3, 5, 'DEBL'), " +
                    "('Programmation Java', 5, 10, 'SPBC')");

            // Insertion de bourses
            stmt.executeUpdate("INSERT INTO Bourses (postes_disponible, id_destination, numero_enseignant) VALUES " +
                    "(2, 'FRPR', 101), " +
                    "(3, 'FRPR', 101), " +
                    "(3, 'DEBL', 102), " +
                    "(1, 'SPBC', 103)");

            // Insertion de candidatures
            stmt.executeUpdate("INSERT INTO Candidature (numero_etudiant, id_bourse) VALUES " +
                    "(1, 1), " +
                    "(2, 1), " +
                    "(2, 2), " +
                    "(3, 3)");

            // Insertion de plans de cours
            stmt.executeUpdate("INSERT INTO PlanCours (id_candidature, id_enseignement) VALUES " +
                    "(1, 1), " +
                    "(2, 1), " +
                    "(2, 2), " +
                    "(2, 3), " +
                    "(3, 3), " +
                    "(4, 5)");

            // Insertion d'evaluations
            stmt.executeUpdate("INSERT INTO Evaluation (numero_enseignant, id_candidature, note) VALUES " +
                    "(101, 1, 17.5), " +
                    "(101, 2, 16.8), " +
                    "(104, 2, 10.5), " +
                    "(101, 3, 14.8), " +
                    "(104, 3, 14.8), " +
                    "(103, 4, 15.3)");

            System.out.println("Preset data inserted successfully.");

        } catch (SQLException e) {
            System.err.println("Error inserting preset data: " + e.getMessage());
        }
    }

	/**
	 * Méthode de creation de la base de données
	 * 
	 * @throws SQLException
	 */
	public void create() throws SQLException {
		String createEtudiant = "CREATE TABLE Etudiant(" + "numero_etudiant SMALLINT," + "nom VARCHAR(50) NOT NULL,"
				+ "prenom VARCHAR(50) NOT NULL," + "note_moyenne DECIMAL(4,2) NOT NULL,"
				+ "PRIMARY KEY(numero_etudiant));";

		String createDestination = "CREATE TABLE Destination(" + "id_destination VARCHAR(4),"
				+ "nom_destination VARCHAR(255) NOT NULL," + "PRIMARY KEY(id_destination));";

		String createEnseignant = "CREATE TABLE Enseignant(" + "numero_enseignant SMALLINT,"
				+ "nom VARCHAR(50) NOT NULL," + "prenom VARCHAR(50) NOT NULL," + "PRIMARY KEY(numero_enseignant));";

		String createEnseignement = "CREATE TABLE Enseignement(" + "id_enseignement INT NOT NULL AUTO_INCREMENT,"
				+ "nom VARCHAR(255) NOT NULL," + "credits_reconnus BYTE NOT NULL," + "volume_horaire SMALLINT NOT NULL,"
				+ "id_destination VARCHAR(4) NOT NULL," + "PRIMARY KEY(id_enseignement),"
				+ "FOREIGN KEY(id_destination) REFERENCES Destination(id_destination));";

		String createBourse = "CREATE TABLE Bourse(" + "id_bourse INT NOT NULL AUTO_INCREMENT,"
				+ "postes_disponibles INT NOT NULL," + "id_destination VARCHAR(4) NOT NULL,"
				+ "numero_enseignant SMALLINT NOT NULL," + "PRIMARY KEY(id_bourse),"
				+ "FOREIGN KEY(id_destination) REFERENCES Destination(id_destination),"
				+ "FOREIGN KEY(numero_enseignant) REFERENCES Enseignant(numero_enseignant));";

		String createCandidature = "CREATE TABLE Candidature(" + "id_candidature INT NOT NULL AUTO_INCREMENT,"
				+ "numero_etudiant SMALLINT NOT NULL," + "id_bourse INT NOT NULL," + "PRIMARY KEY(id_candidature),"
				+ "FOREIGN KEY(numero_etudiant) REFERENCES Etudiant(numero_etudiant),"
				+ "FOREIGN KEY(id_bourse) REFERENCES Bourse(id_bourse));";

		String createPlanCours = "CREATE TABLE PlanCours(" + "id_candidature INT," + "id_enseignement INT,"
				+ "PRIMARY KEY(id_candidature, id_enseignement),"
				+ "FOREIGN KEY(id_candidature) REFERENCES Candidature(id_candidature),"
				+ "FOREIGN KEY(id_enseignement) REFERENCES Enseignement(id_enseignement));";

		String createEvaluation = "CREATE TABLE Evaluation(" + "numero_enseignant SMALLINT," + "id_candidature INT,"
				+ "note DECIMAL(4,2)," + "PRIMARY KEY(numero_enseignant, id_candidature),"
				+ "FOREIGN KEY(numero_enseignant) REFERENCES Enseignant(numero_enseignant),"
				+ "FOREIGN KEY(id_candidature) REFERENCES Candidature(id_candidature));";

		stmt.executeUpdate(createEtudiant);
		stmt.executeUpdate(createDestination);
		stmt.executeUpdate(createEnseignant);
		stmt.executeUpdate(createEnseignement);
		stmt.executeUpdate(createBourse);
		stmt.executeUpdate(createCandidature);
		stmt.executeUpdate(createPlanCours);
		stmt.executeUpdate(createEvaluation);
	}

	/**
	 * Méthode de creation de données dans la base de données
	 * 
	 * @throws SQLException
	 */
	public void generate() throws SQLException {
		// Requêtes de creation des données de la table
		Destination germany = new Destination("DU45","germany");
		Destination spain = new Destination("SP50","spain");
		Destination england = new Destination("UK12","england");
		
		Etudiant john = new Etudiant("22107436", "john", "do", 12);
		Etudiant sarah = new Etudiant("22107437", "sarah", "croche", 11);
		Etudiant david = new Etudiant("22107438", "david", "labouteille", 16);
		
		Enseignant benoit = new Enseignant(1, "benoit", "de cajoux");
		Enseignant deny = new Enseignant(2, "deny", "iliste");

		Bourses b1 = new Bourses(1, 3, england, deny);
		Bourses b2 = new Bourses(2, 4, spain, deny);
		Bourses b3 = new Bourses(3, 5, germany, benoit);
		
		Candidature c1 = new Candidature(1, david, b3);
		Candidature c2 =  new Candidature(1, sarah, b3);
		Candidature c3 =  new Candidature(1, sarah, b2);
		
		Enseignement e1 = new Enseignement(1, "Deutsch wissenshcaft", 10, 64, germany);
		Enseignement e2 = new Enseignement(2, "Abla", 10, 64, spain);
		Enseignement e3 = new Enseignement(3, "Speaking", 10, 64, england);
		
		Evaluation eval1 = new Evaluation(deny, c1, 10);
		Evaluation eval2 = new Evaluation(benoit, c1, 8);
		Evaluation eval3 = new Evaluation(deny, c2, 19);
		Evaluation eval4 = new Evaluation(benoit, c2, 16);
		Evaluation eval5 = new Evaluation(deny, c3, 12);
		
		PlanCours plan1 = new PlanCours(c1, e1);
		PlanCours plan2 = new PlanCours(c2, e1);
		PlanCours plan3 = new PlanCours(c3, e2);
		
		
		Destination.ajouter(stmt, germany);
		Destination.ajouter(stmt, spain);
		Destination.ajouter(stmt, england);
		
		Etudiant.ajouter(stmt, david);
		Etudiant.ajouter(stmt, john);
		Etudiant.ajouter(stmt, sarah);
		
		Enseignant.ajouter(stmt, deny);
		Enseignant.ajouter(stmt, benoit);
		
		Bourses.ajouter(stmt, b3);
		Bourses.ajouter(stmt, b2);
		Bourses.ajouter(stmt, b1);
		
		Candidature.ajouter(stmt, c1);
		Candidature.ajouter(stmt, c2);
		Candidature.ajouter(stmt, c3);
		
		Enseignement.ajouter(stmt, e1);
		Enseignement.ajouter(stmt, e2);
		Enseignement.ajouter(stmt, e3);
		
		Evaluation.ajouter(stmt, eval1);
		Evaluation.ajouter(stmt, eval2);
		Evaluation.ajouter(stmt, eval3);
		Evaluation.ajouter(stmt, eval4);
		Evaluation.ajouter(stmt, eval5);
		
		PlanCours.ajouter(stmt, plan1);
		PlanCours.ajouter(stmt, plan2);
		PlanCours.ajouter(stmt, plan3);
	}
	
	/**
	 * @return the statement
	 */
	public Statement getStatement() {
		return stmt;
	}

	/**
	 * @param statement the statement to set
	 */
	public void setStatement(Statement statement) {
		stmt = statement;
	}


	/**
	 * @return the connect
	 */
	public Connection getConnect() {
		return connect;
	}


	/**
	 * @param connect the connect to set
	 */
	public void setConnect(Connection connect) {
		GestionnaireErasmus.connect = connect;
	}
}