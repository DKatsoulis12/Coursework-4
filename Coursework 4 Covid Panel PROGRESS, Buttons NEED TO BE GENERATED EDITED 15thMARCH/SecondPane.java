
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
    
    
    private void initialiseUI(){
       HBox root = new HBox();
       
       VBox row1 = new VBox();
       VBox row2 = new VBox();
       VBox row3 = new VBox();
       VBox row4 = new VBox();
       VBox row5 = new VBox();
       VBox row6 = new VBox();
       VBox row7 = new VBox();
       row1.getChildren().addAll(boroughNamesMappings.get("Enfield"));
       
       row2.getChildren().addAll(boroughNamesMappings.get("Barnet"),boroughNamesMappings.get("Haringey"),boroughNamesMappings.get("Waltham Forest"));
       
       row3.getChildren().addAll(addButton("HRRW"),addButton("BREN"),addButton("CAMD"),addButton("ISLI"),
       addButton("HACK"),addButton("REDB"),addButton("HAVE"));
       
       row4.getChildren().addAll(addButton("HILL"),addButton("EALI"),addButton("KENS"),addButton("WSTM"),addButton("TOWH")
       ,addButton("NEWH"),addButton("BARK"));
       
       row5.getChildren().addAll(addButton("HOUN"),addButton("HAMM"),addButton("WAND"),addButton("CITY"),
       addButton("GWCH"),addButton("BEXL"));
       
       row6.getChildren().addAll(addButton("RICH"),addButton("MERT"),addButton("LAMB"),addButton("STHW"),addButton("LEWS"));
       
       row7.getChildren().addAll(addButton("KING"),addButton("SUTT"),addButton("CROY"),addButton("BROM"));
       
       root.getChildren().addAll(row1,row2,row3,row4,row5,row6,row7);
       
       
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
        List<CovidData> boroughData = new ArrayList<>();
        for (CovidData data: covidDataList) {
            if (data.getBorough().equals(borough)) {
                boroughData.add(data);
            }
        }
        return boroughData;
    }
    
    private void buttonColourIterator(){
        for (Map.Entry<Button, Integer> entry : boroughButtons.entrySet()) {
            Button borough = entry.getKey();
            int totalDeaths = entry.getValue();
            changeButtonColour(borough, totalDeaths);
        }
    }
    
    private int totalBoroughsAverageDeaths(int totalDeathsTogether, int boroughNum) {
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
    
    private void addBoroughButtons() {
        getUniqueBoroughs();
        for (String borough : boroughNamesMappings.keySet()) {
            Button button = boroughNamesMappings.get(borough);
            boroughButtons.put(button, getNewDeathsForBorough(specificBoroughData(covidDataList,borough), startDate, endDate));
        }
        
        /*for (String borough : getUniqueBoroughs()) {
            Button button = boroughNamesMappings.get(borough);
            boroughButtons.put(button, getNewDeathsForBorough(specificBoroughData(covidDataList,borough), startDate, endDate));
        }*/
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