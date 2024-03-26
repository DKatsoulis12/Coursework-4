import java.util.ArrayList;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Write a description of class dateChecker here.
 * Ryan does description 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class DateChecker
{
    //instance variables - yours
    private static DateChecker uniqueInstance;
    private ArrayList<String> UniqueDates;
    private String alertTxt;
    
    //yours (MINE - I STILL DID IT ANYWAY, EDIT AS YOU NEED);
    /**
     * Gets the single instance of the {@code DateChecker} class, creating it if it doesn't exist.
     * This method implements the Singleton design pattern to ensure that only one instance of {@code DateChecker} is used.
     * 
     * @return The single instance of {@code DateChecker}.
     */
    public static DateChecker getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DateChecker();
        }
        return uniqueInstance;
    }
 
    
    //yours
    /**
     * Constructor for objects of class dateChecker
     */
    public DateChecker()
    {
        
        UniqueDates = CovidDataLoader.getInstance().loadUniqueDates();
        //System.out.println("4");
    }

    //mine
    /**
     * Checks if the button should be disabled based on the validity of the start and end dates provided by the user.
     * The button is disabled if the start date is after the end date, or if the dates are not within the range of available Covid data.
     * 
     * @param startDate The start date selected by the user.
     * @param endDate The end date selected by the user.
     * @return {@code true} if the button should be disabled due to invalid date selection; {@code false} otherwise.
     */
    public boolean checkButtonDisability(LocalDate startDate, LocalDate endDate)
    {
        
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        boolean flag1 =  startDate.isAfter(endDate);
        boolean flag2 = !(UniqueDates.contains(startDateString) && UniqueDates.contains(endDateString));
        if (flag1 || flag2) {
            setAlertText(flag1, flag2);
            alertSetUp();
            return true;
        } else {
            return false;
        }
    }
    
    //mine
    /**
     * Checks if the button should be disabled based on the validity of the start and end dates obtained from the {@code AppGUI} instance.
     * Similar to {@code checkButtonDisability}, but retrieves the dates directly from the {@code AppGUI} instance.
     * 
     * @return {@code true} if the button should be disabled due to invalid date selection; {@code false} otherwise.
     */
    public boolean checkButtonDisability2()
    {
        LocalDate startDate = AppGUI.getInstance().getStartDate();
        LocalDate endDate = AppGUI.getInstance().getEndDate();
        String startDateString = startDate.toString();
        String endDateString = endDate.toString();
        
        
        boolean flag1 =  startDate.isAfter(endDate);
        
        boolean flag2 = !(UniqueDates.contains(startDateString) && UniqueDates.contains(endDateString));
        if (flag1 || flag2) {
            setAlertText(flag1, flag2);
            alertSetUp();
            return true;
        } else {
            return false;
        }
    }
    
    //mine
    /**
     * Sets the alert text based on the reasons for date selection invalidity.
     * If the start date is after the end date, a specific message is set. If the date range is not within the available data, another message is set.
     * 
     * @param startLarger Indicates whether the start date is after the end date.
     * @param invalidRange Indicates whether the selected date range is not within the range of available data.
     */
    private void setAlertText(boolean startLarger, boolean invalidRange) {
        if (startLarger) {
            alertTxt = "Start Date is bigger than the End Date";
        } else if (invalidRange) {
            alertTxt = "Data not available for all these days. Not a valid range";
        } else {
            alertTxt = "";
        }
    }
    
    //mine
    /**
     * Sets up and displays an alert with information about the invalid date selection.
     * The content of the alert is determined by the {@code alertTxt} set in {@code setAlertText}.
     * This method is used to inform the user about why their date selection is invalid.
     */
    private void alertSetUp() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Invalid Date Selection");
        alert.setHeaderText(null);
        alert.setContentText(alertTxt);
            
        alert.showAndWait();
    }
    
}
