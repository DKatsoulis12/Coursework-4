import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import java.time.LocalDate;

/**
 * Represents the introductory pane in a JavaFX application, displaying initial instructions and the functionality of subsequent panes. 
 * It guides users through the application's date range and the specific insights available in each pane related to COVID-19 data visualization.
 * Upon initialization, it sets up labels to introduce the application, detail the navigational aspects, and describe the data visualization 
 * each pane offers, such as maps of boroughs, statistics, and graphs for COVID-19 metrics. Additionally, it includes dynamic labels to 
 * display the selected start and end dates, updating these as the user interacts with the application.
 */
public class FirstPane extends Pane implements AppPane
{
    private Label startDateLabel;
    private Label endDateLabel;
    /**
     * Initializes the pane with instructional content and dynamic date labels.
     * Sets up the layout and content of the welcome, instructions, pane descriptions, and navigation labels.
     * Also initializes labels to display the currently selected start and end dates.
     */
    public FirstPane()
    {
        Label welcomeLabel = new Label("Welcome. Here are the instructions below: ");
        getChildren().add(welcomeLabel);
        
        Label instructionsLabel = new Label("The available date range is 2/3/2020 to 2/9/2023. For for the date period selected, each pane does the following:");
        instructionsLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 15);
        getChildren().add(instructionsLabel);
        
        Label boroughLabel = new Label("The second pane allows you to see a map of the boroughs of London and a visual comparison of their total deaths compared to the average deaths");
        boroughLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 40);
        getChildren().add(boroughLabel);
        
        Label boroughLabelContinued = new Label("Green means below average deaths, orange means close to average deaths, and red above average deaths. Click on each borough to see all info for that borough");
        boroughLabelContinued.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 55);
        getChildren().add(boroughLabelContinued);
        
        Label thirdPaneLabel = new Label("For the third panel, you can view statistics for all of London for the time period selected.");
        thirdPaneLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 80);
        getChildren().add(thirdPaneLabel);
        
        Label thirdPaneLabelContinued = new Label("You can view the total deaths, average cases per day, average retail and recreation percentage change and average transit stations percentage change.");
        thirdPaneLabelContinued.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 95);
        getChildren().add(thirdPaneLabelContinued);
        
        Label fourthPaneLabel = new Label("For the fourth panel, you can view graphs about the reported tests and cases, Covid deaths and Covid cases over time.");
        fourthPaneLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 120);
        getChildren().add(fourthPaneLabel);
        
        Label navigationLabel = new Label("Navigate to different panes using the bottom right and bottom left arrow buttons. To work with data for a new data range, change the date and then click the 'Update' button at the bottom.");
        navigationLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 170);
        getChildren().add(navigationLabel);

        
        
        startDateLabel = new Label("Start Date: ");
        startDateLabel.setLayoutY(navigationLabel.getLayoutY() + navigationLabel.getHeight() + 20); // Adjust Y layout based on your UI
        getChildren().add(startDateLabel);
        
        endDateLabel = new Label("End Date: ");
        endDateLabel.setLayoutY(startDateLabel.getLayoutY() + startDateLabel.getHeight() + 20); // Adjust Y layout based on your UI
        getChildren().add(endDateLabel);
        
    }
    
    /**
     * Updates the label to display the currently selected start date from the application's date picker.
     * This method is intended to be called whenever the start date in the application changes.
     */
    public void updateStartDate() {
        startDateLabel.setText("Start Date: " + AppGUI.getInstance().getStartDate().toString());
    }
    
    /**
     * Updates the label to display the currently selected end date from the application's date picker.
     * This method is intended to be called whenever the end date in the application changes.
     */
    public void updateEndDate() {
        endDateLabel.setText("End Date: " + AppGUI.getInstance().getEndDate().toString());
    }
    
    /**
     * overrided from AppPane interface, but unused
     */
    public void updatePane(){
        //System.out.println(AppGUI.getInstance().getStartDate().toString());
        //System.out.println(AppGUI.getInstance().getEndDate().toString());
    }
}