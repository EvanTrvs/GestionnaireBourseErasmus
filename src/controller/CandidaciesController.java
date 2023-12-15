package controller;

import model.Candidature;
import model.Etudiant;
import model.Evaluation;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.GestionnaireErasmus;

/**
 * This class is a Controller, match with employees.FXML <br/>
 * Use in Central Application as GUI for Staff management <br/>
 * GUI representation of every employee in a table with severals informations
 */
public class CandidaciesController {

	private GestionnaireErasmus appli;
	
	private GridPanController parent;
	
	public ObservableList<Candidature> list;

	@FXML
	private TableView<Candidature> tableView;

	@FXML
	private TableColumn<Candidature, Integer> idColumn;

	@FXML
	private TableColumn<Candidature, String> etuNumColumn;

	@FXML
	private TableColumn<Candidature, String> scholIdColumn;
	
	@FXML
	private TableColumn<Candidature, String> CRUD;

	@FXML
	private TableColumn<Candidature, String> stateColumn;
	
	/****************************  CONSTRUCTOR  *******************************/

	/**
	 * Constructor with objects from model (non FXML objects)
	 * @param appli Main singleton object
	 * @param parent interface origin scene
	 */
	public CandidaciesController(GestionnaireErasmus appli, GridPanController parent) {
		this.parent = parent;
		this.appli = appli;
	}

	/**
	 * Initialize GUI, associate controller elements with view elements
	 */
	@FXML
	public void initialize() {
		list = FXCollections.observableList(appli.ListCandidature );

		idColumn.setCellValueFactory(new PropertyValueFactory<Candidature, Integer>("id_candidature"));
		
		etuNumColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEtudiant().getNumero_etudiant() +
				" ("+ cellData.getValue().getEtudiant().getNom_etudiant() +")" ));

		scholIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getBourse().getId_bourse() +
				" ("+ cellData.getValue().getBourse().getDestination().getLibelle() +")" )));

		stateColumn.setCellValueFactory(cellData -> {
		    float moyenne = calculerMoyenne(appli.ListEvaluation, cellData.getValue());
		    if (moyenne > 0) {
		    return new SimpleStringProperty(String.valueOf(moyenne));
		    }
		    else {
		    	return new SimpleStringProperty("Not Completed");
		    }
		});
		
		CRUD.setCellFactory(col -> {
			TableCell<Candidature, String> cell = new TableCell<Candidature, String>() {
				
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);

					if (empty) {
						setText(null);
					} else {
						setText("   CRUD");
						setStyle("-fx-border-style: solid inside;"
								+ "-fx-border-width: 2;"
								+ "-fx-border-radius: 5;"
								+ "-fx-border-color: gray;");
					}
				}
			};
			cell.setOnMouseClicked(event -> {
				Candidature candidature;
				try {
					candidature = cell.getTableView().getItems().get(cell.getIndex());
				} catch (Exception e) {
					return;
				}

				Scene scene = ((Node) event.getSource()).getScene();
				try {
					this.openCRUDCandidature(scene, candidature, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			return cell;
		});
		tableView.setItems(list);
	}
	
	/******************************  METHODS  *********************************/
	
	/**
	 * Button function for adding employee to central application
	 * @param event Button click
	 * @throws IOException fxml or employee creation failure
	 */
	public void addCandidacy(ActionEvent event) throws IOException {
		/*
		Employee newEmployee = new Employee();
		appli.addEmployee(newEmployee);
		Scene scene = ((Node) event.getSource()).getScene();
		openCRUDEmployee(scene, newEmployee, true);
		*/
		return;
	}

	/**
	 * Employee creation method <br/>
	 * Open new scene for new employee purpose 
	 * @param scene Current interface scene
	 * @param empl New employee object
	 * @param isnew True if it is a employee creation (current case)
	 * @throws IOException fxml failure
	 */
	public void openCRUDCandidature(Scene scene, Candidature cand, boolean isnew) throws IOException {
		/*
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fr/univtours/polytech/clocking/view/CRUDemployee.fxml"));
		
		CRUDemployeeController controlleur = new CRUDemployeeController(appli, empl, parent, isnew);
		loader.setController(controlleur);
		
		VBox root = loader.load();
		parent.setRight(root);
		*/
	}
	
	// Méthode pour calculer la moyenne pour une candidature
    public static float calculerMoyenne(ArrayList<Evaluation> listEvaluation, Candidature candidature) {
        Etudiant etudiant = candidature.getEtudiant();
        float noteMoyenneEtudiant = etudiant.getNote_moyenne();

        // Compter le nombre de notes d'évaluation associées à la candidature
        int nombreDeNotes = 0;
        float sommeNotes = noteMoyenneEtudiant;

        for (Evaluation evaluation : listEvaluation) {
            if (evaluation.getCandidature().getId_candidature() == candidature.getId_candidature() ) {
                sommeNotes += evaluation.getNote() ;
                nombreDeNotes++;
            }
        }
        
        // Vérifier si le nombre de notes est strictement égal à 2
        if (nombreDeNotes >= 2) {
            // Calculer la moyenne
            return sommeNotes / 3;
        } else {
            // Retourner -1 si le nombre de notes n'est pas égal à 2
            return -1;
        }
    }
	
	
	/*************************  GETTER AND SETTER  ****************************/
	
	public void setParentAttribut(GestionnaireErasmus appli) {
		this.appli = appli;
	}

	public GridPane getParent() {
		return parent;
	}

	public void setParent(GridPanController parent) {
		this.parent = parent;
	}
}