import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
//import javafx.scene.Node;

//import javafx.scene.control.Label;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.layout.HBox;

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
    
    //@FXML
    //private Pane pane1 = new Pane();
    
    @FXML
    public DatePicker startDate;
    
    @FXML
    public DatePicker endDate;
    
    @FXML
    private Button updateButton;
    
    private CovidDataLoader theCovidDataLoader =  CovidDataLoader.getInstance();
    
    private DateChecker theDateChecker = DateChecker.getInstance();
    
    private FirstPane pane1 = new FirstPane();
    
    private CovidDataPanel pane2 = new CovidDataPanel();
    
    /////
    private ThirdPane pane3 = new ThirdPane();//ThirdPane.getInstance();
    
       
    
    
    private FourthPane pane4 = new FourthPane();
    
    private ArrayList<String> UniqueDates;
    
    private String alertTxt;
    
    //private CovidDataPanel covidDataPanel;
    
    
    
    private HashMap<Integer,AppPane> panesDisplay = new HashMap<Integer,AppPane>(){{
        put(1,pane1);
        put(2,pane2);
        put(3,pane3);
        put(4,pane4);
    }};
    
    private AppPane currentPane = pane1;
    private int currentPaneNum = 1;
    
    public static AppGUI getInstance() {
        System.out.println(INSTANCE);
        return INSTANCE;
    }
    
    @FXML
    private void goForward(ActionEvent event) {
        if (theDateChecker.checkButtonDisability(startDate.getValue(), endDate.getValue())) {
            return;
        }
        currentPaneNum = ((currentPaneNum) % 4) + 1;
        addtoAnchor(currentPaneNum);
        }
    
    @FXML
    private void goBack(ActionEvent event) {
        if (theDateChecker.checkButtonDisability(startDate.getValue(), endDate.getValue())) {
            return;
        }
        currentPaneNum = (currentPaneNum - 2 + 4) % 4 + 1;
        addtoAnchor(currentPaneNum);
    }
    
    @FXML
    private void updatePressed(){
        if (theDateChecker.checkButtonDisability(startDate.getValue(), endDate.getValue())) {
            return;
        }
        currentPane.updatePane();
    }
    
    // panel 2
    //public String getStartDate(){
    //    return startDate.getValue().toString();
    //}
    
    // panel 2
    ///public String getEndDate() {
     //   return endDate.getValue().toString();
   // }
    
    public LocalDate getStartDate(){
        return startDate.getValue();
    }
    
    public LocalDate getEndDate() {
        return endDate.getValue();
    }
    
    private void addtoAnchor(int currentPaneNum) {
        //System.out.println(INSTANCE);
        anchorPane.getChildren().remove(currentPane);
        //INSTANCE = this;
        currentPane = panesDisplay.get(currentPaneNum);
        currentPane.updatePane();
        try {
            anchorPane.getChildren().add((Pane)currentPane); 
        } catch (ClassCastException e) {
            System.err.println("Error: Could not cast AppPane to Pane: " + e.getMessage());
            }
        
        updateButtonVisibility();
        /*
        switch(currentPaneNum){
            case 1:
            
            Pane newPane = panesDisplay.get(currentPaneNum);
            
          
            
            newPane.setPrefWidth(pane1.getWidth());  // Set dimensions as needed
            newPane.setPrefHeight(pane1.getHeight());
    
            newPane.setLayoutX(pane1.getLayoutX());
            newPane.setLayoutY(pane1.getLayoutY());
    
            anchorPane.getChildren().add(newPane);
    
            currentPane = newPane;
            break;
            
            case 2:
              //newPane.getChildren().remove(paneNumLabel);
           //Label instructionsLabel = new Label("The minimum start date is 2/3/2020 and 2/9/2023");
           //newPane.getChildren().add(instructionsLabel);
           //CovidDataPanel covidDataPanel = new CovidDataPanel(anchorPane);
           //newPane.getChildren().add(covidDataPanel);
           //newPane.getChildren().remove(paneNumLabel);
           // System.out.println("Before CovidDataPanel initialization"); // Debugging statement
           // CovidDataPanel covidDataPanel = new CovidDataPanel(anchorPane);
           // covidDataPanel.initialiseUI(); // Call the method to run initialization
           // System.out.println("After CovidDataPanel initialization"); // Debugging statement
           // newPane.getChildren().add(covidDataPanel);
           //newPane.getChildren().remove(paneNumLabel);
           
           //newPane.getChildren().add(new Label("EEEE"));
        
            //covidDataPanel = new CovidDataPanel(newPane);
            CovidDataPanel pane2 = (CovidDataPanel)panesDisplay.get(currentPaneNum);
            
            anchorPane.getChildren().add(pane2);
            pane2.updatePane();
            currentPane = pane2;
            break;
            
            case 3:
                
            //had to cast pane into borderpane to make use of borderpane functionality
            //(for adam) original pane that fucntions same as the other 3 panels, like welcome panel
            ThirdPane statPane = (ThirdPane)panesDisplay.get(currentPaneNum);
            
            statPane.updatePane();
            anchorPane.getChildren().add(statPane);
            currentPane = statPane;
            
            break;
            
            case 4:
            FourthPane graphPane = (FourthPane) panesDisplay.get(currentPaneNum);
            
            graphPane.updatePane();
            anchorPane.getChildren().add(graphPane);
            currentPane = graphPane;
            
            break;
            
        }*/
    }
    
    private void updateButtonVisibility() {
    // Check if the current pane is not the first pane
    if (currentPaneNum != 1) {
        updateButton.setVisible(true); // Show the button
        updateButton.setDisable(false); // Enable the button
    } else {
        // Hide the button and disable it on the first pane
        updateButton.setVisible(false);
        updateButton.setDisable(true);
    }
    }
    
    // Various similar methods to generate the statistics for pane 3
    
   
   
    
    
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
        
        URL url = getClass().getResource("mainWindow1.fxml");
        Pane root = FXMLLoader.load(url);
        
        Scene scene = new Scene(root);
        
        stage.setMinWidth(780); // Minimum width //660
        stage.setMinHeight(435); // Minimum height
        
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        
        stage.show();
        /*
         if (INSTANCE == null) {
        INSTANCE = this;
        System.out.println(INSTANCE);
        
        }  */
           
    }
    
       
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        INSTANCE = this;
        addtoAnchor(1);
        initializePanes();
        startDate.setValue(LocalDate.of(2020, 2, 3));  
        endDate.setValue(LocalDate.of(2023, 2, 9)); 
        startDate.setEditable(false);
        endDate.setEditable(false);
        
        //updateButtonVisibility();
    }
    
     private void initializePanes() {
        for (AppPane appPane : panesDisplay.values()) {
            try {
                Pane appPaneAsPane = (Pane) appPane;
                appPaneAsPane.setPrefWidth(643);  // Set dimensions as needed
                appPaneAsPane.setPrefHeight(329);
                appPaneAsPane.setLayoutX(0);
                appPaneAsPane.setLayoutY(44);
            AnchorPane.setTopAnchor(appPaneAsPane, 42.5);
            AnchorPane.setBottomAnchor(appPaneAsPane, 30.0);
            AnchorPane.setLeftAnchor(appPaneAsPane, 0.0);
            AnchorPane.setRightAnchor(appPaneAsPane, 0.0);
            } catch (ClassCastException e) {
                
                System.err.println("Error: Could not cast AppPane to Pane: " + e.getMessage());
            }
        }
        
    }
    
    /*private void initializePanes() {
    for (AppPane appPane : panesDisplay.values()) {
        try {
            Pane appPaneAsPane = (Pane) appPane;
            appPaneAsPane.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            appPaneAsPane.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
            appPaneAsPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            appPaneAsPane.layoutBoundsProperty().addListener((observable, oldValue, newValue) -> {
                // Adjust the size of the components within the pane here
                // For example, you can set new sizes or positions based on the new bounds
                // Make sure to account for padding and margins if necessary
            });
        } catch (ClassCastException e) {
            System.err.println("Error: Could not cast AppPane to Pane: " + e.getMessage());
        }
    }
    }*/

    
}
