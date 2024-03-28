
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Write a description of JavaFX class SecondPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

/**
 * Represents a panel displaying COVID-19 data across different London boroughs.
 * It provides a graphical representation where each borough is associated with a button.
 * The color of the button changes based on the relative number of deaths in the borough,
 * offering a visual comparison across the region.
 */

public class CovidDataPanel extends Pane implements AppPane
{
    
    //Method one. Each row is a HBOX containing assigned row of hexagons. All contained in a VBXOX - > 95% likely, this method
    //Method two: Each hexagon picture can be a button and a label rendered on top of it. The label text is the one changing color
    
    //private final String MAP_IMAGE_PATH = "borough.png";
    //private ImageView mapImageVIew;
    
    // Lists to store COVID-19 data and UI components for displaying data
    private List<CovidData> covidDataList = CovidDataLoader.getInstance().load(); //new CovidDataLoader().load();
    private String startDate;// = AppGUI.getInstance().getStartDate();
    private String endDate;// = AppGUI.getInstance().getEndDate();
    private HashMap<Button, Integer> boroughButtons = new HashMap<Button, Integer>();
    private int averageDeaths;
    private HashMap<String,Button> boroughNamesMappings = new HashMap<String,Button>();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
    /**
     * Constructs a new instance of CovidDataPanel. This method initializes the UI components
     * and loads COVID-19 data.
     */
    public CovidDataPanel() {
        //System.out.println("E");
        initialiseUI();
        //anchor.getChildren().add(this);
    }
    
    /**
     * Initializes the user interface for the CovidDataPanel. This includes setting up the layout,
     * creating borough buttons, and configuring event handlers for interactive components.
     */
    public void initialiseUI(){
       VBox root = new VBox();
       root.setAlignment(Pos.CENTER); // Aligns all items to the center of the VBox     
       
       // Creating rows for borough buttons using HBox for horizontal alignment
       HBox row1 = new HBox();
       
       HBox row2 = new HBox();
       HBox row3 = new HBox();
       HBox row4 = new HBox();
       HBox row5 = new HBox();
       HBox row6 = new HBox();
       HBox row7 = new HBox();
             
       //Button updateButton = new Button("Update");
       
       // Aligns items within the row to the center
       row1.setAlignment(Pos.CENTER); 
       row2.setAlignment(Pos.CENTER);
       row3.setAlignment(Pos.CENTER);
       row4.setAlignment(Pos.CENTER);
       row5.setAlignment(Pos.CENTER);
       row6.setAlignment(Pos.CENTER);
       row7.setAlignment(Pos.CENTER);
       
       getUniqueBoroughs();// Retrieves unique boroughs and creates buttons for them
       addBoroughButtons(); // Adds borough buttons to the UI
       createInformationWindow(); // Prepares the information window (not shown by default)
       
       // Add borough buttons to their respective rows based on geographical or categorical grouping
       row1.getChildren().addAll(boroughNamesMappings.get("Enfield"));
    
       row2.getChildren().addAll(boroughNamesMappings.get("Barnet"),boroughNamesMappings.get("Haringey"),boroughNamesMappings.get("Waltham Forest"));
       
       row3.getChildren().addAll(boroughNamesMappings.get("Harrow"),boroughNamesMappings.get("Brent"),boroughNamesMappings.get("Camden"),boroughNamesMappings.get("Islington"),
       boroughNamesMappings.get("Hackney"),boroughNamesMappings.get("Redbridge"),boroughNamesMappings.get("Havering"));
       
       row4.getChildren().addAll(boroughNamesMappings.get("Hillingdon"),boroughNamesMappings.get("Ealing"),boroughNamesMappings.get("Kensington And Chelsea"),
       boroughNamesMappings.get("Westminster"),boroughNamesMappings.get("Tower Hamlets"),boroughNamesMappings.get("Newham"),boroughNamesMappings.get("Barking And Dagenham"));
       
       row5.getChildren().addAll(boroughNamesMappings.get("Hounslow"),boroughNamesMappings.get("Hammersmith And Fulham"),boroughNamesMappings.get("Wandsworth"),
       boroughNamesMappings.get("City Of London"),boroughNamesMappings.get("Greenwich"),boroughNamesMappings.get("Bexley"));
       
       row6.getChildren().addAll(boroughNamesMappings.get("Richmond Upon Thames"),boroughNamesMappings.get("Merton"),boroughNamesMappings.get("Lambeth"),
       boroughNamesMappings.get("Southwark"),boroughNamesMappings.get("Lewisham"));
       
       row7.getChildren().addAll(boroughNamesMappings.get("Kingston Upon Thames"),boroughNamesMappings.get("Sutton"),
       boroughNamesMappings.get("Croydon"),boroughNamesMappings.get("Bromley"));
       
       // Ensure that each row grows with the VBox
       VBox.setVgrow(row1, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row2, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row3, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row4, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row5, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row6, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row7, javafx.scene.layout.Priority.ALWAYS);
       
       // Add all rows to the root VBox
       root.getChildren().addAll(row1);

       root.getChildren().addAll(row2);
       
       root.getChildren().addAll(row3);
       
       root.getChildren().addAll(row4);
       
       root.getChildren().addAll(row5);
       
       root.getChildren().addAll(row6);
       
       root.getChildren().addAll(row7);
       
       setButtonResize(); // Adjust button sizes based on content and available space
        
       this.getChildren().add(root); // Add the root VBox to the Pane
       
       // Bind the layout properties of the root to the Pane for dynamic resizing
        root.layoutXProperty().bind(this.widthProperty().divide(2).subtract(root.widthProperty().divide(2)));
        root.layoutYProperty().bind(this.heightProperty().divide(2).subtract(root.heightProperty().divide(2)));
        
        root.prefWidthProperty().bind(this.widthProperty());
        root.prefHeightProperty().bind(this.heightProperty());
        
       //updateButton.setOnAction(event -> updatePane());
      
       //this.getChildren().add(updateButton);
       //updatePane();
       //calculateAverageDeaths();
       //buttonColourIterator();
       
    }
    
