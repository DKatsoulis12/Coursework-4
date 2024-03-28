import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

/**
 * ThirdPane is used to display a series of statistics from data imported via CovidDataLoader.
 * 
 * This class was also used to perform our unit testing.
 */
public class ThirdPane extends BorderPane implements AppPane
{
    private CovidDataLoader theCovidDataLoader =  CovidDataLoader.getInstance();
    private DateChecker theDateChecker = DateChecker.getInstance();
    
    public int currentStatNum = 1; // public for Unit Testing purposes
 
    private static final int FONT_SIZE = 25;
    
    private static ThirdPane uniqueInstance;
    
    private static final int MAX_STAT_PANE = 4;
    private static final int MIN_STAT_PANE = 1;    
    
    private Button leftButton = new Button("<"); 
    private Button rightButton = new Button(">");
    
    public LocalDate startDate;
    public LocalDate endDate;
    
    /**
     * Initialise the buttons' functionality so that the user can navigate between the different
     * stats displayed.
     */
    public ThirdPane()
    {
            leftButton.setPrefWidth(135);
            leftButton.setMinHeight(Button.USE_PREF_SIZE);
            leftButton.setMaxHeight(Double.MAX_VALUE);
            
            rightButton.setPrefWidth(135);
            rightButton.setMinHeight(Button.USE_PREF_SIZE);
            rightButton.setMaxHeight(Double.MAX_VALUE);
            
            BorderPane.setMargin(leftButton, new Insets(0, 0, 0, 0)); // Adjust margins as needed
            BorderPane.setMargin(rightButton, new Insets(0, 0, 0, 0));
            
            setLeft(leftButton);
            setRight(rightButton);
            
            leftButton.setOnAction(this::goLeftStats);
            rightButton.setOnAction(this::goRightStats);
    }
    
    /**
     * Updates the start and end dates based on the user input.
     */
    private void getUpdatedDate(){
        startDate = AppGUI.getInstance().getStartDate();
        endDate = AppGUI.getInstance().getEndDate(); 
    }
    
    /**
     * Overrides updatePane method to ensure the correct stat is being displayed based on the 
     * current date values and currentStatNum.
     */
    @Override
    public void updatePane(){
        getUpdatedDate(); 
        Label newLabel;
        // generate and update appropriate stats label
        if (currentStatNum == 1) {
            newLabel = generateTotalDeathsLabel();
        }
        else if (currentStatNum == 2) {
            newLabel = generateAverageCasesLabel();
        }
        else if (currentStatNum == 3) {
            newLabel = generateGMR1Label();
        }
        else { // currentStatNum == 4
            newLabel = generateGMR2Label();
        } 
        
        setCenter(newLabel);
    }
    
    /**
     * Updates the currentGraphNum and displays the appropriate stat when the left button is clicked.
     * 
     * @param event Event associated with the left button being pressed
     */
    private void goLeftStats (ActionEvent event) {
        getUpdatedDate();
        if (theDateChecker.checkButtonDisability(startDate, endDate)) {
            return;
        }
        
        if (currentStatNum <= MIN_STAT_PANE) {
            currentStatNum = MAX_STAT_PANE;
        }
        else {
            currentStatNum--;
        }
    
        // call updatePane to display new stat
        updatePane();
    }
    
    /**
     * Updates the currentStatNum and displays the appropriate stat when the right button is clicked.
     * 
     * @param event Event associated with the right button being pressed
     */
    private void goRightStats (ActionEvent event) {
        getUpdatedDate();
        if (theDateChecker.checkButtonDisability(startDate, endDate)) {
            return;
        }
        
        if (currentStatNum >= MAX_STAT_PANE) {
            currentStatNum = MIN_STAT_PANE;
        }
        else {
            currentStatNum++;
        }
        
        // call updatePane to display new stat
        updatePane();
        }
            
    /**
     * Generates the total deaths label to be displayed on the stats pane by loading the deaths value
     * calculated in CovidDataLoader.
     * 
     * @return the total deaths label
     */        
    private Label generateTotalDeathsLabel () {
        Label totalDeathsLabel;
        
        Integer totalDeaths = theCovidDataLoader.loadTotalDeath(startDate, endDate);
        if (totalDeaths == null){
            //since the database includes dates where not all the columns are filled, all of these cases are addressed similar to below
            totalDeathsLabel = new Label("data base does not fully cover selected period\nplease choose after 3/9/2020 for total deaths");
            totalDeathsLabel.setFont(new Font(FONT_SIZE));
            totalDeathsLabel.setWrapText(true);
            setCenter(totalDeathsLabel);
        }
        else{
            totalDeathsLabel = new Label("Total Deaths: " + totalDeaths);
            totalDeathsLabel.setFont(new Font(FONT_SIZE));
            setCenter(totalDeathsLabel);
        }
        
        return totalDeathsLabel;
    }
    
