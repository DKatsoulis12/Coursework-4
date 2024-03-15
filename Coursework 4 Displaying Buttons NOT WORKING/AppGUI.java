import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import java.util.HashMap;
import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import javafx.scene.control.ChoiceBox;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;

/**
 * Write a description of JavaFX class AppGUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class AppGUI extends Application implements Initializable
{
    // We keep track of the count, and label displaying the count:

    private static AppGUI INSTANCE;
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private Button button1;
    
    @FXML
    private Button button2;
    
    @FXML
    private Pane pane1 = new Pane();
    
    private CovidDataLoader theCovidDataLoader = new CovidDataLoader();
    
    @FXML
    private DatePicker startDate;
    
    @FXML
    private DatePicker endDate;
    
    private CovidDataPanel covidDataPanel = new CovidDataPanel();
    
    private Pane pane3 = new Pane();
    
    private Pane pane4 = new Pane();
    
    private ArrayList<String> UniqueDates;
    
    private String alertTxt;
    
    private HashMap<Integer,Pane> panesDisplay = new HashMap<Integer,Pane>(){{
        put(1,pane1);
        put(2,covidDataPanel);
        put(3,pane3);
        put(4,pane4);
    }};
    
    private Pane currentPane = pane1;
    private int currentPaneNum = 1;
    
    
    
    
    public static AppGUI getInstance() {
        /*if (INSTANCE == null) {
            INSTANCE = this;
        }*/
        System.out.println(INSTANCE);
        return INSTANCE;
    }
    
    @FXML
    private void numberPressed(ActionEvent event) {
        boolean f = toggleButtonDisability();
        
        if (f == false) {
            currentPaneNum = ((currentPaneNum) % 4) + 1;
            addtoAnchor(currentPaneNum);
        } else {
            alertSetUp();
        }
    }
        
    @FXML
    private void goBack(ActionEvent event) {
        boolean f = toggleButtonDisability();
        
        if (f == false) {
            currentPaneNum = (currentPaneNum - 2 + 4) % 4 + 1;
            addtoAnchor(currentPaneNum);
        } else {
            alertSetUp();
        }
    }
    
    public String getStartDate(){
        return startDate.getValue().toString();
    }
    
    public String getEndDate() {
        return endDate.getValue().toString();
    }
    
    private void addtoAnchor(int currentPaneNum) {
        anchorPane.getChildren().remove(currentPane);

        Pane newPane = panesDisplay.get(currentPaneNum);
        Label paneNumLabel = new Label("" + currentPaneNum);
        newPane.getChildren().add(paneNumLabel);
        
        if (currentPaneNum == 1) {
            newPane.getChildren().remove(paneNumLabel);
            
            Label welcomeLabel = new Label("Welcome. Here are the instructions below: ");
            newPane.getChildren().add(welcomeLabel);
        
            Label instructionsLabel = new Label("The minimum start date is 2/3/2020 and 2/9/2023");
            instructionsLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 10);
            
            newPane.getChildren().add(instructionsLabel);
        }
        else if(currentPaneNum == 2){
            System.out.println("Seems to be working fine!");
            newPane = covidDataPanel;
            newPane.getChildren().remove(paneNumLabel);
            ((CovidDataPanel) newPane).initialiseUI();
            System.out.println("Seems to be working fine!");
        }

        newPane.setPrefWidth(pane1.getWidth());  // Set dimensions as needed
        newPane.setPrefHeight(pane1.getHeight());

        newPane.setLayoutX(pane1.getLayoutX());
        newPane.setLayoutY(pane1.getLayoutY());

        anchorPane.getChildren().add(newPane);

        currentPane = newPane;
    }
    
    private void setAlertText(boolean startLarger, boolean invalidRange) {
        if (startLarger) {
            alertTxt = "Start Date is bigger than the End Date";
        } else if (invalidRange) {
            alertTxt = "Data not available for all these days. Not a valid range";
        } else {
            alertTxt = "";
        }
    }
    
    private void alertSetUp() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invalid Date Selection");
        alert.setHeaderText(null);
        alert.setContentText(alertTxt);
            
        alert.showAndWait();
    }
    
    /**
     * The start method is the main entry point for every JavaFX application. 
     * It is called after the init() method has returned and after 
     * the system is ready for the application to begin running.
     *
     * @param  stage the primary stage for this application.
     */
    @Override
    public void start(Stage stage) throws Exception
    {
        // Create a Button or any control item
        
        /*if (INSTANCE == null) {
            INSTANCE = this;
        }
        System.out.println(INSTANCE);*/
        
        URL url = getClass().getResource("mainWindow1.fxml");
        Pane root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
    
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        
        stage.show();
        
        if(INSTANCE == null) {
            INSTANCE = this;
        }
        System.out.println(INSTANCE);
        
    }
    
    private boolean toggleButtonDisability()
    {
        LocalDate startDateValue = startDate.getValue();
        LocalDate endDateValue = endDate.getValue();
        String startDateString = startDateValue.toString();
        String endDateString = endDateValue.toString();
        boolean flag1 =  startDate.getValue().isAfter(endDate.getValue());
        boolean flag2 = !(UniqueDates.contains(startDateString) && UniqueDates.contains(endDateString));
        if (flag1 || flag2) {
            setAlertText(flag1, flag2);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UniqueDates = theCovidDataLoader.loadUniqueDates();
        addtoAnchor(1);
        startDate.setValue(LocalDate.of(2022, 3, 8));  
        endDate.setValue(LocalDate.of(2022, 4, 24)); 
        
        startDate.setEditable(false);
        endDate.setEditable(false);
        
        
    }
    
}