    /**
     * Updates the display pane with the latest data. This method refreshes the data based on the
     * selected date range from the main application GUI and updates the borough buttons to reflect
     * the latest statistics.
     */
    @Override
    public void updatePane(){
        //if(DateChecker.getInstance().checkButtonDisability2()){
         //   return;
        //}
        startDate = AppGUI.getInstance().getStartDate().toString();
        endDate = AppGUI.getInstance().getEndDate().toString();
        calculateAverageDeaths(); // Recalculate average deaths based on new date range
        addBoroughButtons(); // Re-add borough buttons to reflect updated data
        buttonColourIterator(); // Update button colors based on new data
    }
    
    /*private void clickUpdate(ActionEvent event) {
        boolean f = AppGUI.getInstance().toggleButtonDisability();

        if (f == false) {
            updatePane(event);
        } else {
            AppGUI.getInstance().alertSetUp();
        }
    }*/
    
    /**
     * Calculates the average deaths across all boroughs for the selected date range.
     * This average is used to determine the color of borough buttons for relative comparison.
     */
    private void calculateAverageDeaths(){
        
        int totalBoroughDeaths = getNewDeathsForBorough(covidDataList, startDate, endDate);
        
        // Assuming 33 boroughs for calculation
        averageDeaths = totalBoroughsAverageDeaths(totalBoroughDeaths, 33);
        
        
    }
    
    /**
     * Creates a button for a specific London borough. The button displays the borough name
     * and changes color based on the number of deaths relative to other boroughs.
     * 
     * @param borough The name of the London borough.
     * @return A Button object configured with the borough name and event handlers.
     */
    private Button addButton(String borough){
            Button btn = new Button(borough);
            
            // Set styles for hover in and hover out
            btn.setOnMouseEntered(e -> {
            // Directly setting the hover style
            btn.setStyle("-fx-background-color: darkgray; -fx-border-color: black; -fx-border-width: 1;");
            
        });
        
        // Handle mouse exiting the button area
        btn.setOnMouseExited(e -> {
            // Reverting to the base style
            changeButtonColour(btn,boroughButtons.get(btn));
            //buttonColourIterator();
        });
            //List<CovidData> covidDataList = new CovidDataLoader().load();
            List<CovidData> boroughSpecificData = specificBoroughData(covidDataList, borough);
            btn.setOnAction(e -> handleButtonClick(btn,boroughSpecificData,borough));
            return btn;
    }
    
    /**
     * Handles click events on borough buttons. This method opens a new window displaying
     * detailed COVID-19 statistics for the selected borough.
     * 
     * @param btn The button that was clicked.
     * @param boroughListData A list of CovidData objects for the selected borough.
     * @param borough The name of the borough associated with the button.
     */
    private void handleButtonClick(Button btn, List<CovidData> boroughListData,String borough) {
        //System.out.println("Button clicked: " + btn.getText());
        
        InformationWindow infoWindow = new InformationWindow(boroughListData,borough);
        infoWindow.start(new Stage()); // Opens a new stage with borough data
    }
    
    //private void updateDate(ActionEvent event) {
      //  this.startDate = AppGUI.getInstance().getStartDate().toString();
      //  this.endDate = AppGUI.getInstance().getEndDate().toString();
   // }
    
