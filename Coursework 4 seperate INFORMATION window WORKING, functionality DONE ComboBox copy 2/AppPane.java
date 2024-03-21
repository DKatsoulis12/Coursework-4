import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;

/**
 * Write a description of class AppPane here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public abstract class AppPane extends Pane
{
    // instance variables - replace the example below with your own
    
    
    /**
     * Constructor for objects of class AppPane
     */
    public AppPane()
    {
        // initialise instance variables
       
    }

    public abstract void updatePane(ActionEvent event);
}
