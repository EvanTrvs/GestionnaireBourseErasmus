package controller;

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
public class CoursesController {

	private GestionnaireErasmus appli;
	
	private GridPanController parent;
	
	public ObservableList<Integer> list;

	@FXML
	private TableView<Integer> tableView;

	@FXML
	private TableColumn<Integer, Integer> idColumn;

	@FXML
	private TableColumn<Integer, String> nameColumn;

	@FXML
	private TableColumn<Integer, String> creditsColumn;
	
	@FXML
	private TableColumn<Integer, String> CRUD;

	@FXML
	private TableColumn<Integer, Float> hourColumn;
	
	@FXML
	private TableColumn<Integer, Float> destiID;
	
	/****************************  CONSTRUCTOR  *******************************/

	/**
	 * Constructor with objects from model (non FXML objects)
	 * @param appli Main singleton object
	 * @param parent interface origin scene
	 */
	public CoursesController(GestionnaireErasmus appli, GridPanController parent) {
		this.parent = parent;
		this.appli = appli;
	}

	/**
	 * Initialize GUI, associate controller elements with view elements
	 */
	@FXML
	public void initialize() {
		//list = FXCollections.observableList(appli.getEmployees());
/*
		idColumn.setCellValueFactory(new PropertyValueFactory<Integer, Integer>("id"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("name"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<Integer, String>("FirstName"));
		meanColumn.setCellValueFactory(new PropertyValueFactory<Integer, Float>("departement"));
		*/
		/*
		CRUD.setCellFactory(col -> {
			TableCell<Integer, String> cell = new TableCell<Integer, String>() {
				
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
				Employee employee;
				try {
					employee = cell.getTableView().getItems().get(cell.getIndex());
				} catch (Exception e) {
					return;
				}

				Scene scene = ((Node) event.getSource()).getScene();
				try {
					this.openCRUDEmployee(scene, employee, false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			return cell;
		});
		tableView.setItems(list);
		*/
	}
	
	/******************************  METHODS  *********************************/
	
	/**
	 * Button function for adding employee to central application
	 * @param event Button click
	 * @throws IOException fxml or employee creation failure
	 */
	public void addCourse(ActionEvent event) throws IOException {
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
	public void openCRUDEmployee(Scene scene, Integer empl, boolean isnew) throws IOException {
		/*
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/fr/univtours/polytech/clocking/view/CRUDemployee.fxml"));
		
		CRUDemployeeController controlleur = new CRUDemployeeController(appli, empl, parent, isnew);
		loader.setController(controlleur);
		
		VBox root = loader.load();
		parent.setRight(root);
		*/
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