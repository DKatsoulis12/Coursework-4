
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
    
    public InformationWindow(List<CovidData> boroughDataRangeData){
        this.data = boroughDataRangeData;
    }
    
    @Override
    public void start(Stage stage)
    {
        
    }

}