   /**
     * Changes the color of a borough button based on the number of deaths relative to the
     * average across all boroughs. Colors range from green (fewer deaths) to red (more deaths).
     * 
     * @param btn The borough button to update.
     * @param currentBoroughDeaths The number of deaths in the borough for the selected date range.
     */
    private void changeButtonColour(Button btn, int currentBoroughDeaths){
        
        //System.out.println(averageDeaths + " is the average");
        
        //System.out.println(currentBoroughDeaths + " is the current borugh deaths");
        // Comparing current borough deaths against the average to decide button color
        if(currentBoroughDeaths <= 0.9 *averageDeaths){
            btn.setStyle("-fx-background-color: #129920;"); // Green for below average deaths
        }
        else if(currentBoroughDeaths <= 1.4 *averageDeaths){
            btn.setStyle("-fx-background-color: #f2a31b;"); // Orange for deaths around the average
        }
        else{
            btn.setStyle("-fx-background-color: #960606;"); // Red for significantly above average deaths
        }
    }
    
    /**
     * Filters the comprehensive list of COVID-19 data to extract information relevant to a specific London borough.
     * This filtered data is used for detailed analysis or display pertaining to that borough.
     * 
     * @param covidDataList The full list of CovidData objects.
     * @param borough The name of the borough for which data is being filtered.
     * @return A filtered list of CovidData objects relevant to the specified borough.
     */
    private List<CovidData> specificBoroughData(List<CovidData> covidDataList, String borough){
        List<CovidData> boroughData = new ArrayList<>();
        for (CovidData data: covidDataList) {
            if (data.getBorough().equals(borough)) {
                boroughData.add(data); // Adding only data relevant to the specified borough
            }
        }
        return boroughData; // Return the filtered list
    }
    
    /**
     * Iterates through all borough buttons and updates their color based on the current
     * death statistics and the average deaths across all boroughs.
     */
    private void buttonColourIterator(){
        System.out.println("Average Deaths: " + averageDeaths);
        for (Map.Entry<Button, Integer> entry : boroughButtons.entrySet()) {
            Button borough = entry.getKey();
            int totalDeaths = entry.getValue();
            System.out.println(borough + "deaths: " + totalDeaths);
            changeButtonColour(borough, totalDeaths); // Updating button color based on death statistics
        }
    }
    
    /**
     * Calculates the average number of deaths across all boroughs for the selected date range.
     * 
     * @param totalDeathsTogether The total number of deaths across all boroughs.
     * @param boroughNum The total number of boroughs.
     * @return The average number of deaths per borough.
     */
    private int totalBoroughsAverageDeaths(int totalDeathsTogether, int boroughNum) {
        //return totalDeathsTogether;
        return totalDeathsTogether/boroughNum; // Dividing total deaths by number of boroughs to get the average
    }
    
    /**
     * Gets the total number of new deaths for all boroughs within the selected date range.
     * 
     * @param covidList A list of CovidData objects.
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @return The total number of new deaths within the selected date range.
     */
    private int getNewDeathsForBorough(List<CovidData> covidList, String startDate, String endDate) {
        
        
        boolean startDateReached = false; 
        boolean endDateReached = false;
        int totalFinalDeaths = 0; 

        /*
         * GOAL: flexibility in extensibility
         * 
         * Advantages:
         * Simplicity and Readibility and easy to understand
         * Less Branching, which can lead to more predicatable performance patterns
         * Flexibility for different Aggregations of various types within same loop
         */
        
        //The difference here is this way uses less branch comparisons,
        //therefore less memory, but more computations because more data needs to be fetched each time. 
        for (CovidData data: covidList) {
            
            if (data.getDate().equals(startDate)) {
                startDateReached = true;
            }if (data.getDate().equals(endDate)) {
                endDateReached = true;
            }
            if (startDateReached) {
                totalFinalDeaths += data.getNewDeaths(); // Accumulate deaths once within the date range
            }
            // Stop the loop once the endDate is reached
            if (startDateReached && endDateReached) {
                return (totalFinalDeaths);
            } 
        }
        
        return totalFinalDeaths;
    }
    
    /**
     * Populates the UI with buttons for each unique borough. Each button is associated with the number of new deaths
     * for that borough within the specified date range. Button properties are set to ensure they utilize available space efficiently.
     */
    private void addBoroughButtons() {
        // Iterate through each borough, creating and configuring its button
        for (String borough : boroughNamesMappings.keySet()) {
            Button button = boroughNamesMappings.get(borough);
            button.setMinHeight(Button.USE_PREF_SIZE);
            button.setMaxHeight(Double.MAX_VALUE); 
            button.setMinWidth(Button.USE_PREF_SIZE);
            button.setMaxWidth(Double.MAX_VALUE);
             
            // Associate each button with the number of new deaths for its borough
            boroughButtons.put(button, getNewDeathsForBorough(specificBoroughData(covidDataList,borough), startDate, endDate));
        }
        
        
    }
    
