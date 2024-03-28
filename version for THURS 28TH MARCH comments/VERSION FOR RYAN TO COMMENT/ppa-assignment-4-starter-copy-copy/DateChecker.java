import java.util.ArrayList;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * checks 
 */
public class DateChecker
{
    /**
     * Sinlgeton instance of datechecker
     */
    private static DateChecker uniqueInstance;
    
    private ArrayList<String> UniqueDates;
    private String alertTxt;
    
    /**
     * implements the Singleton design pattern to ensure that only one instance of {@code  DateChecker} is used, preventing creation of multiple
     * instances of DateChecker when multiple classes need date checking.
     * 
     * @return The single instance of {@link DateChecker}.
     */
    public static DateChecker getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new DateChecker();
        }
        return uniqueInstance;
    }
 
    /**
     * gets the list of possible dates from the csv file using CovidDataLoader, assuming the csv file is neverchanged during the program so 
     * the list of dates are always the same, which is true in current code version.
     */
    public DateChecker()
    {
        UniqueDates = CovidDataLoader.getInstance().loadUniqueDates();
    }

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
