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
 * Write a description of JavaFX class InformationWindow here.
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
    
    public InformationWindow(List<CovidData> boroughDataRangeData,String boroughName){
        data = FXCollections.observableArrayList(boroughDataRangeData);
        this.boroughName = boroughName;
    }
    
    @Override
    public void start(Stage stage)
    {
        
        orderComboBox = new ComboBox<>();
        orderComboBox.getItems().addAll("Date", "Total Cases", "New Cases", "New Deaths","Total Deaths","Retail Recreation GMR",
        "Grocery Pharmacy GMR","Parks GMR","Transit GMR","Workplaces GMR","Residential GMR");
        
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
        
        //newCasesColumn.setSortType(TableColumn.SortType.ASCENDING);
        //tableView.getSortOrder().add(newCasesColumn);
        
        
        newCasesColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableView.getSortOrder().add(newCasesColumn);
        
        tableView.getColumns().addAll(dateColumn,totalCasesColumn, newCasesColumn, newDeathsColumn, totalDeathsColumn,retailRecreationGMRColumn, 
        groceryPharmacyGMRColumn, parksGMRColumn, transitGMRColumn, workplacesGMRColumn, residentialGMRColumn);
        //tableView.getColumns().addAll(totalCasesColumn,newDeathsColumn, retailRecreationGMRColumn);
        tableView.getItems().addAll(data);
    
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
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        
        orderComboBox = new ComboBox<>();
        orderComboBox.getItems().addAll("Date", "Total Cases", "New Cases", "New Deaths","Total Deaths","Retail Recreation GMR",
        "Grocery Pharmacy GMR","Parks GMR","Transit GMR","Workplaces GMR","Residential GMR");
    
    
        orderBy();
        
        
        
        
        VBox root = new VBox(orderComboBox, tableView);
        Scene scene = new Scene(root, 600, 400);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle(this.boroughName);
        primaryStage.show();
    }

    private void orderBy() {
        orderComboBox.setOnAction(event -> {
            String selectedOption = orderComboBox.getValue();
            
            tableView.getSortOrder().clear();
            tableView.getSortOrder().add(columnMappings.get(selectedOption));
            return;
        });
    }
    
    
}
