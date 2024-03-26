import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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
    
    
    /**
     * Singleton instance of {@code AppGUI}.
     * Ensures that only one instance of the application's GUI class exists.
     */
    private static AppGUI INSTANCE; 
    
    
    /**
     * The main container for the application's GUI components.
     */
    @FXML
    private AnchorPane anchorPane;
    
    
    /**
     * Button for performing right action, going to the next pane
     */
    @FXML
    private Button button1;
    
    
    /**
     * Another button for performing left action, going to the previous pane
     */
    @FXML
    private Button button2;
    
    
    /**
     * DatePicker for selecting the start date of a query
     */
    @FXML
    public DatePicker startDate;
    
    
    /**
     * DatePicker for selecting the end date of a query
     */
    @FXML
    public DatePicker endDate;
    
    /**
     * Button to trigger an update action to update the contents of a page i.e. data displayed once date range has been manipulated
     */
    @FXML
    private Button updateButton;
    
    
    //----------------------
    //BELOW 6 - RYAN(EDIT AS NECESSARY OR AS YOU WISH PLEASE)
    //----------------------
    
    /**
     * Loader for COVID data, utilizing the Singleton pattern for instance management.
     */
    private CovidDataLoader theCovidDataLoader =  CovidDataLoader.getInstance();
    
    
    /**
     * Checker for validating selected dates, following the Singleton pattern.
     */
    private DateChecker theDateChecker = DateChecker.getInstance();
    
    
    
    /**
     * Instance of {@code FirstPane}, representing the welcome Panel explaining necessary details and manual. 
     */
    private FirstPane pane1 = new FirstPane();
    
    
    /**
     * Panel displaying COVID data, boroughs relative deaths, and information window per borough.
     * This is an example which demonstrates encapsulation of data-related UI.
     */
    private CovidDataPanel pane2 = new CovidDataPanel();
    
    
    /**
     * Instance of {@code ThirdPane}, representing the Statistics Panel
     */
    private ThirdPane pane3 = new ThirdPane();//ThirdPane.getInstance();
    
    
    /**
     * Instance of {@code FourthPane}, representing the Challenge Panel
     */
    private FourthPane pane4 = new FourthPane();

    
    
    //Mine
    /**
     * Maps numerical identifiers to instances of {@code AppPane}, facilitating navigation and pane management.
     */
    private HashMap<Integer,AppPane> panesDisplay = new HashMap<Integer,AppPane>(){{
        put(1,pane1);
        put(2,pane2);
        put(3,pane3);
        put(4,pane4);
    }};
    
    
    /**
     * Currently active pane, initially set to {@code pane1}.
     */
    private AppPane currentPane = pane1;
    
    /**
     * Numerical identifier of the currently active pane.
     */
    private int currentPaneNum = 1;
    
    //mine - PRIORITY
    /**
     * Provides access to the singleton instance of {@link AppGUI}. If no instance exists, one is created.
     * This method ensures that only a single instance of {@link AppGUI} is active at any time,
     * which conforms with the Singleton design pattern.
     * 
     * @return The singleton instance of {@link AppGUI}.
     */
    public static AppGUI getInstance() {
        System.out.println(INSTANCE); // Print the current instance to the console for debugging purposes.
        return INSTANCE; // Return the singleton instance. This ensures that there's only one instance of AppGUI throughout the application.
    }
    
    //mine
    /**
     * Handles the action of moving forward in the pane display. 
     * This method increments the current pane number and updates the display based on that number.
     * It checks the date range validity before proceeding, ensuring that the navigation only occurs if the date range is valid.
     * The pane display should move forward in a circular loop, meaning the next pane after pane 4 is pane 1
     * 
     * @param event: The {@link ActionEvent} that triggered this method upon click
     */
    @FXML
    private void goForward(ActionEvent event) {
        // Check if the selected date range is valid. If not, navigation is prevented.
        if (theDateChecker.checkButtonDisability(startDate.getValue(), endDate.getValue())) {
            return; // Early exit if the selected date range is invalid, preventing pane navigation.
        }
        // Move to the next pane in a circular manner. If we're at the last pane, this wraps around to the first pane.
        currentPaneNum = ((currentPaneNum) % panesDisplay.size()) + 1; 
        // Update the display to show the new current pane by calling addtoAnchor, which handles the transition.
        addtoAnchor(currentPaneNum); 
    }
    
    //mine
    /**
     * Handles the action of moving backward in the pane display.
     * This method decrements the current pane number and updates the display accordingly. 
     * Similar to {@code goForward}, it first checks the validity of the date range and only allows navigation if the date range is valid.
     * The pane display should move backward in a circular loop, meaning the previous pane before pane 1 is pane 4
     * 
     * @param event The {@link ActionEvent} that triggered this method upon click
     */

    @FXML
    private void goBack(ActionEvent event) {
        // Check for the date range's validity before allowing navigation to ensure data correctness.
        if (theDateChecker.checkButtonDisability(startDate.getValue(), endDate.getValue())) {
            return; // Early exit if the selected date range is invalid, preventing pane navigation.
        }
        // Calculate the previous pane number in a circular loop. This ensures that navigation from the first pane wraps around to the last.
        currentPaneNum = (currentPaneNum - 2 + panesDisplay.size()) % panesDisplay.size() + 1;
        // Show the determined pane by updating the anchor pane's content.
        addtoAnchor(currentPaneNum); 
    }
    
    //yours
    @FXML
    private void updatePressed(){
        if (theDateChecker.checkButtonDisability(startDate.getValue(), endDate.getValue())) {
            return; 
        }
        currentPane.updatePane(); 
    }
    
   //mine
   /**
     * Retrieves the start date selected by the user.
     * 
     * DatePicker is a representation of a calendar
     * @return The {@link LocalDate} representing the start date selected in the date picker.
     */
    public LocalDate getStartDate(){
        // Retrieve the start date from the DatePicker. This is the lower bound of the date range for data filtering.
        return startDate.getValue();
    }
    
    //mine
    /**
     * Retrieves the end date selected by the user.
     * 
     * DatePicker is a representation of a calendar
     * @return The {@link LocalDate} representing the end date selected in the date picker.
     */
    public LocalDate getEndDate() {
        // Retrieve the end date from the DatePicker. This is the upper bound of the date range for data filtering.
        return endDate.getValue();
    }
    
    //mine and (for the try and catch, Ryan) ->PRIORITY
    /**
     * Updates the display to show the pane corresponding to the current pane number. It removes the current pane from the display and adds the new one.
     * This method dynamically updates the UI based on user navigation (forward/back). 
     * It also handles the casting of {@link AppPane} to {@link Pane} and updates button visibility based on the current pane.
     * This is to make sure that when the new pane is added, it really is a Pane instance.
     * 
     * @param currentPaneNum The number representing the current pane to be displayed. Used to retrieve the corresponding {@link AppPane}
     * from {@code panesDisplay}.
     */
    private void addtoAnchor(int currentPaneNum) {
        // Remove the currently displayed pane from the anchor pane to make room for the new pane.
        anchorPane.getChildren().remove(currentPane);
        // Retrieve the new current pane based on the currentPaneNum, ensuring we are displaying the correct pane.
        currentPane = panesDisplay.get(currentPaneNum);
        // Call updatePane on the new current pane to ensure its content is up-to-date before displaying it.
        currentPane.updatePane();
        
        try {
            // Attempt to add the new current pane to the anchor pane. 
            //This cast is necessary as AppPane needs to be treated as a Pane here.
            anchorPane.getChildren().add((Pane)currentPane); 
        } 
        catch (ClassCastException e) {
            System.err.println("Error: Could not cast AppPane to Pane: " + e.getMessage());
        }
        // Update the visibility state of the update button based on whether or not we're on the first pane.
        //updateButton does not need to be there in First Welcome Pane(l), and only is there when data is visualised and displayed
        updateButtonVisibility();
        
    }
    
    //yours
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

   
    
    //mine
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
        
        stage.setMinWidth(1020); // Minimum width //660 (880 before adjusted for expanded Welcome Panel)
        stage.setMinHeight(435); // Minimum height
        
        stage.setTitle("JavaFX Example");
        stage.setScene(scene);
        
        stage.show();
        
        //delete once comment written in initialize
        /*
         if (INSTANCE == null) {
        INSTANCE = this;
        System.out.println(INSTANCE);
        
        }  */
           
    }
    
       
    //mine
    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * It sets up the singleton instance, initializes the date pickers with default values, and prepares the initial pane display.
     * 
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Assign this controller instance as the singleton instance.
        This ensures that initially, when Application is run, the singleton instance is defined since initialize method is immediately called
        This prevents the singleton instance being referenced as null upon use by other classes*/
        INSTANCE = this; 
        
         // Initialize the display with the first pane.
        addtoAnchor(1);
        
        //RYAN - PLEASE EDIT THE BELOW COMMENT WITH MORE DETAIL
         //RYAN - PLEASE EDIT THE BELOW COMMENT WITH MORE DETAIL
          //RYAN - PLEASE EDIT THE BELOW COMMENT WITH MORE DETAIL
           //RYAN - PLEASE EDIT THE BELOW COMMENT WITH MORE DETAIL
            //RYAN - PASTING 4 TIMES SO YOU CAN SEE
        // Prepare the panes and the date pickers with default values.
        initializePanes();
        startDate.setValue(LocalDate.of(2020, 2, 3));  
        endDate.setValue(LocalDate.of(2023, 2, 9)); 
        
        // Set initial values for the date pickers to ensure they have valid dates and are not editable.
        startDate.setEditable(false);
        endDate.setEditable(false);
        
        //updateButtonVisibility();
    }
    
    //yours
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

    
}
