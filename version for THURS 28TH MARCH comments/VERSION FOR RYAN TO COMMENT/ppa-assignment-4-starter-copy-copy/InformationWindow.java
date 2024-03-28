import javafx.scene.control.cell.PropertyValueFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Comparator;
import java.awt.Label;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The InformationWindow class creates a detailed view of CovidData for a specific London borough, allowing users to visualize and sort data across various metrics such as case counts and mobility reports.
 * This class dynamically generates a TableView to display data, a ComboBox for data sorting, and manages data visualization with sortable columns for different data attributes
 *
 * Key features include:
 * - Display of CovidData Objects in a sortable TableView format.
 * - A ComboBox control that lets users choose the data sorting order based on various attributes such as date, case counts, or mobility metrics.
 * - Automatic table update mechanism to reflect the selected sort order
 * - Flexible data sorting options(Ascending or Descending) to facilitate comparative analysis and insights derivation.
 *
 * @author (your name)
 * @version (a version number or a date)
 */


public class InformationWindow extends Application
{
    private Label information = new Label();
    private ObservableList<CovidData> data;
    private TableView<CovidData> tableView = new TableView<>();
    private List<CovidData> boroughDataRangeData = new ArrayList<>();
    
    private ComboBox<String> orderComboBox;
    private TableColumn<CovidData, String> dateColumn;
    private TableColumn<CovidData, Integer> totalCasesColumn;
    private TableColumn<CovidData, Integer> newCasesColumn;
    private TableColumn<CovidData, Integer> newDeathsColumn;
    private TableColumn<CovidData, Integer> totalDeathsColumn;
    
    
    private TableColumn<CovidData, Integer> retailRecreationGMRColumn;
    private TableColumn<CovidData, Integer> groceryPharmacyGMRColumn;
    private TableColumn<CovidData, Integer> parksGMRColumn;
    private TableColumn<CovidData, Integer> transitGMRColumn;
    private TableColumn<CovidData, Integer> workplacesGMRColumn;
    private TableColumn<CovidData, Integer> residentialGMRColumn;
    
    private HashMap<String,TableColumn> columnMappings;
    private String boroughName;
    
    /**
     * Constructs an InformationWindow instance with a specified list of CovidData for a particular borough.
     * Initializes the ObservableList data with the provided list of CovidData and sets the current borough name.
     *
     * @param boroughDataRangeData The list of {@link CovidData} for the specific borough.
     * @param boroughName The name of the borough for which data is to be displayed.
     */
    public InformationWindow(List<CovidData> boroughDataRangeData,String boroughName){
        data = FXCollections.observableArrayList(boroughDataRangeData);
        this.boroughName = boroughName;
    }
    
    /**
     * Starts and displays the application window with a TableView populated with CovidData for a specific borough.
     * Initializes UI components including a ComboBox for sorting and TableColumns for data display.
     *
     * @param stage The primary stage for this application, onto which the scene containing the TableView will be set.
     */
    @Override
    public void start(Stage stage)
    {
        
        // Initialize the ComboBox with sorting options
        orderComboBox = new ComboBox<>();
        orderComboBox.getItems().addAll("Date", "Total Cases", "New Cases", "New Deaths","Total Deaths","Retail Recreation GMR",
        "Grocery Pharmacy GMR","Parks GMR","Transit GMR","Workplaces GMR","Residential GMR");
        
        // Set up TableColumns with PropertyValueFactory to map each column to a property in CovidData
        /*
         * String put in, replace first letter into its capitalised version 
         * It puts the word 'get' in front of the String. This is the name of the getter method used to populate the method in populate
         */
        dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<CovidData,String>("date"));
        
        
        totalCasesColumn = new TableColumn<>("Total Cases");
        totalCasesColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("totalCases"));
        
        newCasesColumn = new TableColumn<>("New Cases");
        newCasesColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("newCases"));
        
        newDeathsColumn = new TableColumn<>("New Deaths");
        newDeathsColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("newDeaths"));
        
        totalDeathsColumn = new TableColumn<>("Total Deaths");
        totalDeathsColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("totalDeaths"));
                
        
        retailRecreationGMRColumn = new TableColumn<>("Retail Recreation GMR");
        retailRecreationGMRColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("retailRecreationGMR"));
        
        groceryPharmacyGMRColumn = new TableColumn<>("Grocery Pharmacy GMR");
        groceryPharmacyGMRColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("groceryPharmacyGMR"));
        
        parksGMRColumn = new TableColumn<>("Parks GMR");
        parksGMRColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("parksGMR"));
        
        transitGMRColumn = new TableColumn<>("Transit GMR");
        transitGMRColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("transitGMR"));
        
        workplacesGMRColumn = new TableColumn<>("Workplaces GMR");
        workplacesGMRColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("workplacesGMR"));
        
        residentialGMRColumn = new TableColumn<>("Residential GMR");
        residentialGMRColumn.setCellValueFactory(new PropertyValueFactory<CovidData,Integer>("residentialGMR"));
        
        
        
        //Creates a new sorted column(s) by column type
        newCasesColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().add(newCasesColumn);
        
        // Add columns to TableView and set the data
        tableView.getColumns().addAll(dateColumn,totalCasesColumn, newCasesColumn, newDeathsColumn, totalDeathsColumn,retailRecreationGMRColumn, 
        groceryPharmacyGMRColumn, parksGMRColumn, transitGMRColumn, workplacesGMRColumn, residentialGMRColumn);
        tableView.getItems().addAll(data);
    
         // Map ComboBox options to their corresponding TableColumns for sorting
        columnMappings= new HashMap<>(){{
            put("Date",dateColumn);
            put("Total Cases",totalCasesColumn);
            put("New Cases",newCasesColumn);
            put("New Deaths",newDeathsColumn);
            put("Total Deaths",totalDeathsColumn);
            put("Retail Recreation GMR",retailRecreationGMRColumn);
            put("Grocery Pharmacy GMR",groceryPharmacyGMRColumn);
            put("Parks GMR",parksGMRColumn);
            put("Transit GMR",transitGMRColumn);
            put("Workplaces GMR",workplacesGMRColumn);
            put("Residential GMR",residentialGMRColumn);
        }};
    
        
        tableView.sort(); 
        
        //There should not be any empty column in table at the end of tableview.
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        orderComboBox = new ComboBox<>();
        orderComboBox.getItems().addAll("Date", "Total Cases", "New Cases", "New Deaths","Total Deaths","Retail Recreation GMR",
        "Grocery Pharmacy GMR","Parks GMR","Transit GMR","Workplaces GMR","Residential GMR");
    
    
        // Set up ordering based on ComboBox selection
        orderBy();
        
        
        VBox root = new VBox(orderComboBox, tableView);
        Scene scene = new Scene(root, 600, 400);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle(this.boroughName); // Set the window title to the borough name
        primaryStage.show(); // Display the stage
    }

    /**
     * Configures the ComboBox used for ordering the TableView data. Sets an action listener that updates the
     * TableView's sortOrder based on the selected option.
     */
    private void orderBy() {
        orderComboBox.setOnAction(event -> {
            String selectedOption = orderComboBox.getValue(); // Get selected sorting option
            
            tableView.getSortOrder().clear();
            tableView.getSortOrder().add(columnMappings.get(selectedOption)); // Apply new sort order based on selection
            return;
        });
    }
    
    
}
