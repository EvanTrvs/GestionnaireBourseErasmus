package controller;

import model.Destination;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Iterator;

import model.GestionnaireErasmus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class is a Controller, match with CRUDemployee.FXML <br/>
 * Use in Central Application as GUI for CRUD staff <br/>
 * GUI representation of every employee's informations in a scene for Reading, Updating or Deletion
 */
public class DestinationsCRUDController {

	private GestionnaireErasmus appli;
	
	private GridPanController parent;
	
	private boolean isnew;
	
	@FXML
	private TextField name;

	@FXML
	private TextField id;

	@FXML
	private Destination destination;
	
	/****************************  CONSTRUCTOR  *******************************/
	
	/**
	 * Constructor with objects from model (non FXML objects)
	 * @param appli Main singleton object 
	 * @param employee Employee subject
	 * @param parent interface origin scene
	 * @param isnew	True if it is a employee creation
	 */
	public DestinationsCRUDController(GestionnaireErasmus appli, Destination destination, GridPanController parent, boolean isnew) {
		this.appli = appli;
		this.destination = destination;
		this.parent = parent;
		this.isnew = isnew;
	}

	/**
	 * Initialize GUI, associate controller elements with view elements
	 */
	public void initialize() {
		name.setText(destination.getLibelle());
		id.setText(destination.getIdDestination() );
		
		id.setEditable(isnew);
	}
	
	/******************************  METHODS  *********************************/
	
	
	/**
	 * Button function for apply and save employee details
	 * @param event Button click
	 * @throws IOException Modification incomplete
	 */
	public void modificationButton(ActionEvent event) throws IOException {
		destination = new Destination(id.getText(), name.getText());
		
		if (isnew == true) {
			try {
				destination.ajouter(appli.getStatement(), destination);	//add to database
				appli.ListDestination.add(destination);
			} catch (SQLException ex2) {
				System.out.println("Erreur : "+ex2);
			}
		}
		else {
			//modifier
			/*
			try {
				etudiant.
				appli.ListEtudiant.add(etudiant);
			} catch (SQLException ex2) {
				System.out.println("Erreur : "+ex2);
			}
			*/
		}
		cancelButton(event);
	}

	/**
	 * Button function for cancel and exit employee consulting/editing
	 * @param event Button click
	 * @throws IOException fxml failure
	 */
	public void cancelButton(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/Destinations.fxml"));
		
		DestinationsController controlleur = new DestinationsController(appli, parent);
		loader.setController(controlleur);
		
		VBox emplroot = null;
		try {
			emplroot = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		parent.setRight(emplroot);
	}

	/**
	 * Button function for deletion of an employee and related informations 
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void deleteButton(ActionEvent event) throws IOException {
		if (isnew == false) {
			try {
				destination.supprimer(appli.getStatement(), destination.getIdDestination());

				//delete from model
				Iterator<Destination> iterator = appli.ListDestination.iterator();

		        while (iterator.hasNext()) {
		        	Destination destination2 = iterator.next();
		            if (destination2.getIdDestination().equals(destination.getIdDestination())) {
		                iterator.remove();
		                System.out.println("Etudiant removed: " + destination.getIdDestination());
		            }
		        }
			} catch (SQLException ex2) {
				System.out.println("Erreur : "+ex2);
			}
		}
		else {
			//none
		}
		cancelButton(event);
	}
	
	/*************************  GETTER AND SETTER  ****************************/

	public GestionnaireErasmus getAppli() {
		return appli;
	}

	public void setAppli(GestionnaireErasmus appli) {
		this.appli = appli;
	}

	public GridPanController getParent() {
		return parent;
	}
/*
	public void setParentAttribut(Employee employee) {
		this.employee = employee;
	}*/

	public void setParent(GridPanController parent) {
		this.parent = parent;
	}
}
