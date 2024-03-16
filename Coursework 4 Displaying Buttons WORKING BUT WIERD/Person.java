
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

/**
 * Write a description of JavaFX class SecondPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Person
{
    
    //Method one. Each row is a HBOX containing assigned row of hexagons. All contained in a VBXOX - > 95% likely, this method
    //Method two: Each hexagon picture can be a button and a label rendered on top of it. The label text is the one changing color
    
    //private final String MAP_IMAGE_PATH = "borough.png";
    //private ImageView mapImageVIew;
    
    private List<CovidData> covidDataList = new CovidDataLoader().load();
    private String startDate = AppGUI.getInstance().getStartDate();
    private String endDate = AppGUI.getInstance().getEndDate();
    private HashMap<Button, Integer> boroughButtons = new HashMap<Button, Integer>();
    private int averageDeaths;
    
    private HashMap<String,Button> boroughNamesMappings = new HashMap<String,Button>();
    
    
    public Person() {
        System.out.println("Person: " + startDate);
        System.out.println("Person: " + endDate);
    }
    
    
    
}