    /**
     * Generates the average cases label to be displayed on the stats pane by loading the cases value
     * calculated in CovidDataLoader.
     * 
     * @return the average cases label
     */
    private Label generateAverageCasesLabel () {
        Label averageCasesLabel;
        
        Long averageCases = theCovidDataLoader.loadAverageCases(startDate, endDate);
        if(averageCases == null){
            averageCasesLabel = new Label("data base does not fully cover selected period\nplease choose before 2/8/2023 for average cases");
            averageCasesLabel.setFont(new Font(FONT_SIZE));
            averageCasesLabel.setWrapText(true);
            setCenter(averageCasesLabel);
        }
        else{
            averageCasesLabel = new Label("Average Cases per Day: " + averageCases);
            averageCasesLabel.setFont(new Font(FONT_SIZE));
            setCenter(averageCasesLabel);
        }
        
        return averageCasesLabel;
    }
    
    /**
     * Generates the first Google Mobility Label (Retail and Recreation Percentage Change) to be displayed 
     * by loading the value calculated in CovidDateLoader.
     * 
     * @return the Retail and Recreation Percentage Change label
     */
    private Label generateGMR1Label () {
        Label averageGMR1Label;
        
        Long GMR1 = theCovidDataLoader.loadAverageGMR(startDate, endDate, 2);   
        if(GMR1 == null){
            averageGMR1Label = new Label("data base does not fully cover selected period\nplease choose between 2/15/2020 and 10/15/2022 for GMR");
            averageGMR1Label.setFont(new Font(FONT_SIZE));
            averageGMR1Label.setWrapText(true);
            setCenter(averageGMR1Label);
        }
        else{
            averageGMR1Label = new Label("Average Retail and Recreation \nPercentage Change: " + GMR1 + "%");
            averageGMR1Label.setFont(new Font(FONT_SIZE));
            setCenter(averageGMR1Label);
        }
        
        return averageGMR1Label;
    }
    
    /**
     * Generates the second Google Mobility Label (Average Transit Stations Usage Percentage Change) to be 
     * displayed by loading the value calculated in CovidDateLoader.
     * 
     * @return the Average Transit Stations Usage Percentage Change label
     */
    private Label generateGMR2Label () {
        Label averageGMR2Label;
        
        Long GMR2 = theCovidDataLoader.loadAverageGMR(startDate, endDate, 5);
        if (GMR2 == null){
            averageGMR2Label = new Label("data base does not fully cover selected period\nplease choose between 2/15/2020 and 10/15/2022 for GMR");
            averageGMR2Label.setFont(new Font(FONT_SIZE));
            averageGMR2Label.setWrapText(true);
            setCenter(averageGMR2Label);
        }
        else{
            averageGMR2Label = new Label("Average Transit Stations \nPercentage Change: " + GMR2 + "%");
            averageGMR2Label.setFont(new Font(FONT_SIZE));
            setCenter(averageGMR2Label);
        }
        
        return averageGMR2Label;
    }
    
    // Methods below for Unit Testing //
    
    /**
     * @return GMR1 label for Unit Testing
     */
    public Label generateGMR1Label_JUnitTest() {
        return generateGMR1Label();
    }
    
    /**
     * @return GMR2 label for Unit Testing
     */
    public Label generateGMR2Label_JUnitTest() {
        return generateGMR2Label();
    }
    
    /**
     * @return total deaths label for Unit Testing
     */
    public Label generateTotalDeathsLabel_JUnitTest() {
        return generateTotalDeathsLabel();
    }
    
    /**
     * @return average cases label for Unit Testing
     */
    public Label generateAverageCasesLabel_JUnitTest() {
        return generateAverageCasesLabel();
    }
    
    /**
     * Call the goleftStats method to update currentStatNum for Unit Testing
     */
    public void goLeftStats_JUnitTest(ActionEvent event) {
        goLeftStats(event);
    }
    
    /**
     * Call the gorightStats method to update currentStatNum for Unit Testing
     */
    public void goRightStats_JUnitTest(ActionEvent event) {
        goRightStats(event);
    }
}
