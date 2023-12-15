package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.GestionnaireErasmus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * This class is a Controller, match with LeftMenu.FXML <br/>
 * Use in Central Application as GUI for navigation throw scenes and functionalities <br/>
 * GUI representation of a menu button bar for Application navigation
 */
public class LeftMenuController {

	private GestionnaireErasmus appli;
	
	private GridPanController parent;
	
	@FXML
	private AnchorPane anchorp;

	/****************************  CONSTRUCTOR  *******************************/

	/**
	 * Initialize GUI, associate controller elements with view elements
	 */
	@FXML
	public void initialize() {
		anchorp.setStyle("-fx-background-color: lightblue;");
	}
	
	/******************************  METHODS  *********************************/
	
	/**
	 * Button function for Home scene access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void homeButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void studentsButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Students.fxml"));
	    StudentsController controlleur = new StudentsController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void candidaciesButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Candidacies.fxml"));
	    CandidaciesController controlleur = new CandidaciesController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void scholarshipsButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Scholarships.fxml"));
	    ScholarshipsController controlleur = new ScholarshipsController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void teachersButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Teachers.fxml"));
	    TeachersController controlleur = new TeachersController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void destinationsButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Destinations.fxml"));
	    DestinationsController controlleur = new DestinationsController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void coursesButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Courses.fxml"));
	    CoursesController controlleur = new CoursesController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void coursePButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CoursePlan.fxml"));
	    CoursePController controlleur = new CoursePController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function students access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void evaluationsButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Evaluations.fxml"));
	    EvaluationsController controlleur = new EvaluationsController(appli,parent);

	    loader.setController(controlleur);
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	
	/**
	 * Button function for Staff management scene access
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	/*
	public void employeesButton(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/univtours/polytech/clocking/view/employees.fxml"));
	    EmployeeController controlleur = new EmployeeController(appli,parent);

	    loader.setController(controlleur);

	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	}
	*/
	
	/**
	 * Button function for reset data to a preset fictive data
	 * @param event Button click
	 * @throws IOException deletion failure
	 */
	public void resetButton(ActionEvent event) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
	    VBox employeesRoot = loader.load();
	    parent.setRight(employeesRoot);
	    
	    GestionnaireErasmus.dropAllTables();
	    GestionnaireErasmus.createTablesIfNotExist();
	    appli.insertPresetData();
	    try {
	    appli.load();
	    } catch (SQLException ex2) {
			System.out.println("Erreur : "+ex2);
		}
	}
	
	/*************************  GETTER AND SETTER  ****************************/

	public GridPanController getParent() {
		return parent;
	}

	public void setParent(GridPanController parent) {
		this.parent = parent;
	}
	
	public GestionnaireErasmus getAppli() {
		return appli;
	}

	public void setAppli(GestionnaireErasmus appli) {
		this.appli = appli;
	}
}