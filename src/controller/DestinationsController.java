package controller;

import model.Destination;
import java.io.IOException;

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
public class DestinationsController {

	private GestionnaireErasmus appli;
	
	private GridPanController parent;
	
	public ObservableList<Destination> list;

	@FXML
	private TableView<Destination> tableView;

	@FXML
	private TableColumn<Destination, String> idColumn;

	@FXML
	private TableColumn<Destination, String> nameColumn;

	@FXML
	private TableColumn<Destination, String> CRUD;
	
	/****************************  CONSTRUCTOR  *******************************/

	/**
	 * Constructor with objects from model (non FXML objects)
	 * @param appli Main singleton object
	 * @param parent interface origin scene
	 */
	public DestinationsController(GestionnaireErasmus appli, GridPanController parent) {
		this.parent = parent;
		this.appli = appli;
	}

	/**
	 * Initialize GUI, associate controller elements with view elements
	 */
	@FXML
	public void initialize() {
		list = FXCollections.observableList(appli.ListDestination );

		idColumn.setCellValueFactory(new PropertyValueFactory<Destination, String>("idDestination"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Destination, String>("libelle"));
		
		CRUD.setCellFactory(col -> {
			TableCell<Destination, String> cell = new TableCell<Destination, String>() {
				
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
				Destination destination;
				try {
					destination = cell.getTableView().getItems().get(cell.getIndex());
				} catch (Exception e) {
					return;
				}

				Scene scene = ((Node) event.getSource()).getScene();
				try {
					this.openCRUDDestination(scene, destination, false);
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
	public void addDestination(ActionEvent event) throws IOException {
		Destination newDest = new Destination();
		//appli.addEmployee(newEmployee);
		Scene scene = ((Node) event.getSource()).getScene();
		openCRUDDestination(scene, newDest, true);
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
	public void openCRUDDestination(Scene scene, Destination destination, boolean isnew) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/view/DestinationsCRUD.fxml"));
		
		DestinationsCRUDController controlleur = new DestinationsCRUDController(appli, destination, parent, isnew);
		loader.setController(controlleur);
		
		VBox root = loader.load();
		parent.setRight(root);
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