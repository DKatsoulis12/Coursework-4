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
        Label instructionsLabel = new Label("The available date range is 2/3/2020 to 2/9/2023");
        instructionsLabel.setLayoutY(welcomeLabel.getLayoutY() + welcomeLabel.getHeight() + 10);
        
        getChildren().add(instructionsLabel);
    }
    
    public void updatePane(){
    }
}
