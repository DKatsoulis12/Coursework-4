import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.AnchorPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Write a description of class ThirdPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ThirdPane extends BorderPane implements AppPane
{
    private CovidDataLoader theCovidDataLoader =  CovidDataLoader.getInstance();
    private DateChecker theDateChecker = DateChecker.getInstance();
    
    public int currentStatNum = 1;
 
    private static final int FONT_SIZE = 25;
    
    private static ThirdPane uniqueInstance;
    
    
    private static final int MAX_STAT_PANE = 4;
    private static final int MIN_STAT_PANE = 1;    
    
    private Button leftButton = new Button("<"); 
    private Button rightButton = new Button(">");
    
    public LocalDate startDate;// = AppGUI.getInstance().getStartLocalDate();
    public LocalDate endDate;// = AppGUI.getInstance().getEndLocalDate();
    
     
    /**
     * Constructor for objects of class ThirdPane
     */
    public ThirdPane()
    {
            
        leftButton.setPrefWidth(135);
        leftButton.setPrefHeight(325);
            
        rightButton.setPrefWidth(135);
        rightButton.setPrefHeight(325);
            
        setLeft(leftButton);
        setRight(rightButton);
            
        leftButton.setOnAction(this::goLeftStats);
        rightButton.setOnAction(this::goRightStats);
            
    }
    
    private void getUpdatedDate(){
        startDate = AppGUI.getInstance().getStartDate();
        endDate = AppGUI.getInstance().getEndDate(); 
    }
    
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
            
    public void goLeftStats (ActionEvent event) {
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
        else { //currentStatNum == 4 
            newLabel = generateGMR2Label();
        }
        
        setCenter(newLabel);
        
    }
    
    


    public void goRightStats (ActionEvent event) {
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
            
            
    public Label generateTotalDeathsLabel () {
        Label totalDeathsLabel;
        
        Integer totalDeaths = theCovidDataLoader.loadTotalDeath(startDate, endDate);
        if (totalDeaths == null){
            //since the database includes dates where not all the columns are filled, all of these cases are addressed similar to below
            totalDeathsLabel = new Label("data base does not fully cover selected period\nplease choose after 3/9/2020 for total deaths");
            totalDeathsLabel.setFont(new Font(FONT_SIZE));
            totalDeathsLabel.setWrapText(true);
            setCenter(totalDeathsLabel); // was statPane1
        }
        else{
            totalDeathsLabel = new Label("Total Deaths: " + totalDeaths);
            totalDeathsLabel.setFont(new Font(FONT_SIZE));
            setCenter(totalDeathsLabel);
        }
        
        return totalDeathsLabel;
    }
    
     public Label generateAverageCasesLabel () {
        Label averageCasesLabel;
        
        Long averageCases = theCovidDataLoader.loadAverageCases(startDate, endDate);
        if(averageCases == null){
            averageCasesLabel = new Label("data base does not fully cover selected period\nplease choose before 2/8/2023 for average cases");
            averageCasesLabel.setFont(new Font(FONT_SIZE));
            averageCasesLabel.setWrapText(true);
            setCenter(averageCasesLabel); // was statPane2
        }
        else{
            averageCasesLabel = new Label("Average Cases per Day: " + averageCases);
            averageCasesLabel.setFont(new Font(FONT_SIZE));
            setCenter(averageCasesLabel);
        }
        
        return averageCasesLabel;
    }
    
    public Label generateGMR1Label () {
        Label averageGMR1Label;
        
        Long GMR1 = theCovidDataLoader.loadAverageGMR(startDate, endDate, 2);   
        if(GMR1 == null){
            averageGMR1Label = new Label("data base does not fully cover selected period\nplease choose between 2/15/2020 and 10/15/2022 for GMR");
            averageGMR1Label.setFont(new Font(FONT_SIZE));
            averageGMR1Label.setWrapText(true);
            setCenter(averageGMR1Label); // was statPane3
        }
        else{
            averageGMR1Label = new Label("Average Retail and Recreation \nPercentage Change: " + GMR1 + "%");
            averageGMR1Label.setFont(new Font(FONT_SIZE));
            setCenter(averageGMR1Label);
        }
        
        return averageGMR1Label;
    }
    
    public Label generateGMR2Label () {
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
}
