import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
/**
 * Write a description of class FirstPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FirstPane extends Pane implements AppPane
{
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
    }
    
    public void updatePane(){
    }
}
