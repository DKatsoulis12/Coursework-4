
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
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.collections.ObservableList;
import javafx.scene.layout.Priority;

/**
 * Write a description of JavaFX class SecondPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CovidDataPanel extends Pane implements AppPane
{
    
    //Method one. Each row is a HBOX containing assigned row of hexagons. All contained in a VBXOX - > 95% likely, this method
    //Method two: Each hexagon picture can be a button and a label rendered on top of it. The label text is the one changing color
    
    //private final String MAP_IMAGE_PATH = "borough.png";
    //private ImageView mapImageVIew;
    
    private List<CovidData> covidDataList = CovidDataLoader.getInstance().load(); //new CovidDataLoader().load();
    private String startDate;// = AppGUI.getInstance().getStartDate();
    private String endDate;// = AppGUI.getInstance().getEndDate();
    private HashMap<Button, Integer> boroughButtons = new HashMap<Button, Integer>();
    private int averageDeaths;
    private HashMap<String,Button> boroughNamesMappings = new HashMap<String,Button>();
    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
    
    /*
    public CovidDataPanel(Pane anchor) {
        System.out.println("E");
        initialiseUI();
        anchor.getChildren().add(this);
    }*/
    
    
    public CovidDataPanel() {
        //System.out.println("E");
        initialiseUI();
        //anchor.getChildren().add(this);
    }
    
    public void initialiseUI(){
       VBox root = new VBox();
       root.setAlignment(Pos.CENTER);       
       
       
       HBox row1 = new HBox();
       
       HBox row2 = new HBox();
       HBox row3 = new HBox();
       HBox row4 = new HBox();
       HBox row5 = new HBox();
       HBox row6 = new HBox();
       HBox row7 = new HBox();
             
       //Button updateButton = new Button("Update");
       
       row1.setAlignment(Pos.CENTER);
       row2.setAlignment(Pos.CENTER);
       row3.setAlignment(Pos.CENTER);
       row4.setAlignment(Pos.CENTER);
       row5.setAlignment(Pos.CENTER);
       row6.setAlignment(Pos.CENTER);
       row7.setAlignment(Pos.CENTER);
       
       getUniqueBoroughs();
       addBoroughButtons();
       createInformationWindow();
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
       
       VBox.setVgrow(row1, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row2, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row3, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row4, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row5, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row6, javafx.scene.layout.Priority.ALWAYS);
       VBox.setVgrow(row7, javafx.scene.layout.Priority.ALWAYS);
       
       
       root.getChildren().addAll(row1);

       root.getChildren().addAll(row2);
       
       root.getChildren().addAll(row3);
       
       root.getChildren().addAll(row4);
       
       root.getChildren().addAll(row5);
       
       root.getChildren().addAll(row6);
       
       root.getChildren().addAll(row7);
       
       setButtonResize();
        
       this.getChildren().add(root);
       
        root.layoutXProperty().bind(this.widthProperty().divide(2).subtract(root.widthProperty().divide(2)));
        root.layoutYProperty().bind(this.heightProperty().divide(2).subtract(root.heightProperty().divide(2)));
        
        root.prefWidthProperty().bind(this.widthProperty());
        root.prefHeightProperty().bind(this.heightProperty());
        
       //updateButton.setOnAction(event -> updatePane());
      
       //this.getChildren().add(updateButton);
       //updatePane();
       //calculateAverageDeaths();
       //buttonColourIterator();
       
    }
    
    @Override
    public void updatePane(){
        //if(DateChecker.getInstance().checkButtonDisability2()){
         //   return;
        //}
        startDate = AppGUI.getInstance().getStartDate().toString();
        endDate = AppGUI.getInstance().getEndDate().toString();
        calculateAverageDeaths();
        addBoroughButtons();
        buttonColourIterator();
    }
    
    /*private void clickUpdate(ActionEvent event) {
        boolean f = AppGUI.getInstance().toggleButtonDisability();

        if (f == false) {
            updatePane(event);
        } else {
            AppGUI.getInstance().alertSetUp();
        }
    }*/
    
    private void calculateAverageDeaths(){
        int totalBoroughDeaths = getNewDeathsForBorough(covidDataList, startDate, endDate);
        averageDeaths = totalBoroughsAverageDeaths(totalBoroughDeaths, 33);
    }
    
    private Button addButton(String borough){
        Button btn = new Button(borough);
        //List<CovidData> covidDataList = new CovidDataLoader().load();
        List<CovidData> boroughSpecificData = specificBoroughData(covidDataList, borough);
        btn.setOnAction(e -> handleButtonClick(btn,boroughSpecificData,borough));
        return btn;
    }
    
    private void handleButtonClick(Button btn, List<CovidData> boroughListData,String borough) {
        //System.out.println("Button clicked: " + btn.getText());
        
        InformationWindow infoWindow = new InformationWindow(boroughListData,borough);
        infoWindow.start(new Stage());
    }
    
    //private void updateDate(ActionEvent event) {
      //  this.startDate = AppGUI.getInstance().getStartDate().toString();
      //  this.endDate = AppGUI.getInstance().getEndDate().toString();
   // }
    
    private void changeButtonColour(Button btn, int currentBoroughDeaths){
        
        
        if(currentBoroughDeaths <= 0.9 *averageDeaths){
            btn.setStyle("-fx-background-color: #129920;");
        }
        else if(currentBoroughDeaths <= 1.4 *averageDeaths){
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
        //System.out.println("Average Deaths: " + averageDeaths);
        for (Map.Entry<Button, Integer> entry : boroughButtons.entrySet()) {
            Button borough = entry.getKey();
            int totalDeaths = entry.getValue();
            //System.out.println(borough + "deaths: " + totalDeaths);
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
        //int initialdeaths = 0;
        //int finaldeaths = 0;
        int totalFinalDeaths = 0;
        
        for (CovidData data: covidList) {
            if (data.getDate().equals(startDate)) {
                flag1 = true;
            } else if (data.getDate().equals(endDate)) {
                flag2 = true;
            }
            if (flag1) {
                totalFinalDeaths += data.getNewDeaths();
            }
            if (flag1 && flag2) {
                return (totalFinalDeaths);
            } 
        }
        
        
        /*for(CovidData data: covidList){
            if(!flag1){
               if (data.getDate().equals(startDate)){
                   flag1 = true;
                   totalFinalDeaths += data.getNewDeaths();
               } 
            }
            else if(flag1 && !flag2){
                if (data.getDate().equals(endDate)){
                    flag2 = true; 
                    totalFinalDeaths += data.getNewDeaths();
                }
                else{
                   totalFinalDeaths += data.getNewDeaths();
                }
            }
            
            else if(flag1 && flag2){
                return totalFinalDeaths;
            }
        }*/
        
        
        return 0;
    }
    
    private void addBoroughButtons() {
        
        for (String borough : boroughNamesMappings.keySet()) {
            Button button = boroughNamesMappings.get(borough);
            button.setMinHeight(Button.USE_PREF_SIZE);
            button.setMaxHeight(Double.MAX_VALUE);
            button.setMinWidth(Button.USE_PREF_SIZE);
            button.setMaxWidth(Double.MAX_VALUE);
             
            
            boroughButtons.put(button, getNewDeathsForBorough(specificBoroughData(covidDataList,borough), startDate, endDate));
        }
        
        
    }
    
    private List<CovidData> generateStatistics(String borough) {
        List<CovidData> boroughDataList = specificBoroughData(covidDataList,borough);
        List<CovidData> boroughDataRangeData = new ArrayList<>();
        boolean flag1 = false;
        boolean flag2 = false;
        for(CovidData data: boroughDataList){
            if(!flag1){
               if (data.getDate().equals(startDate)){
                flag1 = true;
                boroughDataRangeData.add(data);
            } 
            }
            else if(flag1 && !flag2){
                if (data.getDate().equals(endDate)){
                    flag2 = true; 
                    boroughDataRangeData.add(data);
                }
                else{
                   boroughDataRangeData.add(data);
                }
            }
            else if(flag1 && flag2){
                break;
            }
        }
        return boroughDataRangeData;
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
    
    private void createInformationWindow() {
        Stage newStage = new Stage();
        StackPane newRoot = new StackPane();
        Scene newScene = new Scene(newRoot, 200, 150);
    }
    
    public void setButtonResize(){
        //allows certain buttons to take up as much space as possible horizontally, only allowed for the buttons in the row with most buttons
        HBox.setHgrow(boroughNamesMappings.get("Westminster"), javafx.scene.layout.Priority.ALWAYS); 
        HBox.setHgrow(boroughNamesMappings.get("Hillingdon"), javafx.scene.layout.Priority.ALWAYS); 
        HBox.setHgrow(boroughNamesMappings.get("Ealing"), javafx.scene.layout.Priority.ALWAYS); 
        HBox.setHgrow(boroughNamesMappings.get("Kensington And Chelsea"), javafx.scene.layout.Priority.ALWAYS); 
        HBox.setHgrow(boroughNamesMappings.get("Tower Hamlets"), javafx.scene.layout.Priority.ALWAYS); 
        HBox.setHgrow(boroughNamesMappings.get("Newham"), javafx.scene.layout.Priority.ALWAYS);
        HBox.setHgrow(boroughNamesMappings.get("Barking And Dagenham"), javafx.scene.layout.Priority.ALWAYS); 
        
        /*
         * binds all other buttons' wdith to one of the above buttons, so they change horizontal length to a certain extent instead of taking
         * up all horzitontal space which would take up the whole pane. each borough is matched with the above buttons that has the most similar
         * length which is why they are set seperately and looped through and set under a certain condition
         */
        boroughNamesMappings.get("Enfield").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Barnet").prefWidthProperty().bind(boroughNamesMappings.get("Newham").widthProperty());
        boroughNamesMappings.get("Haringey").prefWidthProperty().bind(boroughNamesMappings.get("Hillingdon").widthProperty());
        boroughNamesMappings.get("Waltham Forest").prefWidthProperty().bind(boroughNamesMappings.get("Barking And Dagenham").widthProperty());
        boroughNamesMappings.get("Harrow").prefWidthProperty().bind(boroughNamesMappings.get("Newham").widthProperty());
        boroughNamesMappings.get("Camden").prefWidthProperty().bind(boroughNamesMappings.get("Newham").widthProperty());
        boroughNamesMappings.get("Brent").prefWidthProperty().bind(boroughNamesMappings.get("Ealing").widthProperty());
        boroughNamesMappings.get("Islington").prefWidthProperty().bind(boroughNamesMappings.get("Hillingdon").widthProperty());
        boroughNamesMappings.get("Hackney").prefWidthProperty().bind(boroughNamesMappings.get("Hillingdon").widthProperty());
        boroughNamesMappings.get("Redbridge").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Havering").prefWidthProperty().bind(boroughNamesMappings.get("Hillingdon").widthProperty());
        boroughNamesMappings.get("Richmond Upon Thames").prefWidthProperty().bind(boroughNamesMappings.get("Kensington And Chelsea").widthProperty());
        boroughNamesMappings.get("Kingston Upon Thames").prefWidthProperty().bind(boroughNamesMappings.get("Kensington And Chelsea").widthProperty());
        boroughNamesMappings.get("Sutton").prefWidthProperty().bind(boroughNamesMappings.get("Newham").widthProperty());
        boroughNamesMappings.get("Croydon").prefWidthProperty().bind(boroughNamesMappings.get("Hillingdon").widthProperty());
        boroughNamesMappings.get("Bromley").prefWidthProperty().bind(boroughNamesMappings.get("Hillingdon").widthProperty());
        boroughNamesMappings.get("Merton").prefWidthProperty().bind(boroughNamesMappings.get("Newham").widthProperty());
        boroughNamesMappings.get("Lambeth").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Southwark").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Lewisham").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Hounslow").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Hammersmith And Fulham").prefWidthProperty().bind(boroughNamesMappings.get("Barking And Dagenham").widthProperty());
        boroughNamesMappings.get("Wandsworth").prefWidthProperty().bind(boroughNamesMappings.get("Tower Hamlets").widthProperty());
        boroughNamesMappings.get("City Of London").prefWidthProperty().bind(boroughNamesMappings.get("Kensington And Chelsea").widthProperty());
        boroughNamesMappings.get("Greenwich").prefWidthProperty().bind(boroughNamesMappings.get("Westminster").widthProperty());
        boroughNamesMappings.get("Bexley").prefWidthProperty().bind(boroughNamesMappings.get("Newham").widthProperty());
    }
}