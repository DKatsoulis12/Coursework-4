import javafx.event.ActionEvent;

/**
 * interface used to group all panes used in the application and force them have the updatePane() method so {@code AppGUI} can call it when needed
 */
public interface AppPane
{
    /**
     * called to update the pane, panes that do not require updating will implement it with nothing in it.
     */
    public abstract void updatePane();
    
}
