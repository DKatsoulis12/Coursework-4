import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.time.LocalDate;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map;
import java.time.format.DateTimeFormatter;


/**
 * FourthPane is used for the challenge task and displays data imported
 * via CovidDataLoader graphically with the help of the LineChart class.
 * 
 */
public class FourthPane extends BorderPane implements AppPane
{
    private CovidDataLoader theCovidDataLoader =  CovidDataLoader.getInstance();
    private DateChecker theDateChecker = DateChecker.getInstance();
    private int currentGraphNum = 1;
 
    private static final int FONT_SIZE = 25;
    
    private static FourthPane uniqueInstance;
    
    
    private static final int MAX_GRAPH_PANE = 3;
    private static final int MIN_GRAPH_PANE = 1;    
    
    private Button leftButton = new Button("<"); 
    private Button rightButton = new Button(">");
    
    private LocalDate startDate;
    private LocalDate endDate;
    
    /**
     * Initialise the buttons' functionality so that the user can navigate between the
     * different graphs displayed.
     */
    public FourthPane()
    {
        leftButton.setPrefWidth(35);
        leftButton.setMinHeight(Button.USE_PREF_SIZE);
        leftButton.setMaxHeight(Double.MAX_VALUE);
        
        rightButton.setPrefWidth(35);
        rightButton.setMinHeight(Button.USE_PREF_SIZE);
        rightButton.setMaxHeight(Double.MAX_VALUE);
        
        setLeft(leftButton);
        setRight(rightButton);
        
        leftButton.setOnAction(this::goLeft);
        rightButton.setOnAction(this::goRight);        
    }

    /**
     * Updates the start and end dates based on the user input.
     */
    private void getUpdatedDate(){
        startDate = AppGUI.getInstance().getStartDate();
        endDate = AppGUI.getInstance().getEndDate(); 
    }
    
    /**
     * Overrides updatePane method to ensure the correct graph is being displayed based on
     * the current date values and currentGraphNum.
     */
    @Override
    public void updatePane() {
        getUpdatedDate();
        
        LineChart <String, Integer> graphToDisplay;
        if (currentGraphNum == 1) {
            graphToDisplay = generateCasesAndTestsGraph();
            setCenter(graphToDisplay);
        }
        else if (currentGraphNum == 2) {
            graphToDisplay = generateDeathsGraph();
            setCenter(graphToDisplay);
        }
        else if (currentGraphNum == 3) {
            graphToDisplay = generateCovidCasesGraph();
            setCenter(graphToDisplay);
        }
    }
    
    /**
     * Updates the currentGraphNum and displays the approprate graph when the left
     * button is clicked.
     * 
     * @param event Event associated with clicking the left button
     */
    private void goLeft (ActionEvent event) {
        getUpdatedDate();
        
        if (theDateChecker.checkButtonDisability(startDate, endDate)) {
            return;
        }
        
        if (currentGraphNum <= MIN_GRAPH_PANE) {
            currentGraphNum = MAX_GRAPH_PANE;
        }
        else {
            currentGraphNum--;
        }
        
        // call updatePane to display new graph
        updatePane();
    }
    
    /**
     * Updates the currentGraphNum and displays the appropriate graph when the right
     * button is clicked.
     * 
     * @param event Event associated with clicking the left button
     */
    private void goRight (ActionEvent event) {
        getUpdatedDate();
        
        if (theDateChecker.checkButtonDisability(startDate, endDate)) {
          return;
        }
        
        if (currentGraphNum >= MAX_GRAPH_PANE) {
            currentGraphNum = MIN_GRAPH_PANE;
        }
        else {
            currentGraphNum++;
        }
        
        // call updatePane to display new graph
        updatePane();
    }
    
    /**
     * Generates the Covid Cases Graph by looping through each entry in the casesMap
     * from CovidDataLoader and adding it to the chart.
     * 
     * @return the Covid Cases LineChart 
     */
    private LineChart generateCovidCasesGraph () {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Date");
        yAxis.setLabel("Number of Cases");
        
        final LineChart<String,Number> casesGraph = new LineChart<>(xAxis,yAxis);
        
        casesGraph.setTitle("Covid Cases Graph");
        
        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Covid Cases");
        
        LinkedHashMap <LocalDate, Integer> casesMap = theCovidDataLoader.loadDailyCases(startDate, endDate);
        
        // add each date and associated cases value to the casesGraph
        for (Map.Entry <LocalDate, Integer> entry : casesMap.entrySet()) {
            LocalDate date = entry.getKey();
            Integer casesValue = entry.getValue();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = date.format(formatter);
            
            series.getData().add(new XYChart.Data(dateString, casesValue));
        }
        casesGraph.getData().add(series);
        
        casesGraph.setCreateSymbols(false);
        
        return casesGraph;
    }
    
    
    /**
     * Generates the graph containing the cases and tests recorded by looping through
     * each entry in the cases and tests LinkedHashMaps and adding them to the chart.
     * 
     * @return the Cases and Tests LineChart
     */
    private LineChart generateCasesAndTestsGraph () {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Date");
        
        final LineChart<String,Number> casesAndTests = new LineChart<>(xAxis,yAxis);
        
        casesAndTests.setTitle("Reported Tests and Cases Graph");
        
        XYChart.Series testsSeries = new XYChart.Series();
        testsSeries.setName("Number of Tests");
        
        XYChart.Series casesSeries = new XYChart.Series();
        casesSeries.setName("Number of Covid Cases");
        
        LinkedHashMap <LocalDate, Integer> testsMap = theCovidDataLoader.loadReportedTests(startDate, endDate); 
        
        LinkedHashMap <LocalDate, Integer> casesMap = theCovidDataLoader.loadDailyCases(startDate, endDate);
        
        // add each date and associated tests and cases value to the graph
        for (Map.Entry <LocalDate, Integer> entry : testsMap.entrySet()) {
            LocalDate date = entry.getKey();
            Integer testsValue = entry.getValue();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = date.format(formatter);
            
            testsSeries.getData().add(new XYChart.Data(dateString, testsValue));
        }
        
        for (Map.Entry <LocalDate, Integer> entry : casesMap.entrySet()) {
            LocalDate date = entry.getKey();
            Integer casesValue = entry.getValue();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = date.format(formatter);
            
            casesSeries.getData().add(new XYChart.Data(dateString, casesValue));
        }
        
        casesAndTests.getData().add(testsSeries);
        casesAndTests.getData().add(casesSeries);
        
        casesAndTests.setCreateSymbols(false);
        
        return casesAndTests;
    }
    
    /**
     * Generates the deaths graph by looping through each entry in the deathsMap
     * from CovidDataLoader and adding it to the chart.
     * 
     * @return the Covid Deaths LineChart
     */
    public LineChart generateDeathsGraph () {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("Date");
        yAxis.setLabel("Number of Deaths");
        
        final LineChart<String,Number> deathsGraph = new LineChart<>(xAxis,yAxis);
        
        deathsGraph.setTitle("Covid Deaths Graph");
        
        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Deaths due to Covid");
        
        LinkedHashMap <LocalDate, Integer> deathsMap = theCovidDataLoader.loadDailyDeaths(startDate, endDate); 
        
        // add each date and associated deaths value to the graph
        for (Map.Entry <LocalDate, Integer> entry : deathsMap.entrySet()) {
            LocalDate date = entry.getKey();
            Integer deathsValue = entry.getValue();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateString = date.format(formatter);
            
            series.getData().add(new XYChart.Data(dateString, deathsValue));
        }
        deathsGraph.getData().add(series);
        
        deathsGraph.setCreateSymbols(false);
        
        return deathsGraph;
    }
}