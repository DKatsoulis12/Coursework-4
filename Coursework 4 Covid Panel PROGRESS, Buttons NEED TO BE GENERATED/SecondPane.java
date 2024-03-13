
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

/**
 * Write a description of JavaFX class SecondPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class SecondPane extends Pane
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
    private void initialiseUI(){
       HBox root = new HBox();
       
       VBox row1 = new VBox();
       VBox row2 = new VBox();
       VBox row3 = new VBox();
       VBox row4 = new VBox();
       VBox row5 = new VBox();
       VBox row6 = new VBox();
       VBox row7 = new VBox();
       
       updatePane();
    }
    
    public void updatePane(){
        int totalBoroughDeaths = getNewDeathsForBorough(covidDataList, startDate, endDate);
        averageDeaths = totalBoroughsAverageDeaths(totalBoroughDeaths, 33);
        buttonColourIterator();
}
    
    private Button addButton(String borough){
        Button btn = new Button(borough);
        //List<CovidData> covidDataList = new CovidDataLoader().load();
        specificBoroughData(covidDataList, borough);
        //int totalDeaths = getNewDeathsForBorough(specificBoroughData,startDate,endDate);
        return btn;
    }
    
    private void changeButtonColour(Button btn, int currentBoroughDeaths){
        if(currentBoroughDeaths <= 0.6 *averageDeaths){
            btn.setStyle("-fx-background-color: #129920;");
        }
        else if(currentBoroughDeaths <= 1.3 *averageDeaths){
            btn.setStyle("-fx-background-color: #f2a31b;");
    }
    else{
        btn.setStyle("-fx-background-color: #960606;");
    }
}
    
    private List<CovidData> specificBoroughData(List<CovidData> covidDataList, String borough){
        List<CovidData> specificBoroughData = new ArrayList<>();
        for (CovidData data: covidDataList) {
            if (data.getBorough().equals(borough)) {
                specificBoroughData.add(data);
            }
        }
        return specificBoroughData;
    }
    
    private void buttonColourIterator(){
        for (Map.Entry<Button, Integer> entry : boroughButtons.entrySet()) {
            Button borough = entry.getKey();
            int totalDeaths = entry.getValue();
            changeButtonColour(borough, totalDeaths);
        }
    }
    
    private void addBoroughBTN() {
        //Load the CSV data
        List<CovidData> covidDataList = new CovidDataLoader().load();
        //loop through the list, and create another list which just holds the boroughs
        //Create a hashset that takes all the unique boroughs
        
        //Loop through the hahset(borough)
            //find the object in dataset
            //int n = boroughDifferenceDeaths(borough);
            //create a new button for each borough - VBOX AND HBOX TO BE FIGURED OUT
            //btn.setText("%(borough)")
            //set x and y value
            //set the color based on n (number of new deaths)
            /*
             * if (n > 50) {
             * 
             *     btn.setTextFill(Color.RED);
               } else if (n > 40) {
                   btn.setTextFill(Color.ORANGE);
               } else if (n> 30) {
                   btn.setTextFill(Color.LIGHTGREEN);
               } else{
                   btn.setTextFill(Color.DEEPGREEN);
               }
            btn.setFont(Font.font("Calbri",FontWeight.BOLD,12));   
            
             */
        
    }
    
    private int totalBoroughsAverageDeaths(int totalDeathsTogether, int boroughNum) {
        return totalDeathsTogether/boroughNum;
    }
    
    /*private int boroughDifferenceDeaths(String borough){
        //get the startDate 
        //get the endDate 
        //int newDeaths = getNewDeathsForBorough(borough,covidDataList);
        int finalTotal = 0;
        return finalTotal; //change
    }*/
    
    private int getNewDeathsForBorough(List<CovidData> covidList, String startDate, String endDate) {
        boolean flag1 = false;
        boolean flag2 = false;
        int initialdeaths = 0;
        int finaldeaths = 0;
        
        for (CovidData data: covidList) {
            if (data.getDate().equals(startDate)) {
                flag1 = true;
                initialdeaths += data.getTotalDeaths();
            } else if (data.getDate().equals(endDate)) {
                flag2 = true;
                finaldeaths += data.getTotalDeaths();
            }
            if (flag1 && flag2) {
                return (finaldeaths - initialdeaths);
            }
        }
        return 0;
    }
    
}