    /**
     * Generates statistics for a specific borough within the given date range.
     * It filters the covid data to include only entries for the specified borough and date range.
     *
     * @param borough The name of the borough for which statistics are to be generated.
     * @return A list of {@link CovidData} instances representing the filtered data for the given borough and date range.
     */
    private List<CovidData> generateStatistics(String borough) {
        List<CovidData> boroughDataList = specificBoroughData(covidDataList,borough);
        List<CovidData> boroughDataRangeData = new ArrayList<>();
        boolean startDateReached = false;
        boolean endDateReached = false;
        /*
         * GOAL: collect subset of data for detailed analysis
         * 
         * Advantages:
         * Minimised Date Comparisons
         * Efficiency in Data selection 
         * Direct Applicability to UI elements
         */
          //Even though the below loop has similar logic to that of the loop getNewDeathsForBorough() the following loop is designed to make as little date comparisons
        //as possible, therefore being more time efficent.
        for(CovidData data: boroughDataList){
             // Filter data within the specified date range for the borough
             
            if(!startDateReached){
               if (data.getDate().equals(startDate)){
                    startDateReached = true;
                    boroughDataRangeData.add(data);
               } 
            }
            else if(startDateReached && !endDateReached){
                if (data.getDate().equals(endDate)){
                    endDateReached = true; 
                    boroughDataRangeData.add(data);
                }
                else{
                   boroughDataRangeData.add(data);
                }
            }
            else if(startDateReached && endDateReached){
                break;
            }
        }
        return boroughDataRangeData;
    }
    
    /**
     * Identifies unique boroughs from the loaded Covid data and creates a button for each borough.
     * Each button is stored in a mapping for later use in the UI.
     */
    private void getUniqueBoroughs() {
        List<String> uniqueBoroughs = new ArrayList<>();
        for (CovidData data : covidDataList) {
            String borough = data.getBorough();
            if (!uniqueBoroughs.contains(borough)) {
                uniqueBoroughs.add(borough);
                boroughNamesMappings.put(borough,addButton(borough));
            }
        }
        //return uniqueBoroughs;
    }
    
    /**
     * Prepares a new window intended to display detailed information. This method sets up the window but does not show it.
     */
    private void createInformationWindow() {
        Stage newStage = new Stage();
        StackPane newRoot = new StackPane();
        Scene newScene = new Scene(newRoot, 200, 150);
    }
    
    /**
     * Sets up horizontal growth behavior for borough buttons, allowing them to expand to fill available space.
     * This method ensures that the layout remains responsive and visually appealing across different window sizes.
     */
    public void setButtonResize(){
        //allows certain buttons to take up as much space as possible horizontally, only allowed for the buttons in the row with most buttons
        //List<Button> buttonsSpaceHorizontal = new ArrayList<>();
        List<String> buttonsSpaceHorizontal = Arrays.asList("Westminster","Hillingdon","Ealing","Kensington And Chelsea","Tower Hamlets","Newham",
        "Barking And Dagenham");
        
        for (String buttonString : buttonsSpaceHorizontal) {
            HBox.setHgrow(boroughNamesMappings.get(buttonString), javafx.scene.layout.Priority.ALWAYS);
        }
    
         /*
         * binds all other buttons' wdith to one of the above buttons, so they change horizontal length to a certain extent instead of taking
         * up all horzitontal space which would take up the whole pane. each borough is matched with the above buttons that has the most similar
         * length which is why they are set seperately and looped through and set under a certain condition
         */
        
        Map<String, List<String>> bindings = new HashMap<>();
        bindings.put("Westminster", Arrays.asList("Lambeth", "Southwark", "Lewisham", "Hounslow", "Enfield", "Redbridge", "Greenwich"));
        bindings.put("Newham", Arrays.asList("Barnet", "Harrow", "Camden", "Merton", "Bexley", "Sutton"));
        bindings.put("Hillingdon", Arrays.asList("Haringey", "Islington", "Hackney", "Havering", "Croydon", "Bromley"));
        bindings.put("Kensington And Chelsea", Arrays.asList("Richmond Upon Thames", "Kingston Upon Thames", "City Of London"));
        bindings.put("Barking And Dagenham", Arrays.asList("Hammersmith And Fulham", "Waltham Forest"));
        bindings.put("Tower Hamlets", Arrays.asList("Wandsworth"));
        bindings.put("Ealing", Arrays.asList("Brent"));
    
        // Iterate through the map and bind the width properties
        for (Map.Entry<String, List<String>> entry : bindings.entrySet()) {
            Button referenceButton = boroughNamesMappings.get(entry.getKey());
            for (String boroughName : entry.getValue()) {
                Button boroughButton = boroughNamesMappings.get(boroughName);
                if (boroughButton != null && referenceButton != null) {
                    boroughButton.prefWidthProperty().bind(referenceButton.widthProperty());
                }
            }
        }
    }
}