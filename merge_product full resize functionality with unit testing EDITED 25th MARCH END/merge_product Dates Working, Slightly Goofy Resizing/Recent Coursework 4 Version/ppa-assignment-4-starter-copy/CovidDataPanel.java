
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
import java.util.Arrays;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        btn.getStyleClass().add("button");
        btn.setOnMouseEntered(e -> {
        // Directly setting the hover style
        btn.setStyle("-fx-background-color: darkgray; -fx-border-color: black; -fx-border-width: 1;");
        
    });
    
    // Handle mouse exiting the button area
    btn.setOnMouseExited(e -> {
        // Reverting to the base style
        changeButtonColour(btn,boroughButtons.get(btn));
        //buttonColourIterator();
    });
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
        
        //System.out.println(averageDeaths + " is the average");
        //System.out.println(currentBoroughDeaths + " is the current borugh deaths");
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
        int totalFinalDeaths = 0;
        
        //LocalDate startDateObj = LocalDate.parse(startDate);
        //LocalDate endDateObj = LocalDate.parse(endDate);
        
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //LocalDate startDateObj = LocalDate.parse(startDate, formatter);
        //LocalDate endDateObj = LocalDate.parse(endDate, formatter);

        //boolean isWithinRange = false;
        
        for (CovidData data: covidList) {
            /*LocalDate dataDateObj = LocalDate.parse(data.getDate());
        
            // Check if the current date is after or equal to the start date and before or equal to the end date
            if ((dataDateObj.isAfter(startDateObj) || dataDateObj.isEqual(startDateObj)) && 
                (dataDateObj.isBefore(endDateObj) || dataDateObj.isEqual(endDateObj))) {
                isWithinRange = true;
                System.out.println(data.toString());
                totalFinalDeaths += data.getNewDeaths();
            }
            else if (dataDateObj.isAfter(endDateObj)) {
                // If the current data's date is after the end date, stop processing (assuming the list is sorted by date)
                break;
            }*/
            
            if (data.getDate().equals(startDate)) {
                flag1 = true;
            } else if (data.getDate().equals(endDate)) {
                flag2 = true;
            }
            if (flag1) {
                //System.out.println(data.toString());
                totalFinalDeaths += data.getNewDeaths();
            }
            if (flag1 && flag2) {
                if (startDate.equals(endDate)) {
                    return totalFinalDeaths/2;
                }
                return (totalFinalDeaths);
            } 
        }

        // Return the total deaths if within the date range; otherwise, return 0 or some other appropriate value
        //return isWithinRange ? totalFinalDeaths : 0;
        
        return totalFinalDeaths;
        /*for (CovidData data: covidList) {
            LocalDate dataDate = LocalDate.parse(data.getDate());
            if (data.getDate().equals(startDate)) {
                flag1 = true;
            } else if (data.getDate().equals(endDate)) {
                flag2 = true;
            }
            if (flag1) {
                System.out.println(data.toString());
                totalFinalDeaths += data.getNewDeaths();
            }
            if (flag1 && flag2) {
                if (startDate.equals(endDate)) {
                    return totalFinalDeaths/2;
                }
                return (totalFinalDeaths);
            } 
        }*/
        
        
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
        
        
        
        //return totalFinalDeaths;
        //return 0;
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
        //newScene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }
    
    public void setButtonResize(){
        //allows certain buttons to take up as much space as possible horizontally, only allowed for the buttons in the row with most buttons
        //List<Button> buttonsSpaceHorizontal = new ArrayList<>();
        List<String> buttonsSpaceHorizontal = Arrays.asList("Westminster","Hillingdon","Ealing","Kensington And Chelsea","Tower Hamlets","Newham",
        "Barking And Dagenham");
        
        for (String buttonString : buttonsSpaceHorizontal) {
            HBox.setHgrow(boroughNamesMappings.get(buttonString), javafx.scene.layout.Priority.ALWAYS);
        }
    
         /*
         * binds all other buttons' wdith to one of the above buttons, so they change horizontal length to a certain extent instead of taking
         * up all horzitontal space which would take up the whole pane. each borough is matched with the above buttons that has the most similar
         * length which is why they are set seperately and looped through and set under a certain condition
         */
        
        Map<String, List<String>> bindings = new HashMap<>();
        bindings.put("Westminster", Arrays.asList("Lambeth", "Southwark", "Lewisham", "Hounslow", "Enfield", "Redbridge", "Greenwich"));
        bindings.put("Newham", Arrays.asList("Barnet", "Harrow", "Camden", "Merton", "Bexley", "Sutton"));
        bindings.put("Hillingdon", Arrays.asList("Haringey", "Islington", "Hackney", "Havering", "Croydon", "Bromley"));
        bindings.put("Kensington And Chelsea", Arrays.asList("Richmond Upon Thames", "Kingston Upon Thames", "City Of London"));
        bindings.put("Barking And Dagenham", Arrays.asList("Hammersmith And Fulham", "Waltham Forest"));
        bindings.put("Tower Hamlets", Arrays.asList("Wandsworth"));
        bindings.put("Ealing", Arrays.asList("Brent"));
    
        // Iterate through the map and bind the width properties
        for (Map.Entry<String, List<String>> entry : bindings.entrySet()) {
            Button referenceButton = boroughNamesMappings.get(entry.getKey());
            for (String boroughName : entry.getValue()) {
                Button boroughButton = boroughNamesMappings.get(boroughName);
                if (boroughButton != null && referenceButton != null) {
                    boroughButton.prefWidthProperty().bind(referenceButton.widthProperty());
                }
            }
        }
    }
}