
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
public class CovidDataPanel extends Pane
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
    /*private HashMap<String,Button> boroughNamesMappings = new HashMap<String,Button>(){{
        put("Enfield",addButton("ENFI")); 
        
        put("Barnet",addButton("BARN")); put("Haringey",addButton("HRGY")); put("Waltham Forest",addButton("WALT"));
        
        put("Harrow",addButton("HRRW")); put("Brent",addButton("BREN")); put("Camden",addButton("CAMD")); put("Islington",addButton("ISLI"));
        put("Hackney",addButton("HACK")); put("Redbridge",addButton("REDB")); put("Havering",addButton("HAVE")); 
        
        put("Hillingdon",addButton("HILL")); put("Ealing",addButton("EALI")); put("Kensington And Chelsea",addButton("KENS")); put("Westminster",addButton("WSTM")); 
        put("Tower Hamlets",addButton("TOWH")); put("Newham",addButton("NEWH")); put("Barking And Dagenham",addButton("BARK")); 
        
        put("Hounslow",addButton("HOUN")); put("Hammersmith And Fulham",addButton("HAMM")); put("Wandsworth",addButton("WAND")); put("City Of London",addButton("CITY")); 
        put("Greenwich",addButton("GWCH")); put("Bexley",addButton("BEXL"));
        
        put("Richmond Upon Thames",addButton("RICH")); put("Merton",addButton("MERT")); put("Lambeth",addButton("LAMB")); 
        put("Southwark",addButton("STHW")); put("Lewisham",addButton("LEWS"));
        
        put("Kingston Upon Thames",addButton("KING")); put("Sutton",addButton("SUTT")); put("Croydon",addButton("CROY")); 
        put("Bromley",addButton("BROM"));
    }};*/
    
    
    public CovidDataPanel(Pane anchor) {
        System.out.println("E");
        initialiseUI();
        anchor.getChildren().add(this);
    }
    
    public void initialiseUI(){
       VBox root = new VBox();
       
       HBox row1 = new HBox();
       HBox row2 = new HBox();
       HBox row3 = new HBox();
       HBox row4 = new HBox();
       HBox row5 = new HBox();
       HBox row6 = new HBox();
       HBox row7 = new HBox();
       
       getUniqueBoroughs();
       addBoroughButtons();
       
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
       
       root.getChildren().addAll(row1);
       root.getChildren().addAll(row2);
       root.getChildren().addAll(row3);
       root.getChildren().addAll(row4);
       root.getChildren().addAll(row5);
       root.getChildren().addAll(row6);
       root.getChildren().addAll(row7);
       
       this.getChildren().add(root);
       
       calculateAverageDeaths();
       buttonColourIterator();
       
    }
    
    public void updatePane(){
        calculateAverageDeaths();
        addBoroughButtons();
        buttonColourIterator();
    }
    
    private void calculateAverageDeaths(){
        int totalBoroughDeaths = getNewDeathsForBorough(covidDataList, startDate, endDate);
        averageDeaths = totalBoroughsAverageDeaths(totalBoroughDeaths, 33);
    }
    
    private Button addButton(String borough){
        Button btn = new Button(borough);
        //List<CovidData> covidDataList = new CovidDataLoader().load();
        specificBoroughData(covidDataList, borough);
        
        return btn;
    }
    
    private void changeButtonColour(Button btn, int currentBoroughDeaths){
        
        
        if(currentBoroughDeaths <= 0.6 *averageDeaths){
            btn.setStyle("-fx-background-color: #129920;");
        }
        else if(currentBoroughDeaths <= 1.2 *averageDeaths){
            btn.setStyle("-fx-background-color: #f2a31b;");
        }
        else{
            btn.setStyle("-fx-background-color: #960606;");
        }
    }
    
    private List<CovidData> specificBoroughData(List<CovidData> covidDataList, String borough){
        List<CovidData> boroughData = new ArrayList<>();
        for (CovidData data: covidDataList) {
            if (data.getBorough().equals(borough)) {
                boroughData.add(data);
            }
        }
        return boroughData;
    }
    
    private void buttonColourIterator(){
        System.out.println("Average Deaths: " + averageDeaths);
        for (Map.Entry<Button, Integer> entry : boroughButtons.entrySet()) {
            Button borough = entry.getKey();
            int totalDeaths = entry.getValue();
            System.out.println(borough + "deaths: " + totalDeaths);
            changeButtonColour(borough, totalDeaths);
        }
    }
    
    private int totalBoroughsAverageDeaths(int totalDeathsTogether, int boroughNum) {
        //return totalDeathsTogether;
        return totalDeathsTogether/boroughNum;
    }
    
    private int getNewDeathsForBorough(List<CovidData> covidList, String startDate, String endDate) {
        boolean flag1 = false;
        boolean flag2 = false;
        int initialdeaths = 0;
        int finaldeaths = 0;
        
        for (CovidData data: covidList) {
            if (data.getDate().equals(startDate)) {
                flag1 = true;
                initialdeaths -= data.getTotalDeaths();
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
    
    private void addBoroughButtons() {
        
        for (String borough : boroughNamesMappings.keySet()) {
            Button button = boroughNamesMappings.get(borough);
            boroughButtons.put(button, getNewDeathsForBorough(specificBoroughData(covidDataList,borough), startDate, endDate));
        }
        
        
    }
    
    public void generateStatistics() {
        
    }
    
    
    
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
    
}