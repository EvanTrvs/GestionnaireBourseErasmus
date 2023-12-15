/**
 * 
 */
package controller;

import model.GestionnaireErasmus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//C:\Users\evant\Documents\Travail\DI\S6\JavaProject\javafx-sdk-20.0.1\lib

/**
 * @author evant LauncherApp class perform as Central Application interface
 *         launcher (Executable for Central Application Interface) Serve as Main
 *         controller for the this application
 */
public class LauncherApp extends Application {
	
	/**
	 * Single object from Model, contain data for running central application
	 */
	static GestionnaireErasmus appli;

	/**
	 * main execution to launch Central Application interface
	 * @param no argument needed
	 */
	public static void main(String[] args) {
		
		// Utilisation de Scanner pour lire l'entrée utilisateur
        Scanner scanner = new Scanner(System.in);

        System.out.print("Connection to MySql Database\n");
        // Demande à l'utilisateur de saisir le nom de la base de données
        System.out.print("Database name (e.g.: jdbc:mysql://localhost/NAME)\nPlease enter 'NAME': ");
        String databaseName = scanner.nextLine();

        // Demande à l'utilisateur de saisir le nom d'utilisateur et le mot de passe
        System.out.print("Database User (e.g., root/password)\nPlease enter 'USER/PASSWORD': ");
        String userAndPassword = scanner.nextLine();

        // Séparation du nom d'utilisateur et du mot de passe
        String[] credentials = userAndPassword.split("/");

        // Vérification de la validité de l'entrée
        if (credentials.length != 2 || databaseName.isEmpty() || credentials[0].isEmpty() || credentials[1].isEmpty()) {
            System.err.println("Invalid input. Please enter valid values.");
            scanner.close();
            return;
        }

        String username = credentials[0];
        String password = credentials[1];

        // Construction de l'URL de connexion JDBC
        String jdbcUrl = "jdbc:mysql://localhost/" + databaseName;
        
        //String username = "root";
        //String password = "password";
        //String jdbcUrl = "jdbc:mysql://localhost/world";
        
        // Fermeture du scanner
        scanner.close();
        
        // Affichage des résultats
        System.out.println("\nJDBC Connection URL: " + jdbcUrl);
        System.out.println("Database User: " + username);
        System.out.println("Database Password: " + password);

        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ex1) {
			System.out.println("Pilote non trouvé!");
			System.exit(1);
		}        
        
		appli = new GestionnaireErasmus();
        
        try {
			Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
				
			Statement stmt = connection.createStatement();
			appli.setStatement(stmt);
			appli.setConnect(connection);
			
			System.out.println("\nConnection Success !\n");
			
			GestionnaireErasmus.createTablesIfNotExist();
			System.out.println("\n");
			appli.load();
			
	        //Lancement de l'interface
			launch(args);

		} catch (SQLException ex2) {
			System.err.println("Connection failed. Please check your database information.");
			System.out.println("Erreur JDBC: "+ex2);
			System.exit(1);
		}
	}

	/**
	 * Process when Central Application interface is shutdown, save and disconnect
	 */
	@Override
	public void stop() {
		System.out.println("Exit application");
		//statement.close()
		appli.kill();
		appli = null;
	}

	/**
	 * Launched method, initialize and set GUI with FXML views and controllers for
	 * first scene
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			Platform.setImplicitExit(true);

			primaryStage.setTitle("Gestionnaire Erasmus Application");
			GridPanController parentContainer = new GridPanController();

			//appli = new GestionnaireErasmus();

			FXMLLoader leftMenuLoader = new FXMLLoader(
					getClass().getResource("/view/LeftMenu.fxml"));
			LeftMenuController leftMenuController = new LeftMenuController();
			leftMenuLoader.setController(leftMenuController);
			leftMenuController.setAppli(appli);
			leftMenuController.setParent(parentContainer);

			FXMLLoader home = new FXMLLoader(getClass().getResource("/view/Home.fxml"));

			VBox leftMenuRoot = leftMenuLoader.load();
			VBox workSessionRoot = home.load();

			GridPane.setColumnIndex(leftMenuRoot, 0);
			GridPane.setColumnIndex(workSessionRoot, 1);

			parentContainer.setHgap(10);
			parentContainer.getChildren().add(leftMenuRoot);
			parentContainer.getChildren().add(workSessionRoot);
			Scene scene = new Scene(parentContainer, 920, 600);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
